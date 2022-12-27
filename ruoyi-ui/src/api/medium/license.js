import request from '@/utils/request'

// 查询License文件记录列表
export function listLicense(query) {
  return request({
    url: '/medium/license/list',
    method: 'get',
    params: query
  })
}

// 查询License文件记录详细
export function getLicense(id) {
  return request({
    url: '/medium/license/' + id,
    method: 'get'
  })
}

// 新增License文件记录
export function addLicense(data) {
  return request({
    url: '/medium/license',
    method: 'post',
    data: data
  })
}

// 修改License文件记录
export function updateLicense(data) {
  return request({
    url: '/medium/license',
    method: 'put',
    data: data
  })
}

// 删除License文件记录
export function delLicense(id) {
  return request({
    url: '/medium/license/' + id,
    method: 'delete'
  })
}

// 生成
export function generate(data) {
  return request({
      url: '/medium/license/generator',
      method: 'post',
      data: data
  })
}
