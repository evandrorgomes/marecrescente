package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.service.pagamento.IConfiguracaoTipoServico;
import br.org.cancer.modred.service.pagamento.impl.ConfiguracaoTipoServico;

/**
 * Enum para representar os tipos de serviçoes previstas para pagamentos da plataforma redome.
 * 
 * @author bruno.sousa
 *
 */
public enum TiposServico {
	
	AMOSTRA_CT(1L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
		
	},
	PEDIDO_EXAME_CT(2L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
		
	},
	AMOSTRA_IDM(3L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoIdm.class);
		}
		
	},
	PEDIDO_IDM(4L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoIdm.class);
		}
		
	},
	
	PEDIDO_FASE2_HR_A(8L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_B(9L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_C(10L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DRB1(11L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DQB1(12L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DQA1(14L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DPA1(15L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DRB3(16L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DRB4(17L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DRB5(18L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	},
	PEDIDO_FASE2_HR_DPB1(20L) {
		
		@Override
		protected IConfiguracaoTipoServico buildConfiguracao() {
			return ConfiguracaoTipoServico.newInstance(this.getId())
				.relacionadoCom(PedidoExame.class);
		}
	};
	
	
	private Long id;
	private IConfiguracaoTipoServico configuracao;
	private IConfiguracaoTipoServico configuracaoDefault;

	/**
	 * Construtor.
	 * 
	 * @param id do tipo
	 */
	TiposServico(Long id) {
		this.id = id;
		this.configuracaoDefault = ConfiguracaoTipoServico.newInstance(id);
		this.configuracao = buildConfiguracao();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the entidade
	 */
	public Class<?> getEntidade() {
		return configuracao.getEntidade();
	}

	/**
	 * Utilizado para criar a configuração, de acordo com o tipo de serviço.
	 * Este método deve ser implementado, a cada novo tipo definido na configuração.
	 * Caso não seja, uma configuração default será passada.
	 * 
	 * @return as instruções para configuração da requisição ao process server.
	 */
	protected IConfiguracaoTipoServico buildConfiguracao() {
		return configuracaoDefault;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return TiposTarefa - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static TiposServico valueOf(Long value) {
		if (value != null) {
			TiposServico[] values = TiposServico.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getId().equals(value)) {
					return values[i];
				}
			}
		}
		return null;
	}

	/**
	 * Método para verificar se o id está dentro do range previsto.
	 * 
	 * @return boolean - returna <b>true</b> se o id corresponde a um valor válido da enum, caso contrário, retorna <b>false</b>.
	 */
	public boolean validate() {

		TiposServico[] values = TiposServico.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtém a configuração definida para o tipo de srviço informado.
	 * Esta configuração define quais serão as informações a serem utilizadas
	 * ao criar, obter o pagamento deste tipo.
	 * 
	 * @return configuração definida para o tipo.
	 */
	public IConfiguracaoTipoServico getConfiguracao() {
		return configuracao;
	}

	public static boolean compararTipo(Long id) {
		for (TiposServico tipo : values()) {
			if (tipo.equals(id)) {
				return true;
			}else {
				return false;
			}
		}
		throw new BusinessException("erro.interno");
	}
	
}