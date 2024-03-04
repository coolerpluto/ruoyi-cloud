package com.highgo.crm.mapper;

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
import com.highgo.crm.domain.OpportunityTransferReq;
import com.highgo.crm.domain.OpportunityUnited;
import com.highgo.crm.domain.OpportunityUnitedExportFiled;

import java.util.List;

/**
 * 商机管理Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityUnitedMapper
{
    /**
     * 查询商机管理
     * 
     * @param id 商机管理主键
     * @return 商机管理
     */
    public OpportunityUnited selectOpportunityById(Long id);

    /**
     * 查询商机管理列表
     * 
     * @param opportunity 商机管理
     * @return 商机管理集合
     */
    public List<OpportunityUnited> selectOpportunityList(OpportunityUnited opportunity);

    /**
     * 新增商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int insertOpportunity(OpportunityUnited opportunity);

    /**
     * 修改商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int updateOpportunity(OpportunityUnited opportunity);

    /**
     * 删除商机管理
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    public int deleteOpportunityById(Long id);

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityByIds(Long[] ids);

    OpportunityUnited queryOpportunityInfoByCode(String code);

    List<OpportunitySoftwareOperation> querySoftwareOperationByCode(String code);

    Company queryCompanyByOppCode(String oppCode);

    List<OpportunityPolicy> queryPolicyByOppCode(String oppCode);

    List<OpportunityAdvances> queryAdvanceByOppCode(String oppCode);

    List<OpportunityCost> queryCostByOppCode(String oppCode);

    List<OpportunityCompetitor> queryCompetitorByOppCode(String oppCode);

    List<OpportunityQuotation> queryQuotationByOppCode(String oppCode);

    List<OpportunitySupport> querySupportByOppCode(String oppCode);

    List<OpportunityContactInfo> queryContactsByOppCode(String oppCode);

    int insertOpportunityContact(OpportunityContactInfo contact);

    int updateOpportunityContact(OpportunityContactInfo contact);

    int deleteOpportunityContactByKeyId(Long keyId);

    int updateOpportunityByCode(OpportunityUnited oppo);

    int transferByCodes(OpportunityTransferReq opportunity);

    List<OpportunityUnitedExportFiled> selectOppoList4Export(OpportunityUnited opportunity);

    int deleteOppoShareByCode(OpportunityUnited opp);

    int addOppoShareByCode(OpportunityUnited opp);
}
