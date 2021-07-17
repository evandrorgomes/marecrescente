
package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.MotivoDescarte;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IMotivoDescarteService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe responsável por controlar as requisições do front-end envolvendo a entidade Exame e relacionadas.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/exames", produces = "application/json;charset=UTF-8")
public class ExameController {

	@Autowired
	private IMotivoDescarteService motivoDescarteService;

	@Autowired
	private IExamePacienteService examePacienteService;
	
	@Autowired
	private IExameDoadorService<ExameDoadorInternacional> exameDoadorInternacionalService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método utilizado para validar um ou mais HLAs sem antígeno.
	 * 
	 * @Return ResponseEntity<List<CampoMensagem>> lista de mensagens do retorno da validação. Lista virá vazia se todos os itens
	 * são válidos.
	 */
	@RequestMapping(value = "hla", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> validarHla(
		@RequestParam(	name = "codigo", required = true) String codigo,
		@RequestParam(name = "valor", required = true) String valor) {
		List<CampoMensagem> listaMensagens = examePacienteService.validarHla(codigo, valor);
		if(!listaMensagens.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Método para validar HLAs com antígeno.
	 * @param alelo a ser verficado.
	 * @param valor hla a ser verificado.
	 * @return campos de mensagem de erro caso serja inválido.
	 */
	@RequestMapping(value = "hla/comantigeno", method = RequestMethod.GET)
	public ResponseEntity<String> validarHlaComAntigeno(
			@RequestParam(	name = "codigo", required = true) String codigo,
			@RequestParam(name = "valor", required = true) String valor) {
		List<CampoMensagem> listaMensagens = examePacienteService.validarHlaComAntigeno(codigo, valor);
		if(!listaMensagens.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Método para buscar um exame.
	 * 
	 * @param id
	 * @return ResponseEntity<Exame>
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'ExamePaciente', 'CONSULTAR_EXAMES_PACIENTE')"
			+ " || hasPermission(#id, 'ExamePaciente', 'CADASTRAR_EXAMES_PACIENTE')"
			+ "|| hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')"
			+ "|| hasPermission('CONFERIR_EXAME_HLA') ")
	@JsonView(ExameView.ListaExame.class)
	public ResponseEntity<Exame> obterExame(@PathVariable(name = "id", required = true) Long id) {
		Exame exame = examePacienteService.obterExame(id);
		return new ResponseEntity<Exame>(exame, HttpStatus.OK);
	}

	
	
	/**
	 * Método para buscar um exame de Doador.
	 * 
	 * @param id
	 * @return ResponseEntity<Exame>
	 */
	@RequestMapping(value = "doador/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
	@JsonView(ExameView.ListaExame.class)
	public ResponseEntity<Exame> obterExameDoador(@PathVariable(name = "id", required = true) Long id) {
		Exame exame = exameDoadorInternacionalService.obterExame(id);
		return new ResponseEntity<Exame>(exame, HttpStatus.OK);
	}

	/**
	 * Método para baixar um zip com todos os arquivos de exame.
	 * 
	 * @param id do exame
	 * @param response HttpServletResponse
	 * @throws IOException se ocorrer algum problema ao trabalhar com o arquivo.
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'ExamePaciente', 'CONSULTAR_EXAMES_PACIENTE')"
			+ " || hasPermission(#id, 'ExamePaciente', 'CADASTRAR_EXAMES_PACIENTE')")
	public void obterArquivosExameZipado(@PathVariable(name = "id", required = true) Long id,
			HttpServletResponse response) throws IOException {
		// FIXME mover está lógica para a camada de negócio.
		File file = examePacienteService.obterZipArquivosLaudo(id);
		response.setContentType("application/zip; application/octet-stream");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file
				.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		response.flushBuffer();
	}

	/**
	 * Método para salvar novo exame.
	 * 
	 * @param listaArquivosLaudo - lista com os arquivos
	 * @param exame - Exame
	 * @return CampoMensagem
	 * @throws Exception 
	 */
	@RequestMapping(value = "salvar", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#exame.paciente.rmr, 'Paciente',  'CADASTRAR_EXAMES_PACIENTE')")
	public ResponseEntity<List<CampoMensagem>> salvar(
			@RequestPart(name = "file") List<MultipartFile> listaArquivosLaudo,
			@RequestPart(required = true) ExamePaciente exame) throws Exception {

		examePacienteService.salvarParaAvaliacao(listaArquivosLaudo, exame);
		List<CampoMensagem> listaMensagens = new ArrayList<CampoMensagem>();
		listaMensagens.add(new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"exame.incluido.sucesso")));
		listaMensagens.add(new CampoMensagem("id", exame.getId().toString()));

		return new ResponseEntity<List<CampoMensagem>>(listaMensagens, HttpStatus.OK);
	}

	/**
	 * Método rest para gravar novo exame respondendo uma ou mais pendências.
	 *
	 * @param listaArquivosLaudo lista com os arquivos
	 * @param exame a ser persistido.
	 * @param pendencias a serem respondindas
	 * @param resposta texto a ser colocado na resposta da pendencia
	 * @param respondePendencia flag que indica se as pendências seram marcadas como respondidas
	 * @return ResponseEntity<String> mensagem de sucesso
	 * @throws Exception 
	 */
	@RequestMapping(value = "/respostas", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#exame.paciente.rmr, 'Paciente', 'CADASTRAR_EXAMES_PACIENTE')")
	public ResponseEntity<String> salvar(@RequestPart(name = "file") List<MultipartFile> listaArquivosLaudo,
			@RequestPart ExamePaciente exame, @RequestPart List<Pendencia> pendencias,
			@RequestPart String resposta, @RequestPart Boolean respondePendencia) throws Exception {
		examePacienteService.salvar(listaArquivosLaudo, exame, pendencias, resposta, respondePendencia);
		final String mensagem = AppUtil.getMensagem(messageSource, "exame.incluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
	}

	
	/**
	 * Lista tarefas paginada de exames para conferência de avaliador.
	 * @param rmr do paciente.
	 * @param nomePaciente a ser consultado.
	 * @param pagina - página a ser listada.
	 * @param quantidadeRegistros - quantidade de registros da página.
	 * @return lista paginada.
	 */
	@RequestMapping(value = "/pendentes/tarefas", method = RequestMethod.GET)
	public JsonViewPage<TarefaDTO> listaTarefasDeExamesParaConferencia(
			@RequestParam(required = false) Long rmr,
			@RequestParam(required = false) String nomePaciente,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		JsonViewPage<TarefaDTO> tarefasExameConferencia = 
				examePacienteService.listaTarefasDeExamesParaConferencia(rmr, nomePaciente, new PageRequest(pagina, quantidadeRegistros));
		return tarefasExameConferencia;
	}
	
	
	/**
	 * Realiza a aceitação (conferido e está OK!) e atualização do exame, caso tenha sido alterado.
	 * 
	 * @param exame exame que deverá ser atualizado.
	 * @return ResponseEntity<String> mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}/aceito", method = RequestMethod.PUT, consumes = "application/json;charset=UTF-8",
			produces = "text/plain;charset=UTF-8")
	@PreAuthorize("hasPermission('CONFERIR_EXAME_HLA')")
	public ResponseEntity<String> aceitar(@RequestBody(required = true) ExamePaciente exame) {
		examePacienteService.aceitar(exame);
		return new ResponseEntity<String>(AppUtil.getMensagem(messageSource, "exame.atualizado.sucesso"), HttpStatus.OK);
	}

	/**
	 * Realiza o descarte do exame (conferido não está OK!).
	 * 
	 * @param exameId ID do exame a ser descartado.
	 * @param motivoDescarteId ID do motivo pelo qual ocorreu o descarte.
	 * @return mensagem de procedimento realizado com sucesso.
	 */
	@RequestMapping(value = "{id}/descartar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('CONFERIR_EXAME_HLA')")
	public ResponseEntity<String> descartar(
			@PathVariable(name = "id", required = true) Long exameId,
			@RequestBody(required = true) Long motivoDescarteId) {
		examePacienteService.descartar(exameId, motivoDescarteId);
		return new ResponseEntity<String>(AppUtil.getMensagem(messageSource, "exame.atualizado.sucesso"), HttpStatus.OK);
	}

	/**
	 * Realiza a aceitação (conferido e está OK!) e atualização do exame, caso tenha sido alterado.
	 * 
	 * @param exame exame que deverá ser atualizado.
	 * @return ResponseEntity<String> mensagem de sucesso.
	 */
	@RequestMapping(value = "/motivosdescarte", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONFERIR_EXAME_HLA')")
	public ResponseEntity<List<MotivoDescarte>> obterMotivosDescarte() {
		List<MotivoDescarte> motivosDescarte = motivoDescarteService.obterMotivosDescarte();
		return new ResponseEntity<List<MotivoDescarte>>(motivosDescarte, HttpStatus.OK);
	}
	
	
	/**
	 * Baixar relatório por codigo.
	 * 
	 * @param idExame identificador do exame
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/classe2/c/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('BAIXAR_SOLICITACAO_EXAME_CLASSE2')")
	public void baixarArquivoExameC(
			@PathVariable(name = "id", required = true) Long idExame,
			HttpServletResponse response) throws IOException {
		
		File arquivo = examePacienteService.obterPedidoExame(idExame, Relatorios.PEDIDO_EXAME_C.getCodigo());
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	} 
	
	/**
	 * Baixar relatório por codigo.
	 * 
	 * @param idExame identificador do exame
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/classe2/dqb1/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('BAIXAR_SOLICITACAO_EXAME_CLASSE2')")
	public void baixarArquivoExameDRB1DQB1(
			@PathVariable(name = "id", required = true) Long idExame,
			HttpServletResponse response) throws IOException {
		
		File arquivo = examePacienteService.obterPedidoExame(idExame, Relatorios.PEDIDO_EXAME_DQB1_DRB1.getCodigo());
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
	
	/**
	 * Notifica usuários sobre resultado de exame de doador internacional.
	 * @param id da tarefa followup de notificação
	 * @return status http
	 */
	@PostMapping("notificarresultadoexameinternacional")
	public ResponseEntity<String> notificarUsuariosSobreResultadoDeExameDeDoadorInternacional(@RequestParam(name = "tarefa") Long idTarefa) {
		exameDoadorInternacionalService.notificarUsuariosSobreResultadoDeExameDeDoadorInternacional(idTarefa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Notifica usuários sobre resultado de exame de ct de doador internacional sem cadastro há 15 dias.
	 * @param id da tarefa followup de notificação
	 * @return status http
	 */
	@PostMapping("notificarctinternacional15dias")
	public ResponseEntity<String> notificacarCadastroResultadoExameCtInternacional15(@RequestParam(name = "tarefa") Long idTarefa) {
		exameDoadorInternacionalService.notificacarCadastroResultadoExameCtInternacional15(idTarefa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * Notifica usuários sobre resultado de exame de ct de doador internacional sem cadastro há 7 dias.
	 * @param id da tarefa followup de notificação
	 * @return status http
	 */
	@PostMapping("notificarctinternacional7dias")
	public ResponseEntity<String> notificacarCadastroResultadoExameCtInternacional7(@RequestParam(name = "tarefa") Long idTarefa) {
		exameDoadorInternacionalService.notificacarCadastroResultadoExameCtInternacional7(idTarefa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("notificaridminternacional15dias")
	public ResponseEntity<String> notificacarCadastroResultadoExameIdmInternacional15(@RequestParam(name = "tarefa") Long idTarefa) {
		exameDoadorInternacionalService.notificacarCadastroResultadoExameIdmInternacional15(idTarefa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("notificaridminternacional7dias")
	public ResponseEntity<String> notificacarCadastroResultadoExameIdmInternacional7(@RequestParam(name = "tarefa") Long idTarefa) {
		exameDoadorInternacionalService.notificacarCadastroResultadoExameIdmInternacional7(idTarefa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
