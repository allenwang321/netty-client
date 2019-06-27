package com.bestboke.nettyclient.learn4.messagehandler;

import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettypacket.request.MessageRequestPacket;
import com.bestboke.nettycommons.nettypacket.response.MessageResponsePacket;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import com.betboke.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class MessageResponseBiz implements ChannelRead {
    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {

        MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;

        System.out.println(TimeUtils.getNow() + ": 收到服务端消息: " + messageResponsePacket.getMessage());


    }
}
