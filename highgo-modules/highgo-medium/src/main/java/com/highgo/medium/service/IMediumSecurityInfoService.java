package com.highgo.medium.service;

import java.util.List;
import com.highgo.medium.domain.MediumSecurityInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 介质安全文件记录Service接口
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public interface IMediumSecurityInfoService 
{
    /**
     * 查询介质安全文件记录
     * 
     * @param id 介质安全文件记录主键
     * @return 介质安全文件记录
     */
    public MediumSecurityInfo selectMediumSecurityInfoById(Long id);

    /**
     * 查询介质安全文件记录列表
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 介质安全文件记录集合
     */
    public List<MediumSecurityInfo> selectMediumSecurityInfoList(MediumSecurityInfo mediumSecurityInfo);

    /**
     * 新增介质安全文件记录
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 结果
     */
    public int insertMediumSecurityInfo(MediumSecurityInfo mediumSecurityInfo);

    /**
     * 修改介质安全文件记录
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 结果
     */
    public int updateMediumSecurityInfo(MediumSecurityInfo mediumSecurityInfo);

    /**
     * 批量删除介质安全文件记录
     * 
     * @param ids 需要删除的介质安全文件记录主键集合
     * @return 结果
     */
    public int deleteMediumSecurityInfoByIds(Long[] ids);

    /**
     * 删除介质安全文件记录信息
     * 
     * @param id 介质安全文件记录主键
     * @return 结果
     */
    public int deleteMediumSecurityInfoById(Long id);

    int insertMediumSecurityWithFile(MediumSecurityInfo mediumSecurityInfo, MultipartFile file, MultipartFile fileMd5);

    int updateMediumSecurityWithFile(MediumSecurityInfo mediumSecurityInfo, MultipartFile file, MultipartFile fileMd5);

    void download(HttpServletResponse response, MediumSecurityInfo mediumSecurityInfo);
}
