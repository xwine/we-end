package com.github.xwine.end.spring.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.mock.MockContext;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class HostNameProcessor implements Processor {

    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        String hostName = "";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            hostName =  ia.getHostAddress();
        } catch (UnknownHostException e) {
            // keep silence
            hostName = "127.0.0.1";
        }
        result.put("hostName", hostName);
        result.put("appName", MockContext.getConfig().getAppName());
        result.put("userName", MockContext.getConfig().getNowUser());

        result.put("ftp", MockContext.getConfig().getFtpHost());
        return result;
    }
}
