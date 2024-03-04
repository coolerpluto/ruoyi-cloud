package com.highgo.crm.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机统一查询信息体
 */
public class OpportunityTransferReq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long[] selectedIds;
    private String[] selectedCodes;

    private String ownerName;
    private String ownerId;

    private String deptName;
    private String deptId;

    private String remark;

    public Long[] getSelectedIds()
    {
        return selectedIds;
    }

    public void setSelectedIds(Long[] selectedIds)
    {
        this.selectedIds = selectedIds;
    }

    public String[] getSelectedCodes()
    {
        return selectedCodes;
    }

    public void setSelectedCodes(String[] selectedCodes)
    {
        this.selectedCodes = selectedCodes;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    @Override
    public String getRemark()
    {
        return remark;
    }

    @Override
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
