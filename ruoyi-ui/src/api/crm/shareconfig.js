import request from '@/utils/request'

// 查询共享规则配置列表
export function listShareconfig(query) {
  return request({
    url: '/crm/shareconfig/list',
    method: 'get',
    params: query
  })
}

// 查询共享规则配置详细
export function getShareconfig(id) {
  return request({
    url: '/crm/shareconfig/' + id,
    method: 'get'
  })
}

// 新增共享规则配置
export function addShareconfig(data) {
  return request({
    url: '/crm/shareconfig',
    method: 'post',
    data: data
  })
}

// 修改共享规则配置
export function updateShareconfig(data) {
  return request({
    url: '/crm/shareconfig',
    method: 'put',
    data: data
  })
}

// 删除共享规则配置
export function delShareconfig(id) {
  return request({
    url: '/crm/shareconfig/' + id,
    method: 'delete'
  })
}
