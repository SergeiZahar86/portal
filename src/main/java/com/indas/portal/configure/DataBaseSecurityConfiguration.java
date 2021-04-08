package com.indas.portal.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        value = "com.indas.portal.security.repositories",
        entityManagerFactoryRef = "securityEntityManagerFactory",
        transactionManagerRef = "securityTransactionManager")
//@EntityScan({"com.indas.portal.security.entities"})

//@EnableWebMvc
public class DataBaseSecurityConfiguration {

//    @Autowired
//    private Environment env;

    @Bean(name = "datasource2")
    @Primary
    @ConfigurationProperties(prefix = "app.security.datasource")
    public DataSource securityDataSource() {

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("app.datasource.driverClassName"));
//        dataSource.setUrl(env.getProperty("app.security.datasource.url"));
//        dataSource.setUsername(env.getProperty("app.security.datasource.username"));
//        dataSource.setPassword(env.getProperty("app.security.datasource.password"));
//
//        return dataSource;

        return DataSourceBuilder.create().
                build();
    }

    @Bean(name="securityEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(securityDataSource());
        em.setPackagesToScan("com.indas.portal.security.entities");
        em.setJpaVendorAdapter(securityJpaVendorAdapter());
        em.setJpaProperties(securityAdditionalProperties());

        return em;
    }

    @Bean
    @Primary
    public JpaVendorAdapter securityJpaVendorAdapter(){
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return vendorAdapter;
    }


    @Bean(name="securityTM")
    @Primary
    public JpaTransactionManager securityTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(securityEntityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties securityAdditionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        //properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }

}
