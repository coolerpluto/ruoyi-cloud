package com.highgo.medium.service;

import java.util.List;
import com.highgo.medium.domain.TProdVersionInfo;

/**
 * 产品版本信息Service接口
 * 
 * @author esz
 * @date 2023-06-28
 */
public interface ITProdVersionInfoService 
{
    /**
     * 查询产品版本信息
     * 
     * @param id 产品版本信息主键
     * @return 产品版本信息
     */
    public TProdVersionInfo selectTProdVersionInfoById(Long id);

    /**
     * 查询产品版本信息列表
     * 
     * @param tProdVersionInfo 产品版本信息
     * @return 产品版本信息集合
     */
    public List<TProdVersionInfo> selectTProdVersionInfoList(TProdVersionInfo tProdVersionInfo);

    /**
     * 新增产品版本信息
     * 
     * @param tProdVersionInfo 产品版本信息
     * @return 结果
     */
    public int insertTProdVersionInfo(TProdVersionInfo tProdVersionInfo);

    /**
     * 修改产品版本信息
     * 
     * @param tProdVersionInfo 产品版本信息
     * @return 结果
     */
    public int updateTProdVersionInfo(TProdVersionInfo tProdVersionInfo);

    /**
     * 批量删除产品版本信息
     * 
     * @param ids 需要删除的产品版本信息主键集合
     * @return 结果
     */
    public int deleteTProdVersionInfoByIds(Long[] ids);

    /**
     * 删除产品版本信息信息
     * 
     * @param id 产品版本信息主键
     * @return 结果
     */
    public int deleteTProdVersionInfoById(Long id);
}
