package br.org.cancer.redome.auth.configuration;

import java.beans.PropertyVetoException;
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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Classe responsável pela configuração do JPA.
 * 
 * @author Cintia Oliveira
 *
 */
@Profile("default || dev || hml || prod")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "br.org.cancer.redome.auth.persistence"})
public class JPAConfiguration {

	@Value("${jpa.driver}")
	private String driver;
	
	@Value("${jpa.url}")
	private String jpaUrl;
	
	@Value("${jpa.username}")
	private String jpaUsername;
	
	@Value("${jpa.password}")
	private String jpaPassword;
	
	@Value("${c3p0.initial_pool_size}")
	private String initial_pool_size;
	
	@Value("${c3p0.min_pool_size}")
	private String min_pool_size;
	
	@Value("${c3p0.max_pool_size}")
	private String max_pool_size;
	
	@Value("${c3p0.max_idle_time}")
	private String max_idle_time;
	
	@Value("${hibernate.dialect}")
	private String dialect;
	
	/**
	 * Cria o datasource para conexão ao banco de dados.
	 * 
	 * @return datasource configurado
	 */
	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
		}
		catch (PropertyVetoException e) {
			return null;
		}

		dataSource.setJdbcUrl(jpaUrl);
		dataSource.setUser(jpaUsername);
		dataSource.setPassword(jpaPassword);
		dataSource.setInitialPoolSize(Integer.valueOf(initial_pool_size));
		dataSource.setMinPoolSize(Integer.valueOf(min_pool_size));
		dataSource.setMaxPoolSize(Integer.valueOf(max_pool_size));
		dataSource.setMaxIdleTime(Integer.valueOf(max_idle_time));

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
		em.setPackagesToScan(new String[] { "br.org.cancer.redome.auth.model" });
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
		
		if (dialect == null || "".endsWith(dialect)) {
			dialect = "org.hibernate.dialect.Oracle12cDialect";
		}

		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.show_sql", "false");

		//properties.setProperty("hibernate.hbm2ddl.auto", "update");

		properties.setProperty("hibernate.jdbc.time_zone", "America/Sao_Paulo");
		//properties.setProperty("hibernate.jdbc.time_zone", "UTC");

		// Configuração do Hibernate Envers
		/*
		 * properties.setProperty("org.hibernate.envers.audit_table_suffix", "_AUD");
		 * properties.setProperty("org.hibernate.envers.revision_field_name",
		 * "AUDI_ID");
		 * properties.setProperty("org.hibernate.envers.revision_type_field_name",
		 * "AUDI_TX_TIPO");
		 * properties.setProperty("org.hibernate.envers.store_data_at_delete", "true");
		 */
		
		// Corrigindo criação das tabelas temporários onde existe relacionamento JOINED.
		//properties.setProperty("hibernate.hql.bulk_id_strategy", "org.hibernate.hql.spi.id.inline.InlineIdsOrClauseBulkIdStrategy");

		return properties;
	}
}
