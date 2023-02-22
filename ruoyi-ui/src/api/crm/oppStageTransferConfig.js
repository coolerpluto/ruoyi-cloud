import request from '@/utils/request'

// 查询商机阶段流转配置列表
export function listOppStageTransferConfig(query) {
  return request({
    url: '/crm/oppStageTransferConfig/list',
    method: 'get',
    params: query
  })
}

// 查询商机阶段流转配置详细
export function getOppStageTransferConfig(id) {
  return request({
    url: '/crm/oppStageTransferConfig/' + id,
    method: 'get'
  })
}

// 新增商机阶段流转配置
export function addOppStageTransferConfig(data) {
  return request({
    url: '/crm/oppStageTransferConfig',
    method: 'post',
    data: data
  })
}

// 修改商机阶段流转配置
export function updateOppStageTransferConfig(data) {
  return request({
    url: '/crm/oppStageTransferConfig',
    method: 'put',
    data: data
  })
}

// 删除商机阶段流转配置
export function delOppStageTransferConfig(id) {
  return request({
    url: '/crm/oppStageTransferConfig/' + id,
    method: 'delete'
  })
}
