package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.LicenseApply;
import org.apache.ibatis.annotations.Param;

/**
 * license申请Mapper接口
 *
 * @author eszhang
 * @date 2023-04-26
 */
public interface LicenseApplyMapper
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
     * 删除license申请
     *
     * @param id license申请主键
     * @return 结果
     */
    public int deleteLicenseApplyById(Long id);

    /**
     * 批量删除license申请
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLicenseApplyByIds(Long[] ids);

    LicenseApply getLicApplyByCode(@Param("code") String code);

    int saveAndUpdate(LicenseApply licenseData);
}
