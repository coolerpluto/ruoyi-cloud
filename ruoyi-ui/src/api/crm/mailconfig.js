import request from '@/utils/request'

// 查询邮箱设置列表
export function listMailconfig(query) {
  return request({
    url: '/crm/mailconfig/list',
    method: 'get',
    params: query
  })
}

// 查询邮箱设置详细
export function getMailconfig(id) {
  return request({
    url: '/crm/mailconfig/' + id,
    method: 'get'
  })
}

// 新增邮箱设置
export function addMailconfig(data) {
  return request({
    url: '/crm/mailconfig',
    method: 'post',
    data: data
  })
}

// 修改邮箱设置
export function updateMailconfig(data) {
  return request({
    url: '/crm/mailconfig',
    method: 'put',
    data: data
  })
}

// 删除邮箱设置
export function delMailconfig(id) {
  return request({
    url: '/crm/mailconfig/' + id,
    method: 'delete'
  })
}
