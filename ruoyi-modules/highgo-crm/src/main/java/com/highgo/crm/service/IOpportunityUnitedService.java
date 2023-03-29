package com.highgo.crm.service;

import com.highgo.crm.domain.Company;
import com.highgo.crm.domain.Contact;
import com.highgo.crm.domain.OpportunityAdvances;
import com.highgo.crm.domain.OpportunityCompetitor;
import com.highgo.crm.domain.OpportunityContactInfo;
import com.highgo.crm.domain.OpportunityCost;
import com.highgo.crm.domain.OpportunityPolicy;
import com.highgo.crm.domain.OpportunityQuotation;
import com.highgo.crm.domain.OpportunitySoftwareOperation;
import com.highgo.crm.domain.OpportunitySupport;
import com.highgo.crm.domain.OpportunityUnited;

import java.util.List;

/**
 * 商机管理Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunityUnitedService
{
    /**
     * 查询商机管理
     * 
     * @param id 商机管理主键
     * @return 商机管理
     */
    public OpportunityUnited selectOpportunityUnitedById(Long id);

    /**
     * 查询商机管理列表
     * 
     * @param opportunity 商机管理
     * @return 商机管理集合
     */
    public List<OpportunityUnited> selectOpportunityUnitedList(OpportunityUnited opportunity);

    /**
     * 新增商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int insertOpportunityUnited(OpportunityUnited opportunity);

    /**
     * 修改商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int updateOpportunityUnited(OpportunityUnited opportunity);

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的商机管理主键集合
     * @return 结果
     */
    public int deleteOpportunityUnitedByIds(Long[] ids);

    /**
     * 删除商机管理信息
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    public int deleteOpportunityUnitedById(Long id);

    OpportunityUnited queryOpportunityInfoByCode(String oppCode);

    List<OpportunitySoftwareOperation> querySoftwareOperationByCode(String oppCode);

    Company queryCompanyByOppCode(String oppCode);

    List<OpportunityPolicy> queryPolicyByOppCode(String oppCode);

    List<OpportunityAdvances> queryAdvanceByOppCode(String oppCode);

    List<OpportunityCost> queryCostByOppCode(String oppCode);

    List<OpportunityCompetitor> queryCompetitorByOppCode(String oppCode);

    List<OpportunityQuotation> queryQuotationByOppCode(String oppCode);

    List<OpportunitySupport> querySupportByOppCode(String oppCode);

    List<OpportunityContactInfo> queryContactsByOppCode(String oppCode);
}
