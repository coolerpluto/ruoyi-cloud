import request from '@/utils/request'

// 查询生成表数据
export function listTable(query) {
  return request({
    url: '/code/gen/list',
    method: 'get',
    params: query
  })
}
// 查询db数据库列表
export function listDbTable(query) {
  return request({
    url: '/code/gen/db/list',
    method: 'get',
    params: query
  })
}
// 查询db数据库列表
export function listAssignedDbTable(query) {
  return request({
    url: '/code/gen/assignedDB/list',
    method: 'get',
    params: query
  })
}

// 查询表详细信息
export function getGenTable(tableId) {
  return request({
    url: '/code/gen/' + tableId,
    method: 'get'
  })
}

// 修改代码生成信息
export function updateGenTable(data) {
  return request({
    url: '/code/gen',
    method: 'put',
    data: data
  })
}

// 导入表
export function importTable(data) {
  return request({
    url: '/code/gen/importTable',
    method: 'post',
    params: data
  })
}
// 导入表
export function importTableAssignedDB(data) {
  return request({
    url: '/code/gen/assignedDB/importTable',
    method: 'post',
    params: data
  })
}
// 添加表
export function addTable(data) {
  return request({
    url: '/code/gen/addTable',
    method: 'post',
    params: data
  })
}
// 预览生成代码
export function previewTable(tableId) {
  return request({
    url: '/code/gen/preview/' + tableId,
    method: 'get'
  })
}

// 删除表数据
export function delTable(tableId) {
  return request({
    url: '/code/gen/' + tableId,
    method: 'delete'
  })
}

// 生成代码（自定义路径）
export function genCode(tableName) {
  return request({
    url: '/code/gen/genCode/' + tableName,
    method: 'get'
  })
}

// 同步数据库
export function synchDb(tableName) {
  return request({
    url: '/code/gen/synchDb/' + tableName,
    method: 'get'
  })
}
// 同步数据库
export function synchAssignedDBDb(data) {
  return request({
    url: '/code/gen/assignedDB/synchDb/',
    method: 'get',
    params: data
  })
}
// 添加字段
export function addTableColumn(data) {
  return request({
    url: '/code/gen/column/addTableColumn',
    method: 'post',
    params: data
  })
}
// 查询数据表字段列表
export function columnList(tableId) {
  return request({
    url: '/code/gen/column/' + tableId,
    method: 'get'
  })
}
// 删除数据表字段列表
export function delTableColumn(columnId) {
  return request({
    url: '/code/gen/column/' + columnId,
    method: 'delete'
  })
}
