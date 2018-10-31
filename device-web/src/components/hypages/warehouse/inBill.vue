<template>
    <div>
        <el-form :inline="true" :model="stockIn" size="small" >
            <el-form-item label="入库单号:">
                <el-input v-model="stockIn.billNo"></el-input>
            </el-form-item>
            <el-form-item label="入库日期">
                <el-date-picker v-model="stockIn.billDate" type="date" format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item label="仓位">
                <el-select v-model="stockIn.warehouseId">
                    <el-option
                    v-for="item in warehouses"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    > 
                    <span style="float:let">{{ item.name }}</span>
                    <span style="float:right">{{ item.place }}</span>
                    </el-option>

                </el-select>
            </el-form-item>
            <el-form-item label="合计数量：">
                <span>{{stockIn.sumQuantity}}</span>
            </el-form-item>
            <el-form-item label="合计金额：">
                <span>{{stockIn.sumMoney}}</span>
            </el-form-item>
        </el-form>
        <d2-crud
        ref="d2Crud"
        indexRow
        :header-add="1"
        :columns="columns"
        :data="data"
        :options="options"
        :rowHandle="rowHandle"
        @header-add="headerAdd"
        @custom-emit-1="handleCustomEvent"
        >
        <el-button slot="headerButton" size="mini" type="primary" @click="handleSaveStockIn" >提交</el-button>
        <el-button slot="headerButton" size="mini" @click="goBack">返回</el-button>
        
        </d2-crud>
    </div>
</template>

