<template>
  <div class="app-container">
    <h3>{{ stageTitle }}</h3>
    <el-divider/>
    <el-form ref="winningBiddingForm" :model="winningBiddingForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="中标公司名称" prop="releaseYear">
            <el-select v-model="winningBiddingForm.value" filterable placeholder="请选择中标公司" clearable>
              <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                         :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="中标公司报价" prop="releaseYear">
            <label placeholder="自动生产商机编码">{{ winningBiddingForm.code }}</label>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "winningBidding",
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '中标结论'
    }
  },
  dicts: ['crm_fund_source_type'],
  data() {
    return {
      winningBiddingForm: {},
    }
  },
}
</script>

<style scoped>

</style>
