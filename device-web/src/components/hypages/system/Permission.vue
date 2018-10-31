<template>
    <div>
        <Blockquote>
            <span slot="txt">授权</span>
            <el-button size="small" slot="btns"  icon="el-icon-circle-check-outline" @click="savePermission">提交</el-button>
        </Blockquote>
        <div class="container">
            <el-table :data="tableData">
                <el-table-column prop="name" label="系统模块" width="150"></el-table-column>
                <el-table-column label="主菜单" width="150">
                    <template slot-scope="scope">
                        <div v-for="(menuItem,menuIndex) in scope.row.subMenu" >{{ menuItem.name }}
                            <hr>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="功能菜单">
                        <template slot-scope="scope" >
                            <div  v-for="(menuItem,menuIndex) in scope.row.subMenu">
                                <el-checkbox v-for="(funcItem,funcIndex) in menuItem.subFunc" :key="funcItem.id"  v-model="funcItem.checked" >{{ funcItem.name}}</el-checkbox>
                                <hr>
                            </div>

                        </template>
                </el-table-column>
            </el-table>

        </div>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                tableData: [],
                roleId:''
            }
        },
        methods:{
            getPermission(){
              this.$axios({
                  method:"get",
                  url: this.HOST+"/system/getPermission",
                  params:{"roleId":this.roleId}
              }).then((res) => {
                  this.tableData = res.data.data;

              })
            },
            savePermission(){
                this.$axios({
                    method:"post",
                    url: this.HOST+"/system/savePermission",
                    data:{"roleId":1,"permissions":JSON.stringify(this.tableData)}
                    }).then((res) => {
                        if(res.data.code ===0){
                            this.$message.success(res.data.msg)
                        }
                })
            }
        },
        created:function () {
            //这边接收上个组件传递过来的roleId
            this.roleId = this.$route.query.roleId;
        },
        mounted:function () {
            this.getPermission();
        }

    }
</script>

<style scoped>
    .divrow{
        line-height: 19px;
        font-size: 14px;
    }

</style>
