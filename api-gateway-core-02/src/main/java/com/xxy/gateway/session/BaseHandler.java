package com.xxy.gateway.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author xuxinyi
 * @create 2023/7/4 10:10
 * @Description 数据处理器基类
 */
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {

    /**
     * 定义读取数据的方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        session(ctx, ctx.channel(), msg);
    }

    /**
     * 定义会话方法,具体实现由子类实现
     * @param ctx
     * @param channel
     * @param request
     */
    protected abstract void session(ChannelHandlerContext ctx, final Channel channel, T request);
}
