package com.highgo.crm.service;

import com.highgo.crm.domain.OpportunityUnited;

import java.util.List;

/**
 * 商机管理Service接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface IOpportunityUnitedService
{
    /**
     * 查询商机管理
     * 
     * @param id 商机管理主键
     * @return 商机管理
     */
    public OpportunityUnited selectOpportunityUnitedById(Long id);

    /**
     * 查询商机管理列表
     * 
     * @param opportunity 商机管理
     * @return 商机管理集合
     */
    public List<OpportunityUnited> selectOpportunityUnitedList(OpportunityUnited opportunity);

    /**
     * 新增商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int insertOpportunityUnited(OpportunityUnited opportunity);

    /**
     * 修改商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int updateOpportunityUnited(OpportunityUnited opportunity);

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的商机管理主键集合
     * @return 结果
     */
    public int deleteOpportunityUnitedByIds(Long[] ids);

    /**
     * 删除商机管理信息
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    public int deleteOpportunityUnitedById(Long id);
}
