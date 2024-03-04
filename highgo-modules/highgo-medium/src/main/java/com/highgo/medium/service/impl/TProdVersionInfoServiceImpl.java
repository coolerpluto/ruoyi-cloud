package com.highgo.medium.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.TProdVersionInfoMapper;
import com.highgo.medium.domain.TProdVersionInfo;
import com.highgo.medium.service.ITProdVersionInfoService;

/**
 * 产品版本信息Service业务层处理
 * 
 * @author esz
 * @date 2023-06-28
 */
@Service
public class TProdVersionInfoServiceImpl implements ITProdVersionInfoService 
{
    @Autowired
    private TProdVersionInfoMapper tProdVersionInfoMapper;

    /**
     * 查询产品版本信息
     * 
     * @param id 产品版本信息主键
     * @return 产品版本信息
     */
    @Override
    public TProdVersionInfo selectTProdVersionInfoById(Long id)
    {
        return tProdVersionInfoMapper.selectTProdVersionInfoById(id);
    }

    /**
     * 查询产品版本信息列表
     * 
     * @param tProdVersionInfo 产品版本信息
     * @return 产品版本信息
     */
    @Override
    public List<TProdVersionInfo> selectTProdVersionInfoList(TProdVersionInfo tProdVersionInfo)
    {
        return tProdVersionInfoMapper.selectTProdVersionInfoList(tProdVersionInfo);
    }

    /**
     * 新增产品版本信息
     * 
     * @param tProdVersionInfo 产品版本信息
     * @return 结果
     */
    @Override
    public int insertTProdVersionInfo(TProdVersionInfo tProdVersionInfo)
    {
        tProdVersionInfo.setCreateTime(DateUtils.getNowDate());
        tProdVersionInfo.setCreateId(SecurityUtils.getUserId());
        tProdVersionInfo.setCreateBy(SecurityUtils.getUsername());
        return tProdVersionInfoMapper.insertTProdVersionInfo(tProdVersionInfo);
    }

    /**
     * 修改产品版本信息
     * 
     * @param tProdVersionInfo 产品版本信息
     * @return 结果
     */
    @Override
    public int updateTProdVersionInfo(TProdVersionInfo tProdVersionInfo)
    {
        tProdVersionInfo.setUpdateTime(DateUtils.getNowDate());
        tProdVersionInfo.setUpdateId(SecurityUtils.getUserId());
        tProdVersionInfo.setUpdateBy(SecurityUtils.getUsername());
        return tProdVersionInfoMapper.updateTProdVersionInfo(tProdVersionInfo);
    }

    /**
     * 批量删除产品版本信息
     * 
     * @param ids 需要删除的产品版本信息主键
     * @return 结果
     */
    @Override
    public int deleteTProdVersionInfoByIds(Long[] ids)
    {
        return tProdVersionInfoMapper.deleteTProdVersionInfoByIds(ids);
    }

    /**
     * 删除产品版本信息信息
     * 
     * @param id 产品版本信息主键
     * @return 结果
     */
    @Override
    public int deleteTProdVersionInfoById(Long id)
    {
        return tProdVersionInfoMapper.deleteTProdVersionInfoById(id);
    }
}
