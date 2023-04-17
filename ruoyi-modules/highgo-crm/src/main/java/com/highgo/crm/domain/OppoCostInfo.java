package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoCostInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OppoCostInfo> costInfos_a;
    private List<OppoCostInfo> costInfos_d;
    private List<OppoCostInfo> costInfos_m;

    public List<OppoCostInfo> getCostInfos_a()
    {
        return costInfos_a;
    }

    public void setCostInfos_a(List<OppoCostInfo> costInfos_a)
    {
        this.costInfos_a = costInfos_a;
    }

    public List<OppoCostInfo> getCostInfos_d()
    {
        return costInfos_d;
    }

    public void setCostInfos_d(List<OppoCostInfo> costInfos_d)
    {
        this.costInfos_d = costInfos_d;
    }

    public List<OppoCostInfo> getCostInfos_m()
    {
        return costInfos_m;
    }

    public void setCostInfos_m(List<OppoCostInfo> costInfos_m)
    {
        this.costInfos_m = costInfos_m;
    }
}
