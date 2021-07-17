package br.org.cancer.modred.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.TentativaContatoDoadorView;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.TentativaContatoDoador;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.ITentativaContatoDoadorService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para a entidade de tentativa de contato.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/tentativascontato", produces = "application/json;charset=UTF-8")
public class TentativaContatoDoadorController {

	@Autowired
	private IDoadorNacionalService doadorService;

	@Autowired
	private ITentativaContatoDoadorService tentativaContatoService;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Obtém um doador por uma tentativa de contato.
	 * 
	 * @param tentativaContatoId - id da tentativa de contato.
	 * @return response contendo doador.
	 */
	@GetMapping(value = "{id}/doadores")
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
	@JsonView(DoadorView.ContatoFase2.class)
	public ResponseEntity<DoadorNacional> obterDoadorAssociadoATentativaDeContato(
			@PathVariable(name = "id", required = true) Long tentativaContatoId) {
		return ResponseEntity.ok(doadorService.obterDoadorPorTentativaDeContato(tentativaContatoId));
	}

	/**
	 * Obtém uma tentativa de contato por id.
	 * 
	 * @param tentativaContatoId - id da tentativa de contato.
	 * @return response contendo tentativa de contato.
	 */
	@GetMapping(value = "{id}")
	@JsonView(TentativaContatoDoadorView.Consultar.class)
	public ResponseEntity<TentativaContatoDoador> obterTentativaDeContatoPorId(
			@PathVariable(name = "id", required = true) Long tentativaContatoId) {
		return ResponseEntity.ok(tentativaContatoService.obterTentativaPorId(tentativaContatoId));
	}

	/**
	 * Atribui a tarefa de tentativa de contato.
	 * 
	 * @param idTentativaContato - identificador de tentativa de contato
	 * @param idTipoTarefa - identificador de tipo de tarefa
	 * @return taerfa serializada
	 */
	@PutMapping(value = "{id}/tarefa/atribuir")
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPrmission('CONTACTAR_FASE_3')")
	public MappingJacksonValue atribuirTarefaPorPerfil(
			@PathVariable(name = "id", required = true) Long idTentativaContato,
			@RequestParam(name = "idTipoTarefa", required = true) Long idTipoTarefa) {

		return tentativaContatoService.atribuirTarefa(idTentativaContato, idTipoTarefa);
	}

	/**
	 * Finaliza a tentativa de contato e cria uma nova tentativa para o próximo período.
	 * 
	 * @param id - identificador da tentativa de contato
	 * @param idContatoTelefone - identificador do contato telefonico do doador quando houver agendamento.
	 * @param dataAgendamento - data do agendamento
	 * @param horaInicio - hora de inicio 
	 * @param horaFim - hora final.
	 * @return mensagem de sucesso
	 */
	@PutMapping(value = "{id}")
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<CampoMensagem> finalizarTentativaContatoCriarProximaTentativa(
			@PathVariable(name = "id", required = true) Long id,
			@RequestParam(name="idContatoTelefone", required = false) Long idContatoTelefone,
			@RequestParam(name="dataAgendamento", required = false) LocalDate dataAgendamento,
			@RequestParam(name="horaInicio", required = false) LocalTime horaInicio,
			@RequestParam(name="horaFim", required = false) LocalTime horaFim,
			@RequestParam(name="atribuirUsuario", required = false) String atribuirUsuario) {

		tentativaContatoService.finalizarTentativaContatoECriarNovaTentativa(id, idContatoTelefone, dataAgendamento, horaInicio, horaFim, atribuirUsuario);

		return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "tentativacontatodoador.proximo.turno")));
	}
	
	@GetMapping(value = "{id}/podefinalizar")
	//@PreAuthorize("hasScope('FINALIZAR_TENTATIVA_PEDIDO_CONTATO')")
	public ResponseEntity<Boolean> podeFinalizarTentativaContato(@PathVariable(name = "id", required = true) Long id) {		
		return ResponseEntity.ok().body(tentativaContatoService.podeFinalizarTentativaContato(id));		
	}
	
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "{id}/finalizar")
	//@PreAuthorize("hasScope('FINALIZAR_TENTATIVA_PEDIDO_CONTATO')")
	public ResponseEntity finalizarTentativaContato(@PathVariable(name = "id", required = true) Long id) {
		tentativaContatoService.finalizarTentaticaContato(id);
		return ResponseEntity.ok().build();		
	}

	

}
