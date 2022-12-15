package com.highgo.medium.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.medium.mapper.MediumSecurityInfoMapper;
import com.highgo.medium.domain.MediumSecurityInfo;
import com.highgo.medium.service.IMediumSecurityInfoService;

import javax.servlet.http.HttpServletResponse;

/**
 * 介质安全文件记录Service业务层处理
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@Service
public class MediumSecurityInfoServiceImpl implements IMediumSecurityInfoService 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MediumSecurityInfoMapper mediumSecurityInfoMapper;

    /**
     * 查询介质安全文件记录
     * 
     * @param id 介质安全文件记录主键
     * @return 介质安全文件记录
     */
    @Override
    public MediumSecurityInfo selectMediumSecurityInfoById(Long id)
    {
        return mediumSecurityInfoMapper.selectMediumSecurityInfoById(id);
    }

    /**
     * 查询介质安全文件记录列表
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 介质安全文件记录
     */
    @Override
    public List<MediumSecurityInfo> selectMediumSecurityInfoList(MediumSecurityInfo mediumSecurityInfo)
    {
        return mediumSecurityInfoMapper.selectMediumSecurityInfoList(mediumSecurityInfo);
    }

    /**
     * 新增介质安全文件记录
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 结果
     */
    @Override
    public int insertMediumSecurityInfo(MediumSecurityInfo mediumSecurityInfo)
    {
        mediumSecurityInfo.setCreateTime(DateUtils.getNowDate());
        return mediumSecurityInfoMapper.insertMediumSecurityInfo(mediumSecurityInfo);
    }

    /**
     * 修改介质安全文件记录
     * 
     * @param mediumSecurityInfo 介质安全文件记录
     * @return 结果
     */
    @Override
    public int updateMediumSecurityInfo(MediumSecurityInfo mediumSecurityInfo)
    {
        mediumSecurityInfo.setUpdateTime(DateUtils.getNowDate());
        return mediumSecurityInfoMapper.updateMediumSecurityInfo(mediumSecurityInfo);
    }

    /**
     * 批量删除介质安全文件记录
     * 
     * @param ids 需要删除的介质安全文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumSecurityInfoByIds(Long[] ids)
    {
        return mediumSecurityInfoMapper.deleteMediumSecurityInfoByIds(ids);
    }

    /**
     * 删除介质安全文件记录信息
     * 
     * @param id 介质安全文件记录主键
     * @return 结果
     */
    @Override
    public int deleteMediumSecurityInfoById(Long id)
    {
        return mediumSecurityInfoMapper.deleteMediumSecurityInfoById(id);
    }

    @Override
    public void download(HttpServletResponse response, MediumSecurityInfo mediumSecurityInfo) {
        if (log.isDebugEnabled()){
            log.debug("MediumSecurityInfoServiceImpl.downLoad req:{}", JSON.toJSONString(mediumSecurityInfo));
        }
    }
}
