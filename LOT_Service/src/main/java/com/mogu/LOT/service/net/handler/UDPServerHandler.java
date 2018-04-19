package com.mogu.LOT.service.net.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogu.LOT.model.entity.MessageDo;
import com.mogu.LOT.model.entity.MsgInfoDo;
import com.mogu.LOT.model.entity.ResultDo;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.service.MessageService;
import com.mogu.LOT.service.ResultService;
import com.mogu.LOT.service.TerminalService;
import com.mogu.LOT.service.net.UDPServer;
import com.mogu.LOT.util.BizResult;
import com.mogu.LOT.util.MD5Util;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    Logger log = LoggerFactory.getLogger(UDPServer.class);

    private TerminalService terminalService;

    private MessageService messageService;

    private ResultService resultService;

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public void setTerminalService(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public ResultService getResultService() {
        return resultService;
    }

    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    @Transactional
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);//上面说了，通过content()来获取消息内容.
        log.info(req);
        String[] reqs = req.split("###");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String ip =  packet.sender().getHostString();
        int port = packet.sender().getPort();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //应答消息
        MessageDo returnMsg = new MessageDo();
        returnMsg.setSid("000000000000");
        returnMsg.setTs(sdf.format(new Date()));
        returnMsg.setLife(-1);
        MessageDo messageDo = null;
        String final_string = "";
        OUT:
        for (String ss : reqs) {
            if(ss.length() == 0){
                break;
            }
            try {
                messageDo = mapper.readValue(ss, MessageDo.class);
            }catch (Exception e){
                //异常1、JSON格式错误
                log.error(e.getMessage(), e);
                log.error("异常1、JSON格式错误");
                Map<String, Integer> msg = new HashMap<String, Integer>();
                msg.put("err", 1);
                returnMsg.setSv(1);
                returnMsg.setCv(1);
                returnMsg.setSq(0l);
                returnMsg.setMid("1992");
                returnMsg.setSign("");
                returnMsg.setMsg(mapper.writeValueAsString(msg));
                msg = null;
                //step 2.保存消息记录1
                MsgInfoDo msgInfoDo = new MsgInfoDo();
                msgInfoDo.setAtTime(new Date());
                msgInfoDo.setLife(-1);
                msgInfoDo.setTerminalId("000000000000");
                msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                msgInfoDo.setMsg(returnMsg.getMsg());
                msgInfoDo.setMsgCode("1992");
                msgInfoDo.setMsgCodeName("平台回复交易异常");
                msgInfoDo.setSq(0l);
                msgInfoDo.setPrivateMsg(returnMsg.getMsg());
                msgInfoDo.setMsgType(1);
                messageService.addMessage(msgInfoDo);
                msgInfoDo = null;
                final_string += mapper.writeValueAsString(returnMsg)+"###";
                break OUT;
            }
                //参数是否完全验证
                if(!messageDo.lessAttr()){
                    returnMsg.setSq(messageDo.getSq());
                    returnMsg.setSv(messageDo.getSv());
                    returnMsg.setCv(messageDo.getCv());
                    Date start = sdf.parse(messageDo.getTs());
                    Date now = new Date();
                    Long l = (now.getTime() - start.getTime())/1000;
                    //消息是否过期验证
                    if(-1 == messageDo.getLife() || l < messageDo.getLife()){
                        TerminalDo terminalDo = terminalService.findById(messageDo.getSid());
                        if(terminalDo != null){
                            //step 1.验证sign
                            String str = MessageFormat.format("{0}{1}{2}{3}{4}{5}", messageDo.getTs(), messageDo.getSq().toString(), messageDo.getSid(), messageDo.getMid(), messageDo.getMsg(), terminalDo.getKeyt());
                            String md5 = MD5Util.toMD5(str);
                            md5 = md5.substring(0,8);
                            //解析
                            String mid = messageDo.getMid();
                            if((mid.equals("1994") && messageDo.getSign() != null )|| md5.equals(messageDo.getSign())){//异常无需签名
                                if(mid.equals("1011")) {//终端发送心跳
                                //step 2.保存消息记录1
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("心跳");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);

                                if(bizResult.equals(BizResult.error())){
                                    log.error(bizResult.getMessage());
                                }
                                //step 3 更新终端信息
                                TerminalDo newTerminal = new TerminalDo();
                                newTerminal.setId(terminalDo.getId());
                                newTerminal.setIp(ip);
                                newTerminal.setPort(port);
                                newTerminal.setLastTime(sdf.parse(messageDo.getTs()));
                                bizResult = terminalService.modify(newTerminal);

                                if(bizResult.equals(BizResult.error())){
                                    log.error(bizResult.getMessage());
                                }
                                //step 4  查询下发命令
                                MessageDo commandMsg = terminalService.getCommand(terminalDo);
                                if(commandMsg == null){
                                    //step 5 心跳应答
                                    returnMsg.setMid("1012");
                                    returnMsg.setMsg("");
                                    returnMsg.Encrypt(terminalDo.getKeyt());
                                    msgInfoDo = null;
                                    msgInfoDo = new MsgInfoDo();
                                    msgInfoDo.setAtTime(sdf.parse(returnMsg.getTs()));
                                    msgInfoDo.setLife(-1);
                                    msgInfoDo.setTerminalId("000000000000");
                                    msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                                    msgInfoDo.setMsg("");
                                    msgInfoDo.setMsgCode("1012");
                                    msgInfoDo.setMsgCodeName("心跳应答");
                                    msgInfoDo.setSq(returnMsg.getSq());
                                    msgInfoDo.setPrivateMsg("");
                                    msgInfoDo.setMsgType(1);
                                    bizResult = messageService.addMessage(msgInfoDo);
                                    if(bizResult.equals(BizResult.error())){
                                        log.error("1012 message save error!");
                                        log.error(bizResult.getMessage());
                                    }
                                }else{
                                    returnMsg = commandMsg;
                                    returnMsg.setSq(messageDo.getSq());
                                    returnMsg.setSv(messageDo.getSv());
                                    returnMsg.setCv(messageDo.getCv());
                                    returnMsg.Encrypt(terminalDo.getKeyt());
                                    String jsonText = mapper.writeValueAsString(messageDo);
                                    //step 5 保存响应交易记录
                                    msgInfoDo = new MsgInfoDo();
                                    msgInfoDo.setTerminalId(returnMsg.getSid());
                                    msgInfoDo.setLife(-1);
                                    msgInfoDo.setSq(messageDo.getSq());
                                    msgInfoDo.setAtTime(new Date());
                                    msgInfoDo.setMsg(returnMsg.getMsg());
                                    msgInfoDo.setPrivateMsg(returnMsg.getMsg());
                                    msgInfoDo.setMsgCode("1143");
                                    msgInfoDo.setMsgCodeName("下发配置");
                                    msgInfoDo.setMsgType(1);
                                    msgInfoDo.setJsonText(jsonText);
                                    messageService.addMessage(msgInfoDo);
                                }
                                msgInfoDo = null;
                                newTerminal = null;
                            }else if(mid.equals("1144")){//下发配置命令返回
                                //step 1.保存消息记录
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("配置命令答复");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1144 message save error!");
                                    log.error(bizResult.getMessage());
                                }

                                // step 2 更新命令状态
                                Map<String,Object> msgMap = mapper.readValue(messageDo.getMsg(),new TypeReference<Map<String,Object>>(){});
                                Integer result = (int) msgMap.get("result");
                                if(result == 0){
                                    bizResult = terminalService.successCommand(Long.parseLong(msgMap.get("cmd_id").toString()));
                                    if(bizResult.equals(BizResult.error())){
                                        log.error(bizResult.getMessage());
                                    }
                                }
                                msgInfoDo = null;
                                bizResult = null;
                                returnMsg = null;
                                return;
                            }else if(mid.equals("1051")){
                                //step 1.保存消息记录
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("上传调试日志");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1051 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                //step 2 更新终端信息
                                TerminalDo newTerminal = new TerminalDo();
                                newTerminal.setId(terminalDo.getId());
                                newTerminal.setIp(ip);
                                newTerminal.setPort(port);
                                newTerminal.setLastTime(sdf.parse(messageDo.getTs()));
                                bizResult = terminalService.modify(newTerminal);
                                if(bizResult.equals(BizResult.error())){
                                    log.error(bizResult.getMessage());
                                }
                                //step 3 应答
                                returnMsg.setMid("1052");
                                returnMsg.setMsg("");
                                returnMsg.Encrypt(terminalDo.getKeyt());
                                msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(returnMsg.getTs()));
                                msgInfoDo.setLife(-1);
                                msgInfoDo.setTerminalId("000000000000");
                                msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                                msgInfoDo.setMsg("");
                                msgInfoDo.setMsgCode("1052");
                                msgInfoDo.setMsgCodeName("调试日志应答");
                                msgInfoDo.setSq(returnMsg.getSq());
                                msgInfoDo.setPrivateMsg("");
                                msgInfoDo.setMsgType(1);
                                bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1052 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                msgInfoDo = null;
                                newTerminal = null;
                            }else if(mid.equals("1061")){
                                //step 1.保存消息记录1
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("上传异常报告");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1061 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                //step 2 更新终端信息
                                TerminalDo newTerminal = new TerminalDo();
                                newTerminal.setId(terminalDo.getId());
                                newTerminal.setIp(ip);
                                newTerminal.setPort(port);
                                newTerminal.setLastTime(sdf.parse(messageDo.getTs()));
                                bizResult = terminalService.modify(newTerminal);
                                if(bizResult.equals(BizResult.error())){
                                    log.error(bizResult.getMessage());
                                }
                                //step 3 应答
                                returnMsg.setMid("1062");
                                returnMsg.setMsg("");
                                returnMsg.Encrypt(terminalDo.getKeyt());
                                msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(returnMsg.getTs()));
                                msgInfoDo.setLife(-1);
                                msgInfoDo.setTerminalId("000000000000");
                                msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                                msgInfoDo.setMsg("");
                                msgInfoDo.setMsgCode("1062");
                                msgInfoDo.setMsgCodeName("上传异常应答");
                                msgInfoDo.setSq(returnMsg.getSq());
                                msgInfoDo.setPrivateMsg("");
                                msgInfoDo.setMsgType(1);
                                bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1062 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                msgInfoDo = null;
                                newTerminal = null;
                            }else if(mid.equals("1081")){
                                //step 1.保存消息记录1
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("上传短信内容");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1081 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                //step 2 更新终端信息
                                TerminalDo newTerminal = new TerminalDo();
                                newTerminal.setId(terminalDo.getId());
                                newTerminal.setIp(ip);
                                newTerminal.setPort(port);
                                newTerminal.setLastTime(sdf.parse(messageDo.getTs()));
                                bizResult = terminalService.modify(newTerminal);
                                if(bizResult.equals(BizResult.error())){
                                    log.error(bizResult.getMessage());
                                }
                                //step 3 应答
                                returnMsg.setMid("1082");
                                returnMsg.setMsg("");
                                returnMsg.Encrypt(terminalDo.getKeyt());
                                msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(returnMsg.getTs()));
                                msgInfoDo.setLife(-1);
                                msgInfoDo.setTerminalId("000000000000");
                                msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                                msgInfoDo.setMsg("");
                                msgInfoDo.setMsgCode("1082");
                                msgInfoDo.setMsgCodeName("上传短信内容应答");
                                msgInfoDo.setSq(returnMsg.getSq());
                                msgInfoDo.setPrivateMsg("");
                                msgInfoDo.setMsgType(1);
                                bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1052 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                msgInfoDo = null;
                                newTerminal = null;
                            }else if(mid.equals("2011")){//上传数据
                                //step 1.保存消息记录1
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("上传数据");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1051 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                //step 2 更新终端信息
                                TerminalDo newTerminal = new TerminalDo();
                                newTerminal.setId(terminalDo.getId());
                                newTerminal.setIp(ip);
                                newTerminal.setPort(port);
                                newTerminal.setLastTime(sdf.parse(messageDo.getTs()));
                                bizResult = terminalService.modify(newTerminal);
                                if(bizResult.equals(BizResult.error())){
                                    log.error(bizResult.getMessage());
                                }
                                //step 3 保存数据
                                String msg = messageDo.getMsg();
                                if(msg != null){
                                    Map<String,Object> res = mapper.readValue(msg,new TypeReference<Map<String,Object>>(){});
                                    if(res != null && !res.isEmpty()){
                                        List<Map<String,Object>> rcds = (List<Map<String, Object>>) res.get("rcds");
                                        for(Map<String ,Object> item : rcds){
                                            if(item.isEmpty()){
                                                Map<String, Integer> msgMap = new HashMap<String, Integer>();
                                                msgMap.put("err", 1);
                                                returnMsg.setSq(0l);
                                                returnMsg.setMid("1992");
                                                returnMsg.setSign("");
                                                returnMsg.setMsg(mapper.writeValueAsString(msgMap));
                                                msgMap = null;
                                                res = null;
                                                msg = null;
                                                final_string += mapper.writeValueAsString(returnMsg)+"###";
                                                break OUT;
                                            }
                                            ResultDo resultDo = new ResultDo();
                                            resultDo.setTerminal(newTerminal);
                                            resultDo.setTime(sdf.parse(messageDo.getTs()));
                                            resultDo.setValue(Double.parseDouble(item.get("v").toString()));
                                            resultDo.setStatus(1);
                                            bizResult = resultService.save(resultDo);
                                            resultDo = null;
                                            if(bizResult.equals(BizResult.error())){
                                                break;
                                            }
                                        }
                                    }
                                }
                                //step 3 应答
                                returnMsg.setMid("2012");
                                Map<String,Integer> retMsg = new HashMap<String,Integer>();
                                retMsg.put("result",bizResult.equals(BizResult.success()) ? 0 : -1);
                                returnMsg.setMsg(mapper.writeValueAsString(retMsg));
                                returnMsg.Encrypt(terminalDo.getKeyt());
                                msgInfoDo = null;
                                msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(returnMsg.getTs()));
                                msgInfoDo.setLife(-1);
                                msgInfoDo.setTerminalId("000000000000");
                                msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                                msgInfoDo.setMsg(mapper.writeValueAsString(retMsg));
                                msgInfoDo.setMsgCode("2012");
                                msgInfoDo.setMsgCodeName("上传数据应答");
                                msgInfoDo.setSq(returnMsg.getSq());
                                msgInfoDo.setPrivateMsg(mapper.writeValueAsString(retMsg));
                                msgInfoDo.setMsgType(1);
                                bizResult = messageService.addMessage(msgInfoDo);
                                msgInfoDo = null;
                                newTerminal = null;
                                retMsg = null;
                                if(bizResult.equals(BizResult.error())){
                                    log.error("2012 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                            }else if(mid.equals("1994")){//异常应答

                                //step 1.保存消息记录1
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId(messageDo.getSid());
                                msgInfoDo.setJsonText(req);
                                msgInfoDo.setMsg(messageDo.getMsg());
                                msgInfoDo.setMsgCode(mid);
                                msgInfoDo.setMsgCodeName("异常应答");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(messageDo.getMsg());
                                msgInfoDo.setMsgType(1);
                                BizResult bizResult = messageService.addMessage(msgInfoDo);
                                if(bizResult.equals(BizResult.error())){
                                    log.error("1994 message save error!");
                                    log.error(bizResult.getMessage());
                                }
                                returnMsg = null;
                                msgInfoDo = null;
                                bizResult = null;
                                return;
                            }else {
                                //异常4：不存在的消息ID
                                log.error("异常4：不存在的消息ID");
                                Map<String,Integer> msg = new HashMap<String,Integer>();
                                msg.put("err",4);
                                returnMsg.setSign("");
                                returnMsg.setMsg(mapper.writeValueAsString(msg));
                                returnMsg.setMid("1992");
                                //step 2 答复消息记录
                                MsgInfoDo msgInfoDo = new MsgInfoDo();
                                msgInfoDo.setAtTime(sdf.parse(messageDo.getTs()));
                                msgInfoDo.setLife(messageDo.getLife());
                                msgInfoDo.setTerminalId("000000000000");
                                msgInfoDo.setJsonText(mapper.writeValueAsString(returnMsg));
                                msgInfoDo.setMsg(mapper.writeValueAsString(msg));
                                msgInfoDo.setMsgCode("1992");
                                msgInfoDo.setMsgCodeName("不存在的消息ID");
                                msgInfoDo.setSq(messageDo.getSq());
                                msgInfoDo.setPrivateMsg(mapper.writeValueAsString(msg));
                                msgInfoDo.setMsgType(1);
                                messageService.addMessage(msgInfoDo);
                                msg = null;
                            }
                        }else{
                            //异常2：签名错误
                            log.error("异常2：签名错误");
                            Map<String,Integer> msg = new HashMap<String,Integer>();
                            msg.put("err",2);
                            returnMsg.setMsg(mapper.writeValueAsString(msg));
                            returnMsg.setSign("");
                            returnMsg.setMid("1992");
                            msg = null;
                        }

                    }else{//异常9：未知来源
                        log.error("异常9：未知来源");
                        Map<String,Integer> msg = new HashMap<String,Integer>();
                        msg.put("err",9);
                        returnMsg.setMsg(mapper.writeValueAsString(msg));
                        returnMsg.setMid("1992");
                        returnMsg.setSign("");
                        msg = null;
                    }
                }else{
                    //异常6：消息过期
                    log.error("异常6：消息过期");
                    Map<String,Integer> msg = new HashMap<String,Integer>();
                    msg.put("err",6);
                    returnMsg.setMsg(mapper.writeValueAsString(msg));
                    returnMsg.setMid("1992");
                    returnMsg.setSign("");
                    msg = null;

                }
            }else{
                //异常7：缺少参数
                log.error("异常7：缺少参数");
                Map<String,Integer> msg = new HashMap<String,Integer>();
                msg.put("err",7);
                returnMsg.setMid("1992");
                returnMsg.setSign("");
                returnMsg.setSq(0l);
                returnMsg.setMsg(mapper.writeValueAsString(msg));
                msg = null;
            }
            final_string += mapper.writeValueAsString(returnMsg)+"###";
        }
        DatagramPacket dp = new DatagramPacket(Unpooled.copiedBuffer(final_string.getBytes()), packet.sender());
        ctx.writeAndFlush(dp);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }


}
