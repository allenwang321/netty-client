package com.bestboke.nettyclient.learn1;

import com.bestboke.nettycommons.DefaultParameter;
import com.betboke.utils.TimeUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {


        Bootstrap bootstrap = new Bootstrap();

        // 创建一个线程组
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 指定线程模型
        bootstrap.group(group);
        // 指定IO类型
        bootstrap.channel(NioSocketChannel.class);
        // IO处理逻辑
        bootstrap.handler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new StringEncoder());
            }
        });
//        // 建立连接
//        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
//
//
//        while (true){
//            channel.writeAndFlush(TimeUtils.getNow() + " : hello world!");
//            TimeUnit.SECONDS.sleep(2L);
//        }
        connect(bootstrap, "127.0.0.1", DefaultParameter.DEFAULT_PORT, DefaultParameter.MAX_RETYR);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else if (retry == 0) {
                System.out.println("重试次数已经用完，放弃连接！");
            } else {
                int order = (DefaultParameter.MAX_RETYR - retry) + 1;
                int delay = 1 << order;
                System.out.println(TimeUtils.getNow() + "：连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
