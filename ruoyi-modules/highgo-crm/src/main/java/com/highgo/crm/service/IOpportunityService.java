package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.Opportunity;

/**
 * 商机管理Service接口
 * 
 * @author eszhang
 * @date 2023-02-13
 */
public interface IOpportunityService 
{
    /**
     * 查询商机管理
     * 
     * @param id 商机管理主键
     * @return 商机管理
     */
    public Opportunity selectOpportunityById(Long id);

    /**
     * 查询商机管理列表
     * 
     * @param opportunity 商机管理
     * @return 商机管理集合
     */
    public List<Opportunity> selectOpportunityList(Opportunity opportunity);

    /**
     * 新增商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int insertOpportunity(Opportunity opportunity);

    /**
     * 修改商机管理
     * 
     * @param opportunity 商机管理
     * @return 结果
     */
    public int updateOpportunity(Opportunity opportunity);

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的商机管理主键集合
     * @return 结果
     */
    public int deleteOpportunityByIds(Long[] ids);

    /**
     * 删除商机管理信息
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    public int deleteOpportunityById(Long id);
}
