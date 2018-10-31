<template>
    <div>
        <Blockquote>
            <span slot="txt">单位信息</span>
            <el-button slot="btns" icon="el-icon-circle-plus" size="small" @click="handleSave">保存</el-button>
        </Blockquote>
        <div class="container">
        <el-form ref="form" :model="company" label-width="100px">
            <el-input type="hidden" v-model="company.id" v-show="false"></el-input>
            <el-form-item label="公司Logo" >
                <div class="el-flex">
                    <img class="upload-preview-img" v-if="company.logo" :src="company.logo_tmp">
                    <pictureview type="upload" @selectChange="selectChange"></pictureview>
                </div>
            </el-form-item>
            <el-form-item label="公司名称">
                <el-input v-model="company.cnName"></el-input>
            </el-form-item>
            <el-form-item label="英文名">
                <el-input v-model="company.enName"></el-input>
            </el-form-item>

            <el-form-item label="地址">
                <el-input v-model="company.addr"></el-input>
            </el-form-item>
            <el-form-item label="联系电话">
                <el-input v-model="company.tel"></el-input>
            </el-form-item>
        </el-form>
    </div>
    </div>
</template>

<script>
    import pictureview from "@/components/my-components/pictureview/index";
    export default {
        components:{'pictureview':pictureview},
        name: "company",
        data(){
            return {
                company:{}
            }
        },
        created(){
          this.getData();
        },
        methods:{
            getData(){
              this.$axios.get(this.HOST+'/system/getCompany').then((res) =>{
                  this.company = res.data.data;
              })
            },
            selectChange(attachments){
              if(attachments !=null){
                  this.$set(this.company,"logo",attachments[0].url);
                  this.$set(this.company,"logo_tmp",attachments[0].thumbnail_temp);
              }
            },
            handleSave(){
                delete this.company.logo_tmp;
               this.$axios({
                   method:"post",
                   url:this.HOST+"/system/saveCompany",
                   data:this.company
               }).then((res) =>{
                   if(res.data.code ==0){
                       this.company =res.data.data;
                       this.$message.success('保存成功');
                   }
               })
            }
        }
    }
</script>

<style scoped>

</style>
