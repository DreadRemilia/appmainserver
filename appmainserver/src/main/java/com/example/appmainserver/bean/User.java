package com.example.appmainserver.bean;

import java.sql.Blob;

public class User {
    private String userName;
    private String userPassword;
    private String userSex;
    private String userType;
    private String userCount;
    private String msg;
    private String userMoney;
    private String userHead;

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPw) {
        this.userPassword = userPw;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
