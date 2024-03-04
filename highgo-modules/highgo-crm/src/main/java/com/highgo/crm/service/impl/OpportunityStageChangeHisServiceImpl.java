package com.highgo.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityStageChangeHisMapper;
import com.highgo.crm.domain.OpportunityStageChangeHis;
import com.highgo.crm.service.IOpportunityStageChangeHisService;

/**
 * 商机阶段历史Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityStageChangeHisServiceImpl implements IOpportunityStageChangeHisService 
{
    @Autowired
    private OpportunityStageChangeHisMapper opportunityStageChangeHisMapper;

    /**
     * 查询商机阶段历史
     * 
     * @param id 商机阶段历史主键
     * @return 商机阶段历史
     */
    @Override
    public OpportunityStageChangeHis selectOpportunityStageChangeHisById(Long id)
    {
        return opportunityStageChangeHisMapper.selectOpportunityStageChangeHisById(id);
    }

    /**
     * 查询商机阶段历史列表
     * 
     * @param opportunityStageChangeHis 商机阶段历史
     * @return 商机阶段历史
     */
    @Override
    public List<OpportunityStageChangeHis> selectOpportunityStageChangeHisList(OpportunityStageChangeHis opportunityStageChangeHis)
    {
        return opportunityStageChangeHisMapper.selectOpportunityStageChangeHisList(opportunityStageChangeHis);
    }

    /**
     * 新增商机阶段历史
     * 
     * @param opportunityStageChangeHis 商机阶段历史
     * @return 结果
     */
    @Override
    public int insertOpportunityStageChangeHis(OpportunityStageChangeHis opportunityStageChangeHis)
    {
        return opportunityStageChangeHisMapper.insertOpportunityStageChangeHis(opportunityStageChangeHis);
    }

    /**
     * 修改商机阶段历史
     * 
     * @param opportunityStageChangeHis 商机阶段历史
     * @return 结果
     */
    @Override
    public int updateOpportunityStageChangeHis(OpportunityStageChangeHis opportunityStageChangeHis)
    {
        return opportunityStageChangeHisMapper.updateOpportunityStageChangeHis(opportunityStageChangeHis);
    }

    /**
     * 批量删除商机阶段历史
     * 
     * @param ids 需要删除的商机阶段历史主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityStageChangeHisByIds(Long[] ids)
    {
        return opportunityStageChangeHisMapper.deleteOpportunityStageChangeHisByIds(ids);
    }

    /**
     * 删除商机阶段历史信息
     * 
     * @param id 商机阶段历史主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityStageChangeHisById(Long id)
    {
        return opportunityStageChangeHisMapper.deleteOpportunityStageChangeHisById(id);
    }
}
