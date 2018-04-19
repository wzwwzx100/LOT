package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.entity.ResultDo;
import com.mogu.LOT.model.entity.TerminalDo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResultDoMapper {

    int save(ResultDo resultDo);

    List<ResultDo> lst(ResultDo resultDo);

    List<ResultDo> realValue(TerminalDo terminalDo);
}
