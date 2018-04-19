package com.mogu.LOT.service;

import com.taobao.api.ApiException;

/**
 * Created by chang on 2017/6/20.
 */
public interface SmsService {
    boolean send(String extend, String smsType, String sign, String param, String recTel, String templateCode) throws ApiException;
}
