import request from '@/utils/request'

// 查询代码生成数据源配置列表
export function listGenDbSource(query) {
  return request({
    url: '/code/gen/dbSource/list',
    method: 'get',
    params: query
  })
}

// 查询代码生成数据源配置详细
export function getGenDbSource(id) {
  return request({
    url: '/code/gen/dbSource/' + id,
    method: 'get'
  })
}

// 新增代码生成数据源配置
export function addGenDbSource(data) {
  return request({
    url: '/code/gen/dbSource',
    method: 'post',
    data: data
  })
}

// 修改代码生成数据源配置
export function updateGenDbSource(data) {
  return request({
    url: '/code/gen/dbSource',
    method: 'put',
    data: data
  })
}

// 删除代码生成数据源配置
export function delGenDbSource(id) {
  return request({
    url: '/code/gen/dbSource/' + id,
    method: 'delete'
  })
}
