<template>
  <div class="app-container">
    <h3>产品以及报价</h3><el-divider/>
    <el-form ref="quotationInfoForm" :model="quotationInfoForm" size="medium" label-width="220px">
      <el-button style="float:right;" type="info" icon="el-icon-plus">添加报价</el-button>
      <el-table :data="quotationInfoForm.quotationInfo">
        <el-table-column label="产品名称" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="大版本" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="小版本" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="数量" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="单价" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="总价" align="center" prop="stage" :show-overflow-tooltip="true"/>
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
  name: "quotationInfo",
  data(){
    return {
      quotationInfoForm:{},
    }
  },
}
</script>

<style scoped>

</style>
