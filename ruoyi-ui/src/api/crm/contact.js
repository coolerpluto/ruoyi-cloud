import request from '@/utils/request'

// 查询联系人列表
export function listContact(query) {
  return request({
    url: '/crm/contact/list',
    method: 'get',
    params: query
  })
}

// 查询联系人详细
export function getContact(id) {
  return request({
    url: '/crm/contact/' + id,
    method: 'get'
  })
}

// 新增联系人
export function addContact(data) {
  return request({
    url: '/crm/contact',
    method: 'post',
    data: data
  })
}

// 修改联系人
export function updateContact(data) {
  return request({
    url: '/crm/contact',
    method: 'put',
    data: data
  })
}
// 转移联系人归属
export function transferContact(data) {
  return request({
    url: '/crm/contact/transfer',
    method: 'put',
    data: data
  })
}
// 删除联系人
export function delContact(id) {
  return request({
    url: '/crm/contact/' + id,
    method: 'delete'
  })
}
