package br.com.viavarejo;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.viavarejo.models.Compra;
import br.com.viavarejo.models.CondicaoPagamento;
import br.com.viavarejo.models.Parcela;
import br.com.viavarejo.models.Produto;
import br.com.viavarejo.models.TaxaSelic;
import br.com.viavarejo.service.ICompraService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteViaVarejoApplicationTests {

	@Autowired
	ICompraService compraService;

	@Test
	public void taxaParcela() {
		BigDecimal parcela = compraService.aplicarTaxaParcela(new BigDecimal("500"), new BigDecimal("0.033"));
		Assert.assertEquals(new BigDecimal("500.165"), parcela);
	}

	@Test
	public void buscarTaxaSelic() {
		String data = "01/12/2000";
		BigDecimal valorTaxaEsperado = new BigDecimal("0.060281");

		BigDecimal valorTaxaAtual = compraService.buscarTaxa(data, data)
												 .stream()
												 .map(TaxaSelic::getValor)
												 .findFirst().get();
		
		Assert.assertEquals(valorTaxaEsperado, valorTaxaAtual);

	}
	
	@Test
	public void processaCompra() {
		
		Produto produto = new Produto(1L, "teste", new BigDecimal("950000.00"));
		int qtdeParcelas = 8;
		CondicaoPagamento condicaoPagamento = new CondicaoPagamento(new BigDecimal("750000.00"), qtdeParcelas);		
		Compra compra  = new Compra(produto, condicaoPagamento);	
		
		List<Parcela> parcelas = compraService.processaCompra(compra);
		Assert.assertEquals(qtdeParcelas, parcelas.size());
		Assert.assertEquals(new BigDecimal("0.022751"), parcelas.get(0).getTaxaJurosAoMes());
		Assert.assertEquals(new BigDecimal("118777.01681250"), parcelas.get(0).getValor());
	}

}
