package com.highgo.company.controller;

import com.highgo.company.annotation.CompanyRequireAuth;
import com.highgo.company.domain.SearchInfo;
import com.highgo.company.domain.vo.SearchInfoVo;
import com.highgo.company.service.ISearchInfoService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
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
import java.util.stream.Collectors;

/**
 * 企业简单基本信息Controller
 *
 *
 * @date 2023-12-05
 */
@RestController
@RequestMapping("/info")
public class SearchInfoController extends BaseController
{
    @Autowired
    private ISearchInfoService searchInfoService;

    /**
     * 查询企业简单基本信息列表
     */
    @CompanyRequireAuth("company:info:list")
    @GetMapping("/list")
    public TableDataInfo list(SearchInfo searchInfo)
    {
        startPage();
        List<SearchInfo> list = searchInfoService.selectSearchInfoList(searchInfo);
        List<SearchInfoVo> voList = list.stream().map(sourceVariable  -> {
            //生成新类型对象
            SearchInfoVo targetVariable  = new SearchInfoVo();
            BeanUtils.copyProperties(sourceVariable  , targetVariable);
            return targetVariable  ;
        }).collect(Collectors.toList());

        return getDataTable(voList);
    }

    /**
     * 导出企业简单基本信息列表
     */
    @CompanyRequireAuth("company:info:export")
    @Log(title = "企业简单基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SearchInfo searchInfo)
    {
        List<SearchInfo> list = searchInfoService.selectSearchInfoList(searchInfo);
        ExcelUtil<SearchInfo> util = new ExcelUtil<SearchInfo>(SearchInfo.class);
        util.exportExcel(response, list, "企业简单基本信息数据");
    }

    /**
     * 获取企业简单基本信息详细信息
     */
    @CompanyRequireAuth("company:info:query")
    @GetMapping(value = "/{searchId}")
    public AjaxResult getInfo(@PathVariable("searchId") String searchId)
    {
        return success(searchInfoService.selectSearchInfoBySearchId(searchId));
    }

    /**
     * 新增企业简单基本信息
     */
    @CompanyRequireAuth("company:info:add")
    @Log(title = "企业简单基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SearchInfo searchInfo)
    {
        return toAjax(searchInfoService.insertSearchInfo(searchInfo));
    }

    /**
     * 修改企业简单基本信息
     */
    @CompanyRequireAuth("company:info:edit")
    @Log(title = "企业简单基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SearchInfo searchInfo)
    {
        return toAjax(searchInfoService.updateSearchInfo(searchInfo));
    }

    /**
     * 删除企业简单基本信息
     */
    @CompanyRequireAuth("company:info:remove")
    @Log(title = "企业简单基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{searchIds}")
    public AjaxResult remove(@PathVariable String[] searchIds)
    {
        return toAjax(searchInfoService.deleteSearchInfoBySearchIds(searchIds));
    }
}
