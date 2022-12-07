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
import java.util.List;
import java.util.Map;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
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
        if (StringUtils.isBlank(bucketName)) {
            return uploadFile(file);
        }
        return MinioUtil.getInstance().putObject(file, bucketName);
    }

    /**
     * 下载文件
     *
     * @param fileFullPath 文件的完整路径
     * @param fileName     文件名 带后缀
     * @param bucketName   桶
     * @throws Exception
     */
    @Override
    public byte[] downloadFile(String fileFullPath, String fileName, String bucketName) throws Exception {
        return MinioUtil.getInstance().getObjectByteArray(fileFullPath, bucketName);
    }

    /**
     * 下载文件 完整路径=path+fileName
     *
     * @param fileFullPath 文件全路径
     * @param fileName     文件名 带后缀
     * @param response     http数据流
     * @param bucketName   首级目录 桶名
     * @throws Exception 异常
     */
    @Override
    public void downloadFile(String fileFullPath, String fileName, HttpServletResponse response, String bucketName) {
        // 下载500K压缩包时 第一个时第二个的2倍速
        MinioUtil.getInstance().downloadFile(fileFullPath, fileName, bucketName, response);
        //MinioUtil.getInstance().getObject(fileFullPath,fileName,bucketName,response);
    }

    /**
     * 删除文件
     *
     * @param fileFullPath 完整路径
     * @param fileName     文件名 带后缀
     * @param primaryDir   首级目录 按格式匹配
     * @throws Exception 异常
     */
    @Override
    public void deleteFile(String fileFullPath, String fileName, String primaryDir) throws Exception {
        MinioUtil.getInstance().removeObject(fileFullPath, primaryDir);
    }

    /**
     * 文件预览
     *
     * @param fileFullPath 完整路径
     * @param bucketName   首级目录 按格式匹配
     * @throws Exception 异常
     */
    @Override
    public String previewFile(String fileFullPath, String bucketName) throws Exception {
        return MinioUtil.getInstance().getPresignedObjectUrl(fileFullPath, bucketName);
    }

    /**
     * 获取指定首级目录下的全部文件
     * minio为桶名 fastDfs为组名 loacl为path
     *
     * @param bucketName  minio为桶名 fastDfs为组名 loacl为path
     * @param prefix  二级目录
     * @return
     */
    @Override
    public List<Map<String,Object>> listCatalogNameFile(String bucketName, String prefix) {
        if (StringUtils.isBlank(prefix)){
            prefix= "/";
        }
        return MinioUtil.getInstance().listObjectsMap(bucketName,prefix);
    }
}
