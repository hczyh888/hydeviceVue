<template>
    <div class="container">
            <div class="text-center">
                <OrgTree
                    :data="data"
                    :horizontal="horizontal"
                    :collapsable="collapsable"
                    :label-class-name="labelClassName"
                    :render-content="renderContent"
                    @on-expand="onExpand"
                    @on-node-click="onNodeClick"
                >
                </OrgTree>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'dept',
        data () {
            return {
                data: {
                    id: 0,
                    label: 'XXX科技有限公司',
                    children: [{
                        id: 2,
                        label: '产品研发部',
                        children: [{
                            id: 5,
                            label: '研发-前端'
                        }, {
                            id: 6,
                            label: '研发-后端'
                        }, {
                            id: 9,
                            label: 'UI设计'
                        }, {
                            id: 10,
                            label: '产品经理'
                        }]
                    }, {
                        id: 3,
                        label: '销售部',
                        children: [{
                            id: 7,
                            label: '销售一部'
                        }, {
                            id: 8,
                            label: '销售二部'
                        }]
                    }, {
                        id: 4,
                        label: '财务部'
                    }, {
                        id: 9,
                        label: 'HR人事'
                    }]
                },
                horizontal: false,
                collapsable: true,
                labelClassName: 'bg-white'
            }
        },
        methods: {
            renderContent (h, data) {
                return data.label
            },
            onExpand (data) {
                if ('expand' in data) {
                    data.expand = !data.expand

                    if (!data.expand && data.children) {
                        this.collapse(data.children)
                    }
                } else {
                    this.$set(data, 'expand', true)
                }
            },
            onNodeClick (e, data) {
                alert("id="+data.id+";label="+data.label)
            },
            collapse (list) {
                list.forEach(child => {
                    if (child.expand) {
                        child.expand = false
                    }

                    child.children && this.collapse(child.children)
                })
            }
        }
    }
</script>

<style scoped>
    .bg-white {
        background-color: white;
    }
    .bg-orange {
        background-color: orange;
    }
    .bg-gold {
        background-color: gold;
    }
    .bg-gray {
        background-color: gray;
    }
    .bg-lightpink {
        background-color: lightpink;
    }
    .bg-chocolate {
        background-color: chocolate;
    }
    .bg-tomato {
        background-color: tomato;
    }
</style>
