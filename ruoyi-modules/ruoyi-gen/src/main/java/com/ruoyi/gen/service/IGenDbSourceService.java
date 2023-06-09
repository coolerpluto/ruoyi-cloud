package com.ruoyi.gen.service;

import java.util.List;
import com.ruoyi.gen.domain.GenDbSource;

/**
 * 代码生成数据源配置Service接口
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
public interface IGenDbSourceService 
{
    /**
     * 查询代码生成数据源配置
     * 
     * @param id 代码生成数据源配置主键
     * @return 代码生成数据源配置
     */
    public GenDbSource selectGenDbSourceById(Long id);

    /**
     * 查询代码生成数据源配置列表
     * 
     * @param genDbSource 代码生成数据源配置
     * @return 代码生成数据源配置集合
     */
    public List<GenDbSource> selectGenDbSourceList(GenDbSource genDbSource);

    /**
     * 新增代码生成数据源配置
     * 
     * @param genDbSource 代码生成数据源配置
     * @return 结果
     */
    public int insertGenDbSource(GenDbSource genDbSource);

    /**
     * 修改代码生成数据源配置
     * 
     * @param genDbSource 代码生成数据源配置
     * @return 结果
     */
    public int updateGenDbSource(GenDbSource genDbSource);

    /**
     * 批量删除代码生成数据源配置
     * 
     * @param ids 需要删除的代码生成数据源配置主键集合
     * @return 结果
     */
    public int deleteGenDbSourceByIds(Long[] ids);

    /**
     * 删除代码生成数据源配置信息
     * 
     * @param id 代码生成数据源配置主键
     * @return 结果
     */
    public int deleteGenDbSourceById(Long id);
}
