package com.mogu.LOT.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.Enum.SmsTemp;
import com.mogu.LOT.constants.CommonConstant;
import com.mogu.LOT.dao.mapper.UserDoMapper;
import com.mogu.LOT.model.common.YZM;
import com.mogu.LOT.model.entity.UserDo;
import com.mogu.LOT.model.params.LoginPara;
import com.mogu.LOT.model.params.PhoneRegPara;
import com.mogu.LOT.model.params.UpdUserInfoPara;
import com.mogu.LOT.model.params.YzmPara;
import com.mogu.LOT.service.AdminUserService;
import com.mogu.LOT.service.RedisService;
import com.mogu.LOT.service.SmsService;
import com.mogu.LOT.service.UserService;
import com.mogu.LOT.util.*;
import com.taobao.api.ApiException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chang on 2017/6/17.
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${app_save_root}")
    private String rootDir;

    @Autowired
    private UserDoMapper userDoMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AdminUserService adminUserService;



    @Override
    public UserDo findByTel(String tel){
        return userDoMapper.findByTel(tel);
    }




    @Override
    public BizResult<UserDo> doLogin(LoginPara loginPara){
        UserDo userDo = findByTel(loginPara.getTel());
        if (userDo == null)return BizResult.error(ResultCode.NO_USER);
        String validPwd = EncryptUtils.encryptPassword(loginPara.getPwd(), userDo.getSalt());
        if (validPwd.equals(userDo.getPwd())){
            String  key = getTokenKey(loginPara.getTel(),userDo.getSalt());
            String addResult =  redisService.setObj(key,userDo,30 * 24 * 60 *60);
            return BizResult.success(userDo);
        }
        return BizResult.error(ResultCode.LOGIN_ERROR);
    }


    /**
     * 手机注册验证码校验
     * @param tel
     * @param yzmCode
     * @return
     */
    @Override
    public BizResult yzmValid(String tel,String yzmCode){
        UserDo userDo = findByTel(tel);
        if (null != userDo)return BizResult.error(ResultCode.HAS_TEL);

        String key = BASE64Util.BASE64(tel + SmsTemp.REG_YZM.getName());
        YZM yzm = redisService.getObj(key, YZM.class);
        if (null == yzm) return BizResult.error(ResultCode.YZM_VALID_EMPTY);
        if (!yzm.getCode().equals(yzmCode)){
            return BizResult.error(ResultCode.YZM_VALID_ERROR);
        }
        return BizResult.success();
    }



    public UserDo insert(UserDo userDo){
        if(userDo.getId() == null){
            if (userDoMapper.add(userDo)>0){
                return userDo;
            }else{
                return  null;
            }
        }else{
            return  userDoMapper.modify(userDo) > 0 ? userDo : null;
        }
    }

    @Override
    public BizResult regByTel(UserDo userDo){
        UserDo exist = findByTel(userDo.getTel());
        if (null != userDo)return BizResult.error(ResultCode.HAS_TEL);
        String salt = EncryptUtils.salt();
        String  pwd = EncryptUtils.encryptPassword(userDo.getPwd(),salt);
        UserDo userDo1 = new UserDo();
        userDo1.setAddTime(new Date());
        userDo1.setUpdateTime(null);
        userDo1.setPwd(pwd);
        userDo1.setSalt(salt);
        userDo1.setTel(userDo.getTel());
        String tel = userDo.getTel();
        char[] c = tel.toCharArray();
        c[3] = '*';
        c[4] = '*';
        c[5] = '*';
        c[6] = '*';
        String nickName = String.valueOf(c);
        userDo1.setUserName(nickName);
        userDo1.setValid(CommonConstant.OK);
        if (null != insert(userDo1)){
            return BizResult.success();
        }
        return BizResult.error(ResultCode.ERROR);
    }


    /**
     * token key
     * @param tel
     * @param salt
     * @return
     */
    private static String getTokenKey(String tel,String salt){
        return EncryptUtils.encryptPassword(tel,salt);
    }



    /**
     * redis 存储验证码规则  key 电话号+ 类型
     * value 验证码
     * @param yzmPara
     * @return
     */
    @Override
    public BizResult createYzm(YzmPara yzmPara, SmsTemp smsTemp, JSONObject param, String code){

        String key = BASE64Util.BASE64(yzmPara.getTel() + smsTemp.getName());
        YZM yzmValue = redisService.getObj(key,YZM.class);
        if (null != yzmValue){
            //判断是否 大于1 min   大于1分重新发送  小于 1分 不发送
            long cha = System.currentTimeMillis()/1000 - yzmValue.getAddTime();
            if (cha < 60 * 1){
//            if (cha < 1 * 1){
                return BizResult.error(ResultCode.FREQUENTLY);
            }else yzmValue = new YZM();
        }else yzmValue = new YZM();


        yzmValue.setAddTime(System.currentTimeMillis()/1000);
        yzmValue.setCode(code);
        yzmValue.setValidTime(30 * 60);//5分钟

        //发送
        boolean flag = false;
        try {
            flag = smsService.send(
                    null,
                    "normal",
                    smsTemp.getSign(),
                    param.toJSONString(),
                    yzmPara.getTel(),
                    smsTemp.getTempNo()
            );
        } catch (ApiException e) {
            logger.error("send msg error!");
            return BizResult.error(ResultCode.GET_YZM_ERROR);
        }
        if (flag){
            //存token
            redisService.setObj(key,yzmValue,5 * 60);
            return BizResult.success();
        }else {
            return BizResult.error(ResultCode.GET_YZM_ERROR);
        }
    }

    /**
     * 注册验证
     * @param yzmPara
     * @return
     */
    @Override
    public BizResult createYzmForReg(YzmPara yzmPara){
        UserDo userDo = findByTel(yzmPara.getTel());
        if (null != userDo)return BizResult.error(ResultCode.HAS_TEL);
        JSONObject param  = new JSONObject();
        String code = RandomUtil.simpleRandom();
        param.put("code",code);
        param.put("name","LOT");
        return createYzm(yzmPara,SmsTemp.REG_YZM,param,code);
    }


    @Override
    public BizResult createYzmForRestPwd(YzmPara yzmPara){
        UserDo userDo = findByTel(yzmPara.getTel());
        if (null == userDo)return BizResult.error(ResultCode.NO_USER);
        JSONObject param  = new JSONObject();
        String code = RandomUtil.simpleRandom();
        param.put("code",code);
        param.put("name","LOT");
        return createYzm(yzmPara, SmsTemp.CZ_YZM, param, code);
    }


    /**
     * 重置密码
     * @Author chang
     * @param regPara
     * @return
     */
    @Override
    public BizResult restPwd(PhoneRegPara regPara){
        UserDo userDo = findByTel(regPara.getTel());
        if (null == userDo)return BizResult.error(ResultCode.NO_USER);

        String key = BASE64Util.BASE64(regPara.getTel() + SmsTemp.CZ_YZM.getName());
        YZM yzm = redisService.getObj(key, YZM.class);
        if (null == yzm) return BizResult.error(ResultCode.YZM_VALID_EMPTY);
        if (!yzm.getCode().equals(regPara.getYzm())){
            return BizResult.error(ResultCode.YZM_VALID_ERROR);
        }

        String newPwd =  EncryptUtils.encryptPassword(regPara.getPwd(),userDo.getSalt());
        UserDo upd = new UserDo();
        upd.setId(userDo.getId());
        upd.setUpdateTime(new Date());
        upd.setPwd(newPwd);

        if (userDoMapper.modify(upd)>0)
            return BizResult.success();

        return BizResult.error();
    }




    @Override
    public BizResult updInfo(UpdUserInfoPara updUserInfoPara, String tokenKey){
        UserDo userDo = redisService.getObj(tokenKey,UserDo.class);

        UserDo updUser = BeanMapper.map(updUserInfoPara, UserDo.class);

        updUser.setId(userDo.getId());
        if (userDoMapper.modify(updUser)>0){
            UserDo userDo1 = userDoMapper.selectByPrimaryKey(userDo.getId());
            return BizResult.success();
        }
        return BizResult.error();
    }


    @Override
    public BizResult setToken(String tel, SmsTemp smsTemp){
        String key = BASE64Util.BASE64(tel + smsTemp.getName());
        YZM yzm = new YZM();
        yzm.setAddTime(System.currentTimeMillis()/1000);
        yzm.setValidTime(5 * 60);
        yzm.setCode("666666");
        redisService.setObj(key,yzm,5 * 60);
        return BizResult.success();
    }

    /**
     * 更新密码
     */
    @Override
    public BizResult updatePwd(String tel,String pwd,String oldPwd){
        UserDo userDo = findByTel(tel);
        if (null == userDo)return BizResult.error(ResultCode.NO_USER);
        String old = EncryptUtils.encryptPassword(oldPwd,userDo.getSalt());
        if (!old.equals(userDo.getPwd()))return BizResult.error(ResultCode.OLD_PSD_ERROR);
        String newPwd =  EncryptUtils.encryptPassword(pwd,userDo.getSalt());
        UserDo upd = new UserDo();
        upd.setId(userDo.getId());
        upd.setUpdateTime(new Date());
        upd.setPwd(newPwd);

        if (userDoMapper.modify(upd)>0)
            return BizResult.success();

        return BizResult.error();
    }


    /**
     * 用户退出
     * @param userDo
     * @param sign
     * @return
     */
    @Override
    public BizResult exit(UserDo userDo,String sign){
        String  key = getTokenKey(userDo.getTel(),userDo.getSalt());
        redisService.del(key);
        return BizResult.success();
    }

    @Override
    public BizResult updateUser(UserDo userDo) {
        Integer ret = userDoMapper.modify(userDo);
        if (ret==1){
            return BizResult.success();
        }
        return BizResult.error();
    }

    @Override
    public BizResult findUserByTel(String tel) {
        return BizResult.success(userDoMapper.findByTel(tel));
    }

    private boolean checkIDNumber(String id_number){
        if(StringUtils.isBlank(id_number)){
            return  false;
        }
        if(id_number.length() != 18){
            return  false;
        }
        char[] number = StringUtils.reverse(id_number).toCharArray();
        int[] wi = {0,2,4,8,5,10,9,7,3,6,1,2,4,8,5,10,9,7};
        char[] Y = {'1','0','X','9','8','7','6','5','4','3','2'};
        Integer sum = 0;
        for(int i=1; i<number.length; i++){
            int AI = Integer.parseInt(String.valueOf(number[i]));
            sum += AI * wi[i];
        }
        int y = sum% 11;
        return  String.valueOf(Y[y]).equals(String.valueOf(number[0]));
    }


    public static void main(String[] args){
        char[] tel = StringUtils.reverse("330102197604061223").toCharArray();
        int[] wi = {0,2,4,8,5,10,9,7,3,6,1,2,4,8,5,10,9,7};
        char[] Y = {'1','0','X','9','8','7','6','5','4','3','2'};
        Integer sum = 0;
        for(int i=1; i<tel.length; i++){
            int AI = Integer.parseInt(String.valueOf(tel[i]));
            sum += AI * wi[i];
        }
        int y = sum% 11;
        System.out.println(sum);
        System.out.println(Y[y]);

    }


}
