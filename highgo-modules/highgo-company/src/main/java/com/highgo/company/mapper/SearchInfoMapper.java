package com.highgo.company.mapper;

import java.util.List;
import com.highgo.company.domain.SearchInfo;

/**
 * 企业简单基本信息Mapper接口
 *
 *
 * @date 2023-12-05
 */
public interface SearchInfoMapper
{
    /**
     * 查询企业简单基本信息
     *
     * @param searchId 企业简单基本信息主键
     * @return 企业简单基本信息
     */
    public SearchInfo selectSearchInfoBySearchId(String searchId);

    /**
     * 查询企业简单基本信息列表
     *
     * @param searchInfo 企业简单基本信息
     * @return 企业简单基本信息集合
     */
    public List<SearchInfo> selectSearchInfoList(SearchInfo searchInfo);

    /**
     * 新增企业简单基本信息
     *
     * @param searchInfo 企业简单基本信息
     * @return 结果
     */
    public int insertSearchInfo(SearchInfo searchInfo);

    /**
     * 修改企业简单基本信息
     *
     * @param searchInfo 企业简单基本信息
     * @return 结果
     */
    public int updateSearchInfo(SearchInfo searchInfo);

    /**
     * 删除企业简单基本信息
     *
     * @param searchId 企业简单基本信息主键
     * @return 结果
     */
    public int deleteSearchInfoBySearchId(String searchId);

    /**
     * 批量删除企业简单基本信息
     *
     * @param searchIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSearchInfoBySearchIds(String[] searchIds);

    int insertBatchSearchInfo(List<SearchInfo> list);
}
