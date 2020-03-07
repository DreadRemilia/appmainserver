package com.example.appmainserver.Service;

import com.example.appmainserver.Mapper.UserDao;
import com.example.appmainserver.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(String userName,String userPassword,String userSex,String userType) {
        userDao.insertUser(userName, userPassword, userSex, userType);
    }

    public boolean selectUser(String userName){
        if(userDao.selectUser(userName) != null)
            return false;
        else
            return true;
    }

    public boolean selectLogin(String userName,String userPassword){
        if(userDao.selectLogin(userName,userPassword) != null)
            return true;
        else
            return false;
    }
}
