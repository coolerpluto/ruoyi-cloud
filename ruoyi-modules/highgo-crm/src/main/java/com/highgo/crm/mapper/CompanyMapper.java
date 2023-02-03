package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.Company;
import org.apache.ibatis.annotations.Param;

/**
 * 公司Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-02
 */
public interface CompanyMapper 
{
    /**
     * 查询公司
     * 
     * @param id 公司主键
     * @return 公司
     */
    public Company selectCompanyById(String id);

    /**
     * 查询公司列表
     * 
     * @param company 公司
     * @return 公司集合
     */
    public List<Company> selectCompanyList(Company company);

    /**
     * 新增公司
     * 
     * @param company 公司
     * @return 结果
     */
    public int insertCompany(Company company);

    /**
     * 修改公司
     * 
     * @param company 公司
     * @return 结果
     */
    public int updateCompany(Company company);

    /**
     * 删除公司
     * 
     * @param id 公司主键
     * @return 结果
     */
    public int deleteCompanyById(String id);

    /**
     * 批量删除公司
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompanyByIds(String[] ids);

    int changeCompanyOwners(@Param("targetOwnerId") Long targetOwnerId, @Param("companyResIds")List<String> companyResIds);
}
