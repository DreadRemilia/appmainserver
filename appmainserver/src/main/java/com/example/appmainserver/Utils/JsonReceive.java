package com.example.appmainserver.Utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JsonReceive {
    /**
     * JSON处理方法
     * @param request
     * @return requestJson
     * @throws Exception
     */
    public JSONObject jsonreceive(HttpServletRequest request) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String s = null;
        while((s = br.readLine()) != null){
            sb.append(s);
        }
        JSONObject requestJson = JSONObject.parseObject(sb.toString());
        return requestJson;
    }
}
