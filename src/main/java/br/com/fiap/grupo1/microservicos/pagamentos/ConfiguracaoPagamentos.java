package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_pagamento_configuracao")
public class ConfiguracaoPagamentos {

	@Id
	@Column(name = "id_pagador", nullable = false)
	private Long idPagador;

	@Column(nullable = false)
	private BigDecimal maximoDiario;

	public ConfiguracaoPagamentos() {
	}

	public ConfiguracaoPagamentos(Long idPagador, BigDecimal maximoDiario) {
		this.idPagador = idPagador;
		this.maximoDiario = maximoDiario;
	}

	public Long getIdPagador() {
		return idPagador;
	}

	public void setIdPagador(Long idPagador) {
		this.idPagador = idPagador;
	}

	public BigDecimal getMaximoDiario() {
		return maximoDiario;
	}

	public void setMaximoDiario(BigDecimal maximoDiario) {
		this.maximoDiario = maximoDiario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPagador, maximoDiario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfiguracaoPagamentos other = (ConfiguracaoPagamentos) obj;
		return Objects.equals(idPagador, other.idPagador) && Objects.equals(maximoDiario, other.maximoDiario);
	}

	@Override
	public String toString() {
		return String.format("Perfil [idPagador=%s, maximoDiario=%s]", idPagador, maximoDiario);
	}

}
