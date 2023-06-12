package com.ruoyi.gen.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.ruoyi.common.core.enums.DataSourceType;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.gen.config.datasource.DynamicDataSourceUtil;
import com.ruoyi.gen.domain.GenDbSource;
import com.ruoyi.gen.mapper.GenDbSourceMapper;
import com.ruoyi.gen.service.IGenDbSourceService;
import com.ruoyi.gen.util.DataSourceComposeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代码生成数据源配置Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-09
 */
@Service
public class GenDbSourceServiceImpl implements IGenDbSourceService
{
    @Autowired
    private GenDbSourceMapper genDbSourceMapper;

    /**
     * 查询代码生成数据源配置
     *
     * @param id 代码生成数据源配置主键
     * @return 代码生成数据源配置
     */
    @Override
    public GenDbSource selectGenDbSourceById(Long id)
    {
        return genDbSourceMapper.selectGenDbSourceById(id);
    }

    /**
     * 查询代码生成数据源配置列表
     *
     * @param genDbSource 代码生成数据源配置
     * @return 代码生成数据源配置
     */
    @Override
    public List<GenDbSource> selectGenDbSourceList(GenDbSource genDbSource)
    {
        return genDbSourceMapper.selectGenDbSourceList(genDbSource);
    }

    /**
     * 新增代码生成数据源配置
     *
     * @param genDbSource 代码生成数据源配置
     * @return 结果
     */
    @Override
    public int insertGenDbSource(GenDbSource genDbSource)
    {
        genDbSource.setCreateTime(DateUtils.getNowDate());
        genDbSource.setCreateBy(SecurityUtils.getUsername());
        int rows = genDbSourceMapper.insertGenDbSource(genDbSource);
        if (rows > 0)
        {
            DruidDataSource druidDataSource = DataSourceComposeUtils.composeDruidDataSource(genDbSource);
            DynamicDataSourceUtil.addTargetDataSource(DataSourceType.SLAVE.name() + Convert.toChar(genDbSource.getId()), druidDataSource);
            DynamicDataSourceUtil.flushDataSource();
        }
        return rows;
    }

    /**
     * 修改代码生成数据源配置
     *
     * @param genDbSource 代码生成数据源配置
     * @return 结果
     */
    @Override
    public int updateGenDbSource(GenDbSource genDbSource)
    {
        genDbSource.setUpdateTime(DateUtils.getNowDate());
        genDbSource.setUpdateBy(SecurityUtils.getUsername());
        int rows = genDbSourceMapper.updateGenDbSource(genDbSource);
        if (rows > 0)
        {
            DruidDataSource druidDataSource = DataSourceComposeUtils.composeDruidDataSource(genDbSource);
            DynamicDataSourceUtil.replaceTargetDataSource(DataSourceType.SLAVE.name() + Convert.toChar(genDbSource.getId()), druidDataSource);
            DynamicDataSourceUtil.flushDataSource();
        }
        return rows;
    }

    /**
     * 批量删除代码生成数据源配置
     *
     * @param ids 需要删除的代码生成数据源配置主键
     * @return 结果
     */
    @Override
    public int deleteGenDbSourceByIds(Long[] ids)
    {
        int rows = genDbSourceMapper.deleteGenDbSourceByIds(ids);
        if (rows > 0)
        {
            for (Long id : ids)
            {
                DynamicDataSourceUtil.deleteTargetDataSource(DataSourceType.SLAVE.name() + Convert.toChar(id));
            }
            DynamicDataSourceUtil.flushDataSource();
        }
        return rows;
    }

    /**
     * 删除代码生成数据源配置信息
     *
     * @param id 代码生成数据源配置主键
     * @return 结果
     */
    @Override
    public int deleteGenDbSourceById(Long id)
    {
        int rows = genDbSourceMapper.deleteGenDbSourceById(id);
        if (rows > 0)
        {
            DynamicDataSourceUtil.deleteTargetDataSource(DataSourceType.SLAVE.name() + Convert.toChar(id));
            DynamicDataSourceUtil.flushDataSource();
        }
        return rows;
    }
}
