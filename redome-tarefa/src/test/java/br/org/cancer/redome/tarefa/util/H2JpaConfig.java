package br.org.cancer.redome.tarefa.util;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ValidationMode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("test")
@Configuration
@EnableJpaRepositories(basePackages = { "br.org.cancer.redome.tarefa.persistence" })
@EnableTransactionManagement
public class H2JpaConfig {

	/**
	 * Cria o datasource para conexão ao banco de dados.
	 * 
	 * @return datasource configurado
	 */
	@Bean
	public EmbeddedDatabase dataSource() {
		EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().
	            setType(EmbeddedDatabaseType.H2).
	            addScript("h2/criacao.sql").
//	            addScript("h2/indices.sql").
//	            addScript("h2/dados_basicos.sql").
	            addScript("h2/insert_inicial.sql").
	            build();
		return dataSource;
				
	}

	/**
	 * Configura o controle transacional da aplicação.
	 * 
	 * @param emf entityManager factory
	 * @return transactionManager transaction manager
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
		em.setPackagesToScan(new String[] { "br.org.cancer.redome.tarefa.model" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setValidationMode(ValidationMode.NONE);
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

		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.setProperty("hibernate.show_sql", "false");
		//properties.setProperty("hibernate.hbm2ddl.auto", "update");

		properties.setProperty("hibernate.jdbc.time_zone", "America/Sao_Paulo");

		// Configuração do Hibernate Envers
		properties.setProperty("org.hibernate.envers.audit_table_suffix", "_AUD");
		properties.setProperty("org.hibernate.envers.revision_field_name", "AUDI_ID");
		properties.setProperty("org.hibernate.envers.revision_type_field_name", "AUDI_TX_TIPO");
		properties.setProperty("org.hibernate.envers.store_data_at_delete", "true");

		return properties;
	}
}
