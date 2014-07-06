package com.itrane.mvcdemo.init;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Spring フレームワークの設定.
 *  注意：この Java 設定クラスを使用するには Spring Framework 3.1 以上が必要
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.itrane.mvcdemo")
@PropertySource("classpath:resources/app.properties")
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Resource
	private Environment env;
    
	//静的リソースの設定
    @Override  
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  
            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");  
    }

	//テンプレートリゾルバーの設定
    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        //NB, selecting HTML5 as the template mode.
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(false);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
    
    //Thymeleaf テンプレートエンジンの設定
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }
    
    //Thymeleaf ビューリゾルバー設定
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[]{"*"});
        viewResolver.setCache(false);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }
    
	//メッセージソースの設定
	//WEBページでプロパティファイルを使用できる
	//日本語メッセージ：messages_ja.properties 
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(env.getRequiredProperty("message.source.basename"));

		// trueをセットすれば、メッセージのキーがない場合にキーを表示
		// false の場合、NoSuchMessageExceptionを投げる
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : リロードしない、0 : 常にリロードする
		messageSource.setCacheSeconds(0);
		return messageSource;
	}
}
