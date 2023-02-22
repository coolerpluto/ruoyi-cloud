package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityAdvancesMapper;
import com.highgo.crm.domain.OpportunityAdvances;
import com.highgo.crm.service.IOpportunityAdvancesService;

/**
 * 商机进展Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityAdvancesServiceImpl implements IOpportunityAdvancesService 
{
    @Autowired
    private OpportunityAdvancesMapper opportunityAdvancesMapper;

    /**
     * 查询商机进展
     * 
     * @param id 商机进展主键
     * @return 商机进展
     */
    @Override
    public OpportunityAdvances selectOpportunityAdvancesById(Long id)
    {
        return opportunityAdvancesMapper.selectOpportunityAdvancesById(id);
    }

    /**
     * 查询商机进展列表
     * 
     * @param opportunityAdvances 商机进展
     * @return 商机进展
     */
    @Override
    public List<OpportunityAdvances> selectOpportunityAdvancesList(OpportunityAdvances opportunityAdvances)
    {
        return opportunityAdvancesMapper.selectOpportunityAdvancesList(opportunityAdvances);
    }

    /**
     * 新增商机进展
     * 
     * @param opportunityAdvances 商机进展
     * @return 结果
     */
    @Override
    public int insertOpportunityAdvances(OpportunityAdvances opportunityAdvances)
    {
        opportunityAdvances.setCreateTime(DateUtils.getNowDate());
        return opportunityAdvancesMapper.insertOpportunityAdvances(opportunityAdvances);
    }

    /**
     * 修改商机进展
     * 
     * @param opportunityAdvances 商机进展
     * @return 结果
     */
    @Override
    public int updateOpportunityAdvances(OpportunityAdvances opportunityAdvances)
    {
        return opportunityAdvancesMapper.updateOpportunityAdvances(opportunityAdvances);
    }

    /**
     * 批量删除商机进展
     * 
     * @param ids 需要删除的商机进展主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityAdvancesByIds(Long[] ids)
    {
        return opportunityAdvancesMapper.deleteOpportunityAdvancesByIds(ids);
    }

    /**
     * 删除商机进展信息
     * 
     * @param id 商机进展主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityAdvancesById(Long id)
    {
        return opportunityAdvancesMapper.deleteOpportunityAdvancesById(id);
    }
}
