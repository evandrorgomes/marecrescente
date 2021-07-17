package br.org.cancer.modred.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonicoMedico;
import br.org.cancer.modred.model.EmailContatoMedico;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IMedicoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IContatoTelefonicoService;
import br.org.cancer.modred.service.IEmailContatoService;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IPreCadastroMedicoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;


/**
 * Classe de implementacao de negócio de Medico Service.
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class MedicoService extends AbstractService<Medico, Long> implements IMedicoService {

    @Autowired
    private IMedicoRepository medicoRepository;
    
    @Autowired
    private IPreCadastroMedicoService preCadastroService;
    
    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private MessageSource messageSource;
    
    @SuppressWarnings("rawtypes")
	@Autowired
    private IStorageService storageService;
    
    @Autowired
    private IEmailContatoService<EmailContatoMedico> emailContatoService;
  
	 @Autowired
    private ICentroTransplanteService centroTransplanteService;
  
    @Autowired
    private IContatoTelefonicoService<ContatoTelefonicoMedico> contatoTelefonicoService;
    

	@Override
	public IRepository<Medico, Long> getRepository() {
		return medicoRepository;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public Medico obterMedicoPorUsuario(Long id) {
        return medicoRepository.findByUsuarioId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Medico obterMedico(Long id) {
    	Medico medico = findById(id);
    	if (medico == null) {
    		throw new BusinessException("mensagem.nenhum.registro.encontrado", "Médico");
    	}
        return medico;
    }

	@Override
	public boolean verificarCrm(String crm, Long idMedico) {
		return medicoRepository.existsByCrmAndId(crm, idMedico);
	}

	@Override
	public Medico concederAcessoMedico(Long idPreCadastroMedico) {
		final PreCadastroMedico preCadastro = preCadastroService.findById(idPreCadastroMedico);
		Usuario usuarioCriado = 
				usuarioService.criarUsuarioSenhaTemporaria(
						preCadastro.getLogin(), preCadastro.getNome(), preCadastro.getEmailPrincipal().getEmail(), Perfis.MEDICO);
		
		Medico medico = new Medico(preCadastro);
		medico.setUsuario(usuarioCriado);
		return save(medico);
	}

	@Override
	public PageImpl<Medico> listarMedicos(String crm, String nome, PageRequest pageRequest) {
		
		return medicoRepository.listarMedicos(crm, nome, pageRequest);
	}
	
	@Override
	public List<Medico> listarMedicos() {
		return medicoRepository.findAll();
	}


	@Override
	public void salvarDadosIdentificacao(Long id, Medico medico) {
		Medico medicoRecuperado = obterMedico(id);
		
		String diretorioAnterior = null;
		if (!medicoRecuperado.getCrm().equals(medico.getCrm())) {
			diretorioAnterior = medicoRecuperado.getArquivoCrm();
			String nomeArquivo = ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(diretorioAnterior);
			medicoRecuperado.setArquivoCrm(StorageService.DIRETORIO_MEDICO_CRM_STORAGE + "/" + medico.getCrm() + "/" + nomeArquivo);
		}
		
		medicoRecuperado.setCrm(medico.getCrm());
		medicoRecuperado.setNome(medico.getNome());
		
		save(medicoRecuperado);
		
		if (diretorioAnterior != null) {
			storageService.moverArquivo(diretorioAnterior, medicoRecuperado.getArquivoCrm());
		}
		
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(Medico medico) {
		List<CampoMensagem> erros = super.validateEntity(medico);

		if (verificarCrm(medico.getCrm(), medico.getId())) {
			erros.add(new CampoMensagem("crm", 
					AppUtil.getMensagem(messageSource, "erro.mensagem.medico.crm.ja.utilizado")));			
		}
		
		return erros;
	}

	@Override
	public void salvarCentrosReferencia(Long id, List<CentroTransplante> centrosReferencia) {
		Medico medico = obterMedico(id);
		if(centrosReferencia == null || centrosReferencia.isEmpty()){
			medico.setCentrosReferencia(null);
		}
		else{
			List<CentroTransplante> centros = new ArrayList<CentroTransplante>();
			centrosReferencia.forEach(c->{
				centros.add(centroTransplanteService.findById(c.getId()));
			});
			medico.setCentrosReferencia(centros);
		}
		save(medico);
		
	}
	
	@Override
	public File obterArquivoCRM(Long idMedico) {
		final Medico medico = findById(idMedico);
		return storageService.download(medico.getArquivoCrm());
	}

	@Override
	public void adicionarEmail(Long idMedico, EmailContatoMedico email) {
		if(Boolean.TRUE.equals(email.getPrincipal())){
			emailContatoService.update(new UpdateSet<Boolean>("principal", Boolean.FALSE), new Equals<Long>("medico.id", idMedico));
		}
		email.setMedico(new Medico(idMedico));
		emailContatoService.save(email);
	}

	@Override
	public void adicionarTelefone(Long idMedico, ContatoTelefonicoMedico telefone) {
		if(Boolean.TRUE.equals(telefone.getPrincipal())){
			contatoTelefonicoService.update(new UpdateSet<Boolean>("principal", Boolean.FALSE), new Equals<Long>("medico.id", idMedico));
		}
		telefone.setMedico(new Medico(idMedico));
		contatoTelefonicoService.save(telefone);
	}

	@Override
	public Medico obterMedicoAssociadoUsuarioLogado() {
		Medico medico = obterMedicoPorUsuario(usuarioService.obterUsuarioLogadoId());
		if(medico == null){
			throw new BusinessException("medico.usuario.logado.nao.e.medico.falha");
		}
		return medico;
	}

	
}
