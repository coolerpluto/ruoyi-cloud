package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.OpportunityCost;

/**
 * 商机花费Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunityCostService 
{
    /**
     * 查询商机花费
     * 
     * @param id 商机花费主键
     * @return 商机花费
     */
    public OpportunityCost selectOpportunityCostById(Long id);

    /**
     * 查询商机花费列表
     * 
     * @param opportunityCost 商机花费
     * @return 商机花费集合
     */
    public List<OpportunityCost> selectOpportunityCostList(OpportunityCost opportunityCost);

    /**
     * 新增商机花费
     * 
     * @param opportunityCost 商机花费
     * @return 结果
     */
    public int insertOpportunityCost(OpportunityCost opportunityCost);

    /**
     * 修改商机花费
     * 
     * @param opportunityCost 商机花费
     * @return 结果
     */
    public int updateOpportunityCost(OpportunityCost opportunityCost);

    /**
     * 批量删除商机花费
     * 
     * @param ids 需要删除的商机花费主键集合
     * @return 结果
     */
    public int deleteOpportunityCostByIds(Long[] ids);

    /**
     * 删除商机花费信息
     * 
     * @param id 商机花费主键
     * @return 结果
     */
    public int deleteOpportunityCostById(Long id);
}
