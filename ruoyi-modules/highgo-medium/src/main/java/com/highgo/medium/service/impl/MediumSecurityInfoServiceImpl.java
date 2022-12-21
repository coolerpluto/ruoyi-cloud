package com.highgo.medium.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import com.alibaba.fastjson.JSON;
import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.service.IMFileInfoService;
import com.highgo.medium.utils.MediumUtil;
import com.highgo.medium.utils.ZipUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.FileReq;
import com.ruoyi.system.api.domain.SysFile;
import feign.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MediumSecurityInfoMapper;
import com.highgo.medium.domain.MediumSecurityInfo;
import com.highgo.medium.service.IMediumSecurityInfoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 介质安全文件记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MediumSecurityInfoServiceImpl implements IMediumSecurityInfoService 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MediumSecurityInfoMapper mediumSecurityInfoMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private IMFileInfoService imFileInfoService;
    /**
     * 查询介质安全文件记录
     * 
     * @param id 介质安全文件记录主键
     * @return 介质安全文件记录
     */
    @Override
    public MediumSecurityInfo selectMediumSecurityInfoById(Long id)
    {
        return mediumSecurityInfoMapper.selectMediumSecurityInfoById(id);
    }

    /**
     * 查询介质安全文件记录列表
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 介质安全文件记录
     */
    @Override
    public List<MediumSecurityInfo> selectMediumSecurityInfoList(MediumSecurityInfo mediumSecurityInfo)
    {
        return mediumSecurityInfoMapper.selectMediumSecurityInfoList(mediumSecurityInfo);
    }

    /**
     * 新增介质安全文件记录
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 结果
     */
    @Override
    public int insertMediumSecurityInfo(MediumSecurityInfo mediumSecurityInfo)
    {
        mediumSecurityInfo.setCreateTime(DateUtils.getNowDate());
        return mediumSecurityInfoMapper.insertMediumSecurityInfo(mediumSecurityInfo);
    }

    /**
     * 修改介质安全文件记录
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 结果
     */
    @Override
    public int updateMediumSecurityInfo(MediumSecurityInfo mediumSecurityInfo)
    {
        mediumSecurityInfo.setUpdateTime(DateUtils.getNowDate());
        return mediumSecurityInfoMapper.updateMediumSecurityInfo(mediumSecurityInfo);
    }

    /**
     * 批量删除介质安全文件记录
     * 
     * @param ids 需要删除的介质安全文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumSecurityInfoByIds(Long[] ids)
    {
        return mediumSecurityInfoMapper.deleteMediumSecurityInfoByIds(ids);
    }

    /**
     * 删除介质安全文件记录信息
     * 
     * @param id 介质安全文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumSecurityInfoById(Long id)
    {
        return mediumSecurityInfoMapper.deleteMediumSecurityInfoById(id);
    }

    @Override
    public int insertMediumSecurityWithFile(MediumSecurityInfo mediumSecurityInfo, MultipartFile file, MultipartFile fileMd5) {
        List<MFileInfo> list = new ArrayList<>();
        if (file!=null){
            R<SysFile> upMd = remoteFileService.upload(file,"medium");
            if (upMd.getCode() != 200) {
                return 0;
            }
            MFileInfo security = new MFileInfo();
            security.setId(MediumUtil.getId());
            String fileName = upMd.getData().getName();
            security.setFileName(fileName);
            security.setFilePath(upMd.getData().getUrl());
            security.setFileType(fileName.substring(fileName.lastIndexOf(".")));
            security.setCreateTime(new Date());
            security.setCreateId(mediumSecurityInfo.getCreateId());
            security.setCreateBy(mediumSecurityInfo.getCreateBy());
            list.add(security);
            mediumSecurityInfo.setFileId(security.getId());
        }
        if (fileMd5!=null){
            R<SysFile> upMd5res = remoteFileService.upload(fileMd5,"medium");
            if (upMd5res.getCode() != 200) {
                return 0;
            }
            MFileInfo securityMd5 = new MFileInfo();
            securityMd5.setId(MediumUtil.getId());
            String fileNameMd5 = upMd5res.getData().getName();
            securityMd5.setFileName(fileNameMd5);
            securityMd5.setFilePath(upMd5res.getData().getUrl());
            securityMd5.setFileType(fileNameMd5.substring(fileNameMd5.lastIndexOf(".")));
            securityMd5.setCreateTime(new Date());
            securityMd5.setCreateId(mediumSecurityInfo.getCreateId());
            securityMd5.setCreateBy(mediumSecurityInfo.getCreateBy());
            list.add(securityMd5);
            mediumSecurityInfo.setMd5FileId(securityMd5.getId());
        }
        if (list.size() > 0) {
            imFileInfoService.insertMFileInfoBath(list);
        }
        mediumSecurityInfo.setId(MediumUtil.getId());
        mediumSecurityInfoMapper.insertMediumSecurityInfo(mediumSecurityInfo);
        return 1;
    }

    @Override
    public int updateMediumSecurityWithFile(MediumSecurityInfo mediumSecurityInfo, MultipartFile file, MultipartFile fileMd5) {
        List<MFileInfo> list = new ArrayList<>();
        if (file!=null){
            R<SysFile> upMd = remoteFileService.upload(file,"medium");
            if (upMd.getCode() != 200) {
                return 0;
            }
            MFileInfo medium = new MFileInfo();
            medium.setId(MediumUtil.getId());
            String fileName = upMd.getData().getName();
            medium.setFileName(fileName);
            medium.setFilePath(upMd.getData().getUrl());
            medium.setFileType(fileName.substring(fileName.lastIndexOf(".")));
            medium.setCreateTime(new Date());
            medium.setCreateId(mediumSecurityInfo.getCreateId());
            medium.setCreateBy(mediumSecurityInfo.getCreateBy());
            list.add(medium);
            mediumSecurityInfo.setFileId(medium.getId());
        }
        if (fileMd5!=null){
            R<SysFile> upMd5res = remoteFileService.upload(fileMd5,"medium");
            if (upMd5res.getCode() != 200) {
                return 0;
            }
            MFileInfo mediumMd5 = new MFileInfo();
            mediumMd5.setId(MediumUtil.getId());
            String fileNameMd5 = upMd5res.getData().getName();
            mediumMd5.setFileName(fileNameMd5);
            mediumMd5.setFilePath(upMd5res.getData().getUrl());
            mediumMd5.setFileType(fileNameMd5.substring(fileNameMd5.lastIndexOf(".")));
            mediumMd5.setCreateTime(new Date());
            mediumMd5.setCreateId(mediumSecurityInfo.getCreateId());
            mediumMd5.setCreateBy(mediumSecurityInfo.getCreateBy());
            list.add(mediumMd5);
            mediumSecurityInfo.setMd5FileId(mediumMd5.getId());
        }
        if (list.size() > 0) {
            imFileInfoService.insertMFileInfoBath(list);
        }
        mediumSecurityInfoMapper.updateMediumSecurityInfo(mediumSecurityInfo);
        return 1;
    }

    @Override
    public void download(HttpServletResponse response, MediumSecurityInfo mediumSecurityInfo) {
        if (log.isDebugEnabled()){
            log.debug("SecurityInfoServiceImpl.downLoad req:{}", JSON.toJSONString(mediumSecurityInfo));
        }
        if (StringUtils.isBlank(mediumSecurityInfo.getFileId()) &&
                StringUtils.isBlank(mediumSecurityInfo.getMd5FileId())){
            if (log.isErrorEnabled()){
                log.error("SecurityInfoServiceImpl.downLoad req not right ");
            }
            return;
        }
        List<String> fileIds = new ArrayList<>();
        FileReq fileMd5Req = null;
        if (StringUtils.isNotBlank(mediumSecurityInfo.getMd5FileId())){
            fileMd5Req = new FileReq();
            fileIds.add(mediumSecurityInfo.getMd5FileId());
        }
        FileReq fileMReq = null;
        if (StringUtils.isNotBlank(mediumSecurityInfo.getFileId())){
            fileMReq = new FileReq();
            fileIds.add(mediumSecurityInfo.getFileId());
        }
        if(fileIds.size()<1){
            return;
        }

        List<MFileInfo> downloadFileInfo = imFileInfoService.selectMFileInfoByIds(fileIds);
        for (MFileInfo fileInfo: downloadFileInfo) {
            String filePath = fileInfo.getFilePath();
            String fileNameInDB = fileInfo.getFileName();

            int index = filePath.lastIndexOf(fileNameInDB);
            String finalPath = "";
            if (index==-1|| filePath.endsWith("/")){
                finalPath = filePath;
            }else {
                finalPath = filePath.substring(0,index);
            }
            if (StringUtils.equals(mediumSecurityInfo.getMd5FileId(),(fileInfo.getId()))){
                //md5文件 到了这里 对象就不会是null了
                assert fileMd5Req != null;
                fileMd5Req.setFileName(fileNameInDB);
                fileMd5Req.setFullFileName(finalPath);
            }else {
                //介质文件 到了这里 对象就不会是null了
                assert fileMReq != null;
                fileMReq.setFileName(fileNameInDB);
                fileMReq.setFullFileName(finalPath);
            }
        }
        if (log.isDebugEnabled()){
            log.debug("SecurityInfoServiceImpl.downLoad req:fileMReq:{},fileMd5Req:{}.",
                    JSON.toJSONString(fileMReq),JSON.toJSONString(fileMd5Req));
        }


        Response.Body fileMBody =null;
        if (fileMReq != null &&StringUtils.isNotBlank(fileMReq.getFullFileName())&&StringUtils.isNotBlank(fileMReq.getFileName())){
            Response fileMResp = remoteFileService.downLoad(fileMReq);
            fileMBody = fileMResp.body();
        }
        Response.Body fileMd5Body = null;
        if (fileMd5Req != null &&StringUtils.isNotBlank(fileMd5Req.getFullFileName())&&StringUtils.isNotBlank(fileMd5Req.getFileName())){
            Response fileMd5Resp = remoteFileService.downLoad(fileMd5Req);
            fileMd5Body = fileMd5Resp.body();
        }
        //response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+mediumSecurityInfo.getSecurityName()+".zip");

        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream;
        // 创建 输出流到 response
        OutputStream outputStream= null;

        try {
            outputStream = response.getOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            zipOutputStream.setMethod(ZipOutputStream.DEFLATED);

            Map<String, InputStream> fileInputStream = new HashMap<>();
            if (fileMReq != null &&StringUtils.isNotBlank(fileMReq.getFullFileName())&&StringUtils.isNotBlank(fileMReq.getFileName())){
                fileInputStream.put(fileMReq.getFileName(),fileMBody.asInputStream());
            }
            if (fileMd5Req != null &&StringUtils.isNotBlank(fileMd5Req.getFullFileName())&&StringUtils.isNotBlank(fileMd5Req.getFileName())){
                fileInputStream.put(fileMd5Req.getFileName(),fileMd5Body.asInputStream());
            }
            if (fileInputStream.size()<1){
                return;
            }
            if (log.isDebugEnabled()){
                log.debug("SecurityInfoServiceImpl.downLoad Stream is null");
            }
            ZipUtil.zipStream(fileInputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
