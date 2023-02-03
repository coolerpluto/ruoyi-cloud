package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.Application;
import org.apache.ibatis.annotations.Param;

/**
 * 应用信息Mapper接口
 *
 * @author eszhang
 * @date 2023-02-02
 */
public interface ApplicationMapper
{
    /**
     * 查询应用信息
     *
     * @param id 应用信息主键
     * @return 应用信息
     */
    public Application selectApplicationById(String id);

    /**
     * 查询应用信息列表
     *
     * @param application 应用信息
     * @return 应用信息集合
     */
    public List<Application> selectApplicationList(Application application);

    /**
     * 新增应用信息
     *
     * @param application 应用信息
     * @return 结果
     */
    public int insertApplication(Application application);

    /**
     * 修改应用信息
     *
     * @param application 应用信息
     * @return 结果
     */
    public int updateApplication(Application application);

    /**
     * 删除应用信息
     *
     * @param id 应用信息主键
     * @return 结果
     */
    public int deleteApplicationById(String id);

    /**
     * 批量删除应用信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApplicationByIds(String[] ids);

    int changeCompanyOwners(@Param("targetOwnerId") Long targetOwnerId, List<String> applicationResIds);
}
