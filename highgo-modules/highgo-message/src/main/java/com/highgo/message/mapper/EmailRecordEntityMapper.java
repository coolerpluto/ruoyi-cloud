package com.highgo.message.mapper;

import java.util.List;
import com.highgo.message.domain.EmailRecordEntity;

/**
 * 邮件记录Mapper接口
 * 
 * @author eszhang
 * @date 2023-05-11
 */
public interface EmailRecordEntityMapper 
{
    /**
     * 查询邮件记录
     * 
     * @param id 邮件记录主键
     * @return 邮件记录
     */
    public EmailRecordEntity selectEmailRecordEntityById(Long id);

    /**
     * 查询邮件记录列表
     * 
     * @param emailRecordEntity 邮件记录
     * @return 邮件记录集合
     */
    public List<EmailRecordEntity> selectEmailRecordEntityList(EmailRecordEntity emailRecordEntity);

    /**
     * 新增邮件记录
     * 
     * @param emailRecordEntity 邮件记录
     * @return 结果
     */
    public int insertEmailRecordEntity(EmailRecordEntity emailRecordEntity);

    /**
     * 修改邮件记录
     * 
     * @param emailRecordEntity 邮件记录
     * @return 结果
     */
    public int updateEmailRecordEntity(EmailRecordEntity emailRecordEntity);

    /**
     * 删除邮件记录
     * 
     * @param id 邮件记录主键
     * @return 结果
     */
    public int deleteEmailRecordEntityById(Long id);

    /**
     * 批量删除邮件记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmailRecordEntityByIds(Long[] ids);
}
