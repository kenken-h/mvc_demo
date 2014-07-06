package com.itrane.mvcdemo.init;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * データベース設定.
　*  @EnableTransactionManagement : <tx:annotation-driven transaction-manager="transactionManager" />
 *  @EnableJpaRepositories("..."): <jpa:repositories base-package="..." />
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.itrane.mvcdemo.repo")  
public class DbConfig {  
          
    private static final String P_DB_DRIVER   = "db.driver";  
    private static final String P_DB_URL      = "db.url";  
    private static final String P_DB_USERNAME = "db.username";  
    private static final String P_DB_PASSWORD = "db.password";  
    
    private static final String P_HB_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String P_HB_DIALECT  = "hibernate.dialect";  
    private static final String P_HB_SHOW_SQL = "hibernate.show_sql";  
    private static final String P_MODEL_SCAN = "model.scan.package";  
      
        @Resource  
        private Environment env;  
  
        // データソースの設定。
        //　 プロパティの値は src/resources/app.properties から取得
        @Bean  
        public DataSource dataSource() {  
                DriverManagerDataSource dataSource = new DriverManagerDataSource();  
                  
                dataSource.setDriverClassName(env.getRequiredProperty(P_DB_DRIVER));  
                dataSource.setUrl(env.getRequiredProperty(P_DB_URL));  
                dataSource.setUsername(env.getRequiredProperty(P_DB_USERNAME));  
                dataSource.setPassword(env.getRequiredProperty(P_DB_PASSWORD));  
                  
                return dataSource;  
        }  
        
        //エンティティマネージャ・ファクトリ
        @Bean  
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {  
                LocalContainerEntityManagerFactoryBean entityManagerFactoryBean 
                	= new LocalContainerEntityManagerFactoryBean();  
                entityManagerFactoryBean.setDataSource(dataSource());  
                entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);  
                entityManagerFactoryBean.setPackagesToScan(
                		env.getRequiredProperty(P_MODEL_SCAN));  

                //JPAプロパティの設定
                Properties properties = new Properties(); 
        		properties.put(P_HB_DDL_AUTO, env.getRequiredProperty(P_HB_DDL_AUTO));
        		//properties.put("hibernate.hbm2ddl.auto", "create-drop");
                properties.put(P_HB_DIALECT, env.getRequiredProperty(P_HB_DIALECT));  
                properties.put(P_HB_SHOW_SQL, env.getRequiredProperty(P_HB_SHOW_SQL));  
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