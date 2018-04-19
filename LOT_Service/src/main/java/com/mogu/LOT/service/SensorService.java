package com.mogu.LOT.service;

import com.mogu.LOT.model.entity.SensorDo;
import com.mogu.LOT.util.BizResult;

public interface SensorService {
    BizResult findById(SensorDo sensorDo);

    BizResult register(SensorDo sensorDo);

    BizResult modify(SensorDo sensorDo);

    BizResult delete(SensorDo sensorDo);

    BizResult lst(String terminal);
}
