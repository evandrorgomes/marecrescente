package br.org.cancer.modred.notificacao;

/**
 * Classe responsável por configurar a utilização das categorias de notificacao na aplicação.
 * 
 * @author brunosousa
 *
 */
public class ConfiguracaoCategoriaNotificacao extends AbstractConfiguracaoCategoriaNotificacao {

	private ILerNotificacao lerNotificacao;
	private IObterNotificacao obterNotificacao;
	private IListarNotificacao listarNotificacao;
	private ICriarNotificacao criarNotificacao;
	private IContarNotificacao contarNotificacao;
	
	/**
	 * Construtor preparado para receber o ID da categoria da notificacao, obrigatoriamente.
	 * 
	 * @param idCategoriaNotificacao
	 */
	protected ConfiguracaoCategoriaNotificacao(Long idCategoriaNotificacao) {
		super(idCategoriaNotificacao);
	}
	
	/**
	 * Cria uma nova instância para a categoria da notificação informada.
	 * 
	 * @param idCategoriaNotificacao ID da categoria da notificação.
	 * @return nova instancia de ConfiguracaoCategoriaNotificacao
	 */
	public static ConfiguracaoCategoriaNotificacao newInstance(Long idCategoriaNotificacao) {
		return new ConfiguracaoCategoriaNotificacao(idCategoriaNotificacao);
	}

	@Override
	public IListarNotificacao listar() {
		if (this.listarNotificacao != null) {
			return this.listarNotificacao;
		}
		return new ListarNotificacao(getIdCategoriaNotificacao());
	}

	@Override
	public IObterNotificacao obter() {
		if (this.obterNotificacao != null) {
			return this.obterNotificacao;
		}
		return new ObterNotificacao(getIdCategoriaNotificacao());
	}

	@Override
	public ILerNotificacao ler() {
		if (this.lerNotificacao != null) {
			return this.lerNotificacao;
		}
		return new LerNotificacao(getIdCategoriaNotificacao());
	}

	@Override
	public ICriarNotificacao criar() {
		if (this.criarNotificacao != null) {
			return this.criarNotificacao;
		}
		return new CriarNotificacao(getIdCategoriaNotificacao(), getParceiro());
	}
	
	@Override
	public IContarNotificacao contar() {
		if (this.contarNotificacao != null) {
			return this.contarNotificacao;
		}
		return new ContarNotificacao(getIdCategoriaNotificacao());
	}


	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param lerNotificacao the lerNotificacao to set
	 */
	public void setLerNotificacao(ILerNotificacao lerNotificacao) {
		this.lerNotificacao = lerNotificacao;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param obterNotificacao the obterNotificacao to set
	 */
	public void setObterNotificacao(IObterNotificacao obterNotificacao) {
		this.obterNotificacao = obterNotificacao;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param listarNotificacao the listarNotificacao to set
	 */
	public void setListarNotificacao(IListarNotificacao listarNotificacao) {
		this.listarNotificacao = listarNotificacao;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param criarNotificacao the criarNotificacao to set
	 */
	public void setCriarNotificacao(ICriarNotificacao criarNotificacao) {
		this.criarNotificacao = criarNotificacao;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * 
	 * @param contarNotificacao the contarNotificacao to set
	 */
	public void setContarNotificacao(IContarNotificacao contarNotificacao) {
		this.contarNotificacao = contarNotificacao;
	}
	

	

}
