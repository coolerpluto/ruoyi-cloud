package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.OpportunityAdvances;

/**
 * 商机进展Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityAdvancesMapper 
{
    /**
     * 查询商机进展
     * 
     * @param id 商机进展主键
     * @return 商机进展
     */
    public OpportunityAdvances selectOpportunityAdvancesById(Long id);

    /**
     * 查询商机进展列表
     * 
     * @param opportunityAdvances 商机进展
     * @return 商机进展集合
     */
    public List<OpportunityAdvances> selectOpportunityAdvancesList(OpportunityAdvances opportunityAdvances);

    /**
     * 新增商机进展
     * 
     * @param opportunityAdvances 商机进展
     * @return 结果
     */
    public int insertOpportunityAdvances(OpportunityAdvances opportunityAdvances);

    /**
     * 修改商机进展
     * 
     * @param opportunityAdvances 商机进展
     * @return 结果
     */
    public int updateOpportunityAdvances(OpportunityAdvances opportunityAdvances);

    /**
     * 删除商机进展
     * 
     * @param id 商机进展主键
     * @return 结果
     */
    public int deleteOpportunityAdvancesById(Long id);

    /**
     * 批量删除商机进展
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityAdvancesByIds(Long[] ids);
}
