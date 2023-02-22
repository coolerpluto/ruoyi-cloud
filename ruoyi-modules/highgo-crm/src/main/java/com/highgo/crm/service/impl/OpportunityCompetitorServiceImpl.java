package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityCompetitorMapper;
import com.highgo.crm.domain.OpportunityCompetitor;
import com.highgo.crm.service.IOpportunityCompetitorService;

/**
 * 竞争对手Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityCompetitorServiceImpl implements IOpportunityCompetitorService 
{
    @Autowired
    private OpportunityCompetitorMapper opportunityCompetitorMapper;

    /**
     * 查询竞争对手
     * 
     * @param id 竞争对手主键
     * @return 竞争对手
     */
    @Override
    public OpportunityCompetitor selectOpportunityCompetitorById(Long id)
    {
        return opportunityCompetitorMapper.selectOpportunityCompetitorById(id);
    }

    /**
     * 查询竞争对手列表
     * 
     * @param opportunityCompetitor 竞争对手
     * @return 竞争对手
     */
    @Override
    public List<OpportunityCompetitor> selectOpportunityCompetitorList(OpportunityCompetitor opportunityCompetitor)
    {
        return opportunityCompetitorMapper.selectOpportunityCompetitorList(opportunityCompetitor);
    }

    /**
     * 新增竞争对手
     * 
     * @param opportunityCompetitor 竞争对手
     * @return 结果
     */
    @Override
    public int insertOpportunityCompetitor(OpportunityCompetitor opportunityCompetitor)
    {
        opportunityCompetitor.setCreateTime(DateUtils.getNowDate());
        return opportunityCompetitorMapper.insertOpportunityCompetitor(opportunityCompetitor);
    }

    /**
     * 修改竞争对手
     * 
     * @param opportunityCompetitor 竞争对手
     * @return 结果
     */
    @Override
    public int updateOpportunityCompetitor(OpportunityCompetitor opportunityCompetitor)
    {
        opportunityCompetitor.setUpdateTime(DateUtils.getNowDate());
        return opportunityCompetitorMapper.updateOpportunityCompetitor(opportunityCompetitor);
    }

    /**
     * 批量删除竞争对手
     * 
     * @param ids 需要删除的竞争对手主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityCompetitorByIds(Long[] ids)
    {
        return opportunityCompetitorMapper.deleteOpportunityCompetitorByIds(ids);
    }

    /**
     * 删除竞争对手信息
     * 
     * @param id 竞争对手主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityCompetitorById(Long id)
    {
        return opportunityCompetitorMapper.deleteOpportunityCompetitorById(id);
    }
}
