package com.highgo.company.mapper;

import java.util.List;
import com.highgo.company.domain.ApiToken;

/**
 * 接口授权tokenMapper接口
 *
 *
 * @date 2023-12-05
 */
public interface ApiTokenMapper
{
    /**
     * 查询接口授权token
     *
     * @param tokenId 接口授权token主键
     * @return 接口授权token
     */
    public ApiToken selectApiTokenByTokenId(String tokenId);

    /**
     * 查询接口授权token列表
     *
     * @param apiToken 接口授权token
     * @return 接口授权token集合
     */
    public List<ApiToken> selectApiTokenList(ApiToken apiToken);

    /**
     * 新增接口授权token
     *
     * @param apiToken 接口授权token
     * @return 结果
     */
    public int insertApiToken(ApiToken apiToken);

    /**
     * 修改接口授权token
     *
     * @param apiToken 接口授权token
     * @return 结果
     */
    public int updateApiToken(ApiToken apiToken);

    /**
     * 删除接口授权token
     *
     * @param tokenId 接口授权token主键
     * @return 结果
     */
    public int deleteApiTokenByTokenId(String tokenId);

    /**
     * 批量删除接口授权token
     *
     * @param tokenIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApiTokenByTokenIds(String[] tokenIds);

    ApiToken selectInfoByToken(String token);
}
