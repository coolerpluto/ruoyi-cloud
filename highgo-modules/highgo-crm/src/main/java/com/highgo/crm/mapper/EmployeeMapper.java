package com.highgo.crm.mapper;

import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysUser;

import java.util.List;

public interface EmployeeMapper
{
    List<SysUser> selectUserList(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    List<SysDept> selectDeptList(SysDept dept);
}
