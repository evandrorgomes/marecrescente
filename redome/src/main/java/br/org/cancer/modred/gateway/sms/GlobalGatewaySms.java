package br.org.cancer.modred.gateway.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.org.cancer.modred.configuration.EmptyStringDeserializer;

/**
 * Classe responsável pela implementação da api da empresa Global.
 * 
 * @author brunosousa
 *
 */
public class GlobalGatewaySms implements IGatewaySms {

	private static final String USER = "redome";
	private static final String PSW = "redome1";
	private static final String TIPO = "normal";
	private static final String SAIDA = "long";
	private static final String CARTEIRA = "REDOME";
	private static final String HOST = "http://painelomni.com.br/Api/api";

	private static final String KEY_ID_ACOMPANHAMENTO = "Id de Acompanhamento";
	private static final String KEY_STATUS = "Status";
	private static final String KEY_DATA_ENVIO = "Data do Envio";
	

	@Override
	public IRetornoSms enviar(String numeroTelefone, String mensagem) throws IOException, URISyntaxException {
		return enviarMensagem(numeroTelefone, mensagem);
	}

	@Override
	public IRetornoSms status(String identificador) throws IOException, URISyntaxException {
		return obterStatus(identificador);
	}

	private IRetornoSms enviarMensagem(String numeroTelefone, String mensagem) throws IOException, URISyntaxException {

		try (
			CloseableHttpClient httpclient = HttpClients.createDefault();
		) {
			List<NameValuePair> postParameters = new ArrayList<>();
			postParameters.add(new BasicNameValuePair("envio", null));
			postParameters.add(new BasicNameValuePair("user", USER));
			postParameters.add(new BasicNameValuePair("psw", PSW));
			postParameters.add(new BasicNameValuePair("Tipo", TIPO));
			postParameters.add(new BasicNameValuePair("saida", SAIDA));
			postParameters.add(new BasicNameValuePair("carteira", CARTEIRA));
			postParameters.add(new BasicNameValuePair("tel", numeroTelefone));
			postParameters.add(new BasicNameValuePair("msg", mensagem));

			URIBuilder uriBuilder = new URIBuilder(HOST);
			uriBuilder.addParameters(postParameters);

			HttpPost post = new HttpPost(uriBuilder.build());

			CloseableHttpResponse response = httpclient.execute(post);
			String msgRetorno = EntityUtils.toString(response.getEntity());
			List<Map<String, String>> retorno = getObjectMapper().readValue(msgRetorno,
					new TypeReference<List<Map<String, String>>>() {});
			if (retorno != null && !retorno.isEmpty() && retorno.get(0).containsKey(KEY_ID_ACOMPANHAMENTO)) {
				return new GlobalRetornoSms(retorno.get(0).get(KEY_ID_ACOMPANHAMENTO), StatusSms.ENTREGUE, null);
			}
		}

		return null;
	}

	private IRetornoSms obterStatus(String identificador) throws IOException, URISyntaxException  {

		try (
			CloseableHttpClient httpclient = HttpClients.createDefault();
		) {
			List<NameValuePair> postParameters = new ArrayList<>();
			postParameters.add(new BasicNameValuePair("status", null));
			postParameters.add(new BasicNameValuePair("id_acompanhamento", identificador));
			
			URIBuilder uriBuilder = new URIBuilder(HOST);
			uriBuilder.addParameters(postParameters);

			HttpGet get = new HttpGet(uriBuilder.build());

			CloseableHttpResponse response = httpclient.execute(get);
			String msgRetorno = EntityUtils.toString(response.getEntity());
			List<Map<String, String>> retorno = getObjectMapper().readValue(msgRetorno,
					new TypeReference<List<Map<String, String>>>() {
					});
			if (retorno != null && !retorno.isEmpty() && retorno.get(0).containsKey(KEY_STATUS)) {
				StatusSms status = Enum.valueOf(StatusSms.class, retorno.get(0).get(KEY_STATUS).toUpperCase());
				LocalDateTime dataEnvio = LocalDateTime.parse(retorno.get(0).get(KEY_DATA_ENVIO), new DateTimeFormatterFactory("dd-MM-yyyy  HH:mm").createDateTimeFormatter());
				return new GlobalRetornoSms(identificador, status, dataEnvio);
			}
		}

		return null;

	}

	private ObjectMapper getObjectMapper() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		builder.deserializers(new EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE);
		builder.serializers(LocalDateSerializer.INSTANCE);

		return builder.build();
	}

}
