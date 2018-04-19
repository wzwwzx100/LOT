package com.mogu.LOT.api.listener;

import com.mogu.LOT.service.MessageService;
import com.mogu.LOT.service.ResultService;
import com.mogu.LOT.service.TerminalService;
import com.mogu.LOT.service.net.UDPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UDPListener implements ServletContextListener{

    Logger log = LoggerFactory.getLogger(UDPListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        final TerminalService terminalService = applicationContext.getBean(TerminalService.class);
        final MessageService messageService = applicationContext.getBean(MessageService.class);
        final ResultService resultService = applicationContext.getBean(ResultService.class);
        new Thread(){
            @Override
            public void run(){
                try {
                    new UDPServer(terminalService,messageService,resultService).run(3320);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
