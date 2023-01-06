package com.highgo.medium.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.highgo.medium.config.SFtpServerConfig;
import com.highgo.medium.domain.MFileDownHis;
import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.domain.MLicenseFileInfo;
import com.highgo.medium.mapper.MLicenseFileInfoMapper;
import com.highgo.medium.service.IMFileDownHisService;
import com.highgo.medium.service.IMFileInfoService;
import com.highgo.medium.service.IMLicenseFileInfoService;
import com.highgo.medium.utils.JsonUtils;
import com.highgo.medium.utils.MediumUtil;
import com.highgo.medium.utils.SSHLinuxUtil;
import com.highgo.medium.utils.ZipUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.FileReq;
import feign.Response;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * License文件记录Service业务层处理
 *
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MLicenseFileInfoServiceImpl implements IMLicenseFileInfoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MLicenseFileInfoMapper mLicenseFileInfoMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private IMFileInfoService fileInfoService;
    @Autowired
    private SFtpServerConfig sFtpServerConfig;
    @Autowired
    private IMFileInfoService imFileInfoService;
    @Autowired
    private IMFileDownHisService fileDownHisService;
    /**
     * 查询License文件记录
     *
     * @param id License文件记录主键
     * @return License文件记录
     */
    @Override
    public MLicenseFileInfo selectMLicenseFileInfoById(Long id) {
        return mLicenseFileInfoMapper.selectMLicenseFileInfoById(id);
    }

    /**
     * 查询License文件记录列表
     *
     * @param mLicenseFileInfo License文件记录
     * @return License文件记录
     */
    @Override
    public List<MLicenseFileInfo> selectMLicenseFileInfoList(MLicenseFileInfo mLicenseFileInfo) {
        return mLicenseFileInfoMapper.selectMLicenseFileInfoList(mLicenseFileInfo);
    }

    /**
     * 新增License文件记录
     *
     * @param mLicenseFileInfo License文件记录
     * @return 结果
     */
    @Override
    public int insertMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo) {
        mLicenseFileInfo.setCreateTime(DateUtils.getNowDate());
        mLicenseFileInfo.setCreateBy(SecurityUtils.getUsername());
        mLicenseFileInfo.setCreateId(SecurityUtils.getUserId());
        return mLicenseFileInfoMapper.insertMLicenseFileInfo(mLicenseFileInfo);
    }

    /**
     * 修改License文件记录
     *
     * @param mLicenseFileInfo License文件记录
     * @return 结果
     */
    @Override
    public int updateMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo) {
        mLicenseFileInfo.setUpdateTime(DateUtils.getNowDate());
        mLicenseFileInfo.setUpdateBy(SecurityUtils.getUsername());
        mLicenseFileInfo.setUpdateId(SecurityUtils.getUserId());
        return mLicenseFileInfoMapper.updateMLicenseFileInfo(mLicenseFileInfo);
    }

    /**
     * 批量删除License文件记录
     *
     * @param ids 需要删除的License文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMLicenseFileInfoByIds(Long[] ids) {
        return mLicenseFileInfoMapper.deleteMLicenseFileInfoByIds(ids);
    }

    /**
     * 删除License文件记录信息
     *
     * @param id License文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMLicenseFileInfoById(Long id) {
        return mLicenseFileInfoMapper.deleteMLicenseFileInfoById(id);
    }

    @Override
    public void download(HttpServletResponse response, String licenseId) {
        if (log.isDebugEnabled()) {
            log.debug("MLicenseFileInfoServiceImpl.downLoad req:{}", JSON.toJSONString(licenseId));
        }
        List<MLicenseFileInfo> licenseInfo = mLicenseFileInfoMapper.selectMLicenseFileInfoByIds(licenseId);
        if (log.isDebugEnabled()) {
            log.debug("MLicenseFileInfoServiceImpl.downLoad licenseInfo:{}", JSON.toJSONString(licenseInfo));
        }
        if (licenseInfo.size()==0){
            return;
        }
        String fileId = licenseInfo.get(0).getLicFileId();
        String serial = licenseInfo.get(0).getSerial();
        MFileInfo fileInfo = imFileInfoService.selectMFileInfoById(Long.valueOf(fileId));
        String fileNameInDB = fileInfo.getFileName();
        String filePath = fileInfo.getFilePath();

        String finalPath = "";
        int index = filePath.lastIndexOf(fileNameInDB);
        if (index==-1|| filePath.endsWith("/")){
            finalPath = filePath;
        }else {
            finalPath = filePath.substring(0,index);
        }
        // 准备下载
        FileReq fileMReq = new FileReq();
        fileMReq.setFileName(fileNameInDB);
        fileMReq.setFullFileName(finalPath);
        Response fileMResp = remoteFileService.downLoad(fileMReq);
        Response.Body fileMBody = fileMResp.body();
        // 准备压缩
        response.setHeader("Content-Disposition","attachment; filename="+serial+".zip");
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream;
        // 创建 输出流到 response
        OutputStream outputStream= null;
        try {
            outputStream = response.getOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            zipOutputStream.setMethod(ZipOutputStream.DEFLATED);
            Map<String, InputStream> fileInputStream = new HashMap<>();
            fileInputStream.put(serial+"_"+fileNameInDB,fileMBody.asInputStream());
            ZipUtil.zipStream(fileInputStream,outputStream);
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
        // 单独保存下载记录 供专门菜单统计
        MFileDownHis hisReq = new MFileDownHis();
        hisReq.setFileId(fileId);
        hisReq.setFileType("license");
        hisReq.setOpportunityNum(licenseInfo.get(0).getOpportunityNum());
        hisReq.setCreateBy(SecurityUtils.getUsername());
        hisReq.setCreateId(SecurityUtils.getUserId());
        hisReq.setRemark(licenseInfo.get(0).getSerial());
        fileDownHisService.insertMFileDownHis(hisReq);
    }

    @Override
    public Map<String, Object> generator(JSONObject json) {
        if (log.isDebugEnabled()) {
            log.debug("MLicenseFileInfoServiceImpl.downLoad req:{}", JSON.toJSONString(json));
        }

        MLicenseFileInfo param = JSONObject.toJavaObject(json, MLicenseFileInfo.class);

        // 生成lic执行命令
        StringBuilder command = new StringBuilder();
        command.append(sFtpServerConfig.getCommandDir()).append(" ");// 工具的执行命令文件的全路径
        command.append(" -f").append(sFtpServerConfig.getHomeDir());// 生成文件的指定路径
        command.append(" -b").append(param.getOpportunityNum());// 加装商机编号
        command.append(" -i").append(param.getAuthType());// 加装数据库授权方式
        command.append(" -u").append(param.getCustomerName());// 加装最终用户
        command.append(" -m").append(param.getPurpose());//加装授权用途

        Date expireTime = param.getExpireTime();
        String expireTimeStr;
        if (expireTime != null) {
            expireTimeStr = DateFormatUtils.format(expireTime, "yyyyMMdd");
        } else {
            expireTimeStr = "u";// 不限制
        }

        switch (param.getProdType()) {
            case "2":
                command.append(" -p").append("db_ha/").append(expireTimeStr);//加装要管控的其他产品和有效期 格式为db_ha/xxx、hg_proxy/xxx
                break;
            case "1":
                command.append(" -p").append("hg_proxy/").append(expireTimeStr);//加装要管控的其他产品和有效期 格式为db_ha/xxx、hg_proxy/xxx
                break;
            default:
                // 选择数据库产品
                command.append(" -a").append(expireTimeStr);//加装截止时间
                command.append(" -v").append(param.getDbVersion());//加装数据库版本
        }
        if (log.isDebugEnabled()) {
            log.debug("MLicenseFileInfoServiceImpl.command req:{}", command.toString());
        }
        // 调用执行命令
        String commandRes = SSHLinuxUtil.exeCommand(sFtpServerConfig.getIp(), sFtpServerConfig.getPort(),
                sFtpServerConfig.getAccessKey(), sFtpServerConfig.getSecretKey(), command.toString());
        if (log.isDebugEnabled()) {
            log.debug("MLicenseFileInfoServiceImpl.command res:{}", commandRes);
        }
        JSONObject licenseFile = JsonUtils.parse(commandRes, JSONObject.class);

        // 构建存放整合参数
        JSONObject otherParam = new JSONObject();
        // 将lic的生成信息 存起来
        otherParam.put("licInfo", licenseFile);
        // 将lic生成需要的入参和lic生成信息 存起来
        otherParam.put("licReqInfo", json);
        param.setOtherParam(otherParam.toJSONString());

        String fullPath = (String) licenseFile.get("path");
        int pathL = fullPath.lastIndexOf("/");
        String licPath = fullPath.substring(0, pathL + 1);
        String licName = fullPath.substring(pathL + 1);
        String fileType = licName.split("\\.")[1];

        // 将文件记录插入 记录表
        MFileInfo mFileInfo = new MFileInfo();
        String fileId = MediumUtil.getId();
        mFileInfo.setId(fileId);
        mFileInfo.setFileName(licName);
        mFileInfo.setFileType(fileType);
        mFileInfo.setFilePath(licPath);
        mFileInfo.setFileServer(sFtpServerConfig.getIp());
        fileInfoService.insertMFileInfo(mFileInfo);

        //插入数据记录
        param.setLicFileId(fileId);
        param.setSerial((String) licenseFile.get("serial"));
        param.setApplyTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,(String)licenseFile.get("product_time")));

        StringBuilder serverUrl = new StringBuilder(sFtpServerConfig.getLicUrlTemplate());
        int firstParamIndex = serverUrl.indexOf("{1}");
        serverUrl.replace(firstParamIndex, firstParamIndex + 3, param.getSerial());
        int secParamIndex = serverUrl.indexOf("{2}");
        serverUrl.replace(secParamIndex, secParamIndex + 3, param.getDbVersion());
        param.setServerUrl(serverUrl.toString());
        licenseFile.put("serverUrl",serverUrl);

        insertMLicenseFileInfo(param);
        // 构造返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("response", licenseFile);
        // 传进来的暂时不传回去
        //resultMap.put("request", json);
        resultMap.put("code", 200);
        return resultMap;
    }


}
