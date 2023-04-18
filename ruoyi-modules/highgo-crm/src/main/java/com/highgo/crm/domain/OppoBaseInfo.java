package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoBaseInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private OpportunityUnited oppInfo;
    private List<OpportunitySoftwareOperation> operations_a;
    private List<OpportunitySoftwareOperation> operations_m;
    private List<OpportunitySoftwareOperation> operations_d;

    public OpportunityUnited getOppInfo()
    {
        return oppInfo;
    }

    public void setOppInfo(OpportunityUnited oppInfo)
    {
        this.oppInfo = oppInfo;
    }

    public List<OpportunitySoftwareOperation> getOperations_a()
    {
        return operations_a;
    }

    public void setOperations_a(List<OpportunitySoftwareOperation> operations_a)
    {
        this.operations_a = operations_a;
    }

    public List<OpportunitySoftwareOperation> getOperations_m()
    {
        return operations_m;
    }

    public void setOperations_m(List<OpportunitySoftwareOperation> operations_m)
    {
        this.operations_m = operations_m;
    }

    public List<OpportunitySoftwareOperation> getOperations_d()
    {
        return operations_d;
    }

    public void setOperations_d(List<OpportunitySoftwareOperation> operations_d)
    {
        this.operations_d = operations_d;
    }
}
