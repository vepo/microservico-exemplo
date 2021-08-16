package br.com.fiap.grupo1.microservicos;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/pagamento")
@ApplicationScoped
public class PagamentoEndpoint {

	@Inject
	PagamentoService pagamentoService;

	private static PagamentoResponse toPagamentoResponse(Pagamento pagamento) {
		PagamentoResponse response = new PagamentoResponse();
		response.setId(pagamento.getId());
		response.setIdPagador(pagamento.getIdPagador());
		response.setIdRecebedor(pagamento.getIdRecebedor());
		response.setTimestamp(pagamento.getTimestamp());
		response.setValor(pagamento.getValor());
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Multi<PagamentoResponse> listarPagamentos(@QueryParam("index") @DefaultValue("0") int index,
			@QueryParam("size") @DefaultValue("10") int size) {
		return pagamentoService.listar(Page.of(index, size)).stream().map(PagamentoEndpoint::toPagamentoResponse);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Uni<PagamentoResponse> novoPagamento(@Valid CriarPagamentoRequest request) {
		return pagamentoService.criar(request.getValor(), request.getIdPagador(), request.getIdRecebedor()).onItem()
				.transform(PagamentoEndpoint::toPagamentoResponse);
	}
}