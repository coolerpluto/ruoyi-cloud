package com.highgo.crm.service.impl;

import com.highgo.crm.mapper.EmployeeMapper;
import com.highgo.crm.service.IEmployeeService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService
{
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<SysUser> selectUserList(SysUser user)
    {
        return employeeMapper.selectUserList(user);
    }
}
