package com.github.xwine.end.test.ext;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.spi.DefaultIntelligentService;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderIntelligentService extends DefaultIntelligentService {

    static Map<String,String> jdMap = null;
    static {
        jdMap = new HashMap<String,String>();
        jdMap.put("age","18");
        jdMap.put("name","赵信");
        jdMap.put("sex","男");
    }

    @Override
    public Object getValue(String name, Type type) {
        String value = jdMap.get(name);
        if (value == null) {
            return super.getValue(name,type);
        }
        try {
            if (type.equals(String.class)) {
                return value;
            }
            if (type.equals(Integer.class) || type.equals(int.class)) {
                return Integer.parseInt(value);
            }
            if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(value);
            }
            if (type.equals(Double.class) || type.equals(double.class)) {
                return Double.parseDouble(value);
            }
            if (type.equals(Float.class) || type.equals(float.class)) {
                return Float.parseFloat(value);
            }
            if (type.equals(BigDecimal.class)) {
                return new BigDecimal(value);
            }
        } catch (ClassCastException e) {
            MockContext.LOG.info("intelligent word is {} type:{} cant be converted",name,type);
            return super.getValue(name,type);
        }
        return super.getValue(name,type);
    }
}
