package com.mogu.LOT.service;

import com.alibaba.fastjson.JSONObject;
import com.mogu.LOT.Enum.SmsTemp;
import com.mogu.LOT.model.entity.UserDo;
import com.mogu.LOT.model.params.LoginPara;
import com.mogu.LOT.model.params.PhoneRegPara;
import com.mogu.LOT.model.params.UpdUserInfoPara;
import com.mogu.LOT.model.params.YzmPara;
import com.mogu.LOT.util.BizResult;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 通过手机号码查找
     * @Author chang
     * @param tel
     * @return
     */
    UserDo findByTel(String tel);

    /**
     * 手机号码登录
     * @Author
     * @param loginPara
     * @return
     */
    BizResult<UserDo> doLogin(LoginPara loginPara);


    BizResult updInfo(UpdUserInfoPara updUserInfoPara, String tokenKey);
    BizResult yzmValid(String tel, String yzmCode);

    BizResult regByTel(UserDo userDo);

    BizResult createYzm(YzmPara yzmPara, SmsTemp smsTemp, JSONObject param, String sign);

    BizResult createYzmForReg(YzmPara yzmPara);

    BizResult createYzmForRestPwd(YzmPara yzmPara);

    BizResult restPwd(PhoneRegPara regPara);

    BizResult setToken(String tel, SmsTemp smsTemp);

    BizResult updatePwd(String tel, String pwd, String oldPwd);

    BizResult exit(UserDo userDo, String sign);

    BizResult updateUser(UserDo userDo);

    BizResult findUserByTel(String tel);

}
