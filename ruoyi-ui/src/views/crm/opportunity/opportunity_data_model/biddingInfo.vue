<template>
  <div class="app-container">
    <h3>招投标情况分析</h3>
    <el-divider/>
    <el-form ref="biddingInfoForm" :model="biddingInfoForm" size="medium" label-width="220px">
      <div v-if="[4].includes(stageShow)">
        <el-button style="float:right;" type="info" icon="el-icon-plus">添加新的对手</el-button>
        <el-table :data="biddingInfoForm.biddingInfo">
          <el-table-column label="厂商名称" align="center" prop="stage" :show-overflow-tooltip="true"/>
          <el-table-column label="是否参与投标" align="center" prop="stage" :show-overflow-tooltip="true"/>
          <el-table-column label="支持的数据库厂商" align="center" prop="stage" :show-overflow-tooltip="true"/>
          <el-table-column align="center" prop="stage" :show-overflow-tooltip="true">
            <template slot="header" slot-scope="scope">
              <label>本次投标总价(元)<br><span style="color: red;">(中标结论重要依据)</span></label>
            </template>
          </el-table-column>
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
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="是否了解全部评标专家名单" prop="releaseYear">
              <el-radio v-model="biddingInfoForm.newOrOldCust" label="Y">已了解</el-radio>
              <el-radio v-model="biddingInfoForm.newOrOldCust" label="N">不了解</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否已获得关键专家支持" prop="releaseYear">
              <el-radio v-model="biddingInfoForm.newOrOldCust" label="Y">已获得</el-radio>
              <el-radio v-model="biddingInfoForm.newOrOldCust" label="N">未获得</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>

  </div>
</template>

<script>
export default {
  name: "biddingInfo",
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '招投标情况分析'
    }
  },
  data() {
    return {
      biddingInfoForm: {},
      options: [],
    }
  },
}
</script>

<style scoped>

</style>
