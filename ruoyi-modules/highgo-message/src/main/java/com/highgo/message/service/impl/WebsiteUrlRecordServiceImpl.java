package com.highgo.message.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.message.mapper.WebsiteUrlRecordMapper;
import com.highgo.message.domain.WebsiteUrlRecord;
import com.highgo.message.service.IWebsiteUrlRecordService;

/**
 * 内部网站收藏Service业务层处理
 * 
 * @author esz
 * @date 2023-07-05
 */
@Service
public class WebsiteUrlRecordServiceImpl implements IWebsiteUrlRecordService 
{
    @Autowired
    private WebsiteUrlRecordMapper websiteUrlRecordMapper;

    /**
     * 查询内部网站收藏
     * 
     * @param id 内部网站收藏主键
     * @return 内部网站收藏
     */
    @Override
    public WebsiteUrlRecord selectWebsiteUrlRecordById(Long id)
    {
        return websiteUrlRecordMapper.selectWebsiteUrlRecordById(id);
    }

    /**
     * 查询内部网站收藏列表
     * 
     * @param websiteUrlRecord 内部网站收藏
     * @return 内部网站收藏
     */
    @Override
    public List<WebsiteUrlRecord> selectWebsiteUrlRecordList(WebsiteUrlRecord websiteUrlRecord)
    {
        return websiteUrlRecordMapper.selectWebsiteUrlRecordList(websiteUrlRecord);
    }

    /**
     * 新增内部网站收藏
     * 
     * @param websiteUrlRecord 内部网站收藏
     * @return 结果
     */
    @Override
    public int insertWebsiteUrlRecord(WebsiteUrlRecord websiteUrlRecord)
    {
        websiteUrlRecord.setCreateTime(DateUtils.getNowDate());
        return websiteUrlRecordMapper.insertWebsiteUrlRecord(websiteUrlRecord);
    }

    /**
     * 修改内部网站收藏
     * 
     * @param websiteUrlRecord 内部网站收藏
     * @return 结果
     */
    @Override
    public int updateWebsiteUrlRecord(WebsiteUrlRecord websiteUrlRecord)
    {
        websiteUrlRecord.setUpdateTime(DateUtils.getNowDate());
        return websiteUrlRecordMapper.updateWebsiteUrlRecord(websiteUrlRecord);
    }

    /**
     * 批量删除内部网站收藏
     * 
     * @param ids 需要删除的内部网站收藏主键
     * @return 结果
     */
    @Override
    public int deleteWebsiteUrlRecordByIds(Long[] ids)
    {
        return websiteUrlRecordMapper.deleteWebsiteUrlRecordByIds(ids);
    }

    /**
     * 删除内部网站收藏信息
     * 
     * @param id 内部网站收藏主键
     * @return 结果
     */
    @Override
    public int deleteWebsiteUrlRecordById(Long id)
    {
        return websiteUrlRecordMapper.deleteWebsiteUrlRecordById(id);
    }
}
