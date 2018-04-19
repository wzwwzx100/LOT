package com.mogu.LOT.api.action;

import com.mogu.LOT.service.MessageService;
import com.mogu.LOT.service.ResultService;
import com.mogu.LOT.util.WebResult;
import com.mogu.LOT.model.params.MessageParam;
import com.mogu.LOT.model.params.ResultParam;
import com.mogu.LOT.model.entity.TerminalDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultCtrl extends  BaseCtrl {



    @Autowired
    private ResultService resultService;

    @Autowired
    private MessageService messageService;


    /**
     * 数据列表
     * @return
     */
    @RequestMapping(value = "result/lst",method = RequestMethod.POST)
    public WebResult datalst(@RequestBody ResultParam result){
        return new WebResult(resultService.lst(result.getPageNum(),result.getPageSize(),result));
    }


    /**
     * 交易消息列表
     * @return
     */
    @RequestMapping(value = "message/lst",method = RequestMethod.POST)
    public WebResult msglst(@RequestBody MessageParam msg){
        return new WebResult(messageService.lst(msg));
    }


    /**
     * 实时数据
     * @param terminalDo
     * @return
     */
    @RequestMapping(value = "result/realValue",method = RequestMethod.POST)
    public WebResult realValue(@RequestBody TerminalDo terminalDo){
        return  new WebResult(resultService.realValue(terminalDo));
    }


}
