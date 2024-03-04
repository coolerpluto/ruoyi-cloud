package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.ShareConfig;

/**
 * 共享规则配置Mapper接口
 * 
 * @author eszhang
 * @date 2023-01-17
 */
public interface ShareConfigMapper 
{
    /**
     * 查询共享规则配置
     * 
     * @param id 共享规则配置主键
     * @return 共享规则配置
     */
    public ShareConfig selectShareConfigById(String id);

    /**
     * 查询共享规则配置列表
     * 
     * @param shareConfig 共享规则配置
     * @return 共享规则配置集合
     */
    public List<ShareConfig> selectShareConfigList(ShareConfig shareConfig);

    /**
     * 新增共享规则配置
     * 
     * @param shareConfig 共享规则配置
     * @return 结果
     */
    public int insertShareConfig(ShareConfig shareConfig);

    /**
     * 修改共享规则配置
     * 
     * @param shareConfig 共享规则配置
     * @return 结果
     */
    public int updateShareConfig(ShareConfig shareConfig);

    /**
     * 删除共享规则配置
     * 
     * @param id 共享规则配置主键
     * @return 结果
     */
    public int deleteShareConfigById(String id);

    /**
     * 批量删除共享规则配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShareConfigByIds(String[] ids);
}
