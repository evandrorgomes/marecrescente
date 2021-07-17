package br.org.cancer.modred.service.integracao.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.org.cancer.modred.configuration.IntegracaoRedomeWebConfig;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.service.integracao.IIntegracaoEnriquecimentoRedomeWebService;
import br.org.cancer.modred.webservice.helper.TEnvelope;
import br.org.cancer.modred.webservice.helper.TRetorno;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade DoadorNacional.
 * 
 * @author ergomes
 * 
 */
@Service
@Transactional
public class IntegracaoEnriquecimentoRedomeWebService extends AbstractService<DoadorNacional, Long> implements IIntegracaoEnriquecimentoRedomeWebService {

	private static final Logger LOG = LoggerFactory.getLogger(IntegracaoEnriquecimentoRedomeWebService.class);
	
	@Autowired
	private IntegracaoRedomeWebConfig integracaoConf;
	
	@Override
	public IRepository<DoadorNacional, Long> getRepository() {
		return null;
	}	
	
	@Override
	public Long atualizarDoadorEnriquecimentoRedomeWeb(PedidoEnriquecimento pedido) throws ClientProtocolException, IOException {
		return atualizarEnriquecimentoRedomeWeb((DoadorNacional) pedido.getSolicitacao().getMatch().getDoador());
	}

	@Override
	public Long atualizarDoadorPedidoContatoRedomeWeb(PedidoContato pedido) throws ClientProtocolException, IOException {
		return atualizarEnriquecimentoRedomeWeb((DoadorNacional) pedido.getSolicitacao().getMatch().getDoador());
	}
	
	@Override
	public Long atualizarDoadorRedomeWeb(DoadorNacional doador) throws ClientProtocolException, IOException {
		return atualizarEnriquecimentoRedomeWeb(doador);
	}
	