<script>
import _round from 'lodash.round'
import _difference from 'lodash.difference'
import _concat from 'lodash.concat'
import bus from '../../common/bus';

    export default {
        name: "in-bill",
        data(){
            return{
                columns: [
                    {
                        title: '设备编号',
                        key: 'id',
                        width: 200,
                        component: {
                            render:this.renderSelectComponent
                        }
                    },
                    {
                        title:'设备名称',
                        key:'name'
                    },
                    {
                        title: '规格',
                        key: 'model',
                        width:100
                    },
                    {
                        title: '单位',
                        key: 'unit',
                        width:80
                    },
                    {
                        title: '数量',
                        key: 'quantity',
                        width:150,
                        component: {
                            render:this.renderAmountComponent
                        }
                    },
                    {
                        title: '单价',
                        key: 'price',
                        width: 150,
                        component: {
                            render:this.renderPriceComponent
                        }
                    },
                    {
                        title: '金额',
                        key: 'amount',
                        width:100
                    },
                    ],
                data: [],
                options: {
                        showSummary: true,
                        summaryMethod: this.summaryMethod
                    },
                rowHandle: {
                    fixed: 'right',
                    width: 100,
                    custom: [
                        {
                        //text: '自定义按钮',
                        icon: 'el-icon-remove',
                        size: 'mini',
                        emit: 'custom-emit-1'
                        }
                    ]
                    },
                warehouses:'',
                stockIn:{
                    billDate: this.$myjs.formatDate(new Date(),'-'),
                    sumQuantity: 0,
                    sumMoney:0
                },
                deviceOptions: [],
                loading: false,
                list: [],
                action:"add"
                }
        },
        mounted(){
            if(this.$route.query && this.$route.query.billNo){
                this.getInBill(this.$route.query.billNo);
                this.action="edit";
            }else{
                this.action="add";
                this.getBillNo();
            }
            this.getWarehouses();
            this.getDevices();
        },
        methods: {
            getInBill(billNo){
                this.$axios({
                    method:"get",
                    url:this.HOST+"/warehouse/getStockIn",
                    params:{"billNo":billNo}
                }).then((res) => {
                    this.stockIn = res.data.data;
                    const tempData=[];
                    this.stockIn.detail.forEach((item)=>{
                        tempData.push({"id":item.deviceId,"price":item.price,"quantity":item.quantity,
                                        "amount":item.amount,"name":item.deviceName,"model":item.deviceModel,"unit":item.deviceUnit});
                    });
                    this.data = tempData;
                })
            },
             getBillNo(){
                this.$axios({
                    method:"get",
                    url:this.HOST +"/warehouse/getBillNo",
                    params:{"billType":"RKDH"}
                }).then((res) => {
                    if(res.data.code == 0){
                        this.stockIn.billNo = res.data.data.billNo;
                    }else{
                        this.$message.error(res.data.msg);
                        this.delVisible = true;
                    }

                })
            },
            getWarehouses(){
                this.$axios({
                    method:"get",
                    url:this.HOST+'/warehouse/getWarehouseList',
                    params:{}
                }).then((res)=>{
                    this.warehouses = res.data.data;                    
                })
            },
            getDevices(){
                this.$axios({
                     method:"get",
                     url:this.HOST+'/device/getDevices',
                     params:{}   
                }).then((res) =>{
                    this.list = res.data.data;
                    this.deviceOptions = this.list;
                })
            },
            summaryMethod(param) {
                    //console.log(param);
                    const {columns, data} = param
                    const sums = []
                    columns.forEach((column, index) => {
                        if (index === 0) {
                            sums[index] = '小计'
                            return
                        }
                        if((index <5) || (index ===6)){
                            return
                        }
                        if(data.length===0){
                            this.stockIn.sumQuantity=0
                            this.stockIn.sumMoney=0
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
                            sums[index] = _round(sums[index],2)
                            //console.log(this.stockIn)
                            if(index ===5){this.stockIn.sumQuantity = sums[index]}
                            if(index ===7){this.stockIn.sumMoney = sums[index]}
                        } else {
                            sums[index] = '--'
                        }
                    })

                    return sums
                },
            handleSaveStockIn(){
                this.$axios({
                    method:"post",
                    url:this.HOST+'/warehouse/saveStockIn',
                    data:{"action":this.action,"stockIn":JSON.stringify(this.stockIn),"stockInDetail":JSON.stringify(this.$refs.d2Crud.d2Data)}
                }).then((res) => {
                    this.$confirm('单据已保存, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'info'
                        }).then(() => {
                            this.$message.success(res.data.msg);
                            this.getBillNo();
                            this.stockIn = {billNo:this.stockIn.billNo,billDate: this.$myjs.formatDate(new Date(),'-')};
                            this.$refs.d2Crud.d2Data = [];
                        }).catch(() => {
                           this.goBack();     
                        });
                })
            },
             filterMethod(query) {
                if (query !== '') {
                this.loading = true;
                setTimeout(() => {
                    this.loading = false;
                    this.deviceOptions = this.list.filter(item => {
                    return ((item.name.toLowerCase().indexOf(query.toLowerCase()) > -1)||
                            (item.code.toLowerCase().indexOf(query.toLowerCase()) > -1)||
                            (item.id===query));
                    });
                   let diff= _difference(this.list,this.deviceOptions)
                    this.deviceOptions = _concat(this.deviceOptions,diff)     

                }, 200);
                } else {
                this.deviceOptions = this.list;
                }
            },
            renderSelectComponent(h,row){
                return h('div',{
                            style: {height:'40px'}
                            },
                        [
                            h("el-select",
                            {
                               props: {
                                        placeholder:'输入关键词',
                                        value:row.id,
                                        clearable:false,
                                        size:'small',
                                        filterable:true,
                                        filterMethod:this.filterMethod,
                                    },
                                on: {
                                    input: newValue => {
                                       // this.$emit('input',$event.target.value)
                                        },
                                    change:newValue =>{
                                        const newValues = this.deviceOptions.filter(item=>{
                                            return item.id === newValue;
                                        })
                                        row.id = newValue
                                        row.code = newValues[0].code;
                                        row.name = newValues[0].name;
                                        row.model = newValues[0].model;
                                        row.unit = newValues[0].unit;
                                    }
                                    }
                            },[
                                this.deviceOptions.map(item => {
                                    return h("el-option", {
                                    props: {
                                            value: item.id,
                                            label: item.code
                                            }
                                    },[
                                        [
                                                h('span',{
                                                    style:{
                                                        float: 'left'
                                                    }},item.code
                                                )
                                        ],
                                        [
                                                h('span',{
                                                            style:{
                                                                float: 'right'
                                                            }},item.name
                                                        ) 
                                        ]                                   
                                    ]
                                    );
                                })
                            ]
                        )]
                        )
            },
            renderAmountComponent(h,row){
                return h('el-input',{
                    props:{
                        value:row.amount,
                        size:'small',
                        type:'number'
                    },
                    on:{
                        input:newValue => {
                            row.amount = newValue
                            row.money = _round(row.amount*row.price,2)
                        },
                        change:newValue =>{
                        }
                    }
                })

            },
            renderPriceComponent(h,row){
                return h('el-input',{
                    props:{
                        value:row.price,
                        size:'small',
                        type:'number'
                    },
                    on:{
                        input:newValue => {
                            row.price = newValue
                            row.money = _round(row.price*row.amount,2)
                        },
                        change:newValue =>{
                        }
                    }
                })

            },
            handleCustomEvent ({index, row}) {
                this.$refs.d2Crud.d2Data.splice(index,1);
            },
            headerAdd(){
                this.$refs.d2Crud.d2Data.push({
                    id:'',
                    code:'',
                    name: '',
                    model: '',
                    unit:'个',
                    quantity:1,
                    price:1,
                    amount:1
                });
            },
            goBack(){
                bus.$emit('closeCurr',this.$route);
               //this.$router.go(-1);
            }
        }
    }
</script>

<style scoped>
</style>
