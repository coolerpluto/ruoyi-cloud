import request from '@/utils/request'

// 查询软著登记记录列表
export function listSoft_registration(query) {
  return request({
    url: '/medium/soft_registration/list',
    method: 'get',
    params: query
  })
}

// 查询软著登记记录详细
export function getSoft_registration(id) {
  return request({
    url: '/medium/soft_registration/' + id,
    method: 'get'
  })
}

// 新增软著登记记录
export function addSoft_registration(data) {
  return request({
    url: '/medium/soft_registration',
    method: 'post',
    data: data
  })
}

// 修改软著登记记录
export function updateSoft_registration(data) {
  return request({
    url: '/medium/soft_registration',
    method: 'put',
    data: data
  })
}

// 删除软著登记记录
export function delSoft_registration(id) {
  return request({
    url: '/medium/soft_registration/' + id,
    method: 'delete'
  })
}
