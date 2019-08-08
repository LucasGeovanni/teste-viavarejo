package br.com.viavarejo.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TaxaSelic {

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;

	private BigDecimal valor;

	public TaxaSelic() {

	}

	@JsonFormat(pattern = "dd/MM/yyyy")
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
