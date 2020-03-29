package com.example.appmainserver.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.appmainserver.Service.OrderService;
import com.example.appmainserver.Service.UserService;
import com.example.appmainserver.Utils.JsonReceive;
import com.example.appmainserver.bean.ServiceOrder;
import com.example.appmainserver.bean.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.List;

@RestController
@RequestMapping(value = "/option")
public class OptionCollector {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    /**
     * 查询所有服务
     *
     * @param request
     * @return jsonArray
     * @throws Exception
     */
    @RequestMapping(value = "/his")
    public JSONArray selectHisService(HttpServletRequest request) throws Exception {
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonhis = jsonReceive.jsonreceive(request);
        String userName = requestjsonhis.getString("userName");
        List<ServiceOrder> serviceOrders;
        if (userService.selectType(userName).equals("一般用户")) {
            serviceOrders = orderService.selectCustomerHis(userName);
        } else {
            serviceOrders = orderService.selectProviderHis(userName);
        }
        Gson gson = new Gson();
        String jsonResult = gson.toJson(serviceOrders);
        JSONArray jsonArray = JSONArray.parseArray(jsonResult);
        return jsonArray;
    }

    /**
     * 查询一般用户当前顶单
     *
     * @param request
     * @return jsonArray
     * @throws Exception
     */
    @RequestMapping(value = "/curcst")
    public JSONArray selectCurCst(HttpServletRequest request) throws Exception {
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsoncurcst = jsonReceive.jsonreceive(request);
        String userName = requestjsoncurcst.getString("userName");
        List<ServiceOrder> serviceOrders;
        serviceOrders = orderService.selectCurCst(userName);
        Gson gson = new Gson();
        String jsonResult = gson.toJson(serviceOrders);
        JSONArray jsonArray = JSONArray.parseArray(jsonResult);
        return jsonArray;
    }

    /**
     * 查询用户类别
     *
     * @param request
     * @return requesttype
     * @throws Exception
     */
    @RequestMapping(value = "/type")
    public JSONObject selectType(HttpServletRequest request) throws Exception {
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requesttype = jsonReceive.jsonreceive(request);
        String userName = requesttype.getString("userName");
        String userType = userService.selectType(userName);
        requesttype.put("userType", userType);
        System.out.println("fafa" + requesttype);
        return requesttype;
    }

    /**
     * 服务人员当前服务
     *
     * @param request
     * @return jsonArray
     * @throws Exception
     */
    @RequestMapping(value = "/curser")
    public JSONArray selectCurSer(HttpServletRequest request) throws Exception {
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsoncurser = jsonReceive.jsonreceive(request);
        String userName = requestjsoncurser.getString("userName");
        List<ServiceOrder> serviceOrders;
        serviceOrders = orderService.selectCurSer(userName);
        Gson gson = new Gson();
        String jsonResult = gson.toJson(serviceOrders);
        JSONArray jsonArray = JSONArray.parseArray(jsonResult);
        return jsonArray;
    }

    /**
     * 取消订单操作
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sercancel")
    public JSONObject deleteSerOrder(HttpServletRequest request) throws Exception {
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsondelserorder = jsonReceive.jsonreceive(request);
        String serviceNo = requestjsondelserorder.getString("serviceNo");
        ServiceOrder serviceOrder = orderService.selectNoOrder(serviceNo);
        if (serviceOrder.getServiceState().equals("发布") || serviceOrder.getServiceState().equals("待完成")) {
            if (serviceOrder.getServiceState().equals("待完成")) {
                userService.updateMoneyIn(Integer.valueOf(serviceOrder.getServicePrice()), serviceOrder.getServiceCustomer());
            }
            orderService.deleteSerOrder(serviceNo);
            return requestjsondelserorder;
        } else {
            String msg = "error";
            requestjsondelserorder.put("msg", msg);
            return requestjsondelserorder;
        }
    }

    /**
     * 服务人员显示用户联系方式
     *
     * @param request
     * @return requestjsonseraddress
     * @throws Exception
     */
    @RequestMapping(value = "/seraddress")
    public JSONObject selectAddress(HttpServletRequest request) throws Exception {
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonseraddress = jsonReceive.jsonreceive(request);
        String serviceNo = requestjsonseraddress.getString("serviceNo");
        ServiceOrder serviceOrder = orderService.selectNoOrder(serviceNo);
        requestjsonseraddress.put("serviceCustomer", serviceOrder.getServiceCustomer());
        requestjsonseraddress.put("serviceAddress", serviceOrder.getServiceAddress());
        return requestjsonseraddress;
    }

    /**
     * 用户确认订单操作
     * @param request
     * @return null
     * @throws Exception
     */
    @RequestMapping(value = "/csrcon")
    public JSONObject confirmOrder(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonconfirm = jsonReceive.jsonreceive(request);
        String serviceNo = requestjsonconfirm.getString("serviceNo");
        String serviceMarks = requestjsonconfirm.getString("serviceMarks");
        ServiceOrder serviceOrder = orderService.selectNoOrder(serviceNo);
        orderService.updateConfirmOrder(serviceNo,serviceMarks);
        userService.updateMoneyIn(Integer.valueOf(serviceOrder.getServicePrice()),serviceOrder.getServiceProvider());
        return null;
    }


    /**
     * 显示用户原设置
     * @param request
     * @return requestjsonshowuser
     * @throws Exception
     */
    @RequestMapping(value = "/showuser")
    public JSONObject showchgUser(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonshowuser = jsonReceive.jsonreceive(request);
        String userName = requestjsonshowuser.getString("userName");
        User user = userService.selectShowOption(userName);
        requestjsonshowuser.put("userPassword",user.getUserPassword());
        requestjsonshowuser.put("userSex",user.getUserSex());
        requestjsonshowuser.put("userType",user.getUserType());
        requestjsonshowuser.put("userHead",user.getUserHead());
        return requestjsonshowuser;
    }

    /**
     * 用户更改设置操作
     * @param request
     * @return null
     * @throws Exception
     */
    @RequestMapping(value = "/chgoption")
    public JSONObject changeUserOption(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsonchangeoption = jsonReceive.jsonreceive(request);
        String userType = requestjsonchangeoption.getString("userType");
        String userName = requestjsonchangeoption.getString("userName");
        String userPassword = requestjsonchangeoption.getString("userPassword");
        String userSex = requestjsonchangeoption.getString("userSex");
        String userHead = requestjsonchangeoption.getString("userHead");
        //Blob blobUserHead = new SerialBlob(userHead.getBytes());
        userService.updateUserOption(userType,userPassword,userSex,userHead,userName);
        return null;
    }


    /**
     * 客户取消订单
     * @param request
     * @return null
     * @throws Exception
     */
    @RequestMapping(value = "/cstcancel")
    public JSONObject sctCancel(HttpServletRequest request) throws Exception{
        JsonReceive jsonReceive = new JsonReceive();
        JSONObject requestjsoncstcancel = jsonReceive.jsonreceive(request);
        String serviceNo = requestjsoncstcancel.getString("serviceNo");
        orderService.updateCstOrder(serviceNo);
        return null;
    }
}

