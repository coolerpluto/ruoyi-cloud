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
import com.highgo.medium.domain.MFileDownHis;
import com.highgo.medium.service.IMFileDownHisService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 下载记录Controller
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/filedownhis")
public class MFileDownHisController extends BaseController
{
    @Autowired
    private IMFileDownHisService mFileDownHisService;

    /**
     * 查询下载记录列表
     */
    @RequiresPermissions("medium:filedownhis:list")
    @GetMapping("/list")
    public TableDataInfo list(MFileDownHis mFileDownHis)
    {
        startPage();
        List<MFileDownHis> list = mFileDownHisService.selectMFileDownHisList(mFileDownHis);
        return getDataTable(list);
    }

    /**
     * 导出下载记录列表
     */
    @RequiresPermissions("medium:filedownhis:export")
    @Log(title = "下载记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MFileDownHis mFileDownHis)
    {
        List<MFileDownHis> list = mFileDownHisService.selectMFileDownHisList(mFileDownHis);
        ExcelUtil<MFileDownHis> util = new ExcelUtil<MFileDownHis>(MFileDownHis.class);
        util.exportExcel(response, list, "下载记录数据");
    }

    /**
     * 获取下载记录详细信息
     */
    @RequiresPermissions("medium:filedownhis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mFileDownHisService.selectMFileDownHisById(id));
    }

    /**
     * 新增下载记录
     */
    @RequiresPermissions("medium:filedownhis:add")
    @Log(title = "下载记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MFileDownHis mFileDownHis)
    {
        return toAjax(mFileDownHisService.insertMFileDownHis(mFileDownHis));
    }

    /**
     * 修改下载记录
     */
    @RequiresPermissions("medium:filedownhis:edit")
    @Log(title = "下载记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MFileDownHis mFileDownHis)
    {
        return toAjax(mFileDownHisService.updateMFileDownHis(mFileDownHis));
    }

    /**
     * 删除下载记录
     */
    @RequiresPermissions("medium:filedownhis:remove")
    @Log(title = "下载记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mFileDownHisService.deleteMFileDownHisByIds(ids));
    }
}
