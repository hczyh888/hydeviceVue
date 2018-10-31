<template>
    <div class="">
        <Blockquote>
            <span slot="txt">用户列表</span>
            <el-button slot="btns" icon="el-icon-circle-plus-outline" size="small" @click="handleEdit">增加</el-button>
        </Blockquote>
            <el-row :gutter="20" class="query-box">
                <el-col :span="4">
                    <el-input placeholder="请输入昵称" clearable v-model="nickname" size="small">
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-input placeholder="请输入手机号" clearable v-model="telephone" size="small">
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-search" size="small" @click="filterCustom">查找</el-button>

                </el-col>
            </el-row>

        <el-table
            :data="tableData"
            height="400"
            style="width: 100%">
            <el-table-column prop="id" v-if="isShow"></el-table-column>
            <el-table-column type="index" :index="handleIndex" label="序号" sortable width="100">
            </el-table-column>
            <el-table-column prop="userAccount" label="账号" width="100">
            </el-table-column>
            <el-table-column prop="realName" label="姓名"></el-table-column>
            <el-table-column prop="avatar" label="头像" width="100">
                <template slot-scope="scope">
                    <img class="avatar"  :src="scope.row.avatar_tmp">
                </template>
            </el-table-column>
            <el-table-column prop="mobile" label="手机号码"></el-table-column>
            <el-table-column prop="deptName" label="部门"></el-table-column>
            <el-table-column prop="roleName" label="角色" width="120"></el-table-column>
            <el-table-column prop="registDatetime" label="注册时间" width="120"></el-table-column>
            <el-table-column prop="status" label="状态" width="150"
                             :filters="[{text: '正常', value: 0}, {text: '禁用', value: 1}]"
                             :filter-method="filterHandler">
                <template slot-scope="scope">
                    {{scope.row.status==0?"正常":"禁用"}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.status==0" type="text" size="small"
                            @click="handleEdit(scope.$index,scope.row)">
                        编辑
                    </el-button>
                    <el-button v-if="scope.row.status==0" type="text" size="small"
                               @click.native.prevent="deleteUser(scope.$index,scope.row)">
                        禁用
                    </el-button>
                    <el-button v-if="scope.row.status==1" type="text" size="small"
                               @click.native.prevent="recoveryUser(scope.$index,scope.row)">
                        激活
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页-->
        <el-pagination
            @current-change="handleCurrentChange"
            background
            layout="prev,pager,next,total"
            :total="userPage.totalRow">
        </el-pagination>

        <!-- 编辑弹出框 -->
        <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="50%">
            <el-form :inline="true" ref="form" :model="user" label-width="80px">
                <el-input type="hidden" v-model="user.id" v-show="false"></el-input>
                <el-input type="hidden" v-model="user.status" v-show="false"></el-input>
                <el-input type="hidden" v-model="user.deptPath" v-show="false"></el-input>
                <el-input type="hidden" v-model="user.avatar" v-show="false"></el-input>
                <el-form-item label="用户账号">
                    <el-input v-model="user.userAccount"></el-input>
                </el-form-item>
                <el-form-item label="密码" v-show="dialogTitle == '新增'">
                    <el-input v-model="user.password"  type="password"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="user.realName" ></el-input>
                </el-form-item>

                <el-form-item label="手机号码">
                    <el-input v-model="user.mobile"></el-input>
                </el-form-item>
                <el-form-item label="部门">
                    <el-cascader v-model="user.options"
                        placeholder="请选择部门"
                        :options="deptOptions"
                        filterable="true"
                        change-on-select="true"
                    ></el-cascader>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select v-model="user.roleId" placeholder="请选择">
                        <el-option
                            v-for="item in roles"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                            <span style="float: left">{{ item.name }}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.role }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="图片"  prop="user.avatar" :rules="[
                                      { required: true, message: '请选择图片', trigger: 'blur' }
                                      ]">
                    <div class="el-flex">
                        <img class="upload-preview-img" v-if="user.avatar" :src="user.avatar_tmp">
                        <pictureview type="upload" @selectChange="selectChange"></pictureview>
                    </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>

