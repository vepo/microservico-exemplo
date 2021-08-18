package br.com.fiap.grupo1.microservices.pagamentos;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.fiap.grupo1.microservicos.pagamentos.CriarPagamentoRequest;
import br.com.fiap.grupo1.microservicos.pagamentos.Pagamento;
import br.com.fiap.grupo1.microservicos.pagamentos.PagamentoService;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@Disabled
@DisplayName("Pagamentos")
public class PagamentoEndpointTest {

	@BeforeEach
	void setUp() {
		List<Pagamento> pagamentos = new ArrayList<>();
		PagamentoService mock = Mockito.mock(PagamentoService.class);
		Mockito.when(mock.criar(Mockito.any(), Mockito.any(), Mockito.any())).thenAnswer(invocation -> {
			Pagamento pagamento = new Pagamento();
			pagamento.setId(pagamentos.stream().mapToLong(Pagamento::getId).max().orElse(1L));
			pagamento.setValor(invocation.getArgument(0, BigDecimal.class));
			pagamento.setIdPagador(invocation.getArgument(1, Long.class));
			pagamento.setIdRecebedor(invocation.getArgument(2, Long.class));
			pagamento.setTimestamp(Timestamp.from(Instant.now()));
			return pagamento;
		});
		QuarkusMock.installMockForType(mock, PagamentoService.class);
	}

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
