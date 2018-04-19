package com.mogu.LOT.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.dao.mapper.MessageTypeDoMapper;
import com.mogu.LOT.model.entity.BaseTypeDo;
import com.mogu.LOT.service.BaseTypeService;
import com.mogu.LOT.util.BizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageTypeService")
public class MessageTypeServiceImpl implements BaseTypeService {


    @Autowired
    private MessageTypeDoMapper messageTypeDoMapper;

    @Override
    public BizResult get(BaseTypeDo baseTypeDo) {
        return BizResult.success(messageTypeDoMapper.findById(baseTypeDo));
    }

    @Override
    public BizResult register(BaseTypeDo baseTypeDo) {
        BaseTypeDo exist = messageTypeDoMapper.findByCode(baseTypeDo.getCode());
        if(exist != null){
            return BizResult.error(ResultCode.CODE_EXIST);
        }
        baseTypeDo.setClazz(4);
        return BizResult.success(messageTypeDoMapper.register(baseTypeDo));
    }

    @Override
    public BizResult delete(Integer id) {
        return BizResult.success(messageTypeDoMapper.delete(id));
    }

    @Override
    public BizResult modify(BaseTypeDo baseTypeDo) {
        return BizResult.success(messageTypeDoMapper.modify(baseTypeDo));
    }

    @Override
    public BizResult lst(Integer pageNum, Integer pageSize,BaseTypeDo baseTypeDo) {
        if(pageNum == null || pageSize == null){
            return BizResult.success(messageTypeDoMapper.lst(null));
        }
        String orderBy = "id asc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<BaseTypeDo> lst = messageTypeDoMapper.lst(baseTypeDo);
        PageInfo pageInfo = new PageInfo(lst);
        return BizResult.success(pageInfo);
    }


}
