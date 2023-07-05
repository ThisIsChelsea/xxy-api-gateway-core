package com.xxy.gateway.session;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author xuxinyi
 * @create 2023/7/5 10:13
 * @Description 泛化调用会话工厂接口
 */
public interface IGenericReferenceSessionFactory {
    Future<Channel> openSession() throws ExecutionException, InterruptedException;
}
