package br.com.fiap.grupo1.microservicos.pagamentos;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

	public PanacheQuery<Pagamento> todos() {
		return findAll(Sort.by("timestamp"));
	}

	private static long DIA_EM_MILLIS = 1000L * 60L * 60L * 24L;

	public Uni<List<Pagamento>> efetuadosRecentemente(Long idPagador) {
		return pagamentosRecentemente(idPagador, Status.EFETUADO);
	}

	public Uni<List<Pagamento>> pagamentosRecentemente(Long idPagador, Status status) {
		return find("FROM Pagamento pag WHERE pag.timestamp > ?1 AND pag.idPagador = ?2 AND pag.status = ?3",
				Date.from(Instant.now().minusMillis(DIA_EM_MILLIS)), idPagador, status).list();
	}
}
