import request from '@/utils/request'

// 查询商机阶段历史列表
export function listOppStageChangeHis(query) {
  return request({
    url: '/crm/oppStageChangeHis/list',
    method: 'get',
    params: query
  })
}

// 查询商机阶段历史详细
export function getOppStageChangeHis(id) {
  return request({
    url: '/crm/oppStageChangeHis/' + id,
    method: 'get'
  })
}

// 新增商机阶段历史
export function addOppStageChangeHis(data) {
  return request({
    url: '/crm/oppStageChangeHis',
    method: 'post',
    data: data
  })
}

// 修改商机阶段历史
export function updateOppStageChangeHis(data) {
  return request({
    url: '/crm/oppStageChangeHis',
    method: 'put',
    data: data
  })
}

// 删除商机阶段历史
export function delOppStageChangeHis(id) {
  return request({
    url: '/crm/oppStageChangeHis/' + id,
    method: 'delete'
  })
}
