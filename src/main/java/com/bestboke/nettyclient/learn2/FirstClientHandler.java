package com.bestboke.nettyclient.learn2;

import com.betboke.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(TimeUtils.getNow() + ": 客户端写出数据");
        ByteBuf buffer = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        byte [] bytes = "你好，我是客户端".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(TimeUtils.getNow() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
