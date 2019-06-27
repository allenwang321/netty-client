package com.bestboke.nettyclient.learn4.messagehandler;

import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettypacket.request.LoginRequestPacket;
import com.bestboke.nettycommons.nettypacket.response.LoginResponsePacket;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import com.bestboke.nettycommons.util.LoginUtil;
import com.betboke.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class LoginResponseBiz implements ChannelRead {

    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {
        LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
        if (loginResponsePacket.isSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(TimeUtils.getNow() + ": 客户端登录成功");
        } else {
            System.out.println(TimeUtils.getNow() + ": 客户端登录失败，原因:" + loginResponsePacket.getReason());
        }

    }

}
