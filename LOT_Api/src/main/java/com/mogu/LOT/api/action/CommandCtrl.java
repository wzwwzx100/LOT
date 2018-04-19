package com.mogu.LOT.api.action;

import com.mogu.LOT.model.entity.CommandDo;
import com.mogu.LOT.model.params.CommandParam;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.service.CommandService;
import com.mogu.LOT.util.BizResult;
import com.mogu.LOT.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommandCtrl extends BaseCtrl {


    @Autowired
    private CommandService commandService;


    @RequestMapping(value = "command/send",method = RequestMethod.POST)
    public WebResult send(@RequestBody CommandDo commandDo){
        return  new WebResult(commandService.send(commandDo));
    }

    @RequestMapping(value = "command/del",method = RequestMethod.POST)
    public WebResult del(@RequestBody CommandDo commandDo){
        return  new WebResult(commandService.del(commandDo));
    }


    @RequestMapping(value = "command/lst",method = RequestMethod.POST)
    public WebResult lst(@RequestBody CommandParam commandDo){
        return  new WebResult(commandService.lst(commandDo.getPageNum(),commandDo.getPageSize(),commandDo));
    }

    /**
     * 下发配置
     * @return
     */
    @RequestMapping(value = "command/find",method = RequestMethod.POST)
    public WebResult find(@RequestBody TerminalDo terminalDo){
        return  new WebResult(BizResult.success(commandService.findFailed(terminalDo)));
    }
}
