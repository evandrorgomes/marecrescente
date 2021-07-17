package br.org.cancer.redome.tarefa.configuration;

import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableDiscoveryClient
@EnableOAuth2Sso
@EnableHystrix
@EnableWebMvc
@EnableScheduling
@SpringBootApplication(scanBasePackages = "br.org.cancer.redome.tarefa")
//@EnableFeignClients(basePackages = {"br.org.cancer.redome.tarefa.feign.client"})
@EnableJpaRepositories(basePackages = "br.org.cancer.redome.tarefa.persistence")
@EnableOAuth2Client
public class RedomeTarefaApplication  extends SpringBootServletInitializer{

	public static final Locale BRASIL_LOCALE = new Locale("pt", "BR");
	public static final String TIME_ZONE = "America/Sao_Paulo";
	public static final int BATCH_SIZE = 40;
	
	
	@Value("${jpa.url}")
	private String jpaConfigurationUrl;
	@Value("${jpa.username}")
	private String jpaConfigurationUsername;
	@Value("${jpa.password}")
	private String jpaConfigurationPassword;
	@Value("${jpa.driver}")
	private String jpaDriverClassName;
	
	
	public static void main(String[] args) {
		SpringApplication.run(RedomeTarefaApplication.class, args);		
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}
	
	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(RedomeTarefaApplication.class).web(WebApplicationType.SERVLET);
	}
	
	/**
	 * Cria o datasource para conexão ao banco de dados.
	 * 
	 * @return datasource configurado
	 */
	@Bean
	@Profile("default || dev || hml")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jpaDriverClassName);
        dataSource.setUrl(jpaConfigurationUrl);
        dataSource.setUsername(jpaConfigurationUsername);
        dataSource.setPassword(jpaConfigurationPassword);
        return dataSource;
	}

	/**
	 * Configura o controle transacional da aplicação.
	 * 
	 * @param emf EntityManagerFactory
	 * @return transactionManager
	 */
	@Bean
	@Profile("default || dev || hml")
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	@Profile("default || dev || hml")
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/**
	 * Responsável pela inicialização do factory do entitymanager.
	 * 
	 * @return entityManagerFactory
	 */
	@Bean
	@Profile("default || dev || hml")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "br.org.cancer.redome.tarefa.model" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setValidationMode(ValidationMode.AUTO);
		em.setJpaProperties(getAdditionalProperties());
		return em;
	}

	/**
	 * Define propriedades adicionais de configuração do jpa.
	 * 
	 * @return propriedades
	 */
	public Properties getAdditionalProperties() {
		Properties properties = new Properties();
		//properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
		properties.setProperty("hibernate.jdbc.batch_size", String.valueOf(BATCH_SIZE));
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.setProperty("hibernate.show_sql", "false");

		// Configuração do Hibernate Envers
		properties.setProperty("org.hibernate.envers.audit_table_suffix", "_AUD");
		properties.setProperty("org.hibernate.envers.revision_field_name", "AUDI_ID");
		properties.setProperty("org.hibernate.envers.revision_type_field_name", "AUDI_TX_TIPO");
		properties.setProperty("org.hibernate.envers.store_data_at_delete", "false");

		return properties;
	}
	
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
	
	/*
	 * @Bean public ObjectMapper objectMapper() { Jackson2ObjectMapperBuilder
	 * builder = Jackson2ObjectMapperBuilder.json(); builder.deserializers(new
	 * EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE);
	 * builder.serializers(LocalDateSerializer.INSTANCE);
	 * 
	 * return builder.build(); }
	 */	
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	
	
	@Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
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
	
	
}