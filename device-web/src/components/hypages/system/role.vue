<template>
    <div class="">
        <Blockquote>
            <span slot="txt">角色列表</span>
            <el-button slot="btns" size="small" icon="el-icon-circle-plus-outline" @click="handleEdit" >添加</el-button>
        </Blockquote>
        <el-table
            :data="tableData"
            style="width:100%">
            <el-table-column prop="id" label="id"></el-table-column>
            <el-table-column prop="role" label="角色"></el-table-column>
            <el-table-column prop="name" label="角色名"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <a href="javascript:;" @click="handleEdit(scope.$index,scope.row)"><i class="el-icon-edit-outline"></i> 编辑</a>
                    <a href="javascript:;" @click="handleDelete(scope.$index,scope.row)"><i class="el-icon-delete"></i> 删除</a>
                    <router-link :to="{path: '/permission',query:{roleId:scope.row.id}}">授权</router-link>
                </template>
            </el-table-column>
        </el-table>
        <!-- 编辑弹出框 -->
        <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="40%">
            <el-form ref="formName" :model="role" label-width="100px">
                <el-input type="hidden" v-model="role.id"></el-input>
                <el-form-item label="角色名">
                    <el-input v-model="role.role"></el-input>
                </el-form-item>

                <el-form-item label="角色中文名">
                    <el-input v-model="role.name"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit('form')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="delVisible = false">取 消</el-button>
            <el-button type="primary" @click="deleteRow">确 定</el-button>
        </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "role"
        ,data(){
            return {
                tableData:[],
                role:{},
                dialogTitle:'编辑',
                editVisible:false,
                delVisible:false,
                idx:-1
            }
        },
        methods:{
            init:function(){
                this.getRole();

            },
            getRole(){
                this.$axios.get(
                    this.HOST+"/system/getRole"
                ).then((res) => {
                    if(res.data.code === 0){
                        this.tableData = res.data.data;
                    }
                })
            },
            handleEdit(index, row) {
                if (row != null){
                    this.role = row;
                    this.dialogTitle = '编辑';
                }else {
                    this.role = { };
                    this.dialogTitle = '新增';
                }
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit(formName) {
                this.$axios({
                    method: "post",
                    url: this.HOST +"/system/saveRole",
                    data: this.role
                }).then((res) => {
                    if(res.data.code == 0) {
                        this.getRole();
                        this.editVisible = false;
                        this.$message.success('保存成功');
                    }else{
                        this.$message.error(res.data.msg);
                    }
                })
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },
            // 确定删除
            deleteRow(){
                this.$axios({
                    method: "get",
                    url: this.HOST +"/system/delRole",
                    params: {id: this.tableData[this.idx].id}
                }).then((res) => {
                    if(res.data.code == 0){

                        this.tableData.splice(this.idx, 1);
                        this.$message.success('删除成功');
                        this.delVisible = false;
                    }else{
                        this.$message.error(res.data.msg);
                        this.delVisible = true;
                    }
                })
            }
        },
        created:function () {
            this.init();
         }
    }
</script>

<style scoped>

</style>
