package com.ruoyi.system.api.factory;

import com.ruoyi.system.api.domain.FileReq;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 文件服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileService create(Throwable throwable)
    {
        log.error("文件服务调用失败:{}", throwable.getMessage());
        return new RemoteFileService()
        {
            @Override
            public R<SysFile> upload(MultipartFile file)
            {
                return R.fail("上传文件失败:" + throwable.getMessage());
            }

            @Override
            public R<SysFile> upload(MultipartFile file, String specify) {
                return R.fail("上传文件到指定路径失败:" + throwable.getMessage());
            }

            @Override
            public Response downLoad(FileReq fileReq) {
                R.fail("下载文件失败:" + throwable.getMessage());
                return null;
            }

            @Override
            public R<byte[]> downLoadFileData(FileReq fileReq) {
                return R.fail("下载文件流失败:" + throwable.getMessage());
            }

            @Override
            public R<String> deleteFile(FileReq fileReq) {
                return R.fail("删除文件失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Map<String, Object>>> fileList(String primaryDir, String catalog) {
                return R.fail("获取文件列表失败:" + throwable.getMessage());
            }

            @Override
            public R<String> previewFile(FileReq fileReq) {
                return R.fail("获取文件临时路径失败:" + throwable.getMessage());
            }

        };
    }
}
