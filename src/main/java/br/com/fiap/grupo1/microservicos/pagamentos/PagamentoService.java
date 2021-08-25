package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class PagamentoService {

	@ConfigProperty(name = "limite.diario")
	int defaultLimiteDiario;

	@Inject
	PagamentoRepository pagamentoRepository;

	@Inject
	ConfiguracaoPagamentosRepository perfilRepository;

	@ReactiveTransactional
	public Uni<Pagamento> criar(BigDecimal valor, Long idPagador, Long idRecebedor) {
		return Uni.combine().all()
				.unis(perfilRepository.comIdPagador(idPagador), pagamentoRepository.efetuadosRecentemente(idPagador))
				.combinedWith((perfil, pagamentosRecentes) -> criarPagamento(pagamentosRecentes, perfil, valor,
						idPagador, idRecebedor))
				.flatMap(this::salvarPagamento);
	}

	@Transactional
	private Uni<Pagamento> salvarPagamento(Pagamento pagamento) {
		return pagamentoRepository.persistAndFlush(pagamento);
	}

	private Pagamento criarPagamento(List<Pagamento> pagamentosRecentes, ConfiguracaoPagamentos perfil,
			BigDecimal valor, Long idPagador, Long idRecebedor) {
		var valorUsadoRecentemente = (BigDecimal) pagamentosRecentes.stream().map(Pagamento::getValor)
				.collect(Pagamento.somaBigDecima());
		var maximoDiario = perfil != null ? ((ConfiguracaoPagamentos) perfil).getMaximoDiario()
				: new BigDecimal(defaultLimiteDiario);
		var pagamento = new Pagamento();
		pagamento.setValor(valor);
		pagamento.setIdPagador(idPagador);
		pagamento.setIdRecebedor(idRecebedor);
		if (maximoDiario.subtract(valorUsadoRecentemente).subtract(valor).doubleValue() > 0) {
			pagamento.setStatus(Status.AGUARDANDO);
		} else {
			pagamento.setStatus(Status.REJEITADO_LIMITE_DIARIO);
		}
		pagamento.setTimestamp(Timestamp.from(Instant.now()));
		return pagamento;
	}

	public Multi<Pagamento> listar(int index, int size) {
		return pagamentoRepository.todos().page(Page.of(index, size)).stream();
	}

	public Uni<Pagamento> pagamentoPorId(Long pagamentoId) {
		return pagamentoRepository.findById(pagamentoId);
	}

}