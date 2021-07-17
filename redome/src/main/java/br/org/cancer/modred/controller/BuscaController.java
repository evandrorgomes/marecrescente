package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.BuscaDto;
import br.org.cancer.modred.controller.dto.BuscaPaginacaoDTO;
import br.org.cancer.modred.controller.dto.UltimoPedidoExameDTO;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.model.ArquivoRelatorioInternacional;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CancelamentoBusca;
import br.org.cancer.modred.model.MotivoCancelamentoBusca;
import br.org.cancer.modred.model.TipoBuscaChecklist;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IArquivoRelatorioInternacionalService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.ITipoBuscaChecklistService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para a entidade de busca.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/buscas", produces = "application/json;charset=UTF-8")
public class BuscaController {

	@Autowired
	private IBuscaService buscaService;
	
	@Autowired
	private IPedidoExameService pedidoExameService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ITipoBuscaChecklistService tipoBuscaChecklistService;
	
	@Autowired
	private IArquivoRelatorioInternacionalService arquivoRelatorioInternacionalService;
	

	/**
	 * Serviço para cancelar uma busca.
	 * 
	 * @param rmr
	 *            - id do paciente
	 * @param cancelamentoBusca - entidade de cancelamento da busca
	 * @return String - resposta
	 */
	@RequestMapping(value = "{rmr}/cancelar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'CANCELAR_BUSCA')")
	public ResponseEntity<CampoMensagem> cancelarBusca(@PathVariable(name = "rmr") Long rmr,
			@RequestBody(required = true) CancelamentoBusca cancelamentoBusca) {

		buscaService.cancelarBusca(rmr, cancelamentoBusca);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, "busca.cancelada.sucesso")), HttpStatus.OK);
	}

	/**
	 * Serviço para cancelar uma busca.
	 * 
	 * @param rmr
	 *            - id do paciente
	 * @return String - resposta
	 */
	@RequestMapping(value = "motivoscancelamento", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('VISUALIZAR_MOTIVOS_CANCELAMENTO_BUSCA')")
	public ResponseEntity<List<MotivoCancelamentoBusca>> obterMotivos() {
		return new ResponseEntity<List<MotivoCancelamentoBusca>>(buscaService.listarMotivosCancelamento(),
				HttpStatus.OK);
	}

	/**
	 * Lista as buscas com ordenação pré-definida e 
	 * utilizando ou não os filtros informados.
	 * 
	 * @param pagina página a ser exibida na paginação.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @param loginAnalistaBusca login do analista de busca (default: analista logado).
	 * @param idTipoBuscaCheckList ID do tipo de busca checklist (opcional).
	 * @param rmr RMR do paciente (opcional).
	 * @param nome nome do paciente (opcional, pesquisa por parte do nome).
	 * @return lista de buscas paginada.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.PACIENTES_PARA_PROCESSO_BUSCA + "')")	
	public ResponseEntity<Page<BuscaPaginacaoDTO>> listarBuscas(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros,
			@RequestParam(required = true) String loginAnalistaBusca,
			@RequestParam(required = false) Long idTipoBuscaCheckList,
			@RequestParam(required = false) Long rmr,
			@RequestParam(required = false) String nome) {
		return new ResponseEntity<Page<BuscaPaginacaoDTO>>(
				buscaService.listarBuscas(loginAnalistaBusca, idTipoBuscaCheckList, rmr, nome, 
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}

	/**
	 * Serviço para localizar busca por id.
	 * 
	 * @param rmr - rmr do paciente da busca
	 * @return busca localizada por id
	 */
	@RequestMapping(value = "{rmr}/obter", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')||hasPermission('PEDIDO_EXAME_FASE3_PACIENTE')")
	@JsonView(BuscaView.Busca.class)
	public ResponseEntity<Busca> obterBusca(@PathVariable(name = "rmr") Long rmr) {
		Busca busca = buscaService.obterBuscaAtivaPorRmr(rmr);
		return new ResponseEntity<Busca>(busca, HttpStatus.OK);
	}
	
	/**
	 * Serviço para atribuir uma busca para um analista de busca.
	 * 
	 * @param id
	 *            - id da busca
	 * @return String - resposta
	 */
	@RequestMapping(value = "{id}/atribuir", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<String> atribuirBusca(@PathVariable(name = "id") Long idBusca) {
		buscaService.atribuirBuscaETarefaParaAnalistaRedome(idBusca);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Obtém o último pedido de exame associado a busca informada.
	 * 
	 * @param buscaId ID do busca.
	 * @return
	 */
	@RequestMapping(value = "{id}/ultimopedidoexame", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('PEDIDO_EXAME_FASE3_PACIENTE')  || hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')")
	@JsonView(BuscaView.UltimoPedidoExame.class)
	public ResponseEntity<UltimoPedidoExameDTO> obterUltimoPedidoExame(@PathVariable(name = "id") Long buscaId) {
		return new ResponseEntity<UltimoPedidoExameDTO>(pedidoExameService.obterUltimoPedidoExamePelaBusca(buscaId), HttpStatus.OK);
	}
	
	/**
	 * Lista os tipos de checklist de busca cadastrados no Redome.
	 * 
	 * @return lista os tipos.
	 */
	@RequestMapping(value = "tiposBuscaChecklist", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.PACIENTES_PARA_PROCESSO_BUSCA + "')")
	public ResponseEntity<List<TipoBuscaChecklist>> listarTipoBuscaChecklist() {
		return new ResponseEntity<List<TipoBuscaChecklist>>(tipoBuscaChecklistService.findAll(), HttpStatus.OK);
	}
		
	/**
	 * Lista os arquivos da busca internacional.
	 * 
	 * @return lista os arquivos
	 */
	@RequestMapping(value = "{id}/relatoriosinternacionais", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL + "')")
	@JsonView(BuscaView.ListarArquivosRelatorioBuscaInternacional.class)
	public ResponseEntity<List<ArquivoRelatorioInternacional>> listarArquivosRelatorioBuscaInternacional(
			@PathVariable(name = "id") Long id) {		
		return new ResponseEntity<List<ArquivoRelatorioInternacional>>(arquivoRelatorioInternacionalService.listarArquivoPorBusca(id), HttpStatus.OK);
	}

	/**
	 * Baixar relatório zipado por busca.
	 * 
	 * @param idBusca identificador da busca
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/downloadinternacionalzip", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL + "')")
	public void baixarArquivoZipado(
			@PathVariable(name = "id", required = true) Long idBusca,
			HttpServletResponse response) throws IOException {
		
		File arquivo = arquivoRelatorioInternacionalService.obterTodosArquivosZipadosPorBusca(idBusca);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
	
	/**
	 * Salva busca passada por parametro.
	 * @param busca a ser salva.
	 * @return HttpStatus.OK quando salvo.
	 */
	@PutMapping("{rmr}/alterarstatusparaliberado")
	public ResponseEntity<String> alterarStatusParaLiberado(@PathVariable(value = "rmr") Long rmr) {
		this.buscaService.alterarStatusDeBuscaParaLiberado(rmr);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Desatribui a busca do analista de busca.
	 * @param rmr do paciente da busca.
	 * @return HttpStatus.OK quando salvo.
	 */
	@PutMapping("{rmr}/removeratribuicaobusca")
	public ResponseEntity<String> removerAtribuicaoDeBusca(@PathVariable(value = "rmr") Long rmr) {
		this.buscaService.desatribuirBuscaDeAnalista(rmr);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Serviço para localizar busca por rmr.
	 * 
	 * @param rmr - rmr do paciente da busca
	 * @return buscaDto localizada por rmr
	 */
	@RequestMapping(value = "{rmr}/obtersimplificado", method = RequestMethod.GET)
	@PreAuthorize("#oauth2.hasScope('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<BuscaDto> obterBuscaDto(@PathVariable(name = "rmr") Long rmr) {
		BuscaDto busca = new BuscaDto().parse(buscaService.obterBuscaAtivaPorRmr(rmr));
		return new ResponseEntity<BuscaDto>(busca, HttpStatus.OK);
	}

	/**
	 * Serviço para localizar busca por id.
	 * 
	 * @param id - id da busca
	 * @return buscaDto localizada por id
	 */
	@GetMapping(value = "{id}")
	@PreAuthorize("#oauth2.hasScope('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<BuscaDto> obterBuscaDtoPorId(@PathVariable(name = "id") Long id) {
		BuscaDto busca = new BuscaDto().parse(buscaService.obterBuscaAtivaPorId(id));
		return new ResponseEntity<BuscaDto>(busca, HttpStatus.OK);
	}

}