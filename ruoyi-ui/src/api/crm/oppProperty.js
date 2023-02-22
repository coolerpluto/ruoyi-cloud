import request from '@/utils/request'

// 查询商机属性列表
export function listOppProperty(query) {
  return request({
    url: '/crm/oppProperty/list',
    method: 'get',
    params: query
  })
}

// 查询商机属性详细
export function getOppProperty(id) {
  return request({
    url: '/crm/oppProperty/' + id,
    method: 'get'
  })
}

// 新增商机属性
export function addOppProperty(data) {
  return request({
    url: '/crm/oppProperty',
    method: 'post',
    data: data
  })
}

// 修改商机属性
export function updateOppProperty(data) {
  return request({
    url: '/crm/oppProperty',
    method: 'put',
    data: data
  })
}

// 删除商机属性
export function delOppProperty(id) {
  return request({
    url: '/crm/oppProperty/' + id,
    method: 'delete'
  })
}
