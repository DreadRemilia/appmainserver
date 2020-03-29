package com.example.appmainserver.Mapper;

import com.example.appmainserver.bean.ServiceOrder;
import com.example.appmainserver.bean.User;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {
    //查找用户名
    @Select("SELECT userName FROM common_user WHERE userName = #{userName}")
    User selectCommonUser(@Param("userName")String userName);

    @Select("SELECT userName FROM service_user WHERE userName = #{userName}")
    User selectServiceUser(@Param("userName")String userName);

    //查找用户类型
    @Select("SELECT userType FROM common_user WHERE userName = #{userName}")
    User selectCommonType(@Param("userName")String userName);

    @Select("SELECT userType FROM service_user WHERE userName = #{userName}")
    User selectServiceType(@Param("userName")String userName);

    //注册用户插入表
    @Insert("INSERT INTO common_user(userName,userPassword,userSex,userType) VALUES (#{userName},#{userPassword},#{userSex},#{userType})")
    void insertCommonUser(@Param("userName")String userName,@Param("userPassword")String userPassword,@Param("userSex")String userSex,
                    @Param("userType")String userType);

    @Insert("INSERT INTO service_user(userName,userPassword,userSex,userType) VALUES (#{userName},#{userPassword},#{userSex},#{userType})")
    void insertServiceUser(@Param("userName")String userName,@Param("userPassword")String userPassword,@Param("userSex")String userSex,
                    @Param("userType")String userType);

    //查找登录信息
    @Select("SELECT userName FROM common_user WHERE userName = #{userName} AND userPassword = #{userPassword}")
    User selectCommonLogin(@Param("userName")String userName,@Param("userPassword")String userPassword);

    @Select("SELECT userName FROM service_user WHERE userName = #{userName} AND userPassword = #{userPassword}")
    User selectServiceLogin(@Param("userName")String userName,@Param("userPassword")String userPassword);

    //查找用户明细
    @Select("SELECT * FROM common_user WHERE userName = #{userName}")
    User selectCommonShowOption(@Param("userName")String userName);

    @Select("SELECT * FROM service_user WHERE userName = #{userName}")
    User selectServiceShowOption(@Param("userName")String userName);

    //用户充值操作
    @Update("UPDATE common_user SET userMoney = userMoney + #{userMoney} WHERE userName = #{userName}")
    void updateCommonMoneyIn(@Param("userMoney")int userMoney,@Param("userName")String userName);

    @Update("UPDATE service_user SET userMoney = userMoney + #{userMoney} WHERE userName = #{userName}")
    void updateServiceMoneyIn(@Param("userMoney")int userMoney,@Param("userName")String userName);

    //查询用户余额
    @Select("SELECT userMoney FROM common_user WHERE userName = #{userName}")
    Integer selectCommonRest(@Param("userName")String userName);

    @Select("SELECT userMoney FROM service_user WHERE userName = #{userName}")
    Integer selectServiceRest(@Param("userName")String userName);

    //用户提现操作
    @Update("UPDATE common_user SET userMoney = userMoney - #{userMoney} WHERE userName = #{userName}")
    void updateCommonMoneyOut(@Param("userMoney")int userMoney,@Param("userName")String userName);

    @Update("UPDATE service_user SET userMoney = userMoney - #{userMoney} WHERE userName = #{userName}")
    void updateServiceMoneyOut(@Param("userMoney")int userMoney,@Param("userName")String userName);

    //用户修改设置
    @Update("UPDATE common_user SET userPassword = #{userPassword} , userSex = #{userSex} , userHead = #{userHead} " +
            "Where userName = #{userName}")
    void updateCommonOption(@Param("userPassword")String userPassword,@Param("userSex")String userSex
    , @Param("userHead")String userHead , @Param("userName")String userName);

    @Update("UPDATE service_user SET userPassword = #{userPassword} , userSex = #{userSex} , userHead = #{userHead} " +
            "Where userName = #{userName}")
    void updateServiceOption(@Param("userPassword")String userPassword, @Param("userSex")String userSex
            , @Param("userHead") String userHead , @Param("userName")String userName);
}
