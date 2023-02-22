import request from '@/utils/request'

// 查询商机进展列表
export function listOppAdvances(query) {
  return request({
    url: '/crm/oppAdvances/list',
    method: 'get',
    params: query
  })
}

// 查询商机进展详细
export function getOppAdvances(id) {
  return request({
    url: '/crm/oppAdvances/' + id,
    method: 'get'
  })
}

// 新增商机进展
export function addOppAdvances(data) {
  return request({
    url: '/crm/oppAdvances',
    method: 'post',
    data: data
  })
}

// 修改商机进展
export function updateOppAdvances(data) {
  return request({
    url: '/crm/oppAdvances',
    method: 'put',
    data: data
  })
}

// 删除商机进展
export function delOppAdvances(id) {
  return request({
    url: '/crm/oppAdvances/' + id,
    method: 'delete'
  })
}
