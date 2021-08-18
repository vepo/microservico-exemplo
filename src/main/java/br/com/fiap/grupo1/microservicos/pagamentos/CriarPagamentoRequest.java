package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CriarPagamento", description = "Parâmetros para executar a criação de novo pagamento")
public class CriarPagamentoRequest {

	@Schema(description = "Valor do pagamento. Deve ser um valor positivo com até duas casas decimais.", required = true)
	@NotNull(message = "Valor é um campo obrigatório")
	@Positive(message = "Pagamento não pode ser negativo")
	private BigDecimal valor;

	@Schema(description = "Identificador do pagamento. Deve ser um valor inteiro positivo.", required = true)
	@NotNull(message = "Id do pagador é um campo obrigatório")
	@Positive(message = "Id do pagador inválido")
	private Long idPagador;

	@Schema(description = "Identificador do recebedor. Deve ser um valor inteiro positivo.", required = true)
	@NotNull(message = "Id do recebedor é um campo obrigatório")
	@Positive(message = "Id do recebedor inválido")
	private Long idRecebedor;

	public CriarPagamentoRequest() {
	}

	public CriarPagamentoRequest(
			@NotNull(message = "Valor é um campo obrigatório") @Positive(message = "Pagamento não pode ser negativo") BigDecimal valor,
			@NotNull(message = "Id do pagador é um campo obrigatório") @Positive(message = "Id do pagador inválido") Long idPagador,
			@NotNull(message = "Id do recebedor é um campo obrigatório") @Positive(message = "Id do recebedor inválido") Long idRecebedor) {
		this.valor = valor;
		this.idPagador = idPagador;
		this.idRecebedor = idRecebedor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getIdPagador() {
		return idPagador;
	}

	public void setIdPagador(Long idPagador) {
		this.idPagador = idPagador;
	}

	public Long getIdRecebedor() {
		return idRecebedor;
	}

	public void setIdRecebedor(Long idRecebedor) {
		this.idRecebedor = idRecebedor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPagador, idRecebedor, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CriarPagamentoRequest other = (CriarPagamentoRequest) obj;
		return Objects.equals(idPagador, other.idPagador) && Objects.equals(idRecebedor, other.idRecebedor)
				&& Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return String.format("CriarPagamentoRequest [valor=%s, idPagador=%s, idRecebedor=%s]", valor, idPagador,
				idRecebedor);
	}

}
