package com.highgo.crm.domain;

import java.io.Serializable;
import java.util.List;

public class OppoQuotationInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<OpportunityQuotation> quotationInfo_a;
    private List<OpportunityQuotation> quotationInfo_m;
    private List<OpportunityQuotation> quotationInfo_d;

    public List<OpportunityQuotation> getQuotationInfo_a()
    {
        return quotationInfo_a;
    }

    public void setQuotationInfo_a(List<OpportunityQuotation> quotationInfo_a)
    {
        this.quotationInfo_a = quotationInfo_a;
    }

    public List<OpportunityQuotation> getQuotationInfo_m()
    {
        return quotationInfo_m;
    }

    public void setQuotationInfo_m(List<OpportunityQuotation> quotationInfo_m)
    {
        this.quotationInfo_m = quotationInfo_m;
    }

    public List<OpportunityQuotation> getQuotationInfo_d()
    {
        return quotationInfo_d;
    }

    public void setQuotationInfo_d(List<OpportunityQuotation> quotationInfo_d)
    {
        this.quotationInfo_d = quotationInfo_d;
    }
}
