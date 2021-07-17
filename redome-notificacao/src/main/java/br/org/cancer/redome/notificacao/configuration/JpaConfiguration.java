package br.org.cancer.redome.notificacao.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("default || dev || hml")
@EnableJpaRepositories(basePackages = "br.org.cancer.redome.notificacao.persistence")
public class JpaConfiguration {
	
	public static final int BATCH_SIZE = 40;
	
	@Value("${jpa.url}")
	private String jpaConfigurationUrl;
	@Value("${jpa.username}")
	private String jpaConfigurationUsername;
	@Value("${jpa.password}")
	private String jpaConfigurationPassword;
	@Value("${jpa.driver}")
	private String jpaDriverClassName;
	
	/**
	 * Cria o datasource para conexão ao banco de dados.
	 * 
	 * @return datasource configurado
	 */
	@Bean
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
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}	

	/**
	 * Responsável pela inicialização do factory do entitymanager.
	 * 
	 * @return entityManagerFactory
	 */
	@Bean	
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "br.org.cancer.redome.notificacao.model" });
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
		properties.setProperty("hibernate.show_sql", "true");

		// Configuração do Hibernate Envers
		properties.setProperty("org.hibernate.envers.audit_table_suffix", "_AUD");
		properties.setProperty("org.hibernate.envers.revision_field_name", "AUDI_ID");
		properties.setProperty("org.hibernate.envers.revision_type_field_name", "AUDI_TX_TIPO");
		properties.setProperty("org.hibernate.envers.store_data_at_delete", "true");

		return properties;
	}
	

}
