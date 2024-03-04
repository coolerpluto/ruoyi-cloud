package com.highgo.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.highgo.crm.domain.PolicyFile;
import com.highgo.crm.mapper.PolicyFileMapper;
import com.highgo.crm.service.IPolicyFileService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.file.ZipUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.FileReq;
import com.ruoyi.system.api.domain.SysFile;
import feign.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 政策文件Service业务层处理
 *
 * @author eszhang
 * @date 2023-01-19
 */
@Service
public class PolicyFileServiceImpl implements IPolicyFileService {
    @Autowired
    private PolicyFileMapper policyFileMapper;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 查询政策文件
     *
     * @param id 政策文件主键
     * @return 政策文件
     */
    @Override
    public PolicyFile selectPolicyFileById(Integer id) {
        return policyFileMapper.selectPolicyFileById(id);
    }

    /**
     * 查询政策文件列表
     *
     * @param policyFile 政策文件
     * @return 政策文件
     */
    @Override
    public List<PolicyFile> selectPolicyFileList(PolicyFile policyFile) {
        return policyFileMapper.selectPolicyFileList(policyFile);
    }

    /**
     * 新增政策文件
     *
     * @param policyFile 政策文件
     * @return 结果
     */
    @Override
    public int insertPolicyFile(PolicyFile policyFile) {
        policyFile.setCreateTime(DateUtils.getNowDate());
        return policyFileMapper.insertPolicyFile(policyFile);
    }

    /**
     * 修改政策文件
     *
     * @param policyFile 政策文件
     * @return 结果
     */
    @Override
    public int updatePolicyFile(PolicyFile policyFile) {
        policyFile.setUpdateTime(DateUtils.getNowDate());
        return policyFileMapper.updatePolicyFile(policyFile);
    }

    /**
     * 批量删除政策文件
     *
     * @param ids 需要删除的政策文件主键
     * @return 结果
     */
    @Override
    public int deletePolicyFileByIds(Integer[] ids) {
        return policyFileMapper.deletePolicyFileByIds(ids);
    }

    /**
     * 删除政策文件信息
     *
     * @param id 政策文件主键
     * @return 结果
     */
    @Override
    public int deletePolicyFileById(Integer id) {
        return policyFileMapper.deletePolicyFileById(id);
    }

    @Autowired
    private RemoteFileService remoteFileService;

    @Override
    public int updatePolicyFileWithFile(PolicyFile policyFile, MultipartFile policyFileFile) {
        try {
            return updatePolicyFile(uploadFile(policyFile, policyFileFile));
        } catch (IOException e) {
            log.error("政策文件上传失败：{}",e.getMessage());
        }
        return 0;
    }

    @Override
    public int insertPolicyFileWithFile(PolicyFile policyFile, MultipartFile policyFileFile) {
        try {
            return insertPolicyFile(uploadFile(policyFile, policyFileFile));
        } catch (IOException e) {
            log.error("政策文件上传失败：{}",e.getMessage());
        }
        return 0;
    }

    @Override
    public void download(HttpServletResponse response, PolicyFile policyFile) {
        if (log.isDebugEnabled()){
            log.debug("PolicyFileServiceImpl.downLoad req:{}", JSON.toJSONString(policyFile));
        }
        FileReq fileMReq = new FileReq();
        fileMReq.setFileName(policyFile.getName());

        String filePath = policyFile.getFilePath();
        int index = filePath.lastIndexOf(policyFile.getName());
        String finalPath = "";
        if (index==-1|| filePath.endsWith("/")){
            finalPath = filePath;
        }else {
            finalPath = filePath.substring(0,index);
        }
        fileMReq.setFullFileName(finalPath);

        Response fileMResp = remoteFileService.downLoad(fileMReq);
        Response.Body fileMBody = fileMResp.body();

        response.setHeader("Content-Disposition","attachment; filename="+policyFile.getName()+".zip");
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream;
        // 创建 输出流到 response
        OutputStream outputStream= null;
        try {
            outputStream = response.getOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            zipOutputStream.setMethod(ZipOutputStream.DEFLATED);

            Map<String, InputStream> fileInputStream = new HashMap<>();
            fileInputStream.put(fileMReq.getFileName(),fileMBody.asInputStream());
            ZipUtils.zipStream(fileInputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private PolicyFile uploadFile(PolicyFile policyFile, MultipartFile policyFileFile) throws IOException {
        if (policyFileFile != null) {
            R<SysFile> upFileRes = remoteFileService.upload(policyFileFile, "crm");
            if (upFileRes.getCode() != 200) {
                throw new IOException("文件上传失败！");
            }
            if (StringUtils.isBlank(policyFile.getName())) {
                policyFile.setName(upFileRes.getData().getName());
            }
            policyFile.setFilePath(upFileRes.getData().getUrl());
        }
        return policyFile;
    }
}
