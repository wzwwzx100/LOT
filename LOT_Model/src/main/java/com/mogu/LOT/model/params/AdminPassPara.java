package com.mogu.LOT.model.params;/**
 * Created by Administrator on 2017/7/11 0011.
 */

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * AdminPassPara
 *
 * @author xufeng
 * @date 2017/7/11 0011
 */
public class AdminPassPara {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "原始密码不能为空")
    private String oldPwd;
    @Pattern(regexp = "\\S{6,18}", message = "密码必须是6-20位字符。")
    private String newPwd;
    @NotNull(message = "确认密码不能为空")
    private String confirmPwd;

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
        this.userName = userName;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

}
