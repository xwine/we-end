package com.github.xwine.end.spring.boot;

import com.github.xwine.end.mock.MockContext;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockWebAutoConfiguration {

    @Bean
    @Conditional(ProductCondition.class)
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new com.github.xwine.end.spring.WebEndFilter());
        bean.addUrlPatterns(MockContext.getConfig().getConsolePrefix() + "/*");
        return bean;
    }
}
