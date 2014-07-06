package test.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.itrane.mvcdemo.repo")  
public class TestDbConfig {
	
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();  
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");  
        dataSource.setUrl("jdbc:mysql://localhost:3306/testmvc");  
        dataSource.setUsername("demo");  
        dataSource.setPassword("pass");  
        return dataSource;
    }

    //エンティティマネージャ
    @Bean  
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {  
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean 
            	= new LocalContainerEntityManagerFactoryBean();  
            entityManagerFactoryBean.setDataSource(dataSource());  
            entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);  
            entityManagerFactoryBean.setPackagesToScan("com.itrane.mvcdemo.model");  

            //JPAプロパティの設定
            Properties properties = new Properties(); 
    		properties.put("hibernate.hbm2ddl.auto", "create-drop");
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");  
            properties.put("hibernate.show_sql", "true");  
            entityManagerFactoryBean.setJpaProperties(properties);                
            return entityManagerFactoryBean;  
    }  
      
    //トランザクションマネージャ
    @Bean  
    public JpaTransactionManager transactionManager() {  
            JpaTransactionManager transactionManager = new JpaTransactionManager();  
            transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());  
            return transactionManager;  
    }  
}