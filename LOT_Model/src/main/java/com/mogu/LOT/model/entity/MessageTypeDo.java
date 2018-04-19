package com.mogu.LOT.model.entity;

public class MessageTypeDo extends BaseTypeDo{
    private Integer direction;
    private Integer valid;

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
