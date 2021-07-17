package br.org.cancer.modred.notificacao;

/**
 * Interface que define a estrutura da configuração necessária para a
 * realização de requisições genéricas a lista de notificações.
 * 
 * @author brunosousa
 *
 */
public abstract class AbstractConfiguracaoCategoriaNotificacao implements IConfiguracaoCategoriaNotificacao {

	private Long idCategoriaNotificacao;
	private Class<?> parceiro;	
	
	/**
	 * Coonstrutor para padronizar as extensões de configuração de notificação, forçando
	 * que seja passado um ID da categoria da notificacao para cada instância.
	 * 
	 * @param idCategoriaNotificacao ID da categoria da notificacao associado.
	 */
	protected AbstractConfiguracaoCategoriaNotificacao(Long idCategoriaNotificacao) {
		super();
		this.idCategoriaNotificacao = idCategoriaNotificacao;
	}
		
	/**
	 * Retorna o ID da categoria da notificação.
	 * 
	 * @return the idCategoriaNotificacao
	 */
	public Long getIdCategoriaNotificacao() {
		return idCategoriaNotificacao;
	}

	@Override
	public Class<?> getParceiro() {
		return parceiro;
	}

	@Override
	public IConfiguracaoCategoriaNotificacao comParceiro(Class<?> parceiro) {
		this.parceiro = parceiro;
		return this;
	}

	@Override
	public abstract IListarNotificacao listar();

	@Override
	public abstract IObterNotificacao obter();

	@Override
	public abstract ILerNotificacao ler();

	@Override
	public abstract ICriarNotificacao criar();
	
	@Override
	public abstract IContarNotificacao contar();

}
