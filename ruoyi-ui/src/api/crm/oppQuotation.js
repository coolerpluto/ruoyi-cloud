import request from '@/utils/request'

// 查询商机产品报价列表
export function listOpportunityQuotation(query) {
  return request({
    url: '/crm/opportunityQuotation/list',
    method: 'get',
    params: query
  })
}

// 查询商机产品报价详细
export function getOpportunityQuotation(id) {
  return request({
    url: '/crm/opportunityQuotation/' + id,
    method: 'get'
  })
}

// 新增商机产品报价
export function addOpportunityQuotation(data) {
  return request({
    url: '/crm/opportunityQuotation',
    method: 'post',
    data: data
  })
}

// 修改商机产品报价
export function updateOpportunityQuotation(data) {
  return request({
    url: '/crm/opportunityQuotation',
    method: 'put',
    data: data
  })
}

// 删除商机产品报价
export function delOpportunityQuotation(id) {
  return request({
    url: '/crm/opportunityQuotation/' + id,
    method: 'delete'
  })
}
