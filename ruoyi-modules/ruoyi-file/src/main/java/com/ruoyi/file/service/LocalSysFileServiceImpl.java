package com.ruoyi.file.service;

import com.ruoyi.file.utils.FileUploadUtils;
import com.ruoyi.file.utils.LocalFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 本地文件存储
 *
 * @author ruoyi
 */
//@Primary
@Service
public class LocalSysFileServiceImpl extends ASysFileService {
    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix}")
    public String localFilePrefix;

    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain}")
    public String domain;

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String name = FileUploadUtils.upload(localFilePath, file);
        String url = domain + localFilePrefix + name;
        return url;
    }

    /**
     * 文件上传接口 指定首级目录
     *
     * @param file    上传的文件
     * @param secPath 二级目录 在二级目录之后在进行分割 本地时不能同时开通多个不同的根目录
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file, String secPath) throws Exception {
        if (!StringUtils.endsWith(localFilePath, "/") && !StringUtils.startsWith(secPath, "/")) {
            secPath = "/" + secPath;
        }
        String baseDir = localFilePath + secPath;
        String name = FileUploadUtils.upload(baseDir, file);
        String url = domain + localFilePrefix + secPath + name;
        return url;
    }

    /**
     * 下载文件
     *
     * @param fullFileName 文件的完整路径除了 localFilePrefix
     * @param fileName     文件名 带后缀
     * @param secPath      二级目录 在二级目录之后在进行分割 本地时不能同时开通多个不同的根目录
     * @throws Exception 异常
     */
    @Override
    public byte[] downloadFile(String fullFileName, String fileName, String secPath) throws Exception {
        String fullPath;
        if (StringUtils.isBlank(fullFileName)) {
            fullPath = getFullPath(secPath, fileName);
        } else {
            fullPath = getFullPath(fullFileName);
        }
        return LocalFileUtil.getInstance().downloadFile(fullPath);
    }

    private String getFullPath(String fullFileName) {
        if (!StringUtils.endsWith(localFilePath, "/") && !StringUtils.startsWith(fullFileName, "/")) {
            fullFileName = "/" + fullFileName;
        }
        return localFilePath + fullFileName;
    }

    private String getFullPath(String secDir, String fileName) {
        if (!StringUtils.endsWith(localFilePath, "/") && !StringUtils.startsWith(secDir, "/")) {
            secDir = "/" + secDir;
        }
        if (!StringUtils.endsWith(secDir, "/") && !StringUtils.startsWith(fileName, "/")) {
            secDir = secDir + "/";
        }
        return localFilePath + secDir + fileName;
    }

    /**
     * 下载文件 完整路径
     *
     * @param fullFileName 文件的完整路径 除了 localFilePrefix
     * @param fileName     文件名 带后缀
     * @param secPath      二级目录 在二级目录之后在进行分割 本地时不能同时开通多个不同的根目录
     * @param response     http数据流
     * @throws Exception 异常
     */
    @Override
    public void downloadFile(String fullFileName, String fileName, HttpServletResponse response, String secPath) throws Exception {
        String fullPath;
        if (StringUtils.isBlank(fullFileName)) {
            fullPath = getFullPath(secPath, fileName);
        } else {
            fullPath = getFullPath(fullFileName);
        }
        LocalFileUtil.getInstance().downloadFile(fullPath, fileName, response);
    }


    /**
     * 删除文件
     *
     * @param fullFileName 文件的完整路径 除了 localFilePrefix
     * @param fileName     文件名 带后缀
     * @param secPath      二级目录 在二级目录之后在进行分割 本地时不能同时开通多个不同的根目录
     * @throws Exception 异常
     */
    @Override
    public void deleteFile(String fullFileName, String fileName, String secPath) throws Exception {
        String fullPath;
        if (StringUtils.isBlank(fullFileName)) {
            fullPath = getFullPath(secPath, fileName);
        } else {
            fullPath = getFullPath(fullFileName);
        }
        LocalFileUtil.getInstance().deleteFile(fullPath);
    }

    /**
     * 文件预览 完整路径=path+fileName
     *
     * @param fullFileName 文件的完整路径 除了 localFilePrefix
     * @param secPath      二级目录 在二级目录之后在进行分割 本地时不能同时开通多个不同的根目录
     * @throws Exception 异常
     */
    @Override
    public String previewFile(String fullFileName, String secPath) {
        //截取掉 localFilePath
        if (StringUtils.endsWith(fullFileName, "/")) {
            // 输入的路径不是全路径无法获得正确的临时路径
            return "";
        }
        String prefix = localFilePrefix;
        if (StringUtils.isNotBlank(secPath) && !StringUtils.endsWith(secPath, "/")) {
            prefix = prefix + "/" + secPath;
        }
        if (!StringUtils.endsWith(prefix, "/") && !StringUtils.startsWith(fullFileName, "/")) {
            prefix = prefix + "/";
        }
        return domain + prefix + fullFileName;
    }

    /**
     * 获取指定首级目录下的全部文件
     *
     * @param secPath 二级目录 在二级目录之后在进行分割 本地时不能同时开通多个不同的根目录
     * @param catalog 三级 不作使用
     * @return
     */
    @Override
    public List<Map<String, Object>> listCatalogNameFile(String secPath, String catalog) {
        String path = getFullPath(secPath);
        return LocalFileUtil.getInstance().listPathFilesInfo(path);
    }
}
