package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoCompetitorInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OpportunityCompetitor> competitorInfo_a;
    private List<OpportunityCompetitor> competitorInfo_m;
    private List<OpportunityCompetitor> competitorInfo_d;

    public List<OpportunityCompetitor> getCompetitorInfo_a()
    {
        return competitorInfo_a;
    }

    public void setCompetitorInfo_a(List<OpportunityCompetitor> competitorInfo_a)
    {
        this.competitorInfo_a = competitorInfo_a;
    }

    public List<OpportunityCompetitor> getCompetitorInfo_m()
    {
        return competitorInfo_m;
    }

    public void setCompetitorInfo_m(List<OpportunityCompetitor> competitorInfo_m)
    {
        this.competitorInfo_m = competitorInfo_m;
    }

    public List<OpportunityCompetitor> getCompetitorInfo_d()
    {
        return competitorInfo_d;
    }

    public void setCompetitorInfo_d(List<OpportunityCompetitor> competitorInfo_d)
    {
        this.competitorInfo_d = competitorInfo_d;
    }
}
