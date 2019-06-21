package com.bestboke.nettyclient.learn1;

import com.betboke.utils.TimeUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                // 创建连接
                Socket socket = new Socket("127.0.0.1", 8000);
                // 发送数据
                while (true) {
                    socket.getOutputStream().write((TimeUtils.getNow() + " : hello world").getBytes());
                    try {
                        TimeUnit.SECONDS.sleep(2L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
