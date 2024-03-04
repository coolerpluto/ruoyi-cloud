package com.highgo.crm.service.impl;

import java.util.List;

import com.highgo.crm.utils.CRMUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.ApplicationMapper;
import com.highgo.crm.domain.Application;
import com.highgo.crm.service.IApplicationService;

/**
 * 应用信息Service业务层处理
 *
 * @author eszhang
 * @date 2023-02-02
 */
@Service
public class ApplicationServiceImpl implements IApplicationService
{
    @Autowired
    private ApplicationMapper applicationMapper;

    /**
     * 查询应用信息
     *
     * @param id 应用信息主键
     * @return 应用信息
     */
    @Override
    public Application selectApplicationById(String id)
    {
        return applicationMapper.selectApplicationById(id);
    }

    /**
     * 查询应用信息列表
     *
     * @param application 应用信息
     * @return 应用信息
     */
    @Override
    public List<Application> selectApplicationList(Application application)
    {
        return applicationMapper.selectApplicationList(application);
    }

    /**
     * 新增应用信息
     *
     * @param application 应用信息
     * @return 结果
     */
    @Override
    public int insertApplication(Application application)
    {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        application.setCreateId(userCurrent.getUserId());
        application.setCreateBy(SecurityUtils.getUsername());
        application.setOwnerId(userCurrent.getUserId());
        application.setDeptId(userCurrent.getDeptId());
        application.setCreateTime(DateUtils.getNowDate());
        return applicationMapper.insertApplication(application);
    }

    /**
     * 修改应用信息
     *
     * @param application 应用信息
     * @return 结果
     */
    @Override
    public int updateApplication(Application application)
    {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        application.setUpdateId(userCurrent.getUserId());
        application.setUpdateBy(SecurityUtils.getUsername());
        application.setUpdateTime(DateUtils.getNowDate());
        application.setUpdateTime(DateUtils.getNowDate());
        return applicationMapper.updateApplication(application);
    }

    /**
     * 批量删除应用信息
     *
     * @param ids 需要删除的应用信息主键
     * @return 结果
     */
    @Override
    public int deleteApplicationByIds(String[] ids)
    {
        return applicationMapper.deleteApplicationByIds(ids);
    }

    /**
     * 删除应用信息信息
     *
     * @param id 应用信息主键
     * @return 结果
     */
    @Override
    public int deleteApplicationById(String id)
    {
        return applicationMapper.deleteApplicationById(id);
    }

    @Override
    public int transfer(Application application) {
        Object temp = application.getParams().get("companyIds");
        List<String> applicationResIds = CRMUtil.castList(temp,String.class);
        Long targetOwnerId = application.getOwnerId();
        return applicationMapper.changeCompanyOwners(targetOwnerId, applicationResIds);
    }
}
