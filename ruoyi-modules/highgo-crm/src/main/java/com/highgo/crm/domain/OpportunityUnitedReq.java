package com.highgo.crm.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.Map;

/**
 * 商机统一保存和修改信息体
 */
public class OpportunityUnitedReq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String code;
    private String currentStage;
    private String newStage;
    private String stageActive;
    private String name;

    private OppoBaseInfo baseInfo;
    private OppoKeyContacts keyContacts;
    private Map<String, OpportunityProperty> custInfo;
    private Map<String, OpportunityProperty> policyStandBy;
    private OppoCompetitorInfo competitorInfo;
    private Map<String, OpportunityProperty> oppDesc;
    private Map<String, OpportunityProperty> oppInfluence;
    private OppoQuotationInfo quotationInfo;
    private Map<String, OpportunityProperty> summaryInfo;
    private OppoCostInfo costInfo;
    private Map<String, OpportunityProperty> actionReason;
    private Map<String, OpportunityProperty> biddingInfo;
    private Map<String, OpportunityProperty> winningBidding;
    private OppoAdvancesInfo advancesInfo;
    private OppoKeyStandBy keyStandBy;
    private Map<String, OpportunityProperty> oppImplement;
    private Map<String, OpportunityProperty> signInfo;

    public String getNewStage()
    {
        return newStage;
    }

    public void setNewStage(String newStage)
    {
        this.newStage = newStage;
    }

    public OppoCostInfo getCostInfo()
    {
        return costInfo;
    }

    public void setCostInfo(OppoCostInfo costInfo)
    {
        this.costInfo = costInfo;
    }

    public Map<String, OpportunityProperty> getBiddingInfo()
    {
        return biddingInfo;
    }

    public void setBiddingInfo(Map<String, OpportunityProperty> biddingInfo)
    {
        this.biddingInfo = biddingInfo;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCurrentStage()
    {
        return currentStage;
    }

    public void setCurrentStage(String currentStage)
    {
        this.currentStage = currentStage;
    }

    public String getStageActive()
    {
        return stageActive;
    }

    public void setStageActive(String stageActive)
    {
        this.stageActive = stageActive;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public OppoBaseInfo getBaseInfo()
    {
        return baseInfo;
    }

    public void setBaseInfo(OppoBaseInfo baseInfo)
    {
        this.baseInfo = baseInfo;
    }

    public OppoKeyContacts getKeyContacts()
    {
        return keyContacts;
    }

    public void setKeyContacts(OppoKeyContacts keyContacts)
    {
        this.keyContacts = keyContacts;
    }

    public Map<String, OpportunityProperty> getCustInfo()
    {
        return custInfo;
    }

    public void setCustInfo(Map<String, OpportunityProperty> custInfo)
    {
        this.custInfo = custInfo;
    }

    public Map<String, OpportunityProperty> getPolicyStandBy()
    {
        return policyStandBy;
    }

    public void setPolicyStandBy(Map<String, OpportunityProperty> policyStandBy)
    {
        this.policyStandBy = policyStandBy;
    }

    public Map<String, OpportunityProperty> getOppDesc()
    {
        return oppDesc;
    }

    public void setOppDesc(Map<String, OpportunityProperty> oppDesc)
    {
        this.oppDesc = oppDesc;
    }

    public OppoQuotationInfo getQuotationInfo()
    {
        return quotationInfo;
    }

    public void setQuotationInfo(OppoQuotationInfo quotationInfo)
    {
        this.quotationInfo = quotationInfo;
    }

    public OppoCompetitorInfo getCompetitorInfo()
    {
        return competitorInfo;
    }

    public void setCompetitorInfo(OppoCompetitorInfo competitorInfo)
    {
        this.competitorInfo = competitorInfo;
    }

    public Map<String, OpportunityProperty> getOppInfluence()
    {
        return oppInfluence;
    }

    public void setOppInfluence(Map<String, OpportunityProperty> oppInfluence)
    {
        this.oppInfluence = oppInfluence;
    }

    public Map<String, OpportunityProperty> getOppImplement()
    {
        return oppImplement;
    }

    public void setOppImplement(Map<String, OpportunityProperty> oppImplement)
    {
        this.oppImplement = oppImplement;
    }

    public Map<String, OpportunityProperty> getSummaryInfo()
    {
        return summaryInfo;
    }

    public void setSummaryInfo(Map<String, OpportunityProperty> summaryInfo)
    {
        this.summaryInfo = summaryInfo;
    }

    public Map<String, OpportunityProperty> getWinningBidding()
    {
        return winningBidding;
    }

    public void setWinningBidding(Map<String, OpportunityProperty> winningBidding)
    {
        this.winningBidding = winningBidding;
    }

    public Map<String, OpportunityProperty> getActionReason()
    {
        return actionReason;
    }

    public void setActionReason(Map<String, OpportunityProperty> actionReason)
    {
        this.actionReason = actionReason;
    }

    public Map<String, OpportunityProperty> getSignInfo()
    {
        return signInfo;
    }

    public void setSignInfo(Map<String, OpportunityProperty> signInfo)
    {
        this.signInfo = signInfo;
    }

    public OppoAdvancesInfo getAdvancesInfo()
    {
        return advancesInfo;
    }

    public void setAdvancesInfo(OppoAdvancesInfo advancesInfo)
    {
        this.advancesInfo = advancesInfo;
    }

    public OppoKeyStandBy getKeyStandBy()
    {
        return keyStandBy;
    }

    public void setKeyStandBy(OppoKeyStandBy keyStandBy)
    {
        this.keyStandBy = keyStandBy;
    }
}
