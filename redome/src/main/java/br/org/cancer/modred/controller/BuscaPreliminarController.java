package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.AnaliseMatchPreliminarDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.dto.StatusRetornoInclusaoDTO;
import br.org.cancer.modred.controller.view.BuscaPreliminarView;
import br.org.cancer.modred.model.BuscaPreliminar;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IBuscaPreliminarService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Controlador que expõe os endpoints que receberão as requisições 
 * envolvendo a entidade BuscaPreliminar.
 * 
 * @author Pizão.
 *
 */
@RestController
@RequestMapping(value = "/api/buscaspreliminares", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BuscaPreliminarController { 

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IBuscaPreliminarService buscaPreliminarService;
	
	/**
	 * Salva um nova busca preliminar associado ao usuário logado.
	 * 
	 * @param buscaPreliminar busca preliminar a ser salva.
	 * @return busca após ser salva.
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('"+ Recurso.CADASTRAR_BUSCA_PRELIMINAR +"')")
	public ResponseEntity<RetornoInclusaoDTO> salvarBuscaPreliminar(@RequestBody(required = true) BuscaPreliminar buscaPreliminar) {
		
		return new ResponseEntity<RetornoInclusaoDTO>(
				buscaPreliminarService.realizarBuscaPreliminar(buscaPreliminar), HttpStatus.OK);
	}
	
	/**
	 * Obtém a busca preliminar a partir do ID.
	 * 
	 * @param idBuscaPreliminar ID da busca preliminar.
	 * @return o objeto preenchido da busca preliminar.
	 */
	@RequestMapping(value = "{idBuscaPreliminar}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('"+ Recurso.CADASTRAR_PACIENTE +"')")
	@JsonView(BuscaPreliminarView.Listar.class)
	public ResponseEntity<BuscaPreliminar> obterBuscaPreliminar(@PathVariable(required = true) Long idBuscaPreliminar) {
		BuscaPreliminar buscaPreliminar = buscaPreliminarService.findById(idBuscaPreliminar);
		return new ResponseEntity<BuscaPreliminar>(buscaPreliminar, HttpStatus.OK);
	}
	
	/**
	 * Obtém o DTO com todas as informações a serem exibidas na tela análise de match preliminar.
	 * 
	 * @param idBuscaPreliminar ID da busca preliminar.
	 * @param filtro filtro do match.
	 * @return um DTO contendo as informações de match.
	 */
	@RequestMapping(value = "{idBuscaPreliminar}/matchs", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('"+ Recurso.VISUALIZAR_MATCH_PRELIMINAR +"')")
	public ResponseEntity<AnaliseMatchPreliminarDTO> obterMatchsPreliminares(
			@PathVariable(required = true) Long idBuscaPreliminar, @RequestParam(name="filtro", required=true) Long filtro) {
		AnaliseMatchPreliminarDTO analiseMatchPreliminar = 
				buscaPreliminarService.obterListasMatchsPreliminares(idBuscaPreliminar, FiltroMatch.valueOf(filtro));
		return new ResponseEntity<AnaliseMatchPreliminarDTO>(analiseMatchPreliminar, HttpStatus.OK);
	}
	
	/**
	 * [FAKE] Simula quando uma nova busca preliminar é realizada e não encontra matchs.
	 * 
	 * @param buscaPreliminar busca preliminar a ser salva.
	 * @return busca após ser salva.
	 */
	@RequestMapping(value = "nomatchs", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('"+ Recurso.CADASTRAR_BUSCA_PRELIMINAR +"')")
	public ResponseEntity<RetornoInclusaoDTO> simularBuscaPreliminarNoMatchs(@RequestBody(required = true) BuscaPreliminar buscaPreliminar) {
		RetornoInclusaoDTO dto = buscaPreliminarService.realizarBuscaPreliminar(buscaPreliminar);
		dto.setStatus(StatusRetornoInclusaoDTO.FALHA);
		dto.setMensagem(AppUtil.getMensagem(messageSource, "erro.buscapreliminar.matchs.nao.encontrados"));
		return new ResponseEntity<RetornoInclusaoDTO>(dto, HttpStatus.OK);
	}

}