import layoutHeaderAside from '@/layout/header-aside'

const meta = { requiresAuth: true }

export default {
  path: '/system',
  name: 'system',
  meta,
  redirect: { name: 'system-user' },
  component: layoutHeaderAside,
  children: (pre => [
    { path: 'user', name: `${pre}user`, component: () => import('@/pages/system/user'), meta: { meta, title: '用户管理' } },
    { path: 'role', name: `${pre}role`, component: () => import('@/pages/system/role'), meta: { meta, title: '角色管理' } },
    { path: 'menu', name: `${pre}menu`, component: () => import('@/pages/system/menu'), meta: { meta, title: '菜单管理' } },
    { path: 'module', name: `${pre}module`, component: () => import('@/pages/system/module'), meta: { meta, title: '模块管理' } }
  ])('system-')
}
