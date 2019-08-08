package br.com.viavarejo.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.viavarejo.models.TaxaSelic;

@FeignClient(url = "${url-selic}", name = "selic")
public interface SelicRestClient {

	@GetMapping("")
	List<TaxaSelic> buscarTaxa(@RequestParam("dataInicial") String dataInicial,
			@RequestParam("dataFinal") String dataFinal);

}
