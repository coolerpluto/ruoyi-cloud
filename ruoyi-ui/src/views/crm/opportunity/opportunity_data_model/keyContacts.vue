<template>
  <div class="app-container">
    <h3>商机关键联系人<span style="color: red;">{{stageTitle}}</span></h3>
    <el-divider/>
    <el-form ref="keyContactForm" :model="keyContactForm" size="medium" label-width="220px">
      <el-button style="float:right;" type="info" icon="el-icon-plus">添加新联系人</el-button>
      <el-table :data="keyContactForm.keyContact">
        <el-table-column label="姓名" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="性别" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="单位名称" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="单位性质" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="人员角色" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="人员职务" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="所在部门" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column align="center" prop="value" :show-overflow-tooltip="true">
          <template slot="header" slot-scope="scope">
            <label>联系方式<br><span style="color: red;">(首选手机号)</span></label>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="value" :show-overflow-tooltip="true">
          <template slot="header" slot-scope="scope">
            <label>对接情况<span style="color: red;">(按照重要程度选择最新进展)</span></label>
          </template>
        </el-table-column>
        <el-table-column label="关系程度" align="center" prop="creator" :show-overflow-tooltip="true"/>
        <el-table-column label="关键人意向厂商" align="center" prop="createTime" :show-overflow-tooltip="true"/>
        <el-table-column label="对方是否会面对手" align="center" prop="createTime" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "KeyContacts",
  props: {
    stageShow: {
      type: Number,
      required: true
    },
    stageTitle: {
      type: String,
      default: ''
    },
    reasonDictCode: {
      type: String,
      default: 'crm_opportunity_finish_reason'
    },
  },
  data() {
    return {
      keyContactForm: {},
    }
  },
}
</script>

<style scoped>

</style>
