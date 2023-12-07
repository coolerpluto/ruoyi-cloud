package com.highgo.company.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.highgo.company.mapper.OutApiHisMapper;
import com.highgo.company.domain.OutApiHis;
import com.highgo.company.service.IOutApiHisService;

/**
 * 接口调用Service业务层处理
 *
 * @author eszhanga
 * @date 2023-12-06
 */
@Service
public class OutApiHisServiceImpl implements IOutApiHisService
{
    @Autowired
    private OutApiHisMapper outApiHisMapper;

    /**
     * 查询接口调用
     *
     * @param id 接口调用主键
     * @return 接口调用
     */
    @Override
    public OutApiHis selectOutApiHisById(String id)
    {
        return outApiHisMapper.selectOutApiHisById(id);
    }

    /**
     * 查询接口调用列表
     *
     * @param outApiHis 接口调用
     * @return 接口调用
     */
    @Override
    public List<OutApiHis> selectOutApiHisList(OutApiHis outApiHis)
    {
        return outApiHisMapper.selectOutApiHisList(outApiHis);
    }

    /**
     * 新增接口调用
     *
     * @param outApiHis 接口调用
     * @return 结果
     */
    @Override
    @Async
    public int insertOutApiHis(OutApiHis outApiHis)
    {
        outApiHis.setCreateTime(DateUtils.getNowDate());
        return outApiHisMapper.insertOutApiHis(outApiHis);
    }

    /**
     * 修改接口调用
     *
     * @param outApiHis 接口调用
     * @return 结果
     */
    @Override
    public int updateOutApiHis(OutApiHis outApiHis)
    {
        return outApiHisMapper.updateOutApiHis(outApiHis);
    }

    /**
     * 批量删除接口调用
     *
     * @param ids 需要删除的接口调用主键
     * @return 结果
     */
    @Override
    public int deleteOutApiHisByIds(String[] ids)
    {
        return outApiHisMapper.deleteOutApiHisByIds(ids);
    }

    /**
     * 删除接口调用信息
     *
     * @param id 接口调用主键
     * @return 结果
     */
    @Override
    public int deleteOutApiHisById(String id)
    {
        return outApiHisMapper.deleteOutApiHisById(id);
    }
}
