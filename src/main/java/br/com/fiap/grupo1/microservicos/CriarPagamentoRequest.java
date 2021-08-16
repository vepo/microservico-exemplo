package br.com.fiap.grupo1.microservicos;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CriarPagamentoRequest {
	@NotNull(message = "Valor é um campo obrigatório")
	@Positive(message = "Pagamento não pode ser negativo")
	private BigDecimal valor;

	@NotNull(message = "Id do pagador é um campo obrigatório")
	@Positive(message = "Id do pagador inválido")
	private Long idPagador;

	@NotNull(message = "Id do recebedor é um campo obrigatório")
	@Positive(message = "Id do recebedor inválido")
	private Long idRecebedor;

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
