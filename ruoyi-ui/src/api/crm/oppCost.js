import request from '@/utils/request'

// 查询商机花费列表
export function listOppCost(query) {
  return request({
    url: '/crm/oppCost/list',
    method: 'get',
    params: query
  })
}

// 查询商机花费详细
export function getOppCost(id) {
  return request({
    url: '/crm/oppCost/' + id,
    method: 'get'
  })
}

// 新增商机花费
export function addOppCost(data) {
  return request({
    url: '/crm/oppCost',
    method: 'post',
    data: data
  })
}

// 修改商机花费
export function updateOppCost(data) {
  return request({
    url: '/crm/oppCost',
    method: 'put',
    data: data
  })
}

// 删除商机花费
export function delOppCost(id) {
  return request({
    url: '/crm/oppCost/' + id,
    method: 'delete'
  })
}
