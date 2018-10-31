<template>
    <div class="table">
        <Blockquote>
            <span slot="txt">模块列表</span>
            <el-button slot="btns" size="small" icon="el-icon-circle-plus-outline" @click="handleEdit">新增</el-button>
        </Blockquote>

        <el-table :data="tableData"  size="small" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
            <el-table-column prop="sort_num" label="排序" sortable width="150" >
                <template slot-scope="scope">
                    <el-input @change="handleSort(scope.$index)" class="input-sort" v-model="scope.row.sort_num"  placeholder=""></el-input>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="模块ID" sortable width="150">
            </el-table-column>
            <el-table-column prop="name" label="模块名称" width="180">
            </el-table-column>
            <el-table-column prop="desc" label="描述" width="180">
            </el-table-column>
            <el-table-column label="操作" >
                <template slot-scope="scope">
                    <a href="javascript:;" @click="handleEdit(scope.$index, scope.row)"><i class="el-icon-edit-outline"></i>编辑</a>
                    <a href="javascript:;" @click="handleDelete(scope.$index, scope.row)"><i class="el-icon-delete"></i>删除</a>
                    <router-link :to="{ path: '/lanmu?moduleId='+scope.row.id}"> <i class="fa fa-plus"></i>所属菜单</router-link>
                </template>
            </el-table-column>
        </el-table>

        <!-- 编辑弹出框 -->
        <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="module" label-width="80px">
                <el-input type="hidden" v-model="module.id"></el-input>
                <el-form-item label="模块名称">
                    <el-input v-model="module.name"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input type="number" v-model="module.sort_num" ></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="module.desc"></el-input>
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
    import ElInput from "element-ui/packages/input/src/input";

    export default {
        components: {ElInput},
        name: 'module',
        data() {
            return {
                url: './static/module.json',
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                editVisible: false,
                delVisible: false,
                module: {},
                idx: -1,
                dialogTitle: '增加'
            }
        },
        created() {
            this.getData();
        },

        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
                if (process.env.NODE_ENV === 'development') {
                    this.url = this.HOST+ '/system/getModules';
                };
                this.$axios.get(this.url, {
                    page: this.cur_page
                }).then((res) => {
                    this.tableData = res.data.data;
                })
            },
            search() {
                this.is_search = true;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
               if (row != null){
                   this.module = row;
                   this.dialogTitle = '编辑';
               }else {
                   this.dialogTitle = '新增';
               }
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },
            delAll() {
                const length = this.multipleSelection.length;
                let str = '';
                this.del_list = this.del_list.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].name + ' ';
                }
                this.$message.error('删除了' + str);
                this.multipleSelection = [];
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleSort(index){
                this.$axios({
                    method:"post",
                    url: this.HOST+"/saveLanmu",
                    data: this.tableData[index]
                }).then((res)=>{
                    if(res.data.code==0) {
                        this.getData();
                        this.$message.success('保存成功');
                    }else{
                        this.$message.error(res.data.msg);
                    }
                })
            },
            // 保存编辑
            saveEdit(formName) {
                this.$axios({
                    method: "post",
                    url: this.HOST +"/system/saveModule",
                    data: this.module
                }).then((res) => {
                    if(res.data.code == 0) {
                        this.getData();
                        this.editVisible = false;
                        this.$message.success('保存成功');
                    }else{
                        this.$message.error(res.data.msg);
                    }
                })
            },
            // 确定删除
            deleteRow(){
                this.$axios({
                    method: "get",
                    url: this.HOST +"/system/delModule",
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
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
</style>
