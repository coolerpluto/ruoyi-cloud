import request from '@/utils/request'

// 查询员工用户列表
export function listEmployee(query) {
  return request({
    url: '/crm/employee/list',
    method: 'get',
    params: query
  })
}
// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/crm/employee/deptTree',
    method: 'get'
  })
}