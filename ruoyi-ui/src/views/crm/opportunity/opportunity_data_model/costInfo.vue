<template>
  <div class="app-container"><h3>商机花费</h3>
    <el-divider/>
    <el-form ref="costInfoForm" :model="costInfoForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="累记支出：" prop="releaseYear">
            <label>1000</label>
          </el-form-item>
        </el-col>
        <el-button style="float:right;" type="info" icon="el-icon-plus">添加新的支出</el-button>
      </el-row>
      <el-table :data="costInfoForm.costInfo">
        <el-table-column label="发生日期" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="投入事由" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="投入金额(元)" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="投入人员" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="填写人员" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="填写时间" align="center" prop="stage" :show-overflow-tooltip="true"/>
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
  name: "costInfo",
  props: {
    stageShow: {
      type: Number,
      default: 0
    },
    stageTitle: {
      type: String,
      default: '商机花费支出'
    },
  },
  data() {
    return {
      costInfoForm:{}
    }
  },
}
</script>

<style scoped>

</style>
