package com.xxy.gateway.bind;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.apache.dubbo.rpc.service.GenericService;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuxinyi
 * @create 2023/7/4 22:28
 * @Description 泛化调用静态代理工厂.用于创建代理对象, 这里用了cglib, 先构建出一个rpc接口信息，再把我们定义的IGenericReference接口和构建接口，一起给代理类实现。
 * 也就是说一个代理类，实现了2个接口。
 */
public class GenericReferenceProxyFactory {
    /**
     * RPC 泛化调用服务
     */
    private final GenericService genericService;
    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public GenericReferenceProxyFactory(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * @param method
     * @return
     */
    public IGenericReference newInstance(String method) {
        return genericReferenceCache.computeIfAbsent(method, k -> {
            // 泛化调用
            GenericReferenceProxy genericReferenceProxy = new GenericReferenceProxy(genericService, method);
            // 创建接口
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(method, Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceCLass = interfaceMaker.create();
            // 代理对象
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            // IGenericReference 统一泛化调用接口
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceCLass});
            enhancer.setCallback(genericReferenceProxy);
            return (IGenericReference) enhancer.create();
        });
    }
}
