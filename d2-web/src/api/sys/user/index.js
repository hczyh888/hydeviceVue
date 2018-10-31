import request from '@/plugin/axios'

export function getUserList (params) {
  return request({
    url: '/user/getUserList',
    method: 'get',
    params
  })
}
