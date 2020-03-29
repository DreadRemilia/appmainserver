package com.example.appmainserver.Service;

import com.example.appmainserver.Mapper.OrderDao;
import com.example.appmainserver.Mapper.UserDao;
import com.example.appmainserver.bean.ServiceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertServiceOrder(String serviceNo, String serviceName, String serviceProvider, Date serviceStart, Date serviceEnd, String serviceType, String serviceCity, int servicePrice){
        orderDao.insertServiceOrder(serviceNo,serviceName,serviceProvider,serviceStart,serviceEnd,serviceType,serviceCity,servicePrice);
    }

    public boolean selectServiceNo(String serviceNo){
        return orderDao.selectServiceNo(serviceNo) != null ? true : false;
    }

    public List<ServiceOrder> selectServiceOrder(){
        return orderDao.selectServiceOrder();
    }

    public void updateOrderAction(String serviceCustomer,String serviceNo,int userMoney,String serviceAddress){
        orderDao.updateOrderAction(serviceCustomer,serviceNo,serviceAddress);
        userDao.updateCommonMoneyOut(userMoney,serviceCustomer);
    }

    public int selectServiceTotal(String userName){
        if(userDao.selectCommonType(userName) != null){
            return orderDao.selectCustomerTotal(userName);
        }else {
            return orderDao.selectServiceTotal(userName);
        }
    }

    public List<ServiceOrder> selectProviderHis(String userName){
        return orderDao.selectProviderHis(userName);
    }

    public List<ServiceOrder> selectCustomerHis(String userName){
        return orderDao.selectCustomerHis(userName);
    }

    public List<ServiceOrder> selectCurCst(String userName){
        return orderDao.selectCurCst(userName);
    }

    public List<ServiceOrder> selectCurSer(String userName){
        return orderDao.selectCurSer(userName);
    }

    public Integer selectPrice(String serviceNo){
        return orderDao.selectPrice(serviceNo);
    }

    public void deleteSerOrder(String serviceNo){
        orderDao.deleteSerOrder(serviceNo);
    }

    public ServiceOrder selectNoOrder(String serviceNo){
        return orderDao.selectNoOrder(serviceNo);
    }

    public void updateConfirmOrder(String serviceNo,String serviceMarks){
        orderDao.updateConfirmOrder(serviceNo,serviceMarks);
    }

    public void updateCstOrder(String serviceNo){
        orderDao.updateCstOrder(serviceNo);
    }

    public List<Integer> selectMarks(String serviceProvider){
        return orderDao.selectMarks(serviceProvider);
    }
}
