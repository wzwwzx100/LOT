package com.mogu.LOT.api.action;

import com.mogu.LOT.model.entity.SensorDo;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.model.params.TerminalParam;
import com.mogu.LOT.service.SensorService;
import com.mogu.LOT.service.TerminalService;
import com.mogu.LOT.util.BizResult;
import com.mogu.LOT.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceCtrl extends  BaseCtrl{


    @Autowired
    private TerminalService terminalService;


    @Autowired
    private SensorService sensorService;


    /**
     * 注册终端
     * @return
     */
    @RequestMapping(value = "device/register",method = RequestMethod.POST)
    public WebResult register(@RequestBody TerminalDo terminal){
        return  new WebResult(terminalService.register(terminal));
    }

    /**
     * 查询终端
     * @return
     */
    @RequestMapping(value = "device/get",method = RequestMethod.POST)
    public WebResult terminalGet(@RequestBody TerminalDo terminal){
        return  new WebResult(BizResult.success(terminalService.findById(terminal.getId())));
    }


    /**
     * 终端修改
     * @return
     */
    @RequestMapping(value = "device/modify",method = RequestMethod.POST)
    public WebResult deviceModify(@RequestBody TerminalDo terminal){
        return  new WebResult(terminalService.modify(terminal));
    }


    /**
     * 终端列表
     * @return
     */
    @RequestMapping(value = "device/lst",method = RequestMethod.POST)
    public WebResult lst(@RequestBody TerminalParam terminalParam){
        return new WebResult(terminalService.lst(terminalParam.getPageNum(),terminalParam.getPageSize(),terminalParam));
    }

    /**
     * 删除终端
     * @return
     */
    @RequestMapping(value = "device/del",method = RequestMethod.POST)
    public WebResult del(@RequestBody TerminalDo terminal){
        return new WebResult(terminalService.delete(terminal));
    }


    /**
     * 离线列表
     * @return
     */
    @RequestMapping(value = "device/off_line",method = RequestMethod.POST)
    public WebResult offLine(@RequestBody TerminalParam terminalParam){
        return new WebResult(terminalService.offLine(terminalParam.getPageNum(),terminalParam.getPageSize(),terminalParam));
    }


    /**
     * 注册传感器
     * @return
     */
    @RequestMapping(value = "sensor/register",method = RequestMethod.POST)
    public WebResult registerSensor(@RequestBody SensorDo sensorDo){
        return  new WebResult(sensorService.register(sensorDo));
    }

    /**
     * 删除传感器
     * @return
     */
    @RequestMapping(value = "sensor/del",method = RequestMethod.POST)
    public WebResult sensorDel(@RequestBody SensorDo sensorDo){
        return  new WebResult(sensorService.delete(sensorDo));
    }

    /**
     * 查询传感器
     * @return
     */
    @RequestMapping(value = "sensor/get",method = RequestMethod.POST)
    public WebResult sensorGet(@RequestBody SensorDo sensorDo){
        return  new WebResult(sensorService.findById(sensorDo));
    }


    /**
     * 修改传感器
     * @return
     */
    @RequestMapping(value = "sensor/modify",method = RequestMethod.POST)
    public WebResult sensorModify(@RequestBody SensorDo sensorDo){
        return  new WebResult(sensorService.modify(sensorDo));
    }


    /**
     * 传感器列表
     * @return
     */
    @RequestMapping(value = "sensor/lst",method = RequestMethod.POST)
    public WebResult sensorlst(@RequestBody SensorDo sensorDo){
        return new WebResult(sensorService.lst(sensorDo.getTerminal()));
    }

    /**
     * 下发配置
     * @return
     */
    @RequestMapping(value = "device/issueConfig",method = RequestMethod.POST)
    public WebResult issueConfig(@RequestBody TerminalDo terminalDo){
        return  new WebResult(terminalService.issueConfig(terminalDo));
    }





}
