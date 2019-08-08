package br.com.viavarejo.models;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class CondicaoPagamento {

	@NotNull(message = "É necessário informar o valor de entrada")
	private BigDecimal valorEntrada;

	@NotNull(message = "É necessário informar a quantidade de parcelas")
	private Integer qtdeParcelas;

	public CondicaoPagamento() {

	}

	public CondicaoPagamento(@NotNull(message = "É necessário informar o valor de entrada") BigDecimal valorEntrada,
			@NotNull(message = "É necessário informar a quantidade de parcelas") Integer qtdeParcelas) {
		super();
		this.valorEntrada = valorEntrada;
		this.qtdeParcelas = qtdeParcelas;
	}

	public BigDecimal getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(BigDecimal valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public Integer getQtdeParcelas() {
		return qtdeParcelas;
	}

	public void setQtdeParcelas(Integer qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}

}
