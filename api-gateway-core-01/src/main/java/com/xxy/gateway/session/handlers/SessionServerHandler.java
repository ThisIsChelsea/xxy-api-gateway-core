package com.xxy.gateway.session.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xxy.gateway.session.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author xuxinyi
 * @create 2023/7/4 16:59
 * @Description 会话服务处理器
 */
public class SessionServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(SessionServerHandler.class);

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("网关接受请求 uri : {} method: {} ", request.uri(), request.method());
        // 返回信息处理
        // DefaultFullHttpResponse相当于在构建HTTP会话所需的协议信息，包括头信息、编码、响应体长度、跨域访问等....
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        // 返回信息控制
        response.content().writeBytes(JSON.toJSONBytes("你访问路径被网关管理了 URI：" + request.uri(), SerializerFeature.PrettyFormat));
        // 头部信息设置
        HttpHeaders headers = response.headers();
        // 返回内容类型
        headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + "; charset=UTF-8");
        // 响应体的长度
        headers.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // 配置持久连接
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        // 配置跨域访问
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

        channel.writeAndFlush(response);
    }
}
