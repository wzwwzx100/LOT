package com.mogu.LOT.api.interceptor;

import com.mogu.LOT.constants.CommonConstant;
import com.mogu.LOT.exceptions.NoAuth;
import com.mogu.LOT.exceptions.NoToken;
import com.mogu.LOT.model.entity.AdminUserDo;
import com.mogu.LOT.model.entity.UserDo;
import com.mogu.LOT.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GlobalRequestInterceptor extends AbstractInterceptor {

    private static Logger logger = LoggerFactory.getLogger(GlobalRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (isExtraUrl(request))return true;//例外
        else{
            UserDo userDo = (UserDo)request.getSession().getAttribute(CommonConstant.USER_SESSION);
            if (userDo==null) {
                throw new NoAuth();
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }


    /**
     * 判断是否是例外的接口，以资源开头为准
     * @param request
     * @return
     */
    private boolean isExtraUrl(HttpServletRequest request){

        String uri = request.getPathInfo();
        if(uri == null){
            return true;
        }
        for (String o:CommonConstant.EXTRA_URI){
            if (uri.startsWith(o)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 json 请求
     */
    private boolean isJsonPost(HttpServletRequest request){
        String contentType = request.getContentType();
        return "text/json".equalsIgnoreCase(contentType) || "application/json".equalsIgnoreCase(contentType);
    }

}
