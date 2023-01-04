package com.highgo.medium.mapper;

import java.util.List;
import com.highgo.medium.domain.MLicenseFileInfo;

/**
 * License文件记录Mapper接口
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public interface MLicenseFileInfoMapper 
{
    /**
     * 查询License文件记录
     * 
     * @param id License文件记录主键
     * @return License文件记录
     */
    public MLicenseFileInfo selectMLicenseFileInfoById(Long id);

    /**
     * 查询License文件记录列表
     * 
     * @param mLicenseFileInfo License文件记录
     * @return License文件记录集合
     */
    public List<MLicenseFileInfo> selectMLicenseFileInfoList(MLicenseFileInfo mLicenseFileInfo);

    /**
     * 新增License文件记录
     * 
     * @param mLicenseFileInfo License文件记录
     * @return 结果
     */
    public int insertMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo);

    /**
     * 修改License文件记录
     * 
     * @param mLicenseFileInfo License文件记录
     * @return 结果
     */
    public int updateMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo);

    /**
     * 删除License文件记录
     * 
     * @param id License文件记录主键
     * @return 结果
     */
    public int deleteMLicenseFileInfoById(Long id);

    /**
     * 批量删除License文件记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMLicenseFileInfoByIds(Long[] ids);

    public List<MLicenseFileInfo> selectMLicenseFileInfoByIds(String ids);
}
