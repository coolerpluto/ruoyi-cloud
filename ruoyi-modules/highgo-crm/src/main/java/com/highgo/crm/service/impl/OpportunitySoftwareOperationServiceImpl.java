package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunitySoftwareOperationMapper;
import com.highgo.crm.domain.OpportunitySoftwareOperation;
import com.highgo.crm.service.IOpportunitySoftwareOperationService;

/**
 * 商机应用运营信息Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunitySoftwareOperationServiceImpl implements IOpportunitySoftwareOperationService 
{
    @Autowired
    private OpportunitySoftwareOperationMapper opportunitySoftwareOperationMapper;

    /**
     * 查询商机应用运营信息
     * 
     * @param id 商机应用运营信息主键
     * @return 商机应用运营信息
     */
    @Override
    public OpportunitySoftwareOperation selectOpportunitySoftwareOperationById(Long id)
    {
        return opportunitySoftwareOperationMapper.selectOpportunitySoftwareOperationById(id);
    }

    /**
     * 查询商机应用运营信息列表
     * 
     * @param opportunitySoftwareOperation 商机应用运营信息
     * @return 商机应用运营信息
     */
    @Override
    public List<OpportunitySoftwareOperation> selectOpportunitySoftwareOperationList(OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        return opportunitySoftwareOperationMapper.selectOpportunitySoftwareOperationList(opportunitySoftwareOperation);
    }

    /**
     * 新增商机应用运营信息
     * 
     * @param opportunitySoftwareOperation 商机应用运营信息
     * @return 结果
     */
    @Override
    public int insertOpportunitySoftwareOperation(OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        opportunitySoftwareOperation.setCreateTime(DateUtils.getNowDate());
        return opportunitySoftwareOperationMapper.insertOpportunitySoftwareOperation(opportunitySoftwareOperation);
    }

    /**
     * 修改商机应用运营信息
     * 
     * @param opportunitySoftwareOperation 商机应用运营信息
     * @return 结果
     */
    @Override
    public int updateOpportunitySoftwareOperation(OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        opportunitySoftwareOperation.setUpdateTime(DateUtils.getNowDate());
        return opportunitySoftwareOperationMapper.updateOpportunitySoftwareOperation(opportunitySoftwareOperation);
    }

    /**
     * 批量删除商机应用运营信息
     * 
     * @param ids 需要删除的商机应用运营信息主键
     * @return 结果
     */
    @Override
    public int deleteOpportunitySoftwareOperationByIds(Long[] ids)
    {
        return opportunitySoftwareOperationMapper.deleteOpportunitySoftwareOperationByIds(ids);
    }

    /**
     * 删除商机应用运营信息信息
     * 
     * @param id 商机应用运营信息主键
     * @return 结果
     */
    @Override
    public int deleteOpportunitySoftwareOperationById(Long id)
    {
        return opportunitySoftwareOperationMapper.deleteOpportunitySoftwareOperationById(id);
    }
}
