package com.ruoyi.file.utils;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.file.MimeTypeUtils;
import com.ruoyi.common.core.utils.uuid.Seq;
import com.ruoyi.file.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Minio 工具类
 * 不提供进行桶级操作 进提供桶级存在性校验
 */
public class MinioUtil {
    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    private static MinioUtil instance = new MinioUtil();

    private MinioUtil() {

    }

    public static MinioUtil getInstance() {
        if (instance == null) {
            instance = new MinioUtil();
        }
        return instance;
    }

    /**
     * 查看存储bucket是否存在
     *
     * @return boolean
     */
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = client.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }


    /**
     * 获取临时下载连接 失效时间（以秒为单位），不得大于七天
     *
     * @param objectName 对象的名称
     * @param expiry     有效期
     * @param timeUnit   时间单位
     * @param bucketName 桶名
     * @return 临时URL
     */
    public String getPresignedObjectUrl(String objectName, int expiry, TimeUnit timeUnit, String bucketName) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(objectName)
                .method(Method.GET)
                .expiry(expiry, timeUnit)
                .build();
        try {
            String url = client.getPresignedObjectUrl(args);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取临时下载连接 失效时间 七天
     */
    public String getPresignedObjectUrl(String objectName, String bucketName) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(objectName)
                .method(Method.GET)
                .build();
        try {
            String url = client.getPresignedObjectUrl(args);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 上传 对象
     *
     * @param file 文件
     * @return Boolean
     */
    public String putObject(MultipartFile file, String bucketName) throws Exception {
        String fileName = FileUploadUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        return minioConfig.getUrl() + "/" + bucketName + "/" + fileName;
    }

    /**
     * 使用流的的方式上传
     * @param inputStream 流
     * @param bucketName 桶
     * @param fileName 文件名 不是全路径名
     * @return
     * @throws Exception
     */
    public String putObject(InputStream inputStream, String bucketName, String fileName) throws Exception {
        String extension = FilenameUtils.getExtension(fileName);
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(fileName));
        }

        String finalName = com.ruoyi.common.core.utils.StringUtils.format("{}/{}_{}.{}",
                DateUtils.datePath(),
                FilenameUtils.getBaseName(fileName),
                Seq.getId(Seq.uploadSeqType),
                extension);

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(finalName)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        client.putObject(args);
        return minioConfig.getUrl() + "/" + bucketName + "/" + finalName;
    }

    /**
     * 删除 对象
     *
     * @param fileFullName   文件全名
     * @param bucketName 桶名
     * @return
     * @throws Exception
     */
    public boolean removeObject(String fileFullName, String bucketName) {
        try {
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                    .object(fileFullName)
                    .build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 查看文件对象
     *
     * @param bucketName 桶名
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects(String bucketName) {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .build();

        Iterable<Result<Item>> results = client.listObjects(args);
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }

    /**
     * 复制 对象
     *
     * @param sourceBucketName 源文件所在桶
     * @param sourceObjectName 源文件名
     * @param bucketName       新文件所在桶
     * @param objectName       新文件名
     */
    public boolean copyObject(String sourceBucketName, String sourceObjectName,
                              String bucketName, String objectName) {

        CopyObjectArgs args = CopyObjectArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(objectName)
                .source(CopySource.builder()
                        .bucket(sourceBucketName)
                        .object(sourceObjectName)
                        .build())
                .build();
        try {
            client.copyObject(args);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 下载对象
     * <p>
     * 从bucket获取指定对象的输入流，后续可使用输入流读取对象
     * getObject与minio server连接默认保持5分钟，
     * 每隔15s由minio server向客户端发送keep-alive check，5分钟后由客户端主动发起关闭连接
     *
     * @param fileFullPath   为件全路径名
     * @param bucketName 桶名
     */
    public InputStream getObject(String fileFullPath, String bucketName) throws Exception {
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(fileFullPath).build();
        return client.getObject(objectArgs);
    }

    /**
     * 下载对象
     * 从bucket获取指定对象的输入流，直接通过response传输
     * 使用 FastByteArrayOutputStream 承载二进制
     *
     * @param fileFullPath   为件全路径名
     * @param bucketName 桶名
     */
    public void getObject(String fileFullPath, String fileName, String bucketName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(StringUtils.isBlank(bucketName) ? minioConfig.getBucketName() : bucketName)
                .object(fileFullPath).build();

        try (GetObjectResponse response = client.getObject(objectArgs)) {
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                res.setContentType("application/force-download;charset=utf-8");
                res.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从minio下载文件，直接通过response传输
     * 使用 BufferedInputStream 承载二进制
     */
    public void downloadFile(String fileFullPath, String fileName, String bucketName, HttpServletResponse response) {

        try (InputStream is = getObject(bucketName, fileFullPath);
             BufferedInputStream bis = new BufferedInputStream(is);
             OutputStream os = response.getOutputStream()) {

            response.setContentType("application/force-download;charset=utf-8");// 设置强制下载而不是直接打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));// 设置文件名

            byte[] buffer = new byte[1024 * 1024 * 1024]; //buffer 1M
            int offset = bis.read(buffer);
            while (offset != -1) {
                os.write(buffer, 0, offset);
                offset = bis.read(buffer);
            }
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败", e);
        }
    }

}
