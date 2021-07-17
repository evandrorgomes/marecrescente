package br.org.cancer.modred.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ResultadoWorkup;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.impl.ArquivoResultadoWorkupService;
import br.org.cancer.modred.service.impl.ResultadoWorkupService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para resultado de workup.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/resultadosworkup", produces = "application/json;charset=UTF-8")
public class ResultadoWorkupController {

	@Autowired
	private ResultadoWorkupService resultadoWorkupService;

	@Autowired
	private ArquivoResultadoWorkupService arquivoResultadoWorkupService;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Obtém um doador por com resultado de workup aberto.
	 * 
	 * @param idDoador - identificação do doador.
	 * @param aberto - situação do resultado do pedido de workup.
	 * @return response contendo ResultadoWorkupDTO.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONSULTAR_RESULTADO_WORKUP')")
	@JsonView(WorkupView.Resultado.class)
	public ResponseEntity<ResultadoWorkup> obterDoadorComResultado(
			@RequestParam(name = "dmr", required = true) Long dmr
			,@RequestParam(name = "centro", required = true) Long centro
			,@RequestParam(name = "aberto", required = true) Boolean aberto) {
		return new ResponseEntity<ResultadoWorkup>(resultadoWorkupService.obterDoadorComResultado(dmr, centro, aberto), HttpStatus.OK);
	}

	/**
	 * Método para salvar Resultado de Exame workup.
	 * 
	 * @param resultado - referencia do objeto de resultado de exame de workup.
	 * @return CampoMensagem
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#resultado.id, 'ResultadoWorkupNacionalDTO', 'CADASTRAR_RESULTADO_WORKUP') ||"
			+ " hasPermission('CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<List<CampoMensagem>> salvarResultadoExameWorkup(
			@RequestBody(required = true) ResultadoWorkup resultado) {

		resultadoWorkupService.finalizarResultadoWorkup(resultado);
		List<CampoMensagem> listaMensagens = new ArrayList<CampoMensagem>();
		listaMensagens.add(new CampoMensagem("", AppUtil.getMensagem(messageSource, "resuladoExameWorkup.incluido.sucesso")));
		return new ResponseEntity<List<CampoMensagem>>(listaMensagens, HttpStatus.OK);
	}

	/**
	 * Método para salvar arquivo de resultado de exame de workup.
	 * 
	 * @param arquivoLaudo - arquivo fisico a ser enviado para o servidor.
	 * @param resultado - referencia do objeto de resultado de workup a ser gravado na base.
	 * @return CampoMensagem
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value = "arquivo", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#resultado.id, 'ResultadoWorkupNacionalDTO', 'CADASTRAR_RESULTADO_WORKUP') "
			+ " || hasPermission('CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<RetornoInclusaoDTO> salvarArquivoResultadoWorkup(
			@RequestPart(name = "file") MultipartFile arquivoLaudo,
			@RequestPart(required = true) ResultadoWorkup resultado) throws Exception {

		String caminho = arquivoResultadoWorkupService.salvarArquivoWorkup(resultado, arquivoLaudo);
		return new ResponseEntity<RetornoInclusaoDTO>(new RetornoInclusaoDTO(null, AppUtil.getMensagem(messageSource,
				"arquivoRultadoWorkup.incluido.sucesso"), caminho), HttpStatus.OK);
	}

	/**
	 * Método para excluir arquivo de resultado de exame de workup.
	 * 
	 * @param resultado - resultado de workup com id
	 * @param caminho - caminho do arquivo a ser excluido.
	 * @return CampoMensagem
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value = "arquivo/excluir", method = RequestMethod.POST,
			consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#resultado.id, 'ResultadoWorkupNacionalDTO', 'CADASTRAR_RESULTADO_WORKUP_NACIONAL') "
			+ " || hasPermission('" + Recurso.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<RetornoInclusaoDTO> excluirArquivoResultadoWorkup(
			@RequestPart(required = true) ResultadoWorkup resultado,
			@RequestPart(required = true) String caminho) throws Exception {

		arquivoResultadoWorkupService.excluirArquivoWorkup(caminho);
		return new ResponseEntity<RetornoInclusaoDTO>(new RetornoInclusaoDTO(null, AppUtil.getMensagem(messageSource,
				"arquivoRultadoWorkup.excluido.sucesso"), caminho), HttpStatus.OK);
	}
	

	/**
	 * Obtém um resultado de workup aberto.
	 * 
	 * @param id - identificação do doador.
	 * @return response contendo ResultadoWorkup.
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_RESULTADO_WORKUP_NACIONAL + "') "
			+ " || hasPermission('" + Recurso.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	@JsonView(WorkupView.Resultado.class)
	public ResponseEntity<ResultadoWorkup> obterResultadoWorkup(
			@PathVariable(name = "id", required = true) Long id
			) {
		return new ResponseEntity<ResultadoWorkup>(resultadoWorkupService.findById(id), HttpStatus.OK);
	}
	

	/**
	 * Desatribui a tarefa de resultado de workup.
	 * 
	 * @param dmr - identificação do doador.
	 * @param centro - identificação do centro.
	 * @param aberto - situação do resultado do pedido de workup.
	 * @return response mensagem.
	 */
	@RequestMapping(value = "tarefa", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_RESULTADO_WORKUP_NACIONAL + "')")
	@JsonView(TarefaView.Listar.class)
	public ResponseEntity<TarefaDTO> obterTarefaResultadoWorkupNacional(@RequestParam(name = "dmr", required = true) Long dmr
			,@RequestParam(name = "centro", required = true) Long centro
			,@RequestParam(name = "aberto", required = true) Boolean aberto) {
		

		return new ResponseEntity<TarefaDTO>(resultadoWorkupService.obterTarefaResultadoWorkupNacional(dmr, centro, aberto),
				HttpStatus.OK);
	}
	
	
	/**
	 * Lista as tarefas CADASTRAR_RESULTADO_WORKUP para um determinado centro de coleta.
	 * 
	 * @param idCentroTransplante - Identificador do centro de coleta
	 * @param pagina - Página desejada 
	 * @param quantidadeRegistro - Quantidade de registros por página
	 * @return lista de tarefas.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "tarefas")
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_RESULTADO_WORKUP_NACIONAL + "')")
	public JsonViewPage<TarefaDTO> listarTarefas(@RequestParam(required = false) Long idCentroColeta,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistro) {
		
		return resultadoWorkupService.listarTarefasPorCentroTransplante(idCentroColeta, 
				new PageRequest(pagina, quantidadeRegistro));
	}

	
	/**
	 * Lista as tarefas CADASTRAR_RESULTADO_WORKUP.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "tarefas/cadastroResultado", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_RESULTADO_WORKUP_NACIONAL + "')")
	public ResponseEntity<JsonViewPage<TarefaDTO>> listarTarefasCadastroResultado(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return new ResponseEntity<JsonViewPage<TarefaDTO>>(
				resultadoWorkupService.listarTarefasCadastroResultado(
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}

}
