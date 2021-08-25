package br.com.fiap.grupo1.microservicos.perfil;

import java.math.BigDecimal;
import java.util.Objects;

public class PerfilPagamento {
	private BigDecimal limiteMaximoDiario;
	private BigDecimal valorUsadoRecentemente;
	private BigDecimal valorAguardandoAprovacao;
	private BigDecimal valorRejeitado;

	public BigDecimal getLimiteMaximoDiario() {
		return limiteMaximoDiario;
	}

	public void setLimiteMaximoDiario(BigDecimal limiteMaximoDiario) {
		this.limiteMaximoDiario = limiteMaximoDiario;
	}

	public BigDecimal getValorUsadoRecentemente() {
		return valorUsadoRecentemente;
	}

	public void setValorUsadoRecentemente(BigDecimal valorUsadoRecentemente) {
		this.valorUsadoRecentemente = valorUsadoRecentemente;
	}

	public BigDecimal getValorAguardandoAprovacao() {
		return valorAguardandoAprovacao;
	}

	public void setValorAguardandoAprovacao(BigDecimal valorAguardandoAprovacao) {
		this.valorAguardandoAprovacao = valorAguardandoAprovacao;
	}

	public BigDecimal getValorRejeitado() {
		return valorRejeitado;
	}

	public void setValorRejeitado(BigDecimal valorRejeitado) {
		this.valorRejeitado = valorRejeitado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(limiteMaximoDiario, valorAguardandoAprovacao, valorRejeitado, valorUsadoRecentemente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilPagamento other = (PerfilPagamento) obj;
		return Objects.equals(limiteMaximoDiario, other.limiteMaximoDiario)
				&& Objects.equals(valorAguardandoAprovacao, other.valorAguardandoAprovacao)
				&& Objects.equals(valorRejeitado, other.valorRejeitado)
				&& Objects.equals(valorUsadoRecentemente, other.valorUsadoRecentemente);
	}

	@Override
	public String toString() {
		return String.format(
				"PerfilPagamento [limiteMaximoDiario=%s, valorUsadoRecentemente=%s, valorAguardandoAprovacao=%s, valorRejeitado=%s]",
				limiteMaximoDiario, valorUsadoRecentemente, valorAguardandoAprovacao, valorRejeitado);
	}

}
