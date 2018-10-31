<template>
    <div>
        <Blockquote>
            <span slot="txt">菜单列表</span>
            <div slot="btns">
                <el-button size="small" icon="el-icon-circle-plus-outline" @click="addLanmu">添加一级菜单</el-button>
            </div>
        </Blockquote>
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="sort_num" width="60" label="排序">
                    <template slot-scope="scope">
                        <el-input @change="handleSort(scope.$index)" class="input-sort" v-model="scope.row.sort_num"  placeholder=""></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="菜单Id"  width="70">
                </el-table-column>
                <el-table-column prop="name"  label="菜单名称" width="120">
                    <template slot-scope="scope">
                        <span class="first_level" v-if="scope.row.up_levelId!=0">├─</span>{{scope.row.name}}
                    </template>
                </el-table-column>
                <el-table-column prop="lanmu_type" label="菜单类型">
                    <template slot-scope="scope">
                        {{scope.row.lanmu_type==0?'菜单项':'功能菜单'}}
                    </template>
                </el-table-column>

                <el-table-column prop="route" label="路由">
                </el-table-column>
                <el-table-column prop="font_icon" label="图标">
                    <template slot-scope="scope">
                      <i :class="scope.row.font_icon"></i>
                    </template>
                </el-table-column>
                <el-table-column prop="module_name" label="所属模块">
                </el-table-column>
                <el-table-column label="操作" width="220">
                    <template slot-scope="scope">
                        <router-link v-if="scope.row.is_systerm==0" :to="{ path: '/lanmuEdit?lanmuId='+scope.row.id}"><i class="el-icon-edit-outline"></i>编辑 </router-link>
                        <a v-if="scope.row.is_systerm==0" href="javascript:;" @click="handleDelete(scope.$index, scope.row)"> <i class="el-icon-delete"></i>删除 </a>
                        <router-link v-if="scope.row.is_systerm==0&&scope.row.up_levelId==0" :to="{ path: '/lanmuEdit?up_levelId='+scope.row.id}"> <i class="fa fa-plus"></i>添加子菜单</router-link>
                    </template>
                </el-table-column>
            </el-table>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],

            }
        },
        created() {
            this.getData()
        },
        methods: {
            addLanmu(){
                this.$router.push({path:'/lanmuEdit?up_levelId=0',title:"添加栏目"});
            },
            getData() {
                this.$axios.get(this.HOST+'/getAllLanmu',
                    {
                        params:{page: 1,moduleId: this.$route.query.moduleId}
                    }
                ).then((res) => {
                    this.tableData = res.data.data;
                })
            },
            handleSelectionChange(val) {

            },
            handleEdit(index, row) {
                this.$router.push('/contentEdit?lanmuId='+row.id);
            },
            handleDelete(index, row) {
                this.$message.success('删除成功，测试');
                this.tableData.splice(index, 1);
                this.$axios.get(this.HOST+'/delLanmu',
                    {params:{lanmuId: row.id}}
                ).then((res) => {
                    if(res.data.code==0) {
                        this.$message.success('删除成功');
                        this.tableData.splice(index, 1);
                    }else{
                        this.$message.error(res.data.msg);
                    }
                })

            },
            handleSort(index){
                let rowDatas=this.tableData[index];
                delete rowDatas.module_name;
                this.$axios({
                    method:"post",
                    url: this.HOST+"/saveLanmu",
                    data: rowDatas
                }).then((res)=>{
                    if(res.data.code==0) {
                        this.getData();
                        this.$message.success('保存成功');
                    }else{
                        this.$message.error(res.data.msg);
                    }
                })
            }

        },
        computed: {

        }
    }

</script>

<style>
.first_level{
    margin-left: 20px;
}
</style>

