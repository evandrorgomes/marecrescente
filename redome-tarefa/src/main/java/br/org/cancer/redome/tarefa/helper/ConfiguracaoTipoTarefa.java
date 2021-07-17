package br.org.cancer.redome.tarefa.helper;

/**
 * Classe responsável por configurar a utilização dos tipos de tarefa na aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
public class ConfiguracaoTipoTarefa extends AbstractConfiguracaoTarefa {

	private Long idTarefa;
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
	public IConfiguracaoProcessServer iniciarProcesso() {
		this.iniciarProcesso = Boolean.TRUE;
		return this;
	}

	@Override
	public boolean isIniciarProcesso() {
		return this.iniciarProcesso;
	}
}