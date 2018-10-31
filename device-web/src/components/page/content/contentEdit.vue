<template>
    <div class="">
        <el-form ref="form" :model="content" label-width="80px">
            <template v-for="(item) in fields">
                <template v-if="item.field_en=='thumbnail'">
                    <el-form-item :label="item.field_zh" :prop="item.field_en"  :rules="item.lanmu_option==2?[
                                      { required: true, message: '内容不能为空', trigger: 'blur' }
                                      ]:[]">
                        <pictureview type="upload" :selectedImg="content.thumbnail_temp" @selectChange="handleImgSuccess"></pictureview>
                    </el-form-item>
                </template>
                <template v-else-if="item.field_en=='picture'">
                    <el-form-item :label="item.field_zh" :prop="item.field_en"  :rules="item.lanmu_option==2?[
                                      { required: true, message: '内容不能为空', trigger: 'blur' }
                                      ]:[]">
                        <div class="el-flex pictureview-pictures">
                            <pictureview type="upload"  @selectChange="handlePicturesSuccess"></pictureview>
                            <div v-for="item in content.pictures" class="img-item">
                                <img  :src="item.url">
                                <i class="el-icon-delete" @click="handlePictureRemove(item.id)"></i>
                            </div>
                        </div>
                    </el-form-item>
                </template>
                <template v-else-if="item.field_en=='content'">
                    <el-form-item :label="item.field_zh" :prop="item.field_en"  :rules="item.lanmu_option==2?[
                                      { required: true, message: '内容不能为空', trigger: 'blur' }
                                      ]:[]">
                        <tinymce class="editor" v-model="content.content"  :setting="editorSetting"></tinymce>
                    </el-form-item>
                </template>
                <template v-else-if="item.field_en=='out_link'">
                    <el-form-item :label="item.field_zh" :prop="item.field_en"  :rules="item.lanmu_option==2?[
                                      { required: true, message: '内容不能为空', trigger: 'blur' },
                                       { type: 'url', message: '链接不正确',trigger: 'blur'}
                                      ]:[]">
                        <el-input :value="ObjectGetByKey(item.field_en)" @change="value => { ObjectSetByKey(item.field_en,value)}"></el-input>
                    </el-form-item>
                </template>
                <template v-else-if="item.field_type=='varchar'">
                    <el-form-item :label="item.field_zh" :prop="item.field_en"  :rules="item.lanmu_option==2?[
                                      { required: true, message: '内容不能为空', trigger: 'blur' },
                                      ]:[]">
                        <el-input :value="ObjectGetByKey(item.field_en)" @change="value => { ObjectSetByKey(item.field_en,value)}"></el-input>
                    </el-form-item>
                </template>
            </template>
            <el-form-item>
                <el-button type="primary" @click="saveForm('form')">提交</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import tinymce from '@/components/my-components/tinymce/tinymce';
    import pictureview from '@/components/my-components/pictureview/index';
    export default {
        data() {
            return {
                lanmu:{},
                content:{
                    content:'',
                    pictures:[],
                },
                //tinymce的配置信息 参考官方文档 https://www.tinymce.com/docs/configure/integration-and-setup/
                editorSetting:{
                    height:400
                },
                fields:[]
            }
        },
        methods: {
            init:function () {
                this.lanmu.id=this.$route.query.lanmuId;
                if(this.$route.query.contentId!=null)
                    this.content.id=this.$route.query.contentId;
                this.getData();
                this.getField();
            },
            getData() {
                this.$axios.get(this.HOST+'/getOneContent',
                    {
                        params:{"id": this.content.id,"lanmuId":this.lanmu.id}
                    }
                ).then((res) => {
                    this.lanmu=res.data.data.lanmu;
                    if(res.data.data.content!=null)
                        this.content = res.data.data.content;
                })
            },
            getField(){
                if(this.lanmu.id!=null){
                    this.$axios.get(
                        this.HOST+"/getFieldsByLmIDEditor",
                        {params:{lanmuId:this.lanmu.id}},
                    ).then((res) => {
                        this.fields=res.data.data
                    })
                }
            },
            handleImgSuccess(attachments){
                if(attachments.length>0) {
                    this.$set(this.content, "thumbnail_temp", attachments[0].thumbnail_temp)
                    this.$set(this.content, "thumbnail", attachments[0].url)
                }
            },
            handlePicturesSuccess(attachments){
                if(attachments.length>0) {
                    for(let o of attachments){
                        o.url=o.thumbnail_temp
                        this.content.pictures.push(o)
                    }
                    this.saveForm('form')
                }


            },
            handlePictureRemove(attachmentId) {
                if(this.content.id!=null) {
                    this.$axios.get(
                        this.HOST + "/delAttachment",
                        {params: {contentId: this.content.id,attachmentId:attachmentId}},
                    ).then((res) => {
                        if(res.data.code==0) {
                            this.$message.success('删除图片成功');
                            for(let o in this.content.pictures){
                                if(this.content.pictures[o].id==attachmentId){
                                    this.content.pictures.splice(o,1)
                                    break
                                }
                            }
                        }else{
                            this.$message.error(res.data.msg);
                        }
                    })
                }
            },
            saveForm(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$axios({
                            method:"post",
                            url: this.HOST+"/updateContent?lanmuId="+this.lanmu.id,
                            data: this.content
                        }).then((res)=>{
                            if(res.data.code==0) {
                                this.content.id=res.data.content.id;
                                this.$message.success('保存成功');
                            }else{
                                this.$message.error(res.data.msg);
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

            ObjectGetByKey(key){
                let content=this.content;
                return content[key]
            },
            ObjectSetByKey(key,val){
                this.$set(this.content,key,val);
            }

        },
        created:function(){
            this.init();
        },
        watch: {
            '$route' (to, from) {
                this.init();
            }
        },
        computed: {

        },
        components:{
            'tinymce':tinymce,
            'pictureview':pictureview
        }
    }

</script>

<style>

</style>

