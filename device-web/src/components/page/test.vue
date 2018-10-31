<template>
  <div class="wrapper">
      <el-table>
          <el-table-column :render-header="renderHeader">

          </el-table-column>
      </el-table>
  </div>
</template>

<script>
export default {
  components:{},
  props:{},
  data(){
    return {
    }
  },
  watch:{},
  computed:{},
  methods:{
      renderHeader(h){
          return (
                    <div>
                        <span>表头</span>
                        <el-button>按钮</el-button>
                    </div>
              )
      }
  },
  created(){},
  mounted(){}
}
</script>
<style scoped>
.wrapper{}
</style>