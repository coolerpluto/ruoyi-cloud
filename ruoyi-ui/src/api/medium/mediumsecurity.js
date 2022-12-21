import request from '@/utils/request'

// 查询介质安全文件记录列表
export function listMediumsecurity(query) {
  return request({
    url: '/medium/mediumsecurity/list',
    method: 'get',
    params: query
  })
}

// 查询介质安全文件记录详细
export function getMediumsecurity(id) {
  return request({
    url: '/medium/mediumsecurity/' + id,
    method: 'get'
  })
}

// 新增介质安全文件记录
export function addMediumsecurity(data) {
  return request({
    url: '/medium/mediumsecurity',
    method: 'post',
    data: data
  })
}
// 新增介质安全文件记录
export function addMediumsecurityWithFile(data) {
  return request({
    url: '/medium/mediumsecurity/addWithFile',
    method: 'post',
    data: data,
    timeout: 60000,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 修改介质安全文件记录
export function updateMediumsecurityWithFile(data) {
  return request({
    url: '/medium/mediumsecurity/editWithFile',
    method: 'put',
    data: data,
    timeout: 60000,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
// 修改介质安全文件记录
export function updateMediumsecurity(data) {
  return request({
    url: '/medium/mediumsecurity',
    method: 'put',
    data: data
  })
}

// 删除介质安全文件记录
export function delMediumsecurity(id) {
  return request({
    url: '/medium/mediumsecurity/' + id,
    method: 'delete'
  })
}
