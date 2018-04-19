package com.mogu.LOT.model.common;

import com.mogu.LOT.util.BASE64Util;

import java.io.Serializable;

/**
 * Created by chang on 2017/6/19.
 */
public class Token<T> implements Serializable{
    private String key;
    private String userKey;
    private long timestamp;//token 生成时间 单位毫秒
    private String signature;//渠道签名 //web ios android
    private long duration;//有效时长  单位毫秒

    private String token; //token

    public T user;

    public Token(String userKey, String signature,long duration,T user) {
        this.key = "VOTE";
        this.userKey = userKey;
        this.timestamp = System.currentTimeMillis();
        this.signature = signature;
        this.duration = duration;
        this.user = user;
    }

    private String buildToken(){
        return BASE64Util.BASE64(key + userKey + timestamp + signature);
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
