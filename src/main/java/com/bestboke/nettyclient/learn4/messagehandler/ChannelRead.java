package com.bestboke.nettyclient.learn4.messagehandler;

import com.bestboke.nettycommons.nettypacket.Packet;
import io.netty.channel.ChannelHandlerContext;

public interface ChannelRead {

    void doChannelRead(ChannelHandlerContext ctx, Packet packet);

}
