package com.highgo.medium.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.highgo.medium.domain.TSoftRegistration;
import com.highgo.medium.service.ITSoftRegistrationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 软著登记记录Controller
 * 
 * @author esz
 * @date 2023-06-28
 */
@RestController
@RequestMapping("/soft_registration")
public class TSoftRegistrationController extends BaseController
{
    @Autowired
    private ITSoftRegistrationService tSoftRegistrationService;

    /**
     * 查询软著登记记录列表
     */
    @RequiresPermissions("medium:soft_registration:list")
    @GetMapping("/list")
    public TableDataInfo list(TSoftRegistration tSoftRegistration)
    {
        startPage();
        List<TSoftRegistration> list = tSoftRegistrationService.selectTSoftRegistrationList(tSoftRegistration);
        return getDataTable(list);
    }

    /**
     * 导出软著登记记录列表
     */
    @RequiresPermissions("medium:soft_registration:export")
    @Log(title = "软著登记记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSoftRegistration tSoftRegistration)
    {
        List<TSoftRegistration> list = tSoftRegistrationService.selectTSoftRegistrationList(tSoftRegistration);
        ExcelUtil<TSoftRegistration> util = new ExcelUtil<TSoftRegistration>(TSoftRegistration.class);
        util.exportExcel(response, list, "软著登记记录数据");
    }

    /**
     * 获取软著登记记录详细信息
     */
    @RequiresPermissions("medium:soft_registration:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tSoftRegistrationService.selectTSoftRegistrationById(id));
    }

    /**
     * 新增软著登记记录
     */
    @RequiresPermissions("medium:soft_registration:add")
    @Log(title = "软著登记记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSoftRegistration tSoftRegistration)
    {
        return toAjax(tSoftRegistrationService.insertTSoftRegistration(tSoftRegistration));
    }

    /**
     * 修改软著登记记录
     */
    @RequiresPermissions("medium:soft_registration:edit")
    @Log(title = "软著登记记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSoftRegistration tSoftRegistration)
    {
        return toAjax(tSoftRegistrationService.updateTSoftRegistration(tSoftRegistration));
    }

    /**
     * 删除软著登记记录
     */
    @RequiresPermissions("medium:soft_registration:remove")
    @Log(title = "软著登记记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tSoftRegistrationService.deleteTSoftRegistrationByIds(ids));
    }
}
