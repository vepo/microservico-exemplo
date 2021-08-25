package br.com.fiap.grupo1.microservicos.perfil;

import static java.util.stream.Stream.concat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.fiap.grupo1.microservicos.pagamentos.ConfiguracaoPagamentos;
import br.com.fiap.grupo1.microservicos.pagamentos.ConfiguracaoPagamentosRepository;
import br.com.fiap.grupo1.microservicos.pagamentos.Pagamento;
import br.com.fiap.grupo1.microservicos.pagamentos.PagamentoRepository;
import br.com.fiap.grupo1.microservicos.pagamentos.Status;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class PerfilService {

	@Inject
	ConfiguracaoPagamentosRepository perfilRepository;

	@Inject
	PagamentoRepository pagamentoRepository;

	@ConfigProperty(name = "limite.diario")
	int defaultLimiteDiario;

	@ReactiveTransactional
	public Uni<PerfilPagamento> perfilPorPagadorId(Long idPagador) {
		return Uni.combine().all()
				.unis(perfilRepository.comIdPagador(idPagador), pagamentoRepository.efetuadosRecentemente(idPagador),
						pagamentoRepository.pagamentosRecentemente(idPagador, Status.AGUARDANDO),
						pagamentoRepository.pagamentosRecentemente(idPagador, Status.REJEITADO_LIMITE_DIARIO),
						pagamentoRepository.pagamentosRecentemente(idPagador, Status.REJEITADO_SEM_SALDO))
				.combinedWith(this::montarPerfil);
	}

	public PerfilPagamento montarPerfil(ConfiguracaoPagamentos configuracao, List<Pagamento> efetuadosRecentemente,
			List<Pagamento> aguardandoEfetivacao, List<Pagamento> rejeitadoLimiteDiario,
			List<Pagamento> rejeitadoSemSaldo) {
		var perfil = new PerfilPagamento();
		if (Objects.nonNull(configuracao)) {
			perfil.setLimiteMaximoDiario(configuracao.getMaximoDiario());
		} else {
			perfil.setLimiteMaximoDiario(new BigDecimal(defaultLimiteDiario));
		}
		perfil.setValorUsadoRecentemente(valorTotal(efetuadosRecentemente.stream()));
		perfil.setValorAguardandoAprovacao(valorTotal(aguardandoEfetivacao.stream()));
		perfil.setValorRejeitado(valorTotal(concat(rejeitadoLimiteDiario.stream(), rejeitadoSemSaldo.stream())));
		return perfil;
	}

	private BigDecimal valorTotal(Stream<Pagamento> pagamentos) {
		return (BigDecimal) pagamentos.map(Pagamento::getValor).collect(Pagamento.somaBigDecima());
	}
}
