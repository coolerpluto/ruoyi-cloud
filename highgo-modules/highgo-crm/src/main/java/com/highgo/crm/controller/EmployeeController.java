package com.highgo.crm.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.highgo.crm.service.IEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController
{
    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = employeeService.selectUserList(user);
        return getDataTable(list);
    }
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept)
    {
        return success(employeeService.selectDeptTreeList(dept));
    }
}
