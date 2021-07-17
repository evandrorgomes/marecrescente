package br.org.cancer.modred.helper;

import java.util.Arrays;
import java.util.Comparator;

import org.hibernate.internal.util.config.ConfigurationException;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AtributoOrdenacao;

/**
 * Classe responsável por configurar a utilização dos tipos de tarefa na aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
public class ConfiguracaoTipoTarefa extends AbstractConfiguracaoTarefa {

	private IAtribuirTarefa atribuirTarefa;
	private IRemoverAtribuirTarefa removerAtribuicaoTarefa;
	private IObterTarefa obterTarefa;
	private IFecharTarefa fecharTarefa;
	private IListarTarefa listarTarefa;
	private ICriarTarefa criarTarefa;
	private IAtualizarTarefa atualizarTarefa;
	private ICancelarTarefa cancelarTarefa;
	private Long idTarefa;
	private Comparator<TarefaDTO> ordenacao;
	private boolean iniciarProcesso = false;

	/**
	 * Construtor preparado para receber o ID do tipo de tarefa, obrigatoriamente.
	 * 
	 * @param idTipoTarefa ID do tipo de tarefa.
	 */
	private ConfiguracaoTipoTarefa(Long idTipoTarefa) {
		super(idTipoTarefa);
		somenteAgrupador(Boolean.FALSE);
	}

	/**
	 * Cria uma nova instância para o tipo de tarefa informado.
	 * 
	 * @param idTipoTarefa ID da tarefa.
	 * @return nova instancia de ConfiguracaoTipoTarefa
	 */
	public static ConfiguracaoTipoTarefa newInstance(Long idTipoTarefa) {
		return new ConfiguracaoTipoTarefa(idTipoTarefa);
	}

	@Override
	public IListarTarefa listarTarefa() {
		if (listarTarefa != null) {
			return listarTarefa;
		}
		return new ListarTarefa(getPerfil(), Arrays.asList(getIdTipoTarefa()), getJsonView(), getTipoProcesso(), getOrdenacao());
	}

	@Override
	public IObterTarefa obterTarefa() {
		if (obterTarefa != null) {
			return obterTarefa;
		}

		return new ObterTarefa(getPerfil(), getIdTipoTarefa(), getTipoProcesso());
	}

	@Override
	public IFecharTarefa fecharTarefa() {
		if (fecharTarefa != null) {
			return fecharTarefa;
		}
		return new FecharTarefa(getPerfil(), getIdTipoTarefa());
	}

	@Override
	public IAtribuirTarefa atribuirTarefa() {
		if (atribuirTarefa != null) {
			return atribuirTarefa;
		}
		return new AtribuirTarefa(getPerfil(), getIdTipoTarefa());
	}

	@Override
	public ICriarTarefa criarTarefa() {
		if (criarTarefa != null) {
			return criarTarefa;
		}
		return new CriarTarefa(getPerfil(), getIdTipoTarefa());
	}
	
	@Override
	public IAtualizarTarefa atualizarTarefa() {
		if (atualizarTarefa != null) {
			return atualizarTarefa;
		}
		return new AtualizarTarefa(getPerfil(), getIdTipoTarefa());
	}
	
	@Override
	public IRemoverAtribuirTarefa removerAtribuicaoTarefa() {
		if (removerAtribuicaoTarefa != null) {
			return removerAtribuicaoTarefa;
		}
		return new RemoverAtribuirTarefa(getPerfil(), getIdTipoTarefa());
	}

	@Override
	public ICancelarTarefa cancelarTarefa() {
		if (cancelarTarefa != null) {
			return cancelarTarefa;
		}
		return new CancelarTarefa(getPerfil(), getIdTipoTarefa());
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param atribuirTarefa
	 */
	public void setAtribuirTarefa(IAtribuirTarefa atribuirTarefa) {
		this.atribuirTarefa = atribuirTarefa;
	}
	
	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param removerAtribuirTarefa
	 */
	public void setRemoverAtribuicaoTarefa(IRemoverAtribuirTarefa removerAtribuicaoTarefa) {
		this.removerAtribuicaoTarefa = removerAtribuicaoTarefa;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param obterTarefa the obterTarefa to set
	 */
	public void setObterTarefa(IObterTarefa obterTarefa) {
		this.obterTarefa = obterTarefa;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param buscarTarefa the buscarTarefa to set
	 */
	public void setListarTarefa(IListarTarefa buscarTarefa) {
		this.listarTarefa = buscarTarefa;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param criarTarefa the criarTarefa to set
	 */
	public void setCriarTarefa(ICriarTarefa criarTarefa) {
		this.criarTarefa = criarTarefa;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param atualizarTarefa the atualizarTarefa to set
	 */
	public void setAtualizarTarefa(IAtualizarTarefa atualizarTarefa) {
		this.atualizarTarefa = atualizarTarefa;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param fecharTarefa the fecharTarefa to set
	 */
	public void setFecharTarefa(IFecharTarefa fecharTarefa) {
		this.fecharTarefa = fecharTarefa;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param cancelarTarefa the cancelarTarefa to set
	 */
	public void setCancelarTarefa(ICancelarTarefa cancelarTarefa) {
		this.cancelarTarefa = cancelarTarefa;
	}

	/**
	 * @return the idTarefa
	 */
	public Long getIdTarefa() {
		return idTarefa;
	}

	/**
	 * @param idTarefa the idTarefa to set
	 */
	@Override
	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}

	@Override
	public IConfiguracaoProcessServer comOrdenacao(Class<? extends Comparator<TarefaDTO>> classeComparacao) {
		try {
			this.ordenacao = classeComparacao.newInstance();
		}
		catch (Exception e) {
			throw new ConfigurationException("Classe definida para ordenação não pode ser instanciada. "
					+ "Verifica se existe um construtor sem parâmetros e que o mesmo está acessível.");
		}
		return this;
	}

	@Override
	public IConfiguracaoProcessServer comOrdenacao(AtributoOrdenacao... atributos) {
		this.ordenacao = new TarefaComparator(atributos);
		return this;
	}

	@Override
	public Comparator<TarefaDTO> getOrdenacao() {
		return ordenacao;
	}

	@Override
	public IConfiguracaoProcessServer iniciarProcesso() {
		this.iniciarProcesso = Boolean.TRUE;
		return this;
	}

	@Override
	public boolean isIniciarProcesso() {
		return this.iniciarProcesso;
	}
}