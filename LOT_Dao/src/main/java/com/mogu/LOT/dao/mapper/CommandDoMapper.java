package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.entity.CommandDo;
import com.mogu.LOT.model.entity.TerminalDo;

import java.util.List;

public interface CommandDoMapper {
    int add(CommandDo commandDo);

    int del(Long id);

    int success(Long id);

    List<CommandDo> lst(CommandDo commandDo);

    List<CommandDo> findFailed(TerminalDo terminal);

}
