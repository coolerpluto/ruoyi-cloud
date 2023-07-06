import request from '@/utils/request'

// 查询内部网站收藏列表
export function listWebsite(query) {
  return request({
    url: '/message/website/list',
    method: 'get',
    params: query
  })
}

// 查询内部网站收藏详细
export function getWebsite(id) {
  return request({
    url: '/message/website/' + id,
    method: 'get'
  })
}

// 新增内部网站收藏
export function addWebsite(data) {
  return request({
    url: '/message/website',
    method: 'post',
    data: data
  })
}

// 修改内部网站收藏
export function updateWebsite(data) {
  return request({
    url: '/message/website',
    method: 'put',
    data: data
  })
}

// 删除内部网站收藏
export function delWebsite(id) {
  return request({
    url: '/message/website/' + id,
    method: 'delete'
  })
}
