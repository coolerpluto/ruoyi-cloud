package com.highgo.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商机统一查询信息体
 */
public class OpportunityUnited  extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    @Excel(name = "当前阶段")
    private String currentStage;
    private String name;

    private String custName;
    private Long custId;

    private String ownerName;
    private String ownerId;
    private String ownerDeptId;
    private String ownerDeptName;

    private String sharedId;
    private String sharedDeptId;


    //属性中拎出来用来展示
    /**
     * 投标时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date preTenderDate;
    /**
     * 预签单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date preSignDate;
    /**
     * 预合同金额
     */
    private BigDecimal preContractVal;

    // 下面未商机附属表信息
    /**
     * 商机最新经历过的阶段和时间
     */
    private Map<String, Date> stageActionDate;
    /**
     * 商机对应的进展列表
     */
    private List<OpportunityAdvances> advances;
    /**
     * 商机对应的花费列表
     */
    private List<OpportunityCost> costs;
    /**
     * 商机对应的竞争对上信息
     */
    private List<OpportunityCompetitor> competitors;
    /**
     * 商机对应的政策文件
     */
    private List<OpportunityPolicy> policies;
    /**
     * 商机中除了单独成表的全部其他数据
     */
    private List<OpportunityProperty> properties;
    private Map<String, Object> propertyMap;
    /**
     * 商机中客户的应用运营信息
     */
    private List<OpportunitySoftwareOperation> softwareOperations;
    /**
     * 商机变更历史信息
     */
    private List<OpportunityStageChangeHis> stageChangeHis;

    public Date getPreTenderDate()
    {
        return preTenderDate;
    }

    public void setPreTenderDate(Date preTenderDate)
    {
        this.preTenderDate = preTenderDate;
    }

    public Date getPreSignDate()
    {
        return preSignDate;
    }

    public void setPreSignDate(Date preSignDate)
    {
        this.preSignDate = preSignDate;
    }

    public BigDecimal getPreContractVal()
    {
        return preContractVal;
    }

    public void setPreContractVal(BigDecimal preContractVal)
    {
        this.preContractVal = preContractVal;
    }

    public String getCurrentStage()
    {
        return currentStage;
    }

    public void setCurrentStage(String currentStage)
    {
        this.currentStage = currentStage;
    }

    public Map<String, Object> getPropertyMap()
    {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, Object> propertyMap)
    {
        this.propertyMap = propertyMap;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCustName()
    {
        return custName;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public Long getCustId()
    {
        return custId;
    }

    public void setCustId(Long custId)
    {
        this.custId = custId;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public String getOwnerDeptId()
    {
        return ownerDeptId;
    }

    public void setOwnerDeptId(String ownerDeptId)
    {
        this.ownerDeptId = ownerDeptId;
    }

    public String getOwnerDeptName()
    {
        return ownerDeptName;
    }

    public void setOwnerDeptName(String ownerDeptName)
    {
        this.ownerDeptName = ownerDeptName;
    }

    public String getSharedId()
    {
        return sharedId;
    }

    public void setSharedId(String sharedId)
    {
        this.sharedId = sharedId;
    }

    public String getSharedDeptId()
    {
        return sharedDeptId;
    }

    public void setSharedDeptId(String sharedDeptId)
    {
        this.sharedDeptId = sharedDeptId;
    }

    public Map<String, Date> getStageActionDate()
    {
        return stageActionDate;
    }

    public void setStageActionDate(Map<String, Date> stageActionDate)
    {
        this.stageActionDate = stageActionDate;
    }

    public List<OpportunityAdvances> getAdvances()
    {
        return advances;
    }

    public void setAdvances(List<OpportunityAdvances> advances)
    {
        this.advances = advances;
    }

    public List<OpportunityCost> getCosts()
    {
        return costs;
    }

    public void setCosts(List<OpportunityCost> costs)
    {
        this.costs = costs;
    }

    public List<OpportunityCompetitor> getCompetitors()
    {
        return competitors;
    }

    public void setCompetitors(List<OpportunityCompetitor> competitors)
    {
        this.competitors = competitors;
    }

    public List<OpportunityPolicy> getPolicies()
    {
        return policies;
    }

    public void setPolicies(List<OpportunityPolicy> policies)
    {
        this.policies = policies;
    }

    public List<OpportunityProperty> getProperties()
    {
        return properties;
    }

    public void setProperties(List<OpportunityProperty> properties)
    {
        this.properties = properties;
    }

    public List<OpportunitySoftwareOperation> getSoftwareOperations()
    {
        return softwareOperations;
    }

    public void setSoftwareOperations(List<OpportunitySoftwareOperation> softwareOperations)
    {
        this.softwareOperations = softwareOperations;
    }

    public List<OpportunityStageChangeHis> getStageChangeHis()
    {
        return stageChangeHis;
    }

    public void setStageChangeHis(List<OpportunityStageChangeHis> stageChangeHis)
    {
        this.stageChangeHis = stageChangeHis;
    }
}
