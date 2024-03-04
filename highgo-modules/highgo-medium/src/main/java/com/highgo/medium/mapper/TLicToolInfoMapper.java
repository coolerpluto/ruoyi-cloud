package com.highgo.medium.mapper;

import java.util.List;
import com.highgo.medium.domain.TLicToolInfo;

/**
 * License工具信息Mapper接口
 * 
 * @author esz
 * @date 2023-06-28
 */
public interface TLicToolInfoMapper 
{
    /**
     * 查询License工具信息
     * 
     * @param id License工具信息主键
     * @return License工具信息
     */
    public TLicToolInfo selectTLicToolInfoById(Long id);

    /**
     * 查询License工具信息列表
     * 
     * @param tLicToolInfo License工具信息
     * @return License工具信息集合
     */
    public List<TLicToolInfo> selectTLicToolInfoList(TLicToolInfo tLicToolInfo);

    /**
     * 新增License工具信息
     * 
     * @param tLicToolInfo License工具信息
     * @return 结果
     */
    public int insertTLicToolInfo(TLicToolInfo tLicToolInfo);

    /**
     * 修改License工具信息
     * 
     * @param tLicToolInfo License工具信息
     * @return 结果
     */
    public int updateTLicToolInfo(TLicToolInfo tLicToolInfo);

    /**
     * 删除License工具信息
     * 
     * @param id License工具信息主键
     * @return 结果
     */
    public int deleteTLicToolInfoById(Long id);

    /**
     * 批量删除License工具信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTLicToolInfoByIds(Long[] ids);
}
