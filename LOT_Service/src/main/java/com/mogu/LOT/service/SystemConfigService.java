package com.mogu.LOT.service;

import com.mogu.LOT.model.common.SystemConfig;
import com.mogu.LOT.util.BizResult;

public interface SystemConfigService {

    BizResult add(SystemConfig systemConfig);

    BizResult modify(SystemConfig systemConfig);

    BizResult lst();

    SystemConfig findByCode(String code);
}
