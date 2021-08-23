package br.com.fiap.grupo1.microservicos.perfil;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;

@Entity
@Table(name = "tb_pagamento_perfis")
public class Perfil extends PanacheEntityBase {

	@Id
	@Column(name = "id_pagador", nullable = false)
	private Long idPagador;

	@Column(nullable = false)
	private BigDecimal maximoDiario;

	public Perfil() {
	}

	public Perfil(Long idPagador, BigDecimal maximoDiario) {
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
		Perfil other = (Perfil) obj;
		return Objects.equals(idPagador, other.idPagador) && Objects.equals(maximoDiario, other.maximoDiario);
	}

	@Override
	public String toString() {
		return String.format("Perfil [idPagador=%s, maximoDiario=%s]", idPagador, maximoDiario);
	}

	public static Uni<Perfil> id(Long idPagador) {
		return findById(idPagador);
	}

}
