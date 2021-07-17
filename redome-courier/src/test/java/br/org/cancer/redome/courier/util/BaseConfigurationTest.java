package br.org.cancer.redome.courier.util;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import java.util.Map;
import java.util.UUID;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.UrlPattern;

import br.org.cancer.redome.courier.configuraion.EmptyStringDeserializer;
import br.org.cancer.redome.courier.configuraion.RedomeCourierApplication;
import br.org.cancer.redome.courier.util.authorization.MakeAuthotization;

@RunWith(SpringRunner.class)
@TestPropertySource(locations= {"classpath:application.yml", "classpath:application-test.yml"})
@ActiveProfiles("test")
@SpringBootTest(classes = {RedomeCourierApplication.class}, webEnvironment= WebEnvironment.RANDOM_PORT)
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
	
	protected void makeStubForPatch(String url, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.patch(UrlPattern.fromOneOf(url, null, null, null))
				.withId(UUID.randomUUID())
				.willReturn(response));
	}
	
	protected void makeStubForPatch(String url, ContentPattern<?> bodyPattern,  ResponseDefinitionBuilder response) {		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.patch(UrlPattern.fromOneOf(url, null, null, null))
				.withId(UUID.randomUUID())
				.withRequestBody(bodyPattern)
				.willReturn(response));
	}
	
}
