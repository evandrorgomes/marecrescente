package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.AvaliacaoCamaraTecnicaDTO;
import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.service.IAvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.util.AppUtil;


/**
 * Controlador para avaliacao de camara técnica.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacaocamaratecnica")
public class AvaliacaoCamaraTecnicaController {

	@Autowired
	private IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService;
	
	@Autowired
	private MessageSource messageSource; 
	
	/**
	 * Lista todas as tarefas de avaliação de câmara técnica.
	 * 
	 * @param pagina qual a página a ser exibida na paginação.
	 * @param quantidadeRegistros a quantidade de registros que serão exibidos por página.
	 * 
	 * @return lista de tarefas de avaliação de câmara técnica.
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "tarefas", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('AVALIAR_PACIENTE_CAMARA_TECNICA') ")
	@JsonView(AvaliacaoCamaraTecnicaView.ListarTarefas.class)
	public ResponseEntity<Page<TarefaDTO>> listarTarefasAvaliacaoCamaraTecnica(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		
		return new ResponseEntity<Page<TarefaDTO>>(
				avaliacaoCamaraTecnicaService.listarTarefasAvaliacaoCamaraTecnica(Boolean.FALSE,
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Método para aprovar avaliação de câmara avaliadora.
	 * 
	 * @param avaliacao avaliacao a ser aprovada.
	 * @param arquivo arquivo de relatório.
	 * @return mensagem de sucesso caso tenha havido sucesso.
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value = "aprovar", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('AVALIAR_PACIENTE_CAMARA_TECNICA') ")
	public ResponseEntity<String> aprovarAvaliacao(
			@RequestPart(name = "file" , required = false) MultipartFile arquivo,
			@RequestPart(required = true) AvaliacaoCamaraTecnica avaliacao) throws Exception {
		avaliacaoCamaraTecnicaService.aprovarAvaliacao(avaliacao, arquivo);
		return new ResponseEntity<String>(AppUtil.getMensagem(messageSource,"avaliacao.camara.tecnica.aprovada.sucesso"), HttpStatus.OK);
	}

	
	/**
	 * Método para reprovar avaliação de câmara avaliadora.
	 * 
	 * @param avaliacao avaliacao a ser aprovada.
	 * @param arquivo arquivo de relatório.
	 * @return mensagem de sucesso caso tenha havido sucesso.
	 * @throws Exception Se ocorrer algum erro 
	 */
	@RequestMapping(value = "reprovar", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('AVALIAR_PACIENTE_CAMARA_TECNICA') ")
	public ResponseEntity<String> reprovarAvaliacao(
			@RequestPart(name = "file" , required = false) MultipartFile arquivo,
			@RequestPart(required = true) AvaliacaoCamaraTecnica avaliacao) throws Exception {
		avaliacaoCamaraTecnicaService.reprovarAvaliacao(avaliacao, arquivo);
		return new ResponseEntity<String>(AppUtil.getMensagem(messageSource,"avaliacao.camara.tecnica.reprovada.sucesso"), HttpStatus.OK);
	}
	 
	/**
	 * Método para atualizar status de avaliação.
	 * 
	 * @param avaliacao avaliacao com o status a ser atualizado.
	 * @return mensagem de sucesso caso tenha havido sucesso.
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value = "atualizarstatus", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('AVALIAR_PACIENTE_CAMARA_TECNICA') ")
	public ResponseEntity<String> atualizarStatus(@RequestBody(required = true) AvaliacaoCamaraTecnica avaliacao) throws Exception {
		avaliacaoCamaraTecnicaService.alterarStatus(avaliacao);
		return new ResponseEntity<String>(AppUtil.getMensagem(messageSource,"avaliacao.camara.tecnica.status.sucesso"), HttpStatus.OK);
	}

	
	/**
	 * Método para obter avaliação.
	 * 
	 * @param id da avaliação a ser carregada.
	 * @return avaliacao localizada.
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value="{id}", method =  RequestMethod.GET)
	@PreAuthorize("hasPermission('AVALIAR_PACIENTE_CAMARA_TECNICA') ")
	@JsonView(AvaliacaoCamaraTecnicaView.Detalhe.class)
	public ResponseEntity<AvaliacaoCamaraTecnicaDTO> obterAvaliacao(
			@PathVariable(name = "id", required = true) Long id) throws Exception {
		return new ResponseEntity<AvaliacaoCamaraTecnicaDTO>(avaliacaoCamaraTecnicaService.obterAvaliacaoCamaraTecnicaDTO(id), HttpStatus.OK);
	}

	
}
