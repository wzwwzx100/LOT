package com.mogu.LOT.api.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;


/**
 * GlobalRequestInterceptor
 *
 * @author FE
 * @date 2016/7/27
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {

    /**
     * 设置项目的访问域名
     *
     * @param request
     * @return
     */
//    protected void initContext(HttpServletRequest request) {
//        request.setAttribute(WebKeyConstants.DOMAIN, this.domain(request));
//        request.setAttribute(WebKeyConstants.DOMAIN_PATH, this.domainPath(request));
//        //前台web context path
//        request.setAttribute(WebKeyConstants.ACTIVITY_WEB_CONTEXT_PATH, ConfigConstants.ACTIVITY_WEB_CONTEXT_PATH);
//        //上传图片访问路径
//        request.setAttribute(WebKeyConstants.IMAGE_CONTEXT_PATH, ConfigConstants.IMAGE_CONTEXT_PATH);
//    }

    protected String domainPath(HttpServletRequest request) {
        String DOMAIN_PATH = "";
        if (Integer.valueOf(80).equals(request.getServerPort())) {
            DOMAIN_PATH = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
        } else {
            DOMAIN_PATH = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        }
        return DOMAIN_PATH;
    }

    protected String domain(HttpServletRequest request) {
        String DOMAIN = "";
        if (Integer.valueOf(80).equals(request.getServerPort())) {
            DOMAIN = request.getScheme() + "://" + request.getServerName();
        } else {
            DOMAIN = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }
        return DOMAIN;
    }

    /**
     * 是否是微信浏览器
     *
     * @param request
     * @return
     */
    protected boolean isWeiXin(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        return userAgent.indexOf("micromessenger") > 0;
    }

    /**
     * 判断ajax请求
     *
     * @param request
     * @return
     */
    protected boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With").toString()));
    }

}
