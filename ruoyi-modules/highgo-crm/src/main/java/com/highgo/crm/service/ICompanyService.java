package com.highgo.crm.service;

import java.util.List;

import com.highgo.crm.domain.Application;
import com.highgo.crm.domain.Company;
import com.highgo.crm.domain.Contact;
import com.highgo.crm.domain.OpportunityUnited;

/**
 * 公司Service接口
 * 
 * @author eszhang
 * @date 2023-02-02
 */
public interface ICompanyService 
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
     * 批量删除公司
     * 
     * @param ids 需要删除的公司主键集合
     * @return 结果
     */
    public int deleteCompanyByIds(String[] ids);

    /**
     * 删除公司信息
     * 
     * @param id 公司主键
     * @return 结果
     */
    public int deleteCompanyById(String id);

    int transfer(Company company);

    List<Company> searchCompanyByName(Company company);

    List<OpportunityUnited> selectOppoList(Company company);

    List<Application> selectApplicationList(Company company);

    List<Contact> selectContactList(Company company);
}
