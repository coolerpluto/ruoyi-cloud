import request from '@/utils/request'

// 查询列表
export function listUnitedOpp(query) {
  return request({
    url: '/crm/oppUnited/list',
    method: 'get',
    params: query
  })
}

// 查询阶段配置和阶段信息
export function stageConfigAndInfo(query) {
  return request({
    url: '/crm/oppUnited/stageConfigAndInfo',
    method: 'get',
    params: query
  })
}
// 查询全部属性配置
export function getPropertiesMap(query) {
  return request({
    url: '/crm/oppUnited/getPropertiesMap',
    method: 'post',
    data: query
  })
}
// 查询商机基础信息
export function getOppBaseInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppBaseInfo',
    method: 'get',
    params: query
  })
}
// 查询商机基础信息
export function getOppUserInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppUserInfo',
    method: 'get',
    params: query
  })
}
// 查询政策支持信息
export function getOppPolicyInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppPolicyInfo',
    method: 'get',
    params: query
  })
}
// 查询商机进展信息
export function getOppAdvancesInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppAdvancesInfo',
    method: 'get',
    params: query
  })
}
// 查询商机花费信息
export function getOppCostInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppCostInfo',
    method: 'get',
    params: query
  })
}
// 查询商机竞争对手信息
export function getOppCompetitorInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppCompetitorInfo',
    method: 'get',
    params: query
  })
}
// 查询商机报价信息
export function getOppQuotationInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppQuotationInfo',
    method: 'get',
    params: query
  })
}
// 查询商机支持人信息
export function getOppSupportInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppSupportInfo',
    method: 'get',
    params: query
  })
}
// 查询商机联系人信息
export function getOppContactsInfo(query) {
  return request({
    url: '/crm/oppUnited/getOppContactsInfo',
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
// 分享管理
export function shareUnitedOpp(data) {
  return request({
    url: '/crm/oppUnited/share',
    method: 'put',
    data: data
  })
}

