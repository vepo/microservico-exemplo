package br.com.fiap.grupo1.microservices.pagamentos;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fiap.grupo1.microservicos.pagamentos.CriarPagamentoRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@DisplayName("Pagamentos")
public class PagamentoEndpointTest {
	@Test
	@DisplayName("Listar todos")
	public void listarPagamentosTest() {
		given().when().get("/pagamento").then().statusCode(200).body("$.size()", equalTo(0));
	}

	@Test
	@DisplayName("Criar Pagamento")
	public void criarPagamentoTest() {
		given().accept(ContentType.JSON).contentType(ContentType.JSON)
				.body(new CriarPagamentoRequest(BigDecimal.valueOf(2.5), 222L, 111L)).put("/pagamento").then()
				.assertThat().statusCode(200);
		given().when().get("/pagamento").then().statusCode(200).body("$.size()", equalTo(1));
	}

}
