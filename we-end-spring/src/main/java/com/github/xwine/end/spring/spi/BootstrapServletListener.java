package com.github.xwine.end.spring.spi;

import com.github.xwine.end.spring.WebEndFilter;
import com.github.xwine.end.mock.MockContext;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 利用servlet3提供的spi自动注入
 */
public class BootstrapServletListener implements WebApplicationInitializer {
    private static volatile boolean filterAppended;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (filterAppended) {
            return;
        }
        if (MockContext.getConfig().getMockOn()) {
            FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("webEndFilter", new WebEndFilter());
            filterRegistration.addMappingForUrlPatterns(null, false, MockContext.getConfig().getConsolePrefix()+"/*");
            filterAppended = true;
        }
    }
}
