package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.OpportunityCostMapper;
import com.highgo.crm.domain.OpportunityCost;
import com.highgo.crm.service.IOpportunityCostService;

/**
 * 商机花费Service业务层处理
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@Service
public class OpportunityCostServiceImpl implements IOpportunityCostService 
{
    @Autowired
    private OpportunityCostMapper opportunityCostMapper;

    /**
     * 查询商机花费
     * 
     * @param id 商机花费主键
     * @return 商机花费
     */
    @Override
    public OpportunityCost selectOpportunityCostById(Long id)
    {
        return opportunityCostMapper.selectOpportunityCostById(id);
    }

    /**
     * 查询商机花费列表
     * 
     * @param opportunityCost 商机花费
     * @return 商机花费
     */
    @Override
    public List<OpportunityCost> selectOpportunityCostList(OpportunityCost opportunityCost)
    {
        return opportunityCostMapper.selectOpportunityCostList(opportunityCost);
    }

    /**
     * 新增商机花费
     * 
     * @param opportunityCost 商机花费
     * @return 结果
     */
    @Override
    public int insertOpportunityCost(OpportunityCost opportunityCost)
    {
        opportunityCost.setCreateTime(DateUtils.getNowDate());
        return opportunityCostMapper.insertOpportunityCost(opportunityCost);
    }

    /**
     * 修改商机花费
     * 
     * @param opportunityCost 商机花费
     * @return 结果
     */
    @Override
    public int updateOpportunityCost(OpportunityCost opportunityCost)
    {
        opportunityCost.setUpdateTime(DateUtils.getNowDate());
        return opportunityCostMapper.updateOpportunityCost(opportunityCost);
    }

    /**
     * 批量删除商机花费
     * 
     * @param ids 需要删除的商机花费主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityCostByIds(Long[] ids)
    {
        return opportunityCostMapper.deleteOpportunityCostByIds(ids);
    }

    /**
     * 删除商机花费信息
     * 
     * @param id 商机花费主键
     * @return 结果
     */
    @Override
    public int deleteOpportunityCostById(Long id)
    {
        return opportunityCostMapper.deleteOpportunityCostById(id);
    }
}
