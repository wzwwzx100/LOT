package com.mogu.LOT.api.action;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by chang on 2017/6/17.
 */
public class BaseCtrl {
    protected  HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;



    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public String getTokenKey(){
        return request.getHeader("token");
    }

}
