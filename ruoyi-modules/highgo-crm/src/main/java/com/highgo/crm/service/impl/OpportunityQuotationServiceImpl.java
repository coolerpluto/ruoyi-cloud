package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityQuotationMapper;
import com.highgo.crm.domain.OpportunityQuotation;
import com.highgo.crm.service.IOpportunityQuotationService;

/**
 * 商机产品报价Service业务层处理
 *
 * @author eszhang
 * @date 2023-03-14
 */
@Service
public class OpportunityQuotationServiceImpl implements IOpportunityQuotationService
{
    @Autowired
    private OpportunityQuotationMapper opportunityQuotationMapper;

    /**
     * 查询商机产品报价
     *
     * @param id 商机产品报价主键
     * @return 商机产品报价
     */
    @Override
    public OpportunityQuotation selectOpportunityQuotationById(Long id)
    {
        return opportunityQuotationMapper.selectOpportunityQuotationById(id);
    }

    /**
     * 查询商机产品报价列表
     *
     * @param opportunityQuotation 商机产品报价
     * @return 商机产品报价
     */
    @Override
    public List<OpportunityQuotation> selectOpportunityQuotationList(OpportunityQuotation opportunityQuotation)
    {
        return opportunityQuotationMapper.selectOpportunityQuotationList(opportunityQuotation);
    }

    /**
     * 新增商机产品报价
     *
     * @param opportunityQuotation 商机产品报价
     * @return 结果
     */
    @Override
    public int insertOpportunityQuotation(OpportunityQuotation opportunityQuotation)
    {
        opportunityQuotation.setCreateTime(DateUtils.getNowDate());
        opportunityQuotation.setCreateBy(SecurityUtils.getUsername());
        return opportunityQuotationMapper.insertOpportunityQuotation(opportunityQuotation);
    }

    /**
     * 修改商机产品报价
     *
     * @param opportunityQuotation 商机产品报价
     * @return 结果
     */
    @Override
    public int updateOpportunityQuotation(OpportunityQuotation opportunityQuotation)
    {
        opportunityQuotation.setUpdateTime(DateUtils.getNowDate());
        opportunityQuotation.setUpdateBy(SecurityUtils.getUsername());
        return opportunityQuotationMapper.updateOpportunityQuotation(opportunityQuotation);
    }

    /**
     * 批量删除商机产品报价
     *
     * @param ids 需要删除的商机产品报价主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityQuotationByIds(Long[] ids)
    {
        return opportunityQuotationMapper.deleteOpportunityQuotationByIds(ids);
    }

    /**
     * 删除商机产品报价信息
     *
     * @param id 商机产品报价主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityQuotationById(Long id)
    {
        return opportunityQuotationMapper.deleteOpportunityQuotationById(id);
    }
}
