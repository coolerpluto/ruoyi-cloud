import request from '@/utils/request'

// 查询商机支持人列表
export function listOpportunitySupport(query) {
  return request({
    url: '/crm/opportunitySupport/list',
    method: 'get',
    params: query
  })
}

// 查询商机支持人详细
export function getOpportunitySupport(id) {
  return request({
    url: '/crm/opportunitySupport/' + id,
    method: 'get'
  })
}

// 新增商机支持人
export function addOpportunitySupport(data) {
  return request({
    url: '/crm/opportunitySupport',
    method: 'post',
    data: data
  })
}

// 修改商机支持人
export function updateOpportunitySupport(data) {
  return request({
    url: '/crm/opportunitySupport',
    method: 'put',
    data: data
  })
}

// 删除商机支持人
export function delOpportunitySupport(id) {
  return request({
    url: '/crm/opportunitySupport/' + id,
    method: 'delete'
  })
}
