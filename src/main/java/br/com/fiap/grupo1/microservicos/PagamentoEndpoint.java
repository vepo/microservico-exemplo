package br.com.fiap.grupo1.microservicos;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pagamento")
@ApplicationScoped
public class PagamentoEndpoint {
    @GET
    public String sayHello() {
        return "Hello World!";
    }
}