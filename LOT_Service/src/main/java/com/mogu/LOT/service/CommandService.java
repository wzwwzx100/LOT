package com.mogu.LOT.service;

import com.mogu.LOT.model.entity.CommandDo;
import com.mogu.LOT.model.entity.MessageDo;
import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.util.BizResult;

import java.util.List;

public interface CommandService {

    BizResult add(CommandDo commandDo);

    BizResult success(Long id);

    BizResult del(CommandDo commandDo);

    BizResult send(CommandDo commandDo);

    MessageDo createMessage(CommandDo commandDo) throws Exception;


    BizResult lst(int pageNum,int pageSize,CommandDo commandDo);

    List<CommandDo> findFailed(TerminalDo terminal);

}
