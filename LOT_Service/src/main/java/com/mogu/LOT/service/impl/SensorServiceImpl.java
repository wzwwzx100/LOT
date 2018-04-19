package com.mogu.LOT.service.impl;

import com.mogu.LOT.dao.mapper.SensorDoMapper;
import com.mogu.LOT.model.entity.SensorDo;
import com.mogu.LOT.service.SensorService;
import com.mogu.LOT.util.BizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SensorServiceImpl implements SensorService {


    @Autowired
    private SensorDoMapper sensorDoMapper;


    @Override
    public BizResult findById(SensorDo sensorDo) {
        return BizResult.success(sensorDoMapper.findById(sensorDo));
    }

    @Override
    public BizResult register(SensorDo sensorDo) {
        int ret = sensorDoMapper.register(sensorDo);
        return ret == 1? BizResult.success() : BizResult.error("注册失败！");
    }

    @Override
    public BizResult modify(SensorDo sensorDo) {
        int ret = sensorDoMapper.update(sensorDo);
        return ret == 1? BizResult.success() : BizResult.error("修改失败！");
    }

    @Override
    public BizResult delete(SensorDo sensorDo) {
        int ret = sensorDoMapper.delete(sensorDo);
        return ret == 1? BizResult.success() : BizResult.error("删除失败！");
    }

    @Override
    public BizResult lst(String terminal) {
        List<SensorDo> lst = sensorDoMapper.lst(terminal);
        return BizResult.success(lst);
    }
}
