package com.highgo.company.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.company.mapper.BaseInfoMapper;
import com.highgo.company.domain.BaseInfo;
import com.highgo.company.service.IBaseInfoService;

/**
 * 企业基本信息Service业务层处理
 *
 *
 * @date 2023-12-05
 */
@Service
public class BaseInfoServiceImpl implements IBaseInfoService
{
    @Autowired
    private BaseInfoMapper baseInfoMapper;

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
        return baseInfoMapper.selectBaseInfoList(baseInfo);
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
