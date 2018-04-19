package com.mogu.LOT.service;

import com.mogu.LOT.model.params.MessageParam;
import com.mogu.LOT.model.entity.MsgInfoDo;
import com.mogu.LOT.util.BizResult;

public interface MessageService {

    BizResult addMessage(MsgInfoDo msgInfoDo);


    BizResult lst(MessageParam msg);
}