	private Long atualizarEnriquecimentoRedomeWeb(DoadorNacional doador) throws ClientProtocolException, IOException {
		/* 
		 * @FIXME: Funcionamento fora de produção não está ocorrendo, por conta do acesso ao RedomeWeb no ambiente de DSV.
		 */  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(integracaoConf.obterHostRedomenetWsdl());
			
			StringEntity data = new StringEntity(montarXMLDoadorEnriquecimentoRedomeWeb(doador),
					ContentType.TEXT_XML);
			
			post.setEntity(data);
			
			CloseableHttpResponse response = httpclient.execute(post);
	        try {
	        	ObjectMapper xmlMapper = new XmlMapper();	        	
	        	final TEnvelope envelope = xmlMapper.readValue(EntityUtils.toString(response.getEntity()), TEnvelope.class);
	        	if (envelope.getBody() != null) {
	        		if (envelope.getBody().getAlteraDoadorRedomeResponse() != null && envelope.getBody().getAlteraDoadorRedomeResponse().getDados() != null) {
	        			final TRetorno retorno = xmlMapper.readValue(envelope.getBody().getAlteraDoadorRedomeResponse().getDados(), TRetorno.class);
			        	if (!retorno.getErros().isEmpty()) {
			        		retorno.getErros().stream().forEach(erro -> {		        			
			        			LOG.error(erro.toString());	
			        		});
			        		throw new BusinessException("erro.interno");
			        	}
			        	if (!retorno.getSucessos().isEmpty()) {
			        		return retorno.getSucessos().get(0).getId();
			        	}
	        		}
	        		if (envelope.getBody().getFault() != null) {
	        			
	        			System.out.println(envelope.getBody().getFault().getFaultcode() + " ==> " + envelope.getBody().getFault().getFaultstring() );
	        		}
	        		
	        	}
	        } 
	        finally {
	            response.close();
	        }
		} 
		finally {
			httpclient.close();
        }
		return null;
	}
	
	/**
	 * FIXME: Inserir município de Naturalidade após inclusão do compo na importação de doador/Integração.
	 * Código Município Naturalidade deve ser incluso após o campo fumante. 
	 */		
	private String montarXMLDoadorEnriquecimentoRedomeWeb(DoadorNacional doador) { 		
										
		StringBuffer xml = new StringBuffer("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
				.append("xmlns:red=\""+integracaoConf.getHost().toString()+"\"> ")
				.append("<soap:Body> ")
				.append("<red:alteraDoadorRedome> ")
				.append("<dados> ")
				.append("<![CDATA[ ")
				.append("<tns:doadorAlterado tns:login=\"sismatch\" tns:senha=\"!master!\"") 
				.append(" xmlns:tns=\""+integracaoConf.obterHostDoadorAlterado()+"\" ")
				.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" )
				.append(" xsi:schemaLocation=\""+integracaoConf.obterHostDoadorAlterado()+".xsd \"> ")			
												       
		        .append("		<tns:dmr>" + doador.getDmr()+ "</tns:dmr> ") 		        
		        .append("		<tns:nome>" + doador.getNome() +  "</tns:nome> ") 
		        .append("		<tns:nomeMae>" + doador.getNomeMae() + "</tns:nomeMae> ")
		        .append("		<tns:nomePai>" + doador.getNomePai() + "</tns:nomePai> ")		    
		        .append("		<tns:campanha>" + recuperarCampanha(doador) + "</tns:campanha> ")		        
		        .append("		<tns:codigoHemocentro>" + doador.getNumeroHemocentro() + "</tns:codigoHemocentro> ") 		     		        
		        .append("		<tns:cpf>" + recuperarCpf(doador) + "</tns:cpf> ")
		        .append("		<tns:dataColeta>" + doador.getDataColeta().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "</tns:dataColeta> ") 		        
		        .append("		<tns:dataNascimento>" + doador.getDataNascimento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "</tns:dataNascimento> ") 
		        .append("		<tns:estadoCivil>" + doador.getEstadoCivil().getId() + "</tns:estadoCivil> ")  
		        .append("		<tns:fumante>" + doador.getFumanteRedomeWeb() + "</tns:fumante> ") 
		        .append("		<tns:municipio>" + doador.getNaturalidade().getCodigoIbge() + "</tns:municipio> ") 		    
		        .append("		<tns:nacionalidade>" + doador.getPais().getCodigoPaisRedomeweb() + "</tns:nacionalidade> ") 		        
		        .append("		<tns:raca>" + doador.getRaca().getIdRedomeweb() + "</tns:raca> ") 			        			       		        		       		        
		        .append("		" + recuperarEtnia(doador) +  "" )
		        .append("		<tns:rg>" + doador.getRg() + "</tns:rg> ") 
		        .append("		<tns:sexo>" + doador.getSexo() + "</tns:sexo> ")
		        .append("		<tns:abo>" + recuperarAboSePossivel(doador) + "</tns:abo> ")
		        .append("		<tns:rh>" + recuperarFatorRhSePossivel(doador) + "</tns:rh> ")  		        
        		.append("		<tns:altura>" + recuperarAltura(doador) + "</tns:altura> ")
        		.append("		<tns:peso>" + recuperarPeso(doador) + "</tns:peso> ");				
						
		for (int i = 0; i< doador.getEmailsContato().size(); i++) {
			xml = xml.append("		<tns:emails>");
		    xml = xml.append("			<tns:email>" + doador.getEmailsContato().get(i).getEmail() + "</tns:email> "); 
		    xml = xml.append("		</tns:emails> "); 			
		}
		
		for (int i = 0; i< doador.getEnderecosContato().size(); i++) {
			xml = xml.append("		<tns:enderecos>");
			xml = xml.append("			<tns:estado>" + doador.getEnderecosContato().get(i).getMunicipio().getUf() + "</tns:estado> "); 
			xml = xml.append("			<tns:bairro>" + doador.getEnderecosContato().get(i).getBairro() + "</tns:bairro> "); 
			xml = xml.append("			<tns:cep>" + recuperarCep(doador.getEnderecosContato().get(i).getCep()) + "</tns:cep> ");					      			
	        xml = xml.append("			<tns:complemento>" + doador.getEnderecosContato().get(i).getComplemento() + "</tns:complemento> "); 
	        xml = xml.append("		    <tns:logradouro>" + doador.getEnderecosContato().get(i).getNomeLogradouro() + "</tns:logradouro> ");
	        xml = xml.append("		    <tns:municipio>" + doador.getEnderecosContato().get(i).getMunicipio().getCodigoIbge() + "</tns:municipio> ");
	        xml = xml.append("		    <tns:numero>" + doador.getEnderecosContato().get(i).getNumero() + "</tns:numero> ");
	        
	        if (doador.getEnderecosContato().get(i).isPrincipal()){
	        	xml = xml.append("			<tns:tipo>" + "R" + "</tns:tipo> ");	
	        }
	        else {
	        	xml = xml.append("			<tns:tipo>" + "C" + "</tns:tipo> ");
	        }	        	      
	        xml = xml.append("		</tns:enderecos> ");
		}
						
		for (int i = 0; i< doador.getContatosTelefonicos().size(); i++) {
			xml = xml.append("		<tns:telefones> ");
			xml = xml.append("			<tns:contato>" + doador.getContatosTelefonicos().get(i).getNome() + "</tns:contato> "); 
			xml = xml.append("			<tns:ddd>" + doador.getContatosTelefonicos().get(i).getCodArea() + "</tns:ddd> ");
			if(doador.getContatosTelefonicos().get(i).getComplemento() != null) {
				xml = xml.append("		    <tns:ramal>" + doador.getContatosTelefonicos().get(i).getComplemento() + "</tns:ramal> ");
			}
			xml = xml.append("			<tns:numeroTelefone>" + doador.getContatosTelefonicos().get(i).getNumero() + "</tns:numeroTelefone> "); 
			xml = xml.append("		</tns:telefones> ");
		}
				
		xml = xml.append("</tns:doadorAlterado> ")
				.append("]]> ")
				.append("</dados> ")			
				.append("</red:alteraDoadorRedome> ")
				.append("</soap:Body> ")
				.append("</soap:Envelope> ");
		
		return xml.toString();
	}

	private PrintStream append(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private Long recuperarCampanha(DoadorNacional doador) {			
		if (doador.getCampanha() == null){				
			return 0L;
		} 		
		return doador.getCampanha();	
	
	}
	
	/**
	 * Formatar cep para atualizar o REDOMEWEB.
	 * 
	 * @param cepEndereco 
	 * @return string.
	 */	
	public String recuperarCep(String cepEndereco){ 			
		if (StringUtils.isBlank(cepEndereco)) { 
			return "";
		}	
		Object[] params = new Object[]{cepEndereco.substring(0,5), cepEndereco.substring(5,8)};
		String cep = MessageFormat.format("{0}-{1}", params);	    
    
		return cep;    
	}	
	
	/**
	 * Formatar cpf para atualizar o REDOMEWEB.
	 * 
	 * @param doador 
	 * @return string.
	 */
	public String recuperarCpf(DoadorNacional doador){ 			
		if (!StringUtils.isBlank(doador.getCpf())) {    
			Object[] params = new Object[]{doador.getCpf().substring(0,3), doador.getCpf().substring(3,6), doador.getCpf().substring(6,9) , doador.getCpf().substring(9,11)};
			String cpf = MessageFormat.format("{0}.{1}.{2}-{3}", params);	    
			return cpf;
		}	
		return "";	
	}
	
	private String recuperarEtnia(DoadorNacional doador) {
		if (doador.getEtnia() == null ){
			return  "<tns:etnia xsi:nil=\"true\"/>";					
		}			
		return "<tns:etnia>" + doador.getEtnia().getId() + "</tns:etnia>";
	}	
	
	private String recuperarAboSePossivel(DoadorNacional doador) {
		if (doador.getAbo() != null || doador.getAbo().equals("") ) {
			if (doador.getAbo().length() > 2L) { 
				return doador.getAbo().substring(0, 2);
			}	
			else {
				return doador.getAbo().substring(0, 1);
			}	
		}
		return "";
	}	
	
	private String recuperarFatorRhSePossivel(DoadorNacional doador) {
		if (doador.getAbo() != null || doador.getAbo().equals("") ) {
			if (doador.getAbo().length() > 2L) {				
				return doador.getAbo().substring(2, 3);
			}
			else{
				return doador.getAbo().substring(1, 2);
			}		
		}
		return "";
	}	
	
	private String recuperarAltura(DoadorNacional doador) {
		if (!StringUtils.isBlank(doador.getAltura().toString())){
			return StringUtils.remove(doador.getAltura().toString(), ".");					
		}				
		return "";
	}		
	
	private String recuperarPeso(DoadorNacional doador) {
		if (!StringUtils.isBlank(doador.getPeso().toString())){
			return StringUtils.remove(doador.getPeso().toString(), ".");					
		}			
		return "";
	}

}
