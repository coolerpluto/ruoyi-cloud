<template>
  <div class="app-container"><h3>{{ stageTitle }}</h3><el-divider/>
    <el-form ref="summaryInfoForm" :model="summaryInfoForm" size="medium" label-width="220px">
      <div v-if="[2].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="后续动作" prop="releaseYear">
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="Y">参与</el-radio>
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="N">放弃</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="后续跟进措施或原因" prop="policyName">
              <el-input v-model="summaryInfoForm.policyName" type="textarea" resize="none" :rows="3"
                        placeholder="后续跟进措施或原因应小于500个字" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[4].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="20">
            <el-form-item label="后续动作" prop="releaseYear">
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="Y">中标(转L5)</el-radio>
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="N">弃标(转L6)</el-radio>
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="N">项目终止(转L7)</el-radio>
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="N">丢标(转L8)</el-radio>
              <el-radio v-model="summaryInfoForm.newOrOldCust" label="N">关闭商机(转L11)</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="后续跟进措施或原因" prop="policyName">
              <el-input v-model="summaryInfoForm.policyName" type="textarea" resize="none" :rows="3"
                        placeholder="后续跟进措施或原因应小于500个字" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[5,6,7,8,9,11].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="总结" prop="policyName">
              <el-input v-model="summaryInfoForm.policyName" type="textarea" resize="none" :rows="3"
                        placeholder="请输入项目总结经验,小于500个字" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "summaryInfo",
  dicts:['crm_fund_source_type'],
  props: {
    stageShow: {
      type: Number,
      default: 2
    },
    stageTitle: {
      type: String,
      default: '总结'
    }
  },
  data(){
    return {
      summaryInfoForm:{},
    }
  },
}
</script>

<style scoped>

</style>
