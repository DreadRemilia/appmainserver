package com.example.appmainserver.Mapper;

import com.example.appmainserver.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserDao {
    @Select("SELECT userName FROM user WHERE userName = #{userName}")
    User selectUser(@Param("userName")String userName);

    @Insert("INSERT INTO user(userName,userPassword,userSex,userType) VALUES (#{userName},#{userPassword},#{userSex},#{userType})")
    void insertUser(@Param("userName")String userName,@Param("userPassword")String userPassword,@Param("userSex")String userSex,@Param("userType")String userType);

    @Select("SELECT userName FROM user WHERE userName = #{userName} AND userPassword = #{userPassword}")
    User selectLogin(@Param("userName")String userName,@Param("userPassword")String userPassword);
}
