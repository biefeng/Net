package com.net.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 *@Author BieFeNg
 *@Date 2019/3/28 15:16
 *@DESC
 */
public class EchoInBoundChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[1024];
        byteBuf.getBytes(0, bytes);
        System.out.println("getData: " + new String(bytes));
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer().writeBytes("Replay".getBytes());
        ctx.write(byteBuf1);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
