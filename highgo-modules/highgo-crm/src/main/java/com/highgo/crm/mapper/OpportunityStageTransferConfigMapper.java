package com.highgo.crm.mapper;

import java.util.List;
import java.util.Map;

import com.highgo.crm.domain.OpportunityStageTransferConfig;
import org.apache.ibatis.annotations.MapKey;

/**
 * 商机阶段流转配置Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityStageTransferConfigMapper 
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
     * 删除商机阶段流转配置
     * 
     * @param id 商机阶段流转配置主键
     * @return 结果
     */
    public int deleteOpportunityStageTransferConfigById(String id);

    /**
     * 批量删除商机阶段流转配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityStageTransferConfigByIds(String[] ids);

    @MapKey("sourceStage")
    Map<Integer, String> stageTransferConfig();
}
