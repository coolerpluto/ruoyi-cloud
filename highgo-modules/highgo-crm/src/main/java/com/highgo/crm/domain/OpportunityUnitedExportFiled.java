package com.highgo.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import java.util.Date;

/**
 * 商机统一查询信息体 导出时平铺要导出的字段
 */
public class OpportunityUnitedExportFiled extends OpportunityUnited
{
    private static final long serialVersionUID = 1L;
    // 下面添加父类中没有的 或者不符合要求的
    /**
     * 创建者
     */
    @Excel(name = "创建人")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间")
    private Date updateTime;
    @Excel(name = "签约状态", readConverterExp = "Y=已签约,N=未签约")
    private String signStatus;
    @Excel(name = "签约渠道")
    private String signChannel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约日期")
    private String signDate;
    @Excel(name = "应用系统名称")
    private String applicationName;
    @Excel(name = "ISV厂商")
    private String isv;
    @Excel(name = "项目运作主体")
    private String operationalName;
    @Excel(name = "用户行业大类")
    private String userIndustryCategory;
    @Excel(name = "用户行业小类")
    private String userIndustrySubcategory;
    @Excel(name = "系统项目总预算")
    private String projectBudget;
    @Excel(name = "项目中DB总预算")
    private String projectDbBudget;
    @Excel(name = "签订合同金额")
    private String tenderPrice;
    @Excel(name = "总额")
    private String totalPrice;
    @Excel(name = "商机进展过程")
    private String advancesInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L1时间")
    private String arrivalL01Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L2时间")
    private String arrivalL02Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L3时间")
    private String arrivalL03Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L4时间")
    private String arrivalL04Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L5时间")
    private String arrivalL05Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L6时间")
    private String arrivalL06Time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    @Excel(name = "抵达L7时间")
    private String arrivalL07Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L8时间")
    private String arrivalL08Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L9时间")
    private String arrivalL09Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@Excel(name = "抵达L10时间")
    private String arrivalL10Time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抵达L11时间")
    private String arrivalL11Time;

    @Excel(name = "商机提供人")
    private String provideName;
    @Excel(name = "提供人电话")
    private String providePhone;
    @Excel(name = "提供人公司")
    private String provideCompany;

    @Override
    public String getCreateBy()
    {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime()
    {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getSignStatus()
    {
        return signStatus;
    }

    public void setSignStatus(String signStatus)
    {
        this.signStatus = signStatus;
    }

    public String getSignChannel()
    {
        return signChannel;
    }

    public void setSignChannel(String signChannel)
    {
        this.signChannel = signChannel;
    }

    public String getSignDate()
    {
        return signDate;
    }

    public void setSignDate(String signDate)
    {
        this.signDate = signDate;
    }

    public String getApplicationName()
    {
        return applicationName;
    }

    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }

    public String getIsv()
    {
        return isv;
    }

    public void setIsv(String isv)
    {
        this.isv = isv;
    }

    public String getOperationalName()
    {
        return operationalName;
    }

    public void setOperationalName(String operationalName)
    {
        this.operationalName = operationalName;
    }

    public String getUserIndustryCategory()
    {
        return userIndustryCategory;
    }

    public void setUserIndustryCategory(String userIndustryCategory)
    {
        this.userIndustryCategory = userIndustryCategory;
    }

    public String getUserIndustrySubcategory()
    {
        return userIndustrySubcategory;
    }

    public void setUserIndustrySubcategory(String userIndustrySubcategory)
    {
        this.userIndustrySubcategory = userIndustrySubcategory;
    }

    public String getProjectBudget()
    {
        return projectBudget;
    }

    public void setProjectBudget(String projectBudget)
    {
        this.projectBudget = projectBudget;
    }

    public String getProjectDbBudget()
    {
        return projectDbBudget;
    }

    public void setProjectDbBudget(String projectDbBudget)
    {
        this.projectDbBudget = projectDbBudget;
    }

    public String getTenderPrice()
    {
        return tenderPrice;
    }

    public void setTenderPrice(String tenderPrice)
    {
        this.tenderPrice = tenderPrice;
    }

    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getAdvancesInfo()
    {
        return advancesInfo;
    }

    public void setAdvancesInfo(String advancesInfo)
    {
        this.advancesInfo = advancesInfo;
    }

    public String getArrivalL01Time()
    {
        return arrivalL01Time;
    }

    public void setArrivalL01Time(String arrivalL01Time)
    {
        this.arrivalL01Time = arrivalL01Time;
    }

    public String getArrivalL02Time()
    {
        return arrivalL02Time;
    }

    public void setArrivalL02Time(String arrivalL02Time)
    {
        this.arrivalL02Time = arrivalL02Time;
    }

    public String getArrivalL03Time()
    {
        return arrivalL03Time;
    }

    public void setArrivalL03Time(String arrivalL03Time)
    {
        this.arrivalL03Time = arrivalL03Time;
    }

    public String getArrivalL04Time()
    {
        return arrivalL04Time;
    }

    public void setArrivalL04Time(String arrivalL04Time)
    {
        this.arrivalL04Time = arrivalL04Time;
    }

    public String getArrivalL05Time()
    {
        return arrivalL05Time;
    }

    public void setArrivalL05Time(String arrivalL05Time)
    {
        this.arrivalL05Time = arrivalL05Time;
    }

    public String getArrivalL06Time()
    {
        return arrivalL06Time;
    }

    public void setArrivalL06Time(String arrivalL06Time)
    {
        this.arrivalL06Time = arrivalL06Time;
    }

    public String getArrivalL07Time()
    {
        return arrivalL07Time;
    }

    public void setArrivalL07Time(String arrivalL07Time)
    {
        this.arrivalL07Time = arrivalL07Time;
    }

    public String getArrivalL08Time()
    {
        return arrivalL08Time;
    }

    public void setArrivalL08Time(String arrivalL08Time)
    {
        this.arrivalL08Time = arrivalL08Time;
    }

    public String getArrivalL09Time()
    {
        return arrivalL09Time;
    }

    public void setArrivalL09Time(String arrivalL09Time)
    {
        this.arrivalL09Time = arrivalL09Time;
    }

    public String getArrivalL10Time()
    {
        return arrivalL10Time;
    }

    public void setArrivalL10Time(String arrivalL10Time)
    {
        this.arrivalL10Time = arrivalL10Time;
    }

    public String getArrivalL11Time()
    {
        return arrivalL11Time;
    }

    public void setArrivalL11Time(String arrivalL11Time)
    {
        this.arrivalL11Time = arrivalL11Time;
    }

    public String getProvideName()
    {
        return provideName;
    }

    public void setProvideName(String provideName)
    {
        this.provideName = provideName;
    }

    public String getProvidePhone()
    {
        return providePhone;
    }

    public void setProvidePhone(String providePhone)
    {
        this.providePhone = providePhone;
    }

    public String getProvideCompany()
    {
        return provideCompany;
    }

    public void setProvideCompany(String provideCompany)
    {
        this.provideCompany = provideCompany;
    }
}
