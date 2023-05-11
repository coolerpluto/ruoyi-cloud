package com.highgo.message.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.highgo.message.config.EmailConfig;
import com.highgo.message.domain.MailReq;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.highgo.message.mapper.EmailRecordEntityMapper;
import com.highgo.message.domain.EmailRecordEntity;
import com.highgo.message.service.IEmailRecordEntityService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailConfig emailConfig;

    @Override
    @Async
    public JSONObject sendSimpleMail(MailReq mailReq)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailReq.getTo());//收信人
        message.setCc(mailReq.getCopyTo());
        message.setSubject(mailReq.getSubject());//主题
        message.setText(mailReq.getContent());//内容
        message.setFrom(emailConfig.getAccessKeyId());//发信人
        message.setSentDate(new Date());
        mailSender.send(message);

        insertAfterSend(mailReq, "0", "");
        return new JSONObject();
    }

    private void insertAfterSend(MailReq mailReq, String code, String message)
    {
        EmailRecordEntity entity = new EmailRecordEntity();
        entity.setSourceUser(emailConfig.getAccessKeyId());
        entity.setTargetUser(mailReq.getTo());
        entity.setCopyUser(mailReq.getCopyTo());
        entity.setSendDate(mailReq.getSentDate());
        entity.setSubject(mailReq.getSubject());
        entity.setContent(mailReq.getContent());
        StringBuilder fileList = new StringBuilder();
        MultipartFile[] files = mailReq.getMultipartFiles();
        if (!ObjectUtils.isEmpty(files))
        {
            for (MultipartFile file : files)
            {
                fileList.append(file.getOriginalFilename()).append(";");
            }
        }
        entity.setAttachment(fileList.toString());
        entity.setCreateBy(mailReq.getOperator());
        entity.setCreateTime(DateUtils.getNowDate());

        entity.setResCode(code);
        entity.setResMessage(message);
        emailRecordEntityMapper.insertEmailRecordEntity(entity);
    }

    @Override
    public JSONObject sendComplexMail(MailReq mailReq)
    {
        String to = mailReq.getTo();
        String subject = mailReq.getSubject();
        String content = mailReq.getContent();
        MultipartFile[] multipartFiles = mailReq.getMultipartFiles();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try
        {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailConfig.getAccessKeyId());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            if (!ObjectUtils.isEmpty(multipartFiles))
            {
                for (MultipartFile multipartFile : multipartFiles)
                {
                    helper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);//添加附件，可多次调用该方法添加多个附件
                }
            }
            mailSender.send(message);
            insertAfterSend(mailReq, "0", "");
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            insertAfterSend(mailReq, "1", "发送邮件失败");
        }

        return new JSONObject();
    }
}
