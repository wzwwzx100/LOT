package com.mogu.LOT.model.params;

import com.mogu.LOT.model.entity.MsgInfoDo;

import java.util.Date;

public class MessageParam extends MsgInfoDo {
    private Integer pageSize;
    private Integer pageNum;
    private Date bg;
    private Date ed;
    private String text;

    public Date getBg() {
        return bg;
    }

    public void setBg(Date bg) {
        this.bg = bg;
    }

    public Date getEd() {
        return ed;
    }

    public void setEd(Date ed) {
        this.ed = ed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(text == null || text.length() == 0){
            this.text = null;
        }else
            this.text = "%"+text+"%";
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
