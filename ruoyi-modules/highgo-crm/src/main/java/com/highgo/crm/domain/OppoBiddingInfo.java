package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoBiddingInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private OpportunityProperty supportByExpert;
    private OpportunityProperty knowExpertList;
    private List<OpportunitySoftwareOperation> biddingInfo_a;
    private List<OpportunitySoftwareOperation> biddingInfo_m;
    private List<OpportunitySoftwareOperation> biddingInfo_d;

    public OpportunityProperty getSupportByExpert()
    {
        return supportByExpert;
    }

    public void setSupportByExpert(OpportunityProperty supportByExpert)
    {
        this.supportByExpert = supportByExpert;
    }

    public OpportunityProperty getKnowExpertList()
    {
        return knowExpertList;
    }

    public void setKnowExpertList(OpportunityProperty knowExpertList)
    {
        this.knowExpertList = knowExpertList;
    }

    public List<OpportunitySoftwareOperation> getBiddingInfo_a()
    {
        return biddingInfo_a;
    }

    public void setBiddingInfo_a(List<OpportunitySoftwareOperation> biddingInfo_a)
    {
        this.biddingInfo_a = biddingInfo_a;
    }

    public List<OpportunitySoftwareOperation> getBiddingInfo_m()
    {
        return biddingInfo_m;
    }

    public void setBiddingInfo_m(List<OpportunitySoftwareOperation> biddingInfo_m)
    {
        this.biddingInfo_m = biddingInfo_m;
    }

    public List<OpportunitySoftwareOperation> getBiddingInfo_d()
    {
        return biddingInfo_d;
    }

    public void setBiddingInfo_d(List<OpportunitySoftwareOperation> biddingInfo_d)
    {
        this.biddingInfo_d = biddingInfo_d;
    }
}
