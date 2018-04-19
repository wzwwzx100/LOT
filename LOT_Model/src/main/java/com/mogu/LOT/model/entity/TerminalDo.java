package com.mogu.LOT.model.entity;

import java.util.Date;

public class TerminalDo {
    private String id;
    private Date lastTime;
    private String ip;
    private int port;
    private String keyt;

    private UserDo belong;

    public UserDo getBelong() {
        return belong;
    }

    public void setBelong(UserDo belong) {
        this.belong = belong;
    }

    public String getKeyt() {
        return keyt;
    }

    public void setKeyt(String keyt) {
        this.keyt = keyt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
