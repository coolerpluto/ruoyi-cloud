package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.OpportunityPolicy;

/**
 * 商机支持政策Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityPolicyMapper 
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
     * 删除商机支持政策
     * 
     * @param id 商机支持政策主键
     * @return 结果
     */
    public int deleteOpportunityPolicyById(Long id);

    /**
     * 批量删除商机支持政策
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityPolicyByIds(Long[] ids);
}
