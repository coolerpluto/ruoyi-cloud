package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoKeyStandBy implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OpportunitySupport> keyStandBy_a;
    private List<OpportunitySupport> keyStandBy_m;
    private List<OpportunitySupport> keyStandBy_d;

    public List<OpportunitySupport> getKeyStandBy_a()
    {
        return keyStandBy_a;
    }

    public void setKeyStandBy_a(List<OpportunitySupport> keyStandBy_a)
    {
        this.keyStandBy_a = keyStandBy_a;
    }

    public List<OpportunitySupport> getKeyStandBy_m()
    {
        return keyStandBy_m;
    }

    public void setKeyStandBy_m(List<OpportunitySupport> keyStandBy_m)
    {
        this.keyStandBy_m = keyStandBy_m;
    }

    public List<OpportunitySupport> getKeyStandBy_d()
    {
        return keyStandBy_d;
    }

    public void setKeyStandBy_d(List<OpportunitySupport> keyStandBy_d)
    {
        this.keyStandBy_d = keyStandBy_d;
    }
}
