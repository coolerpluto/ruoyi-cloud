package com.highgo.crm.mapper;

import java.util.List;
import com.highgo.crm.domain.Contact;
import org.apache.ibatis.annotations.Param;

/**
 * 联系人Mapper接口
 * 
 * @author eszhang
 * @date 2023-02-02
 */
public interface ContactMapper 
{
    /**
     * 查询联系人
     * 
     * @param id 联系人主键
     * @return 联系人
     */
    public Contact selectContactById(String id);

    /**
     * 查询联系人列表
     * 
     * @param contact 联系人
     * @return 联系人集合
     */
    public List<Contact> selectContactList(Contact contact);

    /**
     * 新增联系人
     * 
     * @param contact 联系人
     * @return 结果
     */
    public int insertContact(Contact contact);

    /**
     * 修改联系人
     * 
     * @param contact 联系人
     * @return 结果
     */
    public int updateContact(Contact contact);

    /**
     * 删除联系人
     * 
     * @param id 联系人主键
     * @return 结果
     */
    public int deleteContactById(String id);

    /**
     * 批量删除联系人
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContactByIds(String[] ids);

    int changeContactOwners(@Param("targetOwnerId") Long targetOwnerId, @Param("contactResIds") List<String> contactResIds);
}
