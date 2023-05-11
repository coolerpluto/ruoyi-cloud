package com.highgo.message.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class MailReq
{
    private static final long serialVersionUID = 1L;

    /**
     * 邮件接收人
     */
    @NotBlank
    private String to;
    /**
     * 邮件主题
     */
    @NotBlank
    private String subject;
    /**
     * 邮件内容
     */
    @NotBlank
    private String content;
    /**
     * 发送时间
     */
    private Date sentDate;
    /**
     * 抄送
     */
    private String copyTo;

    /**
     * 邮件附件
     */
    private MultipartFile[] multipartFiles;
    @NotBlank
    private String operator;

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getSentDate()
    {
        return sentDate;
    }

    public void setSentDate(Date sentDate)
    {
        this.sentDate = sentDate;
    }

    public String getCopyTo()
    {
        return copyTo;
    }

    public void setCopyTo(String copyTo)
    {
        this.copyTo = copyTo;
    }

    public MultipartFile[] getMultipartFiles()
    {
        return multipartFiles;
    }

    public void setMultipartFiles(MultipartFile[] multipartFiles)
    {
        this.multipartFiles = multipartFiles;
    }
}
