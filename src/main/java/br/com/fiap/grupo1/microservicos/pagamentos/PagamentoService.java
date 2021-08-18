package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Transactional
public class PagamentoService {

	public PanacheQuery<Pagamento> listar(Page page) {
		return Pagamento.findAll(Sort.by("timestamp")).page(page);
	}

	public Uni<Pagamento> criar(BigDecimal valor, Long idPagador, Long idRecebedor) {
		Pagamento pagamento = new Pagamento();
		pagamento.setValor(valor);
		pagamento.setIdPagador(idPagador);
		pagamento.setIdRecebedor(idRecebedor);
		pagamento.setTimestamp(Timestamp.from(Instant.now()));
		return pagamento.persistAndFlush();
	}
}