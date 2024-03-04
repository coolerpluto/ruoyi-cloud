package com.highgo.message.mapper;

import java.util.List;
import com.highgo.message.domain.SMSRecordEntity;

/**
 * 短信记录Mapper接口
 * 
 * @author eszhang
 * @date 2023-05-11
 */
public interface SMSRecordEntityMapper 
{
    /**
     * 查询短信记录
     * 
     * @param id 短信记录主键
     * @return 短信记录
     */
    public SMSRecordEntity selectSMSRecordEntityById(Long id);

    /**
     * 查询短信记录列表
     * 
     * @param sMSRecordEntity 短信记录
     * @return 短信记录集合
     */
    public List<SMSRecordEntity> selectSMSRecordEntityList(SMSRecordEntity sMSRecordEntity);

    /**
     * 新增短信记录
     * 
     * @param sMSRecordEntity 短信记录
     * @return 结果
     */
    public int insertSMSRecordEntity(SMSRecordEntity sMSRecordEntity);

    /**
     * 修改短信记录
     * 
     * @param sMSRecordEntity 短信记录
     * @return 结果
     */
    public int updateSMSRecordEntity(SMSRecordEntity sMSRecordEntity);

    /**
     * 删除短信记录
     * 
     * @param id 短信记录主键
     * @return 结果
     */
    public int deleteSMSRecordEntityById(Long id);

    /**
     * 批量删除短信记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSMSRecordEntityByIds(Long[] ids);
}
