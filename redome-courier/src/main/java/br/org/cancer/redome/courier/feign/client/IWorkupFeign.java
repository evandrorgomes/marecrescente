package br.org.cancer.redome.courier.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.redome.courier.feign.client.dto.LogisticaMaterialTransporteDTO;

public interface IWorkupFeign {
	
	@GetMapping(value = "/api/pedidologistica/{id}/material/dadosparatransportadora")
	LogisticaMaterialTransporteDTO obterLogisitcaMaterial(@PathVariable(required = true, name="id") Long id);
	
	@SuppressWarnings("rawtypes")
	@PatchMapping(value = "/api/pedidologistica/{id}/material/nacional/aereo")
	ResponseEntity atualizarLogisticaMaterialParaAereo(@PathVariable(required = true) Long id);

}
