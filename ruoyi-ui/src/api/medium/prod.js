import request from '@/utils/request'

// 查询产品信息列表
export function listProd(query) {
  return request({
    url: '/medium/prod/list',
    method: 'get',
    params: query
  })
}
// 查询产品信息树列表
export function listProdTree(query) {
  return request({
    url: '/medium/prod/treeSelect',
    method: 'get',
    params: query
  })
}

// 查询产品信息详细
export function getProd(id) {
  return request({
    url: '/medium/prod/' + id,
    method: 'get'
  })
}

// 新增产品信息
export function addProd(data) {
  return request({
    url: '/medium/prod',
    method: 'post',
    data: data
  })
}

// 修改产品信息
export function updateProd(data) {
  return request({
    url: '/medium/prod',
    method: 'put',
    data: data
  })
}

// 删除产品信息
export function delProd(id) {
  return request({
    url: '/medium/prod/' + id,
    method: 'delete'
  })
}
