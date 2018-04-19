package com.mogu.LOT.service.net;

import com.mogu.LOT.service.net.handler.UDPClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class UDPClient {

    private String hostname;
    private int port;


    public UDPClient(String hostname,int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void run(Long cmd_id,String msg) throws Exception{
        EventLoopGroup group  = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)//允许广播
                    .handler(new UDPClientHandler(cmd_id));//设置消息处理器
            Channel ch = b.bind(0).sync().channel();
            //向网段内的所有机器广播UDP消息。
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8), new InetSocketAddress(hostname,port))).sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}
