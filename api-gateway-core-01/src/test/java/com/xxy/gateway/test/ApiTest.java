package com.xxy.gateway.test;

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

    @Test
    public void test() throws ExecutionException, InterruptedException {
        SessionServer server = new SessionServer();

        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty server start error , channel is null");

        while (!channel.isActive()) {
            logger.info("NettyServer服务器启动中.....");
            Thread.sleep(500);
        }
        logger.info("NettyServer启动服务器成功... {}", channel.localAddress());
        Thread.sleep(Long.MAX_VALUE);
    }
}
