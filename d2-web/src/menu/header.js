// 菜单 顶栏
export default [
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '系统设置',
    icon: 'folder-o',
    children: [
      { path: '/system/user', title: '用户管理' },
      { path: '/system/role', title: '角色管理' },
      { path: '/system/menu', title: '菜单管理' },
      { path: '/system/module', title: '模块管理' }
    ]
  }
]
