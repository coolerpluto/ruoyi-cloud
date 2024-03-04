package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunitySupportMapper;
import com.highgo.crm.domain.OpportunitySupport;
import com.highgo.crm.service.IOpportunitySupportService;

/**
 * 商机支持人Service业务层处理
 *
 * @author eszhang
 * @date 2023-03-14
 */
@Service
public class OpportunitySupportServiceImpl implements IOpportunitySupportService
{
    @Autowired
    private OpportunitySupportMapper opportunitySupportMapper;

    /**
     * 查询商机支持人
     *
     * @param id 商机支持人主键
     * @return 商机支持人
     */
    @Override
    public OpportunitySupport selectOpportunitySupportById(Long id)
    {
        return opportunitySupportMapper.selectOpportunitySupportById(id);
    }

    /**
     * 查询商机支持人列表
     *
     * @param opportunitySupport 商机支持人
     * @return 商机支持人
     */
    @Override
    public List<OpportunitySupport> selectOpportunitySupportList(OpportunitySupport opportunitySupport)
    {
        return opportunitySupportMapper.selectOpportunitySupportList(opportunitySupport);
    }

    /**
     * 新增商机支持人
     *
     * @param opportunitySupport 商机支持人
     * @return 结果
     */
    @Override
    public int insertOpportunitySupport(OpportunitySupport opportunitySupport)
    {
        opportunitySupport.setCreateTime(DateUtils.getNowDate());
        return opportunitySupportMapper.insertOpportunitySupport(opportunitySupport);
    }

    /**
     * 修改商机支持人
     *
     * @param opportunitySupport 商机支持人
     * @return 结果
     */
    @Override
    public int updateOpportunitySupport(OpportunitySupport opportunitySupport)
    {
        opportunitySupport.setUpdateTime(DateUtils.getNowDate());
        return opportunitySupportMapper.updateOpportunitySupport(opportunitySupport);
    }

    /**
     * 批量删除商机支持人
     *
     * @param ids 需要删除的商机支持人主键
     * @return 结果
     */
    @Override
    public int deleteOpportunitySupportByIds(Long[] ids)
    {
        return opportunitySupportMapper.deleteOpportunitySupportByIds(ids);
    }

    /**
     * 删除商机支持人信息
     *
     * @param id 商机支持人主键
     * @return 结果
     */
    @Override
    public int deleteOpportunitySupportById(Long id)
    {
        return opportunitySupportMapper.deleteOpportunitySupportById(id);
    }
}
