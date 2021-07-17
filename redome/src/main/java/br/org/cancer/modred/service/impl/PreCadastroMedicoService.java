/**
 * 
 */
package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.model.domain.StatusPreCadastro;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPreCadastroMedicoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IPreCadastroMedicoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Implementação das funcionalidades que envolvem a entidade PreCadastroMedico.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class PreCadastroMedicoService extends AbstractService<PreCadastroMedico, Long> implements IPreCadastroMedicoService {

	@Autowired
	private IPreCadastroMedicoRepository preCadastroMedicoRepositorio;
	
	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IStorageService storageService;
	
	
	@Override
	public IRepository<PreCadastroMedico, Long> getRepository() {
		return preCadastroMedicoRepositorio;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(PreCadastroMedico preCadastro) {
		List<CampoMensagem> validacoes = super.validateEntity(preCadastro);
		List<CampoMensagem> validacoesDuplicidade = 
				validarDuplicidadePreCadastroMedico(preCadastro.getCrm(), preCadastro.getLogin());
		
		List<CampoMensagem> validacoesEndereco = 
				new ConstraintViolationTransformer(super.validator.validate(preCadastro.getEndereco())).transform();
		
		List<CampoMensagem> validacoesTelefones = 
				new ConstraintViolationTransformer(super.validator.validate(preCadastro.getTelefones())).transform();
		
		List<CampoMensagem> validacoesEmails = 
				new ConstraintViolationTransformer(super.validator.validate(preCadastro.getEmails())).transform();
		
		
		validacoes.addAll(validacoesDuplicidade);
		validacoes.addAll(validacoesEndereco);
		validacoes.addAll(validacoesTelefones);
		validacoes.addAll(validacoesEmails);
		return validacoes;
	}

	@Override
	public void incluir(PreCadastroMedico preCadastro, MultipartFile arquivoCrm) {
		preCadastro.setStatus(StatusPreCadastro.AGUARDANDO_APROVACAO);
		preCadastro.setDataSolicitacao(LocalDateTime.now());
		preCadastro.setDataAtualizacao(LocalDateTime.now());
		preCadastro.setArquivoCrm(uploadArquivoCrm(preCadastro, arquivoCrm));
		save(preCadastro);
		
		notificarEmailAdministradores();
	}
	
	/**
	 * Sobe o arquivo referente ao CRM inserido no pré cadastro do médico.
	 * 
	 * @param preCadastroMedico pré cadastro médico.
	 * @param arquivoCrm arquivo comprovando o CRM.
	 * 
	 * @return caminho completo que será salvo no storage.
	 */
	private String uploadArquivoCrm(PreCadastroMedico preCadastroMedico, MultipartFile arquivoCrm) {
		try {
			final String diretorio = StorageService.DIRETORIO_MEDICO_CRM_STORAGE + "/" + preCadastroMedico.getCrm();
			final String nomeArquivo = ArquivoUtil.obterNomeArquivo(arquivoCrm);
			storageService.upload(nomeArquivo, diretorio, arquivoCrm);
			
			return diretorio + "/" + nomeArquivo;
		}
		catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		}
	}

	@Override
	public void validarIdentificacao(String crm, String login) {
		List<CampoMensagem> validacoes = validarDuplicidadePreCadastroMedico(crm, login);
		
		if (!validacoes.isEmpty()) {
			throw new ValidationException("erro.validacao", validacoes);
		}
		
	}

	/**
	 * Valida se existe duplicidade na base com os campos CRM ou login.
	 * 
	 * @param crm número do CRM.
	 * @param login login sugerido no pré cadastro.
	 * @return lista de validações, caso ocorram.
	 */
	private List<CampoMensagem> validarDuplicidadePreCadastroMedico(String crm, String login) {
		List<CampoMensagem> validacoes = new ArrayList<>();

		if (crm != null) {
			if (verificarSeCRMExiste(crm)) {				
				validacoes.add(new CampoMensagem("crm", AppUtil.getMensagem(messageSource, "erro.medico.precadastro.crm.existente")));
			}
		}
		if (login != null) {
			if (verificarSeLoginExiste(login)) {				
				validacoes.add(new CampoMensagem("login", AppUtil.getMensagem(messageSource, "erro.medico.precadastro.login.existente")));
			}
		}
		return validacoes;
	}
	
	/**
	 * Método que verifica se o CRM existe na tabela de pré-cadastro e no médico.
	 * 
	 * @param crm - valor do CRM a ser verificado
	 * @return True se o CRM existir
	 */
	private boolean verificarSeCRMExiste(String crm) {
		if (!preCadastroMedicoRepositorio.existsByCrmAndStatus(crm, StatusPreCadastro.AGUARDANDO_APROVACAO)) {
			return medicoService.verificarCrm(crm, null);
		}
		return true;
	}
	
	/**
	 * Método que verifica se o LOGIN existe na tabela de pré-cadastro e no usuário.
	 * 
	 * @param login - valor do Login a ser verificado
	 * @return True se o Login existir
	 */
	private boolean verificarSeLoginExiste(String login) {
		if (!preCadastroMedicoRepositorio.existsByLoginAndStatus(login, StatusPreCadastro.AGUARDANDO_APROVACAO)) {
			return usuarioService.verificarSeLoginDuplicado(new Usuario(login));
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageImpl<PreCadastroMedico> listarPreCadastroMedicoPorStatus(StatusPreCadastro status,
			PageRequest pageRequest) {
		
		return preCadastroMedicoRepositorio.findByStatus(status, pageRequest);
	}

	@Override
	public PreCadastroMedico obterPreCadastroMedicoPorId(Long id) {
		PreCadastroMedico preCadastro = findById(id);
		if (preCadastro == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "Pré Cadastro Médico");
		}
		
		return preCadastro;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reprovarPreCadastroMedico(Long idPreCadastroMedico, String justificativa) {
		PreCadastroMedico preCadastroMedico = obterPreCadastroMedicoPorId(idPreCadastroMedico);
		
		if (!preCadastroMedico.getStatus().equals(StatusPreCadastro.AGUARDANDO_APROVACAO)) {
			throw new BusinessException("erro.medico.precadastro.ja.avaliado");
		}
		
		preCadastroMedico.setStatus(StatusPreCadastro.REPROVADO);
		preCadastroMedico.setDataAtualizacao(LocalDateTime.now());
		preCadastroMedico.setJustificativa(justificativa);
		preCadastroMedico.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		
		save(preCadastroMedico);
		
		notificarEmailReprovacao(preCadastroMedico.getEmailPrincipal().getEmail());
	}
	
	private void notificarEmailAdministradores(){
		// TODO: Configurar informações do e-mail e mudar assinatura do método no usuario service.
		usuarioService.enviarEmail(Perfis.ADMIN);
	}
	
	private void notificarEmailReprovacao(String email){
		// TODO: Configurar informações do e-mail e mudar assinatura do método no usuario service.
		usuarioService.enviarEmail(email, "", "");
	}

	@Override
	public void aprovar(Long id) {
		Integer aprovado = 
				preCadastroMedicoRepositorio.aprovar(id, LocalDateTime.now(), usuarioService.obterUsuarioLogadoId());
		
		final PreCadastroMedico preCadastro = findById(id);
		
		if(aprovado == 0){
			throw new BusinessException("pre.cadastro.aprovado.erro", preCadastro.getStatus().name());
		}
		
		final Medico medico = medicoService.concederAcessoMedico(id);
		notificarEmailAprovacao(medico.getUsuario());
	}
	
	private void notificarEmailAprovacao(Usuario usuario){
		usuarioService.enviarSenhaPorEmail(usuario, Relatorios.EMAIL_ENVIO_SENHA.getCodigo());
	}

	@Override
	public File obterArquivoCRM(Long idPreCadastroMedico) {
		final PreCadastroMedico preCadastro = findById(idPreCadastroMedico);
		return storageService.download(preCadastro.getArquivoCrm());
	}

}
