package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Pagamento", description = "Detalha todos os campos de um pagamento.")
public class PagamentoResponse {
	@Schema(description = "Identificador único do pagamento")
	private Long id;
	
	@Schema(description = "Valor registrado do pagamento")
	private BigDecimal valor;
	
	@Schema(description = "Indentificador do pagador")
	private Long idPagador;
	
	@Schema(description = "Indentificador do recebedor")
	private Long idRecebedor;
	
	@Schema(description = "Timestamp de confirmação da operação")
	private long timestamp;

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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
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
