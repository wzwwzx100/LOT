package com.mogu.LOT.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.dao.mapper.SensorTypeDoMapper;
import com.mogu.LOT.model.entity.BaseTypeDo;
import com.mogu.LOT.service.BaseTypeService;
import com.mogu.LOT.util.BizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("sensorTypeService")
public class SensorTypeServiceImpl implements BaseTypeService {


    @Autowired
    private SensorTypeDoMapper sensorTypeDoMapper;

    @Override
    public BizResult get(BaseTypeDo baseTypeDo) {
        return BizResult.success(sensorTypeDoMapper.findById(baseTypeDo));
    }

    @Override
    public BizResult register(BaseTypeDo baseTypeDo) {
        BaseTypeDo exist = sensorTypeDoMapper.findByCode(baseTypeDo.getCode());
        if(exist != null){
            return BizResult.error(ResultCode.CODE_EXIST);
        }
        baseTypeDo.setClazz(1);
        return sensorTypeDoMapper.register(baseTypeDo) == 1 ? BizResult.success() : BizResult.error();
    }

    @Override
    public BizResult delete(Integer id) {
        return sensorTypeDoMapper.delete(id) == 1 ? BizResult.success() : BizResult.error();
    }

    @Override
    public BizResult modify(BaseTypeDo baseTypeDo) {
        return sensorTypeDoMapper.modify(baseTypeDo) == 1? BizResult.success() : BizResult.error();
    }

    @Override
    public BizResult lst(Integer pageNum, Integer pageSize,BaseTypeDo baseTypeDo) {
        if(pageNum == null || pageSize == null){
            return BizResult.success(sensorTypeDoMapper.lst(null));
        }
        String orderBy = "id asc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<BaseTypeDo> lst = sensorTypeDoMapper.lst(baseTypeDo);
        PageInfo pageInfo = new PageInfo(lst);
        return BizResult.success(pageInfo);
    }
}
