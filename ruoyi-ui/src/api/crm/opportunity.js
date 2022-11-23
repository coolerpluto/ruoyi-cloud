import request from '@/utils/request'
// 查询商机列表
export function listsOpportunity(query) {
    return request({
        url: '/crm/opportunity/list',
        method: 'get',
        params: query
    })
}
// 查询参数详细
export function getInfo(opportunityId) {
    return request({
        url: '/crm/opportunity/' + opportunityId,
        method: 'get'
    })
}

// 新增参数配置
export function addOpportunity(data) {
    return request({
        url: '/crm/opportunity',
        method: 'post',
        data: data
    })
}

// 修改参数配置
export function updateOpportunity(data) {
    return request({
        url: '/crm/opportunity',
        method: 'put',
        data: data
    })
}

// 删除参数配置
export function delOpportunity(configId) {
    return request({
        url: '/crm/opportunity/' + configId,
        method: 'delete'
    })
}
