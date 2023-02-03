import request from '@/utils/request'

// 查询应用信息列表
export function listApplication(query) {
  return request({
    url: '/crm/application/list',
    method: 'get',
    params: query
  })
}

// 查询应用信息详细
export function getApplication(id) {
  return request({
    url: '/crm/application/' + id,
    method: 'get'
  })
}

// 新增应用信息
export function addApplication(data) {
  return request({
    url: '/crm/application',
    method: 'post',
    data: data
  })
}

// 修改应用信息
export function updateApplication(data) {
  return request({
    url: '/crm/application',
    method: 'put',
    data: data
  })
}
// 转移应用信息负责人归属
export function transferApplication(data) {
  return request({
    url: '/crm/application/transfer',
    method: 'put',
    data: data
  })
}
// 删除应用信息
export function delApplication(id) {
  return request({
    url: '/crm/application/' + id,
    method: 'delete'
  })
}
