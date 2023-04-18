package com.highgo.crm.controller;

import com.alibaba.fastjson.JSON;
import com.highgo.crm.domain.Company;
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
import com.highgo.crm.service.IOpportunityPropertyService;
import com.highgo.crm.service.IOpportunityStageChangeHisService;
import com.highgo.crm.service.IOpportunityStageTransferConfigService;
import com.highgo.crm.service.IOpportunityUnitedService;
import com.highgo.crm.service.impl.OpportunityUnitedServiceImpl;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.Logical;
import com.ruoyi.common.security.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商机管理Controller
 *
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppUnited")
public class OpportunityUnitedController extends BaseController
{
    @Autowired
    private IOpportunityUnitedService opportunityService;

    /**
     * 查询商机统一管理列表
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @GetMapping("/list")
    public TableDataInfo list(OpportunityUnited opportunity)
    {
        startPage();
        List<OpportunityUnited> list = opportunityService.selectOpportunityUnitedList(opportunity);
        return getDataTable(list);
    }

    @Autowired
    private IOpportunityStageTransferConfigService opportunityStageTransferConfigService;
    @Autowired
    private IOpportunityStageChangeHisService opportunityStageChangeHisService;
    @GetMapping("/stageConfigAndInfo")
    public AjaxResult stageConfigAndInfo(OpportunityUnited opportunity)
    {
        Map<String, Object> res = new HashMap<>();
        Map<Integer, String> activeConfig = opportunityStageTransferConfigService.stageTransferConfig();
        res.put("activeConfig", activeConfig);
        Map<String, Object> stageInfo = new HashMap<>();
        if (opportunity.getCode()!=null && !StringUtils.equals(opportunity.getCode(),"0")){
            OpportunityStageChangeHis reqHis = new OpportunityStageChangeHis();
            reqHis.setOpportunityCode(opportunity.getCode());
            List<OpportunityStageChangeHis> stageHisList = opportunityStageChangeHisService.selectOpportunityStageChangeHisList(reqHis);
            stageInfo.put("currentStage",stageHisList.get(0).getTargetStage());
            stageInfo.put("stageHisList",stageHisList);
            Set<String> temp = stageHisList.stream().map(OpportunityStageChangeHis::getTargetStage).collect(Collectors.toSet());
            stageInfo.put("stageHis",temp.toArray());
        }else {
            stageInfo.put("currentStage","1");
            stageInfo.put("stageHis",new String[]{"1"});
        }
        res.put("stageInfo", stageInfo);
        return success(res);
    }
    @Autowired
    private IOpportunityPropertyService opportunityPropertyService;

    private static final Logger log = LoggerFactory.getLogger(OpportunityUnitedServiceImpl.class);

    @PostMapping("/getPropertiesMap")
    public AjaxResult getPropertiesMap(@RequestBody OpportunityUnited opportunity){
        Map<String, Object> res = new HashMap<>();
        OpportunityProperty opportunityProperty = new OpportunityProperty();
        opportunityProperty.setOpportunityCode(opportunity.getCode());
        opportunityProperty.setParams(opportunity.getParams());
        List<String> propertyKeysReq = (List<String>) opportunity.getParams().get("propertyKeys");
        for (String propertyKey:propertyKeysReq){
            res.put(propertyKey,new OpportunityProperty());
        }
        log.info("getPropertiesMap req:{},", JSON.toJSONString(opportunityProperty));
        List<OpportunityProperty> properties = opportunityPropertyService.selectOpportunityPropertyList(opportunityProperty);
        log.info("getPropertiesMap res:{},", JSON.toJSONString(properties));
        for (OpportunityProperty property:properties)
        {
            if (!StringUtils.equals(property.getStatus(),"1")){
                continue;
            }
            res.put(property.getPropertyKey(),property);
        }
        return success(res);
    }

    @GetMapping("/getOppBaseInfo")
    public AjaxResult getOppBaseInfo(OpportunityUnited opportunity){
        Map<String, Object> res = new HashMap<>();
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success(res);
        }

        OpportunityUnited info = opportunityService.queryOpportunityInfoByCode(code);
        res.put("oppInfo",info);
        List<OpportunitySoftwareOperation> operations =opportunityService.querySoftwareOperationByCode(code);
        res.put("operations",operations);
        return success(res);
    }

    @GetMapping("/getOppUserInfo")
    public AjaxResult getOppUserInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        Company userInfo = opportunityService.queryCompanyByOppCode(code);
        return success(userInfo);
    }
    @GetMapping("/getOppPolicyInfo")
    public AjaxResult getOppPolicyInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunityPolicy> policyList = opportunityService.queryPolicyByOppCode(code);
        return success(policyList);
    }
    @GetMapping("/getOppAdvancesInfo")
    public AjaxResult getOppAdvancesInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunityAdvances> advancesList = opportunityService.queryAdvanceByOppCode(code);
        return success(advancesList);
    }
    @GetMapping("/getOppCostInfo")
    public AjaxResult getOppCostInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunityCost> costList = opportunityService.queryCostByOppCode(code);
        return success(costList);
    }
    @GetMapping("/getOppCompetitorInfo")
    public AjaxResult getOppCompetitorInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunityCompetitor> competitorList = opportunityService.queryCompetitorByOppCode(code);
        return success(competitorList);
    }
    @GetMapping("/getOppQuotationInfo")
    public AjaxResult getOppQuotationInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunityQuotation> quotationList = opportunityService.queryQuotationByOppCode(code);
        return success(quotationList);
    }
    @GetMapping("/getOppSupportInfo")
    public AjaxResult getOppSupportInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunitySupport> supportList = opportunityService.querySupportByOppCode(code);
        return success(supportList);
    }
    @GetMapping("/getOppContactsInfo")
    public AjaxResult getOppContactsInfo(OpportunityUnited opportunity){
        String code = opportunity.getCode();
        if(StringUtils.isBlank(code)){
            return success();
        }

        List<OpportunityContactInfo> contactList = opportunityService.queryContactsByOppCode(code);
        return success(contactList);
    }
    /**
     * 导出商机统一管理列表
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityUnited opportunity)
    {
        List<OpportunityUnited> list = opportunityService.selectOpportunityUnitedList(opportunity);
        ExcelUtil<OpportunityUnited> util = new ExcelUtil<OpportunityUnited>(OpportunityUnited.class);
        util.exportExcel(response, list, "商机管理数据");
    }

    /**
     * 获取商机统一管理详细信息
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityService.selectOpportunityUnitedById(id));
    }

    /**
     * 新增商机统一管理
     */
    //@RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityUnitedReq opportunity)
    {
        return toAjax(opportunityService.insertOppoUnited(opportunity));
    }

    /**
     * 修改商机统一管理
     */
    //@RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityUnitedReq opportunity)
    {
        return toAjax(opportunityService.updateOppoUnited(opportunity));
    }

    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.TRANSFER)
    @PutMapping("/transfer")
    public AjaxResult transfer(@RequestBody OpportunityUnited opportunity)
    {
        return toAjax(opportunityService.updateOpportunityUnited(opportunity));
    }

    /**
     * 删除商机统一管理
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityService.deleteOpportunityUnitedByIds(ids));
    }
}
