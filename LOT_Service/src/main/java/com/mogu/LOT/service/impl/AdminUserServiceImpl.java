package com.mogu.LOT.service.impl;
/**
 * Created by Administrator on 2017/6/26 0026.
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogu.LOT.Enum.ResultCode;
import com.mogu.LOT.constants.CommonConstant;
import com.mogu.LOT.dao.mapper.AdminUserDoMapper;
import com.mogu.LOT.model.entity.AdminUserDo;
import com.mogu.LOT.model.common.BCrypt;
import com.mogu.LOT.model.params.AdminLoginPara;
import com.mogu.LOT.model.params.AdminPassPara;
import com.mogu.LOT.model.params.AdminUserPara;
import com.mogu.LOT.service.AdminUserService;
import com.mogu.LOT.util.BizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Service
public class AdminUserServiceImpl implements AdminUserService {



    @Autowired
    private AdminUserDoMapper adminUserDoMapper;


    @Override
    public BizResult<AdminUserDo> doLogin(AdminLoginPara adminLoginPara, HttpServletRequest request) {

        AdminUserDo adminUserDo = adminUserDoMapper.selectUserByName(adminLoginPara.getUserName());
        if (adminUserDo==null) return BizResult.error(ResultCode.NO_USER);
        String hasPass = adminUserDo.getPwd();
        if (BCrypt.checkpw(adminLoginPara.getPwd(),hasPass)){
            AdminUserDo user = new AdminUserDo();
            user.setId(adminUserDo.getId());
            HttpSession session = request.getSession();
            System.out.println("ADMIN SESSION:"+session.getId());
            session.setAttribute(CommonConstant.USER_SESSION,adminUserDo);
            return BizResult.success(adminUserDo);
        }
        return BizResult.error(ResultCode.ADMIN_LOGIN_ERROR);
    }

    @Override
    public BizResult adminUserList(AdminUserDo adminUserDo, int pageNum,int pageSize) {
        Integer role = 1;
        if (role!=null && role==1){
            String orderBy = "id asc";
            PageHelper.startPage(pageNum,pageSize,orderBy);
            List<AdminUserDo> adminUserDoList = adminUserDoMapper.selectAdminList();
            PageInfo pageInfo = new PageInfo(adminUserDoList);
            return BizResult.success(pageInfo);
        }
        return BizResult.error(ResultCode.ADMIN_ROLE);
    }

    @Override
    public BizResult addAdminUser(AdminUserDo adminUserDo,AdminUserPara adminUserPara) {
        AdminUserDo exist = adminUserDoMapper.selectUserByName(adminUserPara.getUserName());
        if (exist!=null) return BizResult.error(ResultCode.USER_HAVE);
        if(adminUserDo.getRole() != null && adminUserDo.getRole() == 1){
            String salt = BCrypt.gensalt();
            String hashPass = BCrypt.hashpw(adminUserPara.getPwd(),salt);
            AdminUserDo adminUse = new AdminUserDo();
            adminUse.setPwd(hashPass);
            adminUse.setSalt(salt);
            adminUse.setUserName(adminUserPara.getUserName());
            adminUse.setRole(0);
            Integer ret = adminUserDoMapper.insertSelective(adminUse);
            if(ret == 1){
                return  BizResult.success();
            }
            return BizResult.error();
        }else
            return BizResult.error(ResultCode.ADMIN_ROLE);

    }

    @Override
    public BizResult updateAdminUser(AdminUserDo logined,AdminUserDo adminUserDo) {
        if(logined.getRole() != null && logined.getRole() == 1){
            String salt = BCrypt.gensalt();
            adminUserDo.setSalt(salt);
            adminUserDo.setPwd(BCrypt.hashpw(adminUserDo.getPwd(),salt));
            Integer ret = adminUserDoMapper.updateAdminUser(adminUserDo);
            if(ret == 1){
                return  BizResult.success();
            }
            return BizResult.error();
        }else
            return BizResult.error(ResultCode.ADMIN_ROLE);
    }


    @Override
    public BizResult delAdminUser(AdminUserDo adminUserDo, int id) {
        AdminUserDo adminUserDo1 = adminUserDoMapper.selectByPrimaryKey(id);
        if (adminUserDo1.getRole()==1){
            return BizResult.error(ResultCode.SUPER_ROLE_ERROR);
        }
        Integer role = adminUserDo.getRole();
        if (role!=null && role==1){
            AdminUserDo delUser = new AdminUserDo();
            delUser.setValid(0);
            delUser.setId(id);
            Integer ret = adminUserDoMapper.updateAdminUser(delUser);
            if (ret==1){
                return BizResult.success();
            }
        }
        return BizResult.error(ResultCode.ADMIN_ROLE);
    }

    @Override
    public BizResult updatePass(AdminPassPara adminPassPara ) {
        if (!adminPassPara.getNewPwd().equals(adminPassPara.getConfirmPwd())) return BizResult.error(ResultCode.PSD_CONFIRM_ERROR);
        AdminUserDo adminUserDo = adminUserDoMapper.selectByPrimaryKey(adminPassPara.getId());
        String pwd = adminPassPara.getNewPwd();
        if(!pwd.matches("\\S{6,18}"))return BizResult.error(ResultCode.PSD_LENTH_ERROR);
        if (!BCrypt.checkpw(adminPassPara.getOldPwd(),adminUserDo.getPwd())) return BizResult.error(ResultCode.OLD_PSD_ERROR);
        String hashPsd = BCrypt.hashpw(adminPassPara.getNewPwd(),BCrypt.gensalt());
        adminPassPara.setNewPwd(hashPsd);
        Integer ret = adminUserDoMapper.updatePass(adminPassPara);
        if (ret==1){
            return BizResult.success();
        }
        return BizResult.error();
    }


}
