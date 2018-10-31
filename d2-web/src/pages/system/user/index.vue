<template>
  <d2-container>
    <template slot="header">
        <el-row :gutter="20" class="query-box">
                <el-col :span="4">
                    <el-input placeholder="请输入昵称" clearable v-model="nickname" size="small">
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-input placeholder="请输入手机号" clearable v-model="telephone" size="small">
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" icon="el-icon-search" size="small" @click="filterCustom">查找</el-button>
                </el-col>
            </el-row>
    </template>
    <d2-crud v-bind="userTable"  >
    </d2-crud>
    <template slot="footer">
      aa
    </template>
  </d2-container>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'user',
  data () {
    return {
      userTable: {
        ref: 'd2Table',
        indexRow: true,
        columns: [
          { title: '账号', key: 'userAccount' },
          { title: '姓名', key: 'realName' },
          {
            title: '图像',
            key: 'avatar_tmp',
            component: {
              name: 'img',
              class: 'img-avatar'
            }
          },
          { title: '手机号码', key: 'mobile' },
          { title: '部门', key: 'deptName' },
          { title: '角色', key: 'roleName' },
          { title: '注册时间', key: 'registDatetime' },
          { title: '状态', key: 'status' }
        ],
        data: []
      }
    }
  },
  created () {
    this.getUserList({
      vm: this,
      pageNum: 1,
      pageSize: 10
    })
  },
  methods: {
    ...mapActions('d2admin/user', ['getUserList'])
  }
}
</script>
<style >
.img-avatar {
  width: 40px;
  height: 40px;
  display: block;
}
.query-box {
  display: flex;
  height: 40px;
  background-color: #f4f4f4;
}
</style>
