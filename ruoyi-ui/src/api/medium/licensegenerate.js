import request from '@/utils/request'
// 查询生成的但是不提供下载的列表
export function listGenerated(query) {
    return request({
        url: '/medium/licensegenerate/list',
        method: 'get',
        params: query
    })
}
// 查询详细
export function getGenerateInfo(generateId) {
    return request({
        url: '/medium/licensegenerate/' + generateId,
        method: 'get'
    })
}

// 生成
export function generateOne(data) {
    return request({
        url: '/medium/licensegenerate',
        method: 'post',
        data: data
    })
}

// 删除
export function delGenerate(generateId) {
    return request({
        url: '/medium/licensegenerate/' + generateId,
        method: 'delete'
    })
}

