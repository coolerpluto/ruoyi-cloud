package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.TransferLog;

/**
 * 转移日志Service接口
 * 
 * @author eszhang
 * @date 2023-01-17
 */
public interface ITransferLogService 
{
    /**
     * 查询转移日志
     * 
     * @param id 转移日志主键
     * @return 转移日志
     */
    public TransferLog selectTransferLogById(String id);

    /**
     * 查询转移日志列表
     * 
     * @param transferLog 转移日志
     * @return 转移日志集合
     */
    public List<TransferLog> selectTransferLogList(TransferLog transferLog);

    /**
     * 新增转移日志
     * 
     * @param transferLog 转移日志
     * @return 结果
     */
    public int insertTransferLog(TransferLog transferLog);

    /**
     * 修改转移日志
     * 
     * @param transferLog 转移日志
     * @return 结果
     */
    public int updateTransferLog(TransferLog transferLog);

    /**
     * 批量删除转移日志
     * 
     * @param ids 需要删除的转移日志主键集合
     * @return 结果
     */
    public int deleteTransferLogByIds(String[] ids);

    /**
     * 删除转移日志信息
     * 
     * @param id 转移日志主键
     * @return 结果
     */
    public int deleteTransferLogById(String id);
}
