package com.springboot.insights.configuration;

import com.springboot.insights.controller.Insights_Controller;
import com.springboot.insights.service.LogStore;
import com.springboot.insights.tracker.QueryCountInspector;
import com.springboot.insights.tracker.RunTimeInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public LogStore logStore() {
        return new LogStore();
    }

    @Bean
    public QueryCountInspector queryCountInspector() {
        return new QueryCountInspector();
    }

    @Bean
    public RunTimeInterceptor runTimeInterceptor() {
        return new RunTimeInterceptor(logStore());
    }

    @Bean
    public Insights_Controller insights_Controller() {
        return new Insights_Controller(logStore());
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(runTimeInterceptor())
                .addPathPatterns("/**");
    }


}
