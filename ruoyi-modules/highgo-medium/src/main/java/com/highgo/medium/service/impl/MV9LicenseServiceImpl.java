package com.highgo.medium.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.highgo.medium.config.SFtpServerConfig;
import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.domain.MLicenseFileInfo;
import com.highgo.medium.mapper.MLicenseFileInfoMapper;
import com.highgo.medium.service.IMFileInfoService;
import com.highgo.medium.utils.MediumUtil;
import com.highgo.medium.utils.SSHLinuxUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.RandomUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * License文件记录Service业务层处理
 *
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MV9LicenseServiceImpl
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MLicenseFileInfoMapper mLicenseFileInfoMapper;
    @Autowired
    private IMFileInfoService fileInfoService;
    @Autowired
    private SFtpServerConfig sFtpServerConfig;

    public Map<String, Object> generator(JSONObject json)
    {
        if (log.isDebugEnabled())
        {
            log.debug("MV9LicenseServiceImpl.generator req:{}", JSON.toJSONString(json));
        }

        json.put("lic_ver", json.getString("licVer"));
        json.remove("licVer");
        json.put("lic_serial", json.getString("licSerial"));
        json.remove("licSerial");
        json.put("lic_auth_style", json.getString("licAuthStyle"));
        json.remove("licAuthStyle");
        json.put("project_info", json.getString("projectInfo"));
        json.remove("projectInfo");
        json.put("user_info", json.getString("userInfo"));
        json.remove("userInfo");
        json.put("lic_used_style", json.getString("licUsedStyle"));
        json.remove("licUsedStyle");
        Date createTime = json.getDate("createTime");
        json.put("create_time", DateUtils.dateTime(createTime));
        json.remove("createTime");
        Date finalTime = json.getDate("finalTime");
        json.put("final_time", DateUtils.dateTime(finalTime));
        json.remove("finalTime");
        json.put("max_connection", json.getString("maxConnection"));
        json.remove("maxConnection");
        json.put("max_cpu_cores", json.getString("maxCpuCores"));
        json.remove("maxCpuCores");
        json.put("is_check_hardware", json.getString("isCheckHardware"));
        json.remove("isCheckHardware");
        json.put("is_hardware_code", json.getString("isHardwareCode"));
        json.remove("isHardwareCode");
        json.put("func_list", json.getString("funcList"));
        json.remove("funcList");
        json.put("seedIndex", json.getString("seedIndex"));
        // 生成lic执行命令
        StringBuilder command = new StringBuilder();
        String reqFileName = json.getString("lic_serial") + "_" + RandomUtils.generateUpperString(5) + ".json";
        command.append("cd ").append(sFtpServerConfig.getExecHome()).append("tongruanv9/").append(" && ");
        command.append("echo '").append(json.toJSONString()).append("' > ").append("req/").append(reqFileName).append(" && ");
        command.append(sFtpServerConfig.getV9Command()).append(" -f ").append("req/").append(reqFileName);
        command.append(" -D ").append(sFtpServerConfig.getHomeDir()).append("tongruanv9/");// 生成文件的指定路径
        log.info("MV9LicenseServiceImpl.generator command:{}", command);
        // 调用执行命令
        String commandRes = SSHLinuxUtil.exeCommand(sFtpServerConfig.getIp(), sFtpServerConfig.getPort(),
                sFtpServerConfig.getAccessKey(), sFtpServerConfig.getSecretKey(), command.toString());

        log.info("MV9LicenseServiceImpl.generator commandRes:{}", commandRes);

        String[] s = commandRes.split("\n");
        String fullPath = "";
        String serial = "";
        for (String d : s)
        {
            if (d.contains("generate:"))
            {
                String[] ds = d.split(":");
                fullPath = ds[1];
            }
            if (d.contains("lic_serial:"))
            {
                String[] ds = d.split(" ");
                serial = ds[2];
            }
        }
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
        JSONObject server = new JSONObject();
        server.put("ip", sFtpServerConfig.getIp());
        server.put("accessKey", sFtpServerConfig.getAccessKey());
        server.put("secretKey", sFtpServerConfig.getSecretKey());
        server.put("port", sFtpServerConfig.getPort());
        mFileInfo.setRemark(server.toJSONString());
        fileInfoService.insertMFileInfo(mFileInfo);
        //插入数据记录
        MLicenseFileInfo param = new MLicenseFileInfo();
        param.setOpportunityNum(json.getString("lic_serial"));
        param.setAuthType(json.getString("lic_used_style"));
        param.setCustomerName(json.getString("user_info"));
        param.setPurpose(json.getString("lic_auth_style"));
        param.setExpireTime(json.getDate("final_time"));
        param.setCreateTime(json.getDate("create_time"));
        param.setProdType(json.getString("productType"));
        param.setDbVersion(json.getString("majorVersion") + "." + json.getString("minVersion"));

        param.setLicFileId(fileId);
        param.setSerial(serial);
        param.setApplyTime(DateUtils.getServerStartDate());

        StringBuilder serverUrl = new StringBuilder(sFtpServerConfig.getLicUrlTemplate());
        int firstParamIndex = serverUrl.indexOf("{1}");
        serverUrl.replace(firstParamIndex, firstParamIndex + 3, param.getSerial());
        int secParamIndex = serverUrl.indexOf("{2}");
        serverUrl.replace(secParamIndex, secParamIndex + 3, param.getDbVersion());
        param.setServerUrl(serverUrl.toString());

        JSONObject licenseFile = new JSONObject();
        licenseFile.put("serverUrl", serverUrl);
        // 构建存放整合参数
        JSONObject otherParam = new JSONObject();
        // 将lic的生成信息 存起来
        otherParam.put("licInfo", licenseFile);
        // 将lic生成需要的入参和lic生成信息 存起来
        otherParam.put("licReqInfo", json);
        param.setOtherParam(otherParam.toJSONString());
        insertMLicenseFileInfo(param);

        // 构造返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("response", licenseFile);
        // 传进来的暂时不传回去
        //resultMap.put("request", json);
        resultMap.put("code", 200);
        return resultMap;
    }

    private int insertMLicenseFileInfo(MLicenseFileInfo mLicenseFileInfo)
    {
        mLicenseFileInfo.setCreateTime(DateUtils.getNowDate());
        mLicenseFileInfo.setCreateBy(SecurityUtils.getUsername());
        mLicenseFileInfo.setCreateId(SecurityUtils.getUserId());
        return mLicenseFileInfoMapper.insertMLicenseFileInfo(mLicenseFileInfo);
    }
}
