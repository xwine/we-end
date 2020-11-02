package com.github.xwine.end.mock.spi;

import com.github.xwine.end.mock.MockContext;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

public class DefaultIntelligentService implements IntelligentService {
    @Override
    public Object getValue(String name, Type type) {
        if (type.equals(String.class)) {
            return MockContext.getConfig().getTemplateString();
        }
        if (type.equals(Integer.class) || type.equals(int.class)) {
            return MockContext.getConfig().getTemplateInteger();
        }
        if (type.equals(Long.class) || type.equals(long.class)) {
            return MockContext.getConfig().getTemplateLong();
        }
        if (type.equals(Double.class) || type.equals(double.class)) {
            return MockContext.getConfig().getTemplateDouble();
        }
        if (type.equals(Float.class) || type.equals(float.class)) {
            return MockContext.getConfig().getTemplateFloat();
        }
        if (type.equals(BigDecimal.class)) {
            return MockContext.getConfig().getTemplateBigDecimal();
        }
        if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return MockContext.getConfig().getTemplateBoolean();
        }
        if (type.equals(Character.class) || type.equals(char.class)) {
            return MockContext.getConfig().getTemplateCharacter();
        }
        if (type.equals(Byte.class) || type.equals(byte.class)) {
            return MockContext.getConfig().getTemplateByte();
        }
        if (type.equals(Date.class)) {
            return MockContext.getConfig().getTemplateDate();
        }
        return null;
    }
}
