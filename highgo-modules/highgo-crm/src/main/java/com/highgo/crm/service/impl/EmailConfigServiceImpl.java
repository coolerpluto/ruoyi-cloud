package com.highgo.crm.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.crm.mapper.EmailConfigMapper;
import com.highgo.crm.domain.EmailConfig;
import com.highgo.crm.service.IEmailConfigService;

/**
 * 邮箱设置Service业务层处理
 * 
 * @author eszhang
 * @date 2023-01-17
 */
@Service
public class EmailConfigServiceImpl implements IEmailConfigService 
{
    @Autowired
    private EmailConfigMapper emailConfigMapper;

    /**
     * 查询邮箱设置
     * 
     * @param id 邮箱设置主键
     * @return 邮箱设置
     */
    @Override
    public EmailConfig selectEmailConfigById(String id)
    {
        return emailConfigMapper.selectEmailConfigById(id);
    }

    /**
     * 查询邮箱设置列表
     * 
     * @param emailConfig 邮箱设置
     * @return 邮箱设置
     */
    @Override
    public List<EmailConfig> selectEmailConfigList(EmailConfig emailConfig)
    {
        return emailConfigMapper.selectEmailConfigList(emailConfig);
    }

    /**
     * 新增邮箱设置
     * 
     * @param emailConfig 邮箱设置
     * @return 结果
     */
    @Override
    public int insertEmailConfig(EmailConfig emailConfig)
    {
        emailConfig.setCreateTime(DateUtils.getNowDate());
        return emailConfigMapper.insertEmailConfig(emailConfig);
    }

    /**
     * 修改邮箱设置
     * 
     * @param emailConfig 邮箱设置
     * @return 结果
     */
    @Override
    public int updateEmailConfig(EmailConfig emailConfig)
    {
        emailConfig.setUpdateTime(DateUtils.getNowDate());
        return emailConfigMapper.updateEmailConfig(emailConfig);
    }

    /**
     * 批量删除邮箱设置
     * 
     * @param ids 需要删除的邮箱设置主键
     * @return 结果
     */
    @Override
    public int deleteEmailConfigByIds(String[] ids)
    {
        return emailConfigMapper.deleteEmailConfigByIds(ids);
    }

    /**
     * 删除邮箱设置信息
     * 
     * @param id 邮箱设置主键
     * @return 结果
     */
    @Override
    public int deleteEmailConfigById(String id)
    {
        return emailConfigMapper.deleteEmailConfigById(id);
    }
}
