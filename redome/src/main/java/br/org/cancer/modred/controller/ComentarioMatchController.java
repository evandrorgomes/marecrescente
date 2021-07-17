package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.model.ComentarioMatch;
import br.org.cancer.modred.service.impl.ComentarioMatchService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de exposição de métodos referentes a comentários de match.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/comentariosmatch", produces = "application/json;charset=UTF-8")
public class ComentarioMatchController {
	
	@Autowired
	private ComentarioMatchService comentarioMatchService;
	
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método para gravar comentário de match.
	 * @param comentario - objeto de comentário de match.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro na criação do match.
	 */
	@PreAuthorize("hasPermission('ADICIONAR_COMENTARIO_MATCH')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<RetornoInclusaoDTO> salvarComentario(@RequestBody(required = true) ComentarioMatch comentario) {
		RetornoInclusaoDTO retorno = new RetornoInclusaoDTO(comentarioMatchService.save(comentario).getId(), 
				AppUtil.getMensagem(messageSource, "comentario.match.mensagem.sucesso_gravacao_match"));
				
		return new ResponseEntity<RetornoInclusaoDTO>(retorno, HttpStatus.OK);
	}

}
