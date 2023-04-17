package com.highgo.crm.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.highgo.crm.domain.Company;
import com.highgo.crm.domain.OpportunityAdvances;
import com.highgo.crm.domain.OpportunityCompetitor;
import com.highgo.crm.domain.OpportunityContactInfo;
import com.highgo.crm.domain.OpportunityCost;
import com.highgo.crm.domain.OpportunityPolicy;
import com.highgo.crm.domain.OpportunityQuotation;
import com.highgo.crm.domain.OpportunitySoftwareOperation;
import com.highgo.crm.domain.OpportunitySupport;
import com.highgo.crm.domain.OpportunityUnited;
import com.highgo.crm.domain.OpportunityUnitedReq;
import com.highgo.crm.mapper.OpportunityUnitedMapper;
import com.highgo.crm.service.IOpportunityUnitedService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.SpringUtils;
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
import java.util.Random;
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
    public int insertOppoUnited(OpportunityUnitedReq opportunity){
        log.info("insertOppoUnited:{}", JSON.toJSONString(opportunity));
        inOrUpOppoUnited(opportunity);
        return 1;
    }

    private Map<String,String> inOrUpOppoUnited(OpportunityUnitedReq opportunity)
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<Object> completionService = ThreadUtil.newCompletionService(executor);
        CountDownLatch end = new CountDownLatch(17);
        List<Future<Object>> futureList = new ArrayList<>();
        futureList.add(completionService.submit(() -> inOrUpBaseInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpKeyContacts(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpCustInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpPolicyStandBy(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpCompetitorInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpOppDesc(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpOppInfluence(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpQuotationInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpSummaryInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpCostInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpActionReason(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpBiddingInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpWinningBidding(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpAdvancesInfo(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpKeyStandBy(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpOppImplement(end,opportunity)));
        futureList.add(completionService.submit(() -> inOrUpSignInfo(end,opportunity)));
        Map<String,String> res = new HashMap<>();
        try
        {
            log.info("等待各个子线程完成任务");
            end.await();
            log.info("各个子线程完成任务,汇集检查结果");
            for (Future<Object> future: futureList)
            {
                Map<String,String> threadItemRes = (Map<String, String>) future.get();
                String code = threadItemRes.get("code");
                if(StringUtils.equals(code,"0")){
                    // 当前操作失败的线程
                    res.put(threadItemRes.get("model"),threadItemRes.get("msg"));
                }
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return res;
    }

    private Map<String, String> inOrUpBaseInfo(CountDownLatch end, OpportunityUnitedReq opportunity){
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("model","baseInfo");
        res.put("msg","");

        end.countDown();
        return res;
    }
    private Map<String, String> inOrUpKeyContacts(CountDownLatch end, OpportunityUnitedReq opportunity){
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpCustInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpPolicyStandBy(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpCompetitorInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpOppDesc(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpOppInfluence(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpQuotationInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpSummaryInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpCostInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpActionReason(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpBiddingInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpWinningBidding(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpAdvancesInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpKeyStandBy(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpOppImplement(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String, String> inOrUpSignInfo(CountDownLatch end, OpportunityUnitedReq opportunity)
    {
        Map<String,String> res = new HashMap<>();
        end.countDown();
        return res;
    }

    private Map<String,String> inOrUpProperty(CountDownLatch end, OpportunityUnitedReq opportunity){return null;}

    private Map execBusiness(CountDownLatch end) throws InterruptedException
    {
        Map<String,String> res = new HashMap<>();
        Thread.sleep(new Random().nextInt(10) * 100);
        System.out.println(Thread.currentThread().getName() + ":check complete");
        res.put(Thread.currentThread().getName(),"123123");
        end.countDown();
        return res;
    }

}
