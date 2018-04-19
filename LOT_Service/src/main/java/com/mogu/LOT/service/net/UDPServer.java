package com.mogu.LOT.service.net;

import com.mogu.LOT.service.MessageService;
import com.mogu.LOT.service.ResultService;
import com.mogu.LOT.service.TerminalService;
import com.mogu.LOT.service.net.handler.UDPServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UDPServer {

    private final TerminalService terminalService;
    private final MessageService messageService;
    private final ResultService resultService;


    public UDPServer(TerminalService terminalService, MessageService messageService,ResultService resultService) {
        this.terminalService = terminalService;
        this.messageService = messageService;
        this.resultService = resultService;
    }

    public void run(int port) throws Exception{
        UDPServerHandler handle = new UDPServerHandler();
        handle.setTerminalService(terminalService);
        handle.setMessageService(messageService);
        handle.setResultService(resultService);
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        //由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
        b.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)//支持广播
                .handler(handle);//ChineseProverbServerHandler是业务处理类
        b.bind(port).sync().channel().closeFuture().await();
    }
}
