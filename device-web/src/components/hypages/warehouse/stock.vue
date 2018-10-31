<template>
    <div>
     <el-button slot="headerButton" size="small" @click="exportExcel">导出到excel</el-button>
     <el-button slot="headerButton" size="small" @click="printStockList">打印</el-button>
    <el-upload class="hy-upload-text" :before-upload="handleUpload" size="small" action="default" >
        <el-button type="success">
            导入到excel
        </el-button>
    </el-upload>
     <section ref="stockList">
     <el-table  v-bind="table" style="width:100%">
         <el-table-column
            v-for="(item,index) in table.columns"
            :key="index"
            :prop="item.prop"
            :label="item.label">
         </el-table-column>
     </el-table>
     </section>
    </div>
</template>

<script>
export default {
    name:'stock',
    data() {
        return {
            table:{
                columns:[
                        {
                            label:'设备编号',
                            prop:'code'
                        },
                        {
                            label:'设备名称',
                            prop:'name'
                        },
                        {
                            label:'设备简码',
                            prop:'simpleName'
                        },
                        {
                            label:'设备编号',
                            prop:'code'
                        },
                        {
                            label:'库存数量',
                            prop:'quantity'
                        },
                        {
                            label:'单价',
                            prop:'price'
                        },
                        {
                            label:'单位',
                            prop:'unit'
                        }
                    ],
                    data:[]
                },
                pageStock:{
                    pageNumber:1,
                    pageSize:10
                }
        }
    },
    components: {

    },
    props:{},
    created(){
     this.getStockList();

    },
 methods:{
     getStockList(){
         this.$axios({
             method:'get',
             url:this.HOST+'/warehouse/getStockList',
             params:{"pageNum":this.pageStock.pageNumber,"pageSize":this.pageStock.pageSize}
         }).then((res) =>{
             this.pageStock = res.data.data;
             this.table.data = res.data.data.list;
         })
     },
     exportExcel(){
         this.$export.excel({
             columns:this.table.columns,
             data:this.table.data
         }).then(() => {
             this.$message('导出成功！')
         })
     },
     handleUpload(file){
         this.$import.xlsx(file)
         .then(({header,results}) =>{
             this.table.columns = header.map(e=>{
                 return{
                     label:e,
                     prop:e
                 }
             })
             this.table.data = results
         })
         return false
     },
     printStockList(){
         this.$print(this.$refs.stockList)
     }
 }
}
</script>

<style scoped>
.hy-upload-text{
    height: 50px;
    width: 50px;
}
</style>
