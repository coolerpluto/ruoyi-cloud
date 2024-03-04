package com.highgo.medium.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.TSoftRegistrationMapper;
import com.highgo.medium.domain.TSoftRegistration;
import com.highgo.medium.service.ITSoftRegistrationService;

/**
 * 软著登记记录Service业务层处理
 * 
 * @author esz
 * @date 2023-06-28
 */
@Service
public class TSoftRegistrationServiceImpl implements ITSoftRegistrationService 
{
    @Autowired
    private TSoftRegistrationMapper tSoftRegistrationMapper;

    /**
     * 查询软著登记记录
     * 
     * @param id 软著登记记录主键
     * @return 软著登记记录
     */
    @Override
    public TSoftRegistration selectTSoftRegistrationById(Long id)
    {
        return tSoftRegistrationMapper.selectTSoftRegistrationById(id);
    }

    /**
     * 查询软著登记记录列表
     * 
     * @param tSoftRegistration 软著登记记录
     * @return 软著登记记录
     */
    @Override
    public List<TSoftRegistration> selectTSoftRegistrationList(TSoftRegistration tSoftRegistration)
    {
        return tSoftRegistrationMapper.selectTSoftRegistrationList(tSoftRegistration);
    }

    /**
     * 新增软著登记记录
     * 
     * @param tSoftRegistration 软著登记记录
     * @return 结果
     */
    @Override
    public int insertTSoftRegistration(TSoftRegistration tSoftRegistration)
    {
        tSoftRegistration.setCreateTime(DateUtils.getNowDate());
        tSoftRegistration.setCreateBy(SecurityUtils.getNickName());
        tSoftRegistration.setCreateId(SecurityUtils.getUserId());
        return tSoftRegistrationMapper.insertTSoftRegistration(tSoftRegistration);
    }

    /**
     * 修改软著登记记录
     * 
     * @param tSoftRegistration 软著登记记录
     * @return 结果
     */
    @Override
    public int updateTSoftRegistration(TSoftRegistration tSoftRegistration)
    {
        tSoftRegistration.setUpdateTime(DateUtils.getNowDate());
        tSoftRegistration.setUpdateBy(SecurityUtils.getNickName());
        tSoftRegistration.setUpdateId(SecurityUtils.getUserId());
        return tSoftRegistrationMapper.updateTSoftRegistration(tSoftRegistration);
    }

    /**
     * 批量删除软著登记记录
     * 
     * @param ids 需要删除的软著登记记录主键
     * @return 结果
     */
    @Override
    public int deleteTSoftRegistrationByIds(Long[] ids)
    {
        return tSoftRegistrationMapper.deleteTSoftRegistrationByIds(ids);
    }

    /**
     * 删除软著登记记录信息
     * 
     * @param id 软著登记记录主键
     * @return 结果
     */
    @Override
    public int deleteTSoftRegistrationById(Long id)
    {
        return tSoftRegistrationMapper.deleteTSoftRegistrationById(id);
    }
}
