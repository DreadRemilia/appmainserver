package com.example.appmainserver.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.appmainserver.Service.UserService;
import com.example.appmainserver.Utils.JsonReceive;
import com.example.appmainserver.bean.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.alibaba.*;

@RestController
@RequestMapping(value = "/fa")
public class Collector {
    @Autowired
    private UserService userService;

    /**
     * 处理注册事件
     * @param request
     * @return requestJsonRegist
     * @throws Exception
     */
    @RequestMapping(value = "/regist")
    public JSONObject regist(HttpServletRequest request) throws Exception {
            JsonReceive jsonReceive = new JsonReceive();
            JSONObject requestJsonRegist = jsonReceive.jsonreceive(request);
            String userName = requestJsonRegist.getString("userName");
            String userPassword = requestJsonRegist.getString("userPassword");
            String userSex = requestJsonRegist.getString("userSex");
            String userType = requestJsonRegist.getString("userType");
            if(userService.selectUser(userName)){
                userService.insertUser(userName,userPassword,userSex,userType);
                requestJsonRegist.put("msg","注册成功");
            }
            else
                requestJsonRegist.put("msg","用户名重复");
        return requestJsonRegist;
    }

    /**
     * 处理登录事件
     * @param request
     * @return requestJsonlogin
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public JSONObject login(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestJsonlogin = jsonReceive.jsonreceive(request);
        String userName = requestJsonlogin.getString("userName");
        String userPassword = requestJsonlogin.getString("userPassword");
        if(userService.selectLogin(userName,userPassword)){
            requestJsonlogin.put("msg","登录成功");
        }
        else {
            requestJsonlogin.put("msg","用户名或密码错误");
        }
        return requestJsonlogin;
    }
}
