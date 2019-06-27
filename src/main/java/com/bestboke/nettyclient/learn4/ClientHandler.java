package com.bestboke.nettyclient.learn4;


import com.bestboke.nettyclient.learn4.messagehandler.ChannelRead;
import com.bestboke.nettyclient.learn4.messagehandler.LoginResponseBiz;
import com.bestboke.nettyclient.learn4.messagehandler.MessageResponseBiz;
import com.bestboke.nettycommons.nettypacket.Command;
import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettypacket.request.LoginRequestPacket;
import com.bestboke.nettycommons.nettypacket.response.LoginResponsePacket;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import com.betboke.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static Map<Byte, Class<? extends ChannelRead>> packetTypeMap;

    static {
        packetTypeMap = new ConcurrentHashMap<>();
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponseBiz.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponseBiz.class);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(TimeUtils.getNow() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(10001);
        loginRequestPacket.setUserName("AllenWang");
        loginRequestPacket.setPassword("pwd");
        // 编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写数据
        ctx.channel().writeAndFlush(buffer);

    }


    public void channelRead(ChannelHandlerContext ctx, Object msg){

        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        Class<? extends ChannelRead> channelRead = getRequestType(packet.getCommand());
        try {
            channelRead.newInstance().doChannelRead(ctx, packet);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private Class<? extends ChannelRead> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
