package com.net.netty;

import com.net.netty.handler.EchoInBoundChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/*
 *@Author BieFeNg
 *@Date 2019/3/28 14:27
 *@DESC
 */
public class NettyMain {

    public static void listen() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            serverBootstrap.group(bossgroup, workGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG)).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoInBoundChannelHandler()).addLast(new EchoInBoundChannelHandler());
                        }
                    }).
                    localAddress(new InetSocketAddress("127.0.0.1", 8388));

            ChannelFuture channelFuture = serverBootstrap.bind().sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            bossgroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        listen();
    }
}
