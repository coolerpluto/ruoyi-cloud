package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.PolicyFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 政策文件Service接口
 *
 * @author eszhang
 * @date 2023-01-19
 */
public interface IPolicyFileService
{
    /**
     * 查询政策文件
     *
     * @param id 政策文件主键
     * @return 政策文件
     */
    public PolicyFile selectPolicyFileById(Integer id);

    /**
     * 查询政策文件列表
     *
     * @param policyFile 政策文件
     * @return 政策文件集合
     */
    public List<PolicyFile> selectPolicyFileList(PolicyFile policyFile);

    /**
     * 新增政策文件
     *
     * @param policyFile 政策文件
     * @return 结果
     */
    public int insertPolicyFile(PolicyFile policyFile);

    /**
     * 修改政策文件
     *
     * @param policyFile 政策文件
     * @return 结果
     */
    public int updatePolicyFile(PolicyFile policyFile);

    /**
     * 批量删除政策文件
     *
     * @param ids 需要删除的政策文件主键集合
     * @return 结果
     */
    public int deletePolicyFileByIds(Integer[] ids);

    /**
     * 删除政策文件信息
     *
     * @param id 政策文件主键
     * @return 结果
     */
    public int deletePolicyFileById(Integer id);

    int updatePolicyFileWithFile(PolicyFile policyFile, MultipartFile policyFileFile);

    int insertPolicyFileWithFile(PolicyFile policyFile, MultipartFile policyFileFile);

    void download(HttpServletResponse response, PolicyFile policyFile);
}
