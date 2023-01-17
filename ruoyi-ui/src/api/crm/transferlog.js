import request from '@/utils/request'

// 查询转移日志列表
export function listTransferlog(query) {
  return request({
    url: '/crm/transferlog/list',
    method: 'get',
    params: query
  })
}

// 查询转移日志详细
export function getTransferlog(id) {
  return request({
    url: '/crm/transferlog/' + id,
    method: 'get'
  })
}

// 新增转移日志
export function addTransferlog(data) {
  return request({
    url: '/crm/transferlog',
    method: 'post',
    data: data
  })
}

// 修改转移日志
export function updateTransferlog(data) {
  return request({
    url: '/crm/transferlog',
    method: 'put',
    data: data
  })
}

// 删除转移日志
export function delTransferlog(id) {
  return request({
    url: '/crm/transferlog/' + id,
    method: 'delete'
  })
}
