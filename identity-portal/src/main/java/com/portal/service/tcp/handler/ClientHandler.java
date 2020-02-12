package com.portal.service.tcp.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * @Author: zt
 * @Date: 2018/9/12 16:01
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private String result;

    public String getContent() {
        return result;
    }

    /**
     * 写数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 读取对方返回的数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = (String) msg;
    }

    /**
     * 读取完毕的回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }

}
