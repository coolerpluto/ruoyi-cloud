package com.ruoyi.file.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * 文件上传接口
 *
 * @author ruoyi
 */
public interface ISysFileService {
    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception;

    /**
     * 文件上传接口 指定首级目录
     *
     * @param file       上传的文件
     * @param primaryDir 首级目录 按格式匹配
     * @return 访问地址
     * @throws Exception
     */
    public String uploadFile(MultipartFile file, String primaryDir) throws Exception;

    /**
     * 下载文件 完整路径=fullFileName
     *
     * @param fullFileName 文件的路径
     * @param fileName   文件名 带后缀
     * @throws Exception 异常
     */
    public InputStream downloadFile(String fullFileName, String fileName, String primaryDir) ;

    /**
     * 下载文件 完整路径
     *
     * @param fileFullPath       完整路径
     * @param fileName   文件名 带后缀
     * @param response   http数据流
     * @param primaryDir 首级目录 按格式匹配
     * @throws Exception 异常
     */
    public void downloadFile(String fileFullPath, String fileName, HttpServletResponse response, String primaryDir) ;

    /**
     * 下删除文件 完整路径=path+fileName
     *
     * @param path     文件的路径
     * @param fileName 文件名 带后缀
     * @param response http数据流
     * @throws Exception 异常
     */
    public void deleteFile(String path, String fileName, HttpServletResponse response) throws Exception;

    /**
     * 下删除文件 完整路径=path+fileName
     *
     * @param path       文件的路径
     * @param fileName   文件名 带后缀
     * @param response   http数据流
     * @param primaryDir 首级目录 按格式匹配
     * @throws Exception 异常
     */
    public void deleteFile(String path, String fileName, HttpServletResponse response, String primaryDir) throws Exception;

    /**
     * 文件预览 完整路径=path+fileName
     *
     * @param path     文件的路径
     * @param fileName 文件名 带后缀
     * @param response http数据流
     * @throws Exception 异常
     */
    public String previewFile(String path, String fileName, HttpServletResponse response) throws Exception;

    /**
     * 文件预览 完整路径=path+fileName
     *
     * @param path       文件的路径
     * @param fileName   文件名 带后缀
     * @param response   http数据流
     * @param primaryDir 首级目录 按格式匹配
     * @throws Exception 异常
     */
    public String previewFile(String path, String fileName, HttpServletResponse response, String primaryDir) throws Exception;
}
