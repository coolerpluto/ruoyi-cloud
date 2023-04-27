import request from '@/utils/request'

// 查询license申请 主列表
export function listLicenseApply(query) {
  return request({
    url: '/crm/licenseApply/list',
    method: 'get',
    params: query
  })
}

// 查询license申请 主详细
export function getLicenseApply(id) {
  return request({
    url: '/crm/licenseApply/' + id,
    method: 'get'
  })
}
export function getLicenseApplyByCode(query) {
  return request({
    url: '/crm/licenseApply/getLicApplyByCode',
    method: 'get',
    params: query
  })
}
export function saveAndUpdateLicenseApply(data) {
  return request({
    url: '/crm/licenseApply/saveAndUpdate',
    method: 'post',
    data: data
  })
}
// 新增license申请 主
export function addLicenseApply(data) {
  return request({
    url: '/crm/licenseApply',
    method: 'post',
    data: data
  })
}

// 修改license申请 主
export function updateLicenseApply(data) {
  return request({
    url: '/crm/licenseApply',
    method: 'put',
    data: data
  })
}

// 删除license申请 主
export function delLicenseApply(id) {
  return request({
    url: '/crm/licenseApply/' + id,
    method: 'delete'
  })
}
