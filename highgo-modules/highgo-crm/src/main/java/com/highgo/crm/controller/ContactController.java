package com.highgo.crm.controller;

import com.highgo.crm.domain.Contact;
import com.highgo.crm.service.IContactService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 联系人Controller
 *
 * @author eszhang
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/contact")
public class ContactController extends BaseController {
    @Autowired
    private IContactService contactService;

    /**
     * 查询联系人列表
     */
    @RequiresPermissions("crm:contact:list")
    @GetMapping("/list")
    public TableDataInfo list(Contact contact) {
        startPage();
        List<Contact> list = contactService.selectContactList(contact);
        return getDataTable(list);
    }

    /**
     * 导出联系人列表
     */
    @RequiresPermissions("crm:contact:export")
    @Log(title = "联系人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Contact contact) {
        List<Contact> list = contactService.selectContactList(contact);
        ExcelUtil<Contact> util = new ExcelUtil<Contact>(Contact.class);
        util.exportExcel(response, list, "联系人数据");
    }

    /**
     * 获取联系人详细信息
     */
    @RequiresPermissions("crm:contact:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(contactService.selectContactById(id));
    }

    /**
     * 新增联系人
     */
    @RequiresPermissions("crm:contact:add")
    @Log(title = "联系人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Contact contact) {
        return toAjax(contactService.insertContact(contact));
    }

    /**
     * 修改联系人
     */
    @RequiresPermissions("crm:contact:edit")
    @Log(title = "联系人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Contact contact) {
        return toAjax(contactService.updateContact(contact));
    }
    /**
     * 转移联系人归属人
     */
    @RequiresPermissions("crm:contact:transfer")
    @Log(title = "联系人", businessType = BusinessType.UPDATE)
    @PutMapping("/transfer")
    public AjaxResult transfer(@RequestBody Contact contact) {
        return toAjax(contactService.transfer(contact));
    }

    /**
     * 删除联系人
     */
    @RequiresPermissions("crm:contact:remove")
    @Log(title = "联系人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(contactService.deleteContactByIds(ids));
    }


}
