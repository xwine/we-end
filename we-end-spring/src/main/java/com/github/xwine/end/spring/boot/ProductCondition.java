package com.github.xwine.end.spring.boot;

import com.github.xwine.end.mock.MockContext;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ProductCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return MockContext.getConfig().getMockOn();
    }
}
