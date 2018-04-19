package com.mogu.LOT.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chang on 2017/6/26.
 */
public class CommonUtil {
        /**
     * 构建推送消息
     * @return
     */
    public static String buildTSM(String message,String type,Object o){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("message",message);
        jsonObject.put("pushTime",System.currentTimeMillis());
        jsonObject.put("ext",o);
        return jsonObject.toJSONString();
    }

    public static Integer level(Long score){
        if (0<= score && score <1){
            return 0;
        }else if (1<= score && score <100){
            return 1;
        }else if (100<= score && score <1000){
            return 2;
        }else if (1000<= score && score <10000){
            return 3;
        }else if (10000<= score && score <100000){
            return 4;
        }
        return 0;
    }
}
