<template>
  <div class="app-container"><h3>{{ stageTitle }}</h3>
    <el-divider/>
    <el-form ref="oppImplementForm" :model="oppImplementForm" size="medium" label-width="350px">
      <div v-if="[3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="是否了解招标方案内容及主要评分项(废标项,加分项等)" prop="releaseYear">
              <el-radio v-model="oppImplementForm.newOrOldCust" label="Y">已了解</el-radio>
              <el-radio v-model="oppImplementForm.newOrOldCust" label="N">不了解</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="公司产品参数是否写入招标方案" prop="policyName">
              <el-radio v-model="oppImplementForm.newOrOldCust" label="Y">已写入</el-radio>
              <el-radio v-model="oppImplementForm.newOrOldCust" label="N">未写入</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="是否了解用户招标的决策流程" prop="releaseYear">
              <el-radio v-model="oppImplementForm.newOrOldCust" label="Y">已了解</el-radio>
              <el-radio v-model="oppImplementForm.newOrOldCust" label="N">不了解</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否可以掌控招标流程各环节" prop="policyName">
              <el-radio v-model="oppImplementForm.newOrOldCust" label="Y">可以</el-radio>
              <el-radio v-model="oppImplementForm.newOrOldCust" label="N">不可以</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="合同落单成功率" prop="code">
              <label>90%</label>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "oppImplement",
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '落实机会关键单点'
    }
  },
  data() {
    return {
      oppImplementForm: {},
      options: [],
    }
  },
}
</script>

<style scoped>

</style>
