package com.mogu.LOT.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mogu.LOT.service.SmsService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by chang on 2017/6/20.
 * 对应阿里大鱼短信验证中心
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${dayu_app_key}")
    private String dayuAppKey;
    @Value("${dayu_secret}")
    private String dayuSecret;
    @Value("${dayu_http_url}")
    private String dayuUrl;

    private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    /**
     * 阿里大于短信
     * @param extend 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
         * @param smsType  短信类型，传入值请填写normal
     * @param sign 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
     * @param param  短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
     * @param recTel 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
     * @param templateCode  短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
     */
    @Override
    public boolean send(String extend, String smsType, String sign, String param, String recTel, String templateCode) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(dayuUrl,dayuAppKey,dayuSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(extend);
        req.setSmsType(smsType);
        req.setSmsFreeSignName(sign);
        req.setSmsParamString(param);
        req.setRecNum(recTel);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            JSONObject jsonObject = JSON.parseObject(rsp.getBody());
            logger.info(jsonObject.toJSONString());
            if (null != jsonObject.get("alibaba_aliqin_fc_sms_num_send_response"))return true;

            else if(null != jsonObject.get("error_response")){
                JSONObject obj = (JSONObject)jsonObject.get("error_response");
                throw new ApiException(obj.getString("sub_msg"));
            }
            return false;
        } catch (ApiException e) {
            throw e;
        }
    }
}
