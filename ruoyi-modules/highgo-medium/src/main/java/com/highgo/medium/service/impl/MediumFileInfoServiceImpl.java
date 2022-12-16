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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MediumFileInfoMapper;
import com.highgo.medium.domain.MediumFileInfo;
import com.highgo.medium.service.IMediumFileInfoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 介质文件信息记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MediumFileInfoServiceImpl implements IMediumFileInfoService 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MediumFileInfoMapper mediumFileInfoMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private IMFileInfoService imFileInfoService;
    /**
     * 查询介质文件信息记录
     * 
     * @param id 介质文件信息记录主键
     * @return 介质文件信息记录
     */
    @Override
    public MediumFileInfo selectMediumFileInfoById(Long id)
    {
        return mediumFileInfoMapper.selectMediumFileInfoById(id);
    }

    /**
     * 查询介质文件信息记录列表
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 介质文件信息记录
     */
    @Override
    public List<MediumFileInfo> selectMediumFileInfoList(MediumFileInfo mediumFileInfo)
    {
        return mediumFileInfoMapper.selectMediumFileInfoList(mediumFileInfo);
    }

    /**
     * 新增介质文件信息记录
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 结果
     */
    @Override
    public int insertMediumFileInfo(MediumFileInfo mediumFileInfo)
    {
        mediumFileInfo.setCreateTime(DateUtils.getNowDate());
        return mediumFileInfoMapper.insertMediumFileInfo(mediumFileInfo);
    }

    /**
     * 修改介质文件信息记录
     * 
     * @param mediumFileInfo 介质文件信息记录
     * @return 结果
     */
    @Override
    public int updateMediumFileInfo(MediumFileInfo mediumFileInfo)
    {
        mediumFileInfo.setUpdateTime(DateUtils.getNowDate());
        return mediumFileInfoMapper.updateMediumFileInfo(mediumFileInfo);
    }

    /**
     * 批量删除介质文件信息记录
     * 
     * @param ids 需要删除的介质文件信息记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumFileInfoByIds(Long[] ids)
    {
        return mediumFileInfoMapper.deleteMediumFileInfoByIds(ids);
    }

    /**
     * 删除介质文件信息记录信息
     * 
     * @param id 介质文件信息记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumFileInfoById(Long id)
    {
        return mediumFileInfoMapper.deleteMediumFileInfoById(id);
    }

    @Override
    public int updateMediumFileInfoWithFile(MediumFileInfo mediumFileInfo, MultipartFile mediumFile, MultipartFile mediumFileMd5) {
        List<MFileInfo> list = new ArrayList<>();
        if (mediumFile!=null){
            R<SysFile> upMd = remoteFileService.upload(mediumFile,"medium");
            if (upMd.getCode()!=200){
                return 0;
            }
            MFileInfo medium = new MFileInfo();
            medium.setId(MediumUtil.getId());
            String fileName = upMd.getData().getName();
            medium.setFileName(fileName);
            medium.setFilePath(upMd.getData().getUrl());
            medium.setFileType(fileName.substring(fileName.lastIndexOf(".")));
            medium.setCreateTime(new Date());
            medium.setCreateId(mediumFileInfo.getCreateId());
            medium.setCreateBy(mediumFileInfo.getCreateBy());
            list.add(medium);
            mediumFileInfo.setMediumFileId(medium.getId());
        }
        if (mediumFileMd5!=null){
            R<SysFile> upMd5res = remoteFileService.upload(mediumFileMd5,"medium");
            if (upMd5res.getCode()!=200){
                return 0;
            }
            MFileInfo mediumMd5 = new MFileInfo();
            mediumMd5.setId(MediumUtil.getId());
            String fileNameMd5 = upMd5res.getData().getName();
            mediumMd5.setFileName(fileNameMd5);
            mediumMd5.setFilePath(upMd5res.getData().getUrl());
            mediumMd5.setFileType(fileNameMd5.substring(fileNameMd5.lastIndexOf(".")));
            mediumMd5.setCreateTime(new Date());
            mediumMd5.setCreateId(mediumFileInfo.getCreateId());
            mediumMd5.setCreateBy(mediumFileInfo.getCreateBy());
            list.add(mediumMd5);
            mediumFileInfo.setMediumMd5FileId(mediumMd5.getId());
        }
        mediumFileInfoMapper.updateMediumFileInfo(mediumFileInfo);
        return 1;
    }

    @Override
    public int insertMediumFileInfoWithFile(MediumFileInfo mediumFileInfo, MultipartFile mediumFile, MultipartFile mediumFileMd5) {
        List<MFileInfo> list = new ArrayList<>();
        if (mediumFile!=null){
            R<SysFile> upMd = remoteFileService.upload(mediumFile,"medium");
            if (upMd.getCode()!=200){
                return 0;
            }
            MFileInfo medium = new MFileInfo();
            medium.setId(MediumUtil.getId());
            String fileName = upMd.getData().getName();
            medium.setFileName(fileName);
            medium.setFilePath(upMd.getData().getUrl());
            medium.setFileType(fileName.substring(fileName.lastIndexOf(".")));
            medium.setCreateTime(new Date());
            medium.setCreateId(mediumFileInfo.getCreateId());
            medium.setCreateBy(mediumFileInfo.getCreateBy());
            list.add(medium);
            mediumFileInfo.setMediumFileId(medium.getId());
        }
        if (mediumFileMd5!=null){
            R<SysFile> upMd5res = remoteFileService.upload(mediumFileMd5,"medium");
            if (upMd5res.getCode()!=200){
                return 0;
            }
            MFileInfo mediumMd5 = new MFileInfo();
            mediumMd5.setId(MediumUtil.getId());
            String fileNameMd5 = upMd5res.getData().getName();
            mediumMd5.setFileName(fileNameMd5);
            mediumMd5.setFilePath(upMd5res.getData().getUrl());
            mediumMd5.setFileType(fileNameMd5.substring(fileNameMd5.lastIndexOf(".")));
            mediumMd5.setCreateTime(new Date());
            mediumMd5.setCreateId(mediumFileInfo.getCreateId());
            mediumMd5.setCreateBy(mediumFileInfo.getCreateBy());
            list.add(mediumMd5);
            mediumFileInfo.setMediumMd5FileId(mediumMd5.getId());
        }
        if(list.size()>0){
            imFileInfoService.insertMFileInfoBath(list);
        }
        mediumFileInfo.setId(MediumUtil.getId());
        mediumFileInfoMapper.insertMediumFileInfo(mediumFileInfo);
        return 1;
    }

    @Override
    public void download(HttpServletResponse response, MediumFileInfo mediumFileInfo) {
        if (log.isDebugEnabled()){
            log.debug("MediumFileInfoServiceImpl.downLoad req:{}", JSON.toJSONString(mediumFileInfo));
        }
        List<String> fileIds = new ArrayList<>();
        fileIds.add(mediumFileInfo.getMediumFileId());
        fileIds.add(mediumFileInfo.getMediumMd5FileId());
        List<MFileInfo> downloadFileInfo = imFileInfoService.selectMFileInfoByIds(fileIds);
        // 下载顺序 调整后就不行了  先下小文件再大文件才能成功
        FileReq fileReq2 = new FileReq();
        String fileName2 = downloadFileInfo.get(1).getFileName();
        String filePath2 = downloadFileInfo.get(1).getFilePath();
        fileReq2.setFileName(fileName2);
        fileReq2.setFullFileName(filePath2);
        Response fileResp2 = remoteFileService.downLoad(fileReq2);
        Response.Body body2 = fileResp2.body();

        FileReq fileReq = new FileReq();
        String fileName = downloadFileInfo.get(0).getFileName();
        String filePath = downloadFileInfo.get(0).getFilePath();
        fileReq.setFileName(fileName);
        fileReq.setFullFileName(filePath);
        Response fileResp = remoteFileService.downLoad(fileReq);
        Response.Body body = fileResp.body();

        //response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+mediumFileInfo.getMediumName()+".zip");

        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream;
        // 创建 输出流到 response
        OutputStream outputStream= null;

        try {
            outputStream = response.getOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            zipOutputStream.setMethod(ZipOutputStream.DEFLATED);

            Map<String,InputStream> fileInputStream = new HashMap<>();
            fileInputStream.put(fileName,body.asInputStream());
            fileInputStream.put(fileName2,body2.asInputStream());
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
