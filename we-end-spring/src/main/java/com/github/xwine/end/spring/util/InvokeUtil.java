package com.github.xwine.end.spring.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.xwine.end.mock.ObjectMock;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class InvokeUtil {
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public static JSON getParamsJson(WebApplicationContext wac, String beanName, String methodName) {
        JSONObject result = new JSONObject();
        Object bean = wac.getBean(beanName);
        if (bean == null) {
            result.put("code", 400);
            result.put("msg", "bean is not found:" + beanName);
            return result;
        }
        Class clazz = bean.getClass();
        Object targetBean = null;
        try {
            targetBean = AopTargetUtils.getTarget(bean);
            clazz = AopTargetUtils.getTargetClass(targetBean.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Method[] methods = clazz.getMethods();
        Method method = null;
        for (Method m : methods) {
            if (methodSign(m).equals(methodName)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            result.put("code", 400);
            result.put("msg", "no method is named:" + methodName + " in bean:" + beanName);
            return result;
        }
        return getParamsInfo(method);
    }





    private static JSONArray getParamsInfo(Method method) {
        JSONArray result = new JSONArray();
        StringBuilder json = new StringBuilder();

        try {
            Class<?>[] types = method.getParameterTypes();
            for (Class<?> clas : types) {
                result.add(ObjectMock.getObject(clas,2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object[] parseArgs(String paramsStr, Type[] parameterTypes) {
        Object[] result = new Object[parameterTypes.length];
        JSONArray jsonArray = JSON.parseArray(paramsStr);
        for (int i = 0; i <parameterTypes.length; i++) {
            if (jsonArray.get(i) == null) {
                result[i] = null;
            } else if (parameterTypes[i].equals(String.class)) {
                result[i] = jsonArray.get(i).toString();
            } else {
                result[i] = JSON.parseObject(jsonArray.get(i).toString(), parameterTypes[i]);
            }
        }
        return result;
    }

    public static JSONObject clientInfo() {
        JSONObject clientInfo = new JSONObject();
        clientInfo.put("name", "3245");
        clientInfo.put("userAgent", "3245");
        clientInfo.put("ip", getLocalIp());
        clientInfo.put("hostName", getLocalHostName());
        clientInfo.put("appId", System.getProperty("deploy.app.id"));
        String instanceHome = System.getProperty("catalina.base");
        if (instanceHome == null || instanceHome.isEmpty()) {
            instanceHome = System.getProperty("user.dir");
        }
        clientInfo.put("instanceHome", instanceHome);

        if (clientInfo.getString("userAgent") == null || clientInfo.getString("userAgent").trim().isEmpty()) {
            clientInfo.put("userAgent", System.getProperty("deploy.app.name"));
        }
        return clientInfo;
    }

    public static JSONObject invoke(Object bean, Method method, String paramsStr) {
        JSONObject result = new JSONObject();
        if (bean == null) {
            result.put("code", 500);
            result.put("msg", "bean is null");
            return result;
        }
        if (method == null) {
            result.put("code", 500);
            result.put("msg", "method is null");
            return result;
        }
        return justInvoke(bean, method, paramsStr);
    }


    public static JSON invoke(WebApplicationContext wac, String beanName, String methodName, String paramsStr) {

        Object bean = wac.getBean(beanName);
        if (bean == null) {
            JSONObject result = new JSONObject();
            result.put("code", 400);
            result.put("msg", "bean is not found:" + beanName);
            return result;
        }
        Class clazz = bean.getClass();
        Object targetBean = null;
        try {
            targetBean = AopTargetUtils.getTarget(bean);
            clazz = AopTargetUtils.getTargetClass(targetBean.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Method[] methods = clazz.getMethods();
        Method method = null;
        for (Method m : methods) {
            if (methodSign(m).equals(methodName)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            JSONObject result = new JSONObject();
            result.put("code", 400);
            result.put("msg", "no method is named:" + methodName + " in bean:" + beanName);
            return result;
        }

        return justInvoke(targetBean, method, paramsStr);
    }

    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if(v==null) {
                return "";
            }
            return v;
        }
    };

    private static JSONObject justInvoke(Object bean, Method method, String paramsStr) {
        JSONObject result = new JSONObject();
        try {
            Object callResult = method.invoke(bean, InvokeUtil.parseArgs(paramsStr, method.getGenericParameterTypes()));
            result.put("code", 200);
            result.put("data", JSON.parse(JSON.toJSONString(callResult)));
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e);
        }
        return result;
    }

    /**
     * 返回一个字符串，为方法的签名，能在一个类里面唯一表示一个方法，包括有重载的的情况
     * method.toGenericString() 然后吧空格换成下划线
     * @param method
     * @return
     */
    public static String methodSign(Method method){
        String s = descMethod(method);
        return s;
        //return s.replaceAll("\\s+", "_");
    }

    /**
     * 描述一个方法
     *
     * @param m
     * @return
     */
    public static String descMethod(Method m) {
        String[] names = parameterNameDiscoverer.getParameterNames(m);
        if (names == null) {
            names = new String[0];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Modifier.toString(m.getModifiers())).append(' '); // 修饰符
        sb.append(m.getReturnType().getSimpleName()).append(' '); // 返回值类型
        sb.append(m.getName()); //  方法名
        sb.append('(');   // 带参数名的参数列表
        Class<?>[] ps = m.getParameterTypes();
        if (ps == null) {
            ps = new Class[0];
        }
        if (ps.length == names.length) { // 能确定参数名字， 如果不能确定参数名字  就只显示参数类型吧
            for (int j = 0; j < ps.length; j++) {
                sb.append(ps[j].getSimpleName());
                sb.append(' ');
                sb.append(names[j]);
                if (j < (ps.length - 1)) {
                    sb.append(", ");
                }
            }
        } else {
            for (int j = 0; j < ps.length; j++) {
                sb.append(ps[j].getSimpleName());
                sb.append(' ');
                if (j < (ps.length - 1)) {
                    sb.append(", ");
                }
            }
        }
        sb.append(')');
        return sb.toString();
    }

    private static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    private static String getLocalIp() {
        InetAddress address = getLocalAddress();
        if (address == null) {
            return null;
        } else {
            String ip = address.getHostAddress();
            if (ip == null || "".equals(ip.trim()) || "0.0.0.0".equals(ip) || "127.0.0.1".equals(ip)) {
                ip = address.getHostName();
            }

            return ip;
        }
    }

    private static InetAddress getLocalAddress() {
        InetAddress localAddress = null;

        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var6) {
            ;
        }

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            if (e != null) {
                while (e.hasMoreElements()) {
                    try {
                        NetworkInterface e1 = (NetworkInterface) e.nextElement();
                        Enumeration addresses = e1.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress e2 = (InetAddress) addresses.nextElement();
                                    if (isValidAddress(e2)) {
                                        return e2;
                                    }
                                } catch (Throwable var5) {
                                    ;
                                }
                            }
                        }
                    } catch (Throwable var7) {
                        ;
                    }
                }
            }
        } catch (Throwable var8) {
            ;
        }

        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress()) {
            String ip = address.getHostAddress();
            return ip != null && !"0.0.0.0".equals(ip) && !"127.0.0.1".equals(ip) && IP_PATTERN.matcher(ip).matches();
        } else {
            return false;
        }
    }
}
