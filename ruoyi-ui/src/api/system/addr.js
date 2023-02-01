import request from '@/utils/request'

// 查询地址联动列表
export function listAddr(query) {
  return request({
    url: '/system/addr/list',
    method: 'get',
    params: query
  })
}
// 查询地址联动列表
export function listPageAddr(query) {
  return request({
    url: '/system/addr/listPage',
    method: 'get',
    params: query
  })
}

// 查询地址联动详细
export function getAddr(id) {
  return request({
    url: '/system/addr/' + id,
    method: 'get'
  })
}

// 新增地址联动
export function addAddr(data) {
  return request({
    url: '/system/addr',
    method: 'post',
    data: data
  })
}

// 修改地址联动
export function updateAddr(data) {
  return request({
    url: '/system/addr',
    method: 'put',
    data: data
  })
}

// 删除地址联动
export function delAddr(id) {
  return request({
    url: '/system/addr/' + id,
    method: 'delete'
  })
}
