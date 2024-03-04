package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunitySoftwareOperation;

/**
 * 商机应用运营信息Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunitySoftwareOperationService 
{
    /**
     * 查询商机应用运营信息
     * 
     * @param id 商机应用运营信息主键
     * @return 商机应用运营信息
     */
    public OpportunitySoftwareOperation selectOpportunitySoftwareOperationById(Long id);

    /**
     * 查询商机应用运营信息列表
     * 
     * @param opportunitySoftwareOperation 商机应用运营信息
     * @return 商机应用运营信息集合
     */
    public List<OpportunitySoftwareOperation> selectOpportunitySoftwareOperationList(OpportunitySoftwareOperation opportunitySoftwareOperation);

    /**
     * 新增商机应用运营信息
     * 
     * @param opportunitySoftwareOperation 商机应用运营信息
     * @return 结果
     */
    public int insertOpportunitySoftwareOperation(OpportunitySoftwareOperation opportunitySoftwareOperation);

    /**
     * 修改商机应用运营信息
     * 
     * @param opportunitySoftwareOperation 商机应用运营信息
     * @return 结果
     */
    public int updateOpportunitySoftwareOperation(OpportunitySoftwareOperation opportunitySoftwareOperation);

    /**
     * 批量删除商机应用运营信息
     * 
     * @param ids 需要删除的商机应用运营信息主键集合
     * @return 结果
     */
    public int deleteOpportunitySoftwareOperationByIds(Long[] ids);

    /**
     * 删除商机应用运营信息信息
     * 
     * @param id 商机应用运营信息主键
     * @return 结果
     */
    public int deleteOpportunitySoftwareOperationById(Long id);
}
