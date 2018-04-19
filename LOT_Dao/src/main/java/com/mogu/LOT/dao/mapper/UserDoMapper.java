package com.mogu.LOT.dao.mapper;

import com.mogu.LOT.model.entity.UserDo;

import java.util.List;

public interface UserDoMapper {

    int add(UserDo userDo);
    int del(UserDo userDo);
    int modify(UserDo userDo);
    UserDo findByTel(String tel);
    UserDo selectByPrimaryKey(Long id);
    List<String> selectUserList();
    List<String> selectdisabled();

}
