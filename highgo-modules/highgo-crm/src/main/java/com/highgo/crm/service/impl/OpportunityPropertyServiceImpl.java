package com.highgo.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityPropertyMapper;
import com.highgo.crm.domain.OpportunityProperty;
import com.highgo.crm.service.IOpportunityPropertyService;

/**
 * 商机属性Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityPropertyServiceImpl implements IOpportunityPropertyService 
{
    @Autowired
    private OpportunityPropertyMapper opportunityPropertyMapper;

    /**
     * 查询商机属性
     * 
     * @param id 商机属性主键
     * @return 商机属性
     */
    @Override
    public OpportunityProperty selectOpportunityPropertyById(Long id)
    {
        return opportunityPropertyMapper.selectOpportunityPropertyById(id);
    }

    /**
     * 查询商机属性列表
     * 
     * @param opportunityProperty 商机属性
     * @return 商机属性
     */
    @Override
    public List<OpportunityProperty> selectOpportunityPropertyList(OpportunityProperty opportunityProperty)
    {
        return opportunityPropertyMapper.selectOpportunityPropertyList(opportunityProperty);
    }

    /**
     * 新增商机属性
     * 
     * @param opportunityProperty 商机属性
     * @return 结果
     */
    @Override
    public int insertOpportunityProperty(OpportunityProperty opportunityProperty)
    {
        return opportunityPropertyMapper.insertOpportunityProperty(opportunityProperty);
    }

    /**
     * 修改商机属性
     * 
     * @param opportunityProperty 商机属性
     * @return 结果
     */
    @Override
    public int updateOpportunityProperty(OpportunityProperty opportunityProperty)
    {
        return opportunityPropertyMapper.updateOpportunityProperty(opportunityProperty);
    }

    /**
     * 批量删除商机属性
     * 
     * @param ids 需要删除的商机属性主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityPropertyByIds(Long[] ids)
    {
        return opportunityPropertyMapper.deleteOpportunityPropertyByIds(ids);
    }

    /**
     * 删除商机属性信息
     * 
     * @param id 商机属性主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityPropertyById(Long id)
    {
        return opportunityPropertyMapper.deleteOpportunityPropertyById(id);
    }
}
