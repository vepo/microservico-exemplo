package br.com.fiap.grupo1.microservicos;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
		tags = { 
				@Tag(name = "pagamentos", description = "Operações de pagamentos") 
		}, 
		info = @Info(
				     title = "Microserviço API", 
					 version = "1.0.1", 
					 contact = @Contact(
							            name = "Example API Support", 
					                    url = "http://exampleurl.com/contact",
					                    email = "techsupport@example.com"
							 		   ),
		             license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
				     )
		)
public class MicroservicoApplication extends Application {

}
