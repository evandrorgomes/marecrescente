package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.SolicitacaoDTO;
import br.org.cancer.modred.controller.view.ComentarioMatchView;
import br.org.cancer.modred.model.ComentarioMatch;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para expor métodos de match.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/matchs", produces = "application/json;charset=UTF-8")
public class MatchController {

	@Autowired
	private IMatchService matchService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método para criar um match entre o paciente e o doador.
	 * @param criarSolicitacaoDto - dto para juntar em uma entidade o paciente e o doador.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro na criação do match.
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CampoMensagem> criarMatch(@RequestBody(required = true) SolicitacaoDTO criarSolicitacaoDto) {

		matchService.criarMatch(criarSolicitacaoDto.getRmr(), criarSolicitacaoDto.getIdDoador());
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "match.mensagem.sucesso_criacao_match"));
		
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	@PreAuthorize("hasPermission('VISUALIZAR_COMENTARIO_MATCH')")
	@RequestMapping(value = "{id}/comentarios", method = RequestMethod.GET)
	@JsonView(ComentarioMatchView.Lista.class)
	public ResponseEntity<List<ComentarioMatch>> listarComentarios(@PathVariable(name="id", required = true) Long idMatch) {
		return new ResponseEntity<List<ComentarioMatch>>(matchService.listarComentarios(idMatch), HttpStatus.OK);
	}
	
	/**
	 * Obtém o match ativo entre paciente e doador, informados por parâmetro.
	 * 
	 * @param rmr identificação do doador.
	 * @param doadorId identificação do doador.
	 * @return match ativo entre os dois, caso exista. Se não existir, retornar nulo.
	 */
	@PreAuthorize("hasPermission('SOLICITAR_PEDIDO_FASE_2_INTERNACIONAL')")
	@RequestMapping(value = "ativo", method = RequestMethod.GET)
	public ResponseEntity<Match> obterMatchAtivo(
			@RequestParam(name="rmr", required = true) Long rmr, @RequestParam(name="doadorId", required = true) Long doadorId) {
		return new ResponseEntity<Match>(matchService.obterMatchAtivo(rmr, doadorId), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasPermission('ANALISE_MATCH')")	
	public ResponseEntity<MatchDTO> obterMatchPorId(
			@PathVariable(name="id", required = true) Long id) {
		return ResponseEntity.ok(matchService.obterMatchPorId(id));
	}
	
	@PostMapping(value = "criarchecklistdivergenciamatch")
	public ResponseEntity<String> criarCheckListsPorDivergenciasMatch(@RequestParam("tarefa") Long idTarefa){
		matchService.criarCheckListParaMatchDivergente(idTarefa);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Método rest para disponibilizar um doador.
	 * 
	 * @param idMatch - id do match.
	 * @return mensagem de sucesso
	 */
	@PutMapping(value = "{id}/disponibilizar")
	@PreAuthorize("hasPermission('" + Recurso.DISPONIBILIZAR_DOADOR + "')")
	public ResponseEntity<String> disponibilizar(@PathVariable(name="id", required = true) Long id) {
		matchService.disponibilizarDoador(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "executarprocedurematchdoador")
	public ResponseEntity<String> executarProcedureMatchDoador(@RequestParam("tarefa") Long idTarefa){
		matchService.executarProcedureMatchDoadorPorIdTarefa(idTarefa);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "executarprocedurematchpaciente")
	public ResponseEntity<String> executarProcedureMatchPaciente(@RequestParam("tarefa") Long idTarefa){
		matchService.executarProcedureMatchPacientePorIdTarefa(idTarefa);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
