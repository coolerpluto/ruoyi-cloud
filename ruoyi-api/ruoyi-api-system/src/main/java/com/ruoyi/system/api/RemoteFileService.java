package com.ruoyi.system.api;

import com.ruoyi.system.api.domain.FileReq;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysFile;
import com.ruoyi.system.api.factory.RemoteFileFallbackFactory;

import java.util.List;
import java.util.Map;

/**
 * 文件服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);

    /**
     * 上传文件
     *
     * @param file 文件信息 指定的目录下
     * @return 结果
     */
    @PostMapping(value = "/upSpecifyDir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "specify") String specify);

    /**
     * 下载文件
     */
    @GetMapping(value = "/downLoad")
    public Response downLoad(@SpringQueryMap FileReq fileReq);

    /**
     * 下载文件流
     */
    @GetMapping(value = "/downLoadStream")
    public R<byte[]> downLoadFileData(@SpringQueryMap FileReq fileReq);

    /**
     * 删除文件
     */
    @DeleteMapping(value = "/file")
    public R<String> deleteFile(FileReq fileReq);

    /**
     * 查询目录下的文件
     */
    @GetMapping(value = "/file/list")
    public R<List<Map<String, Object>>> fileList(@RequestParam(value = "primaryDir") String primaryDir, @RequestParam(value = "catalog") String catalog);

    /**
     * 获取文件的预览URL
     *
     * @param fileReq 入参
     */
    @GetMapping(value = "/file/previewFile")
    public R<String> previewFile(@SpringQueryMap FileReq fileReq);
}
