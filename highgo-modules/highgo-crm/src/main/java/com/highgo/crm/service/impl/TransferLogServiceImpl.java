package com.highgo.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.TransferLogMapper;
import com.highgo.crm.domain.TransferLog;
import com.highgo.crm.service.ITransferLogService;

/**
 * 转移日志Service业务层处理
 * 
 * @author eszhang
 * @date 2023-01-17
 */
@Service
public class TransferLogServiceImpl implements ITransferLogService 
{
    @Autowired
    private TransferLogMapper transferLogMapper;

    /**
     * 查询转移日志
     * 
     * @param id 转移日志主键
     * @return 转移日志
     */
    @Override
    public TransferLog selectTransferLogById(String id)
    {
        return transferLogMapper.selectTransferLogById(id);
    }

    /**
     * 查询转移日志列表
     * 
     * @param transferLog 转移日志
     * @return 转移日志
     */
    @Override
    public List<TransferLog> selectTransferLogList(TransferLog transferLog)
    {
        return transferLogMapper.selectTransferLogList(transferLog);
    }

    /**
     * 新增转移日志
     * 
     * @param transferLog 转移日志
     * @return 结果
     */
    @Override
    public int insertTransferLog(TransferLog transferLog)
    {
        return transferLogMapper.insertTransferLog(transferLog);
    }

    /**
     * 修改转移日志
     * 
     * @param transferLog 转移日志
     * @return 结果
     */
    @Override
    public int updateTransferLog(TransferLog transferLog)
    {
        return transferLogMapper.updateTransferLog(transferLog);
    }

    /**
     * 批量删除转移日志
     * 
     * @param ids 需要删除的转移日志主键
     * @return 结果
     */
    @Override
    public int deleteTransferLogByIds(String[] ids)
    {
        return transferLogMapper.deleteTransferLogByIds(ids);
    }

    /**
     * 删除转移日志信息
     * 
     * @param id 转移日志主键
     * @return 结果
     */
    @Override
    public int deleteTransferLogById(String id)
    {
        return transferLogMapper.deleteTransferLogById(id);
    }
}
