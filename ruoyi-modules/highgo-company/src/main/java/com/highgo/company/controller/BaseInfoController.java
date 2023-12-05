package com.highgo.company.controller;

import com.highgo.company.annotation.CompanyRequireAuth;
import com.highgo.company.domain.BaseInfo;
import com.highgo.company.domain.vo.BaseInfoVo;
import com.highgo.company.domain.vo.SearchInfoVo;
import com.highgo.company.service.IBaseInfoService;
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
 * 企业基本信息Controller
 *
 * @date 2023-12-05
 */
@RestController
@RequestMapping("/baseInfo")
public class BaseInfoController extends BaseController
{
    @Autowired
    private IBaseInfoService baseInfoService;

    /**
     * 查询企业基本信息列表
     */
    @CompanyRequireAuth("company:baseInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BaseInfo baseInfo)
    {
        startPage();
        List<BaseInfo> list = baseInfoService.selectBaseInfoList(baseInfo);
        List<BaseInfoVo> voList = list.stream().map(sourceVariable ->
        {
            //生成新类型对象
            BaseInfoVo targetVariable = new BaseInfoVo();
            BeanUtils.copyProperties(sourceVariable, targetVariable);
            return targetVariable;
        }).collect(Collectors.toList());
        return getDataTable(voList);
    }

    /**
     * 导出企业基本信息列表
     */
    @CompanyRequireAuth("company:baseInfo:export")
    @Log(title = "企业基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaseInfo baseInfo)
    {
        List<BaseInfo> list = baseInfoService.selectBaseInfoList(baseInfo);
        ExcelUtil<BaseInfo> util = new ExcelUtil<BaseInfo>(BaseInfo.class);
        util.exportExcel(response, list, "企业基本信息数据");
    }

    /**
     * 获取企业基本信息详细信息
     */
    @CompanyRequireAuth("company:baseInfo:query")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") String infoId)
    {
        return success(baseInfoService.selectBaseInfoByInfoId(infoId));
    }

    /**
     * 新增企业基本信息
     */
    @CompanyRequireAuth("company:baseInfo:add")
    @Log(title = "企业基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseInfo baseInfo)
    {
        return toAjax(baseInfoService.insertBaseInfo(baseInfo));
    }

    /**
     * 修改企业基本信息
     */
    @CompanyRequireAuth("company:baseInfo:edit")
    @Log(title = "企业基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseInfo baseInfo)
    {
        return toAjax(baseInfoService.updateBaseInfo(baseInfo));
    }

    /**
     * 删除企业基本信息
     */
    @CompanyRequireAuth("company:baseInfo:remove")
    @Log(title = "企业基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable String[] infoIds)
    {
        return toAjax(baseInfoService.deleteBaseInfoByInfoIds(infoIds));
    }
}
