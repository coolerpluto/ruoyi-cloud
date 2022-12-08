package com.ruoyi.file.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileSystemUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 文件在服务器时的操作 工具类
 * ServerFile
 */
public class LocalFileUtil {
    private static LocalFileUtil instance = new LocalFileUtil();

    private LocalFileUtil() {
    }

    public static LocalFileUtil getInstance() {
        if (instance == null) {
            instance = new LocalFileUtil();
        }
        return instance;
    }

    /**
     * 直接使用  HttpServletResponse 进行下载
     * 将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存，通过响应输出流输出到前端
     *
     * @param fullFileName 指想要下载的文件的路径
     * @param fileName     新的名字带后缀
     * @param response
     */
    public void downloadFile(String fullFileName, String fileName, HttpServletResponse response) throws Exception {
        // 是指欲下载的文件的完整路径。
        File file = new File(fullFileName);
        // 检查文件是不是存在
        if (!file.exists()) {
            return;
        }
        String originName = file.getName();//没传就用原来的名字
        if (StringUtils.isBlank(fileName)) {
            fileName = originName;//没传就用原来的名字
        } else {
            String ext = originName.substring(originName.lastIndexOf(".") + 1).toLowerCase();
            //补一下后缀 防止没写后缀
            fileName = fileName + "." + ext;
        }
        // 清空response
        response.reset();
        // 设置response的字符集
        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        InputStream inputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();

        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();

    }

    /**
     * 获取文件的二进制流
     *
     * @param fullFileName 全路径
     * @return
     */
    public byte[] downloadFile(String fullFileName) throws Exception {
        InputStream inputStream = new FileInputStream(fullFileName);
        byte[] res = MinioUtil.getInstance().inputStreamToByteArray(inputStream);
        inputStream.close();
        return res;
    }

    /**
     * 删除文件或文件夹
     *
     * @param fullFileName 输入文件夹的删除文件 是路径删除路径
     */
    public String deleteFile(String fullFileName) {
        String flag = "N";
        //根据路径创建文件对象
        File file = new File(fullFileName);
        //路径是个文件且不为空时删除文件
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = "Y";
        }
        //路径是个文件夹删除文件夹
        if (file.isDirectory() && file.exists()) {
            FileSystemUtils.deleteRecursively(file);
            flag = "Y";
        }
        //删除失败时，返回 Y
        return flag;
    }

    /**
     * 获取指定目录下的全部的文件信息
     *
     * @param path
     * @return
     */
    public List<Map<String, Object>> listPathFilesInfo(String path) {
        File file = new File(path);
//        if (!file.isDirectory()) {
//            return new ArrayList<>();
//        }
        List<Map<String, Object>> res = new ArrayList<>();
        File[] files = file.listFiles();
        for (File tempFile : files) {
            if (tempFile.isDirectory()) {
                res.addAll(listPathFilesInfo(tempFile.getAbsolutePath()));
                continue;
            }
            Map<String, Object> mapTemp = new HashMap<>();
            mapTemp.put("fileName", tempFile.getName());
            mapTemp.put("lastModified", new Date(tempFile.lastModified()));
            mapTemp.put("filePath", tempFile.getPath());
            mapTemp.put("fileFullPath", tempFile.getAbsolutePath());
            mapTemp.put("size", String.format("%.2f", tempFile.length() / (1024 * 1024d)) + " MB");
            res.add(mapTemp);
        }
        return res;
    }
}
