package com.xxy.gateway.bind;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;

/**
 * @Author xuxinyi
 * @create 2023/7/4 22:17
 * @Description 泛化调用静态代理，方便进行一些拦截处理。给http对应的rpc调用，做一层代理控制。每调用到一个http对应的网关方法，就会以代理的方式调用到rpc对应的泛化调用方法上.
 *              封装rpc提供的GenericService方法: genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"world"});
 */
public class GenericReferenceProxy implements MethodInterceptor {
    /**
     * rpc泛化调用服务
     */
    private final GenericService genericService;
    /**
     * rpc泛化调用方法
     */
    private final String methodName;

    public GenericReferenceProxy(GenericService genericService, String methodName) {
        this.genericService = genericService;
        this.methodName = methodName;
    }


    /**
     * 做一层代理控制。后续不止是可以用Dubbo泛化调用，也可以是其他服务的泛化调用
     * 泛化调用文档：https://dubbo.apache.org/zh/docsv2.7/user/examples/generic-reference/
     *
     * @param o
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameters = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = parameterTypes[i].getName();
        }

        // Dubbo的泛化调用
        // 举例：genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"world"});
        return genericService.$invoke(methodName, parameters, args);
    }
}
