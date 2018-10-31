<template>
    <div>
        <div class="query-box">
            <el-form ref="queryform" :inline="true" size="mini" label-width="100px" >
                <el-form-item label="分类名称:">
                    <el-input></el-input>
                </el-form-item>

            </el-form>
        </div>
        <d2-crud
            :columns="columns"
            :data="data"
            :options="options"
            title="入库单列表"
            index-row
            :rowHandle="rowHandle"
            @edit-emit="editEmit"
        >
            <el-button slot="headerButton" icon="el-icon-plus" size="small" @click="addInBill">新增入库单</el-button>
        </d2-crud>
         <!--分页-->
        <el-pagination
            @current-change="handleCurrentChange"
            background
            layout="prev,pager,next,total"
            :total="stockInPage.totalRow">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "stock-in",
        data () {
            return {
                columns: [
                    {
                        title: '日期',
                        type: 'date',
                        key: 'billDate',
                        width: 100,
                        formatter:(row, col,value,index)=>{
                            return this.$myjs.formatDate(new Date(value),'-');
                        }
                    },
                    {
                        title: '入库单号',
                        key: 'billNo',
                    },
                    {
                        title: '数量',
                        key: 'sumQuantity',
                    },
                    {
                        title: '金额',
                        key: 'sumMoney',
                    },
                    {
                        title: '仓位',
                        key: 'warehouseName',
                    },
                    {
                        title: '状态',
                        key: 'status',
                        formatter:(row,col,value,index)=>{
                            if(value==1){return '等待收货'}
                            if(value==2){return '已收货'}
                            if(value==3){return '正在收货'}
                            if(value==4){return '收货异常'}
                        }
                    }
                ],
                data: [],
                options: {
                    height:500,
                    showSummary: true,
                    summaryMethod(param) {
                        const {columns, data} = param
                        const sums = []
                        columns.forEach((column, index) => {
                            if (index === 0) {
                                sums[index] = '小计'
                                return
                            }
                            const values = data.map(item => Number(item[column.property]))
                            if (!values.every(value => isNaN(value))) {
                                sums[index] = values.reduce((prev, curr) => {
                                    const value = Number(curr)
                                    if (!isNaN(value)) {
                                        return prev + curr
                                    } else {
                                        return prev
                                    }
                                }, 0)
                                sums[index] += ' 元'
                            } else {
                                sums[index] = '--'
                            }
                        })

                        return sums
                    }
                },
                stockInPage:{
                    pageNumber:1,
                    pageSize:10
                },
                rowHandle:{
                    custom:[
                        {
                            text:'编辑',
                            size:'small',
                            emit:'edit-emit',
                            show (index, row) {
                                if (row.status===1) {
                                return true
                                }
                                return false
                            },
                            disabled (index, row) {
                                if (row.status === 2) {
                                return true
                                }
                                return false
                            }
                        },
                        {
                            text:'查看',
                            size:'small',
                            emit:'view-emit',
                            show (index, row) {
                                if (row.status==='已收货') {
                                return true
                                }
                                return false
                            },
                            disabled (index, row) {
                                if (row.status === '未收货') {
                                return true
                                }
                                return false
                            }
                        }
                    ]
                }
                
            }
        },
        created:function(){
            this.getStockInList();
        },
        methods: {
            getStockInList(){
                this.$axios({
                    method:'get',
                    url:this.HOST+'/warehouse/getStockInList',
                    params:{'pageNum':this.stockInPage.pageNumber,'pageSize':this.stockInPage.pageSize}
                }).then((res) =>{
                    this.data = res.data.data.list;
                    this.stockInPage = res.data.data;
                })

            },
            handleRowAdd (row, done) {
                this.formOptions.saveLoading = true
                setTimeout(() => {
                    console.log(row)
                    this.$message({
                        message: '保存成功',
                        type: 'success'
                    });
                    done()
                    this.formOptions.saveLoading = false
                }, 300);
            },
            handleDialogCancel (done) {
                this.$message({
                    message: '取消保存...',
                    type: 'warning'
                });
                done()
            },
            addInBill(){
                this.$router.push('/inBill');
            },
            editEmit({index,row}){
                this.$router.push({path:'/inBill',query:{billNo:row.billNo}});
            },
            handleCurrentChange(pageNum){
               this.$set(this.stockInPage,"pageNumber",pageNum) ;
               this.getStockInList();
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
