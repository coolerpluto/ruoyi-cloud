package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.LicenseApplyMapper;
import com.highgo.crm.domain.LicenseApply;
import com.highgo.crm.service.ILicenseApplyService;

/**
 * license申请Service业务层处理
 *
 * @author eszhang
 * @date 2023-04-26
 */
@Service
public class LicenseApplyServiceImpl implements ILicenseApplyService
{
    @Autowired
    private LicenseApplyMapper licenseApplyMapper;

    /**
     * 查询license申请
     *
     * @param id license申请主键
     * @return license申请
     */
    @Override
    public LicenseApply selectLicenseApplyById(Long id)
    {
        return licenseApplyMapper.selectLicenseApplyById(id);
    }

    /**
     * 查询license申请列表
     *
     * @param licenseApply license申请
     * @return license申请
     */
    @Override
    public List<LicenseApply> selectLicenseApplyList(LicenseApply licenseApply)
    {
        return licenseApplyMapper.selectLicenseApplyList(licenseApply);
    }

    /**
     * 新增license申请
     *
     * @param licenseApply license申请
     * @return 结果
     */
    @Override
    public int insertLicenseApply(LicenseApply licenseApply)
    {
        licenseApply.setCreateTime(DateUtils.getNowDate());
        SysUser current = SecurityUtils.getLoginUser().getSysUser();
        licenseApply.setCreateBy(current.getUserName());
        licenseApply.setOwnerId(current.getUserId());
        licenseApply.setDeptId(current.getDeptId());
        return licenseApplyMapper.insertLicenseApply(licenseApply);
    }

    /**
     * 修改license申请
     *
     * @param licenseApply license申请
     * @return 结果
     */
    @Override
    public int updateLicenseApply(LicenseApply licenseApply)
    {
        licenseApply.setUpdateTime(DateUtils.getNowDate());
        licenseApply.setUpdateBy(SecurityUtils.getUsername());
        return licenseApplyMapper.updateLicenseApply(licenseApply);
    }

    /**
     * 批量删除license申请
     *
     * @param ids 需要删除的license申请主键
     * @return 结果
     */
    @Override
    public int deleteLicenseApplyByIds(Long[] ids)
    {
        return licenseApplyMapper.deleteLicenseApplyByIds(ids);
    }

    /**
     * 删除license申请信息
     *
     * @param id license申请主键
     * @return 结果
     */
    @Override
    public int deleteLicenseApplyById(Long id)
    {
        return licenseApplyMapper.deleteLicenseApplyById(id);
    }
}
