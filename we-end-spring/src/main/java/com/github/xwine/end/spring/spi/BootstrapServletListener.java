package com.github.xwine.end.spring.spi;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.WebEndFilter;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

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

        if (MockContext.getConfig().getWeEndOn()) {
            MockContext.LOG.warn("[O-MOCK][ we end filter on  ！！！]");
            FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("webEndFilter", new WebEndFilter());
            if (filterRegistration != null) {
                filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, MockContext.getConfig().getConsolePrefix() + "/*");
                filterAppended = true;
            }
        } else {
            MockContext.LOG.info("[O-MOCK][we end off ！！！]");
        }
    }
}
