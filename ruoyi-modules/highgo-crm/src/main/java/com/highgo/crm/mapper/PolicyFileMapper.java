package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.PolicyFile;

/**
 * 政策文件Mapper接口
 *
 * @author eszhang
 * @date 2023-01-19
 */
public interface PolicyFileMapper
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
     * 删除政策文件
     *
     * @param id 政策文件主键
     * @return 结果
     */
    public int deletePolicyFileById(Integer id);

    /**
     * 批量删除政策文件
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePolicyFileByIds(Integer[] ids);
}
