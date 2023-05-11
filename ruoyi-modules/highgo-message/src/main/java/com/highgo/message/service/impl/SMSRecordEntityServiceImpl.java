package com.highgo.message.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.message.mapper.SMSRecordEntityMapper;
import com.highgo.message.domain.SMSRecordEntity;
import com.highgo.message.service.ISMSRecordEntityService;

/**
 * 短信记录Service业务层处理
 * 
 * @author eszhang
 * @date 2023-05-11
 */
@Service
public class SMSRecordEntityServiceImpl implements ISMSRecordEntityService 
{
    @Autowired
    private SMSRecordEntityMapper sMSRecordEntityMapper;

    /**
     * 查询短信记录
     * 
     * @param id 短信记录主键
     * @return 短信记录
     */
    @Override
    public SMSRecordEntity selectSMSRecordEntityById(Long id)
    {
        return sMSRecordEntityMapper.selectSMSRecordEntityById(id);
    }

    /**
     * 查询短信记录列表
     * 
     * @param sMSRecordEntity 短信记录
     * @return 短信记录
     */
    @Override
    public List<SMSRecordEntity> selectSMSRecordEntityList(SMSRecordEntity sMSRecordEntity)
    {
        return sMSRecordEntityMapper.selectSMSRecordEntityList(sMSRecordEntity);
    }

    /**
     * 新增短信记录
     * 
     * @param sMSRecordEntity 短信记录
     * @return 结果
     */
    @Override
    public int insertSMSRecordEntity(SMSRecordEntity sMSRecordEntity)
    {
        sMSRecordEntity.setCreateTime(DateUtils.getNowDate());
        return sMSRecordEntityMapper.insertSMSRecordEntity(sMSRecordEntity);
    }

    /**
     * 修改短信记录
     * 
     * @param sMSRecordEntity 短信记录
     * @return 结果
     */
    @Override
    public int updateSMSRecordEntity(SMSRecordEntity sMSRecordEntity)
    {
        return sMSRecordEntityMapper.updateSMSRecordEntity(sMSRecordEntity);
    }

    /**
     * 批量删除短信记录
     * 
     * @param ids 需要删除的短信记录主键
     * @return 结果
     */
    @Override
    public int deleteSMSRecordEntityByIds(Long[] ids)
    {
        return sMSRecordEntityMapper.deleteSMSRecordEntityByIds(ids);
    }

    /**
     * 删除短信记录信息
     * 
     * @param id 短信记录主键
     * @return 结果
     */
    @Override
    public int deleteSMSRecordEntityById(Long id)
    {
        return sMSRecordEntityMapper.deleteSMSRecordEntityById(id);
    }
}
