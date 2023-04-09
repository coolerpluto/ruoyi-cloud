package com.highgo.crm.mapper;

import com.ruoyi.system.api.domain.SysUser;

import java.util.List;

public interface EmployeeMapper
{
    List<SysUser> selectUserList(SysUser user);
}
