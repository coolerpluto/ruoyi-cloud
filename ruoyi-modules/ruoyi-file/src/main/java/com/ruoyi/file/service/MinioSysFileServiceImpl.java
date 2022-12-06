package com.ruoyi.file.service;

import com.ruoyi.file.config.MinioConfig;
import com.ruoyi.file.utils.FileUploadUtils;
import com.ruoyi.file.utils.MinioUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
@Primary
public class MinioSysFileServiceImpl extends ASysFileService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUploadUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }

    /**
     * 文件上传接口 指定首级目录
     *
     * @param file       上传的文件
     * @param bucketName 首级目录 桶名
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file, String bucketName) throws Exception {
        if (StringUtils.isBlank(bucketName)){
            return uploadFile(file);
        }
        return MinioUtil.getInstance().putObject(file,bucketName);
    }

    /**
     * 下载文件
     *
     * @param fileFullPath     文件的完整路径
     * @param fileName 文件名 带后缀
     * @param bucketName 桶
     * @throws Exception
     */
    @Override
    public InputStream downloadFile(String fileFullPath, String fileName, String bucketName) {
        try {
            return MinioUtil.getInstance().getObject(fileFullPath,bucketName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }

    /**
     * 下载文件 完整路径=path+fileName
     *
     * @param fileFullPath       文件全路径
     * @param fileName   文件名 带后缀
     * @param response   http数据流
     * @param bucketName 首级目录 桶名
     * @throws Exception 异常
     */
    @Override
    public void downloadFile(String fileFullPath, String fileName, HttpServletResponse response, String bucketName) {
        //不知道下面俩那个快
        System.out.println(new Date().getTime());
        MinioUtil.getInstance().downloadFile(fileFullPath,fileName,bucketName,response);
        //MinioUtil.getInstance().getObject(fileFullPath,fileName,bucketName,response);
        System.out.println(new Date().getTime());
    }

    /**
     * 下删除文件 完整路径=path+fileName
     *
     * @param path     文件的路径
     * @param fileName 文件名 带后缀
     * @param response http数据流
     * @throws Exception
     */
    @Override
    public void deleteFile(String path, String fileName, HttpServletResponse response) throws Exception {
        //TODO
    }

    /**
     * 下删除文件 完整路径=path+fileName
     *
     * @param path       文件的路径
     * @param fileName   文件名 带后缀
     * @param response   http数据流
     * @param bucketName 首级目录 桶名
     * @throws Exception 异常
     */
    @Override
    public void deleteFile(String path, String fileName, HttpServletResponse response, String bucketName) throws Exception {
//TODO
    }

    /**
     * 文件预览 完整路径=path+fileName
     *
     * @param path     文件的路径
     * @param fileName 文件名 带后缀
     * @param response http数据流
     * @throws Exception 异常
     */
    @Override
    public String previewFile(String path, String fileName, HttpServletResponse response) throws Exception {
        //TODO
        return "";
    }

    /**
     * 文件预览 完整路径=path+fileName
     *
     * @param path       文件的路径
     * @param fileName   文件名 带后缀
     * @param response   http数据流
     * @param bucketName 首级目录 按格式匹配
     * @throws Exception 异常
     */
    @Override
    public String previewFile(String path, String fileName, HttpServletResponse response, String bucketName) {
        return MinioUtil.getInstance().getPresignedObjectUrl(path+fileName,bucketName);
    }
}
