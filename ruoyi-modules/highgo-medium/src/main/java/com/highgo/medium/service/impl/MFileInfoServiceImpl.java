package com.highgo.medium.service.impl;

import java.util.List;

import com.highgo.medium.utils.MediumUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MFileInfoMapper;
import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.service.IMFileInfoService;

/**
 * 文件记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MFileInfoServiceImpl implements IMFileInfoService 
{
    @Autowired
    private MFileInfoMapper mFileInfoMapper;

    /**
     * 查询文件记录
     * 
     * @param id 文件记录主键
     * @return 文件记录
     */
    @Override
    public MFileInfo selectMFileInfoById(Long id)
    {
        return mFileInfoMapper.selectMFileInfoById(id);
    }

    /**
     * 查询文件记录列表
     * 
     * @param mFileInfo 文件记录
     * @return 文件记录
     */
    @Override
    public List<MFileInfo> selectMFileInfoList(MFileInfo mFileInfo)
    {
        return mFileInfoMapper.selectMFileInfoList(mFileInfo);
    }

    /**
     * 新增文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    @Override
    public int insertMFileInfo(MFileInfo mFileInfo)
    {
        mFileInfo.setCreateTime(DateUtils.getNowDate());
        mFileInfo.setCreateBy(SecurityUtils.getUsername());
        mFileInfo.setCreateId(SecurityUtils.getUserId());
        return mFileInfoMapper.insertMFileInfo(mFileInfo);
    }

    @Override
    public int insertMFileInfoBath(List<MFileInfo> mFileInfos) {
        int res = 0;
        for (MFileInfo mFileInfo:mFileInfos ) {
            if (StringUtils.isBlank(mFileInfo.getId())) {
                mFileInfo.setId(MediumUtil.getId());
            }
            mFileInfo.setCreateTime(DateUtils.getNowDate());
            mFileInfo.setCreateBy(SecurityUtils.getUsername());
            mFileInfo.setCreateId(SecurityUtils.getUserId());
            res=res+mFileInfoMapper.insertMFileInfo(mFileInfo);
        }
        return res;
    }

    /**
     * 修改文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    @Override
    public int updateMFileInfo(MFileInfo mFileInfo)
    {
        mFileInfo.setUpdateTime(DateUtils.getNowDate());
        mFileInfo.setUpdateBy(SecurityUtils.getUsername());
        mFileInfo.setUpdateId(SecurityUtils.getUserId());
        return mFileInfoMapper.updateMFileInfo(mFileInfo);
    }

    /**
     * 批量删除文件记录
     * 
     * @param ids 需要删除的文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMFileInfoByIds(Long[] ids)
    {
        return mFileInfoMapper.deleteMFileInfoByIds(ids);
    }

    /**
     * 删除文件记录信息
     * 
     * @param id 文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMFileInfoById(Long id)
    {
        return mFileInfoMapper.deleteMFileInfoById(id);
    }
}
