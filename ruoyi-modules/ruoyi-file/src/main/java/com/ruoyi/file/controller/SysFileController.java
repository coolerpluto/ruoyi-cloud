package com.ruoyi.file.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.file.config.SFtpServerConfig;
import com.ruoyi.system.api.domain.FileReq;
import com.ruoyi.file.service.ISysFileService;
import com.ruoyi.system.api.domain.SysFile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@RestController
public class SysFileController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private SFtpServerConfig ftpServerConfig;
    /**
     * 文件上传请求 默认路径
     */
    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            sysFile.setServerIp(ftpServerConfig.getIp());
            sysFile.setHomeDir(ftpServerConfig.getHomeDir());
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(930022000, "下载文件流失败!请稍后重试或联系管理员");
        }
    }

    /**
     * 文件上传请求 到指定目录
     * minio为桶名 fastDfs为组名 local 为path下的二级目录
     */
    @PostMapping("upSpecifyDir")
    public R<SysFile> upload(MultipartFile file, String specify) {
        if (StringUtils.isBlank(specify)) {
            return upload(file);
        }
        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file, specify);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(930022000, "下载文件流失败!请稍后重试或联系管理员");
        }
    }

    @GetMapping("downLoad")
    public void downLoad(HttpServletResponse response, FileReq fileReq) {
        try {
            sysFileService.downloadFile(fileReq.getFullFileName(), fileReq.getFileName(),
                    response, fileReq.getBucketName());
        } catch (Exception e) {
            log.error("请稍后重试或联系管理员", e);
        }
    }

    @GetMapping("downLoadStream")
    public R<byte[]> downLoadFileData(FileReq fileReq) {
        try {
            byte[] fileData = sysFileService.downloadFile(fileReq.getFullFileName(),
                    fileReq.getFileName(), fileReq.getBucketName());
            return R.ok(fileData, "success");
        } catch (Exception e) {
            log.error("下载文件流失败", e);
            return R.fail(930022002, "下载文件流失败!请稍后重试或联系管理员");
        }
    }

    @DeleteMapping("file")
    public R<String> deleteFile(FileReq fileReq) {
        try {
            sysFileService.deleteFile(fileReq.getFullFileName(), fileReq.getFileName(), fileReq.getBucketName());
            return R.ok("完成", "success");
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return R.fail(930022001, "文件删除失败!请稍后重试或联系管理员");
        }
    }

    @GetMapping("file/list")
    public R<List<Map<String, Object>>> fileList(String primaryDir, String catalog) {
        try {
            List<Map<String, Object>> tempObjectUrl = sysFileService.listCatalogNameFile(primaryDir, catalog);
            return R.ok(tempObjectUrl, "success");
        } catch (Exception e) {
            log.error("获取文件临时路径失败", e);
            return R.fail(930022003, "获取文件临时路径失败!请稍后重试或联系管理员");
        }
    }

    @GetMapping("file/previewFile")
    public R<String> previewFile(FileReq fileReq) {
        try {
            String tempObjectUrl = sysFileService.previewFile(fileReq.getFullFileName(), fileReq.getBucketName());
            return R.ok(tempObjectUrl, "success");
        } catch (Exception e) {
            log.error("获取文件临时路径失败", e);
            return R.fail(930022003, "获取文件临时路径失败!请稍后重试或联系管理员");
        }
    }
}
