package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.EmailConfig;

/**
 * 邮箱设置Service接口
 * 
 * @author eszhang
 * @date 2023-01-17
 */
public interface IEmailConfigService 
{
    /**
     * 查询邮箱设置
     * 
     * @param id 邮箱设置主键
     * @return 邮箱设置
     */
    public EmailConfig selectEmailConfigById(String id);

    /**
     * 查询邮箱设置列表
     * 
     * @param emailConfig 邮箱设置
     * @return 邮箱设置集合
     */
    public List<EmailConfig> selectEmailConfigList(EmailConfig emailConfig);

    /**
     * 新增邮箱设置
     * 
     * @param emailConfig 邮箱设置
     * @return 结果
     */
    public int insertEmailConfig(EmailConfig emailConfig);

    /**
     * 修改邮箱设置
     * 
     * @param emailConfig 邮箱设置
     * @return 结果
     */
    public int updateEmailConfig(EmailConfig emailConfig);

    /**
     * 批量删除邮箱设置
     * 
     * @param ids 需要删除的邮箱设置主键集合
     * @return 结果
     */
    public int deleteEmailConfigByIds(String[] ids);

    /**
     * 删除邮箱设置信息
     * 
     * @param id 邮箱设置主键
     * @return 结果
     */
    public int deleteEmailConfigById(String id);
}
