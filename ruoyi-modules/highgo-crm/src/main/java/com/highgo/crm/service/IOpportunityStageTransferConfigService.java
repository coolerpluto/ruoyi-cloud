package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunityStageTransferConfig;

/**
 * 商机阶段流转配置Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunityStageTransferConfigService 
{
    /**
     * 查询商机阶段流转配置
     * 
     * @param id 商机阶段流转配置主键
     * @return 商机阶段流转配置
     */
    public OpportunityStageTransferConfig selectOpportunityStageTransferConfigById(String id);

    /**
     * 查询商机阶段流转配置列表
     * 
     * @param opportunityStageTransferConfig 商机阶段流转配置
     * @return 商机阶段流转配置集合
     */
    public List<OpportunityStageTransferConfig> selectOpportunityStageTransferConfigList(OpportunityStageTransferConfig opportunityStageTransferConfig);

    /**
     * 新增商机阶段流转配置
     * 
     * @param opportunityStageTransferConfig 商机阶段流转配置
     * @return 结果
     */
    public int insertOpportunityStageTransferConfig(OpportunityStageTransferConfig opportunityStageTransferConfig);

    /**
     * 修改商机阶段流转配置
     * 
     * @param opportunityStageTransferConfig 商机阶段流转配置
     * @return 结果
     */
    public int updateOpportunityStageTransferConfig(OpportunityStageTransferConfig opportunityStageTransferConfig);

    /**
     * 批量删除商机阶段流转配置
     * 
     * @param ids 需要删除的商机阶段流转配置主键集合
     * @return 结果
     */
    public int deleteOpportunityStageTransferConfigByIds(String[] ids);

    /**
     * 删除商机阶段流转配置信息
     * 
     * @param id 商机阶段流转配置主键
     * @return 结果
     */
    public int deleteOpportunityStageTransferConfigById(String id);
}
