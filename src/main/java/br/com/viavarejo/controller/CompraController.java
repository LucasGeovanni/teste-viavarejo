package br.com.viavarejo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.viavarejo.models.Compra;
import br.com.viavarejo.models.Parcela;
import br.com.viavarejo.service.ICompraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "compra")
public class CompraController {

	@Autowired
	private ICompraService compraService;

	@ApiOperation(value = "Criar uma nova compra")
	@PostMapping(value = "/compra", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Parcela>> create(@RequestBody Compra compra) {
		return ResponseEntity.ok(compraService.processaCompra(compra));
	}

}
