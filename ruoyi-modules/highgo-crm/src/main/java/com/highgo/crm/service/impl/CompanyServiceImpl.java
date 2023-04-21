package com.highgo.crm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.highgo.crm.domain.Company;
import com.highgo.crm.domain.TransferLog;
import com.highgo.crm.mapper.CompanyMapper;
import com.highgo.crm.mapper.TransferLogMapper;
import com.highgo.crm.service.ICompanyService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.RandomUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.datascope.annotation.CrmDataScope;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公司Service业务层处理
 *
 * @author eszhang
 * @date 2023-02-02
 */
@Service
public class CompanyServiceImpl implements ICompanyService
{
    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 查询公司
     *
     * @param id 公司主键
     * @return 公司
     */
    @Override
    public Company selectCompanyById(String id)
    {
        return companyMapper.selectCompanyById(id);
    }

    /**
     * 查询公司列表
     *
     * @param company 公司
     * @return 公司
     */
    @Override
    @CrmDataScope(deptAlias = "sd", userAlias = "su")
    public List<Company> selectCompanyList(Company company)
    {
        return companyMapper.selectCompanyList(company);
    }

    /**
     * 新增公司
     *
     * @param company 公司
     * @return 结果
     */
    @Override
    public int insertCompany(Company company)
    {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        company.setCreateId(userCurrent.getUserId());
        company.setCreateBy(SecurityUtils.getUsername());
        company.setOwnerId(userCurrent.getUserId());
        company.setDeptId(userCurrent.getDeptId());
        company.setCreateTime(DateUtils.getNowDate());
        return companyMapper.insertCompany(company);
    }

    /**
     * 修改公司
     *
     * @param company 公司
     * @return 结果
     */
    @Override
    public int updateCompany(Company company)
    {
        SysUser userCurrent = SecurityUtils.getLoginUser().getSysUser();
        company.setUpdateId(userCurrent.getUserId());
        company.setUpdateBy(SecurityUtils.getUsername());
        company.setUpdateTime(DateUtils.getNowDate());
        return companyMapper.updateCompany(company);
    }

    /**
     * 批量删除公司
     *
     * @param ids 需要删除的公司主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByIds(String[] ids)
    {
        return companyMapper.deleteCompanyByIds(ids);
    }

    /**
     * 删除公司信息
     *
     * @param id 公司主键
     * @return 结果
     */
    @Override
    public int deleteCompanyById(String id)
    {
        return companyMapper.deleteCompanyById(id);
    }

    @Autowired
    private TransferLogMapper transferLogMapper;

    @Override
    public int transfer(Company company)
    {
        TransferLog transferLog = new TransferLog();
        transferLog.setModel("CUST");
        transferLog.setUserFrom(SecurityUtils.getUsername());
        transferLog.setUserTo(String.valueOf(company.getOwnerId()));
        List<String> selectedCodes = (List<String>) company.getParams().get("selectedCodes");
        transferLog.setRecordIds(selectedCodes.toString());
        transferLog.setQuantity(selectedCodes.size());
        transferLog.setReason(company.getRemark());
        transferLog.setActionTime(DateUtils.getNowDate());
        transferLogMapper.insertTransferLog(transferLog);
        return companyMapper.changeCompanyOwners(company);
    }

    @Override
    public List<Company> searchCompanyByName(Company companyReq)
    {
        if (StringUtils.isBlank(companyReq.getSearchValue()))
        {
            return new ArrayList<>();
        }
//        Company company = new Company();
//        company.setCompanyName(companyReq.getSearchValue());
//        List<Company> companyInDb = companyMapper.selectCompanyList(company);
//        if (CollectionUtils.isEmpty(companyInDb)){
//            // 数据库没值调天眼查查10个返回去
//            return searchFromTanYanCha(companyReq.getSearchValue(),companyReq.getSourceType());
//        }
        return searchFromTanYanCha(companyReq.getSearchValue(), companyReq.getSourceType());
    }


    private List<Company> searchFromTanYanCha(String word, String sourceType)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://capi.tianyancha.com/cloud-tempest/web/searchCompanyV3";
        JSONObject requestJson = new JSONObject();
        requestJson.put("word", word);
        requestJson.put("sortType", 0);
        requestJson.put("pageSize", 10);
        requestJson.put("pageNum", 1);
        String res = restTemplate.postForObject(url, requestJson, String.class);
        assert res != null;
        res = res.replace("<em>", "");
        res = res.replace("</em>", "");
        res = res.replace("\\t", "");
        JSONObject resJson = JSON.parseObject(res, JSONObject.class);
        assert resJson != null;
        JSONObject data = (JSONObject) resJson.get("data");
        JSONArray companyList = (JSONArray) data.get("companyList");

        Long currentUserId = SecurityUtils.getLoginUser().getSysUser().getUserId();
        String currentUserName = SecurityUtils.getLoginUser().getSysUser().getUserName();
        Long currentDeptId = SecurityUtils.getLoginUser().getSysUser().getDeptId();

        List<Company> resReturn = new ArrayList<>();
        for (int i = 0; i < companyList.size(); i++)
        {
            JSONObject companyJson = (JSONObject) companyList.get(i);
            Company companyTemp = new Company();
            //companyTemp.setId((String.valueOf(companyJson.get("id"))));
            String rt = DateUtils.parseDateToStr("yyyyMMdd HHmmss", new Date());
            String code = rt.replace(" ", RandomUtils.generateString(6));
            companyTemp.setCode(sourceType + code);
            String name = (String) companyJson.get("name");
            companyTemp.setCompanyName(name);
            companyTemp.setLegal((String) companyJson.get("legalPersonName"));
            companyTemp.setCapitalReg(getCapitalRegFormat((String) companyJson.get("regCapital")));
            companyTemp.setSourceType(sourceType);
            String webStr = (String) companyJson.get("websites");
            String[] webs = webStr.split(";");
            if (webs.length > 0)
            {
                companyTemp.setWebsite(webs[0]);
            }
            companyTemp.setAddrDetail((String) companyJson.get("regLocation"));

            String base = (String) companyJson.get("base");
            String city = (String) companyJson.get("city");
            String district = (String) companyJson.get("district");
            companyTemp.setAddr(base + city + district);

            companyTemp.setDeptId(currentDeptId);
            companyTemp.setCreateId(currentUserId);
            companyTemp.setCreateBy(currentUserName);
            companyTemp.setCreateTime(new Date());

            resReturn.add(companyTemp);
        }
        //companyMapper.batchInsertCompany(resReturn);
        return resReturn;
    }

    private String getCapitalRegFormat(String str)
    {
        //String str = "1,000.222万元人民币";
        String regEx = "[^0-9.,]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
