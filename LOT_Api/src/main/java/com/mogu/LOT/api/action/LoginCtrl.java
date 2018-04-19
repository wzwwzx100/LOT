package com.mogu.LOT.api.action;

import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.constants.CommonConstant;
import com.mogu.LOT.model.entity.AdminUserDo;
import com.mogu.LOT.service.AdminUserService;
import com.mogu.LOT.model.params.AdminLoginPara;
import com.mogu.LOT.model.params.AdminPassPara;
import com.mogu.LOT.model.params.AdminUserPara;
import com.mogu.LOT.util.BizResult;
import com.mogu.LOT.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class LoginCtrl extends BaseCtrl {



    @Autowired
    private AdminUserService adminUserService;




    @RequestMapping(value = "admin/index")
    public ModelAndView  index(){
        return new ModelAndView("index");
    }



    /**
     * 管理员登录
     * @param adminLoginPara
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "admin/doLogin")
    public WebResult adlogin(@Valid @RequestBody AdminLoginPara adminLoginPara, BindingResult result, HttpServletRequest request){
        if (result.hasErrors()) return WebResult.paramError(result.getAllErrors().get(0).getDefaultMessage());
        return new WebResult(adminUserService.doLogin(adminLoginPara,request));
    }

    /**
     * 修改密码
     * @param adminPassPara
     * @return
     */
    @RequestMapping(value = "admin/updatePass",method = RequestMethod.POST)
    public WebResult updatePass(@Valid @RequestBody AdminPassPara adminPassPara) {
        return new WebResult(adminUserService.updatePass(adminPassPara));
    }

}
