package com.example.appmainserver.Collector;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.appmainserver.Mapper.OrderDao;
import com.example.appmainserver.Service.OrderService;
import com.example.appmainserver.Service.UserService;
import com.example.appmainserver.Utils.JsonReceive;
import com.example.appmainserver.Utils.RamdomString;
import com.example.appmainserver.bean.ServiceOrder;
import com.example.appmainserver.bean.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/main")
public class MainCollector {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    /**
     * 显示用户明细
     * @param request
     * @return requestjsonshowoption
     * @throws Exception
     */
    @RequestMapping(value = "/showoption")
    public JSONObject showoption(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonshowoption = jsonReceive.jsonreceive(request);
        String userName = requestjsonshowoption.getString("userName");
        User user = userService.selectShowOption(userName);
        Integer userCount = orderService.selectServiceTotal(userName);
        requestjsonshowoption.put("userSex",user.getUserSex());
        requestjsonshowoption.put("userType",user.getUserType());
        requestjsonshowoption.put("userCount",userCount);
        requestjsonshowoption.put("userHead",user.getUserHead());
        return requestjsonshowoption;
    }

    /**
     * 发布服务
     * @param request
     * @return null
     * @throws Exception
     */
    @RequestMapping(value = "/publish")
    public JSONObject publishService(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonpublish = jsonReceive.jsonreceive(request);
        System.out.println(requestjsonpublish);
        String no = RamdomString.random(16);
        while(orderService.selectServiceNo(no)){
            no = RamdomString.random(16);
        }
        String serviceNo = no;
        String serviceName = requestjsonpublish.getString("serviceName");
        Date serviceStart = requestjsonpublish.getDate("serviceStart");
        Date serviceEnd = requestjsonpublish.getDate("serviceEnd");
        String serviceProvider = requestjsonpublish.getString("serviceProvider");
        String serviceType = requestjsonpublish.getString("serviceType");
        String serviceCity = requestjsonpublish.getString("serviceCity");
        int servicePrice = requestjsonpublish.getInteger("servicePrice");
        orderService.insertServiceOrder(serviceNo,serviceName,serviceProvider,serviceStart,serviceEnd,serviceType,serviceCity,servicePrice);
        return null;
    }

    /**
     * 显示服务
     * @param request
     * @return jsonArray
     * @throws Exception
     */
    @RequestMapping(value = "/showorder")
    public JSONArray showOrder(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonorder = jsonReceive.jsonreceive(request);
        List<ServiceOrder> serviceOrders = orderService.selectServiceOrder();
        Gson gson = new Gson();
        String jsonResult = gson.toJson(serviceOrders);
        JSONArray jsonArray = JSONArray.parseArray(jsonResult);
        return jsonArray;
    }

    /**
     * 服务预订活动
     * @param request
     * @return null
     * @throws Exception
     */
    @Transactional
    @RequestMapping(value = "/order")
    public JSONObject orderAction(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonupdateorder = jsonReceive.jsonreceive(request);
        String serviceNo = requestjsonupdateorder.getString("serviceNo");
        String serviceCustomer = requestjsonupdateorder.getString("serviceCustomer");
        String serviceAddress = requestjsonupdateorder.getString("serviceAddress");
        Integer orderPrice = orderService.selectPrice(serviceNo);
        Integer userRest = userService.selectUserRest(serviceCustomer);
        if(userRest >= orderPrice)
            orderService.updateOrderAction(serviceCustomer,serviceNo,orderPrice,serviceAddress);
        else
            requestjsonupdateorder.put("msg","error");
        return requestjsonupdateorder;
    }

    /**
     * 显示服务人员信息
     * @param request
     * @return requestjsonshowprovider
     * @throws Exception
     */
    @RequestMapping(value = "/showprovider")
    public JSONObject showProvider(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonshowprovider = jsonReceive.jsonreceive(request);
        String serviceNo = requestjsonshowprovider.getString("serviceNo");
        String serviceProvider = requestjsonshowprovider.getString("serviceProvider");
        User user = userService.selectShowOption(serviceProvider);
        List<Integer> list = orderService.selectMarks(serviceProvider);
        Integer userMarks = 5;
        Integer total = 0;
        if(list.size() > 0){
            for(int i = 0 ; i < list.size() ; i++){
                total = total + list.get(i);
            }
            userMarks = total / list.size();
        }
        requestjsonshowprovider.put("userSex",user.getUserSex());
        requestjsonshowprovider.put("userCount",orderService.selectServiceTotal(serviceProvider));
        requestjsonshowprovider.put("userName",serviceProvider);
        requestjsonshowprovider.put("userHead",user.getUserHead());
        requestjsonshowprovider.put("userMarks",userMarks);
        requestjsonshowprovider.remove("serviceProvider");
        requestjsonshowprovider.remove("serviceNo");
        return requestjsonshowprovider;
    }

    /**
     * 充值余额事件
     * @param request
     * @return requestjsonmoneyin
     * @throws Exception
     */
    @RequestMapping(value = "/moneyin")
    public JSONObject moneyIn(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonmoneyin = jsonReceive.jsonreceive(request);
        String userName = requestjsonmoneyin.getString("userName");
        Integer userMoneyIn = requestjsonmoneyin.getInteger("userMoneyIn");
        userService.updateMoneyIn(userMoneyIn,userName);
        Integer userMoney = userService.selectUserRest(userName);
        requestjsonmoneyin.put("userMoney",userMoney);
        return requestjsonmoneyin;
    }

    /**
     * 查询用户余额
     * @param request
     * @return requestuserrest
     * @throws Exception
     */
    @RequestMapping(value = "/money")
    public JSONObject selectMoney(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestuserrest = jsonReceive.jsonreceive(request);
        String userName = requestuserrest.getString("userName");
        Integer userMoney = userService.selectUserRest(userName);
        requestuserrest.put("userMoney",userMoney);
        return requestuserrest;
    }

    /**
     * 用户提现操作
     * @param request
     * @return requestjsonmoneyout
     * @throws Exception
     */
    @RequestMapping(value = "/moneyout")
    public JSONObject moneyOut(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonmoneyout = jsonReceive.jsonreceive(request);
        String userName = requestjsonmoneyout.getString("userName");
        Integer userMoneyOut = requestjsonmoneyout.getInteger("userMoneyOut");
        userService.updateMoneyOut(userMoneyOut,userName);
        Integer userMoney = userService.selectUserRest(userName);
        requestjsonmoneyout.put("userMoney",userMoney);
        return requestjsonmoneyout;
    }


}
