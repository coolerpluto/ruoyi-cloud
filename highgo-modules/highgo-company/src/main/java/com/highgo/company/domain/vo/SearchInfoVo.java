package com.highgo.company.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业简单基本信息对象 search_info
 *
 *
 * @date 2023-12-05
 */
public class SearchInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 经营状态 */
    private String regStatus;

    /** 成立日期 */
    private String estiblishTime;

    /** 注册资本 */
    private String regCapital;

    /** 机构类型-1：公司；2：香港企业；3：社会组织；4：律所；5：事业单位；6：基金会；7-不存在法人、注册资本、统一社会信用代码、经营状态;8：台湾企业；9-新机构 */
    private Integer companyType;

    /** 匹配原因 */
    private String matchType;

    /** 1-公司 2-人 */
    private Integer type;

    /** 法人 */
    private String legalPersonName;

    /** 注册号 */
    private String regNumber;

    /** 统一社会信用代码 */
    private String creditCode;

    /** 公司名 */
    private String name;

    /** 公司id */
    private Long id;

    /** 组织机构代码 */
    private String orgNumber;

    /** 省份 */
    private String base;


    public void setRegStatus(String regStatus)
    {
        this.regStatus = regStatus;
    }

    public String getRegStatus()
    {
        return regStatus;
    }
    public void setEstiblishTime(String estiblishTime)
    {
        this.estiblishTime = estiblishTime;
    }

    public String getEstiblishTime()
    {
        return estiblishTime;
    }
    public void setRegCapital(String regCapital)
    {
        this.regCapital = regCapital;
    }

    public String getRegCapital()
    {
        return regCapital;
    }
    public void setCompanyType(Integer companyType)
    {
        this.companyType = companyType;
    }

    public Integer getCompanyType()
    {
        return companyType;
    }
    public void setMatchType(String matchType)
    {
        this.matchType = matchType;
    }

    public String getMatchType()
    {
        return matchType;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
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
    public void setRegNumber(String regNumber)
    {
        this.regNumber = regNumber;
    }

    public String getRegNumber()
    {
        return regNumber;
    }
    public void setCreditCode(String creditCode)
    {
        this.creditCode = creditCode;
    }

    public String getCreditCode()
    {
        return creditCode;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrgNumber(String orgNumber)
    {
        this.orgNumber = orgNumber;
    }

    public String getOrgNumber()
    {
        return orgNumber;
    }
    public void setBase(String base)
    {
        this.base = base;
    }

    public String getBase()
    {
        return base;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("regStatus", getRegStatus())
                .append("estiblishTime", getEstiblishTime())
                .append("regCapital", getRegCapital())
                .append("companyType", getCompanyType())
                .append("matchType", getMatchType())
                .append("type", getType())
                .append("legalPersonName", getLegalPersonName())
                .append("regNumber", getRegNumber())
                .append("creditCode", getCreditCode())
                .append("name", getName())
                .append("id", getId())
                .append("orgNumber", getOrgNumber())
                .append("base", getBase())
                .toString();
    }
}
