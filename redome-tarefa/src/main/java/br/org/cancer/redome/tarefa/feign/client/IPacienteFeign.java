package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.cancer.redome.tarefa.dto.PacienteWmdaDTO;

public interface IPacienteFeign {
	
	@GetMapping("/api/pacientes/{rmr}/obterpacientewmda")
	public PacienteWmdaDTO obterPacienteDtoWmdaPorRmr(@PathVariable(name = "rmr", required = true) Long rmr);
	
	@PutMapping("/api/pacientes/{rmr}/wmda")
	public String atualizarWmdaIdPorRmrDoPaciente(@PathVariable(name="rmr", required = true) Long rmr, @RequestBody(required=true) String idWmda);
	
}
