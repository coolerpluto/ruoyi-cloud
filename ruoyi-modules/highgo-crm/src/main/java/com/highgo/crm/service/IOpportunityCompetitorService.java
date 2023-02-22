package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunityCompetitor;

/**
 * 竞争对手Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunityCompetitorService 
{
    /**
     * 查询竞争对手
     * 
     * @param id 竞争对手主键
     * @return 竞争对手
     */
    public OpportunityCompetitor selectOpportunityCompetitorById(Long id);

    /**
     * 查询竞争对手列表
     * 
     * @param opportunityCompetitor 竞争对手
     * @return 竞争对手集合
     */
    public List<OpportunityCompetitor> selectOpportunityCompetitorList(OpportunityCompetitor opportunityCompetitor);

    /**
     * 新增竞争对手
     * 
     * @param opportunityCompetitor 竞争对手
     * @return 结果
     */
    public int insertOpportunityCompetitor(OpportunityCompetitor opportunityCompetitor);

    /**
     * 修改竞争对手
     * 
     * @param opportunityCompetitor 竞争对手
     * @return 结果
     */
    public int updateOpportunityCompetitor(OpportunityCompetitor opportunityCompetitor);

    /**
     * 批量删除竞争对手
     * 
     * @param ids 需要删除的竞争对手主键集合
     * @return 结果
     */
    public int deleteOpportunityCompetitorByIds(Long[] ids);

    /**
     * 删除竞争对手信息
     * 
     * @param id 竞争对手主键
     * @return 结果
     */
    public int deleteOpportunityCompetitorById(Long id);
}
