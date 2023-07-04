package com.xxy.gateway.session;

import com.xxy.gateway.session.handlers.SessionServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


/**
 * @Author xuxinyi
 * @create 2023/7/4 17:12
 * @Description ChannelInitializer是一个特殊的ChannelHandler，用于初始化新创建的Channel。它在Channel被添加到ChannelPipeline时自动执行
 */
public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline line = channel.pipeline();
        line.addLast(new HttpRequestDecoder());
        line.addLast(new HttpResponseEncoder());
        // 用于处理除了GET请求以外的POST请求的对象信息
        line.addLast(new HttpObjectAggregator(1024 * 1024));
        // 我们自己实现的会话处理,用于拿到HTTP请求后，处理自己的业务逻辑
        line.addLast(new SessionServerHandler());
    }
}
