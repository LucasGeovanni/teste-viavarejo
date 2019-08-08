package br.com.viavarejo.models;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class Produto {

	@NotNull(message = "É necessário informar o código")
	private Long codigo;

	private String nome;

	@NotNull(message = "É necessário informar o valor")
	private BigDecimal valor;

	public Produto() {

	}

	public Produto(@NotNull(message = "É necessário informar o código") Long codigo, String nome,
			@NotNull(message = "É necessário informar o valor") BigDecimal valor) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.valor = valor;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
