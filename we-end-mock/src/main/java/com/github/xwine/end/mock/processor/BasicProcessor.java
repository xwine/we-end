package com.github.xwine.end.mock.processor;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.spi.SpiContext;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwine
 * @date 2020-03-05 09:50
 */
public class BasicProcessor extends DefaultProcessor {


    public BasicProcessor(Type type) {
        super(type);
    }

    public BasicProcessor(Type type, int deep) {
        super(type, deep);
    }
    public BasicProcessor(Type type, int deep, String name) {
        super(type,deep,name);
    }

    @Override
    public Object process() {
        return SpiContext.getIntelligentService().getValue(this.name,this.type);
    }
}
