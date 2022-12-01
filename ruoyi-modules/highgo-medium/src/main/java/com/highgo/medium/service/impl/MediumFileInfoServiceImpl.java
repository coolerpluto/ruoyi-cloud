package com.highgo.medium.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MediumFileInfoMapper;
import com.highgo.medium.domain.MediumFileInfo;
import com.highgo.medium.service.IMediumFileInfoService;

/**
 * 介质文件信息记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MediumFileInfoServiceImpl implements IMediumFileInfoService 
{
    @Autowired
    private MediumFileInfoMapper mediumFileInfoMapper;

    /**
     * 查询介质文件信息记录
     * 
     * @param id 介质文件信息记录主键
     * @return 介质文件信息记录
     */
    @Override
    public MediumFileInfo selectMediumFileInfoById(Long id)
    {
        return mediumFileInfoMapper.selectMediumFileInfoById(id);
    }

    /**
     * 查询介质文件信息记录列表
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 介质文件信息记录
     */
    @Override
    public List<MediumFileInfo> selectMediumFileInfoList(MediumFileInfo mediumFileInfo)
    {
        return mediumFileInfoMapper.selectMediumFileInfoList(mediumFileInfo);
    }

    /**
     * 新增介质文件信息记录
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 结果
     */
    @Override
    public int insertMediumFileInfo(MediumFileInfo mediumFileInfo)
    {
        mediumFileInfo.setCreateTime(DateUtils.getNowDate());
        return mediumFileInfoMapper.insertMediumFileInfo(mediumFileInfo);
    }

    /**
     * 修改介质文件信息记录
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 结果
     */
    @Override
    public int updateMediumFileInfo(MediumFileInfo mediumFileInfo)
    {
        mediumFileInfo.setUpdateTime(DateUtils.getNowDate());
        return mediumFileInfoMapper.updateMediumFileInfo(mediumFileInfo);
    }

    /**
     * 批量删除介质文件信息记录
     * 
     * @param ids 需要删除的介质文件信息记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumFileInfoByIds(Long[] ids)
    {
        return mediumFileInfoMapper.deleteMediumFileInfoByIds(ids);
    }

    /**
     * 删除介质文件信息记录信息
     * 
     * @param id 介质文件信息记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumFileInfoById(Long id)
    {
        return mediumFileInfoMapper.deleteMediumFileInfoById(id);
    }
}
