package com.mogu.LOT.exceptions;

/**
 * Created by chang on 2017/7/7.
 */
public class OpError extends RuntimeException {
    public OpError(){
        super("操作异常");
    }
}
