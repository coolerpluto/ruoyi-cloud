package com.highgo.crm.service.impl;

import java.util.List;

import com.highgo.crm.utils.CRMUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.datascope.annotation.CrmDataScope;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.CompanyMapper;
import com.highgo.crm.domain.Company;
import com.highgo.crm.service.ICompanyService;

/**
 * 公司Service业务层处理
 *
 * @author eszhang
 * @date 2023-02-02
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 查询公司
     *
     * @param id 公司主键
     * @return 公司
     */
    @Override
    public Company selectCompanyById(String id) {
        return companyMapper.selectCompanyById(id);
    }

    /**
     * 查询公司列表
     *
     * @param company 公司
     * @return 公司
     */
    @Override
    @CrmDataScope(deptAlias = "sd", userAlias = "su")
    public List<Company> selectCompanyList(Company company) {
        return companyMapper.selectCompanyList(company);
    }

    /**
     * 新增公司
     *
     * @param company 公司
     * @return 结果
     */
    @Override
    public int insertCompany(Company company) {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        company.setCreateId(userCurrent.getUserId());
        company.setCreateBy(SecurityUtils.getUsername());
        company.setOwnerId(userCurrent.getUserId());
        company.setDeptId(userCurrent.getDeptId());
        company.setCreateTime(DateUtils.getNowDate());
        return companyMapper.insertCompany(company);
    }

    /**
     * 修改公司
     *
     * @param company 公司
     * @return 结果
     */
    @Override
    public int updateCompany(Company company) {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        company.setUpdateId(userCurrent.getUserId());
        company.setUpdateBy(SecurityUtils.getUsername());
        company.setUpdateTime(DateUtils.getNowDate());
        return companyMapper.updateCompany(company);
    }

    /**
     * 批量删除公司
     *
     * @param ids 需要删除的公司主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByIds(String[] ids) {
        return companyMapper.deleteCompanyByIds(ids);
    }

    /**
     * 删除公司信息
     *
     * @param id 公司主键
     * @return 结果
     */
    @Override
    public int deleteCompanyById(String id) {
        return companyMapper.deleteCompanyById(id);
    }

    @Override
    public int transfer(Company company) {
        Object temp = company.getParams().get("companyIds");
        List<String> companyResIds = CRMUtil.castList(temp,String.class);
        Long targetOwnerId = company.getOwnerId();
        return companyMapper.changeCompanyOwners(targetOwnerId, companyResIds);
    }
}
