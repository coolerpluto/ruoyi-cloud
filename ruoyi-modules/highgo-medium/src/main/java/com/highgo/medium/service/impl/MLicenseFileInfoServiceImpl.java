package com.highgo.medium.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MLicenseFileInfoMapper;
import com.highgo.medium.domain.MLicenseFileInfo;
import com.highgo.medium.service.IMLicenseFileInfoService;

/**
 * License文件记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MLicenseFileInfoServiceImpl implements IMLicenseFileInfoService 
{
    @Autowired
    private MLicenseFileInfoMapper mLicenseFileInfoMapper;

    /**
     * 查询License文件记录
     * 
     * @param id License文件记录主键
     * @return License文件记录
     */
    @Override
    public MLicenseFileInfo selectMLicenseFileInfoById(Long id)
    {
        return mLicenseFileInfoMapper.selectMLicenseFileInfoById(id);
    }

    /**
     * 查询License文件记录列表
     * 
     * @param mLicenseFileInfo License文件记录
     * @return License文件记录
     */
    @Override
    public List<MLicenseFileInfo> selectMLicenseFileInfoList(MLicenseFileInfo mLicenseFileInfo)
    {
        return mLicenseFileInfoMapper.selectMLicenseFileInfoList(mLicenseFileInfo);
    }

    /**
     * 新增License文件记录
     * 
     * @param mLicenseFileInfo License文件记录
     * @return 结果
     */
    @Override
    public int insertMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo)
    {
        mLicenseFileInfo.setCreateTime(DateUtils.getNowDate());
        return mLicenseFileInfoMapper.insertMLicenseFileInfo(mLicenseFileInfo);
    }

    /**
     * 修改License文件记录
     * 
     * @param mLicenseFileInfo License文件记录
     * @return 结果
     */
    @Override
    public int updateMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo)
    {
        mLicenseFileInfo.setUpdateTime(DateUtils.getNowDate());
        return mLicenseFileInfoMapper.updateMLicenseFileInfo(mLicenseFileInfo);
    }

    /**
     * 批量删除License文件记录
     * 
     * @param ids 需要删除的License文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMLicenseFileInfoByIds(Long[] ids)
    {
        return mLicenseFileInfoMapper.deleteMLicenseFileInfoByIds(ids);
    }

    /**
     * 删除License文件记录信息
     * 
     * @param id License文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMLicenseFileInfoById(Long id)
    {
        return mLicenseFileInfoMapper.deleteMLicenseFileInfoById(id);
    }
}
