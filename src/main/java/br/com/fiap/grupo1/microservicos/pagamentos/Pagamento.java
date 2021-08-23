package br.com.fiap.grupo1.microservicos.pagamentos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

@Entity
@Table(name = "tb_pagamentos")
public class Pagamento extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private BigDecimal valor;

	@Column(name = "id_pagador", nullable = false)
	private Long idPagador;

	@Column(name = "id_recebedor", nullable = false)
	private Long idRecebedor;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.idPagador, this.idRecebedor, this.valor, this.status, this.timestamp);
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
		Pagamento other = (Pagamento) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.idPagador, other.idPagador)
				&& Objects.equals(this.idRecebedor, other.idRecebedor) && Objects.equals(this.valor, other.valor)
				&& Objects.equals(this.status, other.status) && Objects.equals(this.timestamp, other.timestamp);
	}

	@Override
	public String toString() {
		return String.format("Pagamento [id=%d, idPagador=%d, idRecebedor=%d, valor=%f, status=%s, timestamp=%s]",
				this.id, this.idPagador, this.idRecebedor, this.valor, this.status, this.timestamp);
	}

	public static PanacheQuery<Pagamento> todos() {
		return findAll(Sort.by("timestamp"));
	}

	private static long DIA_EM_MILLIS = 1000 * 60 * 60 * 24;

	public static Uni<List<PanacheEntityBase>> efetuadosRecentemente(Long idPagador) {
		return find("FROM Pagamento pag WHERE pag.timestamp > ?1 AND pag.idPagador = ?2 AND pag.status = ?3",
				Date.from(Instant.now().minusMillis(DIA_EM_MILLIS)), idPagador, Status.EFETUADO).list();
	}

	public static Uni<Pagamento> id(Long pagamentoId) {
		return findById(pagamentoId);
	}
}
