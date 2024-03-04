package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoKeyContacts implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OpportunityContactInfo> keyContacts_a;
    private List<OpportunityContactInfo> keyContacts_m;
    private List<OpportunityContactInfo> keyContacts_d;

    public List<OpportunityContactInfo> getKeyContacts_a()
    {
        return keyContacts_a;
    }

    public void setKeyContacts_a(List<OpportunityContactInfo> keyContacts_a)
    {
        this.keyContacts_a = keyContacts_a;
    }

    public List<OpportunityContactInfo> getKeyContacts_m()
    {
        return keyContacts_m;
    }

    public void setKeyContacts_m(List<OpportunityContactInfo> keyContacts_m)
    {
        this.keyContacts_m = keyContacts_m;
    }

    public List<OpportunityContactInfo> getKeyContacts_d()
    {
        return keyContacts_d;
    }

    public void setKeyContacts_d(List<OpportunityContactInfo> keyContacts_d)
    {
        this.keyContacts_d = keyContacts_d;
    }
}
