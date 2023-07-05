package com.xxy.gateway.test;

import com.xxy.gateway.session.Configuration;
import com.xxy.gateway.session.GenericReferenceSessionFactoryBuilder;
import com.xxy.gateway.session.SessionServer;
import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author xuxinyi
 * @create 2023/7/4 20:50
 * @Description
 */
public class ApiTest {
    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    /**
     * 测试：http://localhost:7397/sayHi
     */
    @Test
    public void test() throws ExecutionException, InterruptedException {
        // 获取一些配置信息并封装,比如注册中心地址,服务名,方法名等....
        // 这里写死了要调用的接口的信息，假装是从配置文件读取的
        Configuration configuration = new Configuration();
        // 注册泛化调用服务接口方法,绑定http请求和rpc方法
        configuration.addGenericReference("api-gateway-test", "cn.bugstack.gateway.rpc.IActivityBooth", "sayHi");

        // 新建一个会话工厂建造类
        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
        // 通过会话工厂建造类创建一个会话工厂
        Future<Channel> future = builder.build(configuration);

        logger.info("服务启动完成 {}", future.get().id());

        Thread.sleep(Long.MAX_VALUE);
    }
}
