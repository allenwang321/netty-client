package com.bestboke.nettyclient.learn3;

import com.bestboke.nettycommons.DefaultParameter;
import com.betboke.utils.TimeUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String [] args){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel ch){
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        connect(bootstrap, "127.0.0.1", DefaultParameter.DEFAULT_PORT, DefaultParameter.MAX_RETYR);

    }

    private static void connect(Bootstrap bootstrap,String host, int port, int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("连接成功");
            }else if(retry == 0){
                System.out.println("重试次数已用完，放弃连接");
            }else {
                int order = (DefaultParameter.MAX_RETYR - retry) + 1;
                int delay = 1 << order;
                System.out.println(TimeUtils.getNow() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(()->connect(bootstrap, host, port, retry-1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
