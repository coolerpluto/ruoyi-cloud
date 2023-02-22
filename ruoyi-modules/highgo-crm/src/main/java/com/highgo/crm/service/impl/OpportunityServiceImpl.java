package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityMapper;
import com.highgo.crm.domain.Opportunity;
import com.highgo.crm.service.IOpportunityService;

/**
 * 商机管理Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityServiceImpl implements IOpportunityService 
{
    @Autowired
    private OpportunityMapper opportunityMapper;

    /**
     * 查询商机管理
     * 
     * @param id 商机管理主键
     * @return 商机管理
     */
    @Override
    public Opportunity selectOpportunityById(Long id)
    {
        return opportunityMapper.selectOpportunityById(id);
    }

    /**
     * 查询商机管理列表
     * 
     * @param opportunity 商机管理
     * @return 商机管理
     */
    @Override
    public List<Opportunity> selectOpportunityList(Opportunity opportunity)
    {
        return opportunityMapper.selectOpportunityList(opportunity);
    }

    /**
     * 新增商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    @Override
    public int insertOpportunity(Opportunity opportunity)
    {
        opportunity.setCreateTime(DateUtils.getNowDate());
        return opportunityMapper.insertOpportunity(opportunity);
    }

    /**
     * 修改商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    @Override
    public int updateOpportunity(Opportunity opportunity)
    {
        opportunity.setUpdateTime(DateUtils.getNowDate());
        return opportunityMapper.updateOpportunity(opportunity);
    }

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的商机管理主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityByIds(Long[] ids)
    {
        return opportunityMapper.deleteOpportunityByIds(ids);
    }

    /**
     * 删除商机管理信息
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityById(Long id)
    {
        return opportunityMapper.deleteOpportunityById(id);
    }
}
