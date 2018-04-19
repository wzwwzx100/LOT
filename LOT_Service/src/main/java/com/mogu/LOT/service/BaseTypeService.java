package com.mogu.LOT.service;

import com.mogu.LOT.model.entity.BaseTypeDo;
import com.mogu.LOT.util.BizResult;

public interface BaseTypeService {

    BizResult get(BaseTypeDo baseTypeDo);

    BizResult register(BaseTypeDo baseTypeDo);

    BizResult delete(Integer id);

    BizResult modify(BaseTypeDo baseTypeDo);

    BizResult lst(Integer pageNum,Integer pageSize,BaseTypeDo baseTypeDo);
}
