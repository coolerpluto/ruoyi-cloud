package com.highgo.medium.service;

import java.util.List;
import com.highgo.medium.domain.MediumFileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 介质文件信息记录Service接口
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public interface IMediumFileInfoService 
{
    /**
     * 查询介质文件信息记录
     * 
     * @param id 介质文件信息记录主键
     * @return 介质文件信息记录
     */
    public MediumFileInfo selectMediumFileInfoById(Long id);

    /**
     * 查询介质文件信息记录列表
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 介质文件信息记录集合
     */
    public List<MediumFileInfo> selectMediumFileInfoList(MediumFileInfo mediumFileInfo);

    /**
     * 新增介质文件信息记录
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 结果
     */
    public int insertMediumFileInfo(MediumFileInfo mediumFileInfo);

    /**
     * 修改介质文件信息记录
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 结果
     */
    public int updateMediumFileInfo(MediumFileInfo mediumFileInfo);

    /**
     * 批量删除介质文件信息记录
     * 
     * @param ids 需要删除的介质文件信息记录主键集合
     * @return 结果
     */
    public int deleteMediumFileInfoByIds(Long[] ids);

    /**
     * 删除介质文件信息记录信息
     * 
     * @param id 介质文件信息记录主键
     * @return 结果
     */
    public int deleteMediumFileInfoById(Long id);

    int updateMediumFileInfoWithFile(MediumFileInfo mediumFileInfo, MultipartFile mediumFile, MultipartFile mediumFileMd5);

    int insertMediumFileInfoWithFile(MediumFileInfo mediumFileInfo, MultipartFile mediumFile, MultipartFile mediumFileMd5);

    void download(HttpServletResponse response, MediumFileInfo mediumFileInfo);
}
