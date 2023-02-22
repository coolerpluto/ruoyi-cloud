import request from '@/utils/request'

// 查询商机应用运营信息列表
export function listOppSoftwareOperation(query) {
  return request({
    url: '/crm/oppSoftwareOperation/list',
    method: 'get',
    params: query
  })
}

// 查询商机应用运营信息详细
export function getOppSoftwareOperation(id) {
  return request({
    url: '/crm/oppSoftwareOperation/' + id,
    method: 'get'
  })
}

// 新增商机应用运营信息
export function addOppSoftwareOperation(data) {
  return request({
    url: '/crm/oppSoftwareOperation',
    method: 'post',
    data: data
  })
}

// 修改商机应用运营信息
export function updateOppSoftwareOperation(data) {
  return request({
    url: '/crm/oppSoftwareOperation',
    method: 'put',
    data: data
  })
}

// 删除商机应用运营信息
export function delOppSoftwareOperation(id) {
  return request({
    url: '/crm/oppSoftwareOperation/' + id,
    method: 'delete'
  })
}
