package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.LicenseApply;
import com.highgo.crm.domain.LicenseSaveAndUpReq;

/**
 * license申请Service接口
 *
 * @author eszhang
 * @date 2023-04-26
 */
public interface ILicenseApplyService
{
    /**
     * 查询license申请
     *
     * @param id license申请主键
     * @return license申请
     */
    public LicenseApply selectLicenseApplyById(Long id);

    /**
     * 查询license申请列表
     *
     * @param licenseApply license申请
     * @return license申请集合
     */
    public List<LicenseApply> selectLicenseApplyList(LicenseApply licenseApply);

    /**
     * 新增license申请
     *
     * @param licenseApply license申请
     * @return 结果
     */
    public int insertLicenseApply(LicenseApply licenseApply);

    /**
     * 修改license申请
     *
     * @param licenseApply license申请
     * @return 结果
     */
    public int updateLicenseApply(LicenseApply licenseApply);

    /**
     * 批量删除license申请
     *
     * @param ids 需要删除的license申请主键集合
     * @return 结果
     */
    public int deleteLicenseApplyByIds(Long[] ids);

    /**
     * 删除license申请信息
     *
     * @param id license申请主键
     * @return 结果
     */
    public int deleteLicenseApplyById(Long id);

    LicenseApply getLicApplyByCode(String code);

    int saveAndUpdate(LicenseSaveAndUpReq licenseReq);
}
