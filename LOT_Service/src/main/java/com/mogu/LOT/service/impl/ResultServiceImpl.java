package com.mogu.LOT.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogu.LOT.dao.mapper.ResultDoMapper;
import com.mogu.LOT.dao.mapper.SensorDoMapper;
import com.mogu.LOT.model.entity.ResultDo;
import com.mogu.LOT.model.entity.SensorDo;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.service.ResultService;
import com.mogu.LOT.util.BizResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultDoMapper resultDoMapper;

    @Autowired
    private SensorDoMapper sensorDoMapper;

    private static final Logger log = LoggerFactory.getLogger(ResultService.class);


    @Override
    public BizResult lst(int pageNum, int pageSize, ResultDo result) {
        String orderBy = "time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<ResultDo> lst = resultDoMapper.lst(result);
        PageInfo pageInfo = new PageInfo(lst);
        return BizResult.success(pageInfo);
    }

    @Override
    public BizResult save(ResultDo result) {
        SensorDo sensorDo = new SensorDo();
        //sensor
        sensorDo.setLastTime(result.getTime());
        sensorDoMapper.update(sensorDo);
        result.setSensor(sensorDo);
        return resultDoMapper.save(result) == 1? BizResult.success() : BizResult.error("保存失败！");
    }

    @Override
    public BizResult realValue(TerminalDo terminalDo) {
        return BizResult.success(resultDoMapper.realValue(terminalDo));
    }
}
