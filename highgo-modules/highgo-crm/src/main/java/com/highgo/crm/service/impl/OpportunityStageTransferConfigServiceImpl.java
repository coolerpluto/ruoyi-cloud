package com.highgo.crm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityStageTransferConfigMapper;
import com.highgo.crm.domain.OpportunityStageTransferConfig;
import com.highgo.crm.service.IOpportunityStageTransferConfigService;

/**
 * 商机阶段流转配置Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityStageTransferConfigServiceImpl implements IOpportunityStageTransferConfigService 
{
    @Autowired
    private OpportunityStageTransferConfigMapper opportunityStageTransferConfigMapper;

    /**
     * 查询商机阶段流转配置
     * 
     * @param id 商机阶段流转配置主键
     * @return 商机阶段流转配置
     */
    @Override
    public OpportunityStageTransferConfig selectOpportunityStageTransferConfigById(String id)
    {
        return opportunityStageTransferConfigMapper.selectOpportunityStageTransferConfigById(id);
    }

    /**
     * 查询商机阶段流转配置列表
     * 
     * @param opportunityStageTransferConfig 商机阶段流转配置
     * @return 商机阶段流转配置
     */
    @Override
    public List<OpportunityStageTransferConfig> selectOpportunityStageTransferConfigList(OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        return opportunityStageTransferConfigMapper.selectOpportunityStageTransferConfigList(opportunityStageTransferConfig);
    }

    /**
     * 新增商机阶段流转配置
     * 
     * @param opportunityStageTransferConfig 商机阶段流转配置
     * @return 结果
     */
    @Override
    public int insertOpportunityStageTransferConfig(OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        return opportunityStageTransferConfigMapper.insertOpportunityStageTransferConfig(opportunityStageTransferConfig);
    }

    /**
     * 修改商机阶段流转配置
     * 
     * @param opportunityStageTransferConfig 商机阶段流转配置
     * @return 结果
     */
    @Override
    public int updateOpportunityStageTransferConfig(OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        return opportunityStageTransferConfigMapper.updateOpportunityStageTransferConfig(opportunityStageTransferConfig);
    }

    /**
     * 批量删除商机阶段流转配置
     * 
     * @param ids 需要删除的商机阶段流转配置主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityStageTransferConfigByIds(String[] ids)
    {
        return opportunityStageTransferConfigMapper.deleteOpportunityStageTransferConfigByIds(ids);
    }

    /**
     * 删除商机阶段流转配置信息
     * 
     * @param id 商机阶段流转配置主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityStageTransferConfigById(String id)
    {
        return opportunityStageTransferConfigMapper.deleteOpportunityStageTransferConfigById(id);
    }

    @Override
    public Map<Integer, String> stageTransferConfig()
    {
        return opportunityStageTransferConfigMapper.stageTransferConfig();
    }
}
