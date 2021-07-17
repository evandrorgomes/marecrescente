package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.redome.tarefa.dto.ConfiguracaoDTO;

/**
 * Interface de acesso aos métodos de pedido de exame do redome. 
 * @author Flipe Paes
 *
 */
public interface IConfiguracaoFeign {
	
	
	@GetMapping(value= "/api/configuracao/{chave}")
    public ResponseEntity<ConfiguracaoDTO> obterConfiguracaoPorChave(@PathVariable("chave") String chave);

}
