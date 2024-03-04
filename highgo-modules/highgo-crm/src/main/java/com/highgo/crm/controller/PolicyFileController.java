package com.highgo.crm.controller;

import com.highgo.crm.domain.PolicyFile;
import com.highgo.crm.service.IPolicyFileService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 政策文件Controller
 *
 * @author eszhang
 * @date 2023-01-19
 */
@RestController
@RequestMapping("/policyfile")
public class PolicyFileController extends BaseController {
    @Autowired
    private IPolicyFileService policyFileService;

    /**
     * 查询政策文件列表
     */
    @RequiresPermissions("crm:policyfile:list")
    @GetMapping("/list")
    public TableDataInfo list(PolicyFile policyFile) {
        startPage();
        List<PolicyFile> list = policyFileService.selectPolicyFileList(policyFile);
        return getDataTable(list);
    }

    /**
     * 导出政策文件列表
     */
    @RequiresPermissions("crm:policyfile:export")
    @Log(title = "政策文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PolicyFile policyFile) {
        List<PolicyFile> list = policyFileService.selectPolicyFileList(policyFile);
        ExcelUtil<PolicyFile> util = new ExcelUtil<PolicyFile>(PolicyFile.class);
        util.exportExcel(response, list, "政策文件数据");
    }

    /**
     * 获取政策文件详细信息
     */
    @RequiresPermissions("crm:policyfile:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return success(policyFileService.selectPolicyFileById(id));
    }

    /**
     * 新增政策文件
     */
    @RequiresPermissions("crm:policyfile:add")
    @Log(title = "政策文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PolicyFile policyFile) {
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        policyFile.setOwnerId(String.valueOf(currentUser.getUserId()));
        policyFile.setDeptId(String.valueOf(currentUser.getDeptId()));
        policyFile.setCreateBy(currentUser.getNickName());
        return toAjax(policyFileService.insertPolicyFile(policyFile));
    }

    @RequiresPermissions("crm:policyfile:add")
    @Log(title = "政策文件", businessType = BusinessType.INSERT)
    @PostMapping("/addWithFile")
    public AjaxResult addWithFile(PolicyFile policyFile, MultipartFile policyFileFile) {
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        policyFile.setOwnerId(String.valueOf(currentUser.getUserId()));
        policyFile.setDeptId(String.valueOf(currentUser.getDeptId()));
        policyFile.setCreateBy(currentUser.getNickName());
        return toAjax(policyFileService.insertPolicyFileWithFile(policyFile, policyFileFile));
    }

    /**
     * 修改政策文件
     */
    @RequiresPermissions("crm:policyfile:edit")
    @Log(title = "政策文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PolicyFile policyFile) {
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        policyFile.setUpdateBy(currentUser.getNickName());
        return toAjax(policyFileService.updatePolicyFile(policyFile));
    }

    @RequiresPermissions("crm:policyfile:edit")
    @Log(title = "政策文件", businessType = BusinessType.UPDATE)
    @PutMapping("/editWithFile")
    public AjaxResult editWithFile(PolicyFile policyFile, MultipartFile policyFileFile) {
        SysUser currentUser = SecurityUtils.getLoginUser().getSysUser();
        policyFile.setUpdateBy(currentUser.getNickName());
        return toAjax(policyFileService.updatePolicyFileWithFile(policyFile, policyFileFile));
    }

    @RequiresPermissions("crm:policyfile:download")
    @Log(title = "政策文件", businessType = BusinessType.DOWNLOAD)
    @PostMapping("/download")
    public void download(HttpServletResponse response, PolicyFile policyFile) {
        policyFileService.download(response, policyFile);
    }

    /**
     * 删除政策文件
     */
    @RequiresPermissions("crm:policyfile:remove")
    @Log(title = "政策文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(policyFileService.deletePolicyFileByIds(ids));
    }
}
