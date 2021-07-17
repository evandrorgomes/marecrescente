package br.org.cancer.modred.service.integracao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe DoadorNacional.
 * 
 * 
 * @author ergomes
 */
public interface IIntegracaoEnriquecimentoRedomeWebService extends IService<DoadorNacional, Long> {

	Long atualizarDoadorEnriquecimentoRedomeWeb(PedidoEnriquecimento pedido) throws ClientProtocolException, IOException;
	
	Long atualizarDoadorPedidoContatoRedomeWeb(PedidoContato pedido) throws ClientProtocolException, IOException;

	Long atualizarDoadorRedomeWeb(DoadorNacional doador) throws ClientProtocolException, IOException;
	
}