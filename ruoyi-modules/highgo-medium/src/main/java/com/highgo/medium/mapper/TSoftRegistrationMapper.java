package com.highgo.medium.mapper;

import java.util.List;
import com.highgo.medium.domain.TSoftRegistration;

/**
 * 软著登记记录Mapper接口
 * 
 * @author esz
 * @date 2023-06-28
 */
public interface TSoftRegistrationMapper 
{
    /**
     * 查询软著登记记录
     * 
     * @param id 软著登记记录主键
     * @return 软著登记记录
     */
    public TSoftRegistration selectTSoftRegistrationById(Long id);

    /**
     * 查询软著登记记录列表
     * 
     * @param tSoftRegistration 软著登记记录
     * @return 软著登记记录集合
     */
    public List<TSoftRegistration> selectTSoftRegistrationList(TSoftRegistration tSoftRegistration);

    /**
     * 新增软著登记记录
     * 
     * @param tSoftRegistration 软著登记记录
     * @return 结果
     */
    public int insertTSoftRegistration(TSoftRegistration tSoftRegistration);

    /**
     * 修改软著登记记录
     * 
     * @param tSoftRegistration 软著登记记录
     * @return 结果
     */
    public int updateTSoftRegistration(TSoftRegistration tSoftRegistration);

    /**
     * 删除软著登记记录
     * 
     * @param id 软著登记记录主键
     * @return 结果
     */
    public int deleteTSoftRegistrationById(Long id);

    /**
     * 批量删除软著登记记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSoftRegistrationByIds(Long[] ids);
}
