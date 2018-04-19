package com.mogu.LOT.Enum;


/**
 * Created by chang on 2017/6/20.
 */
public enum  SmsTemp {
    NONE(0,"","","",""),

    REG_YZM(1,"REG_YZM","登录确认验证码","SMS_26070327","杭州明铂科技"),

    CZ_YZM(2,"CZ_YZM","重置验证码","SMS_71185740","磨古科技"),

    VALID(3,"REG_YZM","验证验证码","SMS_78680211","磨古科技"),
    ;
    private Integer code;
    private String name;//名字
    private String des;//描述
    private String tempNo;//模板号
    private String sign;//签名

    SmsTemp(Integer code, String name, String des, String tempNo, String sign) {
        this.code = code;
        this.name = name;
        this.des = des;
        this.tempNo = tempNo;
        this.sign = sign;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getTempNo() {
        return tempNo;
    }

    public String getSign() {
        return sign;
    }
}
