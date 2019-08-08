package br.com.viavarejo.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.viavarejo.models.Compra;
import br.com.viavarejo.models.Parcela;
import br.com.viavarejo.models.TaxaSelic;

public interface ICompraService {

	List<Parcela> processaCompra(Compra compra);

	List<TaxaSelic> buscarTaxa(String dataInicial, String dataFinal);

	String dataHoje();

	BigDecimal aplicarTaxaParcela(BigDecimal valorParcela, BigDecimal taxaJuros);

}