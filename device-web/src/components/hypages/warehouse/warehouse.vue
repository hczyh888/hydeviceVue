<template>
  <div class="wrapper">
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="datas"
        index-row
        add-mode
        :add-button="addButton"
        :rowHandle="rowHandle"
        :form-template="formTemplate"
        :form-options="formOptions"
        @row-add="handleRowAdd"
        @row-edit="handleRowEdit"
        @row-remove="handleRowRemove"
        @dialog-cancel="handleDialogCancel"
      >
      </d2-crud>
  </div>
</template>

<script>
import _clonedeep from "lodash.clonedeep"
export default {
  components:{},
  props:{},
  data(){
    return {
        columns:[
              {
                  title:'仓库名称',
                  key:'name'
              },
              {
                  title:'仓库位置',
                  key:'place'
              },
              {
                  title:'备注',
                  key:'desc'
              }
        ],
        datas:[
            {
                name:'总仓',
                place:'一楼',
                desc:'一楼存放大型设备',
                showEditButton:true,
                showRemoveButton:true
            }
        ],
        rowHandle: {
            columnHeader: '操作',
            edit: {
            icon: 'el-icon-edit',
            text: '编辑',
            size: 'small',
            show (index, row) {
                if (row.showEditButton) {
                return true
                }
                return false
            },
            disabled (index, row) {
                if (row.forbidEdit) {
                return true
                }
                return false
            }
            },
            remove: {
                icon: 'el-icon-delete',
                size: 'small',
                fixed: 'right',
                confirm: true,
                show (index, row) {
                    if (row.showRemoveButton) {
                    return true
                    }
                    return false
                },
                disabled (index, row) {
                    if (row.forbidRemove) {
                    return true
                    }
                    return false
                }
            }
      
      },
        addButton:{
            icon: 'el-icon-plus',
            size: 'small'
        },
        formTemplate:{
            name:{title:'仓库名称',value:'大仓'},
            place:{title:'仓库位置',value:''},
            desc:{title:'备注',value:''},
            forbidEdit: {   
                title: '禁用按钮',
                value: false,
                component: {
                    show: false
                }
            },
            showEditButton: {
                title: '显示按钮',
                value: true,
                component: {
                    show: false
                }
            }
        },
        formOptions:{
            labelWidth: '80px',
            labelPosition: 'left',
            saveLoading: false
        }
    }
  },
  watch:{},
  computed:{},
  methods:{
    handleRowAdd (row, done) {
      this.formOptions.saveLoading = true
      setTimeout(() => {
          let curRowData = _clonedeep(row);
          delete curRowData.forbidEdit
          delete curRowData.showEditButton
          this.$axios({
              method:"post",
              url:this.HOST+"/warehouse/saveWarehouse",
              data:curRowData
          }).then((res)=>{
              this.$message.success('保存成功');
          });
        done()
        this.formOptions.saveLoading = false
      }, 300);
    },
    handleRowEdit ({index, row}, done) {
      this.formOptions.saveLoading = true
      setTimeout(() => {
          delete row.forbidEdit;
          delete row.showEditButton;
          this.$axios({
              method:"post",
              url:this.HOST+"/warehouse/saveWarehouse",
              data:row
          }).then((res)=>{
              this.$message.success('编辑成功');
              });
        done()
        this.formOptions.saveLoading = false
      }, 300);
    },
    handleRowRemove ({index, row}, done) {
      setTimeout(() => {
        this.$axios({
            method:"post",
            url:this.HOST+"/warehouse/delWarehouse",
            data:{"id":row.id}
        }).then((res)=>{
            if(res.data.code==0){
                this.$message.success('删除成功')
            }
        })
        done()
      }, 300)
    },
    handleDialogCancel(done){
        this.$message({
            message: '取消保存',
            type: 'warning'
        });
        done()
    },
    getDatas(){
        this.$axios({
            methods:'get',
            url:this.HOST+"/warehouse/getWarehouseList",
            paras:{}
        }).then((res)=>{
            this.datas = res.data.data;
            this.datas.forEach(item => {
                item.showEditButton =true;
                item.showRemoveButton = true;
                
            });
        })
    }
  },
  created(){
      this.getDatas();
  },
  mounted(){}
}
</script>
<style scoped>
.wrapper{}
</style>