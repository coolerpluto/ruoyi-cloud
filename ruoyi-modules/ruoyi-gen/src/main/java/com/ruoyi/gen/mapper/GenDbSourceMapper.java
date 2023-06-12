package com.ruoyi.gen.mapper;

import java.util.List;
import com.ruoyi.gen.domain.GenDbSource;
import com.ruoyi.gen.domain.GenTable;
import com.ruoyi.gen.domain.GenTableColumn;
import org.apache.ibatis.annotations.Param;

/**
 * 代码生成数据源配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
public interface GenDbSourceMapper 
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
     * 删除代码生成数据源配置
     * 
     * @param id 代码生成数据源配置主键
     * @return 结果
     */
    public int deleteGenDbSourceById(Long id);

    /**
     * 批量删除代码生成数据源配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGenDbSourceByIds(Long[] ids);

    List<GenTable> selectDbTableList(@Param("genTable") GenTable genTable, @Param("dbType")String dbType);

    List<GenTable> selectDbTableListByNames(@Param("tableNames")String[] tableNames, @Param("dbType")String dbType);

    List<GenTableColumn> selectDbTableColumnsByName(@Param("tableName")String tableName,  @Param("dbType")String dbType);
}