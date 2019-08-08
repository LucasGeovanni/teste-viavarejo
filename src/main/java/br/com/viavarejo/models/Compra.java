package br.com.viavarejo.models;

import javax.validation.constraints.NotNull;

public class Compra {

	@NotNull(message = "É necessário informar o produto")
	private Produto produto;

	@NotNull(message = "É necessário informar a condição de pagamento")
	private CondicaoPagamento condicaoPagamento;

	public Compra() {

	}

	public Compra(@NotNull(message = "É necessário informar o produto") Produto produto,
			@NotNull(message = "É necessário informar a condição de pagamento") CondicaoPagamento condicaoPagamento) {
		super();
		this.produto = produto;
		this.condicaoPagamento = condicaoPagamento;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

}
