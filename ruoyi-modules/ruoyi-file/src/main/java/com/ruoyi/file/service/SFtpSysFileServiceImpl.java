package com.ruoyi.file.service;

import com.ruoyi.file.utils.SFtpServerUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ftp 服务器文件存储
 *
 * @author ruoyi
 */
@Service
@Primary
public class SFtpSysFileServiceImpl extends ASysFileService {
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        return SFtpServerUtil.getInstance().upload(file.getOriginalFilename(), inputStream);
    }

    @Override
    public String uploadFile(MultipartFile file, String primaryDir) throws Exception {
        InputStream inputStream = file.getInputStream();
        return SFtpServerUtil.getInstance().upload(primaryDir, file.getOriginalFilename(), inputStream);
    }

    /**
     * 下载文件
     *
     * @param fileFullPath 文件路径 到最后一级
     * @param fileName     文件名 带后缀
     * @param primaryDir     不使用
     * @throws Exception 异常
     */
    @Override
    public byte[] downloadFile(String fileFullPath, String fileName, String primaryDir) throws Exception {

        return SFtpServerUtil.getInstance().download(fileFullPath,fileName);
    }

    /**
     * 下载文件 完整路径
     *
     * @param fileFullPath 文件路径 到最后一级
     * @param fileName     文件名 带后缀
     * @param primaryDir   不使用
     */
    @Override
    public void downloadFile(String fileFullPath, String fileName, HttpServletResponse response, String primaryDir) throws Exception {
        SFtpServerUtil.getInstance().download(response,fileFullPath,fileName);
    }


    /**
     * 删除文件
     *
     * @param fileFullPath 完整路径
     * @param fileName     文件名 带后缀
     * @param primaryDir  不使用
     * @throws Exception 异常
     */
    @Override
    public void deleteFile(String fileFullPath, String fileName, String primaryDir) {
        SFtpServerUtil.getInstance().delete(fileFullPath,fileName);
    }

    /**
     * 文件预览 完整路径=path+fileName
     *
     * @param fileFullPath 文件的路径
     * @param primaryDir   首级目录 按格式匹配
     * @throws Exception 异常
     */
    @Override
    public String previewFile(String fileFullPath, String primaryDir) throws Exception {
        return "not support";
    }

    /**
     * 获取指定首级目录下的全部文件
     *
     * @param primaryDir minio为桶名 fastDfs为组名 loacl为path
     * @param catalog    二级目录
     * @return
     */
    @Override
    public List<Map<String, Object>> listCatalogNameFile(String primaryDir, String catalog) {
        return new ArrayList<>();
    }
}
