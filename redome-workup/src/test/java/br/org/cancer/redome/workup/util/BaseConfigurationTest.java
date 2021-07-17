package br.org.cancer.redome.workup.util;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import br.org.cancer.redome.workup.configuration.EmptyStringDeserializer;
import br.org.cancer.redome.workup.configuration.RedomeWorkupApplication;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.util.authorization.MakeAuthotization;

@RunWith(SpringRunner.class)
@TestPropertySource(locations= {"classpath:application.yml", "classpath:application-test.yml"})
@ActiveProfiles("test")
@SpringBootTest(classes = {RedomeWorkupApplication.class}, webEnvironment= WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public abstract class BaseConfigurationTest {
	
	public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
	
	@Autowired
	protected MockMvc mockMvc;
		
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(1111);
	
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	
	
	public static MakeAuthotization makeAuthotization = new MakeAuthotization();
	
	
	@Before
	public void setupAll() {
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlPathMatching("/redome-auth/oauth/check_token.json"))
				.withHeader("Content-Type", matching("application/x-www-form-urlencoded;charset=UTF-8"))
				.willReturn(okForContentType("application/json;charset=UTF-8", makeAuthotization.getJson())));
	
	}
	
	@AfterClass
	public static void afterAll() {
		makeAuthotization.clear();
	}
	

	public static ObjectMapper getObjectMapper() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		builder.deserializers(new EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE);
		builder.serializers(LocalDateSerializer.INSTANCE);

		return builder.build();
	}
	

	protected void makeStubForGet(String url, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(url)
				.withId(UUID.randomUUID())
				.willReturn(response));
	}
	
	protected void makeStubForGet(String url, Map<String, StringValuePattern> queryParams, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(url)
				.withQueryParams(queryParams)
				.withId(UUID.randomUUID())
				.willReturn(response));
	}
	
	protected void makeStubForPost(String url, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(url)
				.withId(UUID.randomUUID())
				.willReturn(response));
	}
	
	protected void makeStubForPost(String url, ContentPattern<?> bodyPattern,  ResponseDefinitionBuilder response) {		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(url)
				.withId(UUID.randomUUID())
				.withRequestBody(bodyPattern)
				.willReturn(response));
	}
	
	protected void makeStubForPut(String url, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.put(url)
				.withId(UUID.randomUUID())
				.willReturn(response));
	}
	
	protected void makeStubForPut(String url, ContentPattern<?> bodyPattern, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.put(url)
				.withId(UUID.randomUUID())
				.withRequestBody(bodyPattern)
				.willReturn(response));
	}
	
	protected String montarRetornoListarTarfaJson(List<TarefaDTO> tarefas) {
		
		int totalPages = 0;
		int totalElements = 0;
		String tarefasJson = "";
		if (CollectionUtils.isNotEmpty(tarefas)) {
			totalElements = tarefas.size();
			totalPages = 1;
			tarefasJson = tarefas.stream().map(tarefa -> {
				try {
					return BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa);
				} catch (JsonProcessingException e) {
					throw new BusinessException(e.getMessage());					
				}
			})
			.collect(Collectors.joining(","));
		}
		
		
		
		String retorno = "{\n" + 
				"    \"pageable\": {\n" + 
				"        \"sort\": {\n" + 
				"            \"sorted\": false,\n" + 
				"            \"unsorted\": true,\n" + 
				"            \"empty\": true\n" + 
				"        },\n" + 
				"        \"pageSize\": 1,\n" + 
				"        \"pageNumber\": 0,\n" + 
				"        \"offset\": 0,\n" + 
				"        \"unpaged\": false,\n" + 
				"        \"paged\": true\n" + 
				"    },\n" + 
				"    \"totalElements\": " + totalElements + ",\n" + 
				"    \"totalPages\": " + totalPages + ",\n" + 
				"    \"last\": true,\n" + 
				"    \"first\": true,\n" + 
				"    \"sort\": {\n" + 
				"        \"sorted\": false,\n" + 
				"        \"unsorted\": true,\n" + 
				"        \"empty\": true\n" + 
				"    },\n" + 
				"    \"size\": 1,\n" + 
				"    \"number\": 0,\n" + 
				"    \"numberOfElements\": 0,\n" + 
				"    \"empty\": true,\n" + 
				"    \"content\": [\n" +
				"        " + tarefasJson + "\n" +
				"    ]\n" +
				"}";
		
		return retorno;
	}

	

	

}
