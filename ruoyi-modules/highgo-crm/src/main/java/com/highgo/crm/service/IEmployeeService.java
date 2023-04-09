package com.highgo.crm.service;

import com.ruoyi.system.api.domain.SysUser;

import java.util.List;

public interface IEmployeeService
{
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);
}
