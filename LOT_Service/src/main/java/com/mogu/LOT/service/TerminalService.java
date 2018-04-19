package com.mogu.LOT.service;

import com.mogu.LOT.model.entity.MessageDo;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.model.params.TerminalParam;
import com.mogu.LOT.util.BizResult;

public interface TerminalService {

    BizResult register(TerminalDo terminal);


    BizResult modify(TerminalDo terminal);

    BizResult delete(TerminalDo terminal);

    BizResult lst(int pageNum,int pageSize,TerminalDo terminalDo);

    BizResult offLine(int pageNum,int pageSize,TerminalParam terminalDo);

    TerminalDo findById(String id);


    BizResult issueConfig(TerminalDo terminalDo);

    BizResult resendCommand(TerminalDo terminalDo);

    MessageDo getCommand(TerminalDo terminalDo);

    BizResult successCommand(Long cmd_id);


}
