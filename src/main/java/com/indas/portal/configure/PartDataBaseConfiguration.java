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
@EnableJpaRepositories(value = "com.indas.portal.repositories",
        entityManagerFactoryRef = "partEntityManagerFactory",
        transactionManagerRef = "partTransactionManager")
//@EntityScan({"com.indas.portal.entities"})

//@EnableWebMvc
public class PartDataBaseConfiguration {

//    @Autowired
//    private Environment env;

    @Bean(name = "datasource1")
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource partDataSource() {

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("app.datasource.driverClassName"));
//        dataSource.setUrl(env.getProperty("app.datasource.url"));
//        dataSource.setUsername(env.getProperty("app.datasource.username"));
//        dataSource.setPassword(env.getProperty("app.datasource.password"));
//
//        return dataSource;
        return DataSourceBuilder.create().
                build();
    }

    @Bean(name="partEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean partEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(partDataSource());
        em.setPackagesToScan("com.indas.portal.entities");
        em.setJpaVendorAdapter(partJpaVendorAdapter());
        em.setJpaProperties(partAdditionalProperties());

        return em;
    }

    @Bean
    public JpaVendorAdapter partJpaVendorAdapter(){
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return vendorAdapter;
    }


    @Bean(name="partTM")
    public JpaTransactionManager partTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(partEntityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties partAdditionalProperties() {
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
