package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.OpportunityStageChangeHis;

/**
 * 商机阶段历史Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityStageChangeHisMapper 
{
    /**
     * 查询商机阶段历史
     * 
     * @param id 商机阶段历史主键
     * @return 商机阶段历史
     */
    public OpportunityStageChangeHis selectOpportunityStageChangeHisById(Long id);

    /**
     * 查询商机阶段历史列表
     * 
     * @param opportunityStageChangeHis 商机阶段历史
     * @return 商机阶段历史集合
     */
    public List<OpportunityStageChangeHis> selectOpportunityStageChangeHisList(OpportunityStageChangeHis opportunityStageChangeHis);

    /**
     * 新增商机阶段历史
     * 
     * @param opportunityStageChangeHis 商机阶段历史
     * @return 结果
     */
    public int insertOpportunityStageChangeHis(OpportunityStageChangeHis opportunityStageChangeHis);

    /**
     * 修改商机阶段历史
     * 
     * @param opportunityStageChangeHis 商机阶段历史
     * @return 结果
     */
    public int updateOpportunityStageChangeHis(OpportunityStageChangeHis opportunityStageChangeHis);

    /**
     * 删除商机阶段历史
     * 
     * @param id 商机阶段历史主键
     * @return 结果
     */
    public int deleteOpportunityStageChangeHisById(Long id);

    /**
     * 批量删除商机阶段历史
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityStageChangeHisByIds(Long[] ids);
}
