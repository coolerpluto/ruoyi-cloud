import request from '@/utils/request'

// 查询公司列表
export function listCompany(query) {
  return request({
    url: '/crm/company/list',
    method: 'get',
    params: query
  })
}

// 查询公司列表联通企查查
export function tianYanChaSearch(query) {
  return request({
    url: '/crm/company/tianYanChaSearch',
    method: 'get',
    params: query
  })
}

// 查询公司详细
export function getCompany(id) {
  return request({
    url: '/crm/company/' + id,
    method: 'get'
  })
}

// 新增公司
export function addCompany(data) {
  return request({
    url: '/crm/company',
    method: 'post',
    data: data
  })
}

// 修改公司
export function updateCompany(data) {
  return request({
    url: '/crm/company',
    method: 'put',
    data: data
  })
}
// 转移公司负责人归属
export function transferCompany(data) {
  return request({
    url: '/crm/company/transfer',
    method: 'put',
    data: data
  })
}
// 删除公司
export function delCompany(id) {
  return request({
    url: '/crm/company/' + id,
    method: 'delete'
  })
}
