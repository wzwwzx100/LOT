package com.mogu.LOT.model.params;

import com.mogu.LOT.model.entity.TerminalDo;

public class TerminalParam extends TerminalDo {
    private Long time;
    private Integer pageSize;
    private Integer pageNum;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
