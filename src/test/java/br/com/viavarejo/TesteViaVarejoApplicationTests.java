package br.com.viavarejo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	ICompraService compraService;

	@Test
	public void taxaParcela() {
		BigDecimal parcela = compraService.aplicarTaxaParcela(new BigDecimal("500"), new BigDecimal("0.033"));
		Assert.assertEquals(new BigDecimal("500.165"), parcela);
	}

	@Test
	public void buscarTaxaSelic() {
		String dataHoje = LocalDate.now().format(formatter);
		String hojeMenos30Dias =  LocalDate.now().minusDays(30).format(formatter);
		
		BigDecimal valorTaxaAtual = new BigDecimal("0");
		
		BigDecimal valorTaxaEsperado = new BigDecimal("0.532295");

		valorTaxaAtual = compraService.buscarTaxa(hojeMenos30Dias, dataHoje)
										  .stream()
										  .map(TaxaSelic::getValor)
										  .reduce(BigDecimal::add)
										  .orElse(valorTaxaAtual);		
		
		Assert.assertEquals(valorTaxaEsperado, valorTaxaAtual);

	}
	
	@Test
	public void processaCompra() {
		
		Produto produto = new Produto(1L, "teste", new BigDecimal("9999.99"));
		int qtdeParcelas = 7;
		CondicaoPagamento condicaoPagamento = new CondicaoPagamento(new BigDecimal("9999.99"), qtdeParcelas);		
		Compra compra  = new Compra(produto, condicaoPagamento);	
		
		List<Parcela> parcelas = compraService.processaCompra(compra);
		Assert.assertEquals(qtdeParcelas, parcelas.size());
		Assert.assertEquals(new BigDecimal("0.532295"), parcelas.get(0).getTaxaJurosAoMes());
		Assert.assertEquals(new BigDecimal("1436.1742066815"), parcelas.get(0).getValor());
	}

}
