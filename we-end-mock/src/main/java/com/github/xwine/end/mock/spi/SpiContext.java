package com.github.xwine.end.mock.spi;


import com.github.xwine.end.mock.MockContext;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiContext {

    private static IntelligentService intelligentService;

    public static IntelligentService getIntelligentService() {
        if (intelligentService != null) {
            return intelligentService;
        }
        ServiceLoader<IntelligentService> intelligentServices = ServiceLoader.load(IntelligentService.class);
        Iterator<IntelligentService> iterator = intelligentServices.iterator();
        if (iterator.hasNext()) {
            intelligentService = iterator.next();
        } else {
            MockContext.LOG.error("[O-Mock] can not find intelligentService");
            intelligentService = new DefaultIntelligentService();
        }
        return intelligentService;
    }
}
