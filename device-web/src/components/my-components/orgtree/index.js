import Orgtree from './OrgTree.vue'
// 这里是重点
const OrgTree = {
    install: function(Vue){
        Vue.component('OrgTree',Orgtree)
    }
}

// 导出组件
export default OrgTree
