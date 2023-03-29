package com.highgo.crm.domain;

/**
 * t_opportunity_contact_info
 */
public class OpportunityContactInfo extends Contact
{
    private static final long serialVersionUID = 1L;
    /**
     * 联系表ID
     */
    private Long keyId;
    private String opportunityCode;
    /**
     * 关联联系人表ID
     */
    private Long contactId;
    /**
     * 对接情况
     */
    private String exposure;
    /**
     * 关系程度
     */
    private String relationship;
    /**
     * 联系人意向
     */
    private String intention;
    /**
     * 对手是否会面
     */
    private String sideVisited;

    public Long getKeyId()
    {
        return keyId;
    }

    public void setKeyId(Long keyId)
    {
        this.keyId = keyId;
    }

    public String getOpportunityCode()
    {
        return opportunityCode;
    }

    public void setOpportunityCode(String opportunityCode)
    {
        this.opportunityCode = opportunityCode;
    }

    public Long getContactId()
    {
        return contactId;
    }

    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
    }

    public String getExposure()
    {
        return exposure;
    }

    public void setExposure(String exposure)
    {
        this.exposure = exposure;
    }

    public String getRelationship()
    {
        return relationship;
    }

    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    public String getIntention()
    {
        return intention;
    }

    public void setIntention(String intention)
    {
        this.intention = intention;
    }

    public String getSideVisited()
    {
        return sideVisited;
    }

    public void setSideVisited(String sideVisited)
    {
        this.sideVisited = sideVisited;
    }
}
