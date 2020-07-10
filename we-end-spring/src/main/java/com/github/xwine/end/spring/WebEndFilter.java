package com.github.xwine.end.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.util.StringUtils;
import com.github.xwine.end.spring.processor.EmptyProcessor;
import com.github.xwine.end.spring.processor.HostNameProcessor;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.processor.beans.CallProcessor;
import com.github.xwine.end.spring.processor.beans.GetBeanNameProcessor;
import com.github.xwine.end.spring.processor.beans.GetMethodsProcessor;
import com.github.xwine.end.spring.processor.beans.GetParamsProcessor;
import com.github.xwine.end.spring.processor.ftp.DownloadProcessor;
import com.github.xwine.end.spring.processor.ftp.GetRemoteDataProcessor;
import com.github.xwine.end.spring.processor.ftp.GetRemoteDicProcessor;
import com.github.xwine.end.spring.processor.ftp.GetRemoteUserProcessor;
import com.github.xwine.end.spring.processor.loc.GetLocDataProcessor;
import com.github.xwine.end.spring.processor.loc.GetLocDicProcessor;
import com.github.xwine.end.spring.processor.loc.UploadProcessor;
import com.github.xwine.end.spring.util.FileUtil;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author xwine
 * @date 2020-02-28 09:21
 */
public class WebEndFilter implements Filter {

    private static Map<String, Processor> processMap;

    private FilterConfig filterConfig;

    static {
        processMap = new HashMap<String, Processor>();
        processMap.put("/getBeanName.json", new GetBeanNameProcessor());
        processMap.put("/getMethods.json", new GetMethodsProcessor());
        processMap.put("/getParams.json", new GetParamsProcessor());
        processMap.put("/call.json", new CallProcessor());
        processMap.put("/hostName.json", new HostNameProcessor());
        processMap.put("/getLocDic.json", new GetLocDicProcessor());
        processMap.put("/getLocData.json", new GetLocDataProcessor());
        processMap.put("/mockUpload.json", new UploadProcessor());
        processMap.put("/getRemoteDic.json", new GetRemoteDicProcessor());
        processMap.put("/getRemoteUser.json", new GetRemoteUserProcessor());
        processMap.put("/getRemoteData.json", new GetRemoteDataProcessor());
        processMap.put("/mockDownload.json", new DownloadProcessor());
    }

    /**
     * 解析配置文件
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            this.filterConfig = filterConfig;
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
            Environment environment = wac.getEnvironment();
            String port = environment.getProperty("server.port");
            if (StringUtils.isEmpty(port) || "null".equalsIgnoreCase(port)) {
                port = String.valueOf(getHttpPort());
            }
            MockContext.LOG.info("\n" +
                    "                              __  \n" +
                    "   ____ ___   ____   _____   / /__\n" +
                    "  / __ `__ \\ / __ \\ / ___/  / //_/\n" +
                    " / / / / / // /_/ // /__   / ,<   \n" +
                    "/_/ /_/ /_/ \\____/ \\___/  /_/|_|  \n" +
                    "                                  \n");
            MockContext.LOG.info("[O-MOCK] \033[31;1m web-end:\033[1m:http://localhost:" + port + MockContext.getConfig().getConsolePrefix() + "/index.html");
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] WebEndFilter.init exception:", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (MockContext.getConfig().getMockOn()) {
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setCharacterEncoding("utf-8");
                //获取去掉前缀后的请求地址
                String path = requestPath(request);
                if (path.isEmpty() || path.equals("/")) {
                    response.sendRedirect(MockContext.getConfig().getConsolePrefix() + "/index.html");
                }
                //是否JSON数据请求
                if (isJsonRequest(path)) {
                    returnJson(request, response);
                } else {
                    returnFileContent(path, response);
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] WebEndFilter.doFilter exception:", e);
        }
    }

    @Override
    public void destroy() {}

    private boolean isJsonRequest(String path) {
        return path.endsWith(".json");
    }

    /**
     * 获取去掉前缀后的请求地址
     *
     * @param request
     * @return
     */
    private String requestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(MockContext.getConfig().getConsolePrefix().length());
    }


    private void returnJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        JSON json = null;
        Processor processor = dispatchToProcessor(request);
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.filterConfig.getServletContext());
        json = processor.process(wac, request.getParameterMap());
        if (json != null && StringUtils.isNotEmpty(json.toJSONString())) {
            response.getWriter().write(StringUtils.uriDecode(json.toJSONString(), Charset.defaultCharset()));
            response.getWriter().flush();
        } else {
            response.getWriter().write(StringUtils.uriDecode(new JSONObject().toJSONString(), Charset.defaultCharset()));
            response.getWriter().flush();
        }
        response.getWriter().close();
    }

    private Processor dispatchToProcessor(HttpServletRequest request) {
        Processor processor = processMap.get(requestPath(request));
        return processor == null ? new EmptyProcessor() : processor;
    }


    private void returnFileContent(String path, HttpServletResponse response) throws IOException {
        String contentType = getContentType(path);
        if (StringUtils.isEmpty(contentType)) {
            return;
        }
        response.setHeader("Content-Type", contentType);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(FileUtil.readByteArrayFromResource(path));
        outputStream.flush();
        outputStream.close();
    }

    private String getContentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css;charset=UTF-8";
        }
        if (path.endsWith(".js")) {
            return "application/javascript;charset=UTF-8";
        }
        if (path.endsWith("html")) {
            return "text/html;charset=UTF-8";
        }
        return null;

    }

    public int getHttpPort() {
        try {
            MBeanServer server;
            if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
                server = MBeanServerFactory.findMBeanServer(null).get(0);
            } else {
                return 8080;
            }

            Set names = server.queryNames(new ObjectName("Catalina:type=Connector,*"),
                    Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

            Iterator iterator = names.iterator();
            if (iterator.hasNext()) {
                ObjectName name = (ObjectName) iterator.next();
                return Integer.parseInt(server.getAttribute(name, "port").toString());
            }
        } catch (Exception e) {
            MockContext.LOG.warn("[O-MOCK] get port exception");
        }
        return 8080;
    }
}
