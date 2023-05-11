import request from '@/utils/request'

// 查询短信记录列表
export function listSms(query) {
  return request({
    url: '/message/sms/list',
    method: 'get',
    params: query
  })
}

// 查询短信记录详细
export function getSms(id) {
  return request({
    url: '/message/sms/' + id,
    method: 'get'
  })
}

// 新增短信记录
export function addSms(data) {
  return request({
    url: '/message/sms',
    method: 'post',
    data: data
  })
}

// 修改短信记录
export function updateSms(data) {
  return request({
    url: '/message/sms',
    method: 'put',
    data: data
  })
}

// 删除短信记录
export function delSms(id) {
  return request({
    url: '/message/sms/' + id,
    method: 'delete'
  })
}
