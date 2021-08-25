package br.com.fiap.grupo1.microservicos.pagamentos;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ConfiguracaoPagamentosRepository implements PanacheRepository<ConfiguracaoPagamentos> {

	public Uni<ConfiguracaoPagamentos> comIdPagador(Long idPagador) {
		return findById(idPagador);
	}
}
