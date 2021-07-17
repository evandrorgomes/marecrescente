package br.org.cancer.redome.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.redome.feign.client.dto.MedicoDTO;

public interface IMedicoFeign {
		
	@GetMapping(value = "/api/medicos/logado")
	MedicoDTO obterMedicoAssociadoUsuarioLogado();
	
	
	@GetMapping(value = "/api/medicos/{id}")	
	public MedicoDTO obterMedico(@PathVariable(required=true, name="id") Long idMedico);

}
