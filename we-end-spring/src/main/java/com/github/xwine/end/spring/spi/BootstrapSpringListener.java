package com.github.xwine.end.spring.spi;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.aop.MockMethodAdvise;
import com.github.xwine.end.spring.aop.MockMethodPointcut;
import com.github.xwine.end.mock.util.PropertiesUtil;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.Iterator;
import java.util.ServiceLoader;

public class BootstrapSpringListener implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static volatile boolean initialized;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (initialized) {
            return;
        }
        applicationContext.addBeanFactoryPostProcessor(new BeanRegister());
        initialized = true;
    }

    private static class BeanRegister implements BeanFactoryPostProcessor, Ordered {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            if (MockContext.getConfig().getWeEndOn()) {
                ServiceLoader<MockFileService> mockFileServices = ServiceLoader.load(MockFileService.class);
                Iterator<MockFileService> iterator = mockFileServices.iterator();
                if (iterator.hasNext()) {
                    beanFactory.registerSingleton("mockFileService", iterator.next());
                } else {
                    MockContext.LOG.error("[O-Mock] can not find remote mock server config");
                }
            }
            if (MockContext.getConfig().getMockOn()) {
                MockMethodPointcut mockMethodPointcut = new MockMethodPointcut();
                MockMethodAdvise mockMethodAdvise = new MockMethodAdvise();
                DefaultPointcutAdvisor mockAdvisor = new DefaultPointcutAdvisor(mockMethodPointcut,mockMethodAdvise);
                beanFactory.registerSingleton("mockAdvisor",mockAdvisor);
            }
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }
}
