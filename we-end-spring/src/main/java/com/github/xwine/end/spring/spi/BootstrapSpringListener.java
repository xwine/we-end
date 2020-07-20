package com.github.xwine.end.spring.spi;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.aop.MockMethodAdvise;
import com.github.xwine.end.spring.aop.MockMethodPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class BootstrapSpringListener implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static volatile boolean initialized;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!initialized && MockContext.getConfig().getMockOn()) {
            applicationContext.addBeanFactoryPostProcessor(new BeanRegister());
            initialized = true;
        }
    }

    private static class BeanRegister implements BeanFactoryPostProcessor, Ordered {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            MockMethodPointcut mockMethodPointcut = new MockMethodPointcut();
            MockMethodAdvise mockMethodAdvise = new MockMethodAdvise();
            DefaultPointcutAdvisor mockAdvisor = new DefaultPointcutAdvisor(mockMethodPointcut,mockMethodAdvise);
            beanFactory.registerSingleton("mockAdvisor",mockAdvisor);
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }
}
