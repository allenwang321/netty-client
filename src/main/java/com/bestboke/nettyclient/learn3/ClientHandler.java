package com.bestboke.nettyclient.learn3;


import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettypacket.request.LoginRequestPacket;
import com.bestboke.nettycommons.nettypacket.response.LoginResponsePacket;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import com.betboke.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

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
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if(loginResponsePacket.isSuccess()){
                System.out.println(TimeUtils.getNow() + ": 客户端登录成功");
            }else {
                System.out.println(TimeUtils.getNow() + ": 客户端登录失败，原因:" + loginResponsePacket.getReason());
            }
        }

    }

}
