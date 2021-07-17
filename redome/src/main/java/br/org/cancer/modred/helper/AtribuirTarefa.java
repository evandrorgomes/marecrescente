/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe para centralizar a atribuição de tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public class AtribuirTarefa extends IAtribuirTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(AtribuirTarefa.class);

	private Usuario usuario;
	private Long objetoRelacionado;
	private Perfis perfil;
	
	public static IProcessoFeign processoFeign;
	public static ITarefaFeign tarefaFeign;
	private List<Long> parceiros;
	private TarefaDTO tarefa;
	public static IUsuarioService usuarioService;
	private boolean comUsuarioLogado;

	public AtribuirTarefa() {
		super();
	}

	public AtribuirTarefa(Perfis perfil, Long idTipoTarefa) {
		this.perfil = perfil;
		this.tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
	}

	@Override
	public IAtribuirTarefa comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	@Override
	public IAtribuirTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}
	@Override
	public IAtribuirTarefa comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}

	@Override
	public IAtribuirTarefa comUsuarioLogado() {
		this.comUsuarioLogado = true;
		return this;
	}

	@Override
	public IAtribuirTarefa comTarefa(TarefaDTO tarefa) {
		this.tarefa = tarefa;
		return this;
	}
	
	@Override
	public TarefaDTO apply() {
		
		if (AtribuirTarefa.processoFeign == null || AtribuirTarefa.tarefaFeign == null ||  AtribuirTarefa.usuarioService == null) {
			AutowireHelper.autowire(this);
		}

		if (usuario == null && !comUsuarioLogado) {
			throw new BusinessException("erro.mensagem.tarefa.atribuir.usuario.invalido");
		}

		if (usuario != null && comUsuarioLogado) {
			throw new BusinessException("erro.mensagem.tarefa.atribuir.usuario.definir");
		}
		//usuarioService = (IUsuarioService) ApplicationContextProvider.obterBean(IUsuarioService.class);
		if (comUsuarioLogado) {
			usuario = usuarioService.obterUsuarioLogado();
		}
		

		LOGGER.info("Iniciando atribuição de tarefa para usuario: " + usuario.getId());

		if (tarefa == null) {

			//final ProcessoDTO processo = obterProcesso();
			tarefa = tipoTarefa.getConfiguracao().obterTarefa()
						.comParceiros(parceiros)
						.comObjetoRelacionado(objetoRelacionado)
						.comRmr(rmr)
						.comIdDoador(idDoador)						
						.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
						.apply();
		}

		if (tarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.nulo");
		}

		/*
		 * if (StatusTarefa.ATRIBUIDA.equals(tarefa.getStatus()) &&
		 * isOutroUsuario(tarefa, usuario)) { throw new
		 * BusinessException("erro.mensagem.tarefa.ja.atribuida"); }
		 */

		if (perfil != null) {

			List<Perfil> perfisResult = usuarioService.obterUsuario(usuario.getId())
					.getPerfis()
					.stream()
					.filter(p -> p.getId()
							.equals(perfil.getId()))
					.collect(Collectors.toList());

			if (perfisResult.isEmpty()) {
				throw new BusinessException("erro.mensagem.tarefa.usuario.invalido");
			}
		}

		//criarTarefaAssociadaSeHouver(tarefa);
		tarefa = tarefaFeign.atribuirTarefaUsuario(tarefa.getId(), usuario.getId());
		//	throw new BusinessException("erro.mensagem.tarefa.responsavel.nao.alterado");
		//}

		return tarefa;

	}

	private boolean isOutroUsuario(TarefaDTO tarefa, Usuario usuarioResponsavel) {
		return !usuarioResponsavel.getId().equals(tarefa.getUsuarioResponsavel());
	}

	/**
	 * Cria a tarefa automática, se a tarefa original indicar que necessita.
	 * 
	 * @param tarefa tarefa que origina a tarefa automática, se a configuração indicar que há necessidade.
	 */
	/*
	 * private void criarTarefaAssociadaSeHouver(TarefaDTO tarefa) { //TiposTarefa
	 * tipo = TiposTarefa.valueOf(tarefa.getTipoTarefa().getId());
	 * 
	 * if (tipoTarefa.getTimeout() == null) { return; }
	 * 
	 * Long ultimoUsuarioResponsavel = null;
	 * 
	 * TarefaDTO tarefaRollback = obterTarefaAssociada(tarefa); if (tarefaRollback
	 * != null) { LOGGER.info("Fechando tarefa de rollback");
	 * tarefaRollback.getTipo() .getConfiguracao() .cancelarTarefa()
	 * .comProcessoId(tarefaRollback.getProcesso().getId())
	 * .comTarefa(tarefaRollback.getId()) .apply(); }
	 * 
	 * if (TiposTarefa.ROLLBACK_ATRIBUICAO.equals(tipoTarefa.getTimeout())) {
	 * ultimoUsuarioResponsavel = tarefa.getUsuarioResponsavel() == null ? null :
	 * tarefa.getUsuarioResponsavel(); }
	 * 
	 * LOGGER.info("Criando tarefa associada a tarefa do tipo:" +
	 * tipoTarefa.getTimeout().getId());
	 * tipoTarefa.getTimeout().getConfiguracao().criarTarefa()
	 * .comProcessoId(tarefa.getProcesso().getId())
	 * .comUltimoUsuarioResponsavel(ultimoUsuarioResponsavel) .comTarefaPai(tarefa)
	 * .apply(); }
	 */

	/**
	 * Obtém tarefa de rollback associada a tarefa informada.
	 * 
	 * @param tarefa ID da tarefa relacionada a tarefa automática.
	 * @param tipoTarefa Tipo de tarefa automática (TIMEOUT, ROLLBACK...)
	 * @return tarefa associada, se existir.
	 */
	/*
	 * private TarefaDTO obterTarefaAssociada(TarefaDTO tarefa) { return
	 * tipoTarefa.getTimeout().getConfiguracao().obterTarefa()
	 * .comTarefaPai(tarefa.getId()) .comProcessoId(tarefa.getProcesso().getId())
	 * .comStatus(Arrays.asList(StatusTarefa.ABERTA)).apply(); }
	 */
	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	@Lazy(true)
	public void setUsuarioService(IUsuarioService usuarioService) {
		AtribuirTarefa.usuarioService = usuarioService;
	}

	/**
	 * @param tarefaFeign the tarefaRepository to set
	 */
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		AtribuirTarefa.tarefaFeign = tarefaFeign;
	}
	
	public IProcessoFeign getProcessoFeign() {
		return AtribuirTarefa.processoFeign;
	}
	
	

}