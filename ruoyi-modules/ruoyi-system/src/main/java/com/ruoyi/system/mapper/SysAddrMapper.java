package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysAddr;

/**
 * 地址联动Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-01
 */
public interface SysAddrMapper 
{
    /**
     * 查询地址联动
     * 
     * @param id 地址联动主键
     * @return 地址联动
     */
    public SysAddr selectSysAddrById(String id);

    /**
     * 查询地址联动列表
     * 
     * @param sysAddr 地址联动
     * @return 地址联动集合
     */
    public List<SysAddr> selectSysAddrList(SysAddr sysAddr);

    /**
     * 新增地址联动
     * 
     * @param sysAddr 地址联动
     * @return 结果
     */
    public int insertSysAddr(SysAddr sysAddr);

    /**
     * 修改地址联动
     * 
     * @param sysAddr 地址联动
     * @return 结果
     */
    public int updateSysAddr(SysAddr sysAddr);

    /**
     * 删除地址联动
     * 
     * @param id 地址联动主键
     * @return 结果
     */
    public int deleteSysAddrById(String id);

    /**
     * 批量删除地址联动
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysAddrByIds(String[] ids);
}