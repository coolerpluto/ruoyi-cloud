package com.highgo.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityPolicyMapper;
import com.highgo.crm.domain.OpportunityPolicy;
import com.highgo.crm.service.IOpportunityPolicyService;

/**
 * 商机支持政策Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityPolicyServiceImpl implements IOpportunityPolicyService 
{
    @Autowired
    private OpportunityPolicyMapper opportunityPolicyMapper;

    /**
     * 查询商机支持政策
     * 
     * @param id 商机支持政策主键
     * @return 商机支持政策
     */
    @Override
    public OpportunityPolicy selectOpportunityPolicyById(Long id)
    {
        return opportunityPolicyMapper.selectOpportunityPolicyById(id);
    }

    /**
     * 查询商机支持政策列表
     * 
     * @param opportunityPolicy 商机支持政策
     * @return 商机支持政策
     */
    @Override
    public List<OpportunityPolicy> selectOpportunityPolicyList(OpportunityPolicy opportunityPolicy)
    {
        return opportunityPolicyMapper.selectOpportunityPolicyList(opportunityPolicy);
    }

    /**
     * 新增商机支持政策
     * 
     * @param opportunityPolicy 商机支持政策
     * @return 结果
     */
    @Override
    public int insertOpportunityPolicy(OpportunityPolicy opportunityPolicy)
    {
        return opportunityPolicyMapper.insertOpportunityPolicy(opportunityPolicy);
    }

    /**
     * 修改商机支持政策
     * 
     * @param opportunityPolicy 商机支持政策
     * @return 结果
     */
    @Override
    public int updateOpportunityPolicy(OpportunityPolicy opportunityPolicy)
    {
        return opportunityPolicyMapper.updateOpportunityPolicy(opportunityPolicy);
    }

    /**
     * 批量删除商机支持政策
     * 
     * @param ids 需要删除的商机支持政策主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityPolicyByIds(Long[] ids)
    {
        return opportunityPolicyMapper.deleteOpportunityPolicyByIds(ids);
    }

    /**
     * 删除商机支持政策信息
     * 
     * @param id 商机支持政策主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityPolicyById(Long id)
    {
        return opportunityPolicyMapper.deleteOpportunityPolicyById(id);
    }
}
