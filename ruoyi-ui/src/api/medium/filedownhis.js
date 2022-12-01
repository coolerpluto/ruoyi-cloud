import request from '@/utils/request'

// 查询下载记录列表
export function listFiledownhis(query) {
  return request({
    url: '/medium/filedownhis/list',
    method: 'get',
    params: query
  })
}

// 查询下载记录详细
export function getFiledownhis(id) {
  return request({
    url: '/medium/filedownhis/' + id,
    method: 'get'
  })
}

// 新增下载记录
export function addFiledownhis(data) {
  return request({
    url: '/medium/filedownhis',
    method: 'post',
    data: data
  })
}

// 修改下载记录
export function updateFiledownhis(data) {
  return request({
    url: '/medium/filedownhis',
    method: 'put',
    data: data
  })
}

// 删除下载记录
export function delFiledownhis(id) {
  return request({
    url: '/medium/filedownhis/' + id,
    method: 'delete'
  })
}
