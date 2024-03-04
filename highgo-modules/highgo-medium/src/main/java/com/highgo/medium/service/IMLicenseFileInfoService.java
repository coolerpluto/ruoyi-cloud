package com.highgo.medium.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.highgo.medium.domain.MLicenseFileInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * License文件记录Service接口
 *
 * @author eshzhang
 * @date 2022-12-01
 */
public interface IMLicenseFileInfoService
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
     * 批量删除License文件记录
     *
     * @param ids 需要删除的License文件记录主键集合
     * @return 结果
     */
    public int deleteMLicenseFileInfoByIds(Long[] ids);

    /**
     * 删除License文件记录信息
     *
     * @param id License文件记录主键
     * @return 结果
     */
    public int deleteMLicenseFileInfoById(Long id);

    void download(HttpServletResponse response, String licenseId);

    Map<String, Object> generator(JSONObject json);

    Map<String, Object> generatorHGDB(JSONObject json);

    Map<String, Object> generatorHGDW(JSONObject json);

    Map<String, Object> generatorV9(JSONObject json);
}
