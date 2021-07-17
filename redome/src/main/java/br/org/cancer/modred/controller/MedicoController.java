package br.org.cancer.modred.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import br.org.cancer.modred.controller.abstracts.AbstractController;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.PreCadastroMedicoView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonicoMedico;
import br.org.cancer.modred.model.EmailContatoMedico;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.StatusPreCadastro;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IPreCadastroMedicoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de REST controller para a entidade médico.
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class MedicoController extends AbstractController{

	@Autowired
	private IPreCadastroMedicoService preCadastroMedicoService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método para validar identificação do pré-cadastro.
	 * 
	 * @param crm - valor do CRM a ser pesquisado
	 * @param login - valor do Login a ser pesquisado
	 * @return OK - Se não existir CRM ou LOGIN 
	 */
	@RequestMapping(value = "/public/medicos/precadastros/validaridentificacao", method = RequestMethod.GET)
	public ResponseEntity<?> validarIdentificacao(
			@RequestParam(required = false) String crm,
			@RequestParam(required = false) String login) {
		
		if ((crm == null || "".equals(crm)) && (login == null || "".equals(login))) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		
		preCadastroMedicoService.validarIdentificacao(crm, login);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Inclui a solicitação de acesso (pré cadastro) do médico ao sistema Redome.
	 * 
	 * @param preCadastroMedico dados do médico a serem salvos.
	 * @param arquivoCrm arquivo do CRM do médico para ser utilizada na validação.
	 * 
	 * @return mensagem validando a operação.
	 */
	@RequestMapping(value = "/public/medicos/precadastros", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CampoMensagem> incluirPreCadastro(
			@RequestPart(required = true) PreCadastroMedico preCadastroMedico, 
			@RequestPart(name = "file", required = true) MultipartFile arquivoCrm) {
		preCadastroMedicoService.incluir(preCadastroMedico, arquivoCrm);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "pre.cadastro.incluido.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	
	/**
	 * Método para listar pré cadastro de médico.
	 * 
	 * @param status - Filtro obrigatorio por status
	 * @param pagina - Página inicial
	 * @param quantidadeRegistros - quantidade de registros
	 * @return Lista de pré cadastro paginada. 
	 */
	@RequestMapping(value = "/api/medicos/precadastros", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_PRE_CADASTRO_MEDICO + "')")
	@JsonView(PreCadastroMedicoView.Listar.class)
	public ResponseEntity<PageImpl<PreCadastroMedico>> listarPreCadastros(
			@RequestParam(required = true) String status,
			@RequestParam(required = false) Integer pagina,
            @RequestParam(required = false) Integer quantidadeRegistros) {
		
		StatusPreCadastro statusPreCadastro = null;
		if (status == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		else {
			statusPreCadastro = StatusPreCadastro.valueOf(status);
		}
		
		if(pagina == null){
            pagina = new Integer(0);
        }
        
        PageRequest pageRequest = (quantidadeRegistros !=null && quantidadeRegistros.intValue() > 0 )?
                                                                            new PageRequest(pagina, quantidadeRegistros):
                                                                            null;
		
		return new ResponseEntity<PageImpl<PreCadastroMedico>>(preCadastroMedicoService
				.listarPreCadastroMedicoPorStatus(statusPreCadastro, pageRequest), HttpStatus.OK);
	}
	
	/**
	 * Método para obter o pré cadastro médico por id.
	 * 
	 * @param idPreCadastroMedico - Identificador do pré cadastro médico
	 * @return Entidade PreCadastroMedico
	 */
	@RequestMapping(value = "/api/medicos/precadastros/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.VISUALIZAR_DETALHE_PRE_CADASTRO_MEDICO + "')")
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	public ResponseEntity<PreCadastroMedico> obterPreCadastros(
			@PathVariable(required=true, name="id") Long idPreCadastroMedico) {
		
		return new ResponseEntity<PreCadastroMedico>(preCadastroMedicoService
				.obterPreCadastroMedicoPorId(idPreCadastroMedico), HttpStatus.OK);
	}
	
	/**
	 * Método para reprovar o pré cadastro médico por id.
	 * 
	 * @param idPreCadastroMedico - Identificador do pré cadastro médico
	 * @param justificativa - Texto que justifica o motivo da reprovação
	 * @return mensagem de sucesso e status OK
	 */
	@RequestMapping(value = "/api/medicos/precadastros/{id}/reprovar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.REPROVAR_PRE_CADASTRO_MEDICO + "')")	
	public ResponseEntity<CampoMensagem> reprovarPreCadastros(
			@PathVariable(required=true, name="id") Long idPreCadastroMedico,
			@RequestBody(required= true) String justificativa) {
		
		preCadastroMedicoService.reprovarPreCadastroMedico(idPreCadastroMedico, justificativa);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "pre.cadastro.reprovado.sucesso"));		
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	
	/**
	 * Aprova o pré cadastro do médico concedendo acesso ao Redome com as informações
	 * passadas.
	 * 
	 * @param idPreCadastroMedico Identificador do pré cadastro médico
	 * @return mensagem de sucesso e status OK
	 */
	@RequestMapping(value = "/api/medicos/precadastros/{id}/aprovar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.APROVAR_PRE_CADASTRO_MEDICO + "')")	
	public ResponseEntity<CampoMensagem> aprovarPreCadastro(@PathVariable(required = true, name = "id") Long idPreCadastroMedico) {
		preCadastroMedicoService.aprovar(idPreCadastroMedico);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "pre.cadastro.aprovado.sucesso"));		
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método para listar médicos cadastrados.
	 * 
	 * @param crm - string crm do médico. 
	 * @param nome - string nome do médico.
	 * @param pagina - Página inicial
	 * @param quantidadeRegistros - quantidade de registros
	 * @return Lista de médicos cadastros paginada. 
	 */
	@RequestMapping(value = "/api/medicos", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_CADASTRO_MEDICO + "')")
	@JsonView(MedicoView.Listar.class)
	public ResponseEntity<PageImpl<Medico>> listarMedicos(
			@RequestParam(required = false) String crm,
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer pagina,
            @RequestParam(required = false) Integer quantidadeRegistros) {
			
		if(pagina == null){
            pagina = new Integer(0);
        }
        
        PageRequest pageRequest = (quantidadeRegistros !=null && quantidadeRegistros.intValue() > 0 )?
                                    new PageRequest(pagina, quantidadeRegistros): null;
		
		return new ResponseEntity<PageImpl<Medico>>(medicoService.listarMedicos(crm, nome, pageRequest), HttpStatus.OK);
	}
	
	/**
	 * Realiza o download do arquivo CRM contido no pré cadastro.
	 * 
	 * @param idPreCadastroMedico ID do pré cadastro.
	 * @param response response que deverá escrever os dados para o front.
	 * @throws IOException exceção lançada, caso não consiga acessar o arquivo.
	 */
	@RequestMapping(value = "/api/medicos/precadastros/{id}/download", method = RequestMethod.GET)
	public void baixarArquivoCRM(
			@PathVariable(name = "id", required = true) Long idPreCadastroMedico, HttpServletResponse response) throws IOException {
		File arquivo = preCadastroMedicoService.obterArquivoCRM(idPreCadastroMedico);
		serializarArquivo(response, arquivo);
	}

	/**
	 * Método para obter o detalhe de um médico cadastrado.
	 * 
	 * @param idMedico - Identificador do médico
	 * 
	 * @return Dados do Médico 
	 */
	@RequestMapping(value = "/api/medicos/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.VISUALIZAR_CADASTRO_MEDICO + "') "
			+ " || hasPermission('" + Recurso.DISTRIBUIR_WORKUP + "') "
			+ " || hasPermission('" + Recurso.DISTRIBUIR_WORKUP_INTERNACIONAL + "') "
			+ " || hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA + "')"
			+ "	|| hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP + "')"
			+ " || hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP_INTERNACIONAL + "')")
	@JsonView(MedicoView.Detalhe.class)
	public ResponseEntity<Medico> obterMedico(			
			@PathVariable(required=true, name="id") Long idMedico) {
			
		return new ResponseEntity<Medico>(medicoService.obterMedico(idMedico), HttpStatus.OK);
	}
	
	/**
	 * Método para alterar os dados de identificação de um médico cadastrado.
	 * 
	 * @param idMedico - Identificador do médico
	 * @param medico medico
	 * 
	 * @return Mensagem de sucesso e Htttp.status.OK 
	 */
	@RequestMapping(value = "/api/medicos/{id}/dadosidentificacao", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.ALTERAR_CADASTRO_MEDICO + "')")	
	public ResponseEntity<CampoMensagem> alterarDadosIdentificacaoDoMedico(			
			@PathVariable(required=true, name="id") Long idMedico,
			@RequestBody(required = true) Medico medico) {
		
		medicoService.salvarDadosIdentificacao(idMedico, medico);

		final CampoMensagem mensagem = new CampoMensagem("", 
                AppUtil.getMensagem(messageSource, "medico.dadosidentificacao.atualizado.sucesso"));
			
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Realiza o download do arquivo CRM contido no pré cadastro.
	 * 
	 * @param idPreCadastroMedico ID do pré cadastro.
	 * @param response response que deverá escrever os dados para o front.
	 * @throws IOException exceção lançada, caso não consiga acessar o arquivo.
	 */
	@RequestMapping(value = "/api/medicos/{id}/download", method = RequestMethod.GET)
	public void baixarArquivoMedicoCRM(
			@PathVariable(name = "id", required = true) Long idMedico, HttpServletResponse response) throws IOException {
		File arquivo = medicoService.obterArquivoCRM(idMedico);
		serializarArquivo(response, arquivo);
	}
	
	/**
	 * Cadastra um novo e-mail para o médico.
	 * 
	 * @param idMedico ID do médico.
	 * @param novoEmail entidade contendo as informações do novo e-mail.
	 * @return OK indicando que a operação foi realizada.
	 */
	@RequestMapping(value = "/api/medicos/{id}/email", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> adicionarNovoEmail(
			@PathVariable(name = "id", required = true) Long idMedico,
			@RequestBody EmailContatoMedico novoEmail) {
		
		medicoService.adicionarEmail(idMedico, novoEmail);
		return statusOK("medico.novoemail.cadastrado.sucesso");
	}
	
	/**
	 * Cadastra novo telefone para o médico.
	 * 
	 * @param idMedico ID do médico.
	 * @param novoTelefone novo telefone a ser cadastrado.
	 * @return confirmação da operação.
	 */
	@RequestMapping(value = "/api/medicos/{id}/telefone", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> adicionarNovoTelefone(
			@PathVariable(name = "id", required = true) Long idMedico,
			@RequestBody ContatoTelefonicoMedico novoTelefone) {
		
		medicoService.adicionarTelefone(idMedico, novoTelefone);
		return statusOK("medico.novotelefone.cadastrado.sucesso");
	}

	/**
	 * Método para alterar os centros de referência de um médico. 
	 * 
	 * @param idMedico - Identificador do médico
	 * @param centrosReferencia - Lista de centros de referência podendo ser nulo
	 * @return Mensagem de sucesso 
	 */
	@RequestMapping(value = "/api/medicos/{id}/centrosreferencia", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.ALTERAR_CADASTRO_MEDICO + "')")	
	public ResponseEntity<CampoMensagem> alterarCentrosReferenciaDoMedico(			
			@PathVariable(required=true, name="id") Long idMedico,
			@RequestBody(required = false) List<CentroTransplante> centrosReferencia) {
		
		medicoService.salvarCentrosReferencia(idMedico, centrosReferencia);

		final CampoMensagem mensagem = new CampoMensagem("", 
                AppUtil.getMensagem(messageSource, "medico.centrosreferencia.atualizado.sucesso"));
			
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Cadastra um novo e-mail para o médico.
	 * 
	 * @param idMedico ID do médico.
	 * @param novoEmail entidade contendo as informações do novo e-mail.
	 * @return OK indicando que a operação foi realizada.
	 */
	@RequestMapping(value = "/api/medicos/logado", method = RequestMethod.GET)
	@JsonView(MedicoView.Logado.class)
	public ResponseEntity<Medico> obterMedicoAssociadoUsuarioLogado() {
		final Medico medico = medicoService.obterMedicoAssociadoUsuarioLogado();
		return statusOK(medico);
	}
	
}
