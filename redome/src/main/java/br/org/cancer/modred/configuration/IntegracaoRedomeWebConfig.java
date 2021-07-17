package br.org.cancer.modred.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * Classe que retorna uma instance com a integração do redome web configurado.
 * 
 * @author ergomes
 *
 */
@Configuration
@ApplicationScope
public class IntegracaoRedomeWebConfig {

	private static final String SCHEMAS = "/schemas";
	private static final String REDOMENET_WSDL = "/redomenet?wsdl";
	private static final String SOLICITACAO_EXAME = "/solicitarExame";
	private static final String SOLICITACAO_AMOSTRA = "/solicitarAmostra";
	private static final String CANCELANDO_AMOSTRA = "/cancelarAmostra";
	
	private static final String DOADOR_ALTERADO = "/doadorAlterado";
	
	@Value("${host.redomeweb}")
	private String host;
		
	public IntegracaoRedomeWebConfig() {		
	}
	
	public String obterHostSolicitarExame() {
		return this.getHost() + SCHEMAS + SOLICITACAO_EXAME;
	}

	public String obterHostSolicitarAmostra() {
		return this.getHost() + SCHEMAS + SOLICITACAO_AMOSTRA;
	}

	public String obterHostCancelarAmostra() {
		return this.getHost() + SCHEMAS + CANCELANDO_AMOSTRA;
	}

	public String obterHostDoadorAlterado() {
		return this.getHost() + SCHEMAS + DOADOR_ALTERADO;
	}
	
	public String obterHostRedomenetWsdl() {
		return this.getHost() + REDOMENET_WSDL;
	}
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IntegracaoRedomeWebConfig [obterHostSolicitarExame()=" + obterHostSolicitarExame()
				+ ", obterHostSolicitarAmostra()=" + obterHostSolicitarAmostra() + ", obterHostCancelarAmostra()="
				+ obterHostCancelarAmostra() + ", obterHostDoadorAlterado()=" + obterHostDoadorAlterado()
				+ ", obterHostRedomenetWsdl()=" + obterHostRedomenetWsdl() + ", getHost()=" + getHost() + "]";
	}

	
}