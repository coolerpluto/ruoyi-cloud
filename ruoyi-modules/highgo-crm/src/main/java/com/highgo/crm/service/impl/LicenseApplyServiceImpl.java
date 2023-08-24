package com.highgo.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.highgo.crm.domain.Application;
import com.highgo.crm.domain.LicenseApply;
import com.highgo.crm.domain.LicenseSaveAndUpReq;
import com.highgo.crm.domain.OpportunityQuotation;
import com.highgo.crm.domain.OpportunitySoftwareOperation;
import com.highgo.crm.mapper.LicenseApplyMapper;
import com.highgo.crm.service.IApplicationService;
import com.highgo.crm.service.ILicenseApplyService;
import com.highgo.crm.service.IOpportunityQuotationService;
import com.highgo.crm.service.IOpportunitySoftwareOperationService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * license申请Service业务层处理
 *
 * @author eszhang
 * @date 2023-04-26
 */
@Service
public class LicenseApplyServiceImpl implements ILicenseApplyService
{
    private static final Logger log = LoggerFactory.getLogger(LicenseApplyServiceImpl.class);

    @Autowired
    private LicenseApplyMapper licenseApplyMapper;

    /**
     * 查询license申请
     *
     * @param id license申请主键
     * @return license申请
     */
    @Override
    public LicenseApply selectLicenseApplyById(Long id)
    {
        return licenseApplyMapper.selectLicenseApplyById(id);
    }

    /**
     * 查询license申请列表
     *
     * @param licenseApply license申请
     * @return license申请
     */
    @Override
    public List<LicenseApply> selectLicenseApplyList(LicenseApply licenseApply)
    {
        return licenseApplyMapper.selectLicenseApplyList(licenseApply);
    }

    /**
     * 新增license申请
     *
     * @param licenseApply license申请
     * @return 结果
     */
    @Override
    public int insertLicenseApply(LicenseApply licenseApply)
    {
        licenseApply.setCreateTime(DateUtils.getNowDate());
        SysUser current = SecurityUtils.getLoginUser().getSysUser();
        licenseApply.setCreateBy(current.getUserName());
        licenseApply.setOwnerId(current.getUserId());
        licenseApply.setDeptId(current.getDeptId());
        return licenseApplyMapper.insertLicenseApply(licenseApply);
    }

    /**
     * 修改license申请
     *
     * @param licenseApply license申请
     * @return 结果
     */
    @Override
    public int updateLicenseApply(LicenseApply licenseApply)
    {
        licenseApply.setUpdateTime(DateUtils.getNowDate());
        licenseApply.setUpdateBy(SecurityUtils.getUsername());
        return licenseApplyMapper.updateLicenseApply(licenseApply);
    }

    /**
     * 批量删除license申请
     *
     * @param ids 需要删除的license申请主键
     * @return 结果
     */
    @Override
    public int deleteLicenseApplyByIds(Long[] ids)
    {
        return licenseApplyMapper.deleteLicenseApplyByIds(ids);
    }

    /**
     * 删除license申请信息
     *
     * @param id license申请主键
     * @return 结果
     */
    @Override
    public int deleteLicenseApplyById(Long id)
    {
        return licenseApplyMapper.deleteLicenseApplyById(id);
    }

    @Override
    public LicenseApply getLicApplyByCode(String code)
    {
        return licenseApplyMapper.getLicApplyByCode(code);
    }

