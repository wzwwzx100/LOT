package com.mogu.LOT.util;

import com.mogu.LOT.Enum.ResultCode;

/**
 * Created by chang on 2017/6/19.
 */
public class BizResult<T> {
    private int code;
    private String message;
    private T t;
    private boolean success;

    public BizResult(){}

    public BizResult(ResultCode code){
        this.message = code.getMessage();
        this.code = code.getCode();
        this.success = code.isSuccess();
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

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static BizResult error(ResultCode code){
        BizResult bizResult = new BizResult();
        bizResult.setMessage(code.getMessage());
        bizResult.setCode(code.getCode());
        bizResult.setSuccess(false);
        return bizResult;
    }

    public static BizResult error(){
        return error(ResultCode.ERROR);
    }

    public static BizResult error(String message){
        BizResult bizResult = new BizResult();
        bizResult.setMessage(message);
        bizResult.setCode(ResultCode.ERROR.getCode());
        bizResult.setSuccess(false);
        return bizResult;
    }

    public static BizResult success(){
        return new BizResult(ResultCode.SUCCESS);
    }

    public static BizResult success(Object t){
        BizResult bizResult =  new BizResult(ResultCode.SUCCESS);
        bizResult.setT(t);
        return bizResult;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  BizResult){
            return this.code == ((BizResult) obj).getCode();
        }else{
            return  false;
        }
    }
}
