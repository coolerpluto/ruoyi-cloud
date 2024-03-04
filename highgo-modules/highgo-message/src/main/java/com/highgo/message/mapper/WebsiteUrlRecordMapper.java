package com.highgo.message.mapper;

import java.util.List;
import com.highgo.message.domain.WebsiteUrlRecord;

/**
 * 内部网站收藏Mapper接口
 * 
 * @author esz
 * @date 2023-07-05
 */
public interface WebsiteUrlRecordMapper 
{
    /**
     * 查询内部网站收藏
     * 
     * @param id 内部网站收藏主键
     * @return 内部网站收藏
     */
    public WebsiteUrlRecord selectWebsiteUrlRecordById(Long id);

    /**
     * 查询内部网站收藏列表
     * 
     * @param websiteUrlRecord 内部网站收藏
     * @return 内部网站收藏集合
     */
    public List<WebsiteUrlRecord> selectWebsiteUrlRecordList(WebsiteUrlRecord websiteUrlRecord);

    /**
     * 新增内部网站收藏
     * 
     * @param websiteUrlRecord 内部网站收藏
     * @return 结果
     */
    public int insertWebsiteUrlRecord(WebsiteUrlRecord websiteUrlRecord);

    /**
     * 修改内部网站收藏
     * 
     * @param websiteUrlRecord 内部网站收藏
     * @return 结果
     */
    public int updateWebsiteUrlRecord(WebsiteUrlRecord websiteUrlRecord);

    /**
     * 删除内部网站收藏
     * 
     * @param id 内部网站收藏主键
     * @return 结果
     */
    public int deleteWebsiteUrlRecordById(Long id);

    /**
     * 批量删除内部网站收藏
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWebsiteUrlRecordByIds(Long[] ids);
}
