package br.org.cancer.redome.tarefa.configuration;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("pt", "BR"));
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter #extendMessageConverters(java.util.List)
	 */
	@Override
	public void extendMessageConverters(java.util.List<HttpMessageConverter<?>> converters) {
		// removendo o jackson2 messagem converter default configurado pelo
		// spring
		converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
		// e substituimos pelo customizado
		converters.add(getCustomMappingJackson2HttpMessageConverter());
	}

	/**
	 * Configura um message converter customizado para json.
	 * 
	 * @return MappingJackson2HttpMessageConverter
	 */
	@Bean(name = "customMappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter getCustomMappingJackson2HttpMessageConverter() {

		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		builder.indentOutput(true);

		builder.dateFormat(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"));
		builder.deserializers(new EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE,
				LocalDateTimeDeserializer.INSTANCE  );
		builder.serializers(LocalDateSerializer.INSTANCE, LocalDateTimeSerializer.INSTANCE);

		// não inclui propriedades nulas na serialização
		builder.serializationInclusion(Include.NON_NULL);
		builder.serializationInclusion(Include.NON_EMPTY);

		// desabilita a falha ao encontrar propriedade desconhecidas ou vazias
		builder.failOnUnknownProperties(false);
		builder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
				DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(
				builder.build());
		messageConverter.setDefaultCharset(Charset.forName("UTF-8"));

		return messageConverter;
	}
	
	
}
