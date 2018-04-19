package com.mogu.LOT.model.entity;

import java.util.Date;

public class MsgInfoDo {

    private Long id;

    private Date atTime;
    //有效期
    private Integer life;
    //消息流水号
    private Long sq;
    //终端ID
    private String terminalId;
    //消息编码
    private String msgCode;
    //消息名称
    private String msgCodeName;
    //消息类型
    private int msgType;
    //明文消息
    private String msg;
    //密文消息
    private String privateMsg;
    //消息内容
    private String jsonText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAtTime() {
        return atTime;
    }

    public void setAtTime(Date atTime) {
        this.atTime = atTime;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Long getSq() {
        return sq;
    }

    public void setSq(Long sq) {
        this.sq = sq;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgCodeName() {
        return msgCodeName;
    }

    public void setMsgCodeName(String msgCodeName) {
        this.msgCodeName = msgCodeName;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPrivateMsg() {
        return privateMsg;
    }

    public void setPrivateMsg(String privateMsg) {
        this.privateMsg = privateMsg;
    }

    public String getJsonText() {
        return jsonText;
    }

    public void setJsonText(String jsonText) {
        this.jsonText = jsonText;
    }
}
