package com.highgo.company.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highgo.company.mapper.ApiTokenMapper;
import com.highgo.company.domain.ApiToken;
import com.highgo.company.service.IApiTokenService;

/**
 * 接口授权tokenService业务层处理
 *
 * @date 2023-12-05
 */
@Service
public class ApiTokenServiceImpl implements IApiTokenService
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiTokenMapper apiTokenMapper;

    /**
     * 查询接口授权token
     *
     * @param tokenId 接口授权token主键
     * @return 接口授权token
     */
    @Override
    public ApiToken selectApiTokenByTokenId(String tokenId)
    {
        return apiTokenMapper.selectApiTokenByTokenId(tokenId);
    }

    @Autowired
    private RedisService redisService;

    @Override
    public ApiToken selectInfoByToken(String token)
    {
        String resCache = redisService.getCacheObject(getCacheKey(token));
        ApiToken res;
        if (StringUtils.isNotEmpty(resCache))
        {
            res = JSON.parseObject(resCache, ApiToken.class);
            log.debug("selectInfoByToken cache token:{},val:{}", token, JSON.toJSONString(res));
            return res;
        }
        log.debug("selectInfoByToken token:{}", token);
        res = apiTokenMapper.selectInfoByToken(token);
        redisService.setCacheObject(getCacheKey(token), JSON.toJSONString(res), 10L, TimeUnit.MINUTES);
        return res;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return "company_api_token:" + configKey;
    }

    /**
     * 查询接口授权token列表
     *
     * @param apiToken 接口授权token
     * @return 接口授权token
     */
    @Override
    public List<ApiToken> selectApiTokenList(ApiToken apiToken)
    {
        return apiTokenMapper.selectApiTokenList(apiToken);
    }

    /**
     * 新增接口授权token
     *
     * @param apiToken 接口授权token
     * @return 结果
     */
    @Override
    public int insertApiToken(ApiToken apiToken)
    {
        apiToken.setCreateTime(DateUtils.getNowDate());
        return apiTokenMapper.insertApiToken(apiToken);
    }

    /**
     * 修改接口授权token
     *
     * @param apiToken 接口授权token
     * @return 结果
     */
    @Override
    public int updateApiToken(ApiToken apiToken)
    {
        apiToken.setUpdateTime(DateUtils.getNowDate());
        return apiTokenMapper.updateApiToken(apiToken);
    }

    /**
     * 批量删除接口授权token
     *
     * @param tokenIds 需要删除的接口授权token主键
     * @return 结果
     */
    @Override
    public int deleteApiTokenByTokenIds(String[] tokenIds)
    {
        return apiTokenMapper.deleteApiTokenByTokenIds(tokenIds);
    }

    /**
     * 删除接口授权token信息
     *
     * @param tokenId 接口授权token主键
     * @return 结果
     */
    @Override
    public int deleteApiTokenByTokenId(String tokenId)
    {
        return apiTokenMapper.deleteApiTokenByTokenId(tokenId);
    }
}
