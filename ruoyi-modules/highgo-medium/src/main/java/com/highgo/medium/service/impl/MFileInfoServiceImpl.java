package com.highgo.medium.service.impl;

import com.alibaba.fastjson.JSON;
import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.mapper.MFileInfoMapper;
import com.highgo.medium.service.IMFileInfoService;
import com.highgo.medium.utils.MediumUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.FileReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 文件记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MFileInfoServiceImpl implements IMFileInfoService 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MFileInfoMapper mFileInfoMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    /**
     * 查询文件记录
     * 
     * @param id 文件记录主键
     * @return 文件记录
     */
    @Override
    public MFileInfo selectMFileInfoById(Long id)
    {
        return mFileInfoMapper.selectMFileInfoById(id);
    }
    @Override
    public List<MFileInfo> selectMFileInfoByIds(List<String> ids)
    {
        return mFileInfoMapper.selectMFileInfoByIds(ids);
    }

    @Override
    public List<MFileInfo> getMFileInfoByMediumIds(List<String> mediumIds) {
        return mFileInfoMapper.getMFileInfoByMediumIds(mediumIds);
    }

    @Override
    public List<MFileInfo> getMFileInfoBySecurityIds(List<String> securityIds) {
        return  mFileInfoMapper.getMFileInfoBySecurityIds(securityIds);
    }

    @Override
    public List<Map<String, Object>> getMFileAssociationStatus(Long id) {
        return mFileInfoMapper.getMFileAssociationStatus(id);
    }

    /**
     * 查询文件记录列表
     * 
     * @param mFileInfo 文件记录
     * @return 文件记录
     */
    @Override
    public List<MFileInfo> selectMFileInfoList(MFileInfo mFileInfo)
    {
        return mFileInfoMapper.selectMFileInfoList(mFileInfo);
    }

    /**
     * 新增文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    @Override
    public int insertMFileInfo(MFileInfo mFileInfo)
    {
        mFileInfo.setCreateTime(DateUtils.getNowDate());
        mFileInfo.setCreateBy(SecurityUtils.getUsername());
        mFileInfo.setCreateId(SecurityUtils.getUserId());
        return mFileInfoMapper.insertMFileInfo(mFileInfo);
    }

    @Override
    public int insertMFileInfoBath(List<MFileInfo> mFileInfos) {
        int res = 0;
        for (MFileInfo mFileInfo:mFileInfos ) {
            if (StringUtils.isBlank(mFileInfo.getId())) {
                mFileInfo.setId(MediumUtil.getId());
            }
            mFileInfo.setCreateTime(DateUtils.getNowDate());
            mFileInfo.setCreateBy(SecurityUtils.getUsername());
            mFileInfo.setCreateId(SecurityUtils.getUserId());
            res=res+mFileInfoMapper.insertMFileInfo(mFileInfo);
        }
        return res;
    }

    /**
     * 修改文件记录
     * 
     * @param mFileInfo 文件记录
     * @return 结果
     */
    @Override
    public int updateMFileInfo(MFileInfo mFileInfo)
    {
        mFileInfo.setUpdateTime(DateUtils.getNowDate());
        mFileInfo.setUpdateBy(SecurityUtils.getUsername());
        mFileInfo.setUpdateId(SecurityUtils.getUserId());
        return mFileInfoMapper.updateMFileInfo(mFileInfo);
    }

    /**
     * 批量删除文件记录
     * 
     * @param ids 需要删除的文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMFileInfoByIds(Long[] ids)
    {
        List list = Arrays.asList(ids);
        List files = mFileInfoMapper.selectMFileInfoByIds(list);
        if(!delFileOnServer(files)){
            return 0;
        }
        return mFileInfoMapper.deleteMFileInfoByIds(ids);
    }
    private boolean delFileOnServer(List<MFileInfo> list){
        log.info("delFileOnServer:{}",JSON.toJSONString(list));
        try{
            for (MFileInfo file:list) {
                String filePath = file.getFilePath();
                String fileNameInDB = file.getFileName();
                int index = filePath.lastIndexOf(fileNameInDB);
                String finalPath = "";
                if (index==-1|| filePath.endsWith("/")){
                    finalPath = filePath;
                }else {
                    finalPath = filePath.substring(0,index);
                }
                FileReq delReq = new FileReq();
                delReq.setFullFileName(finalPath);
                delReq.setFileName(fileNameInDB);
                remoteFileService.deleteFile(delReq);
            }
            return true;
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }
    /**
     * 删除文件记录信息
     * 
     * @param id 文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMFileInfoById(Long id)
    {
        return mFileInfoMapper.deleteMFileInfoById(id);
    }

    @Override
    public void download(HttpServletResponse response, MFileInfo mFileInfo) {
        if (log.isDebugEnabled()){
            log.debug("MFileInfoServiceImpl.downLoad req:{}", JSON.toJSONString(mFileInfo));
        }
    }
}
