package com.highgo.company.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.company.mapper.SearchInfoMapper;
import com.highgo.company.domain.SearchInfo;
import com.highgo.company.service.ISearchInfoService;

/**
 * 企业简单基本信息Service业务层处理
 *
 *
 * @date 2023-12-05
 */
@Service
public class SearchInfoServiceImpl implements ISearchInfoService
{
    @Autowired
    private SearchInfoMapper searchInfoMapper;

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
        return searchInfoMapper.selectSearchInfoList(searchInfo);
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
