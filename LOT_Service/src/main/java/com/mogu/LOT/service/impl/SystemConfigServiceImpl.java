package com.mogu.LOT.service.impl;

import com.mogu.LOT.constants.CommonConstant;
import com.mogu.LOT.dao.mapper.SystemConfigMapper;
import com.mogu.LOT.model.common.SystemConfig;
import com.mogu.LOT.service.SystemConfigService;
import com.mogu.LOT.util.BizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SystemConfigServiceImpl implements SystemConfigService {



    @Autowired
    private SystemConfigMapper systemConfigMapper;



    @Override
    public BizResult add(SystemConfig systemConfig) {
        int ret = systemConfigMapper.add(systemConfig);
        if(ret == 1){
            Map config = CommonConstant.config.get();
            if(config == null){
                config = new ConcurrentHashMap();
            }
            config.put(systemConfig.getCode(),systemConfig.getValue());
            CommonConstant.config.set(config);
            return BizResult.success();
        }else{
            return BizResult.error();
        }
    }

    @Override
    public BizResult modify(SystemConfig systemConfig) {
        int ret = systemConfigMapper.modify(systemConfig);
        if(ret == 1){
            Map config = CommonConstant.config.get();
            if(config == null){
                config = new ConcurrentHashMap();
            }
            config.put(systemConfig.getCode(),systemConfig.getValue());
            CommonConstant.config.set(config);
            return BizResult.success();
        }else{
            return BizResult.error();
        }
    }

    @Override
    public BizResult lst() {
        List<SystemConfig> lst = systemConfigMapper.lst();
        return BizResult.success(lst);
    }

    @Override
    public SystemConfig findByCode(String code) {
        SystemConfig systemConfig = null;
        Map config = CommonConstant.config.get();
        if(config == null || config.get(code) == null){
            config = new ConcurrentHashMap<String,String>();
            systemConfig = systemConfigMapper.findByCode(code);
            config.put(code,systemConfig.getValue());
            CommonConstant.config.set(config);
        }else{
            systemConfig = new SystemConfig();
            systemConfig.setCode(code);
            systemConfig.setValue(config.get(code).toString());
        }
        return systemConfig;
    }
}
