package com.highgo.medium.service;

import java.util.List;
import java.util.Map;

import com.highgo.medium.domain.MFileInfo;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件记录Service接口
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public interface IMFileInfoService 
{
    /**
     * 查询文件记录
     * 
     * @param id 文件记录主键
     * @return 文件记录
     */
    public MFileInfo selectMFileInfoById(Long id);

    /**
     * 通过表id查询 该id在另个一个表中为要给外部连接
     */
    public List<MFileInfo> selectMFileInfoByIds(List<String> ids);

    /**
     * 通过介质的id查询
     */
    List<MFileInfo> getMFileInfoByMediumIds(List<String> mediumIds);

    /**
     * 通过介质附件的id查询
     */
    List<MFileInfo> getMFileInfoBySecurityIds(List<String> securityIds);

    /**
     * 查询文件的关联状态
     */
    List<Map<String,Object>>getMFileAssociationStatus(Long id);
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
    public int insertMFileInfoBath(List<MFileInfo> mFileInfos);

    /**
     * 修改文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    public int updateMFileInfo(MFileInfo mFileInfo);

    /**
     * 批量删除文件记录
     * 
     * @param ids 需要删除的文件记录主键集合
     * @return 结果
     */
    public int deleteMFileInfoByIds(Long[] ids);

    /**
     * 删除文件记录信息
     * 
     * @param id 文件记录主键
     * @return 结果
     */
    public int deleteMFileInfoById(Long id);

    void download(HttpServletResponse response, MFileInfo mFileInfo);
}
