package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.ShareConfigMapper;
import com.highgo.crm.domain.ShareConfig;
import com.highgo.crm.service.IShareConfigService;

/**
 * 共享规则配置Service业务层处理
 * 
 * @author eszhang
 * @date 2023-01-17
 */
@Service
public class ShareConfigServiceImpl implements IShareConfigService 
{
    @Autowired
    private ShareConfigMapper shareConfigMapper;

    /**
     * 查询共享规则配置
     * 
     * @param id 共享规则配置主键
     * @return 共享规则配置
     */
    @Override
    public ShareConfig selectShareConfigById(String id)
    {
        return shareConfigMapper.selectShareConfigById(id);
    }

    /**
     * 查询共享规则配置列表
     * 
     * @param shareConfig 共享规则配置
     * @return 共享规则配置
     */
    @Override
    public List<ShareConfig> selectShareConfigList(ShareConfig shareConfig)
    {
        return shareConfigMapper.selectShareConfigList(shareConfig);
    }

    /**
     * 新增共享规则配置
     * 
     * @param shareConfig 共享规则配置
     * @return 结果
     */
    @Override
    public int insertShareConfig(ShareConfig shareConfig)
    {
        shareConfig.setCreateTime(DateUtils.getNowDate());
        return shareConfigMapper.insertShareConfig(shareConfig);
    }

    /**
     * 修改共享规则配置
     * 
     * @param shareConfig 共享规则配置
     * @return 结果
     */
    @Override
    public int updateShareConfig(ShareConfig shareConfig)
    {
        shareConfig.setUpdateTime(DateUtils.getNowDate());
        return shareConfigMapper.updateShareConfig(shareConfig);
    }

    /**
     * 批量删除共享规则配置
     * 
     * @param ids 需要删除的共享规则配置主键
     * @return 结果
     */
    @Override
    public int deleteShareConfigByIds(String[] ids)
    {
        return shareConfigMapper.deleteShareConfigByIds(ids);
    }

    /**
     * 删除共享规则配置信息
     * 
     * @param id 共享规则配置主键
     * @return 结果
     */
    @Override
    public int deleteShareConfigById(String id)
    {
        return shareConfigMapper.deleteShareConfigById(id);
    }
}
