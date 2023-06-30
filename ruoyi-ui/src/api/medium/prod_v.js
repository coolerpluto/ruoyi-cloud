import request from '@/utils/request'

// 查询产品版本信息列表
export function listProd_v(query) {
  return request({
    url: '/medium/prod_v/list',
    method: 'get',
    params: query
  })
}

// 查询产品版本信息详细
export function getProd_v(id) {
  return request({
    url: '/medium/prod_v/' + id,
    method: 'get'
  })
}

// 新增产品版本信息
export function addProd_v(data) {
  return request({
    url: '/medium/prod_v',
    method: 'post',
    data: data
  })
}

// 修改产品版本信息
export function updateProd_v(data) {
  return request({
    url: '/medium/prod_v',
    method: 'put',
    data: data
  })
}

// 删除产品版本信息
export function delProd_v(id) {
  return request({
    url: '/medium/prod_v/' + id,
    method: 'delete'
  })
}
