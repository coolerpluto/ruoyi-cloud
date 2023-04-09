import request from '@/utils/request'

// 查询员工用户列表
export function listEmployee(query) {
  return request({
    url: '/crm/employee/list',
    method: 'get',
    params: query
  })
}
