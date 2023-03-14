package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunitySupport;

/**
 * 商机支持人Service接口
 *
 * @author eszhang
 * @date 2023-03-14
 */
public interface IOpportunitySupportService
{
    /**
     * 查询商机支持人
     *
     * @param id 商机支持人主键
     * @return 商机支持人
     */
    public OpportunitySupport selectOpportunitySupportById(Long id);

    /**
     * 查询商机支持人列表
     *
     * @param opportunitySupport 商机支持人
     * @return 商机支持人集合
     */
    public List<OpportunitySupport> selectOpportunitySupportList(OpportunitySupport opportunitySupport);

    /**
     * 新增商机支持人
     *
     * @param opportunitySupport 商机支持人
     * @return 结果
     */
    public int insertOpportunitySupport(OpportunitySupport opportunitySupport);

    /**
     * 修改商机支持人
     *
     * @param opportunitySupport 商机支持人
     * @return 结果
     */
    public int updateOpportunitySupport(OpportunitySupport opportunitySupport);

    /**
     * 批量删除商机支持人
     *
     * @param ids 需要删除的商机支持人主键集合
     * @return 结果
     */
    public int deleteOpportunitySupportByIds(Long[] ids);

    /**
     * 删除商机支持人信息
     *
     * @param id 商机支持人主键
     * @return 结果
     */
    public int deleteOpportunitySupportById(Long id);
}
