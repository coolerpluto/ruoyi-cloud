package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.Opportunity;

/**
 * 商机管理Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityMapper 
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
     * 删除商机管理
     * 
     * @param id 商机管理主键
     * @return 结果
     */
    public int deleteOpportunityById(Long id);

    /**
     * 批量删除商机管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityByIds(Long[] ids);
}
