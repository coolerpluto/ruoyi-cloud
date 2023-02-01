package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysAddrMapper;
import com.ruoyi.system.domain.SysAddr;
import com.ruoyi.system.service.ISysAddrService;

/**
 * 地址联动Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-01
 */
@Service
public class SysAddrServiceImpl implements ISysAddrService 
{
    @Autowired
    private SysAddrMapper sysAddrMapper;

    /**
     * 查询地址联动
     * 
     * @param id 地址联动主键
     * @return 地址联动
     */
    @Override
    public SysAddr selectSysAddrById(String id)
    {
        return sysAddrMapper.selectSysAddrById(id);
    }

    /**
     * 查询地址联动列表
     * 
     * @param sysAddr 地址联动
     * @return 地址联动
     */
    @Override
    public List<SysAddr> selectSysAddrList(SysAddr sysAddr)
    {
        return sysAddrMapper.selectSysAddrList(sysAddr);
    }

    /**
     * 新增地址联动
     * 
     * @param sysAddr 地址联动
     * @return 结果
     */
    @Override
    public int insertSysAddr(SysAddr sysAddr)
    {
        return sysAddrMapper.insertSysAddr(sysAddr);
    }

    /**
     * 修改地址联动
     * 
     * @param sysAddr 地址联动
     * @return 结果
     */
    @Override
    public int updateSysAddr(SysAddr sysAddr)
    {
        return sysAddrMapper.updateSysAddr(sysAddr);
    }

    /**
     * 批量删除地址联动
     * 
     * @param ids 需要删除的地址联动主键
     * @return 结果
     */
    @Override
    public int deleteSysAddrByIds(String[] ids)
    {
        return sysAddrMapper.deleteSysAddrByIds(ids);
    }

    /**
     * 删除地址联动信息
     * 
     * @param id 地址联动主键
     * @return 结果
     */
    @Override
    public int deleteSysAddrById(String id)
    {
        return sysAddrMapper.deleteSysAddrById(id);
    }
}