    @Override
    public int saveAndUpdate(LicenseSaveAndUpReq licenseReq)
    {
        log.info("saveAndUpdate req:{},", JSON.toJSONString(licenseReq));
        inOrUpApplication(licenseReq);
        inOrUpQuotationInfo(licenseReq);
        if(null!=licenseReq.getLicenseData()){
            SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
            LicenseApply licInfo = licenseReq.getLicenseData();
            if(null == licInfo.getDeptId()){
                licInfo.setDeptId(currentUser.getDeptId());
            }
            if(null == licInfo.getOwnerId()){
                licInfo.setOwnerId(currentUser.getUserId());
            }
            licInfo.setCreateBy(currentUser.getUserName());
            licInfo.setUpdateBy(currentUser.getUserName());
            licInfo.setUpdateTime(DateUtils.getNowDate());
            licInfo.setCreateTime(DateUtils.getNowDate());
            licenseApplyMapper.saveAndUpdate(licInfo);
        }
        return 1;
    }
    @Autowired
    private IOpportunitySoftwareOperationService softwareOperationService;
    @Autowired
    private IApplicationService applicationService;
    private String inOrUpApplication(LicenseSaveAndUpReq licenseReq){
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        List<OpportunitySoftwareOperation> operation_a =licenseReq.getApplicationList_a();
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
                application.setIsvName(opera.getIsv());
                application.setDataBase(opera.getDbBase());
                application.setDbAdaptStatus(opera.getCurrentAdaptedProgress());
                application.setSourceType("TEST");
                applicationService.insertApplication(application);
                opera.setApplicationId(Long.valueOf(application.getId()));
                if(StringUtils.isBlank(opera.getOpportunityCode())){
                    opera.setOpportunityCode(licenseReq.getCode());
                }
                if (StringUtils.isBlank(opera.getCreateBy())){
                    opera.setCreateBy(currentUser.getUserName());
                }
                opera.setCreateTime(DateUtils.getNowDate());
                softwareOperationService.insertOpportunitySoftwareOperation(opera);
            }
        }
        List<OpportunitySoftwareOperation> operation_m = licenseReq.getApplicationList_m();
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

                if(StringUtils.isBlank(opera.getOpportunityCode())){
                    opera.setOpportunityCode(licenseReq.getCode());
                }
                if (StringUtils.isBlank(opera.getCreateBy())){
                    opera.setCreateBy(currentUser.getUserName());
                }
                opera.setCreateTime(DateUtils.getNowDate());
                softwareOperationService.updateOpportunitySoftwareOperation(opera);
            }
        }
        List<OpportunitySoftwareOperation> operation_d = licenseReq.getApplicationList_d();
        if (CollectionUtils.isNotEmpty(operation_d))
        {
            log.info("删除商机运营信息");
            for (OpportunitySoftwareOperation opera : operation_d)
            {
                applicationService.deleteApplicationById(String.valueOf(opera.getApplicationId()));
                softwareOperationService.deleteOpportunitySoftwareOperationById(opera.getId());
            }
        }
        return "1";
    }
    @Autowired
    private IOpportunityQuotationService quotationService;

    private String inOrUpQuotationInfo(LicenseSaveAndUpReq licenseReq){
        List<OpportunityQuotation> a = licenseReq.getQuotationList_a();
        if (CollectionUtils.isNotEmpty(a)){
            for (OpportunityQuotation aa: a)
            {
                if(StringUtils.isBlank(aa.getOpportunityCode())){
                    aa.setOpportunityCode(licenseReq.getCode());
                }
                quotationService.insertOpportunityQuotation(aa);
            }
        }
        List<OpportunityQuotation> m = licenseReq.getQuotationList_m();
        if (CollectionUtils.isNotEmpty(m)){
            for (OpportunityQuotation aa: m)
            {
                if(StringUtils.isBlank(aa.getOpportunityCode())){
                    aa.setOpportunityCode(licenseReq.getCode());
                }
                quotationService.updateOpportunityQuotation(aa);
            }
        }
        List<OpportunityQuotation> d = licenseReq.getQuotationList_d();
        if (CollectionUtils.isNotEmpty(d)){
            for (OpportunityQuotation aa: d)
            {
                if(StringUtils.isBlank(aa.getOpportunityCode())){
                    aa.setOpportunityCode(licenseReq.getCode());
                }
                quotationService.deleteOpportunityQuotationById(aa.getId());
            }
        }
        return "1";
    }
}
