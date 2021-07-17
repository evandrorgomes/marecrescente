/**
 * 
 */
package br.org.cancer.modred.helper;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por fechar tarefas.
 * 
 * @author fillipe.queiroz
 *
 */
public class AtualizarTarefa extends IAtualizarTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarTarefa.class);
	
	public static IProcessoFeign processoFeign;
	public static ITarefaFeign tarefaFeign;

	private Usuario usuario;
	private Long objetoRelacionado;
	private Long objetoParceiro;
	private Perfis perfil;
	
	private String descricao;
	private LocalDateTime dataInicio;
	private Long ultimoUsuarioResponsavel;
	private Long idTarefa;

	public AtualizarTarefa() {
		super();
	}

	/**
	 * Construtor com passagem de parametros da configuração do tipo da tarefa.
	 * 
	 * @param perfil - perfis do usuario
	 * @param idTipoTarefa - tipo da tarefa
	 * @param classeObterTarefa - classe a ser utilizada para obter a tarefa
	 */
	public AtualizarTarefa(Perfis perfil, Long idTipoTarefa) {
		this.perfil = perfil;
		this.tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
	}

	public IAtualizarTarefa comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public IAtualizarTarefa comParceiro(Long parceiro) {
		this.objetoParceiro = parceiro;
		return this;
	}

	public IAtualizarTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	public IAtualizarTarefa comTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
		return this;
	}

	public IAtualizarTarefa comDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public IAtualizarTarefa comDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
		return this;
	}

	public IAtualizarTarefa comUltimoUsuarioResponsavel(Long ultimoUsuarioResponsavel) {
		this.ultimoUsuarioResponsavel = ultimoUsuarioResponsavel;
		return this;
	}
	
	/**
	 * Executar a atualização da tarefa.
	 * 
	 * @return tarefa - tarefa
	 */
	public TarefaDTO apply() {
		if (idTarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.id.invalido");
		}
		
		if (AtualizarTarefa.processoFeign == null || AtualizarTarefa.tarefaFeign == null) {
			AutowireHelper.autowire(this);
		}
		
		
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		if (tarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.nulo");

		}

		tarefa.setDataAtualizacao(LocalDateTime.now());
		if (objetoParceiro != null) {
			tarefa.setRelacaoParceiro(objetoParceiro);
		}
		if (descricao != null) {
			tarefa.setDescricao(descricao);
		}
		if (objetoRelacionado != null) {
			tarefa.setObjetoRelacaoEntidade(objetoRelacionado);
		}
		if (perfil != null) {
			tarefa.setPerfilResponsavel(perfil.getId());
		}
		if (usuario != null) {
			tarefa.setUsuarioResponsavel(usuario.getId());
		}
		if (dataInicio != null) {
			tarefa.setDataInicio(dataInicio);
		}
		if (ultimoUsuarioResponsavel != null) {
			tarefa.setUltimoUsuarioResponsavel(ultimoUsuarioResponsavel);
		}

		tarefa = tarefaFeign.atualizarTarefa(tarefa.getId(), tarefa);
		LOGGER.info("TarefaDTO atualizada com sucesso: {}", tarefa);

		return tarefa;

	}
	
	/**
	 * @param tarefaFeign the tarefaRepository to set
	 */
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		AtualizarTarefa.tarefaFeign = tarefaFeign;
	}
	
	@Autowired
	@Lazy(true)
	public void setProcessoFeign(IProcessoFeign processoFeign) {
		AtualizarTarefa.processoFeign = processoFeign;
	}

	public IProcessoFeign getProcessoFeign() {
		return AtualizarTarefa.processoFeign;
	}


}
