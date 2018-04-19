package com.mogu.LOT.service;

import com.mogu.LOT.model.entity.AdminUserDo;
import com.mogu.LOT.model.params.AdminLoginPara;
import com.mogu.LOT.model.params.AdminPassPara;
import com.mogu.LOT.model.params.AdminUserPara;
import com.mogu.LOT.util.BizResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public interface AdminUserService {

    BizResult<AdminUserDo> doLogin(AdminLoginPara adminLoginPara, HttpServletRequest request);

    BizResult adminUserList(AdminUserDo adminUserDo, int pageNum,int pageSize);

    BizResult addAdminUser(AdminUserDo adminUserDo,AdminUserPara adminUserPara);

    BizResult updateAdminUser(AdminUserDo logined,AdminUserDo adminUserDo);

    BizResult delAdminUser(AdminUserDo adminUserDo, int id);

    BizResult updatePass(AdminPassPara adminPassPara);

}
