package com.mogu.LOT.model.entity;


import java.util.Date;

public class CommandDo {
    private Long id;
    private Date time;
    private Integer status;
    private MessageTypeDo messageType;
    private String jsonMsg;
    private TerminalDo terminal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MessageTypeDo getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeDo messageType) {
        this.messageType = messageType;
    }

    public String getJsonMsg() {
        return jsonMsg;
    }

    public void setJsonMsg(String jsonMsg) {
        this.jsonMsg = jsonMsg;
    }

    public TerminalDo getTerminal() {
        return terminal;
    }

    public void setTerminal(TerminalDo terminal) {
        this.terminal = terminal;
    }
}
