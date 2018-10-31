<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item><i class="fa fa-bars"></i> 商品管理</el-breadcrumb-item>
				<el-breadcrumb-item>订单管理</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="handle-box">
			<!--<Blockquote>
				<el-button type="primary" icon="el-icon-circle-plus-outline" @click="addAttribute"></el-button>
			</Blockquote>-->
		</div>
		<div class="container">
			<el-table :data="tableData" Checkbox border style="width: 100%" fit ref="multipleTable">
				<el-table-column prop="orderSN" label="订单编号" width="150">
				</el-table-column>
				<el-table-column prop="userId" label="用户ID" width="70">
				</el-table-column>
				<el-table-column label="订单状态" width="80">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.order_status==1" size="small"> 待付款 </el-tag>
						<el-tag v-else-if="scope.row.order_status==2" size="small"> 待发货 </el-tag>
						<el-tag v-else-if="scope.row.order_status==3" size="small"> 已发货 </el-tag>
						<el-tag v-else-if="scope.row.order_status==4" size="small"> 已完成 </el-tag>
						<el-tag v-else-if="scope.row.order_status==5" size="small"> 申请退款 </el-tag>
						<el-tag v-else-if="scope.row.order_status==6" size="small"> 退款成功 </el-tag>
						<el-tag v-else-if="scope.row.order_status==7" size="small"> 已取消 </el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="totalPrice" label="订单金额" width="80">
				</el-table-column>
				<el-table-column prop="payFee" label="支付金额" width="80">
				</el-table-column>
				<el-table-column prop="createTime" label="下单时间" width="100">
				</el-table-column>
				<el-table-column label="收货地址" width="200">
					<template slot-scope="scope">
					{{scope.row.address.provinceName}} {{scope.row.address.cityName}} {{scope.row.address.countyName}} {{scope.row.address.detailInfo}}
					{{scope.row.address.userName}} {{scope.row.address.telNumber}}
					</template>
				</el-table-column>
				<el-table-column prop="remark" label="备注" >
				</el-table-column>
				<el-table-column label="操作" width="220">
					<template slot-scope="scope">
						<a href="javascript:;" @click="fahuo(scope.$index, scope.row)"> <i class="el-icon-edit-outline"></i>发货 </a>
						<a href="javascript:;" @click="tuikuan(scope.$index, scope.row)"> <i class="fa fa-plus"></i>退款 </a>
					</template>
				</el-table-column>
			</el-table>
			<el-dialog title="规格编辑" :visible.sync="dialogFieldVisible" width="500px">
				<el-form :model="attribute" label-width="80px" ref="form">
					<el-form-item label="规格名" prop="name" :rules="[
                                      { required: true, message: '请输入规格名', trigger: 'blur' }
                                      ]">
						<el-input v-model="attribute.name" placeholder="颜色"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFieldVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveForm('form')">确 定</el-button>
				</div>
			</el-dialog>
			<el-dialog title="添加规格值" :visible.sync="dialogField2Visible" width="500px">
				<el-form :model="attributeValue" label-width="80px" ref="form2">
					<el-form-item label="规格值" prop="value" :rules="[
                                      { required: true, message: '规格值', trigger: 'blur' }
                                      ]">
						<el-input v-model="attributeValue.value" placeholder="黄色"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogField2Visible = false">取 消</el-button>
					<el-button type="primary" @click="saveForm2('form2')">确 定</el-button>
				</div>
			</el-dialog>
		</div>
	</div>
</template>

<script>
	export default {
		data: function() {
			return {
				tableData: [],
				attribute: {},
				attributeValue: {},
				dialogFieldVisible: false,
				dialogField2Visible: false,
				orderPage: {
					pageNumber: 1,
					pageSize: 10
				}
			}
		},
		methods: {
			getData() {
				this.$axios.get(this.HOST + '/product/getOrderList', {
						"pageNum": this.orderPage.pageNumber,
						"pageSize": this.orderPage.pageSize
				}
			).then((res) => {
				this.tableData =[]
				for(let o of res.data.data.list){
					o.address=JSON.parse(o.address)
					o.youhuis=JSON.parse(o.youhuis)
					this.tableData.push(o)
				}
				this.orderPage = res.data.data;
			})
		},
		fahuo(){},
		tuikuan(){}

	},
	created: function() {
		this.getData();
	},
	}
</script>

<style scoped>
	.icon {
		width: 50px;
		height: 50px;
	}
	
	.el-tag {
		margin: 2px 5px 2px 0;
	}
</style>