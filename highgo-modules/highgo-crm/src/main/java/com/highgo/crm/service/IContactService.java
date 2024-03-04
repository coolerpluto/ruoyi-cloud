package com.highgo.crm.service;

import java.util.List;
import com.highgo.crm.domain.Contact;

/**
 * 联系人Service接口
 * 
 * @author eszhang
 * @date 2023-02-02
 */
public interface IContactService 
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
     * 批量删除联系人
     * 
     * @param ids 需要删除的联系人主键集合
     * @return 结果
     */
    public int deleteContactByIds(String[] ids);

    /**
     * 删除联系人信息
     * 
     * @param id 联系人主键
     * @return 结果
     */
    public int deleteContactById(String id);

    int transfer(Contact contact);
}
