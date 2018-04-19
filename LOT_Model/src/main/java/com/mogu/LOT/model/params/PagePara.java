package com.mogu.LOT.model.params;

/**
 * Created by chang on 2017/6/26.
 */
public class PagePara {
    private Integer pageSize = 10;
    private Integer pageNumber = 1;
    private Integer orderBy = 0;//默认desc

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
