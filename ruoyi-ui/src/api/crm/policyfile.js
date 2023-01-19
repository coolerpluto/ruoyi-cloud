import request from '@/utils/request'

// 查询政策文件列表
export function listPolicyfile(query) {
  return request({
    url: '/crm/policyfile/list',
    method: 'get',
    params: query
  })
}

// 查询政策文件详细
export function getPolicyfile(id) {
  return request({
    url: '/crm/policyfile/' + id,
    method: 'get'
  })
}

// 新增政策文件
export function addPolicyfile(data) {
  return request({
    url: '/crm/policyfile',
    method: 'post',
    data: data
  })
}
// 新增政策文件
export function addPolicyfileWithFile(data) {
  return request({
    url: '/crm/policyfile/addWithFile',
    method: 'post',
    data: data,
    timeout: 60000,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 修改政策文件
export function updatePolicyfileWithFile(data) {
  return request({
    url: '/crm/policyfile/editWithFile',
    method: 'put',
    data: data,
    timeout: 60000,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 修改政策文件
export function updatePolicyfile(data) {
  return request({
    url: '/crm/policyfile',
    method: 'put',
    data: data
  })
}

// 删除政策文件
export function delPolicyfile(id) {
  return request({
    url: '/crm/policyfile/' + id,
    method: 'delete'
  })
}
