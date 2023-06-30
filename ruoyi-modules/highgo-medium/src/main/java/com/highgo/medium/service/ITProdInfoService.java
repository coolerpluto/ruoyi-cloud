package com.highgo.medium.service;

import java.util.List;
import com.highgo.medium.domain.TProdInfo;

/**
 * 产品信息Service接口
 * 
 * @author esz
 * @date 2023-06-28
 */
public interface ITProdInfoService 
{
    /**
     * 查询产品信息
     * 
     * @param id 产品信息主键
     * @return 产品信息
     */
    public TProdInfo selectTProdInfoById(Long id);

    /**
     * 查询产品信息列表
     * 
     * @param tProdInfo 产品信息
     * @return 产品信息集合
     */
    public List<TProdInfo> selectTProdInfoList(TProdInfo tProdInfo);

    /**
     * 新增产品信息
     * 
     * @param tProdInfo 产品信息
     * @return 结果
     */
    public int insertTProdInfo(TProdInfo tProdInfo);

    /**
     * 修改产品信息
     * 
     * @param tProdInfo 产品信息
     * @return 结果
     */
    public int updateTProdInfo(TProdInfo tProdInfo);

    /**
     * 批量删除产品信息
     * 
     * @param ids 需要删除的产品信息主键集合
     * @return 结果
     */
    public int deleteTProdInfoByIds(Long[] ids);

    /**
     * 删除产品信息信息
     * 
     * @param id 产品信息主键
     * @return 结果
     */
    public int deleteTProdInfoById(Long id);
}
