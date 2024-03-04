package com.highgo.medium.service.impl;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.highgo.medium.domain.TProdVersionInfo;
import com.highgo.medium.mapper.TProdInfoMapper;
import com.highgo.medium.domain.TProdInfo;
import com.highgo.medium.service.ITProdInfoService;

/**
 * 产品信息Service业务层处理
 * 
 * @author esz
 * @date 2023-06-28
 */
@Service
public class TProdInfoServiceImpl implements ITProdInfoService 
{
    @Autowired
    private TProdInfoMapper tProdInfoMapper;

    /**
     * 查询产品信息
     * 
     * @param id 产品信息主键
     * @return 产品信息
     */
    @Override
    public TProdInfo selectTProdInfoById(Long id)
    {
        return tProdInfoMapper.selectTProdInfoById(id);
    }

    /**
     * 查询产品信息列表
     * 
     * @param tProdInfo 产品信息
     * @return 产品信息
     */
    @Override
    public List<TProdInfo> selectTProdInfoList(TProdInfo tProdInfo)
    {
        return tProdInfoMapper.selectTProdInfoList(tProdInfo);
    }

    /**
     * 新增产品信息
     * 
     * @param tProdInfo 产品信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTProdInfo(TProdInfo tProdInfo)
    {
        tProdInfo.setCreateTime(DateUtils.getNowDate());
        tProdInfo.setCreateBy(SecurityUtils.getUsername());
        tProdInfo.setCreateId(SecurityUtils.getUserId());
        int rows = tProdInfoMapper.insertTProdInfo(tProdInfo);
        insertTProdVersionInfo(tProdInfo);
        return rows;
    }

    /**
     * 修改产品信息
     * 
     * @param tProdInfo 产品信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTProdInfo(TProdInfo tProdInfo)
    {
        tProdInfo.setUpdateTime(DateUtils.getNowDate());
        tProdInfo.setUpdateBy(SecurityUtils.getUsername());
        tProdInfo.setUpdateId(SecurityUtils.getUserId());
        tProdInfoMapper.deleteTProdVersionInfoByProdId(tProdInfo.getId());
        insertTProdVersionInfo(tProdInfo);
        return tProdInfoMapper.updateTProdInfo(tProdInfo);
    }

    /**
     * 批量删除产品信息
     * 
     * @param ids 需要删除的产品信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTProdInfoByIds(Long[] ids)
    {
        tProdInfoMapper.deleteTProdVersionInfoByProdIds(ids);
        return tProdInfoMapper.deleteTProdInfoByIds(ids);
    }

    /**
     * 删除产品信息信息
     * 
     * @param id 产品信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTProdInfoById(Long id)
    {
        tProdInfoMapper.deleteTProdVersionInfoByProdId(id);
        return tProdInfoMapper.deleteTProdInfoById(id);
    }

    @Override
    public List<TProdInfo> selectListAsTree(String searchValue)
    {
        return tProdInfoMapper.selectProdAncVersionAsTree(searchValue);
    }

    /**
     *
     * @return [{label:'label',value:'value',children:[]}]
     */
    @Override
    public List<Map<String, Object>> buildTreeSelect(List<TProdInfo> prodInfos)
    {
        List<Map<String, Object>> res = new ArrayList<>();
        for (TProdInfo prodInfo : prodInfos)
        {
            Map<String, Object> prod = new HashMap<>();
            prod.put("label", prodInfo.getProdName());
            prod.put("value", prodInfo.getId().toString());
            List<TProdVersionInfo> prodVersions = prodInfo.getTProdVersionInfoList();
            if (prodVersions.size() == 0)
            {
                res.add(prod);
                continue;
            }
            List<Map<String, Object>> versions = new ArrayList<>();
            for (TProdVersionInfo version : prodVersions)
            {
                Map<String, Object> prod_v = new HashMap<>();
                prod_v.put("label", version.getVersion());
                prod_v.put("value", version.getId().toString());
                prod_v.put("prod_pic", version.getProdPic());
                prod_v.put("pm_pic", version.getPmPic());
                prod_v.put("sign_flag", version.getSignFlag());
                prod_v.put("mac_tool_file_id", version.getMacToolFileId());
                prod_v.put("lic_tool_id", version.getLicToolId());
                prod_v.put("registration_id", version.getRegistrationId());
                versions.add(prod_v);
            }
            prod.put("children", versions);
            res.add(prod);
        }
        return res;
    }

    /**
     * 新增产品版本信息信息
     * 
     * @param tProdInfo 产品信息对象
     */
    public void insertTProdVersionInfo(TProdInfo tProdInfo)
    {
        List<TProdVersionInfo> tProdVersionInfoList = tProdInfo.getTProdVersionInfoList();
        Long id = tProdInfo.getId();
        if (StringUtils.isNotNull(tProdVersionInfoList))
        {
            List<TProdVersionInfo> list = new ArrayList<TProdVersionInfo>();
            for (TProdVersionInfo tProdVersionInfo : tProdVersionInfoList)
            {
                // 同步记录时间和操作员
                tProdVersionInfo.setCreateTime(tProdInfo.getCreateTime());
                tProdVersionInfo.setCreateBy(tProdInfo.getCreateBy());
                tProdVersionInfo.setCreateId(tProdInfo.getCreateId());
                tProdVersionInfo.setUpdateTime(tProdInfo.getUpdateTime());
                tProdVersionInfo.setUpdateBy(tProdInfo.getUpdateBy());
                tProdVersionInfo.setUpdateId(tProdInfo.getUpdateId());

                tProdVersionInfo.setProdId(id);
                list.add(tProdVersionInfo);
            }
            if (list.size() > 0)
            {
                tProdInfoMapper.batchTProdVersionInfo(list);
            }
        }
    }
}
