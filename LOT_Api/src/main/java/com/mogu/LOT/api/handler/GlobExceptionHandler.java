package com.mogu.LOT.api.handler;

import com.alibaba.fastjson.JSONObject;
import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.exceptions.NoToken;
import com.mogu.LOT.util.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobExceptionHandler.class);

    @ExceptionHandler(NoToken.class)
    @ResponseBody
    public WebResult<String> handleUnexpectedServerError(NoToken ex) {
//        logBuild(ex);
        WebResult<String> webResult = new WebResult<String>(ResultCode.NO_AUTH);
        return webResult;
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResult<String> handleUnexpectedServerError(Exception ex) {
        ex.printStackTrace();
        logBuild(ex);
        WebResult<String> webResult = new WebResult<String>(ResultCode.ERROR);
        return webResult;
    }

    private void logBuild(Exception ex){
        if (logger.isWarnEnabled()){
            logger.error("<=============================================>");
//            ex.printStackTrace();
            logger.error("exception detail info :" + ex);
            logger.error("exception detail info :" + JSONObject.toJSONString(ex));
            logger.error("<=====================用户token 异常========================>");
        }
    }
}
