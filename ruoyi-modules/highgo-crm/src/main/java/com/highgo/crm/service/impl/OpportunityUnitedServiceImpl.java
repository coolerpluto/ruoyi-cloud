package com.highgo.crm.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.highgo.crm.domain.Application;
import com.highgo.crm.domain.Company;
import com.highgo.crm.domain.OppoAdvancesInfo;
import com.highgo.crm.domain.OppoBaseInfo;
import com.highgo.crm.domain.OppoBiddingInfo;
import com.highgo.crm.domain.OppoCompetitorInfo;
import com.highgo.crm.domain.OppoCostInfo;
import com.highgo.crm.domain.OppoKeyContacts;
import com.highgo.crm.domain.OppoKeyStandBy;
import com.highgo.crm.domain.OppoQuotationInfo;
import com.highgo.crm.domain.OpportunityAdvances;
import com.highgo.crm.domain.OpportunityCompetitor;
import com.highgo.crm.domain.OpportunityContactInfo;
import com.highgo.crm.domain.OpportunityCost;
import com.highgo.crm.domain.OpportunityPolicy;
import com.highgo.crm.domain.OpportunityProperty;
import com.highgo.crm.domain.OpportunityQuotation;
import com.highgo.crm.domain.OpportunitySoftwareOperation;
import com.highgo.crm.domain.OpportunityStageChangeHis;
import com.highgo.crm.domain.OpportunitySupport;
import com.highgo.crm.domain.OpportunityUnited;
import com.highgo.crm.domain.OpportunityUnitedReq;
import com.highgo.crm.mapper.OpportunityUnitedMapper;
import com.highgo.crm.service.IApplicationService;
import com.highgo.crm.service.IContactService;
import com.highgo.crm.service.IOpportunityAdvancesService;
import com.highgo.crm.service.IOpportunityCompetitorService;
import com.highgo.crm.service.IOpportunityCostService;
import com.highgo.crm.service.IOpportunityPropertyService;
import com.highgo.crm.service.IOpportunityQuotationService;
import com.highgo.crm.service.IOpportunitySoftwareOperationService;
import com.highgo.crm.service.IOpportunityStageChangeHisService;
import com.highgo.crm.service.IOpportunitySupportService;
import com.highgo.crm.service.IOpportunityUnitedService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.datascope.annotation.CrmDataScope;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 商机管理Service业务层处理
 *
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityUnitedServiceImpl implements IOpportunityUnitedService
{
    private static final Logger log = LoggerFactory.getLogger(OpportunityUnitedServiceImpl.class);

    @Autowired
    private OpportunityUnitedMapper opportunityUnitedMapper;

    /**
     * 查询商机管理
     *
     * @param id 商机管理主键
     * @return 商机管理
     */
    @Override
    public OpportunityUnited selectOpportunityUnitedById(Long id)
    {
        return opportunityUnitedMapper.selectOpportunityById(id);
    }

    /**
     * 查询商机管理列表
     *
     * @param opportunity 商机管理
     * @return 商机管理
     */
    @Override
    @CrmDataScope(deptAlias = "sd", userAlias = "su")
    public List<OpportunityUnited> selectOpportunityUnitedList(OpportunityUnited opportunity)
    {
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        if (!currentUser.isAdmin())
        {
            opportunity.getParams().put("sharedId", currentUser.getUserId());
            opportunity.getParams().put("sharedDeptId", currentUser.getDeptId());
        }
        return opportunityUnitedMapper.selectOpportunityList(opportunity);
    }

    /**
     * 新增商机管理
     *
     * @param opportunity 商机管理
     * @return 结果
     */
    @Override
    public int insertOpportunityUnited(OpportunityUnited opportunity)
    {
        opportunity.setCreateTime(DateUtils.getNowDate());
        return opportunityUnitedMapper.insertOpportunity(opportunity);
    }

    /**
     * 修改商机管理
     *
     * @param opportunity 商机管理
     * @return 结果
     */
    @Override
    public int updateOpportunityUnited(OpportunityUnited opportunity)
    {
        opportunity.setUpdateTime(DateUtils.getNowDate());
        return opportunityUnitedMapper.updateOpportunity(opportunity);
    }

    /**
     * 批量删除商机管理
     *
     * @param ids 需要删除的商机管理主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityUnitedByIds(Long[] ids)
    {
        return opportunityUnitedMapper.deleteOpportunityByIds(ids);
    }

    /**
     * 删除商机管理信息
     *
     * @param id 商机管理主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityUnitedById(Long id)
    {
        return opportunityUnitedMapper.deleteOpportunityById(id);
    }

    @Override
    public OpportunityUnited queryOpportunityInfoByCode(String oppCode)
    {
        return opportunityUnitedMapper.queryOpportunityInfoByCode(oppCode);
    }

    @Override
    public List<OpportunitySoftwareOperation> querySoftwareOperationByCode(String oppCode)
    {
        return opportunityUnitedMapper.querySoftwareOperationByCode(oppCode);
    }

    @Override
    public Company queryCompanyByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryCompanyByOppCode(oppCode);
    }

    @Override
    public List<OpportunityPolicy> queryPolicyByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryPolicyByOppCode(oppCode);
    }

    @Override
    public List<OpportunityAdvances> queryAdvanceByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryAdvanceByOppCode(oppCode);
    }

    @Override
    public List<OpportunityCost> queryCostByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryCostByOppCode(oppCode);
    }

    @Override
    public List<OpportunityCompetitor> queryCompetitorByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryCompetitorByOppCode(oppCode);
    }

    @Override
    public List<OpportunityQuotation> queryQuotationByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryQuotationByOppCode(oppCode);
    }

    @Override
    public List<OpportunitySupport> querySupportByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.querySupportByOppCode(oppCode);
    }

    @Override
    public List<OpportunityContactInfo> queryContactsByOppCode(String oppCode)
    {
        return opportunityUnitedMapper.queryContactsByOppCode(oppCode);
    }

    @Override
    public int updateOppoUnited(OpportunityUnitedReq opportunity)
    {
        log.info("updateOppoUnited:{}", JSON.toJSONString(opportunity));
        inOrUpOppoUnited(opportunity);
        return 1;
    }

    @Override
    public int insertOppoUnited(OpportunityUnitedReq opportunity)
    {
        log.info("insertOppoUnited:{}", JSON.toJSONString(opportunity));
        inOrUpOppoUnited(opportunity);
        return 1;
    }

    private Map<String, String> inOrUpOppoUnited(OpportunityUnitedReq opportunity)
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<Object> completionService = ThreadUtil.newCompletionService(executor);
        CountDownLatch end = new CountDownLatch(18);//开启各个模块线程加快处理进度
        List<Future<Object>> futureList = new ArrayList<>();
        futureList.add(completionService.submit(() -> inOrUpBaseInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpKeyContacts(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpCustInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpPolicyStandBy(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpCompetitorInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpOppDesc(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpOppInfluence(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpQuotationInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpSummaryInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpCostInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpActionReason(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpBiddingInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpWinningBidding(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpAdvancesInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpKeyStandBy(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpOppImplement(end, opportunity)));
        futureList.add(completionService.submit(() -> inOrUpSignInfo(end, opportunity)));
        futureList.add(completionService.submit(() -> recordStageHis(end, opportunity)));
        Map<String, String> res = new HashMap<>();
        try
        {
            log.info("等待各个子线程完成任务");
            end.await();
            log.info("各个子线程完成任务,汇集检查结果");
            for (Future<Object> future : futureList)
            {
                Map<String, String> threadItemRes = (Map<String, String>) future.get();
                String code = threadItemRes.get("code");
                if (StringUtils.equals(code, "0"))
                {
                    // 当前操作失败的线程
                    res.put(threadItemRes.get("model"), threadItemRes.get("msg"));
                }
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return res;
    }

    @Autowired
    private IOpportunityStageChangeHisService opportunityStageChangeHisService;

    private Map<String, String> recordStageHis(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("model", "recordStageHis");
        res.put("msg", "");
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        // 记录变更历史
        OpportunityStageChangeHis stageHis = new OpportunityStageChangeHis();
        String targetStage = opportunity.getNewStage();
        if (StringUtils.isBlank(targetStage))
        {
            targetStage = opportunity.getCurrentStage();
        }
        else
        {
            // 变更主表阶段
            OpportunityUnited oppo = new OpportunityUnited();
            oppo.setCurrentStage(targetStage);
            oppo.setCode(opportunity.getCode());
            oppo.setUpdateBy(currentUser.getUserName());
            oppo.setUpdateTime(DateUtils.getNowDate());
            opportunityUnitedMapper.updateOpportunityByCode(oppo);
        }
        stageHis.setTargetStage(targetStage);
        stageHis.setSourceStage(opportunity.getCurrentStage());
        stageHis.setChangeTime(DateUtils.getNowDate());
        stageHis.setCreateTime(DateUtils.getNowDate());
        stageHis.setCreateBy(currentUser.getUserName());
        stageHis.setOpportunityCode(getOppoCode(opportunity));
        stageHis.setOperatorId(currentUser.getUserId());
        stageHis.setOperatorName(currentUser.getNickName());
        opportunityStageChangeHisService.insertOpportunityStageChangeHis(stageHis);
        end.countDown();
        return res;
    }

    @Autowired
    private IOpportunitySoftwareOperationService softwareOperationService;
    @Autowired
    private IApplicationService applicationService;

    private Map<String, String> inOrUpBaseInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("model", "BaseInfo");
        res.put("msg", "");
        OppoBaseInfo baseInfo = opportunity.getBaseInfo();
        if (null == baseInfo)
        {
            end.countDown();
            return res;
        }
        log.info("inOrUpBaseInfo:begin:{}", Thread.currentThread().getName());
        OpportunityUnited oppInfo = baseInfo.getOppInfo();
        String name = "";
        String code = "";
        if (null != oppInfo)
        {
            name = oppInfo.getName();
            code = oppInfo.getCode();
        }
        OpportunityUnited oppo = new OpportunityUnited();
        if (StringUtils.isNotEmpty(name))
        {
            oppo.setName(name);
        }
        Map<String, OpportunityProperty> custInfo = opportunity.getCustInfo();
        if (null != custInfo)
        {
            // 更新或新增
            OpportunityProperty userCompanyIdPro = opportunity.getCustInfo().get("userCompanyId");
            if (userCompanyIdPro != null)
            {
                // 修改的用户信息
                String custId = userCompanyIdPro.getPropertyVal();
                oppo.setCustId(Long.parseLong(custId));
            }
        }
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        if (StringUtils.isNotEmpty(code))
        {
            // 新增
            oppo.setCode(getOppoCode(opportunity));
            oppo.setCreateBy(currentUser.getUserName());
            oppo.setCreateTime(DateUtils.getNowDate());
            oppo.setOwnerId(String.valueOf(currentUser.getUserId()));
            oppo.setOwnerDeptId(String.valueOf(currentUser.getDeptId()));
            oppo.setCurrentStage(opportunity.getCurrentStage());
            log.info("商机基本信息新增:{}", JSON.toJSONString(oppo));
            opportunityUnitedMapper.insertOpportunity(oppo);
        }
        else if (StringUtils.isNotEmpty(name))
        {
            // 更新了商机名称
            log.info("商机基本信息名称改变:{}", name);
            oppo.setCode(opportunity.getCode());
            oppo.setUpdateBy(currentUser.getUserName());
            oppo.setUpdateTime(DateUtils.getNowDate());
            opportunityUnitedMapper.updateOpportunityByCode(oppo);
        }
        else
        {
            log.info("商机基本信息为改变");
        }

        List<OpportunitySoftwareOperation> operation_a = baseInfo.getOperations_a();
        if (CollectionUtils.isNotEmpty(operation_a))
        {
            log.info("添加商机运营信息");
            for (OpportunitySoftwareOperation opera : operation_a)
            {
                Application application = new Application();
                application.setCategory(opera.getCategory());
                application.setCategoryL1(opera.getCategoryL1());
                application.setCategoryL2(opera.getCategoryL2());
                application.setCategoryL4(opera.getCategoryL3());
                application.setCategoryL4(opera.getCategoryL4());
                application.setName(opera.getApplicationName());
                application.setIsv(Long.valueOf(opera.getIsvId()));
                application.setDataBase(opera.getDbBase());
                applicationService.insertApplication(application);
                opera.setApplicationId(Long.valueOf(application.getId()));
                opera.setOpportunityCode(getOppoCode(opportunity));
                opera.setCreateBy(currentUser.getUserName());
                opera.setCreateTime(DateUtils.getNowDate());
                softwareOperationService.insertOpportunitySoftwareOperation(opera);
            }
        }
        List<OpportunitySoftwareOperation> operation_m = baseInfo.getOperations_m();
        if (CollectionUtils.isNotEmpty(operation_m))
        {
            log.info("修改商机运营信息");
            for (OpportunitySoftwareOperation opera : operation_m)
            {
                Application application = new Application();
                application.setCategory(opera.getCategory());
                application.setCategoryL1(opera.getCategoryL1());
                application.setCategoryL2(opera.getCategoryL2());
                application.setCategoryL4(opera.getCategoryL3());
                application.setCategoryL4(opera.getCategoryL4());
                application.setName(opera.getApplicationName());
                application.setIsv(Long.valueOf(opera.getIsvId()));
                application.setDataBase(opera.getDbBase());
                application.setId(String.valueOf(opera.getApplicationId()));
                applicationService.updateApplication(application);

                opera.setOpportunityCode(getOppoCode(opportunity));
                opera.setCreateBy(currentUser.getUserName());
                opera.setCreateTime(DateUtils.getNowDate());
                softwareOperationService.updateOpportunitySoftwareOperation(opera);
            }
        }
        List<OpportunitySoftwareOperation> operation_d = baseInfo.getOperations_d();
        if (CollectionUtils.isNotEmpty(operation_d))
        {
            log.info("删除商机运营信息");
            for (OpportunitySoftwareOperation opera : operation_d)
            {
                applicationService.deleteApplicationById(String.valueOf(opera.getApplicationId()));
                softwareOperationService.deleteOpportunitySoftwareOperationById(opera.getId());
            }
        }
        end.countDown();
        log.info("inOrUpBaseInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IContactService contactService;

    private Map<String, String> inOrUpKeyContacts(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("model", "KeyContacts");
        res.put("msg", "");

        OppoKeyContacts keyContact = opportunity.getKeyContacts();
        if (null == keyContact)
        {
            end.countDown();
            return res;
        }
        log.info("inOrUpKeyContacts:begin:{}", Thread.currentThread().getName());
        String code = getOppoCode(opportunity);
        List<OpportunityContactInfo> contact_a = keyContact.getKeyContacts_a();
        if (CollectionUtils.isNotEmpty(contact_a))
        {
            log.info("添加关键联系人");
            for (OpportunityContactInfo contact : contact_a)
            {
                contact.setOpportunityCode(code);
                contact.setSourceType("opportunity");
                contact.setSourceId(code);
                contactService.insertContact(contact);
                contact.setContactId(Long.valueOf(contact.getId()));//设置关联
                opportunityUnitedMapper.insertOpportunityContact(contact);
            }
        }
        List<OpportunityContactInfo> contact_m = keyContact.getKeyContacts_m();
        if (CollectionUtils.isNotEmpty(contact_m))
        {
            log.info("修改关键联系人");
            for (OpportunityContactInfo contact : contact_m)
            {
                contact.setOpportunityCode(code);
                contactService.updateContact(contact);
                opportunityUnitedMapper.updateOpportunityContact(contact);
            }
        }
        List<OpportunityContactInfo> contact_d = keyContact.getKeyContacts_d();
        if (CollectionUtils.isNotEmpty(contact_d))
        {
            log.info("删除关键联系人");
            for (OpportunityContactInfo contact : contact_d)
            {
                contactService.deleteContactById(contact.getId());
                opportunityUnitedMapper.deleteOpportunityContactByKeyId(contact.getKeyId());
            }
        }
        end.countDown();
        log.info("inOrUpKeyContacts:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IOpportunityPropertyService propertyService;

    private Map<String, String> inOrUpCustInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpCustInfo:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> custInfo = opportunity.getCustInfo();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), custInfo);
        res.put("model", "CustInfo");
        log.info("inOrUpCustInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpPolicyStandBy(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpPolicyStandBy:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> policy = opportunity.getPolicyStandBy();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), policy);
        res.put("model", "PolicyStandBy");
        log.info("inOrUpPolicyStandBy:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IOpportunityCompetitorService competitorService;

    private Map<String, String> inOrUpCompetitorInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("msg", "");
        res.put("model", "CompetitorInfo");
        OppoCompetitorInfo competitor = opportunity.getCompetitorInfo();
        if (null == competitor)
        {
            end.countDown();
            return res;
        }
        log.info("inOrUpCompetitorInfo:begin:{}", Thread.currentThread().getName());
        List<OpportunityCompetitor> competitor_a = competitor.getCompetitorInfo_a();
        if (CollectionUtils.isNotEmpty(competitor_a))
        {
            for (OpportunityCompetitor comp : competitor_a)
            {
                comp.setOpportunity_code(getOppoCode(opportunity));
                competitorService.insertOpportunityCompetitor(comp);
            }
        }
        List<OpportunityCompetitor> competitor_m = competitor.getCompetitorInfo_m();
        if (CollectionUtils.isNotEmpty(competitor_m))
        {
            for (OpportunityCompetitor comp : competitor_m)
            {
                competitorService.updateOpportunityCompetitor(comp);
            }
        }
        List<OpportunityCompetitor> competitor_d = competitor.getCompetitorInfo_d();
        if (CollectionUtils.isNotEmpty(competitor_d))
        {
            for (OpportunityCompetitor comp : competitor_d)
            {
                competitorService.deleteOpportunityCompetitorById(comp.getId());
            }
        }
        log.info("inOrUpCompetitorInfo:end:{}", Thread.currentThread().getName());
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpOppDesc(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpOppDesc:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> oppDesc = opportunity.getOppDesc();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), oppDesc);
        res.put("model", "OppDesc");
        log.info("inOrUpOppDesc:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpOppInfluence(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpOppInfluence:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> oppInfluence = opportunity.getOppInfluence();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), oppInfluence);
        res.put("model", "OppInfluence");
        log.info("inOrUpOppInfluence:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IOpportunityQuotationService quotationService;

    private Map<String, String> inOrUpQuotationInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {

        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("msg", "");
        res.put("model", "QuotationInfo");
        OppoQuotationInfo quotation = opportunity.getQuotationInfo();
        if (null == quotation)
        {
            end.countDown();
            return res;
        }
        log.info("inOrUpQuotationInfo:begin:{}", Thread.currentThread().getName());
        String code = getOppoCode(opportunity);
        List<OpportunityQuotation> quotation_a = quotation.getQuotationInfo_a();
        if (CollectionUtils.isNotEmpty(quotation_a))
        {
            log.info("添加报价信息");
            for (OpportunityQuotation quo : quotation_a)
            {
                quo.setOpportunityCode(code);
                quotationService.insertOpportunityQuotation(quo);
            }
        }
        List<OpportunityQuotation> quotation_m = quotation.getQuotationInfo_m();
        if (CollectionUtils.isNotEmpty(quotation_m))
        {
            log.info("修改报价信息");
            for (OpportunityQuotation quo : quotation_m)
            {
                quo.setOpportunityCode(code);
                quotationService.updateOpportunityQuotation(quo);
            }
        }
        List<OpportunityQuotation> quotation_d = quotation.getQuotationInfo_d();
        if (CollectionUtils.isNotEmpty(quotation_d))
        {
            log.info("删除报价信息");
            for (OpportunityQuotation quo : quotation_d)
            {
                quotationService.deleteOpportunityQuotationById(quo.getId());
            }
        }
        end.countDown();
        log.info("inOrUpQuotationInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpSummaryInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpSummaryInfo:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> summaryInfo = opportunity.getSummaryInfo();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), summaryInfo);
        res.put("model", "summaryInfo");
        log.info("inOrUpSummaryInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IOpportunityCostService costService;

    private Map<String, String> inOrUpCostInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("msg", "");
        res.put("model", "CostInfo");
        OppoCostInfo costInfo = opportunity.getCostInfo();
        if (null == costInfo)
        {
            end.countDown();
            return res;
        }
        log.info("inOrUpCostInfo:begin:{}", Thread.currentThread().getName());
        String code = getOppoCode(opportunity);
        List<OpportunityCost> costInfo_a = costInfo.getCostInfos_a();
        if (CollectionUtils.isNotEmpty(costInfo_a))
        {
            log.info("添加花费信息");
            for (OpportunityCost cost : costInfo_a)
            {
                cost.setOpportunityCode(code);
                costService.insertOpportunityCost(cost);
            }
        }
        List<OpportunityCost> costInfo_m = costInfo.getCostInfos_m();
        if (CollectionUtils.isNotEmpty(costInfo_m))
        {
            log.info("修改花费信息");
            for (OpportunityCost cost : costInfo_m)
            {
                if (StringUtils.isBlank(cost.getOpportunityCode()))
                {
                    cost.setOpportunityCode(code);
                }
                costService.updateOpportunityCost(cost);
            }
        }
        List<OpportunityCost> costInfo_d = costInfo.getCostInfos_d();
        if (CollectionUtils.isNotEmpty(costInfo_d))
        {
            log.info("删除花费信息");
            for (OpportunityCost cost : costInfo_d)
            {
                costService.deleteOpportunityCostById(cost.getId());
            }
        }
        end.countDown();
        log.info("inOrUpCostInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpActionReason(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpActionReason:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> actionReason = opportunity.getActionReason();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), actionReason);
        res.put("model", "Reason");
        log.info("inOrUpActionReason:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpBiddingInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpBiddingInfo:begin:{}", Thread.currentThread().getName());
        OppoBiddingInfo biddingInfo = opportunity.getBiddingInfo();

        Map<String, OpportunityProperty> biddingInfoProperties = new HashMap<>();
        biddingInfoProperties.put("knowExpertList", biddingInfo.getKnowExpertList());
        biddingInfoProperties.put("supportByExpert", biddingInfo.getSupportByExpert());
        Map<String, String> res = inOrUpProperty(null, getOppoCode(opportunity), biddingInfoProperties);

        List<OpportunitySoftwareOperation> biddingInfo_m = biddingInfo.getBiddingInfo_m();
        // 无增 无删  只关注修改
        for (OpportunitySoftwareOperation opera : biddingInfo_m)
        {
            softwareOperationService.updateOpportunitySoftwareOperation(opera);
        }

        res.put("model", "biddingInfo");
        log.info("inOrUpBiddingInfo:end:{}", Thread.currentThread().getName());
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpWinningBidding(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpWinningBidding:begin:{}", Thread.currentThread().getName());

        Map<String, OpportunityProperty> winningBidding = opportunity.getWinningBidding();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), winningBidding);
        res.put("model", "winningBidding");
        log.info("inOrUpWinningBidding:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IOpportunityAdvancesService advancesService;

    private Map<String, String> inOrUpAdvancesInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpAdvancesInfo:begin:{}", Thread.currentThread().getName());
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("msg", "");
        res.put("model", "AdvancesInfo");

        OppoAdvancesInfo advancesInfo = opportunity.getAdvancesInfo();
        if (null == advancesInfo)
        {
            end.countDown();
            return res;
        }
        String code = getOppoCode(opportunity);
        List<OpportunityAdvances> advancesInfo_a = advancesInfo.getAdvancesInfo_a();
        if (CollectionUtils.isNotEmpty(advancesInfo_a))
        {
            // 新增进展
            for (OpportunityAdvances adv : advancesInfo_a)
            {
                adv.setOpportunityCode(code);
                advancesService.insertOpportunityAdvances(adv);
            }
        }

        List<OpportunityAdvances> advancesInfo_m = advancesInfo.getAdvancesInfo_m();
        if (CollectionUtils.isNotEmpty(advancesInfo_m))
        {
            // 编辑进展
            for (OpportunityAdvances adv : advancesInfo_m)
            {
                adv.setOpportunityCode(code);
                advancesService.updateOpportunityAdvances(adv);
            }
        }
        List<OpportunityAdvances> advancesInfo_d = advancesInfo.getAdvancesInfo_d();
        if (CollectionUtils.isNotEmpty(advancesInfo_d))
        {
            // 删除进展
            for (OpportunityAdvances adv : advancesInfo_d)
            {
                advancesService.deleteOpportunityAdvancesById(adv.getId());
            }
        }
        end.countDown();
        log.info("inOrUpAdvancesInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    @Autowired
    private IOpportunitySupportService supportService;

    private Map<String, String> inOrUpKeyStandBy(CountDownLatch end, OpportunityUnitedReq opportunity)
    {

        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("msg", "");
        res.put("model", "KeyStandBy");
        OppoKeyStandBy keyStandBy = opportunity.getKeyStandBy();
        if (null == keyStandBy)
        {
            end.countDown();
            return res;
        }
        log.info("inOrUpKeyStandBy:begin:{}", Thread.currentThread().getName());
        String code = getOppoCode(opportunity);
        List<OpportunitySupport> keyStandBy_a = keyStandBy.getKeyStandBy_a();
        if (CollectionUtils.isNotEmpty(keyStandBy_a))
        {
            log.info("新增内部支持人");
            for (OpportunitySupport adv : keyStandBy_a)
            {
                adv.setOpportunityCode(code);
                supportService.insertOpportunitySupport(adv);
            }
        }

        List<OpportunitySupport> keyStandBy_m = keyStandBy.getKeyStandBy_m();
        if (CollectionUtils.isNotEmpty(keyStandBy_m))
        {
            log.info("修改内部支持人");
            for (OpportunitySupport adv : keyStandBy_m)
            {
                adv.setOpportunityCode(code);
                supportService.updateOpportunitySupport(adv);
            }
        }
        List<OpportunitySupport> keyStandBy_d = keyStandBy.getKeyStandBy_d();
        if (CollectionUtils.isNotEmpty(keyStandBy_d))
        {
            log.info("删除内部支持人");
            for (OpportunitySupport adv : keyStandBy_d)
            {
                supportService.deleteOpportunitySupportById(adv.getId());
            }
        }
        end.countDown();
        log.info("inOrUpKeyStandBy:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpOppImplement(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpOppImplement:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> oppImplement = opportunity.getOppImplement();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), oppImplement);
        res.put("model", "oppImplement");
        log.info("inOrUpOppImplement:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpSignInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        log.info("inOrUpSignInfo:begin:{}", Thread.currentThread().getName());
        Map<String, OpportunityProperty> signInfo = opportunity.getSignInfo();
        Map<String, String> res = inOrUpProperty(end, getOppoCode(opportunity), signInfo);
        res.put("model", "signInfo");
        log.info("inOrUpSignInfo:end:{}", Thread.currentThread().getName());
        return res;
    }

    private Map<String, String> inOrUpProperty(CountDownLatch end, String code, Map<String, OpportunityProperty> propertyMap)
    {
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        if (null == propertyMap)
        {
            if (null != end)
            {
                end.countDown();
            }
            return res;
        }
        for (String key : propertyMap.keySet())
        {
            try
            {
                OpportunityProperty property = propertyMap.get(key);
                property.setPropertyKey(key);
                String status = property.getStatus();
                if (StringUtils.isEmpty(status))
                {
                    status = "1";
                    property.setStatus(status);
                }
                property.setOpportunityCode(code);
                if (null == property.getId())
                {
                    propertyService.insertOpportunityProperty(property);
                }
                else
                {
                    propertyService.updateOpportunityProperty(property);
                }
            }
            catch (Exception e)
            {
                res.put("msg", res.get("msg") + key + " 操作失败,");
            }
        }
        if (StringUtils.isNotEmpty(res.get("msg")))
        {
            res.put("code", "0");
        }
        if (null != end)
        {
            end.countDown();
        }
        return res;
    }

    private String getOppoCode(OpportunityUnitedReq opportunity)
    {
        String code = opportunity.getCode();
        if (StringUtils.equals("0", code))
        {
            code = opportunity.getBaseInfo().getOppInfo().getCode();
        }
        return code;
    }
}
