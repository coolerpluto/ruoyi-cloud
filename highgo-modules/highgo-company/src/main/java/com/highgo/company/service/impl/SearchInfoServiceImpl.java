package com.highgo.company.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.highgo.company.config.TianYanChaConfig;
import com.highgo.company.domain.BaseInfo;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.highgo.company.mapper.SearchInfoMapper;
import com.highgo.company.domain.SearchInfo;
import com.highgo.company.service.ISearchInfoService;
import org.springframework.web.client.RestTemplate;

/**
 * 企业简单基本信息Service业务层处理
 *
 * @date 2023-12-05
 */
@Service
public class SearchInfoServiceImpl implements ISearchInfoService
{
    @Autowired
    private SearchInfoMapper searchInfoMapper;
    @Autowired
    private TianYanChaConfig tianYanChaConfig;
    @Autowired
    private RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询企业简单基本信息
     *
     * @param searchId 企业简单基本信息主键
     * @return 企业简单基本信息
     */
    @Override
    public SearchInfo selectSearchInfoBySearchId(String searchId)
    {
        return searchInfoMapper.selectSearchInfoBySearchId(searchId);
    }

    /**
     * 查询企业简单基本信息列表
     *
     * @param searchInfo 企业简单基本信息
     * @return 企业简单基本信息
     */
    @Override
    public List<SearchInfo> selectSearchInfoList(SearchInfo searchInfo)
    {
        List<SearchInfo> list = searchInfoMapper.selectSearchInfoList(searchInfo);
        if (null != list && list.size() > 0)
        {
            log.debug("get from local");
            return list;
        }
        log.debug("get from tian—yan-cha");
        String key = searchInfo.getName();
        key = StringUtils.isBlank(key) ? searchInfo.getLegalPersonName() : key;
        key = StringUtils.isBlank(key) ? searchInfo.getBase() : key;
        if (StringUtils.isNotBlank(key))
        {
            list = getCompanyTianYanChaList(key);
        }
        return list;
    }

    private List<SearchInfo> getCompanyTianYanChaList(String key)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tianYanChaConfig.getToken());
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(tianYanChaConfig.getSearch_816(), HttpMethod.GET, request, JSONObject.class, key);
        response.getHeaders();
        response.getStatusCodeValue();
        JSONObject body = response.getBody();

        assert body != null;
        Integer errorCode = body.getInteger("error_code");
        String reason = body.getString("reason");
        if (0 != errorCode)
        {
            throw new ServiceException(body.getString("reason"), body.getInteger("error_code"));
            //return new ArrayList<>();
        }
        JSONObject result = JSONObject.parseObject(JSON.toJSONString(body.get("result")));
        JSONArray items = result.getJSONArray("items");
        List<SearchInfo> r = JSON.parseArray(items.toJSONString(), SearchInfo.class);
        if (null != r && r.size() > 0)
        {
            log.debug("save from tian—yan-cha:{}", JSON.toJSONString(r));
            searchInfoMapper.insertBatchSearchInfo(r);
        }
        return r;
    }

    /**
     * 新增企业简单基本信息
     *
     * @param searchInfo 企业简单基本信息
     * @return 结果
     */
    @Override
    public int insertSearchInfo(SearchInfo searchInfo)
    {
        searchInfo.setCreateTime(DateUtils.getNowDate());
        return searchInfoMapper.insertSearchInfo(searchInfo);
    }

    /**
     * 修改企业简单基本信息
     *
     * @param searchInfo 企业简单基本信息
     * @return 结果
     */
    @Override
    public int updateSearchInfo(SearchInfo searchInfo)
    {
        searchInfo.setUpdateTime(DateUtils.getNowDate());
        return searchInfoMapper.updateSearchInfo(searchInfo);
    }

    /**
     * 批量删除企业简单基本信息
     *
     * @param searchIds 需要删除的企业简单基本信息主键
     * @return 结果
     */
    @Override
    public int deleteSearchInfoBySearchIds(String[] searchIds)
    {
        return searchInfoMapper.deleteSearchInfoBySearchIds(searchIds);
    }

    /**
     * 删除企业简单基本信息信息
     *
     * @param searchId 企业简单基本信息主键
     * @return 结果
     */
    @Override
    public int deleteSearchInfoBySearchId(String searchId)
    {
        return searchInfoMapper.deleteSearchInfoBySearchId(searchId);
    }
}
