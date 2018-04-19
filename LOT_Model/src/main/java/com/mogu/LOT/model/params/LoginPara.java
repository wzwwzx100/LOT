package com.mogu.LOT.model.params;

import javax.validation.constraints.Pattern;

/**
 * Created by chang on 2017/6/19.
 */
public class LoginPara extends BaseParas {
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(17[6-8]))\\d{8}$",message = "手机号码格式不匹配。")
    private String tel;

    @Pattern(regexp = "\\S{6,}", message = "密码必须是6位以上字符。")
    private String pwd;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
