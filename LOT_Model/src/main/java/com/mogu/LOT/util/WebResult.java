package com.mogu.LOT.util;

import com.mogu.LOT.Enum.ResultCode;

import java.io.Serializable;

/**
 * Created by chang on 2017/6/19.
 */

public class WebResult<T> implements Serializable{
    private int code;
    private String message;
    private T data;

    public WebResult() {
    }

    @Deprecated
    public WebResult(int code, String message, T t) {
        this.code = code;
        this.message = message;
        this.data = t;
    }

    public WebResult(BizResult result) {
        this.code = result.getCode();
        this.message = result.getMessage();
        this.data = (T)result.getT();
    }

    public WebResult(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static WebResult me(){
        return new WebResult();
    }

    public static WebResult success(){
        return new WebResult(ResultCode.SUCCESS);
    }

    public static WebResult success(Object o){
        WebResult result =  new WebResult(ResultCode.SUCCESS);
        result.setData(o);
        return result;
    }

    public static WebResult error(String message){
        WebResult result =  new WebResult(ResultCode.ERROR);
        result.setMessage(message);
        return result;
    }

    public static WebResult paramError(String message){
        WebResult result =  new WebResult(ResultCode.UN_VAILD_PARA);
        result.setMessage(message);
        return result;
    }

    public static WebResult paramError(){
        WebResult result =  new WebResult(ResultCode.UN_VAILD_PARA);
        return result;
    }


    public static WebResult error(String message,Object t){
        WebResult result =  new WebResult(ResultCode.ERROR);
        result.setMessage(message);
        result.setData(t);
        return result;
    }
}
