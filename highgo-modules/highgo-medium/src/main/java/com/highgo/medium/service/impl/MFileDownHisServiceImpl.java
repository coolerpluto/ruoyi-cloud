package com.highgo.medium.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MFileDownHisMapper;
import com.highgo.medium.domain.MFileDownHis;
import com.highgo.medium.service.IMFileDownHisService;

/**
 * 下载记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MFileDownHisServiceImpl implements IMFileDownHisService 
{
    @Autowired
    private MFileDownHisMapper mFileDownHisMapper;

    /**
     * 查询下载记录
     * 
     * @param id 下载记录主键
     * @return 下载记录
     */
    @Override
    public MFileDownHis selectMFileDownHisById(Long id)
    {
        return mFileDownHisMapper.selectMFileDownHisById(id);
    }

    /**
     * 查询下载记录列表
     * 
     * @param mFileDownHis 下载记录
     * @return 下载记录
     */
    @Override
    public List<MFileDownHis> selectMFileDownHisList(MFileDownHis mFileDownHis)
    {
        return mFileDownHisMapper.selectMFileDownHisList(mFileDownHis);
    }

    /**
     * 新增下载记录
     * 
     * @param mFileDownHis 下载记录
     * @return 结果
     */
    @Override
    public int insertMFileDownHis(MFileDownHis mFileDownHis)
    {
        mFileDownHis.setCreateTime(DateUtils.getNowDate());
        return mFileDownHisMapper.insertMFileDownHis(mFileDownHis);
    }

    /**
     * 修改下载记录
     * 
     * @param mFileDownHis 下载记录
     * @return 结果
     */
    @Override
    public int updateMFileDownHis(MFileDownHis mFileDownHis)
    {
        mFileDownHis.setUpdateTime(DateUtils.getNowDate());
        return mFileDownHisMapper.updateMFileDownHis(mFileDownHis);
    }

    /**
     * 批量删除下载记录
     * 
     * @param ids 需要删除的下载记录主键
     * @return 结果
     */
    @Override
    public int deleteMFileDownHisByIds(Long[] ids)
    {
        return mFileDownHisMapper.deleteMFileDownHisByIds(ids);
    }

    /**
     * 删除下载记录信息
     * 
     * @param id 下载记录主键
     * @return 结果
     */
    @Override
    public int deleteMFileDownHisById(Long id)
    {
        return mFileDownHisMapper.deleteMFileDownHisById(id);
    }
}
