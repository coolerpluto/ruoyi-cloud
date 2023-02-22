<template>
  <div class="app-container"><h3>签约信息</h3><el-divider/>
    <el-form ref="signInfoForm" :model="signInfoForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="签约状态" prop="releaseYear">
            <el-radio v-model="signInfoForm.newOrOldCust" label="Y">已签约</el-radio>
            <el-radio v-model="signInfoForm.newOrOldCust" label="N">未签约</el-radio>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="签约渠道" prop="policyName">
            <el-select v-model="signInfoForm.value" filterable placeholder="请选择中标公司" clearable>
              <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                         :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="签约日期" prop="policyName">
            <el-date-picker
              v-model="signInfoForm.value1"
              type="datetime"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="状态说明" prop="policyName">
            <el-input v-model="signInfoForm.policyName" type="textarea" resize="none" :rows="3"
                      placeholder="请输入项目总结经验,小于500个字" clearable>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "signInfo",
  dicts: ['crm_fund_source_type'],
  data(){
    return {
      signInfoForm:{},
    }
  },
}
</script>

<style scoped>

</style>
