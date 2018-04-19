package com.mogu.LOT.model.common;

import java.io.Serializable;

/**
 * Created by chang on 2017/6/20.
 */
public class YZM implements Serializable{
    private String code;//验证码
    private long addTime;//添加时间 单位 s
    private long validTime;//有效时间  单位 s

    private Integer count;//发送总数

    public YZM() {
    }

    public YZM(String code, long addTime, long validTime, Integer count) {
        this.code = code;
        this.addTime = addTime;
        this.validTime = validTime;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getValidTime() {
        return validTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }
}
