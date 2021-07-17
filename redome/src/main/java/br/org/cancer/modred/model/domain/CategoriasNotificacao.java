package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.notificacao.ConfiguracaoCategoriaNotificacao;
import br.org.cancer.modred.notificacao.ContarNotificacao;
import br.org.cancer.modred.notificacao.IConfiguracaoCategoriaNotificacao;
import br.org.cancer.modred.notificacao.IContarNotificacao;
import br.org.cancer.modred.notificacao.ILerNotificacao;
import br.org.cancer.modred.notificacao.IListarNotificacao;
import br.org.cancer.modred.notificacao.IObterNotificacao;
import br.org.cancer.modred.notificacao.LerNotificacao;
import br.org.cancer.modred.notificacao.ListarNotificacao;
import br.org.cancer.modred.notificacao.ObterNotificacao;

/**
 * Enum para categorias de notificacao.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum CategoriasNotificacao {

	EXAME(1L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId());
		}
	},
	PEDIDO_EXAME(2L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId());
		}
	},
	PRESCRICAO(3L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	},
	AVALIACAO_PACIENTE(4L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	},
	TRANSFERENCIA_PACIENTE(5L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	},
	AVALIACAO_PEDIDO_COLETA(6L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	},
	MATCH(7L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId());
		}
	},
	RECEBIMENTO_COLETA(8L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId());
		}
	},
	PEDIDO_WORKUP(9L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId());
		}
	}, 
	AVALIACAO_NOVA_BUSCA(10L){
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId());
		}
	},
	PEDIDO_COLETA(11L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	},
	AVALIACAO_RESULTADO_WORKUP(12L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	},
	MATCH_DISPONIBILIZADO(13L) {
		@Override
		protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
			return ConfiguracaoCategoriaNotificacao.newInstance(this.getId())
					.comParceiro(CentroTransplante.class);
		}
	};

	private Long id;
	private IConfiguracaoCategoriaNotificacao configuracao;
	private IConfiguracaoCategoriaNotificacao configuracaoDefault;

	/**
	 * Construtor recebendo como parametro por id.
	 * 
	 * @param id identificador da categoria
	 */
	CategoriasNotificacao(Long id) {
		this.id = id;
		this.configuracaoDefault = ConfiguracaoCategoriaNotificacao.newInstance(id);
		this.configuracao = buildConfiguracao();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Utilizado para criar a configuração, de acordo com a categoria da notificação.
	 * Este método deve ser implementado, a cada novo tipo definido na configuração.
	 * Caso não seja, uma configuração default será passada.
	 * 
	 * @return as instruções para configuração da requisição ao process server.
	 */
	protected IConfiguracaoCategoriaNotificacao buildConfiguracao() {
		return configuracaoDefault;
	}
	
	/**
	 * Obtém a configuração definida para o tipo de tarefa informado.
	 * Esta configuração define quais serão as informações a serem utilizadas
	 * no process server ao criar, listar, atribuir, fechar e cancelar a tarefa deste tipo.
	 * 
	 * @return configuração definida para o tipo.
	 */
	public IConfiguracaoCategoriaNotificacao getConfiguracao() {
		return this.configuracao;
	}

	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param codigo código a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static CategoriasNotificacao get(Long codigo) {
		for (CategoriasNotificacao status : values()) {
			if (status.id.equals(codigo)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}
	
	private static IListarNotificacao LISTARNOTIFICACAO;
	private static IObterNotificacao OBTERNOTIFICACAO;
	private static ILerNotificacao LERNOTIFICACAO;
	private static IContarNotificacao CONTARNOTIFICACAO;
	
	/**
	 * Método para listar notificações independente da categoria.
	 * 
	 * @return Método para listar
	 */
	public static IListarNotificacao listar() {
		if (LISTARNOTIFICACAO != null) {
			return LISTARNOTIFICACAO;
		}
		return new ListarNotificacao(null);
	}
	
	/**
	 * Método para obter notificação independente da categoria.
	 * 
	 * @return Método para obter
	 */
	public static IObterNotificacao obter() {
		if (OBTERNOTIFICACAO != null) {
			return OBTERNOTIFICACAO;
		}
		return new ObterNotificacao(null);
	}
	
	/**
	 * Método para marcar como lida uma notificação independente da categoria.
	 * 
	 * @return Método para marcar como lida.
	 */
	public static ILerNotificacao ler() {
		if (LERNOTIFICACAO != null) {
			return LERNOTIFICACAO;
		}
		return new LerNotificacao(null);
	}

	/**
	 * Método para contar as notificações independente da categoria.
	 * 
	 * @return Método para contar.
	 */
	public static IContarNotificacao contar() {
		if (CONTARNOTIFICACAO != null) {
			return CONTARNOTIFICACAO;
		}
		return new ContarNotificacao(null);
	}

	/**
	 * @param listarNotificacao the listarNotificacao to set
	 */
	public static void setListarNotificacao(IListarNotificacao listarNotificacao) {
		CategoriasNotificacao.LISTARNOTIFICACAO = listarNotificacao;
	}

	/**
	 * @param obterNotificacao the obterNotificacao to set
	 */
	public static void setObterNotificacao(IObterNotificacao obterNotificacao) {
		CategoriasNotificacao.OBTERNOTIFICACAO = obterNotificacao;
	}

	/**
	 * @param lerNotificacao the lerNotificacao to set
	 */
	public static void setLerNotificacao(ILerNotificacao lerNotificacao) {
		CategoriasNotificacao.LERNOTIFICACAO = lerNotificacao;
	}

	/**
	 * @param contarNotificacao the contarNotificacao to set
	 */
	public static void setContarNotificacao(IContarNotificacao contarNotificacao) {
		CategoriasNotificacao.CONTARNOTIFICACAO = contarNotificacao;
	}
	
	
}
