package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.entity.SensorDo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SensorDoMapper {

    SensorDo findById(SensorDo sensorDo);

    int register(SensorDo sensorDo);

    List<SensorDo> lst(String terminal);

    int update(SensorDo sensorDo);

    int disCount(SensorDo sensorDo);

    int delete(SensorDo sensorDo);
}
