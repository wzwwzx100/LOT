package com.mogu.LOT.model.entity;

import com.mogu.LOT.util.MD5Util;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class MessageDo {
    private Double sv;
    private Double cv;
    private String sid;
    private String mid;
    private Long sq;
    private String ts;
    private Integer life;
    private  String msg;
    private String sign;

    public double getSv() {
        return sv;
    }

    public void setSv(double sv) {
        this.sv = sv;
    }

    public double getCv() {
        return cv;
    }

    public void setCv(double cv) {
        this.cv = cv;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Long getSq() {
        return sq;
    }

    public void setSq(Long sq) {
        this.sq = sq;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean lessAttr(){
        return this.sv == null || this.cv==null || this.ts == null || this.life == null || this.sq == null || this.sid == null || this.mid == null || this.sign == null || this.msg == null;
    }

    public void Encrypt(String key)
    {
        if(StringUtils.isEmpty(key)){
            this.sign =  "";
        }
        else
        {
            String str = MessageFormat.format("{0}{1}{2}{3}{4}{5}", ts, sq.toString(), sid, mid, msg, key);
            String md5 = MD5Util.toMD5(str);
            this.sign = md5.substring(0,8);
        }
    }

}
