package com.highgo.message.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.message.mapper.EmailRecordEntityMapper;
import com.highgo.message.domain.EmailRecordEntity;
import com.highgo.message.service.IEmailRecordEntityService;

/**
 * 邮件记录Service业务层处理
 * 
 * @author eszhang
 * @date 2023-05-11
 */
@Service
public class EmailRecordEntityServiceImpl implements IEmailRecordEntityService 
{
    @Autowired
    private EmailRecordEntityMapper emailRecordEntityMapper;

    /**
     * 查询邮件记录
     * 
     * @param id 邮件记录主键
     * @return 邮件记录
     */
    @Override
    public EmailRecordEntity selectEmailRecordEntityById(Long id)
    {
        return emailRecordEntityMapper.selectEmailRecordEntityById(id);
    }

    /**
     * 查询邮件记录列表
     * 
     * @param emailRecordEntity 邮件记录
     * @return 邮件记录
     */
    @Override
    public List<EmailRecordEntity> selectEmailRecordEntityList(EmailRecordEntity emailRecordEntity)
    {
        return emailRecordEntityMapper.selectEmailRecordEntityList(emailRecordEntity);
    }

    /**
     * 新增邮件记录
     * 
     * @param emailRecordEntity 邮件记录
     * @return 结果
     */
    @Override
    public int insertEmailRecordEntity(EmailRecordEntity emailRecordEntity)
    {
        emailRecordEntity.setCreateTime(DateUtils.getNowDate());
        return emailRecordEntityMapper.insertEmailRecordEntity(emailRecordEntity);
    }

    /**
     * 修改邮件记录
     * 
     * @param emailRecordEntity 邮件记录
     * @return 结果
     */
    @Override
    public int updateEmailRecordEntity(EmailRecordEntity emailRecordEntity)
    {
        return emailRecordEntityMapper.updateEmailRecordEntity(emailRecordEntity);
    }

    /**
     * 批量删除邮件记录
     * 
     * @param ids 需要删除的邮件记录主键
     * @return 结果
     */
    @Override
    public int deleteEmailRecordEntityByIds(Long[] ids)
    {
        return emailRecordEntityMapper.deleteEmailRecordEntityByIds(ids);
    }

    /**
     * 删除邮件记录信息
     * 
     * @param id 邮件记录主键
     * @return 结果
     */
    @Override
    public int deleteEmailRecordEntityById(Long id)
    {
        return emailRecordEntityMapper.deleteEmailRecordEntityById(id);
    }
}
