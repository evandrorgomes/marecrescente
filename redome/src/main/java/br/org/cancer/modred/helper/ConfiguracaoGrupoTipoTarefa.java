package br.org.cancer.modred.helper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.internal.util.config.ConfigurationException;
import org.terracotta.modules.ehcache.wan.IllegalConfigurationException;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AtributoOrdenacao;

/**
 * @author Pizão
 * 
 * Define o comportamento para a configuração do tipo de tarefa definido como somente agrupador.
 * Este tipo é utilizado apenas para agrupar outros tipos de tarefa somente.
 *
 */
public class ConfiguracaoGrupoTipoTarefa extends AbstractConfiguracaoTarefa{
	
	private Comparator<TarefaDTO> ordenacao;
	private IListarTarefa listarTarefa;
	
	/**
	 * Construtor de configuração de tarefa agrupadas.
	 * 
	 * @param idTipoTarefa ID do tipo de tarefa associado
	 * (este ID não é utilizado nos métodos de listagem, atribuição, etc).
	 */
	private ConfiguracaoGrupoTipoTarefa(Long idTipoTarefa) {
		super(idTipoTarefa);
		somenteAgrupador(Boolean.TRUE);
	}
	
	/**
	 * Cria uma nova instância para o tipo de tarefa informado.
	 * 
	 * @param idTipoTarefa ID da tarefa.
	 * @return nova instancia de ConfiguracaoGrupoTipoTarefa.
	 */
	public static ConfiguracaoGrupoTipoTarefa newInstance(Long idTipoTarefa) {
		ConfiguracaoGrupoTipoTarefa configuracao = new ConfiguracaoGrupoTipoTarefa(idTipoTarefa);
		return configuracao;
	}

	@Override
	public IListarTarefa listarTarefa() {
		if(listarTarefa != null){
			return listarTarefa;
		}
		
		List<Long> idsTiposTarefa = 
				Arrays.asList(getAgrupados()).stream().map(tipo -> tipo.getId()).collect(Collectors.toList());
		return new ListarTarefa(getPerfil(), idsTiposTarefa, getJsonView(), getTipoProcesso(), getOrdenacao());
	}

	@Override
	public IObterTarefa obterTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método obterTarefa.");
	}

	@Override
	public IFecharTarefa fecharTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método fecharTarefa.");
	}

	@Override
	public IAtribuirTarefa atribuirTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método obterTarefa.");
	}
	
	@Override
	public IRemoverAtribuirTarefa removerAtribuicaoTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método obterTarefa.");
	}

	@Override
	public ICriarTarefa criarTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método criarTarefa.");
	}

	@Override
	public IAtualizarTarefa atualizarTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método atualizarTarefa.");
	}
	
	@Override
	public ICancelarTarefa cancelarTarefa() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método cancelarTarefa.");
	}
	
	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * @param buscarTarefa the buscarTarefa to set
	 */
	public void setBuscarTarefa(IListarTarefa buscarTarefa) {
		this.listarTarefa = buscarTarefa;
	}

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.helper.IConfiguracaoProcessServer#setIdTarefa(java.lang.Long)
	 */
	@Override
	public void setIdTarefa(Long id) {
		
	}

	@Override
	public IConfiguracaoProcessServer comOrdenacao(Class<? extends Comparator<TarefaDTO>> classeComparacao) {
		try {
			this.ordenacao = classeComparacao.newInstance();
			return this;
		}
		catch (Exception e) {
			throw new ConfigurationException("Classe definida para ordenação não pode ser instanciada. "
					+ "Verifica se existe um construtor sem parâmetros e que o mesmo está acessível.");
		}
	}

	@Override
	public IConfiguracaoProcessServer comOrdenacao(AtributoOrdenacao... atributos) {
		this.ordenacao = new TarefaComparator(atributos);
		return this;
	}

	@Override
	public Comparator<TarefaDTO> getOrdenacao() {
		return this.ordenacao;
	}

	@Override
	public IConfiguracaoProcessServer iniciarProcesso() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método iniciarProcesso().");
	}

	@Override
	public boolean isIniciarProcesso() {
		throw new IllegalConfigurationException("Configuração definida como somente agrupadora não utiliza o método iniciarProcesso().");
	}

}
