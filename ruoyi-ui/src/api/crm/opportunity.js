import request from '@/utils/request'

// 查询商机管理列表
export function listOpportunity(query) {
  return request({
    url: '/crm/opportunity/list',
    method: 'get',
    params: query
  })
}

// 查询商机管理详细
export function getOpportunity(id) {
  return request({
    url: '/crm/opportunity/' + id,
    method: 'get'
  })
}

// 新增商机管理
export function addOpportunity(data) {
  return request({
    url: '/crm/opportunity',
    method: 'post',
    data: data
  })
}

// 修改商机管理
export function updateOpportunity(data) {
  return request({
    url: '/crm/opportunity',
    method: 'put',
    data: data
  })
}

// 删除商机管理
export function delOpportunity(id) {
  return request({
    url: '/crm/opportunity/' + id,
    method: 'delete'
  })
}
