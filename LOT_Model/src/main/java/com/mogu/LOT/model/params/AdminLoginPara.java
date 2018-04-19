package com.mogu.LOT.model.params;/**
 * Created by Administrator on 2017/6/26 0026.
 */

import javax.validation.constraints.Pattern;

/**
 * AdminLoginPara
 *
 * @author xufeng
 * @date 2017/6/26 0026
 */
public class AdminLoginPara {

    private String userName;

    private Integer role;
    @Pattern(regexp = "\\S{6,18}", message = "密码必须是6-20位字符。")
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
