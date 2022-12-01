package com.highgo.medium.mapper;

import java.util.List;
import com.highgo.medium.domain.MFileInfo;

/**
 * 文件记录Mapper接口
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public interface MFileInfoMapper 
{
    /**
     * 查询文件记录
     * 
     * @param id 文件记录主键
     * @return 文件记录
     */
    public MFileInfo selectMFileInfoById(Long id);

    /**
     * 查询文件记录列表
     * 
     * @param mFileInfo 文件记录
     * @return 文件记录集合
     */
    public List<MFileInfo> selectMFileInfoList(MFileInfo mFileInfo);

    /**
     * 新增文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    public int insertMFileInfo(MFileInfo mFileInfo);

    /**
     * 修改文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    public int updateMFileInfo(MFileInfo mFileInfo);

    /**
     * 删除文件记录
     * 
     * @param id 文件记录主键
     * @return 结果
     */
    public int deleteMFileInfoById(Long id);

    /**
     * 批量删除文件记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMFileInfoByIds(Long[] ids);
}
