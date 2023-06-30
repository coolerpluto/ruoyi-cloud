package com.highgo.medium.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.highgo.medium.domain.TProdVersionInfo;
import com.highgo.medium.mapper.TProdInfoMapper;
import com.highgo.medium.domain.TProdInfo;
import com.highgo.medium.service.ITProdInfoService;

/**
 * 产品信息Service业务层处理
 * 
 * @author esz
 * @date 2023-06-28
 */
@Service
public class TProdInfoServiceImpl implements ITProdInfoService 
{
    @Autowired
    private TProdInfoMapper tProdInfoMapper;

    /**
     * 查询产品信息
     * 
     * @param id 产品信息主键
     * @return 产品信息
     */
    @Override
    public TProdInfo selectTProdInfoById(Long id)
    {
        return tProdInfoMapper.selectTProdInfoById(id);
    }

    /**
     * 查询产品信息列表
     * 
     * @param tProdInfo 产品信息
     * @return 产品信息
     */
    @Override
    public List<TProdInfo> selectTProdInfoList(TProdInfo tProdInfo)
    {
        return tProdInfoMapper.selectTProdInfoList(tProdInfo);
    }

    /**
     * 新增产品信息
     * 
     * @param tProdInfo 产品信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTProdInfo(TProdInfo tProdInfo)
    {
        tProdInfo.setCreateTime(DateUtils.getNowDate());
        tProdInfo.setCreateBy(SecurityUtils.getUsername());
        tProdInfo.setCreateId(SecurityUtils.getUserId());
        int rows = tProdInfoMapper.insertTProdInfo(tProdInfo);
        insertTProdVersionInfo(tProdInfo);
        return rows;
    }

    /**
     * 修改产品信息
     * 
     * @param tProdInfo 产品信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTProdInfo(TProdInfo tProdInfo)
    {
        tProdInfo.setUpdateTime(DateUtils.getNowDate());
        tProdInfo.setUpdateBy(SecurityUtils.getUsername());
        tProdInfo.setUpdateId(SecurityUtils.getUserId());
        tProdInfoMapper.deleteTProdVersionInfoByProdId(tProdInfo.getId());
        insertTProdVersionInfo(tProdInfo);
        return tProdInfoMapper.updateTProdInfo(tProdInfo);
    }

    /**
     * 批量删除产品信息
     * 
     * @param ids 需要删除的产品信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTProdInfoByIds(Long[] ids)
    {
        tProdInfoMapper.deleteTProdVersionInfoByProdIds(ids);
        return tProdInfoMapper.deleteTProdInfoByIds(ids);
    }

    /**
     * 删除产品信息信息
     * 
     * @param id 产品信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTProdInfoById(Long id)
    {
        tProdInfoMapper.deleteTProdVersionInfoByProdId(id);
        return tProdInfoMapper.deleteTProdInfoById(id);
    }

    /**
     * 新增产品版本信息信息
     * 
     * @param tProdInfo 产品信息对象
     */
    public void insertTProdVersionInfo(TProdInfo tProdInfo)
    {
        List<TProdVersionInfo> tProdVersionInfoList = tProdInfo.getTProdVersionInfoList();
        Long id = tProdInfo.getId();
        if (StringUtils.isNotNull(tProdVersionInfoList))
        {
            List<TProdVersionInfo> list = new ArrayList<TProdVersionInfo>();
            for (TProdVersionInfo tProdVersionInfo : tProdVersionInfoList)
            {
                // 同步记录时间和操作员
                tProdVersionInfo.setCreateTime(tProdInfo.getCreateTime());
                tProdVersionInfo.setCreateBy(tProdInfo.getCreateBy());
                tProdVersionInfo.setCreateId(tProdInfo.getCreateId());
                tProdVersionInfo.setUpdateTime(tProdInfo.getUpdateTime());
                tProdVersionInfo.setUpdateBy(tProdInfo.getUpdateBy());
                tProdVersionInfo.setUpdateId(tProdInfo.getUpdateId());

                tProdVersionInfo.setProdId(id);
                list.add(tProdVersionInfo);
            }
            if (list.size() > 0)
            {
                tProdInfoMapper.batchTProdVersionInfo(list);
            }
        }
    }
}
