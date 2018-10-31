<template>
    <div>
        <div class="query-box">
            <el-form ref="queryform" :inline="true" size="mini" label-width="100px" >
                <el-form-item label="分类名称:">
                    <el-input></el-input>
                </el-form-item>

            </el-form>
        </div>
        <el-tabs v-model="activeName">
            <el-tab-pane label="待收货" name="receive">
                <d2-crud
                    :columns="columns"
                    :data="data"
                    :options="options"
                    title="入库单列表"
                    index-row
                    :rowHandle="rowHandle"
                    @receive-emit="receiveEmit"
                >
                </d2-crud>
                <!--分页-->
                <el-pagination
                    @current-change="handleCurrentChange"
                    background
                    layout="prev,pager,next,total"
                    :total="stockInPage.totalRow">
                </el-pagination>
            </el-tab-pane>
            <el-tab-pane label="待发货" name="send">
              
            </el-tab-pane>
        </el-tabs>
        <el-dialog title="收货" ref="receiveDialog" :visible.sync="receiveVisible" width="70%">
            <el-tag>{{"入库单号："+stockIn.billNo}}</el-tag>
            <el-table :data="detailRecord" >
                <el-table-column label="设备" >
                    <template slot-scope="scope">
                            <span>{{"("+scope.row.deviceCode+")"+scope.row.deviceName}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="入库数量" prop="quantity" >
                </el-table-column>
                <el-table-column label="已收货数量" prop="receivedNum" >
                </el-table-column>
                <el-table-column label="收货数量" prop="receiveNum" >
                    <template slot-scope="scope" >
                        <el-input type="number" size="small" v-model="scope.row.receiveNum"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="收货日期" prop="receiveDate"  >
                    <template slot-scope="scope">
                        <el-date-picker 
                        v-model="scope.row.receiveDate"
                        type="date" 
                        size="small">
                        </el-date-picker>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="receiveVisible=false">取消</el-button>
                <el-button @click="receiveCommit">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "stock-in",
        data () {
            return {
                activeName:'receive',
                receiveVisible:false,
                stockIn:{},
                detailRecord:{},
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
                        formatter: function(row,col,value,index){
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
                            text:'收货',
                            size:'small',
                            emit:'receive-emit',
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
                            text:'编辑',
                            size:'small',
                            emit:'edit-emit',
                            show (index, row) {
                                if (row.status==='未收货') {
                                return true
                                }
                                return false
                            },
                            disabled (index, row) {
                                if (row.status === '已收货') {
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
            receiveEmit({index,row}){
                this.stockIn = row;
                this.detailRecord = row.detail;
                this.receiveVisible = true;
            },
            //提交收货
            receiveCommit(){
                this.$axios({
                    method:'post',
                    url: this.HOST+'/warehouse/saveStockIn',
                    data:{"action":"receive","stockInDetail":JSON.stringify(this.detailRecord)}
                }).then((res)=>{
                    this.$message.success(res.data.msg);
                    this.getStockInList();
                    this.receiveVisible = false;
                })
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
