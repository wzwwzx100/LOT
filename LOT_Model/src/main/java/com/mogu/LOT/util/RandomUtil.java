package com.mogu.LOT.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by chang on 2017/6/20.
 */
public class RandomUtil {
    /**
     * @return
     */
    public static String simpleRandom() {
        int a = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(a);
    }

    /**
     * 生成28位订单号
     *
     * @param preFix 订单号的前缀 [MOGU] 最多四位
     * @return
     */
    public static String createOrderNum(String preFix) {
        String _preFix = StringUtils.trimToEmpty(preFix);
        if (StringUtils.isBlank(_preFix)) {
            _preFix = StringUtils.EMPTY;
        } else if (_preFix.length() > 4) {
            _preFix = _preFix.substring(0, 4).toUpperCase();
        } else {
            _preFix = _preFix.toUpperCase();
        }
        String _currTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String _orderNum = StringUtils.join(_preFix, _currTime, RandomUtil.randomCharNum(10));
        return _orderNum;
    }

    /**
     * java生成随机数字和字母组合
     *
     * @param length 生成随机数的长度
     * @return
     */
    public static String randomCharNum(int length) {
        StringBuffer sb = new StringBuffer("");
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "C" : "N";
            // 字符串
            if ("C".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                sb.append((char) (choice + random.nextInt(26)));
            } else if ("N".equalsIgnoreCase(charOrNum)) { // 数字
                sb.append(String.valueOf(random.nextInt(10)));
            }
        }
        return sb.toString();
    }


    /**
     * @return
     */
    public static String createEnterpriseUserID() {
        return StringUtils.join(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), RandomUtil.randomCharNum(34));
    }

}
