package com.mogu.LOT.api.action;

import com.mogu.LOT.model.entity.UserDo;
import com.mogu.LOT.model.params.LoginPara;
import com.mogu.LOT.service.UserService;
import com.mogu.LOT.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user/")
public class UserCtrl extends  BaseCtrl{
    @Autowired
    private UserService userService;


    /**
     * 登录
     * @param loginPara
     * @param result
     * @return
     */
    @RequestMapping(value = "doLogin")
    public WebResult doLogin(@Valid @RequestBody LoginPara loginPara, BindingResult result){
        if (result.hasErrors())return WebResult.paramError(result.getAllErrors().get(0).getDefaultMessage());
        return new WebResult(userService.doLogin(loginPara));
    }


    /**
     * 注册
     * @return
     */
    @RequestMapping(value = "reg")
    public WebResult reg(@RequestBody UserDo userDo){
        return new WebResult(userService.regByTel(userDo));
    }
}
