package br.org.cancer.redome.tarefa.helper;

import br.org.cancer.redome.tarefa.exception.BusinessException;

/**
 * @author Pizão
 * 
 * Define o comportamento para a configuração do tipo de tarefa definido como somente agrupador.
 * Este tipo é utilizado apenas para agrupar outros tipos de tarefa somente.
 *
 */
public class ConfiguracaoGrupoTipoTarefa extends AbstractConfiguracaoTarefa{
		
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

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.helper.IConfiguracaoProcessServer#setIdTarefa(java.lang.Long)
	 */
	@Override
	public void setIdTarefa(Long id) {
		
	}

	@Override
	public IConfiguracaoProcessServer iniciarProcesso() {
		throw new BusinessException("Configuração definida como somente agrupadora não utiliza o método iniciarProcesso().");
	}

	@Override
	public boolean isIniciarProcesso() {
		throw new BusinessException("Configuração definida como somente agrupadora não utiliza o método iniciarProcesso().");
	}

}
