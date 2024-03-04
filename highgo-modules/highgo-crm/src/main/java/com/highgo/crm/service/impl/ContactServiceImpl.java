package com.highgo.crm.service.impl;

import com.highgo.crm.domain.Contact;
import com.highgo.crm.mapper.ContactMapper;
import com.highgo.crm.service.IContactService;
import com.highgo.crm.utils.CRMUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人Service业务层处理
 *
 * @author eszhang
 * @date 2023-02-02
 */
@Service
public class ContactServiceImpl implements IContactService {
    @Autowired
    private ContactMapper contactMapper;

    /**
     * 查询联系人
     *
     * @param id 联系人主键
     * @return 联系人
     */
    @Override
    public Contact selectContactById(String id) {
        return contactMapper.selectContactById(id);
    }

    /**
     * 查询联系人列表
     *
     * @param contact 联系人
     * @return 联系人
     */
    @Override
    public List<Contact> selectContactList(Contact contact) {
        return contactMapper.selectContactList(contact);
    }

    /**
     * 新增联系人
     *
     * @param contact 联系人
     * @return 结果
     */
    @Override
    public int insertContact(Contact contact) {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        contact.setCreateId(userCurrent.getUserId());
        contact.setCreateBy(SecurityUtils.getUsername());
        contact.setOwnerId(userCurrent.getUserId());
        contact.setOwnerDeptId(userCurrent.getDeptId());
        contact.setCreateTime(DateUtils.getNowDate());
        return contactMapper.insertContact(contact);
    }

    /**
     * 修改联系人
     *
     * @param contact 联系人
     * @return 结果
     */
    @Override
    public int updateContact(Contact contact) {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        contact.setUpdateId(userCurrent.getUserId());
        contact.setUpdateBy(SecurityUtils.getUsername());
        contact.setUpdateTime(DateUtils.getNowDate());
        return contactMapper.updateContact(contact);
    }

    /**
     * 批量删除联系人
     *
     * @param ids 需要删除的联系人主键
     * @return 结果
     */
    @Override
    public int deleteContactByIds(String[] ids) {
        return contactMapper.deleteContactByIds(ids);
    }

    /**
     * 删除联系人信息
     *
     * @param id 联系人主键
     * @return 结果
     */
    @Override
    public int deleteContactById(String id) {
        return contactMapper.deleteContactById(id);
    }

    @Override
    public int transfer(Contact contact) {
        Object temp = contact.getParams().get("contactIds");
        List<String> contactResIds = CRMUtil.castList(temp,String.class);
        Long targetOwnerId = contact.getOwnerId();
        return contactMapper.changeContactOwners(targetOwnerId, contactResIds);
    }
}