</template>

<script>
    import ElButton from "element-ui/packages/button/src/button";
    import pictureview from '@/components/my-components/pictureview/index';

    export default {
        data() {
            return {
                deptOptions: [],
                roles:[],
                isShow:false,
                userPage:{
                    pageNumber: 1,
                    pageSize: 10
                },
                nickname:"",
                telephone:"",
                tableData:[],
                dialogTitle:"",
                user: {},
                editVisible: false,
                imageUrl:"",
                uploadPath:""
            }
        },
        methods: {
            init:function () {
                this.getUser();
                this.getDeptCmd();
                this.getRoleCmd();
            },
            getUser(){
                this.$axios.get(this.HOST+'/user/getUserList',
                    {
                        params:{"pageNum": this.userPage.pageNumber,"pageSize":this.userPage.pageSize}
                    }
                ).then((res) => {
                    this.tableData=res.data.data.list
                this.userPage=res.data.data
            })
            },
            getDeptCmd(){
                this.$axios.get(this.HOST+'/system/getDeptCmd'
                ).then((res) => {
                    this.deptOptions = res.data.data;
                })
            },
            getRoleCmd(){
                this.$axios.get(this.HOST+'/system/getRole',
                    {
                        params:{"pageNum": 1,"pageSize":10}
                    }
                ).then((res) => {
                    this.roles = res.data.data;
                })
            },
            handleEdit(index, row) {
                if (row != null){
                    this.user = row;
                    this.user.options = row.deptPath.split('/');
                    this.imageUrl = row.avatar;
                    this.dialogTitle = '编辑';
                }else {
                    this.user={};
                    this.dialogTitle = '新增';
                }
                this.editVisible = true;
            },
            selectChange(attachments){
                if(attachments!=null){
                    this.$set(this.user,"avatar",attachments[0].url)
                    this.$set(this.user,"avatar_tmp",attachments[0].thumbnail_temp)
                }
            },
            saveEdit() {
                let opts = this.user.options;
                this.user.deptPath = opts.join('/');
                let deptid = opts.pop();  //取数组opts的最后一个
                this.user.deptId = deptid;
                delete this.user.options;
                delete this.user.avatar_tmp;
                this.user.status = 0;
                this.$axios({
                    method: "post",
                    url: this.HOST +"/user/saveUser",
                    data: this.user
                }).then((res) => {
                    if(res.data.code == 0) {
                        this.getUser();
                        this.editVisible = false;
                        this.$message.success('保存成功');
                    }else{
                        this.$message.error(res.data.msg);
                    }
                })

            },
            filterHandler:function (value, row, column) {
                const property = column['property'];
                return row[property] === value;
            },
            filterCustom(){
                if(this.nickname==""&&this.telephone==""){
                    this.tableData=this.userPage.list;
                }else{
                    this.tableData=[];
                    for(let o of this.userPage.list){
                        if(this.nickname==""&&this.telephone!=""&&this.telephone==o.telephone){
                            this.tableData.push(o)
                        }else if(this.nickname!=""&&this.telephone==""&&this.nickname==o.nickname){
                            this.tableData.push(o)
                        }else if(this.telephone==o.telephone&&this.nickname==o.nickname){
                            this.tableData.push(o)
                        }
                    }
                }
            },
            deleteUser(index,row){
                row.status=1
            },
            recoveryUser(){
                row.status=0
            },
            handleIndex(index){
                return index+1;
            },
            handleCurrentChange(pageNum){
               this.$set(this.userPage,"pageNumber",pageNum) ;
               this.getUser();
            }
        },
        created:function(){
            this.uploadPath = this.UPLAODPATH;
            this.init();
        },
        computed: {

        },
        components:{
            'pictureview':pictureview
        }
    }

</script>

<style >
    .avatar {
        width: 40px;
        height: 40px;
        display: block;
    }
    .query-box{
        display:flex;
        height: 40px;
        background-color: #f4f4f4;
    }
</style>

