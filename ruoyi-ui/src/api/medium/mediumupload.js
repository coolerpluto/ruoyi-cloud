import request from '@/utils/request'
// 查询已上传的列表
export function listUpload(query) {
    return request({
        url: '/medium/mediumUpload/list',
        method: 'get',
        params: query
    })
}

// 新增
export function mediumUpload(data) {
    return request({
        url: '/medium/mediumUpload',
        method: 'post',
        data: data
    })
}
