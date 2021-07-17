package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.dto.doador.RessalvaDoadorDTO;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.model.RessalvaDoador;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IRessalvaDoadorService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra as chamadas REST envolvendo a entidade Doador.
 * 
 * @author Pizão.
 *
 */
@RestController
@RequestMapping(value = "/api/ressalvas", produces = "application/json;charset=UTF-8")
public class RessalvaDoadorController {

	@Autowired
	private IRessalvaDoadorService ressalvaService;


	/**
	 * Salva uma nova ressalva.
	 * 
	 * @param ressalvaDoador - objecto ressalva preenchido.
	 * @return mensagem de sucesso.
	 */
	@PreAuthorize("hasPermission('" + Recurso.ADICIONAR_RESSALVA_DOADOR + "')")	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CampoMensagem> salvarRessalvaDoadorDTO(@RequestBody(required = true) RessalvaDoadorDTO ressalvaDoadorDTO) {
		RessalvaDoador ressalvaDoadorPersistido = ressalvaService.salvar(ressalvaDoadorDTO);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("ressalva.incluido", null, ressalvaDoadorPersistido.getId()),
				HttpStatus.OK);
	}
	
	/**
	 * Exclui logicamente uma ressalva e, caso necessário, atualiza o status do doador.
	 * 
	 * @param idRessalva ID da ressalva.
	 * @return TRUE se foi realizado com sucesso, FALSE se houve falha.
	 */
	@PreAuthorize("hasPermission('EXCLUIR_RESSALVA_DOADOR')")
	@RequestMapping(value = "{idRessalva}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> excluirRessalva(@PathVariable(required = true) Long idRessalva) {
		return new ResponseEntity<Boolean>(ressalvaService.excluirRessalva(idRessalva), HttpStatus.OK);
	}

	/**
	 * Adiciona uma nova ressalva ao doador.
	 * 
	 * @param id - identificador de doador.
	 * @param ressalva - nova ressalva adicionada ao doador.
	 * @return mensagem de sucesso.
	 */
	@PreAuthorize("hasPermission('" + Recurso.ADICIONAR_RESSALVA_DOADOR + "')")
	@RequestMapping(value = "{id}/ressalvas", method = RequestMethod.POST)
	public ResponseEntity<RetornoInclusaoDTO> adicionarEmailContato(
			@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) String ressalva) {
		return new ResponseEntity<RetornoInclusaoDTO>(ressalvaService.adicionarRessalva(id, ressalva), HttpStatus.OK);
	}

	/**
	 * Lista as ressalvas de um doador.
	 * 
	 * @param id Identificador do doador.
	 * @return lista de ressalvas.
	 */
	@PreAuthorize("hasPermission('AVALIAR_WORKUP_DOADOR') || hasPermission('VISUALIZAR_RESSALVA_MATCH')")
	@RequestMapping(value = "{id}/ressalvas", method = RequestMethod.GET)
	@JsonView(DoadorView.Ressalva.class)
	public ResponseEntity<List<RessalvaDoador>> listarRessalvasPorid(@PathVariable(required = true) Long id) {
		return new ResponseEntity<List<RessalvaDoador>>(ressalvaService.listarRessalvas(id), HttpStatus.OK);
	}

}
