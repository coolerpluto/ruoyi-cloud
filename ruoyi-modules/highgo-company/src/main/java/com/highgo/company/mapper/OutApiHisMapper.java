package com.highgo.company.mapper;

import java.util.List;
import com.highgo.company.domain.OutApiHis;

/**
 * 接口调用Mapper接口
 *
 * @author eszhanga
 * @date 2023-12-06
 */
public interface OutApiHisMapper
{
    /**
     * 查询接口调用
     *
     * @param id 接口调用主键
     * @return 接口调用
     */
    public OutApiHis selectOutApiHisById(String id);

    /**
     * 查询接口调用列表
     *
     * @param outApiHis 接口调用
     * @return 接口调用集合
     */
    public List<OutApiHis> selectOutApiHisList(OutApiHis outApiHis);

    /**
     * 新增接口调用
     *
     * @param outApiHis 接口调用
     * @return 结果
     */
    public int insertOutApiHis(OutApiHis outApiHis);

    /**
     * 修改接口调用
     *
     * @param outApiHis 接口调用
     * @return 结果
     */
    public int updateOutApiHis(OutApiHis outApiHis);

    /**
     * 删除接口调用
     *
     * @param id 接口调用主键
     * @return 结果
     */
    public int deleteOutApiHisById(String id);

    /**
     * 批量删除接口调用
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutApiHisByIds(String[] ids);
}
