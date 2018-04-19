package com.mogu.LOT.api.action;

import com.mogu.LOT.model.entity.BaseTypeDo;
import com.mogu.LOT.model.entity.MessageTypeDo;
import com.mogu.LOT.model.entity.SensorTypeDo;
import com.mogu.LOT.service.BaseTypeService;
import com.mogu.LOT.util.WebResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingCtrl extends  BaseCtrl{



    @Autowired
    private BaseTypeService messageTypeService;

    @Autowired
    private BaseTypeService sensorTypeService;



    /**
     * 注册消息类型
     * @return
     */
    @RequestMapping(value = "setting/messagetype/register",method = RequestMethod.POST)
    public WebResult registerMessage(@RequestBody MessageTypeDo messageTypeDo){
        return  new WebResult(messageTypeService.register(messageTypeDo));
    }


    /**
     * 获取消息类型
     * @return
     */
    @RequestMapping(value = "setting/messagetype/get",method = RequestMethod.POST)
    public WebResult getMessage(@RequestBody MessageTypeDo messageTypeDo){
        return  new WebResult(messageTypeService.get(messageTypeDo));
    }


    /**
     * 禁用消息类型
     * @return
     */
    @RequestMapping(value = "setting/messagetype/disable",method = RequestMethod.POST)
    public WebResult disableMessage(@RequestBody MessageTypeDo messageTypeDo){
        messageTypeDo.setValid(0);
        return  new WebResult(messageTypeService.modify(messageTypeDo));
    }

    /**
     * 启用消息类型
     * @return
     */
    @RequestMapping(value = "setting/messagetype/enable",method = RequestMethod.POST)
    public WebResult enableMessage(@RequestBody MessageTypeDo messageTypeDo){
        messageTypeDo.setValid(1);
        return  new WebResult(messageTypeService.modify(messageTypeDo));
    }

    /**
     * 修改消息类型
     * @return
     */
    @RequestMapping(value = "setting/messagetype/modify",method = RequestMethod.POST)
    public WebResult modifyMessage(@RequestBody MessageTypeDo messageTypeDo){
        return  new WebResult(messageTypeService.modify(messageTypeDo));
    }

    /**
     * 消息类型列表
     * @return
     */
    @RequestMapping(value = "setting/messagetype/lst",method = RequestMethod.POST)
    public WebResult lstMessgae(@RequestParam(value = "pageNum",required = false) Integer pageNum, @RequestParam(value = "pageSize",required = false) Integer pageSize,@RequestParam(value = "direction",required = false) Integer direction,@RequestParam(value = "code",required = false) String code){
        MessageTypeDo messageTypeDo = new MessageTypeDo();
        if(direction != null && direction != 0){
            messageTypeDo.setDirection(direction);
        }
        if(code != null && code.length() > 0){
            messageTypeDo.setCode(code);
        }
        return  new WebResult(messageTypeService.lst(pageNum,pageSize,messageTypeDo));
    }


    /**
     * 注册传感器类型
     * @return
     */
    @RequestMapping(value = "setting/sensortype/register",method = RequestMethod.POST)
    public WebResult registerSensorType(@RequestBody SensorTypeDo sensorTypeDo){
        return  new WebResult(sensorTypeService.register(sensorTypeDo));
    }

    /**
     * 删除传感器类型
     * @return
     */
    @RequestMapping(value = "setting/sensortype/del",method = RequestMethod.POST)
    public WebResult deleteSensorType(@RequestBody SensorTypeDo sensorTypeDo){
        return  new WebResult(sensorTypeService.delete(sensorTypeDo.getId()));
    }

    /**
     * 修改传感器类型
     * @return
     */
    @RequestMapping(value = "setting/sensortype/modify",method = RequestMethod.POST)
    public WebResult modifySensorType(@RequestBody SensorTypeDo sensorTypeDo){
        return  new WebResult(sensorTypeService.modify(sensorTypeDo));
    }

    /**
     * 获取传感器类型
     * @return
     */
    @RequestMapping(value = "setting/sensortype/get",method = RequestMethod.POST)
    public WebResult getSensorType(@RequestBody SensorTypeDo sensorTypeDo){
        return  new WebResult(sensorTypeService.get(sensorTypeDo));
    }

    /**
     * 传感器类型列表
     * @return
     */
    @RequestMapping(value = "setting/sensortype/lst",method = RequestMethod.POST)
    public WebResult lstSensor(@RequestParam(value = "pageNum",required = false) Integer pageNum, @RequestParam(value = "pageSize",required = false) Integer pageSize ,@RequestParam(value = "text",required = false) String text){
        BaseTypeDo sensorTypeDo = new SensorTypeDo();
        if(!StringUtils.isEmpty(text)){
            sensorTypeDo.setCode("%"+text+"%");
        }
        return  new WebResult(sensorTypeService.lst(pageNum,pageSize,sensorTypeDo));
    }



}
