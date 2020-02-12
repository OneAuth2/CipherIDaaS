package com.portal.service.tcp.client;


import com.google.gson.Gson;
import com.portal.domain.BaseReq;
import com.portal.service.tcp.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Author: zt
 * @Date: 2018/9/12 14:09
 */

//@Component
//@Configurable
//@Configuration
public class SockerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SockerClient.class);

    //    @Value("${account.server.ip}")
/*    private String host = "192.168.1.21";

    //    @Value("${account.server.port}")
    private Integer port = 6910;*/


    public String sendMessage(String host, int port, BaseReq baseRequest) {
        String msg = new Gson().toJson(baseRequest);
        msg += "\r\n";
        ChannelFuture future = null;
        ClientHandler clientHandler = new ClientHandler();
        SocketChannel channel = null;
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInit(clientHandler));
            // 等待客户端链接成功
            future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                LOGGER.info("启动客户端成功!");
            }
            channel = (SocketChannel) future.channel();
            channel.writeAndFlush(msg);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error("SockerClient进入异常");
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        String res = clientHandler.getContent();
        LOGGER.info("获取到的返回数据:" + res);
        return res;
    }
}


class ChannelInit extends ChannelInitializer<SocketChannel> {

    private ClientHandler clientHandlerl;

    ChannelInit(ClientHandler clientHandler) {
        this.clientHandlerl = clientHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast(new StringEncoder());
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast(clientHandlerl);
    }
}
