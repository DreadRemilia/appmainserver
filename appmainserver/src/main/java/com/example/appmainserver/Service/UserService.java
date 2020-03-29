package com.example.appmainserver.Service;

import com.example.appmainserver.Mapper.UserDao;
import com.example.appmainserver.bean.ServiceOrder;
import com.example.appmainserver.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(String userName,String userPassword,String userSex,String userType) {
        if(userType.equals("一般用户"))
            userDao.insertCommonUser(userName, userPassword, userSex, userType);
        else if(userType.equals("服务人员"))
            userDao.insertServiceUser(userName, userPassword, userSex, userType);
    }

    public boolean selectUser(String userName){
        if(userDao.selectCommonUser(userName) != null || userDao.selectServiceUser(userName) != null)
            return false;
        else
            return true;
    }

    public String selectType(String userName){
        return userDao.selectCommonType(userName) != null ? userDao.selectCommonType(userName).getUserType() : userDao.selectServiceType(userName).getUserType();
    }

    public boolean selectLogin(String userName,String userPassword){
        if(userDao.selectCommonLogin(userName,userPassword) != null || userDao.selectServiceLogin(userName,userPassword) != null)
            return true;
        else
            return false;
    }

    public User selectShowOption(String userName) {
        return userDao.selectCommonShowOption(userName) != null ? userDao.selectCommonShowOption(userName)
                : userDao.selectServiceShowOption(userName);
    }

    public void updateMoneyIn(int userMoney,String userName){
        if(userDao.selectCommonType(userName) != null){
            userDao.updateCommonMoneyIn(userMoney, userName);
        }else {
            userDao.updateServiceMoneyIn(userMoney, userName);
        }
    }

    public Integer selectUserRest(String userName){
        return userDao.selectCommonType(userName) != null ? userDao.selectCommonRest(userName) : userDao.selectServiceRest(userName);
    }

    public void updateMoneyOut(int userMoney,String userName){
        if(userDao.selectCommonType(userName) != null){
            userDao.updateCommonMoneyOut(userMoney, userName);
        }else {
            userDao.updateServiceMoneyOut(userMoney, userName);
        }
    }

    public void updateUserOption(String userType, String userPassword, String userSex, String userHead, String userName){
        if(userType.equals("一般用户")){
            userDao.updateCommonOption(userPassword, userSex, userHead, userName);
        }else {
            userDao.updateServiceOption(userPassword, userSex, userHead, userName);
        }
    }
}
