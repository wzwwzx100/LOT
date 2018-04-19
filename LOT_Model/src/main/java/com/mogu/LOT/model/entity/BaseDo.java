package com.mogu.LOT.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by zfl on 2017/11/3.
 */
public class BaseDo  implements Serializable {
    private Integer id;
    private Integer valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }


    public static void main(String[] args){


        for(int i=1;i<22;i++){
            System.out.println("values.add(String.valueOf(avgM.getZ"+i+"()));");
        }

    }
}
