package com.mogu.LOT.service;

import com.mogu.LOT.model.entity.ResultDo;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.util.BizResult;

public interface ResultService {
    BizResult lst(int pageNum,int pageSize,ResultDo result);

    BizResult save(ResultDo result);

    BizResult realValue(TerminalDo terminalDo);
}
