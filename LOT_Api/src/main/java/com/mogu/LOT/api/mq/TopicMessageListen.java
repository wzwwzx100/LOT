package com.mogu.LOT.api.mq;

import com.alibaba.fastjson.JSONObject;
import com.mogu.LOT.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by chang on 2017/7/19.
 */
public class TopicMessageListen implements MessageListener{

    private static final Logger logger = LoggerFactory.getLogger(TopicMessageListen.class);

    public void onMessage(Message message) {
        System.out.println("监听==================监听");
        try {
            TextMessage tm = (TextMessage)(message);
            logger.info(tm.getText());
            if (StringUtils.isNotEmpty(tm.getText())){
                JSONObject o = JSONObject.parseObject(tm.getText());
                if (StringUtils.isNotEmpty(o.getString("DEFAULT_RADIUS"))) CommonConstant.DEFAULT_RADIUS = o.getLong("DEFAULT_RADIUS");
                if (StringUtils.isNotEmpty(o.getString("FIRST_JOIN_SCORE"))) CommonConstant.FIRST_JOIN_SCORE = o.getInteger("FIRST_JOIN_SCORE");
                if (StringUtils.isNotEmpty(o.getString("FIND_LOST_SCORE"))) CommonConstant.FIND_LOST_SCORE = o.getInteger("FIND_LOST_SCORE");
                if (StringUtils.isNotEmpty(o.getString("HOLIDAY_SCORE"))) CommonConstant.HOLIDAY_SCORE = o.getInteger("HOLIDAY_SCORE");

                if (StringUtils.isNotEmpty(o.getString("MILEAGE_UP_1K"))) CommonConstant.MILEAGE_UP_1K = o.getInteger("MILEAGE_UP_1K");
                if (StringUtils.isNotEmpty(o.getString("MILEAGE_UP_2K"))) CommonConstant.MILEAGE_UP_2K = o.getInteger("MILEAGE_UP_2K");
                if (StringUtils.isNotEmpty(o.getString("MILEAGE_UP_4K"))) CommonConstant.MILEAGE_UP_4K = o.getInteger("MILEAGE_UP_4K");
                if (StringUtils.isNotEmpty(o.getString("MILEAGE_UP_MAX"))) CommonConstant.MILEAGE_UP_MAX = o.getInteger("MILEAGE_UP_MAX");

                if (StringUtils.isNotEmpty(o.getString("TIME_STAGE_1"))) CommonConstant.TIME_STAGE_1 = o.getInteger("TIME_STAGE_1");
                if (StringUtils.isNotEmpty(o.getString("TIME_STAGE_2"))) CommonConstant.TIME_STAGE_2 = o.getInteger("TIME_STAGE_2");
                if (StringUtils.isNotEmpty(o.getString("TIME_STAGE_3"))) CommonConstant.TIME_STAGE_3 = o.getInteger("TIME_STAGE_3");
                if (StringUtils.isNotEmpty(o.getString("TIME_STAGE_4"))) CommonConstant.TIME_STAGE_4 = o.getInteger("TIME_STAGE_4");


                if (StringUtils.isNotEmpty(o.getString("M"))) CommonConstant.M = o.getInteger("M");

                if (StringUtils.isNotEmpty(o.getString("OVER_AREA"))) CommonConstant.OVER_AREA = o.getBoolean("OVER_AREA");
                if (StringUtils.isNotEmpty(o.getString("DISTANCE_FILTER"))) CommonConstant.DISTANCE_FILTER = o.getInteger("DISTANCE_FILTER");
                if (StringUtils.isNotEmpty(o.getString("GROUP_TIME"))) CommonConstant.DISTANCE_FILTER = o.getInteger("GROUP_TIME");

                if (StringUtils.isNotEmpty(o.getString("INCREMENT"))) CommonConstant.INCREMENT = o.getInteger("INCREMENT");

                if (StringUtils.isNotEmpty(o.getString("MAX_ENCLOSURE_RADIUS"))) CommonConstant.MAX_ENCLOSURE_RADIUS = o.getInteger("MAX_ENCLOSURE_RADIUS");



            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
