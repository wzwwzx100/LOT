package com.mogu.LOT.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.constants.CommonConstant;
import com.mogu.LOT.model.common.SystemConfig;
import com.mogu.LOT.model.entity.*;
import com.mogu.LOT.model.params.TerminalParam;
import com.mogu.LOT.service.CommandService;
import com.mogu.LOT.service.SystemConfigService;
import com.mogu.LOT.service.TerminalService;
import com.mogu.LOT.service.net.UDPClient;
import com.mogu.LOT.util.BizResult;
import com.mogu.LOT.dao.mapper.MessageTypeDoMapper;
import com.mogu.LOT.dao.mapper.SensorDoMapper;
import com.mogu.LOT.dao.mapper.TerminalDoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TerminalServiceImpl implements TerminalService {


    @Autowired
    private TerminalDoMapper terminalDoMapper;

    @Autowired
    private SensorDoMapper sensorDoMapper;

    @Autowired
    private MessageTypeDoMapper messageTypeDoMapper;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private CommandService commandService;

    private static final Logger log = LoggerFactory.getLogger(TerminalServiceImpl.class);
    @Override
    public BizResult register(TerminalDo terminal) {
        terminal.setKeyt(CommonConstant.KEY);
        TerminalDo exist = terminalDoMapper.findById(terminal.getId());
        if(exist != null){
            return BizResult.error(ResultCode.CODE_EXIST);
        }
        int ret = terminalDoMapper.register(terminal);
        if(ret == 1){
            return BizResult.success();
        }
        return BizResult.error("注册失败，请重试！");
    }

    @Override
    public BizResult modify(TerminalDo terminal) {
        int ret = terminalDoMapper.modify(terminal);
        if(ret == 1){
            return BizResult.success();
        }
        return BizResult.error("修改失败，请重试！");
    }

    @Override
    public BizResult delete(TerminalDo terminal) {
        int ret = terminalDoMapper.modify(terminal);
        if(ret == 1){
            return BizResult.success();
        }
        return BizResult.error("删除失败，请重试！");
    }

    @Override
    public BizResult lst(int pageNum, int pageSize,TerminalDo terminalDo) {
        String orderBy = "id asc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<TerminalDo> lst = terminalDoMapper.lst(terminalDo);
        PageInfo pageInfo = new PageInfo(lst);
        return BizResult.success(pageInfo);
    }

    @Override
    public BizResult offLine(int pageNum, int pageSize, TerminalParam terminalDo) {
        String orderBy = "id asc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        terminalDo.setTime(terminalDo.getTime()*60);
        List<TerminalDo> lst = terminalDoMapper.offLine(terminalDo);
        PageInfo pageInfo = new PageInfo(lst);
        return BizResult.success(pageInfo);
    }

    @Override
    public TerminalDo findById(String id) {
        return terminalDoMapper.findById(id);
    }

    @Override
    public BizResult issueConfig(TerminalDo terminalDo) {
        return  BizResult.error();
    }


    @Override
    public BizResult resendCommand(TerminalDo terminalDo) {
        List<CommandDo> cmds = commandService.findFailed(terminalDo);
        if(cmds != null && !cmds.isEmpty()){
            for(CommandDo item : cmds){
                commandService.send(item);
            }
        }
        return BizResult.success();
    }


    @Override
    public MessageDo getCommand(TerminalDo terminalDo) {
        List<CommandDo> cmds = commandService.findFailed(terminalDo);
        if(cmds != null && !cmds.isEmpty()){
            try{
                return commandService.createMessage(cmds.get(0));
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return null;
            }
        }
        return null;
    }

    @Override
    public BizResult successCommand(Long cmd_id) {
        return commandService.success(cmd_id);
    }

}
