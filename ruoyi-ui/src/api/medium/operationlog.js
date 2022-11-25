import request from '@/utils/request'
// 查询列表
export function list(query) {
    return request({
        url: '/medium/operationlog/list',
        method: 'get',
        params: query
    })
}
// 删除
export function del(logId) {
    return request({
        url: '/medium/operationlog/' + logId,
        method: 'delete'
    })
}
// 查询详细
export function getInfo(logId) {
    return request({
        url: '/medium/operationlog/' + logId,
        method: 'get'
    })
}