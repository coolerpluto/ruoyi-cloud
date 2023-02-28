import request from '@/utils/request'

// 查询列表
export function listUnitedOpp(query) {
  return request({
    url: '/crm/oppUnited/list',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getUnitedOpp(id) {
  return request({
    url: '/crm/oppUnited/' + id,
    method: 'get'
  })
}

// 新增管理
export function addUnitedOpp(data) {
  return request({
    url: '/crm/oppUnited',
    method: 'post',
    data: data
  })
}

// 修改管理
export function updateUnitedOpp(data) {
  return request({
    url: '/crm/oppUnited',
    method: 'put',
    data: data
  })
}

// 删除管理
export function delUnitedOpp(id) {
  return request({
    url: '/crm/oppUnited/' + id,
    method: 'delete'
  })
}
// 转交管理
export function transferUnitedOpp(data) {
  return request({
    url: '/crm/oppUnited/transfer',
    method: 'put',
    data: data
  })
}
