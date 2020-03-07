package com.example.appmainserver.bean;

public class User {
    private String userName;
    private String userPassword;
    private String userSex;
    private String userType;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    private String msg;


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
