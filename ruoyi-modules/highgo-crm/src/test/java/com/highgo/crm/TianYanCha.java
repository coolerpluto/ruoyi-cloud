package com.highgo.crm;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.highgo.crm.domain.Company;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.RandomUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TianYanCha
{
    public static void main(String[] args){
        List<Company> ee = main2(args);
        System.out.println(ee.toString());
    }
    public static List<Company> main2(String[] args){
        try
        {
            return mains();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(((ServiceException) e).getCode());
        }
        System.out.println(2222222);
        List<Company> companies = new ArrayList<>();
        Company c = new Company();
        c.setAddr("2342343434");
        companies.add(c);
        return companies;
    }
    public static List<Company> mains()
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.api.tianyancha.com/services/open/search/2.0?word={key}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "871bf862-2598-4161-bf48-4a3205637722");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, request, JSONObject.class, "瀚高");
        response.getStatusCodeValue();
        response.getHeaders();

        JSONObject body = response.getBody();
        if(0 != body.getInteger("error_code")){
            throw new ServiceException(body.getString("reason"),body.getInteger("error_code"));
        }
        System.out.println("234342434333");
        JSONObject result = JSONObject.parseObject(JSON.toJSONString(body.get("result")));
        JSONArray items = result.getJSONArray("items");

        List<Company> resReturn = new ArrayList<>();
        for (int i = 0; i < items.size(); i++)
        {
            JSONObject jsonObject = items.getJSONObject(i);
            Company companyTemp = new Company();

            String rt = DateUtils.parseDateToStr("yyyyMMdd HHmmss", new Date());
            String code = rt.replace(" ", RandomUtils.generateString(6));
            companyTemp.setCode("sourceType" + code);

            companyTemp.setCompanyName(jsonObject.getString("name"));
            companyTemp.setCapitalReg(jsonObject.getString("regCapital"));
            companyTemp.setLegal(jsonObject.getString("legalPersonName"));
            companyTemp.setRegNumber(jsonObject.getString("regNumber"));
            companyTemp.setCreditCode(jsonObject.getString("creditCode"));
            companyTemp.setOrgNumber(jsonObject.getString("orgNumber"));
            companyTemp.setAddr(jsonObject.getString("base"));

            resReturn.add(companyTemp);
        }
         return resReturn;
    }
}
