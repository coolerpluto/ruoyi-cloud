<template>
  <div class="app-container">
    <h3>用户信息概述</h3>
    <el-divider/>
    <el-form ref="custInfoForm" :model="custInfoForm" size="medium" label-width="220px">
      <div v-if="stageShow==1">
        <el-row :gutter="15">
          <el-col :span="9">
            <el-form-item label="客户公司名称" prop="releaseYear">
              <el-input v-model="custInfoForm.releaseYear" placeholder="请选择客户公司名称" clearable>
                <el-button slot="append" icon="el-icon-search">选择</el-button>
                <el-button slot="append" icon="el-icon-plus">新建</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="15">
            <el-form-item label="公司地址" prop="policyName">
              <el-input v-model="custInfoForm.policyName" placeholder="请选择公司地址三级联动" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="9">
            <el-form-item label="地址邮编" prop="releaseYear">
              <el-input v-model="custInfoForm.releaseYear" placeholder="请输入地址邮编" clearable>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="15">
            <el-form-item label="详细地址" prop="policyName">
              <el-input v-model="custInfoForm.policyName" placeholder="请输入公司地址到门牌号" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="客户描述" prop="policyName">
              <el-input v-model="custInfoForm.policyName" type="textarea" resize="none" :rows="2"
                        placeholder="请输入对客户的描述信息" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="stageShow==2">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="新老客户" prop="newOrOldCust">
              <el-radio v-model="custInfoForm.newOrOldCust" label="new">新客户</el-radio>
              <el-radio v-model="custInfoForm.newOrOldCust" label="old">老客户</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="行业影响力" prop="policyName">
              <el-select v-model="custInfoForm.industryInfluence" placeholder="请选择">
                <el-option
                  v-for="item in dict.type.crm_industry_influence"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="项目是否正式发布红头文件" prop="newOrOldCust">
              <el-radio v-model="custInfoForm.redHeadedDoc" label="have">有发布</el-radio>
              <el-radio v-model="custInfoForm.redHeadedDoc" label="no">未发布</el-radio>
              <el-radio v-model="custInfoForm.redHeadedDoc" label="unknow">不知道</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目是否有会议指示" prop="meetingInstruction">
              <el-radio v-model="custInfoForm.meetingInstruction" label="have">有指示</el-radio>
              <el-radio v-model="custInfoForm.meetingInstruction" label="no">无指示</el-radio>
              <el-radio v-model="custInfoForm.meetingInstruction" label="unknow">不知道</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否需要售前支持" prop="preSaleSupport">
              <el-radio v-model="custInfoForm.preSaleSupport" label="need">需要支持</el-radio>
              <el-radio v-model="custInfoForm.preSaleSupport" label="no">暂不需要</el-radio>
              <el-radio v-model="custInfoForm.preSaleSupport" label="done">已完成支持</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "custInfo",
  dicts:['crm_industry_influence'],
  props: {
    stageShow: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      custInfoForm: {},
    }
  },
}
</script>

<style scoped>

</style>
