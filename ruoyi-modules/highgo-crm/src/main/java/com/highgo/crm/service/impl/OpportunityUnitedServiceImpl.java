package com.highgo.crm.service.impl;

import com.highgo.crm.domain.OpportunityUnited;
import com.highgo.crm.mapper.OpportunityUnitedMapper;
import com.highgo.crm.service.IOpportunityUnitedService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.datascope.annotation.CrmDataScope;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商机管理Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityUnitedServiceImpl implements IOpportunityUnitedService
{
    @Autowired
    private OpportunityUnitedMapper opportunityUnitedMapper;

    /**
     * 查询商机管理
     * 
     * @param id 商机管理主键
     * @return 商机管理
     */
    @Override
    public OpportunityUnited selectOpportunityUnitedById(Long id)
    {
        return opportunityUnitedMapper.selectOpportunityById(id);
    }

    /**
     * 查询商机管理列表
     * 
     * @param opportunity 商机管理
     * @return 商机管理
     */
    @Override
    @CrmDataScope(deptAlias = "sd", userAlias = "su")
    public List<OpportunityUnited> selectOpportunityUnitedList(OpportunityUnited opportunity)
    {
        opportunity.getParams().put("sharedId", SecurityUtils.getUserId());
        opportunity.getParams().put("properties", "tenderTime,preSignedTime,preContractAmount");
        return opportunityUnitedMapper.selectOpportunityList(opportunity);
    }

    /**
     * 新增商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    @Override
    public int insertOpportunityUnited(OpportunityUnited opportunity)
    {
        opportunity.setCreateTime(DateUtils.getNowDate());
        return opportunityUnitedMapper.insertOpportunity(opportunity);
    }

    /**
     * 修改商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    @Override
    public int updateOpportunityUnited(OpportunityUnited opportunity)
    {
        opportunity.setUpdateTime(DateUtils.getNowDate());
        return opportunityUnitedMapper.updateOpportunity(opportunity);
    }

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的商机管理主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityUnitedByIds(Long[] ids)
    {
        return opportunityUnitedMapper.deleteOpportunityByIds(ids);
    }

    /**
     * 删除商机管理信息
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityUnitedById(Long id)
    {
        return opportunityUnitedMapper.deleteOpportunityById(id);
    }
}
