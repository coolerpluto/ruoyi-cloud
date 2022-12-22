import request from '@/utils/request'

// 查询文件记录列表
export function listFile(query) {
  return request({
    url: '/medium/file/list',
    method: 'get',
    params: query
  })
}

// 查询文件记录详细
export function getFile(id) {
  return request({
    url: '/medium/file/' + id,
    method: 'get'
  })
}
// 查询文件关联详细
export function getFileAssociation(id) {
  return request({
    url: '/medium/file/association/' + id,
    method: 'get'
  })
}
// 新增文件记录
export function addFile(data) {
  return request({
    url: '/medium/file',
    method: 'post',
    data: data
  })
}
// 新增文件记录 带附件模式
export function addWithFile(data) {
  return request({
    url: '/medium/file/addWithFile',
    method: 'post',
    data: data,
    timeout: 60000,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 修改文件记录 带附件模式
export function updateWithFile(data) {
  return request({
    url: '/medium/file/editWithFile',
    method: 'put',
    data: data,
    timeout: 60000,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
// 修改文件记录
export function updateFile(data) {
  return request({
    url: '/medium/file',
    method: 'put',
    data: data
  })
}

// 删除文件记录
export function delFile(id) {
  return request({
    url: '/medium/file/' + id,
    method: 'delete'
  })
}
