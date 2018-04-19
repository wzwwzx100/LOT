package com.mogu.LOT.exceptions;

public class NoAuth extends RuntimeException {
    public NoAuth() {
        super("未登录");
    }
}
