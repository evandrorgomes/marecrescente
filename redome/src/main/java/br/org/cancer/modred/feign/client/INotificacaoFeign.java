package br.org.cancer.modred.feign.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.modred.feign.dto.NotificacaoDTO;

public interface INotificacaoFeign {

	@GetMapping("/api/notificacoes/{rmr}/contar")
	public Long contarNotificacoes(@PathVariable(required = true, name= "rmr") Long rmr, 
			@RequestParam(required = false, name= "lida") Boolean lida);

	@PostMapping("/api/notificacoes/criarnotificacao")
	public List<NotificacaoDTO> criarNotificacao(@RequestBody(required = true) NotificacaoDTO notificacaoDto);

}
