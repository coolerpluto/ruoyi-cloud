package com.highgo.company.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 企业基本信息对象 base_info
 *
 *
 * @date 2023-12-05
 */
public class BaseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String infoId;

    /** 状态码 */
    @Excel(name = "状态码")
    private String errorCode;

    /** 经营开始时间 */
    @Excel(name = "经营开始时间")
    private String reason;

    /** 企业id */
    private String id;

    /** 法人类型，1 人 2 公司 */
    @Excel(name = "法人类型，1 人 2 公司")
    private String type;

    /** 法人 */
    @Excel(name = "法人")
    private String legalPersonName;

    /** 企业名 */
    @Excel(name = "企业名")
    private String name;

    /** 英文名 */
    @Excel(name = "英文名")
    private String property3;

    /** 简称 */
    @Excel(name = "简称")
    private String alias;

    /** 曾用名 */
    @Excel(name = "曾用名")
    private String historyNames;

    /** 是否是小微企业 0不是 1是 */
    @Excel(name = "是否是小微企业 0不是 1是")
    private String isMicroEnt;

    /** 企业状态 */
    @Excel(name = "企业状态")
    private String regStatus;

    /** 成立日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date estiblishTime;

    /** 经营开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "经营开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fromTime;

    /** 经营结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "经营结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date toTime;

    /** 人员规模 */
    @Excel(name = "人员规模")
    private String staffNumRange;

    /** 企业类型 */
    @Excel(name = "企业类型")
    private String companyOrgType;

    /** 股票类型 */
    @Excel(name = "股票类型")
    private String bondType;

    /** 股票号 */
    @Excel(name = "股票号")
    private String bondNum;

    /** 股票名 */
    @Excel(name = "股票名")
    private String bondName;

    /** 股票曾用名 */
    @Excel(name = "股票曾用名")
    private String usedBondName;

    /** 注册号 */
    @Excel(name = "注册号")
    private String regNumber;

    /** 注册资本 */
    @Excel(name = "注册资本")
    private String regCapital;

    /** 注册资本币种 人民币 美元 欧元 等 */
    @Excel(name = "注册资本币种 人民币 美元 欧元 等")
    private String regCapitalCurrency;

    /** 实收注册资金 */
    @Excel(name = "实收注册资金")
    private String actualCapital;

    /** 实收注册资本币种 人民币 美元 欧元 等 */
    @Excel(name = "实收注册资本币种 人民币 美元 欧元 等")
    private String actualCapitalCurrency;

    /** 纳税人识别号 */
    @Excel(name = "纳税人识别号")
    private String taxNumber;

    /** 组成形式，1-个人经营、2-家庭经营 */
    @Excel(name = "组成形式，1-个人经营、2-家庭经营")
    private String compForm;

    /** 核准时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "核准时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvedTime;

    /** 登记机关 */
    @Excel(name = "登记机关")
    private String regInstitute;

    /** 组织机构代码 */
    @Excel(name = "组织机构代码")
    private String orgNumber;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String creditCode;

    /** 参保人数 */
    @Excel(name = "参保人数")
    private String socialStaffNum;

    /** 企业标签 */
    @Excel(name = "企业标签")
    private String tags;

    /** 经营范围 */
    @Excel(name = "经营范围")
    private String businessScope;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTimes;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String regLocation;

    /** 省份简称 */
    @Excel(name = "省份简称")
    private String base;

    /** 市 */
    @Excel(name = "市")
    private String city;

    /** 区 */
    @Excel(name = "区")
    private String district;

    /** 行业 */
    @Excel(name = "行业")
    private String industry;

    /** 国民经济行业分类门类 */
    @Excel(name = "国民经济行业分类门类")
    private String category;

    /** 国民经济行业分类大类 */
    @Excel(name = "国民经济行业分类大类")
    private String categoryBig;

    /** 国民经济行业分类中类 */
    @Excel(name = "国民经济行业分类中类")
    private String categoryMiddle;

    /** 国民经济行业分类小类 */
    @Excel(name = "国民经济行业分类小类")
    private String categorySmall;

    /** 吊销日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "吊销日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date revokeDate;

    /** 吊销原因 */
    @Excel(name = "吊销原因")
    private String revokeReason;

    /** 注销日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注销日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelDate;

    /** 注销原因 */
    @Excel(name = "注销原因")
    private String cancelReason;

    /** 企业评分 */
    @Excel(name = "企业评分")
    private String percentileScore;

    /** 查询关键字 */
    @Excel(name = "查询关键字")
    private String keyword;

    public void setInfoId(String infoId)
    {
        this.infoId = infoId;
    }

    public String getInfoId()
    {
        return infoId;
    }
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setLegalPersonName(String legalPersonName)
    {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonName()
    {
        return legalPersonName;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setProperty3(String property3)
    {
        this.property3 = property3;
    }

    public String getProperty3()
    {
        return property3;
    }
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getAlias()
    {
        return alias;
    }
    public void setHistoryNames(String historyNames)
    {
        this.historyNames = historyNames;
    }

    public String getHistoryNames()
    {
        return historyNames;
    }
    public void setIsMicroEnt(String isMicroEnt)
    {
        this.isMicroEnt = isMicroEnt;
    }

    public String getIsMicroEnt()
    {
        return isMicroEnt;
    }
    public void setRegStatus(String regStatus)
    {
        this.regStatus = regStatus;
    }

    public String getRegStatus()
    {
        return regStatus;
    }
    public void setEstiblishTime(Date estiblishTime)
    {
        this.estiblishTime = estiblishTime;
    }

    public Date getEstiblishTime()
    {
        return estiblishTime;
    }
    public void setFromTime(Date fromTime)
    {
        this.fromTime = fromTime;
    }

    public Date getFromTime()
    {
        return fromTime;
    }
    public void setToTime(Date toTime)
    {
        this.toTime = toTime;
    }

    public Date getToTime()
    {
        return toTime;
    }
    public void setStaffNumRange(String staffNumRange)
    {
        this.staffNumRange = staffNumRange;
    }

    public String getStaffNumRange()
    {
        return staffNumRange;
    }
    public void setCompanyOrgType(String companyOrgType)
    {
        this.companyOrgType = companyOrgType;
    }

    public String getCompanyOrgType()
    {
        return companyOrgType;
    }
    public void setBondType(String bondType)
    {
        this.bondType = bondType;
    }

    public String getBondType()
    {
        return bondType;
    }
    public void setBondNum(String bondNum)
    {
        this.bondNum = bondNum;
    }

    public String getBondNum()
    {
        return bondNum;
    }
    public void setBondName(String bondName)
    {
        this.bondName = bondName;
    }

    public String getBondName()
    {
        return bondName;
    }
    public void setUsedBondName(String usedBondName)
    {
        this.usedBondName = usedBondName;
    }

    public String getUsedBondName()
    {
        return usedBondName;
    }
    public void setRegNumber(String regNumber)
    {
        this.regNumber = regNumber;
    }

    public String getRegNumber()
    {
        return regNumber;
    }
    public void setRegCapital(String regCapital)
    {
        this.regCapital = regCapital;
    }

    public String getRegCapital()
    {
        return regCapital;
    }
    public void setRegCapitalCurrency(String regCapitalCurrency)
    {
        this.regCapitalCurrency = regCapitalCurrency;
    }

    public String getRegCapitalCurrency()
    {
        return regCapitalCurrency;
    }
    public void setActualCapital(String actualCapital)
    {
        this.actualCapital = actualCapital;
    }

    public String getActualCapital()
    {
        return actualCapital;
    }
    public void setActualCapitalCurrency(String actualCapitalCurrency)
    {
        this.actualCapitalCurrency = actualCapitalCurrency;
    }

    public String getActualCapitalCurrency()
    {
        return actualCapitalCurrency;
    }
    public void setTaxNumber(String taxNumber)
    {
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber()
    {
        return taxNumber;
    }
    public void setCompForm(String compForm)
    {
        this.compForm = compForm;
    }

    public String getCompForm()
    {
        return compForm;
    }
    public void setApprovedTime(Date approvedTime)
    {
        this.approvedTime = approvedTime;
    }

    public Date getApprovedTime()
    {
        return approvedTime;
    }
    public void setRegInstitute(String regInstitute)
    {
        this.regInstitute = regInstitute;
    }

    public String getRegInstitute()
    {
        return regInstitute;
    }
    public void setOrgNumber(String orgNumber)
    {
        this.orgNumber = orgNumber;
    }

    public String getOrgNumber()
    {
        return orgNumber;
    }
    public void setCreditCode(String creditCode)
    {
        this.creditCode = creditCode;
    }

    public String getCreditCode()
    {
        return creditCode;
    }
    public void setSocialStaffNum(String socialStaffNum)
    {
        this.socialStaffNum = socialStaffNum;
    }

    public String getSocialStaffNum()
    {
        return socialStaffNum;
    }
    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getTags()
    {
        return tags;
    }
    public void setBusinessScope(String businessScope)
    {
        this.businessScope = businessScope;
    }

    public String getBusinessScope()
    {
        return businessScope;
    }
    public void setUpdateTimes(Date updateTimes)
    {
        this.updateTimes = updateTimes;
    }

    public Date getUpdateTimes()
    {
        return updateTimes;
    }
    public void setRegLocation(String regLocation)
    {
        this.regLocation = regLocation;
    }

    public String getRegLocation()
    {
        return regLocation;
    }
    public void setBase(String base)
    {
        this.base = base;
    }

    public String getBase()
    {
        return base;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }
    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getDistrict()
    {
        return district;
    }
    public void setIndustry(String industry)
    {
        this.industry = industry;
    }

    public String getIndustry()
    {
        return industry;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setCategoryBig(String categoryBig)
    {
        this.categoryBig = categoryBig;
    }

    public String getCategoryBig()
    {
        return categoryBig;
    }
    public void setCategoryMiddle(String categoryMiddle)
    {
        this.categoryMiddle = categoryMiddle;
    }

    public String getCategoryMiddle()
    {
        return categoryMiddle;
    }
    public void setCategorySmall(String categorySmall)
    {
        this.categorySmall = categorySmall;
    }

    public String getCategorySmall()
    {
        return categorySmall;
    }
    public void setRevokeDate(Date revokeDate)
    {
        this.revokeDate = revokeDate;
    }

    public Date getRevokeDate()
    {
        return revokeDate;
    }
    public void setRevokeReason(String revokeReason)
    {
        this.revokeReason = revokeReason;
    }

    public String getRevokeReason()
    {
        return revokeReason;
    }
    public void setCancelDate(Date cancelDate)
    {
        this.cancelDate = cancelDate;
    }

    public Date getCancelDate()
    {
        return cancelDate;
    }
    public void setCancelReason(String cancelReason)
    {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason()
    {
        return cancelReason;
    }
    public void setPercentileScore(String percentileScore)
    {
        this.percentileScore = percentileScore;
    }

    public String getPercentileScore()
    {
        return percentileScore;
    }
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getKeyword()
    {
        return keyword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("infoId", getInfoId())
                .append("errorCode", getErrorCode())
                .append("reason", getReason())
                .append("id", getId())
                .append("type", getType())
                .append("legalPersonName", getLegalPersonName())
                .append("name", getName())
                .append("property3", getProperty3())
                .append("alias", getAlias())
                .append("historyNames", getHistoryNames())
                .append("isMicroEnt", getIsMicroEnt())
                .append("regStatus", getRegStatus())
                .append("estiblishTime", getEstiblishTime())
                .append("fromTime", getFromTime())
                .append("toTime", getToTime())
                .append("staffNumRange", getStaffNumRange())
                .append("companyOrgType", getCompanyOrgType())
                .append("bondType", getBondType())
                .append("bondNum", getBondNum())
                .append("bondName", getBondName())
                .append("usedBondName", getUsedBondName())
                .append("regNumber", getRegNumber())
                .append("regCapital", getRegCapital())
                .append("regCapitalCurrency", getRegCapitalCurrency())
                .append("actualCapital", getActualCapital())
                .append("actualCapitalCurrency", getActualCapitalCurrency())
                .append("taxNumber", getTaxNumber())
                .append("compForm", getCompForm())
                .append("approvedTime", getApprovedTime())
                .append("regInstitute", getRegInstitute())
                .append("orgNumber", getOrgNumber())
                .append("creditCode", getCreditCode())
                .append("socialStaffNum", getSocialStaffNum())
                .append("tags", getTags())
                .append("businessScope", getBusinessScope())
                .append("updateTimes", getUpdateTimes())
                .append("regLocation", getRegLocation())
                .append("base", getBase())
                .append("city", getCity())
                .append("district", getDistrict())
                .append("industry", getIndustry())
                .append("category", getCategory())
                .append("categoryBig", getCategoryBig())
                .append("categoryMiddle", getCategoryMiddle())
                .append("categorySmall", getCategorySmall())
                .append("revokeDate", getRevokeDate())
                .append("revokeReason", getRevokeReason())
                .append("cancelDate", getCancelDate())
                .append("cancelReason", getCancelReason())
                .append("percentileScore", getPercentileScore())
                .append("keyword", getKeyword())
                .toString();
    }
}
