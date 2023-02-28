package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.OpportunityProperty;

/**
 * 商机属性Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public interface OpportunityPropertyMapper 
{
    /**
     * 查询商机属性
     * 
     * @param id 商机属性主键
     * @return 商机属性
     */
    public OpportunityProperty selectOpportunityPropertyById(Long id);

    /**
     * 查询商机属性列表
     * 
     * @param opportunityProperty 商机属性
     * @return 商机属性集合
     */
    public List<OpportunityProperty> selectOpportunityPropertyList(OpportunityProperty opportunityProperty);

    /**
     * 新增商机属性
     * 
     * @param opportunityProperty 商机属性
     * @return 结果
     */
    public int insertOpportunityProperty(OpportunityProperty opportunityProperty);

    /**
     * 修改商机属性
     * 
     * @param opportunityProperty 商机属性
     * @return 结果
     */
    public int updateOpportunityProperty(OpportunityProperty opportunityProperty);

    /**
     * 删除商机属性
     * 
     * @param id 商机属性主键
     * @return 结果
     */
    public int deleteOpportunityPropertyById(Long id);

    /**
     * 批量删除商机属性
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpportunityPropertyByIds(Long[] ids);
}