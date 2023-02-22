import request from '@/utils/request'

// 查询竞争对手列表
export function listOppCompetitor(query) {
  return request({
    url: '/crm/oppCompetitor/list',
    method: 'get',
    params: query
  })
}

// 查询竞争对手详细
export function getOppCompetitor(id) {
  return request({
    url: '/crm/oppCompetitor/' + id,
    method: 'get'
  })
}

// 新增竞争对手
export function addOppCompetitor(data) {
  return request({
    url: '/crm/oppCompetitor',
    method: 'post',
    data: data
  })
}

// 修改竞争对手
export function updateOppCompetitor(data) {
  return request({
    url: '/crm/oppCompetitor',
    method: 'put',
    data: data
  })
}

// 删除竞争对手
export function delOppCompetitor(id) {
  return request({
    url: '/crm/oppCompetitor/' + id,
    method: 'delete'
  })
}
