package com.highgo.medium.service;

import java.util.List;
import com.highgo.medium.domain.MFileDownHis;

/**
 * 下载记录Service接口
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public interface IMFileDownHisService 
{
    /**
     * 查询下载记录
     * 
     * @param id 下载记录主键
     * @return 下载记录
     */
    public MFileDownHis selectMFileDownHisById(Long id);

    /**
     * 查询下载记录列表
     * 
     * @param mFileDownHis 下载记录
     * @return 下载记录集合
     */
    public List<MFileDownHis> selectMFileDownHisList(MFileDownHis mFileDownHis);

    /**
     * 新增下载记录
     * 
     * @param mFileDownHis 下载记录
     * @return 结果
     */
    public int insertMFileDownHis(MFileDownHis mFileDownHis);

    /**
     * 修改下载记录
     * 
     * @param mFileDownHis 下载记录
     * @return 结果
     */
    public int updateMFileDownHis(MFileDownHis mFileDownHis);

    /**
     * 批量删除下载记录
     * 
     * @param ids 需要删除的下载记录主键集合
     * @return 结果
     */
    public int deleteMFileDownHisByIds(Long[] ids);

    /**
     * 删除下载记录信息
     * 
     * @param id 下载记录主键
     * @return 结果
     */
    public int deleteMFileDownHisById(Long id);
}
