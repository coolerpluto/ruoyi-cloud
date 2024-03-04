package com.highgo.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.highgo.crm.domain.Application;
import com.highgo.crm.domain.Contact;
import com.highgo.crm.domain.OpportunityUnited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.highgo.crm.domain.Company;
import com.highgo.crm.service.ICompanyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 公司Controller
 *
 * @author eszhang
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController
{
    @Autowired
    private ICompanyService companyService;

    /**
     * 查询公司列表
     */
    @RequiresPermissions("crm:company:list")
    @GetMapping("/list")
    public TableDataInfo list(Company company)
    {
        startPage();
        List<Company> list = companyService.selectCompanyList(company);
        return getDataTable(list);
    }

    @GetMapping("/listOppo")
    public TableDataInfo listOppo(Company company)
    {
        startPage();
        List<OpportunityUnited> oppList = companyService.selectOppoList(company);
        return getDataTable(oppList);
    }

    @GetMapping("/listApp")
    public TableDataInfo listApplication(Company company)
    {
        startPage();
        List<Application> appList = companyService.selectApplicationList(company);
        return getDataTable(appList);
    }

    @GetMapping("/listContact")
    public TableDataInfo listContact(Company company)
    {
        startPage();
        List<Contact> contactList = companyService.selectContactList(company);
        return getDataTable(contactList);
    }

    @GetMapping("/tianYanChaSearch")
    public AjaxResult searchCompanyByName(Company company)
    {
        List<Company> list = companyService.searchCompanyByName(company);
        return success(list);
    }

    /**
     * 导出公司列表
     */
    @RequiresPermissions("crm:company:export")
    @Log(title = "公司", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Company company)
    {
        List<Company> list = companyService.selectCompanyList(company);
        ExcelUtil<Company> util = new ExcelUtil<Company>(Company.class);
        util.exportExcel(response, list, "公司数据");
    }

    /**
     * 获取公司详细信息
     */
    @RequiresPermissions("crm:company:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(companyService.selectCompanyById(id));
    }

    /**
     * 新增公司
     */
    @RequiresPermissions("crm:company:add")
    @Log(title = "公司", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Company company)
    {
        return toAjax(companyService.insertCompany(company));
    }

    /**
     * 修改公司
     */
    @RequiresPermissions("crm:company:edit")
    @Log(title = "公司", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Company company)
    {
        return toAjax(companyService.updateCompany(company));
    }
    /**
     * 转移联系人归属人
     */
    @RequiresPermissions("crm:company:transfer")
    @Log(title = "联系人", businessType = BusinessType.UPDATE)
    @PutMapping("/transfer")
    public AjaxResult transfer(@RequestBody Company company)
    {
        return toAjax(companyService.transfer(company));
    }
    /**
     * 删除公司
     */
    @RequiresPermissions("crm:company:remove")
    @Log(title = "公司", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(companyService.deleteCompanyByIds(ids));
    }
}
