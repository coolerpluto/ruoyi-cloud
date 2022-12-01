import request from '@/utils/request'

// 查询介质文件信息记录列表
export function listMedium(query) {
  return request({
    url: '/medium/medium/list',
    method: 'get',
    params: query
  })
}

// 查询介质文件信息记录详细
export function getMedium(id) {
  return request({
    url: '/medium/medium/' + id,
    method: 'get'
  })
}

// 新增介质文件信息记录
export function addMedium(data) {
  return request({
    url: '/medium/medium',
    method: 'post',
    data: data
  })
}

// 修改介质文件信息记录
export function updateMedium(data) {
  return request({
    url: '/medium/medium',
    method: 'put',
    data: data
  })
}

// 删除介质文件信息记录
export function delMedium(id) {
  return request({
    url: '/medium/medium/' + id,
    method: 'delete'
  })
}
