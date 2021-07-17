package br.org.cancer.redome.workup.feign.client;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.cancer.redome.workup.dto.NotificacaoDTO;

public interface INotificacaoFeign {

	@PostMapping("/api/notificacoes/criarnotificacao")
	public List<NotificacaoDTO> criarNotificacao(@RequestBody(required = true) NotificacaoDTO notificacaoDto);

}
