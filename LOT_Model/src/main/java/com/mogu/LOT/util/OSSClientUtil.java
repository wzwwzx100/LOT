package com.mogu.LOT.util;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by zfl on 2017/10/16.
 */
public class OSSClientUtil implements InitializingBean {

    private String endpoint;
    private String bucketName;
    public static String BUCKET_NAME;
    private String access_key_id;
    private String access_key_secret;
    private static OSSClient ossClient;


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


    public String getAccess_key_id() {
        return access_key_id;
    }

    public void setAccess_key_id(String access_key_id) {
        this.access_key_id = access_key_id;
    }

    public String getAccess_key_secret() {
        return access_key_secret;
    }

    public void setAccess_key_secret(String access_key_secret) {
        this.access_key_secret = access_key_secret;
    }

    public static final Logger logger = LoggerFactory.getLogger(OSSClientUtil.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        BUCKET_NAME = bucketName;
        ossClient = new OSSClient(endpoint, access_key_id, access_key_secret);
    }

    public static OSSClient getInstance(){
        return ossClient;
    }
}
