package com.highgo.crm.controller;

import com.highgo.crm.domain.TransferLog;
import com.highgo.crm.service.ITransferLogService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
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
 * 转移日志Controller
 * 
 * @author eszhang
 * @date 2023-01-17
 */
@RestController
@RequestMapping("/transferlog")
public class TransferLogController extends BaseController
{
    @Autowired
    private ITransferLogService transferLogService;

    /**
     * 查询转移日志列表
     */
    @RequiresPermissions("crm:transferlog:list")
    @GetMapping("/list")
    public TableDataInfo list(TransferLog transferLog)
    {
        startPage();
        List<TransferLog> list = transferLogService.selectTransferLogList(transferLog);
        return getDataTable(list);
    }

    /**
     * 导出转移日志列表
     */
    @RequiresPermissions("crm:transferlog:export")
    @Log(title = "转移日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TransferLog transferLog)
    {
        List<TransferLog> list = transferLogService.selectTransferLogList(transferLog);
        ExcelUtil<TransferLog> util = new ExcelUtil<TransferLog>(TransferLog.class);
        util.exportExcel(response, list, "转移日志数据");
    }

    /**
     * 获取转移日志详细信息
     */
    @RequiresPermissions("crm:transferlog:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(transferLogService.selectTransferLogById(id));
    }

    /**
     * 新增转移日志
     */
    @RequiresPermissions("crm:transferlog:add")
    @Log(title = "转移日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TransferLog transferLog)
    {
        transferLog.setCreateBy(SecurityUtils.getUsername());
        return toAjax(transferLogService.insertTransferLog(transferLog));
    }

    /**
     * 修改转移日志
     */
    @RequiresPermissions("crm:transferlog:edit")
    @Log(title = "转移日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TransferLog transferLog)
    {
        transferLog.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(transferLogService.updateTransferLog(transferLog));
    }

    /**
     * 删除转移日志
     */
    @RequiresPermissions("crm:transferlog:remove")
    @Log(title = "转移日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(transferLogService.deleteTransferLogByIds(ids));
    }
}
