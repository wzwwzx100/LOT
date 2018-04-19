package com.mogu.LOT.Enum;

/**
 * Created by chang on 2017/6/19.
 */
public enum  ResultCode {
    SUCCESS(1000,"操作成功",true),

    //2 开头  登录相关
    NO_AUTH(2001,"未登录",false),
    AUTH_ERROR(2003,"登录信息失效",false),
    NO_USER(2002,"用户不存在",false),
    LOGIN_ERROR(2003,"手机号或密码错误",false),
    HAS_TEL(2004,"手机号码已被注册，请直接登录",false),
    USER_DEL(2005,"用户已被封停，请联系管理员",false),
    USER_STATUS_EXCEPT(2006,"用户账号异常",false),
    OLD_PWD_VALID(2007,"旧密码验证异常",false),
    CODE_EXIST(2008,"编号已存在",false),
    PWD_ERR(2011,"密码错误",false),
    ERROR_USER(2012,"用户不存在或存在异常",false),
    //3开头，权限相关

    //4开头，上传参数相关
    UN_VAILD_PARA(4000,"上传参数不正确",false),
    TIME_YYYY_MM_DD_HH_MM_SS(4001,"请输入正取时间格式,例：1997-01-01 01:01:01",false),
    TIME_YYYY_MM_DD(4002,"请输入正取时间格式,例：1997-01-01",false),
    TIME_YYYY(4003,"请输入正取时间格式,例：1997",false),


    //5开头，内部错误相关
    ERROR(5000,"服务器内部异常",false),



    //6 开头  其它错误
    FREQUENTLY(6000,"接口请求太过频繁，请重试",false),

    GET_YZM_ERROR(6001,"验证码请求失败",false),
    YZM_VALID_ERROR(6002,"验证码校验失败",false),

    YZM_VALID_EMPTY(6004,"请重新获取验证码",false),

    //7开头 管理员
    ADMIN_LOGIN_ERROR(7001,"用户名或密码错误",false),
    ADMIN_ROLE(7003,"权限不足",false),
    USER_HAVE(7007,"用户名重复",false),
    SUPER_ROLE_ERROR(7008,"无法删除超级管理员",false),
    PSD_CONFIRM_ERROR(7009,"新密码和确认新密码不一致",false),
    OLD_PSD_ERROR(7010,"旧密码不对",false),
    PSD_LENTH_ERROR(7011,"密码必须为6-20位字符",false),

    //导入相关
    IMPORT_NULL(0001,"导入完成，内容为空",true),
    IMPORT_ERROR(0002,"导入失败，文件读取错误",true),

    ;

    private Integer code;
    private String message;
    private boolean success;

    ResultCode(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
