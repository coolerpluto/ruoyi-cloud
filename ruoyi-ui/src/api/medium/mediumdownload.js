import request from '@/utils/request'
// 查询列表
export function listMediums(query) {
    return request({
        url: '/medium/mediumdownload/list',
        method: 'get',
        params: query
    })
}
// 查询详细
export function getMediumInfo(mediumId) {
    return request({
        url: '/medium/mediumdownload/' + mediumId,
        method: 'get'
    })
}

// 新增
export function addMedium(data) {
    return request({
        url: '/medium/mediumdownload',
        method: 'post',
        data: data
    })
}

// 修改
export function updateMedium(data) {
    return request({
        url: '/medium/mediumdownload',
        method: 'put',
        data: data
    })
}

// 删除
export function delMedium(mediumId) {
    return request({
        url: '/medium/mediumdownload/' + mediumId,
        method: 'delete'
    })
}
