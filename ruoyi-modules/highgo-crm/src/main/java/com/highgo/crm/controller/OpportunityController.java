package com.highgo.crm.controller;

import com.highgo.crm.domain.SysConfig;
import com.highgo.crm.service.SysConfigService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商机处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/opportunity")
public class OpportunityController extends BaseController {
    @Autowired
    private SysConfigService configService;

    /**
     * 获取商机列表
     */
    @RequiresPermissions("crm:opportunity:list")
    @GetMapping("/list")
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    /**
     * 商机导出
     */
    @Log(title = "商机管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("crm:opportunity:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysConfig config) {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        util.exportExcel(response, list, "商机数据");
    }

    /**
     * 根据编号获取详细信息
     */
    @GetMapping(value = "/{opportunityId}")
    public AjaxResult getInfo(@PathVariable Long opportunityId) {
        return success(configService.selectConfigById(opportunityId));
    }


    /**
     * 新增参数配置
     */
    @RequiresPermissions("crm:opportunity:add")
    @Log(title = "商机管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(SecurityUtils.getUsername());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改商机
     */
    @RequiresPermissions("crm:opportunity:edit")
    @Log(title = "商机管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("crm:opportunity:remove")
    @Log(title = "商机管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{opportunityIds}")
    public AjaxResult remove(@PathVariable Long[] opportunityIds) {
        configService.deleteConfigByIds(opportunityIds);
        return success();
    }
}
