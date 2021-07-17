/**
 * 
 */
package br.org.cancer.modred.helper;

import java.time.LocalDateTime;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.client.ITipoTarefaFeign;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.feign.dto.TipoTarefaDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.IDoadorService;

/**
 * Classe responsável por fechar tarefas.
 * 
 * @author fillipe.queiroz
 *
 */
public class CriarTarefa extends ICriarTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(CriarTarefa.class);

	public static IProcessoFeign processoFeign;
	public static ITarefaFeign tarefaFeign;
	public static IDoadorService doadorService;
	public static ITipoTarefaFeign tipoTarefaFeign;
	
	private Usuario usuario;
	private Long objetoRelacionado;
	private Long objetoParceiro;
	private StatusTarefa status;
	private Perfis perfil;
	//private TarefaDTO tarefa;
	
	private String descricao;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Long ultimoUsuarioResponsavel;
	private TarefaDTO tarefaPai;
	private Boolean inclusivoExclusivo;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFim;
	private Boolean agendada = false;
	private Usuario usuarioAgendamento;
	
	/**
	 * Construtor com passagem de parametros da configuração do tipo da tarefa.
	 * 
	 * @param perfil - perfis do usuario
	 * @param idTipoTarefa - tipo da tarefa
	 * @param classeObterTarefa - classe a ser utilizada para obter a tarefa
	 */
	public CriarTarefa(Perfis perfil, Long idTipoTarefa) {
		this.perfil = perfil;
		this.tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
	}

	@Override
	public ICriarTarefa comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}
	
	@Override
	public ICriarTarefa comParceiro(Long objetoParceiro) {
		this.objetoParceiro = objetoParceiro;
		return this;
	}

	@Override
	public ICriarTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	@Override
	public ICriarTarefa comDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	@Override
	public ICriarTarefa comDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
		return this;
	}

	@Override
	public ICriarTarefa comStatus(StatusTarefa statusTarefa) {
		this.status = statusTarefa;
		return this;
	}

	@Override
	public ICriarTarefa comUltimoUsuarioResponsavel(Long ultimoUsuarioResponsavel) {
		this.ultimoUsuarioResponsavel = ultimoUsuarioResponsavel;
		return this;
	}
	
	@Override
	public ICriarTarefa comTarefaPai(TarefaDTO tarefaPai) {
		this.tarefaPai = tarefaPai;
		return this;
	}
	
	@Override
	public ICriarTarefa comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}

	@Override
	public ICriarTarefa comDoadorId(Long idDoador) {
		this.idDoador = idDoador;
		return this;
	}
	
	@Override
	public ICriarTarefa comDataFinal(LocalDateTime dataFinal) {
		this.dataFim = dataFinal;
		return this;
	}
	
	public ICriarTarefa inclusive() {
		this.inclusivoExclusivo = false;
		return this;
	}
	
	public ICriarTarefa exclusive() {
		this.inclusivoExclusivo = true;
		return this;
	}
	
	@Override
	public ICriarTarefa comHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
		return this;
	}
	
	@Override
	public ICriarTarefa comHoraFim(LocalDateTime horaFim) {
		this.horaFim = horaFim;
		return this;
	}
	
	@Override
	public ICriarTarefa agendada() {
		this.agendada = true;
		return this;
	}
	
	@Override
	public ICriarTarefa comOUsuarioParaAgendamento(Usuario usuario) {
		this.usuarioAgendamento = usuario;
		return this;
	}

	@Override
	public TarefaDTO apply() {
		
		if (CriarTarefa.processoFeign == null || CriarTarefa.tarefaFeign == null
				|| CriarTarefa.doadorService == null || CriarTarefa.tipoTarefaFeign == null) {
			AutowireHelper.autowire(this);
		}
		
		TarefaDTO tarefa = new TarefaDTO();
		tarefa.setDataCriacao(LocalDateTime.now());
		tarefa.setDataAtualizacao(LocalDateTime.now());

		//ProcessoDTO processo = obterProcesso();
		ProcessoDTO processo = new ProcessoDTO(tipoTarefa.getTipoProcesso());
		processo.setRmr(rmr);
		processo.setIdDoador(idDoador);
		tarefa.setProcesso(processo);

		if (status == null) {
			tarefa.setStatus(StatusTarefa.ABERTA.getId());
		}
		else {
			tarefa.setStatus(status.getId());
		}
		tarefa.setRelacaoParceiro(objetoParceiro);
		tarefa.setDescricao(descricao);
		tarefa.setRelacaoEntidade(objetoRelacionado);
		tarefa.setPerfilResponsavel(perfil != null ? perfil.getId() : null);
		tarefa.setUsuarioResponsavel(usuario != null ? usuario.getId() : null);
		tarefa.setTipoTarefa(tipoTarefa != null ? new TipoTarefaDTO(tipoTarefa.getId()) : null);
		tarefa.setDataInicio(dataInicio);
		tarefa.setDataFim(dataFim);
		tarefa.setInclusivoExclusivo(inclusivoExclusivo);
		tarefa.setUltimoUsuarioResponsavel(ultimoUsuarioResponsavel);
		tarefa.setTarefaPai(tarefaPai);
		tarefa.setHoraInicio(horaInicio);
		tarefa.setHoraFim(horaFim);
		tarefa.setAgendado(agendada);
		tarefa.setUsuarioResponsavelAgendamento(usuarioAgendamento != null ? usuarioAgendamento.getId() : null);

		tarefa = tarefaFeign.criarTarefa(tarefa);

		LOGGER.info("TarefaDTO criada com sucesso: {}", tarefa);

		//criarTarefaFollowUp(tarefa);
		
		/*
		 * if (usuario != null && tipoTarefa.isCriarTarefaAtribuida()) {
		 * tipoTarefa.getConfiguracao().atribuirTarefa() .comUsuario(usuario);
		 * LOGGER.info("TarefaDTO atribuida com sucesso: {}", tarefa); }
		 */
		return tarefa;

	}
	
	private void criarTarefaFollowUp(TarefaDTO tarefaPai) {
		//TiposTarefa tipoTarefaEnum = TiposTarefa.valueOf(tarefa.getTipoTarefa().getId());

		if (CollectionUtils.isNotEmpty(tipoTarefa.getFollowUp())) {			
			tipoTarefa.getFollowUp().stream().forEach(tipoTarefaFollowUp -> {
				TipoTarefaDTO tipoTarefaFollowUpRecuperado = tipoTarefaFeign.obterTipoTarefa(tipoTarefaFollowUp.getId());
				LocalDateTime dataParaExecutar = LocalDateTime.now().plusSeconds(tipoTarefaFollowUpRecuperado.getTempoExecucao());

				TarefaDTO tarefaCriada = tipoTarefaFollowUp.getConfiguracao().criarTarefa()						
						.comDataInicio(dataParaExecutar)
						.comStatus(StatusTarefa.AGUARDANDO)
						.comProcessoId(tarefaPai.getProcesso().getId())
						.comTarefaPai(tarefaPai)
						.apply();

				LOGGER.info("Criou tarefa de follow up com id \"{}\" para a tarefa de id\"{}\"", tarefaCriada.getId(), tarefaPai
						.getId());
			});
		}
	}
	
	@Override
	public ProcessoDTO obterProcesso() {
		ProcessoDTO processo = super.obterProcesso();
		
		if(tipoTarefa.getConfiguracao().isIniciarProcesso() && processo == null){
			/*
			 * if(processo != null){ LOGGER.error("O processo, marcado inicial na tarefa " +
			 * tipoTarefa.getId() + ", já foi criado."); throw new
			 * BusinessException("erro.interno"); }
			 */
			processo = new ProcessoDTO(tipoTarefa.getTipoProcesso());
			if (rmr != null) {
				processo.setPaciente(new Paciente(rmr));	
			}
			if(idDoador != null) {
				Doador doador = doadorService.findById(idDoador);
				processo.setDoador(doador);
			}
			
			processo = processoFeign.criarProcesso(processo);
		}
		return processo;
	}


	/**
	 * @param tarefaFeign the tarefaRepository to set
	 */
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		CriarTarefa.tarefaFeign = tarefaFeign;
	}
	
	@Autowired
	@Lazy(true)
	public void setProcessoFeign(IProcessoFeign processoFeign) {
		CriarTarefa.processoFeign = processoFeign;
	}

	@Autowired
	public void setDoadorService(IDoadorService doadorService) {
		CriarTarefa.doadorService = doadorService;
	}

	@Autowired
	@Lazy(true)
	public void setTipoTarefaFeign(ITipoTarefaFeign tipoTarefaFeign) {
		CriarTarefa.tipoTarefaFeign = tipoTarefaFeign;
	}
	
    @Override
    protected IProcessoFeign getProcessoFeign() {
    	return CriarTarefa.processoFeign;
    }	

	
	
}