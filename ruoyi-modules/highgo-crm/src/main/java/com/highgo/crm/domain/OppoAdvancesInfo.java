package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoAdvancesInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OpportunityAdvances> advancesInfo_a;
    private List<OpportunityAdvances> advancesInfo_m;
    private List<OpportunityAdvances> advancesInfo_d;

    public List<OpportunityAdvances> getAdvancesInfo_a()
    {
        return advancesInfo_a;
    }

    public void setAdvancesInfo_a(List<OpportunityAdvances> advancesInfo_a)
    {
        this.advancesInfo_a = advancesInfo_a;
    }

    public List<OpportunityAdvances> getAdvancesInfo_m()
    {
        return advancesInfo_m;
    }

    public void setAdvancesInfo_m(List<OpportunityAdvances> advancesInfo_m)
    {
        this.advancesInfo_m = advancesInfo_m;
    }

    public List<OpportunityAdvances> getAdvancesInfo_d()
    {
        return advancesInfo_d;
    }

    public void setAdvancesInfo_d(List<OpportunityAdvances> advancesInfo_d)
    {
        this.advancesInfo_d = advancesInfo_d;
    }
}
