<template>
    <div>
        <Blockquote color="666666;">
            <span slot="txt">设备目录</span>
            <el-button slot="btns" size="small" @click="handleEdit" icon="el-icon-circle-plus-outline" >新增</el-button>
        </Blockquote>
        <el-row :gutter="20" class="query-box">
            <el-col :span="2"><span style="font-size: small">设备名称:</span></el-col>
            <el-col :span="4">
                <el-input size="small" clearable v-model="deviceName"></el-input>
            </el-col>

            <el-col :span="4">
                <el-button type="primary" icon="el-icon-search" size="small" @click="filterCustom">查找</el-button>
            </el-col>
        </el-row>
        <el-table :data="tableData">
            <el-table-column prop="id" v-if="false" ></el-table-column>
            <el-table-column type="index" :index="handleIndex" label="序号"></el-table-column>
            <el-table-column prop="code" label="编码" width="120">

            </el-table-column>
            <el-table-column prop="name" label="设备名称">

            </el-table-column>
            <el-table-column prop="simple" label="简码" width="120">

            </el-table-column>
            <el-table-column prop="className" label="设备分类" width="120">

            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <a href="javascript:;" @click="handleEdit(scope.$index,scope.row)"><i class="el-icon-edit-outline"></i>编辑 </a>
                    <a href="javascript:;" @click="handleDelete(scope.$index, scope.row)"> <i class="el-icon-delete"></i>删除 </a>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="handleCurrentChange"
            background
            layout="prev,pager,next"
            :total="devicePage.totalRow">
        </el-pagination>
        <!--编辑窗口-->
        <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="40%">
            <el-form ref="form"  :model="device" label-width="20%">
                <el-input type="hidden" v-show="false" v-model="device.id"></el-input>
                <el-form-item label="编码" >
                    <el-input v-model="device.code"></el-input>
                </el-form-item>
                <el-form-item label="名称">
                    <el-input v-model="device.name"></el-input>
                </el-form-item>
                <el-form-item label="简码">
                    <el-input v-model="device.simple"></el-input>
                </el-form-item>
                <el-form-item label="分类名">
                    <el-cascader v-model="device.options"
                                 placeholder="请选择分类"
                                 :options="deviceClassOptions"
                                 filterable="true"
                                 change-on-select="true">

                    </el-cascader>
                </el-form-item>
                <el-form-item label="单位">
                    <el-select v-model="device.unit" placeholder="请选择">
                        <el-option
                            v-for="item in units"
                            :key="item.id"
                            :label="item.name"
                            :value="item.name">
                            <span style="float: left" >{{ item.id }}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.name }}</span>
                        </el-option>
                    </el-select>
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
    export default {
        name: "device",
        data(){
            return{
                tableData:[],
                device:{},
                deviceName:'',
                deviceClassOptions:[],
                dialogTitle: '增加',
                pName:'',
                devicePage:{
                    pageNumber:1,
                    pageSize:10
                },
                units:[
                    {id:1,name:'个'},
                    {id:2,name:'台'}
                ],
                editVisible: false,
                delVisible: false,
                idx:''
            }

        },
        created(){
            this.getData();
            this.getDeviceClassCmd();
        },
        methods:{
            getData() {
                this.$axios.get(this.HOST+'/device/getDevice',{
                    params:{"pageNum":this.devicePage.pageNumber,"pageSize":this.devicePage.pageSize}
                    }
                ).then((res) => {
                    this.tableData = res.data.data.list;
                    this.devicePage=res.data.data
                })
            },
            getDeviceClassCmd(){
                this.$axios.get(this.HOST+'/system/getDeviceClassCmd'
                ).then((res) => {
                    this.deviceClassOptions = res.data.data;
                })
            },
            handleIndex(index){
                return index+1;
            },
            handleCurrentChange(pageNum){
                this.$set(this.devicePage,"pageNumber",pageNum) ;
                this.getData();
            },
            handleEdit(index,row){
                if(row !=null){
                    this.device = row;
                    this.dialogTitle = '编辑';
                }else{
                    this.device={};
                    this.dialogTitle = '新增';
                }
                this.editVisible = true;
            },
            handleDelete(index,row){
                this.idx = index;
                this.delVisible = true;
            },
            saveEdit(formName){
                this.$refs[formName].validate((valid) => {
                    if(valid){
                        delete this.device.className;
                        this.$axios({
                            method: "post",
                            url: this.HOST +"/device/saveDevice",
                            data: this.device
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
            filterCustom(){
                if(this.deviceName==""){
                    this.tableData=this.devicePage.list;
                }else{
                    this.tableData=[];
                    for(let o of this.devicePage.list){
                        if(this.deviceName!=""&&this.deviceName==o.name){
                            this.tableData.push(o)
                        }
                    }
                }
            },
            deleteRow(){
                this.$axios({
                    methos:"get",
                    url:this.HOST +"/device/delDevice",
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
        height:40px;
        background-color: #f4f4f4;
    }

</style>
