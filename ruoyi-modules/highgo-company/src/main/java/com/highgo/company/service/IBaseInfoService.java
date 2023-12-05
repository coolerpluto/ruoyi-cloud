package com.highgo.company.service;

import java.util.List;
import com.highgo.company.domain.BaseInfo;

/**
 * 企业基本信息Service接口
 *
 *
 * @date 2023-12-05
 */
public interface IBaseInfoService
{
    /**
     * 查询企业基本信息
     *
     * @param infoId 企业基本信息主键
     * @return 企业基本信息
     */
    public BaseInfo selectBaseInfoByInfoId(String infoId);

    /**
     * 查询企业基本信息列表
     *
     * @param baseInfo 企业基本信息
     * @return 企业基本信息集合
     */
    public List<BaseInfo> selectBaseInfoList(BaseInfo baseInfo);

    /**
     * 新增企业基本信息
     *
     * @param baseInfo 企业基本信息
     * @return 结果
     */
    public int insertBaseInfo(BaseInfo baseInfo);

    /**
     * 修改企业基本信息
     *
     * @param baseInfo 企业基本信息
     * @return 结果
     */
    public int updateBaseInfo(BaseInfo baseInfo);

    /**
     * 批量删除企业基本信息
     *
     * @param infoIds 需要删除的企业基本信息主键集合
     * @return 结果
     */
    public int deleteBaseInfoByInfoIds(String[] infoIds);

    /**
     * 删除企业基本信息信息
     *
     * @param infoId 企业基本信息主键
     * @return 结果
     */
    public int deleteBaseInfoByInfoId(String infoId);
}
