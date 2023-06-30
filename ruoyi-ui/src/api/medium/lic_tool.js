import request from '@/utils/request'

// 查询License工具信息列表
export function listLic_tool(query) {
  return request({
    url: '/medium/lic_tool/list',
    method: 'get',
    params: query
  })
}

// 查询License工具信息详细
export function getLic_tool(id) {
  return request({
    url: '/medium/lic_tool/' + id,
    method: 'get'
  })
}

// 新增License工具信息
export function addLic_tool(data) {
  return request({
    url: '/medium/lic_tool',
    method: 'post',
    data: data
  })
}
// 新增License工具信息
export function addLic_tool_withFile(data) {
  return request({
    url: '/medium/lic_tool/withFile',
    method: 'post',
    data: data
  })
}

// 修改License工具信息
export function updateLic_tool(data) {
  return request({
    url: '/medium/lic_tool',
    method: 'put',
    data: data
  })
}
// 修改License工具信息
export function updateLic_tool_withFile(data) {
  return request({
    url: '/medium/lic_tool/withFile',
    method: 'put',
    data: data
  })
}
// 删除License工具信息
export function delLic_tool(id) {
  return request({
    url: '/medium/lic_tool/' + id,
    method: 'delete'
  })
}
