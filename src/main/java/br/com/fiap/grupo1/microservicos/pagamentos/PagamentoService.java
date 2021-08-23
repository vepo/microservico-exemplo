package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.fiap.grupo1.microservicos.perfil.Perfil;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class PagamentoService {

	@ConfigProperty(name = "limite.diario")
	int defaultLimiteDiario;

	public PanacheQuery<Pagamento> listar(Page page) {
		return Pagamento.todos().page(page);
	}

	@ReactiveTransactional
	public Uni<Pagamento> criar(BigDecimal valor, Long idPagador, Long idRecebedor) {
		return Perfil.id(idPagador).onItem().transformToUni(perfil -> Pagamento.efetuadosRecentemente(idPagador)
				.onItem().transformToUni(entity -> Panache.withTransaction(() -> {
					var pagamentosEfetuados = (BigDecimal) ((List<?>) entity).stream()
							.map(value -> ((Pagamento) value).getValor())
							.collect(Collectors.reducing(BigDecimal.ZERO, (a, b) -> a.add(b)));
					var maximoDiario = perfil != null ? ((Perfil) perfil).getMaximoDiario()
							: new BigDecimal(defaultLimiteDiario);
					var pagamento = new Pagamento();
					pagamento.setValor(valor);
					pagamento.setIdPagador(idPagador);
					pagamento.setIdRecebedor(idRecebedor);
					if (maximoDiario.subtract(pagamentosEfetuados).subtract(valor).doubleValue() > 0) {
						pagamento.setStatus(Status.AGUARDANDO);
					} else {
						pagamento.setStatus(Status.REJEITADO_LIMITE_DIARIO);
					}
					pagamento.setTimestamp(Timestamp.from(Instant.now()));
					return pagamento.persistAndFlush();
				})));
	}

	public Uni<Pagamento> encontra(Long pagamentoId) {
		return Pagamento.id(pagamentoId);
	}
}