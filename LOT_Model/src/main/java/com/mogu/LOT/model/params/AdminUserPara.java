package com.mogu.LOT.model.params;/**
 * Created by Administrator on 2017/6/30 0030.
 */

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * AdminUserPara
 *
 * @author xufeng
 * @date 2017/6/30 0030
 */
public class AdminUserPara {
    private Integer id;

    private String userName;
//    @Pattern(regexp = "\\S{6,18}", message = "密码必须是6-20位字符。")
    private String pwd;

    private Integer role;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

}
