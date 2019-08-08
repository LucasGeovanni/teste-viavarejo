package br.com.viavarejo.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viavarejo.models.Compra;
import br.com.viavarejo.models.Parcela;
import br.com.viavarejo.models.TaxaSelic;
import br.com.viavarejo.restclient.SelicRestClient;
import br.com.viavarejo.service.ICompraService;

@Service
public class CompraServiceImpl implements ICompraService {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	private SelicRestClient selicRestClient;

	@Override
	public List<Parcela> processaCompra(Compra compra) {
		List<Parcela> parcelas = new ArrayList<Parcela>();

		int qtdeParcelas = compra.getCondicaoPagamento().getQtdeParcelas();
		BigDecimal valorParcela = compra.getProduto().getValor().divide(new BigDecimal(qtdeParcelas));
		BigDecimal taxaJuros = new BigDecimal("0");

		String dataHoje = dataHoje();

		if (compra.getCondicaoPagamento().getQtdeParcelas() > 6) {			

			taxaJuros = buscarTaxa(dataHoje, dataHoje)
					.stream()
					.findFirst()
					.map(TaxaSelic::getValor)
					.orElse(taxaJuros);		
			
			valorParcela = aplicarTaxaParcela(valorParcela, taxaJuros);
		}			   

		for (int i = 0; i < qtdeParcelas; i++) {
			parcelas.add(new Parcela(i + 1, valorParcela, taxaJuros));
		}

		return parcelas;
	}	


	@Override
	public List<TaxaSelic> buscarTaxa(String dataInicial, String dataFinal) {
		return selicRestClient.buscarTaxa(dataInicial, dataFinal);
	}

	@Override
	public String dataHoje() {
		return LocalDate.now()
				.format(formatter);	
	}

	@Override
	public BigDecimal aplicarTaxaParcela(BigDecimal valorParcela, BigDecimal taxaJuros) {
		return valorParcela.add(valorParcela.multiply(taxaJuros).divide(new BigDecimal("100")));
	}

}
