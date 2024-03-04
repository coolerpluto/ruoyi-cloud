package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunityPolicy;

/**
 * 商机支持政策Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunityPolicyService 
{
    /**
     * 查询商机支持政策
     * 
     * @param id 商机支持政策主键
     * @return 商机支持政策
     */
    public OpportunityPolicy selectOpportunityPolicyById(Long id);

    /**
     * 查询商机支持政策列表
     * 
     * @param opportunityPolicy 商机支持政策
     * @return 商机支持政策集合
     */
    public List<OpportunityPolicy> selectOpportunityPolicyList(OpportunityPolicy opportunityPolicy);

    /**
     * 新增商机支持政策
     * 
     * @param opportunityPolicy 商机支持政策
     * @return 结果
     */
    public int insertOpportunityPolicy(OpportunityPolicy opportunityPolicy);

    /**
     * 修改商机支持政策
     * 
     * @param opportunityPolicy 商机支持政策
     * @return 结果
     */
    public int updateOpportunityPolicy(OpportunityPolicy opportunityPolicy);

    /**
     * 批量删除商机支持政策
     * 
     * @param ids 需要删除的商机支持政策主键集合
     * @return 结果
     */
    public int deleteOpportunityPolicyByIds(Long[] ids);

    /**
     * 删除商机支持政策信息
     * 
     * @param id 商机支持政策主键
     * @return 结果
     */
    public int deleteOpportunityPolicyById(Long id);
}
