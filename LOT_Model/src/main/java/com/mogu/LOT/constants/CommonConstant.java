package com.mogu.LOT.constants;

import java.util.List;
import java.util.Map;

/**
 * Created by chang on 2017/6/17.
 */
public class CommonConstant {
    public final static String[] EXTRA_URI = {
            "/user/doLogin",
            "/admin/doLogin"
    };

    public final static String[] PERMISSION_URI = {
            "/admin/delAdminUser",
            "/admin/addAdminUser",
            "/admin/updateAdminUser",
            "/admin/lst"
    };


    public static final ThreadLocal<Map<String,String>> config = new ThreadLocal();

    public final static String DB_ERROR = "db_error";
    public final static Integer OK = 1;//正常
    public final static Integer DEL = 0;//删除
    public final static Integer BAN = 2;//管理后台禁止

    public  static  Long DEFAULT_RADIUS = 5000L;

    public static final String USER_SESSION="userSession";



    public static final Long HOUR_OF_LINE_LIFT = (long)(8 * 3600 * 1000);//显示八小时


    public static Integer FIRST_JOIN_SCORE = 5;
    public static Integer FIND_LOST_SCORE = 5;
    public static Integer HOLIDAY_SCORE = 5;


    public static Integer MILEAGE_UP_1K = 1;
    public static Integer MILEAGE_UP_2K = 2;
    public static Integer MILEAGE_UP_4K = 5;
    public static Integer MILEAGE_UP_MAX = 10;



    public static Integer TIME_STAGE_1 = 0;
    public static Integer TIME_STAGE_2 = 2;
    public static Integer TIME_STAGE_3 = 6;
    public static Integer TIME_STAGE_4 = 10;



    public static Integer M = 10;//默认10米丢掉数据

    public static Integer StepByOneMill = 1333;//默认1公里 1333 步


    //超过两分钟分组
    public static Integer GROUP_TIME = 60 * 2;


    //超过100m分组
    public static Integer DISTANCE_FILTER = 100;

    //是否显示范围外
    public static boolean OVER_AREA = true;


    /**
     * 默认半径增量：1
     */
    public static Integer INCREMENT = 1;


    /**
     * 默认半径最大值：24
     */
    public static Integer MAX_ENCLOSURE_RADIUS = 24;


    public static String KEY = "12345678";


}
