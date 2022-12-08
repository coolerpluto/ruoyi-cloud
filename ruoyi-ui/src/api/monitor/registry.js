import request from '@/utils/request'

// 查询在线用户列表
export function listCatalog() {
  return request({
    url: '/registry/v2/_catalog',
    method: 'get'
  })
}

export function listTags(image) {
  return request({
    url: '/registry/v2/'+image+'/tags/list',
    method: 'get'
  })
}

