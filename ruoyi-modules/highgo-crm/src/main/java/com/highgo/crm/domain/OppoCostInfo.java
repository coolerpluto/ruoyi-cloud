package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoCostInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OpportunityCost> costInfos_a;
    private List<OpportunityCost> costInfos_d;
    private List<OpportunityCost> costInfos_m;

    public List<OpportunityCost> getCostInfos_a()
    {
        return costInfos_a;
    }

    public void setCostInfos_a(List<OpportunityCost> costInfos_a)
    {
        this.costInfos_a = costInfos_a;
    }

    public List<OpportunityCost> getCostInfos_d()
    {
        return costInfos_d;
    }

    public void setCostInfos_d(List<OpportunityCost> costInfos_d)
    {
        this.costInfos_d = costInfos_d;
    }

    public List<OpportunityCost> getCostInfos_m()
    {
        return costInfos_m;
    }

    public void setCostInfos_m(List<OpportunityCost> costInfos_m)
    {
        this.costInfos_m = costInfos_m;
    }
}
