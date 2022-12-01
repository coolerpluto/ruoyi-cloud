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

// 新增文件记录
export function addFile(data) {
  return request({
    url: '/medium/file',
    method: 'post',
    data: data
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
