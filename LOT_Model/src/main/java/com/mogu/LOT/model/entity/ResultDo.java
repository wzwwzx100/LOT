package com.mogu.LOT.model.entity;

import java.util.Date;

public class ResultDo {

    private Long id;
    private Date time;
    private Double value;
    private Integer status;
    private Integer valid;



    //terminal
    private TerminalDo terminal;
    //sensor
    private SensorDo sensor;

    private Date bg;
    private Date ed;


    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getEd() {
        return ed;
    }

    public void setEd(Date ed) {
        this.ed = ed;
    }

    public Date getBg() {
        return bg;
    }

    public void setBg(Date bg) {
        this.bg = bg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TerminalDo getTerminal() {
        return terminal;
    }

    public void setTerminal(TerminalDo terminal) {
        this.terminal = terminal;
    }

    public SensorDo getSensor() {
        return sensor;
    }

    public void setSensor(SensorDo sensor) {
        this.sensor = sensor;
    }

}
