package com.github.xwine.end.mock.spi;

import java.lang.reflect.Type;

public interface IntelligentService {

    Object getValue(String name, Type type);
}
