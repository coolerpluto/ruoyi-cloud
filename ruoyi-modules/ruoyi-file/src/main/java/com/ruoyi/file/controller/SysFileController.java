package com.ruoyi.file.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.file.domain.FileReq;
import com.ruoyi.file.service.ISysFileService;
import com.ruoyi.system.api.domain.SysFile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    @PostMapping("downLoad")
    public void downLoad(HttpServletResponse res,FileReq fileReq) {
        String fullPath="";
        if (StringUtils.isBlank(fileReq.getFullFileName())){
            fullPath= fileReq.getFilePath()+"/"+fileReq.getFileName();
        }else {
            fullPath = fileReq.getFullFileName();
        }
        log.debug(fileReq.toString());
        //sysFileService.downloadFile(fullPath, fileReq.getFileName(), res, fileReq.getBucketName());
    }
}
