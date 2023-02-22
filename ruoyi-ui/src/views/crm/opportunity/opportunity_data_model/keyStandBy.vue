<template>
  <div class="app-container">
    <h3>商机支持人(公司内部人员)</h3>
    <el-divider/>
    <el-form ref="keyStandByForm" :model="keyStandByForm" size="medium" label-width="220px">
      <el-button style="float:right;" type="info" icon="el-icon-plus">添加新支持人</el-button>
      <el-table :data="keyStandByForm.keyStandBy">
        <el-table-column align="center" fixed prop="value" :show-overflow-tooltip="true" width="500">
          <template slot="header" slot-scope="scope">
            <label >支持内容<span style="color: red;">(必须注明和我方业务相关信息)</span></label>
          </template>
        </el-table-column>
        <el-table-column label="支持人员" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="填报人" align="center" prop="creator" :show-overflow-tooltip="true"/>
        <el-table-column label="填报时间" align="center" prop="createTime" :show-overflow-tooltip="true"/>
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
  name: "keyStandBy",
  data(){
    return {
      keyStandByForm:{},
    }
  },
}
</script>

<style scoped>

</style>
