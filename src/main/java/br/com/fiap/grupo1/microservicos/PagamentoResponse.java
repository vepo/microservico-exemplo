package br.com.fiap.grupo1.microservicos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class PagamentoResponse {
	private Long id;
	private BigDecimal valor;
	private Long idPagador;
	private Long idRecebedor;
	private Timestamp timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idPagador, idRecebedor, timestamp, valor);
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
		PagamentoResponse other = (PagamentoResponse) obj;
		return Objects.equals(id, other.id) && Objects.equals(idPagador, other.idPagador)
				&& Objects.equals(idRecebedor, other.idRecebedor) && Objects.equals(timestamp, other.timestamp)
				&& Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return String.format("PagamentoResponse [id=%s, valor=%s, idPagador=%s, idRecebedor=%s, timestamp=%s]", id,
				valor, idPagador, idRecebedor, timestamp);
	}

}
