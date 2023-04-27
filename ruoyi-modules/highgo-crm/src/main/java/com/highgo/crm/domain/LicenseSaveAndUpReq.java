package com.highgo.crm.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * license申请 主对象 t_license_apply
 *
 * @author eszhang
 * @date 2023-04-26
 */
public class LicenseSaveAndUpReq extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private String code;
    private LicenseApply licenseData;
    private List<OpportunitySoftwareOperation> applicationList_a;
    private List<OpportunitySoftwareOperation> applicationList_m;
    private List<OpportunitySoftwareOperation> applicationList_d;
    private List<OpportunityQuotation> quotationList_a;
    private List<OpportunityQuotation> quotationList_m;
    private List<OpportunityQuotation> quotationList_d;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public LicenseApply getLicenseData()
    {
        return licenseData;
    }

    public void setLicenseData(LicenseApply licenseData)
    {
        this.licenseData = licenseData;
    }

    public List<OpportunitySoftwareOperation> getApplicationList_a()
    {
        return applicationList_a;
    }

    public void setApplicationList_a(List<OpportunitySoftwareOperation> applicationList_a)
    {
        this.applicationList_a = applicationList_a;
    }

    public List<OpportunitySoftwareOperation> getApplicationList_m()
    {
        return applicationList_m;
    }

    public void setApplicationList_m(List<OpportunitySoftwareOperation> applicationList_m)
    {
        this.applicationList_m = applicationList_m;
    }

    public List<OpportunitySoftwareOperation> getApplicationList_d()
    {
        return applicationList_d;
    }

    public void setApplicationList_d(List<OpportunitySoftwareOperation> applicationList_d)
    {
        this.applicationList_d = applicationList_d;
    }

    public List<OpportunityQuotation> getQuotationList_a()
    {
        return quotationList_a;
    }

    public void setQuotationList_a(List<OpportunityQuotation> quotationList_a)
    {
        this.quotationList_a = quotationList_a;
    }

    public List<OpportunityQuotation> getQuotationList_m()
    {
        return quotationList_m;
    }

    public void setQuotationList_m(List<OpportunityQuotation> quotationList_m)
    {
        this.quotationList_m = quotationList_m;
    }

    public List<OpportunityQuotation> getQuotationList_d()
    {
        return quotationList_d;
    }

    public void setQuotationList_d(List<OpportunityQuotation> quotationList_d)
    {
        this.quotationList_d = quotationList_d;
    }
}
