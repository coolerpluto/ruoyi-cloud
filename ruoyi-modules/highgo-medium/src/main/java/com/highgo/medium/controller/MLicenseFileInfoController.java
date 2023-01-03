package com.highgo.medium.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
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
import com.highgo.medium.domain.MLicenseFileInfo;
import com.highgo.medium.service.IMLicenseFileInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * License文件记录Controller
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/license")
public class MLicenseFileInfoController extends BaseController
{
    @Autowired
    private IMLicenseFileInfoService mLicenseFileInfoService;

    /**
     * 查询License文件记录列表
     */
    @RequiresPermissions("medium:license:list")
    @GetMapping("/list")
    public TableDataInfo list(MLicenseFileInfo mLicenseFileInfo)
    {
        startPage();
        List<MLicenseFileInfo> list = mLicenseFileInfoService.selectMLicenseFileInfoList(mLicenseFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出License文件记录列表
     */
    @RequiresPermissions("medium:license:export")
    @Log(title = "License文件记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MLicenseFileInfo mLicenseFileInfo)
    {
        List<MLicenseFileInfo> list = mLicenseFileInfoService.selectMLicenseFileInfoList(mLicenseFileInfo);
        ExcelUtil<MLicenseFileInfo> util = new ExcelUtil<MLicenseFileInfo>(MLicenseFileInfo.class);
        util.exportExcel(response, list, "License文件记录数据");
    }

    @RequiresPermissions("medium:license:download")
    @Log(title = "License文件记录", businessType = BusinessType.DOWNLOAD)
    @PostMapping("/download")
    public void download(HttpServletResponse response, MLicenseFileInfo mLicenseFileInfo)
    {
        mLicenseFileInfoService.download(response,mLicenseFileInfo);
    }

    @RequiresPermissions("medium:license:download")
    @Log(title = "License文件记录", businessType = BusinessType.DOWNLOAD)
    @PostMapping("/downLoadBatch")
    public void downLoadBatch(HttpServletResponse response, MLicenseFileInfo mLicenseFileInfo)
    {
        mLicenseFileInfoService.downLoadBatch(response,mLicenseFileInfo);
    }

    /**
     * 获取License文件记录详细信息
     */
    @RequiresPermissions("medium:license:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mLicenseFileInfoService.selectMLicenseFileInfoById(id));
    }

    /**
     * 新增License文件记录
     */
    @RequiresPermissions("medium:license:add")
    @Log(title = "License文件记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MLicenseFileInfo mLicenseFileInfo)
    {
        return toAjax(mLicenseFileInfoService.insertMLicenseFileInfo(mLicenseFileInfo));
    }

    //@RequiresPermissions("medium:license:generator")
    @Log(title = "License文件记录", businessType = BusinessType.INSERT)
    @PostMapping("/generator")
    public AjaxResult generator(@RequestBody JSONObject json) {
        Map<String, Object> generatorRes = mLicenseFileInfoService.generator(json);
        return success(generatorRes);
    }
    /**
     * 修改License文件记录
     */
    @RequiresPermissions("medium:license:edit")
    @Log(title = "License文件记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MLicenseFileInfo mLicenseFileInfo)
    {
        return toAjax(mLicenseFileInfoService.updateMLicenseFileInfo(mLicenseFileInfo));
    }

    /**
     * 删除License文件记录
     */
    @RequiresPermissions("medium:license:remove")
    @Log(title = "License文件记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mLicenseFileInfoService.deleteMLicenseFileInfoByIds(ids));
    }
}
