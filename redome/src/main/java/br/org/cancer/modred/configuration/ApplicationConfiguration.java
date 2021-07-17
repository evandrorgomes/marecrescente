package br.org.cancer.modred.configuration;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.ImmutableList;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.BluemixStorageService;
import br.org.cancer.modred.service.impl.invocation.LogAspect;

/**
 * Classe de configuração do spring.
 * 
 * @author Cintia Oliveira
 *
 */
@Configuration
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@EnableOAuth2Sso
@EnableWebMvc
@EnableCaching
//@EnableAsync
@EnableAspectJAutoProxy()
//@EnableFeignClients(basePackages = {"br.org.cancer.modred.feign.client"})
@ComponentScan(basePackages = {"br.org.cancer.modred", "br.org.cancer.modred.redomelib"})
//@EnableOAuth2Client
public class ApplicationConfiguration implements WebMvcConfigurer {  //extends WebMvcConfigurerAdapter {

	public static final Locale BRASIL_LOCALE = new Locale("pt", "BR");
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #configureViewResolvers(org.springframework.web.servlet.config.annotation .ViewResolverRegistry)
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addResourceHandlers(org.springframework.web.servlet.config.annotation. ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
/*	@Bean
	@Primary
	public LogAspect logAspect() {
		return new LogAspect();		
	}
*/
	/**
	 * Define o messsage source responsável pela internacionalização das mensagens.
	 * 
	 * @return message source
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * Define o cookie responsável por armazenar o locale do usuário.
	 * 
	 * @return cookie
	 */
	@Bean
	public LocaleResolver localeResolver() {
		// TODO revisar se a estrategia por cookie é a melhor opção.
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(BRASIL_LOCALE);
		localeResolver.setCookieName("modred-cookie");
		localeResolver.setCookieMaxAge(3600);

		return localeResolver;
	}

	/**
	 * Define o interceptor responsável pela alteração de locale da aplicação.
	 * 
	 * @return interceptor
	 */
	@Bean
	public LocaleChangeInterceptor localeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addInterceptors(org.springframework.web.servlet.config.annotation. InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeInterceptor());
		TimeZoneInterceptor timeZoneInterceptor = new TimeZoneInterceptor();
		registry.addInterceptor(timeZoneInterceptor);
	}

	/**
	 * Define a factory e configura o uso de mensagens customizadas para o bean validation.
	 * 
	 * @return customização da factory de validator
	 */
	// @Bean
	// public LocalValidatorFactoryBean getValidator() {
	// 	LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	// 	bean.setValidationMessageSource(messageSource());
	// 	return bean;
	// }

	/**
	 * Retorna um novo objeto do validador.
	 * 
	 * @return validador do hibernate
	 */
	/*public Validator getValidator() {
		return validator();
	}*/

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
				LocalDateTimeDeserializer.INSTANCE);
		builder.serializers(LocalDateSerializer.INSTANCE, LocalDateTimeSerializer.INSTANCE, JsonPageSerializer.INSTANCE);

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

	/**
	 * Configura/Habilta o spring para upload de arquivos.
	 * 
	 * @return CommonsMultipartResolver
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #configureDefaultServletHandling(org.springframework.web.servlet.config. annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addCorsMappings(org.springframework.web.servlet.config.annotation. CorsRegistry)
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("POST", "GET", "OPTIONS", "PUT", "DELETE")
				.allowedHeaders("*")
				.allowCredentials(true).maxAge(3600);
	}

	/**
	 * Configura um provedor do contexto do spring.
	 * 
	 * @return
	 */
	@Bean
	public ApplicationContextProvider applicationContextProvider() {
		return new ApplicationContextProvider();
	}

	/**
	 * Configura o cache manager na aplicação.
	 * 
	 * @return cache manager
	 */
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	/**
	 * Configura o factory de cache manager do ehcache.
	 * 
	 * @return cache manager factory
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	@ApplicationScope
	public IStorageConnection storageConnection() {
		return new BluemixStorageConnection();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public IStorageService storageService(IStorageConnection storageConnection) {
		return new BluemixStorageService(storageConnection);
	}
	
	@Bean
	public LogAspect logAspectBean(){
		return new LogAspect();
	}
	
	/**
	 * Bean para conexão como client de servidores REST.
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
	    List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
	    for (HttpMessageConverter<?> converter : converters) {
	        if (converter instanceof MappingJackson2HttpMessageConverter) {
	            MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
	            jsonConverter.setObjectMapper(new ObjectMapper());
	            jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
	        }
	    }
	    return restTemplate;		
	}
	
	@Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
	
	@Bean
	public RequestContextListener requestContextListener() {
	    return new RequestContextListener();
	}

}