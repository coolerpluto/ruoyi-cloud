package com.highgo.message.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 邮件记录对象 email_record
 *
 * @author eszhang
 * @date 2023-05-11
 */
public class EmailRecordEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 发件人
     */
    @Excel(name = "发件人")
    private String sourceUser;

    /**
     * 收件人
     */
    @Excel(name = "收件人")
    private String targetUser;

    /**
     * 抄送人
     */
    @Excel(name = "抄送人")
    private String copyUser;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendDate;

    /**
     * 邮件主题
     */
    @Excel(name = "邮件主题")
    private String subject;

    /**
     * 邮件内容
     */
    @Excel(name = "邮件内容")
    private String content;

    /**
     * 附件文件列表
     */
    @Excel(name = "附件文件列表")
    private String attachment;
    private String resCode;
    private String resMessage;

    public String getResCode()
    {
        return resCode;
    }

    public void setResCode(String resCode)
    {
        this.resCode = resCode;
    }

    public String getResMessage()
    {
        return resMessage;
    }

    public void setResMessage(String resMessage)
    {
        this.resMessage = resMessage;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setSourceUser(String sourceUser)
    {
        this.sourceUser = sourceUser;
    }

    public String getSourceUser()
    {
        return sourceUser;
    }

    public void setTargetUser(String targetUser)
    {
        this.targetUser = targetUser;
    }

    public String getTargetUser()
    {
        return targetUser;
    }

    public void setCopyUser(String copyUser)
    {
        this.copyUser = copyUser;
    }

    public String getCopyUser()
    {
        return copyUser;
    }

    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }

    public Date getSendDate()
    {
        return sendDate;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }

    public String getAttachment()
    {
        return attachment;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sourceUser", getSourceUser())
                .append("targetUser", getTargetUser())
                .append("copyUser", getCopyUser())
                .append("sendDate", getSendDate())
                .append("subject", getSubject())
                .append("content", getContent())
                .append("attachment", getAttachment())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
