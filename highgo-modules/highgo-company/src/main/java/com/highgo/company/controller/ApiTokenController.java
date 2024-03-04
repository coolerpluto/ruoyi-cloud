package com.highgo.company.controller;

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
import com.highgo.company.domain.ApiToken;
import com.highgo.company.service.IApiTokenService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 接口授权tokenController
 *
 * @date 2023-12-05
 */
@RestController
@RequestMapping("/token")
public class ApiTokenController extends BaseController
{
    @Autowired
    private IApiTokenService apiTokenService;

    /**
     * 查询接口授权token列表
     */
    @RequiresPermissions("company:token:list")
    @GetMapping("/list")
    public TableDataInfo list(ApiToken apiToken)
    {
        startPage();
        List<ApiToken> list = apiTokenService.selectApiTokenList(apiToken);
        return getDataTable(list);
    }

    /**
     * 导出接口授权token列表
     */
    @RequiresPermissions("company:token:export")
    @Log(title = "接口授权token", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApiToken apiToken)
    {
        List<ApiToken> list = apiTokenService.selectApiTokenList(apiToken);
        ExcelUtil<ApiToken> util = new ExcelUtil<ApiToken>(ApiToken.class);
        util.exportExcel(response, list, "接口授权token数据");
    }

    /**
     * 获取接口授权token详细信息
     */
    @RequiresPermissions("company:token:query")
    @GetMapping(value = "/{tokenId}")
    public AjaxResult getInfo(@PathVariable("tokenId") String tokenId)
    {
        return success(apiTokenService.selectApiTokenByTokenId(tokenId));
    }

    /**
     * 新增接口授权token
     */
    @RequiresPermissions("company:token:add")
    @Log(title = "接口授权token", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApiToken apiToken)
    {
        return toAjax(apiTokenService.insertApiToken(apiToken));
    }

    /**
     * 修改接口授权token
     */
    @RequiresPermissions("company:token:edit")
    @Log(title = "接口授权token", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApiToken apiToken)
    {
        return toAjax(apiTokenService.updateApiToken(apiToken));
    }

    /**
     * 删除接口授权token
     */
    @RequiresPermissions("company:token:remove")
    @Log(title = "接口授权token", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tokenIds}")
    public AjaxResult remove(@PathVariable String[] tokenIds)
    {
        return toAjax(apiTokenService.deleteApiTokenByTokenIds(tokenIds));
    }
}
