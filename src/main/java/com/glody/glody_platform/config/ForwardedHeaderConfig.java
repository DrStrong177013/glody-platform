package com.glody.glody_platform.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class ForwardedHeaderConfig {
    
    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        ForwardedHeaderFilter filter = new ForwardedHeaderFilter();
        filter.setRelativeRedirects(true);
        
        FilterRegistrationBean<ForwardedHeaderFilter> bean = 
                new FilterRegistrationBean<>(filter);
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        bean.addUrlPatterns("/api/*");
        
        return bean;
    }
}