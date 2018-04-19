package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.entity.AdminUserDo;
import com.mogu.LOT.model.params.AdminPassPara;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserDoMapper {


    int insertSelective(AdminUserDo record);

    AdminUserDo selectByPrimaryKey(Integer id);

    AdminUserDo selectUserByName(String userName);//查询用户


    List<AdminUserDo> selectAdminList();


    Integer updatePass(AdminPassPara adminPassPara);


    Integer updateAdminUser(AdminUserDo adminUserDo);

}