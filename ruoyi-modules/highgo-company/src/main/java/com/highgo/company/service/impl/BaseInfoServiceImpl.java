package com.highgo.company.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.highgo.company.config.TianYanChaConfig;
import com.ruoyi.common.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.highgo.company.mapper.BaseInfoMapper;
import com.highgo.company.domain.BaseInfo;
import com.highgo.company.service.IBaseInfoService;
import org.springframework.web.client.RestTemplate;

/**
 * 企业基本信息Service业务层处理
 *
 * @date 2023-12-05
 */
@Service
public class BaseInfoServiceImpl implements IBaseInfoService
{
    @Autowired
    private BaseInfoMapper baseInfoMapper;
    @Autowired
    private TianYanChaConfig tianYanChaConfig;
    @Autowired
    private RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询企业基本信息
     *
     * @param infoId 企业基本信息主键
     * @return 企业基本信息
     */
    @Override
    public BaseInfo selectBaseInfoByInfoId(String infoId)
    {
        return baseInfoMapper.selectBaseInfoByInfoId(infoId);
    }

    /**
     * 查询企业基本信息列表
     *
     * @param baseInfo 企业基本信息
     * @return 企业基本信息
     */
    @Override
    public List<BaseInfo> selectBaseInfoList(BaseInfo baseInfo)
    {
        List<BaseInfo> list = baseInfoMapper.selectBaseInfoList(baseInfo);
        if (null != list && list.size() > 0)
        {
            log.debug("get from local");
            return list;
        }
        log.debug("get from tian—yan-cha");
        list = new ArrayList<>();
        String keyword = baseInfo.getName();
        keyword = StringUtils.isBlank(keyword) ? baseInfo.getLegalPersonName() : keyword;
        keyword = StringUtils.isBlank(keyword) ? baseInfo.getAlias() : keyword;
        BaseInfo remoteRes = getInfoByName(keyword);
        if (0 == remoteRes.getErrorCode())
        {
            list.add(remoteRes);
        }
        return list;
    }

    private BaseInfo getInfoByName(String keyword)
    {
        String url = tianYanChaConfig.getBaseInfoNormal_1116();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tianYanChaConfig.getToken());
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, request, JSONObject.class, keyword);
        response.getStatusCodeValue();
        response.getHeaders();
        JSONObject body = response.getBody();

        assert body != null;
        Integer errorCode = body.getInteger("error_code");
        String reason = body.getString("reason");
        if (0 != errorCode)
        {
            BaseInfo be = new BaseInfo();
            be.setErrorCode(errorCode);
            be.setReason(reason);
            return be;
        }
        BaseInfo b2 = body.getObject("result", BaseInfo.class);
        b2.setErrorCode(errorCode);
        b2.setReason(reason);

        b2.setCategory(b2.getIndustryAll().get("category"));
        b2.setCategoryBig(b2.getIndustryAll().get("categoryBig"));
        b2.setCategoryMiddle(b2.getIndustryAll().get("categoryMiddle"));
        b2.setCategorySmall(b2.getIndustryAll().get("categorySmall"));

        b2.setKeyword(keyword);
        log.debug("save from tian—yan-cha");
        baseInfoMapper.insertBaseInfo(b2);
        return b2;
    }

    /**
     * 新增企业基本信息
     *
     * @param baseInfo 企业基本信息
     * @return 结果
     */
    @Override
    public int insertBaseInfo(BaseInfo baseInfo)
    {
        return baseInfoMapper.insertBaseInfo(baseInfo);
    }

    /**
     * 修改企业基本信息
     *
     * @param baseInfo 企业基本信息
     * @return 结果
     */
    @Override
    public int updateBaseInfo(BaseInfo baseInfo)
    {
        return baseInfoMapper.updateBaseInfo(baseInfo);
    }

    /**
     * 批量删除企业基本信息
     *
     * @param infoIds 需要删除的企业基本信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseInfoByInfoIds(String[] infoIds)
    {
        return baseInfoMapper.deleteBaseInfoByInfoIds(infoIds);
    }

    /**
     * 删除企业基本信息信息
     *
     * @param infoId 企业基本信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseInfoByInfoId(String infoId)
    {
        return baseInfoMapper.deleteBaseInfoByInfoId(infoId);
    }
}
