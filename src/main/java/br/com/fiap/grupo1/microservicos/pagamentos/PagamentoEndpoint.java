package br.com.fiap.grupo1.microservicos.pagamentos;

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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
		response.setTimestamp(pagamento.getTimestamp().getTime());
		response.setValor(pagamento.getValor());
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Lista todas as operações de pagamento", description = "Retorna todas operações de pagamentos ordenadas por tempo. Operação paginada.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Lista com pagamentos realizados. O primeiro pagamento é o mais recente", content = @Content(mediaType = MediaType.APPLICATION_JSON)) })
	public Multi<PagamentoResponse> listarPagamentos(
			@Parameter(description = "Índice da página de resultados.", required = false, schema = @Schema(type = SchemaType.INTEGER)) @QueryParam("index") @DefaultValue("0") int index,
			@Parameter(description = "Tamanho da página de resultados.", required = false, schema = @Schema(type = SchemaType.INTEGER)) @QueryParam("size") @DefaultValue("10") int size) {
		return pagamentoService.listar(Page.of(index, size)).stream().map(PagamentoEndpoint::toPagamentoResponse);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Cria novo pagamento.", description = "Cria novo pagamento.")
	@APIResponses(value = { @APIResponse(responseCode = "400", description = "Erro na requisição."),
			@APIResponse(responseCode = "200", description = "Pagamento criado com sucesso!", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PagamentoResponse.class))) })
	public Uni<PagamentoResponse> novoPagamento(@Valid CriarPagamentoRequest request) {
		return pagamentoService.criar(request.getValor(), request.getIdPagador(), request.getIdRecebedor()).onItem()
				.transform(PagamentoEndpoint::toPagamentoResponse);
	}
}