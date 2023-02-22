import request from '@/utils/request'

// 查询商机支持政策列表
export function listOppPolicy(query) {
  return request({
    url: '/crm/oppPolicy/list',
    method: 'get',
    params: query
  })
}

// 查询商机支持政策详细
export function getOppPolicy(id) {
  return request({
    url: '/crm/oppPolicy/' + id,
    method: 'get'
  })
}

// 新增商机支持政策
export function addOppPolicy(data) {
  return request({
    url: '/crm/oppPolicy',
    method: 'post',
    data: data
  })
}

// 修改商机支持政策
export function updateOppPolicy(data) {
  return request({
    url: '/crm/oppPolicy',
    method: 'put',
    data: data
  })
}

// 删除商机支持政策
export function delOppPolicy(id) {
  return request({
    url: '/crm/oppPolicy/' + id,
    method: 'delete'
  })
}
