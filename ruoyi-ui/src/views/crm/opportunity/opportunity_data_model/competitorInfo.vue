<template>
  <div class="app-container"><h3>{{ stageTitle }}</h3>
    <el-divider/>
    <el-form ref="competitorInfoForm" :model="competitorInfoForm" size="medium" label-width="220px">
      <el-button style="float:right;" type="info" icon="el-icon-plus">添加竞争对手</el-button>
      <el-table :data="competitorInfoForm.competitorInfo">
        <el-table-column label="竞争对手" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="参与项目/产品名" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="单价/报价" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="对方优势" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column label="我方具备情况" align="center" prop="stage" :show-overflow-tooltip="true"/>
        <el-table-column align="center" prop="stage" :show-overflow-tooltip="true">
          <template slot="header" slot-scope="scope">
            <label>备注<br><span style="color: red;">(销售人员对该竞争对手的其他描述)</span></label>
          </template>
        </el-table-column>
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
  name: "competitorInfo",
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '竞争对手'
    }
  },
  data() {
    return {
      competitorInfoForm: {},
    }
  },
}
</script>

<style scoped>

</style>
