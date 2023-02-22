<template>
  <div class="app-container"><h3>{{ stageTitle }}</h3>
    <el-divider/>
    <el-form ref="oppDescForm" :model="oppDescForm" size="medium" label-width="220px">
      <div v-if="[1,2,3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="客户使用该系统的原因" prop="policyName">
              <el-input v-model="oppDescForm.policyName" type="textarea" resize="none" :rows="3"
                        placeholder="请输入客户使用该系统的主要痛点以及原因说明" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="客户要求系统正式上线时间" prop="releaseYear">
              <el-date-picker
                v-model="oppDescForm.value1"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户召集ISV、SI启动项目时间" prop="policyName">
              <el-date-picker
                v-model="oppDescForm.value1"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="系统项目总预算" prop="releaseYear">
              <el-input v-model="oppDescForm.releaseYear" placeholder="请输入系统项目总预算金额" clearable>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目中DB预算" prop="policyName">
              <el-input v-model="oppDescForm.policyName" placeholder="请输入项目中DB预算金额" clearable>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="对应我司产品" prop="policyName">
              <el-select v-model="oppDescForm.value" filterable placeholder="请选择对应我司产品" clearable>
                <el-option v-for="item in options" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[2,3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="有无入围要求" prop="releaseYear">
              <el-radio v-model="oppDescForm.newOrOldCust" label="Y">有</el-radio>
              <el-radio v-model="oppDescForm.newOrOldCust" label="N">无</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否批准立项" prop="policyName">
              <el-radio v-model="oppDescForm.newOrOldCust" label="Y">是</el-radio>
              <el-radio v-model="oppDescForm.newOrOldCust" label="N">否</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资金是否到位" prop="policyName">
              <el-radio v-model="oppDescForm.newOrOldCust" label="Y">是</el-radio>
              <el-radio v-model="oppDescForm.newOrOldCust" label="N">否</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="预算来源" prop="releaseYear">
              <el-select v-model="oppDescForm.value" filterable placeholder="请选择预算来源" clearable>
                <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计投标日期" prop="policyName">
              <el-date-picker
                v-model="oppDescForm.value1"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计签单期日" prop="policyName">
              <el-date-picker
                v-model="oppDescForm.value1"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="预计招标类型" prop="releaseYear">
              <el-select v-model="oppDescForm.value" filterable placeholder="请选择招标类型" clearable>
                <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计合同金额" prop="policyName">
              <el-input v-model="oppDescForm.policyName" placeholder="请输入预计合同金额" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="商机背景目的简述" prop="policyName">
              <el-input v-model="oppDescForm.policyName" type="textarea" resize="none" :rows="2"
                        placeholder="请输入商机背景目的简述" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="核心业务说明" prop="policyName">
              <el-input v-model="oppDescForm.policyName" type="textarea" resize="none" :rows="2"
                        placeholder="请输入客户核心业务说明" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="用户关注点" prop="releaseYear">
              <el-select v-model="oppDescForm.value" filterable placeholder="请选择用户关注点" clearable>
                <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[4].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="预计投标日期" prop="releaseYear">
              <el-date-picker
                v-model="oppDescForm.value1"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计签单日期" prop="releaseYear">
              <el-date-picker
                v-model="oppDescForm.value1"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "oppDesc",
  dicts: ['crm_fund_source_type'],
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '商机描述'
    }
  },
  data() {
    return {
      oppDescForm: {},
      options: [],
    }
  },
}
</script>

<style scoped>

</style>
