package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunityQuotation;

/**
 * 商机产品报价Service接口
 *
 * @author eszhang
 * @date 2023-03-14
 */
public interface IOpportunityQuotationService
{
    /**
     * 查询商机产品报价
     *
     * @param id 商机产品报价主键
     * @return 商机产品报价
     */
    public OpportunityQuotation selectOpportunityQuotationById(Long id);

    /**
     * 查询商机产品报价列表
     *
     * @param opportunityQuotation 商机产品报价
     * @return 商机产品报价集合
     */
    public List<OpportunityQuotation> selectOpportunityQuotationList(OpportunityQuotation opportunityQuotation);

    /**
     * 新增商机产品报价
     *
     * @param opportunityQuotation 商机产品报价
     * @return 结果
     */
    public int insertOpportunityQuotation(OpportunityQuotation opportunityQuotation);

    /**
     * 修改商机产品报价
     *
     * @param opportunityQuotation 商机产品报价
     * @return 结果
     */
    public int updateOpportunityQuotation(OpportunityQuotation opportunityQuotation);

    /**
     * 批量删除商机产品报价
     *
     * @param ids 需要删除的商机产品报价主键集合
     * @return 结果
     */
    public int deleteOpportunityQuotationByIds(Long[] ids);

    /**
     * 删除商机产品报价信息
     *
     * @param id 商机产品报价主键
     * @return 结果
     */
    public int deleteOpportunityQuotationById(Long id);
}
