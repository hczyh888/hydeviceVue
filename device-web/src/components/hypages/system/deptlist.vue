<template>
    <div>
        <div class="handle-box">
            <Blockquote>
                <span slot="txt">部门科室</span>
                <el-dropdown slot="btns"  trigger="click">
                    <el-button plain>{{ !isDeleted?'部门列表':'回收站' }}
                     <i class="el-icon-caret-bottom el-icon--right"/>
                    </el-button>
                    <el-dropdown-menu slot="dropdown" class="no-padding">
                        <el-dropdown-item>
                            <el-radio-group v-model="isDeleted" style="padding: 10px;" @change="getData">
                                <el-radio :label="false">部门列表</el-radio>
                                <el-radio :label="true">回收站</el-radio>
                            </el-radio-group>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </Blockquote>
        </div>
        <div class="container" v-show="!isDeleted">
            <el-table :data="tableData" border style="width: 100%" >
                <el-table-column prop="sort_num" width="60" label="排序">
                    <template slot-scope="scope">
                        <el-input @change="handleSort(scope.$index)" class="input-sort" v-model="scope.row.sort_num"  placeholder=""></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="部门Id"  width="70">
                </el-table-column>
                <el-table-column prop="name"  label="部门名称" >
                    <template slot-scope="scope">
                        <span class="first_level" v-if="scope.row.level==1">├─</span>
                        <span class="first_level" v-else-if="scope.row.level==2">&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                        <span class="first_level" v-else-if="scope.row.level==3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                        <span class="first_level" v-else-if="scope.row.level==4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                        {{scope.row.name}}
                    </template>
                </el-table-column>
                <el-table-column prop="alias_name" label="部门别名" width="100"></el-table-column>

                <el-table-column label="操作" width="220">
                    <template slot-scope="scope">
                        <a v-if="scope.row.pid!=0" href="javascript:;" @click="handleEdit(scope.$index,scope.row)"><i class="el-icon-edit-outline"></i>编辑 </a>
                        <a v-if="scope.row.pid!=0" href="javascript:;" @click="handleDelete(scope.$index, scope.row)"> <i class="el-icon-delete"></i>删除 </a>
                        <a href="javascript:;" @click="addChildDept(scope.row.id,scope.row.level+1,scope.row.name)"> <i class="fa fa-plus"></i>添加下级部门</a>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="container" v-show="isDeleted">
            <el-table :data="tableData" border style="width: 100%" >
                <el-table-column prop="sort_num" width="60" label="排序">
                    <template slot-scope="scope">
                        <el-input @change="handleSort(scope.$index)" class="input-sort" v-model="scope.row.sort_num"  placeholder=""></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="部门Id"  width="70">
                </el-table-column>
                <el-table-column prop="name"  label="部门名称" >
                    <template slot-scope="scope">
                        <span class="first_level" v-if="scope.row.level==1">├─</span>
                        <span class="first_level" v-else-if="scope.row.level==2">&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                        <span class="first_level" v-else-if="scope.row.level==3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                        <span class="first_level" v-else-if="scope.row.level==4">AAA├─</span>
                        {{scope.row.name}}
                    </template>
                </el-table-column>
                <el-table-column prop="alias_name" label="部门别名" width="100"></el-table-column>

                <el-table-column label="操作" width="220">
                    <template slot-scope="scope">
                        <a v-if="scope.row.pid!=0" href="javascript:;" @click="handleRecovery(scope.$index,scope.row)"><i class="el-icon-edit-outline"></i>恢复 </a>
                        <a v-if="scope.row.pid!=0" href="javascript:;" @click="handleDelete(scope.$index, scope.row)"> <i class="el-icon-delete"></i>删除 </a>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!--弹出框-->
        <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="dept" label-width="20%">
                <el-input type="hidden" v-model="dept.id"></el-input>
                <el-form-item label="上级部门">
                    <span>{{ pName }}</span>
                </el-form-item>
                <el-form-item label="部门名称" prop="name"
                                :rules="[{required:true,message:'请输入部门名称',trigger:'blur'}]">
                    <el-input v-model="dept.name"></el-input>
                </el-form-item>
                <el-form-item label="别名">
                    <el-input v-model="dept.alias_name"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input type="number" v-model="dept.sort_num"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible=false">取消</el-button>
                <el-button type="primary" @click="saveEdit('form')">确定</el-button>
            </span>
        </el-dialog>
        <!--删除提示框-->
        <el-dialog title="提示" :visible.sync="delVisible" width="300" center>
            <div v-show="isDeleted" class="del-dialog-cnt">删除不可回复，是否确定删除？</div>
            <div v-show="!isDeleted" class="del-dialog-cnt">放到回收站，是否确定？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button v-show="isDeleted" type="primary" @click="deleteRow">确 定</el-button>
                <el-button v-show="!isDeleted" type="primary" @click="updateRow">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>


    export default {

        name: "deptlist",
        data(){
            return {
                isDeleted:false,
                tableData:[],
                editVisible:false,
                delVisible:false,
                idx:-1,
                dialogTitle:'增加',
                dept:{},
                pName:''
            }
        },
        created(){
          this.getData();
        },

        methods:{
            addChildDept(i_pid,i_level,s_pName){
                this.dialogTitle = '增加下级部门';
                this.dept ={pid:i_pid,level:i_level,sort_num:1};
                this.pName =s_pName;
                this.editVisible = true;
            },
            getData() {
                this.$axios.get(this.HOST+'/system/getDepts',{
                    params:{deletedTag:this.isDeleted?1:0}
                }).then((res) => {
                    this.tableData = res.data.data;
                })
            },
            // 保存编辑
            saveEdit(form) {
                this.$refs[form].validate((valid) => {
                    if(valid){
                        this.$axios({
                            method: "post",
                            url: this.HOST +"/system/saveDept",
                            data: this.dept
                        }).then((res) => {
                            if(res.data.code == 0) {
                                this.getData();
                                this.editVisible = false;
                                this.$message.success('保存成功');
                            }else{
                                this.$message.error(res.data.msg);
                            }
                        })
                    }else{
                     return false;
                    }
                })
            },
            updateRow(){
                this.$axios({
                    methos:"get",
                    url:this.HOST +"/system/updateDept",
                    params:{id:this.tableData[this.idx].id,deleted:this.isDeleted?0:1}
                }).then((res) => {
                    if(res.data.code == 0){
                        this.tableData.splice(this.idx,1);
                        this.$message.success('成功');
                        this.delVisible = false;
                    }else{
                        this.$message.error(res.data.msg);
                        this.delVisible = true;
                    }

                })
            },
            deleteRow(){
                this.$axios({
                    methos:"get",
                    url:this.HOST +"/system/delDept",
                    params:{id:this.tableData[this.idx].id}
                }).then((res) => {
                    if(res.data.code == 0){
                        this.tableData.splice(this.idx,1);
                        this.$message.success('成功');
                        this.delVisible = false;
                    }else{
                        this.$message.error(res.data.msg);
                        this.delVisible = true;
                    }

                })

            },
            handleEdit(index,row){
                if(row !=null){
                    this.dept = row;
                    this.dialogTitle = '编辑';
                }else {
                    this.dialogTitle = '新增';
                }
                this.editVisible = true;
            },
            handleDelete(index,row){
                this.idx = index;
                this.delVisible = true;
            },
            handleRecovery(index,row){
                this.idx = index;
                this.updateRow();
            }


        }
    }
</script>

<style scoped>

</style>
