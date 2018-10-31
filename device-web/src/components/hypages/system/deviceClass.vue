<template>
    <div>
        <Blockquote color="666666;">
           <span slot="txt">设备分类</span>
            <el-button slot="btns" size="small" @click="handleEdit" icon="el-icon-circle-plus-outline" >新增</el-button>
        </Blockquote>
        <div class="query-box">
            <el-form ref="queryform" :inline="true" size="mini" label-width="100px" >
                <el-form-item label="分类名称:">
                    <el-input></el-input>
                </el-form-item>

            </el-form>
        </div>
        <el-table :data="tableData">
            <el-table-column prop="id" v-if="false" ></el-table-column>
            <el-table-column type="index" :index="handleIndex" label="序号"></el-table-column>
            <el-table-column prop="classBm" label="分类编码" width="120">

            </el-table-column>
            <el-table-column prop="className" label="分类名称">
                <template slot-scope="scope">
                    <span class="first_level" v-if="scope.row.level==1">&nbsp;&nbsp;├─</span>
                    <span class="first_level" v-else-if="scope.row.level==2">&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                    <span class="first_level" v-else-if="scope.row.level==3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─</span>
                    {{ scope.row.className }}
                </template>
            </el-table-column>
            <el-table-column prop="classSimple" label="简码" width="120">

            </el-table-column>
            <el-table-column prop="sort_num" label="排序" width="50">

            </el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <a href="javascript:;" @click="handleEdit(scope.$index,scope.row)"><i class="el-icon-edit-outline"></i>编辑 </a>
                    <a href="javascript:;" @click="handleDelete(scope.$index, scope.row)"> <i class="el-icon-delete"></i>删除 </a>
                    <a href="javascript:;" @click="addChildClass(scope.row.id,scope.row.level+1,scope.row.className)"> <i class="fa fa-plus"></i>添加子类</a>
                </template>
            </el-table-column>
        </el-table>
        <!--编辑窗口-->
        <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="40%">
            <el-form ref="form"  :model="deviceClass" label-width="20%">
                <el-input type="hidden" v-show="false" v-model="deviceClass.id"></el-input>
                <el-form-item v-show="deviceClass.pName != null" label="上级分类:">
                    <span>{{ deviceClass.pName }}</span>
                </el-form-item>
                <el-form-item label="分类编码" >
                    <el-input v-model="deviceClass.classBm"></el-input>
                </el-form-item>
                <el-form-item label="分类名称">
                    <el-input v-model="deviceClass.className"></el-input>
                </el-form-item>
                <el-form-item label="简码">
                    <el-input v-model="deviceClass.classSimple"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-input v-model="deviceClass.level"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input type="number" v-model="deviceClass.sort_num"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit('form')">确 定</el-button>
            </span>
        </el-dialog>
        <!--删除提示框-->
        <el-dialog title="提示" :visible.sync="delVisible" width="300" center>
            <div class="del-dialog-cnt">删除不可回复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button  type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import ElFormItem from "element-ui/packages/form/src/form-item";
    import ElInput from "element-ui/packages/input/src/input";
    import ElButton from "element-ui/packages/button/src/button";

    export default {
        components: {
            ElButton,
            ElInput,
            ElFormItem},
        name: "device-class",
        data(){
            return{
                tableData:[],
                deviceClass:{},
                dialogTitle: '增加',
                pName:'',
                editVisible: false,
                delVisible: false,
                idx:''
            }

        },
        created(){
          this.getData();
        },
        methods:{
            getData() {
                this.$axios.get(this.HOST+'/system/getDeviceClasses'
               ).then((res) => {
                    this.tableData = res.data.data;
                })
            },
            handleIndex(index){
              return index+1;
            },
            handleEdit(index,row){
                if(row !=null){
                    this.deviceClass = row;
                    this.dialogTitle = '编辑';
                }else{
                    this.deviceClass = {pid:0,level:0};
                    this.dialogTitle = '新增';
                }
                this.editVisible = true;
            },
            handleDelete(index,row){
                this.idx = index;
                this.delVisible = true;
            },
            addChildClass(i_pid,i_level,s_pName){
                this.dialogTitle = '增加子类';
                this.deviceClass ={pid:i_pid,level:i_level,sort_num:1,pName:s_pName};
                //this.pName =s_pName;
                this.editVisible = true;
            },
            saveEdit(formName){
                this.$refs[formName].validate((valid) => {
                    if(valid){
                        delete this.deviceClass.pName;
                        this.$axios({
                            method: "post",
                            url: this.HOST +"/system/saveDeviceClass",
                            data: this.deviceClass
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
            deleteRow(){
                this.$axios({
                    methos:"get",
                    url:this.HOST +"/system/delDeviceClass",
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

            }

        }
    }

</script>

<style scoped>
.query-box{
    display:flex;
    height: 40px;
    background-color: #f4f4f4;
}
</style>
