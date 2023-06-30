package com.highgo.medium.mapper;

import java.util.List;
import com.highgo.medium.domain.TProdInfo;
import com.highgo.medium.domain.TProdVersionInfo;

/**
 * 产品信息Mapper接口
 * 
 * @author esz
 * @date 2023-06-28
 */
public interface TProdInfoMapper 
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
     * 删除产品信息
     * 
     * @param id 产品信息主键
     * @return 结果
     */
    public int deleteTProdInfoById(Long id);

    /**
     * 批量删除产品信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTProdInfoByIds(Long[] ids);

    /**
     * 批量删除产品版本信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTProdVersionInfoByProdIds(Long[] ids);
    
    /**
     * 批量新增产品版本信息
     * 
     * @param tProdVersionInfoList 产品版本信息列表
     * @return 结果
     */
    public int batchTProdVersionInfo(List<TProdVersionInfo> tProdVersionInfoList);
    

    /**
     * 通过产品信息主键删除产品版本信息信息
     * 
     * @param id 产品信息ID
     * @return 结果
     */
    public int deleteTProdVersionInfoByProdId(Long id);
}
