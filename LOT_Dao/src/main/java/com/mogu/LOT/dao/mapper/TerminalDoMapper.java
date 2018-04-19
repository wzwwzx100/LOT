package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.entity.TerminalDo;
import com.mogu.LOT.model.params.TerminalParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminalDoMapper {

    TerminalDo findById(String id);

    int register(TerminalDo terminal);

    int modify(TerminalDo terminal);

    int delete(TerminalDo terminal);


    List<TerminalDo> lst(TerminalDo terminalDo);

    List<TerminalDo> offLine(TerminalParam terminalDo);




}
