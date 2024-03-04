package com.highgo.medium.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.service.IMFileInfoService;
import com.highgo.medium.utils.MediumUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.TLicToolInfoMapper;
import com.highgo.medium.domain.TLicToolInfo;
import com.highgo.medium.service.ITLicToolInfoService;
import org.springframework.web.multipart.MultipartFile;

/**
 * License工具信息Service业务层处理
 * 
 * @author esz
 * @date 2023-06-28
 */
@Service
public class TLicToolInfoServiceImpl implements ITLicToolInfoService 
{
    @Autowired
    private TLicToolInfoMapper tLicToolInfoMapper;

    /**
     * 查询License工具信息
     * 
     * @param id License工具信息主键
     * @return License工具信息
     */
    @Override
    public TLicToolInfo selectTLicToolInfoById(Long id)
    {
        return tLicToolInfoMapper.selectTLicToolInfoById(id);
    }

    /**
     * 查询License工具信息列表
     * 
     * @param tLicToolInfo License工具信息
     * @return License工具信息
     */
    @Override
    public List<TLicToolInfo> selectTLicToolInfoList(TLicToolInfo tLicToolInfo)
    {
        return tLicToolInfoMapper.selectTLicToolInfoList(tLicToolInfo);
    }

    /**
     * 新增License工具信息
     * 
     * @param tLicToolInfo License工具信息
     * @return 结果
     */
    @Override
    public int insertTLicToolInfo(TLicToolInfo tLicToolInfo)
    {
        tLicToolInfo.setCreateTime(DateUtils.getNowDate());
        tLicToolInfo.setCreateBy(SecurityUtils.getUsername());
        tLicToolInfo.setCreateId(SecurityUtils.getUserId());
        return tLicToolInfoMapper.insertTLicToolInfo(tLicToolInfo);
    }

    /**
     * 修改License工具信息
     * 
     * @param tLicToolInfo License工具信息
     * @return 结果
     */
    @Override
    public int updateTLicToolInfo(TLicToolInfo tLicToolInfo)
    {
        tLicToolInfo.setUpdateTime(DateUtils.getNowDate());
        tLicToolInfo.setUpdateBy(SecurityUtils.getUsername());
        tLicToolInfo.setUpdateId(SecurityUtils.getUserId());
        return tLicToolInfoMapper.updateTLicToolInfo(tLicToolInfo);
    }

    /**
     * 批量删除License工具信息
     * 
     * @param ids 需要删除的License工具信息主键
     * @return 结果
     */
    @Override
    public int deleteTLicToolInfoByIds(Long[] ids)
    {
        return tLicToolInfoMapper.deleteTLicToolInfoByIds(ids);
    }

    /**
     * 删除License工具信息信息
     * 
     * @param id License工具信息主键
     * @return 结果
     */
    @Override
    public int deleteTLicToolInfoById(Long id)
    {
        return tLicToolInfoMapper.deleteTLicToolInfoById(id);
    }

    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private IMFileInfoService imFileInfoService;

    private int fileUpload(TLicToolInfo tLicToolInfo, MultipartFile toolFile, MultipartFile macFile)
    {
        List<MFileInfo> list = new ArrayList<>();
        if (toolFile != null)
        {
            R<SysFile> upLicTool = remoteFileService.upload(toolFile, "medium");
            if (upLicTool.getCode() != 200)
            {
                return 0;
            }
            MFileInfo medium = new MFileInfo();
            medium.setId(MediumUtil.getId());
            String fileName = upLicTool.getData().getName();
            medium.setFileName(fileName);
            medium.setFilePath(upLicTool.getData().getUrl());
            medium.setFileServer(upLicTool.getData().getServerIp());
            medium.setFileType(fileName.substring(fileName.lastIndexOf(".")));
            medium.setCreateTime(new Date());
            medium.setCreateId(tLicToolInfo.getCreateId());
            medium.setCreateBy(tLicToolInfo.getCreateBy());
            list.add(medium);
            tLicToolInfo.setToolFileId(Long.valueOf(medium.getId()));
        }
        if (macFile != null)
        {
            R<SysFile> upMacTool = remoteFileService.upload(macFile, "medium");
            if (upMacTool.getCode() != 200)
            {
                return 0;
            }
            SysFile upRes = upMacTool.getData();
            MFileInfo mac = new MFileInfo();
            mac.setId(MediumUtil.getId());
            String fileNameMd5 = upRes.getName();
            mac.setFileName(fileNameMd5);
            mac.setFilePath(upRes.getUrl());
            mac.setFileServer(upRes.getServerIp());
            mac.setRemark("直接下载连接：http://"+upRes.getServerIp()+":88/download/"+upRes.getUrl());
            mac.setFileType(fileNameMd5.substring(fileNameMd5.lastIndexOf(".")));
            mac.setCreateTime(new Date());
            mac.setCreateId(tLicToolInfo.getCreateId());
            mac.setCreateBy(tLicToolInfo.getCreateBy());
            list.add(mac);
            tLicToolInfo.setMacFileId(Long.valueOf(mac.getId()));
        }
        if (list.size() > 0)
        {
            imFileInfoService.insertMFileInfoBath(list);
        }
        return 1;
    }

    @Override
    public int insertTLicToolInfoWithFile(TLicToolInfo tLicToolInfo, MultipartFile toolFile, MultipartFile macFile)
    {
        tLicToolInfo.setCreateTime(DateUtils.getNowDate());
        tLicToolInfo.setCreateBy(SecurityUtils.getUsername());
        tLicToolInfo.setCreateId(SecurityUtils.getUserId());

        if (0 == fileUpload(tLicToolInfo, toolFile, macFile))
        {
            return 0;
        }
        return tLicToolInfoMapper.insertTLicToolInfo(tLicToolInfo);
    }

    @Override
    public int updateTLicToolInfoWithFile(TLicToolInfo tLicToolInfo, MultipartFile toolFile, MultipartFile macFile)
    {
        tLicToolInfo.setUpdateTime(DateUtils.getNowDate());
        tLicToolInfo.setUpdateBy(SecurityUtils.getUsername());
        tLicToolInfo.setUpdateId(SecurityUtils.getUserId());
        if (0 == fileUpload(tLicToolInfo, toolFile, macFile))
        {
            return 0;
        }
        return tLicToolInfoMapper.updateTLicToolInfo(tLicToolInfo);
    }
}
