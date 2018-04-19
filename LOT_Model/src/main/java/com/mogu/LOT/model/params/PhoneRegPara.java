package com.mogu.LOT.model.params;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by chang on 2017/6/21.
 */
public class PhoneRegPara {
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(17[0-9]))\\d{8}$",message = "手机号码格式不匹配。")
    @NotNull
    private String  tel;//电话
    @Pattern(regexp = "\\S{6,20}", message = "密码必须是6-20位字符。")
    @NotNull
    private String pwd;//密码
    @Pattern(regexp = "\\S{6}", message = "验证码为6位随机数。")
    @NotNull
    private String yzm;//验证码

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

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }


    public static void main(String[] args){
        String number = "17362634525";
        String reg = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(17[0-9]))\\d{8}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(reg);
        System.out.println(number.matches(reg));
    }
}
