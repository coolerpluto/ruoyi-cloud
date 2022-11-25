import request from '@/utils/request'
// 查询列表
export function listLicenses(query) {
    return request({
        url: '/medium/licensedownload/list',
        method: 'get',
        params: query
    })
}
// 查询详细
export function getLicenseInfo(licenseId) {
    return request({
        url: '/medium/licensedownload/' + licenseId,
        method: 'get'
    })
}

// 新增
export function addLicense(data) {
    return request({
        url: '/medium/licensedownload',
        method: 'post',
        data: data
    })
}

// 修改
export function updateLicense(data) {
    return request({
        url: '/medium/licensedownload',
        method: 'put',
        data: data
    })
}

// 删除
export function delLicense(licenseId) {
    return request({
        url: '/medium/licensedownload/' + licenseId,
        method: 'delete'
    })
}

