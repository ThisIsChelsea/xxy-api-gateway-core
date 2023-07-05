package com.xxy.gateway.session;

import com.xxy.gateway.session.defaults.GenericReferenceSessionFactory;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author xuxinyi
 * @create 2023/7/5 10:11
 * @Description 会话工厂建造类
 */
public class GenericReferenceSessionFactoryBuilder {

    /**
     * 通过会话工厂建造类创建一个会话工厂,并返回一个会话
     * @param configuration
     * @return
     */
    public Future<Channel> build(Configuration configuration) {
        GenericReferenceSessionFactory factory = new GenericReferenceSessionFactory(configuration);

        try {
            return factory.openSession();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
