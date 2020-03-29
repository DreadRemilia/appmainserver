package com.example.appmainserver.Mapper;

import com.example.appmainserver.Service.OrderService;
import com.example.appmainserver.bean.ServiceOrder;
import com.example.appmainserver.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderDao {
    //插入服务信息
    @Insert("INSERT INTO service_order(serviceNo,serviceName,serviceProvider,serviceStart,serviceEnd,serviceType,serviceCity,servicePrice)" +
            "VALUES (#{serviceNo},#{serviceName},#{serviceProvider},#{serviceStart},#{serviceEnd},#{serviceType},#{serviceCity},#{servicePrice})")
    void insertServiceOrder(@Param("serviceNo")String serviceNo, @Param("serviceName")String serviceName, @Param("serviceProvider")String serviceProvider
            , @Param("serviceStart") Date serviceStart, @Param("serviceEnd")Date serviceEnd
            , @Param("serviceType")String serviceType, @Param("serviceCity")String serviceCity
            , @Param("servicePrice")int servicePrice);

    //查询服务号
    @Select("SELECT serviceNo FROM service_order WHERE serviceNo = #{serviceNo}")
    User selectServiceNo(@Param("serviceNo")String serviceNo);

    //查询所有服务
    @Select("SELECT * FROM service_order WHERE serviceState = '发布' AND serviceStart > Now()")
    List<ServiceOrder> selectServiceOrder();

    //插入服务客户
    @Update("UPDATE service_order SET serviceCustomer = #{serviceCustomer} , serviceState = '待完成' , serviceAddress = #{serviceAddress}" +
            "WHERE serviceNo = #{serviceNo}")
    void updateOrderAction(@Param("serviceCustomer")String serviceCustomer, @Param("serviceNo")String serviceNo, @Param("serviceAddress")
                           String serviceAddress);

    //查询服务总数
    @Select("SELECT COUNT(*) FROM service_order WHERE serviceProvider = #{serviceProvider}")
    int selectServiceTotal(@Param("serviceProvider")String serviceProvider);

    @Select("SELECT COUNT(*) FROM service_order WHERE serviceCustomer = #{serviceCustomer}")
    int selectCustomerTotal(@Param("serviceCustomer")String serviceCustomer);

    //查询服务人员全部服务
    @Select("SELECT * FROM service_order WHERE serviceProvider = #{serviceProvider}")
    List<ServiceOrder> selectProviderHis(@Param("serviceProvider")String serviceProvider);

    //查询一般客户全部服务
    @Select("SELECT * FROM service_order WHERE serviceCustomer = #{serviceCustomer}")
    List<ServiceOrder> selectCustomerHis(@Param("serviceCustomer")String serviceCustomer);

    //查询当前客户待完成订单
    @Select("SELECT * FROM service_order WHERE serviceCustomer = #{serviceCustomer} AND (serviceState = '待完成' OR serviceState = '待确认')")
    List<ServiceOrder> selectCurCst(@Param("serviceCustomer")String serviceCustomer);

    //查询当前服务人员订单
    @Select("SELECT * FROM service_order WHERE serviceProvider = #{serviceProvider} AND ((serviceState = '发布' " +
            "AND serviceStart > Now()) OR (serviceState = '待完成' OR serviceState = '待确认')) ")
    List<ServiceOrder> selectCurSer(@Param("serviceProvider")String serviceProvider);

    //查询当前订单金额
    @Select("SELECT servicePrice FROM service_order WHERE serviceNo = #{serviceNo}")
    Integer selectPrice(@Param("serviceNo")String serviceNo);

    //服务人员订单取消
    @Delete("DELETE FROM service_order WHERE serviceNo = #{serviceNo}")
    void deleteSerOrder(@Param("serviceNo")String serviceNo);

    //以服务号查询指定服务
    @Select("SELECT * FROM service_order WHERE serviceNo = #{serviceNo}")
    ServiceOrder selectNoOrder(@Param("serviceNo")String serviceNo);

    //订单确认
    @Update("UPDATE service_order SET serviceState = '已完成' , serviceMarks = #{serviceMarks} WHERE serviceNo = #{serviceNo}")
    void updateConfirmOrder(@Param("serviceNo")String serviceNo , @Param("serviceMarks")String serviceMarks);

    //一般用户订单取消
    @Update("UPDATE service_order SET serviceState = '发布' , serviceCustomer = null , serviceAddress = null " +
            "WHERE serviceNo = #{serviceNo}")
    void updateCstOrder(@Param("serviceNo")String serviceNo);

    //查询用户订单金额
    @Select("SELECT serviceMarks FROM service_order WHERE serviceProvider = #{serviceProvider} AND " +
            "serviceState = '已完成'")
    List<Integer> selectMarks(@Param("serviceProvider")String serviceProvider);
}
