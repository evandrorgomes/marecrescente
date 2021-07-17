package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.configuration.EmailConfig;
import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.ITipoTarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.feign.dto.TipoTarefaDTO;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.ILaboratorioRepository;
import br.org.cancer.modred.persistence.ILaboratorioRepositoryCustom;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.ILaboratorioService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Implementação da interface que descreve os métodos de negócio que operam sobre a entidade Laboratorio.
 * 
 * @author bruno.sousa
 */
@Service
@Transactional
public class LaboratorioService extends AbstractLoggingService<Laboratorio, Long> implements ILaboratorioService {

    @Autowired
    private ILaboratorioRepository laboratorioRepository;

    @Autowired
    private ILaboratorioRepositoryCustom laboratorioRepositoryCustom;
    
    private IMatchService matchService;
    
    @Autowired
	private EmailConfig emailConfig;
    
    private IConfiguracaoService configuracaoService;
    
    @Autowired
    @Lazy(true)
    private ITipoTarefaFeign tipoTarefaFeign;
    
	private IUsuarioService usuarioService;
    
	@Override
	public IRepository<Laboratorio, Long> getRepository() {
		return laboratorioRepository;
	}
    
    @Override
    public JsonPage listarLaboratorios(PageRequest pageRequest) {
    	Page<Laboratorio> retorno = null;
    	
    	if (pageRequest != null) { 
    		retorno = laboratorioRepository.findAll(pageRequest);
    	}
    	else {
    		List<Laboratorio> lista = laboratorioRepository.findAll(new Sort("nome"));
    		if(!lista.isEmpty()) {
    			retorno = new PageImpl<Laboratorio>(lista, new PageRequest(0, lista.size()), lista.size());
    		}
    	}
    	
    	return retorno == null ? null : new JsonPage(LaboratorioView.Listar.class, retorno);
    	
    }

	@Override
	public List<Laboratorio> listarLaboratoriosCTExame() {
		List<Laboratorio> lista = laboratorioRepository.findByFazExameCT();
		return lista;
	}
	
	@Override
    public Page<Laboratorio> listarLaboratoriosCT(String nome, String uf, PageRequest pageRequest) {
    	return laboratorioRepositoryCustom.listarLaboratorios(nome, uf, pageRequest);
    }
	
	private Laboratorio obterLaboratorio(Long id) {
		if (id == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		final Laboratorio laboratorio = findById(id);
		if (laboratorio == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "laboratório");
		}
		return laboratorio;
	}
	
	@CreateLog(value=TipoLogEvolucao.ENVIO_EMAIL_LABORATORIO_EXAME_DIVERGENTE, perfisExcluidos=Perfis.MEDICO)
    @Override
    public void enviarEmailExameDivergente(Long id, Long idMatch, String destinatarios, String texto) {
    	final Laboratorio laboratorio = obterLaboratorio(id);
    	laboratorio.setMatch(matchService.obterMatch(idMatch));
    	
    	if (destinatarios == null || "".equals(destinatarios) || texto == null || "".equals(texto)) {
  			throw new BusinessException("erro.parametros.invalidos");
    	}
    	
    	final String assunto = configuracaoService.obterConfiguracao(Configuracao.ASSUNTO_EMAIL_EXAME_DIVERGENTE).getValor();
    	
    	emailConfig.comAssunto(assunto);
    	emailConfig.para(destinatarios);
    	emailConfig.comMensagem(texto);
    	
    	emailConfig.sendAutenticado();
    	
    	verificarSeTemLog(laboratorio);
    	
    	criarTarefaCheckListFollowup(laboratorio.getMatch());
    	
    	
    }

	private void criarTarefaCheckListFollowup(Match match) {
		
		TarefaDTO tarefa = TiposTarefa.CHECKLIST_EXAME_DIVERGENTE_FOLLOWUP.getConfiguracao()
			.obterTarefa()
			.comRmr(match.getBusca().getPaciente().getRmr())
			.comObjetoRelacionado(match.getId())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.apply();
		if (tarefa == null) {
			final TipoTarefaDTO tipoTarefa = tipoTarefaFeign.obterTipoTarefa(TiposTarefa.CHECKLIST_EXAME_DIVERGENTE_FOLLOWUP.getId());
			final LocalDateTime dataParaExecutar = LocalDateTime.now().plusSeconds(tipoTarefa.getTempoExecucao());
			
			tipoTarefa.getConfiguracao()
				.criarTarefa()
				.comRmr(match.getBusca().getPaciente().getRmr())
				.comObjetoRelacionado(match.getId())
				.comStatus(StatusTarefa.ABERTA)
				.comDataInicio(dataParaExecutar)
				.apply();
		}
		
	}

	@Override
	public Paciente obterPaciente(Laboratorio laboratorio) {
		if (laboratorio.getMatch() == null) {
			throw new BusinessException("erro.interno");
		}
		return laboratorio.getMatch().getBusca().getPaciente();
	}

	@Override
	public String[] obterParametros(Laboratorio laboratorio) {
		if (laboratorio.getMatch() == null) {
			throw new BusinessException("erro.interno");
		}
		final Long dmr = ((DoadorNacional)laboratorio.getMatch().getDoador()).getDmr();
		
		return new String[] {laboratorio.getNome(), dmr+""};
	}

	/**
	 * Atualiza a lista de usuários vinculados ao laboratório. 
	 * 
	 * @param id do laboratório selecionado.
	 * @param usuariosLaboratorio lista de usuário com perfil de laboratório.
	 */
	@Override
	public void atualizarUsuarios(Long id, List<Usuario> usuariosLaboratorio) {
		Laboratorio laboratorio = this.findById(id);
		List<Usuario> usuarios = new ArrayList<>();
		usuariosLaboratorio.stream().forEach(u->{
			Usuario usuario = usuarioService.obterUsuario(u.getId());
			usuario.setLaboratorio(laboratorio);
			usuarios.add(usuario);
		});
		laboratorio.setUsuarios(usuarios);
		this.save(laboratorio);
	}

	/**
	 * @param matchService the matchService to set
	 */
	@Autowired
	public void setMatchService(IMatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	
}
