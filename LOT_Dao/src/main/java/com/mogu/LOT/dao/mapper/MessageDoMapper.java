package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.params.MessageParam;
import com.mogu.LOT.model.entity.MsgInfoDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDoMapper {
    int addMessage(MsgInfoDo msgInfoDo);

    List<MsgInfoDo> lst(MessageParam msgInfoDo);
}
