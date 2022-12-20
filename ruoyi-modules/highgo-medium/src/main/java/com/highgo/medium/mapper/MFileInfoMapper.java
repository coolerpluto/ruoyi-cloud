package com.highgo.medium.mapper;

import com.highgo.medium.domain.MFileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文件记录Mapper接口
 *
 * @author eshzhang
 * @date 2022-12-01
 */
public interface MFileInfoMapper {
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
    List<MFileInfo> selectMFileInfoByIds(@Param("fileIds") List<String> fileIds);

    /**
     * 通过介质的id查询
     */
    List<MFileInfo> getMFileInfoByMediumIds(@Param("mediumIds") List<String> mediumIds);

    /**
     * 通过介质附件的id查询
     */
    List<MFileInfo> getMFileInfoBySecurityIds(@Param("securityIds") List<String> securityIds);

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
