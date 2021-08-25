package br.com.fiap.grupo1.microservicos.perfil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.smallrye.mutiny.Uni;

@Path("/pagamento/perfil")
@ApplicationScoped
public class PerfilEndpoint {

	@Inject
	PerfilService perfilService;

	@GET
	@Counted
	@Timed
	@Path("/{idPagador}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Estatísticas do pagador nas últimas 24hs", description = "Retorna estatísticas das últimas 24hs.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Retorna estatísticas das últimas 24hs.", content = @Content(mediaType = MediaType.APPLICATION_JSON)) })
	public Uni<PerfilResponse> perfilDePagamento(
			@Parameter(description = "Id do pagador", required = false, schema = @Schema(type = SchemaType.INTEGER)) @PathParam("idPagador") Long idPagador) {
		return perfilService.perfilPorPagadorId(idPagador).map(this::toResponse);
	}

	private PerfilResponse toResponse(PerfilPagamento perfil) {
		var response = new PerfilResponse();
		response.setLimiteMaximoDiario(perfil.getLimiteMaximoDiario());
		response.setValorUsadoRecentemente(perfil.getValorUsadoRecentemente());
		response.setValorAguardandoAprovacao(perfil.getValorAguardandoAprovacao());
		response.setValorRejeitado(perfil.getValorRejeitado());
		return response;
	}
}
