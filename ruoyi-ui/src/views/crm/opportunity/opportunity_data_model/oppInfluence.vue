<template>
  <div class="app-container"><h3>产生的影响</h3>
    <el-divider/>
    <el-form ref="oppInfluenceForm" :model="oppInfluenceForm" size="medium" label-width="220px">
      <div v-if="[2].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="对用户组织架构及作用说明" prop="policyName">
              <el-input v-model="oppInfluenceForm.policyName" type="textarea" resize="none" :rows="3"
                        placeholder="对用户组织架构及作用的补充说明应小于500个字" clearable>
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
  name: "oppInfluence",
  dicts:['crm_fund_source_type'],
  props: {
    stageShow: {
      type: Number,
      default: 2
    }
  },
  data() {
    return {
      oppInfluenceForm: {},
      options: [],
    }
  },
}
</script>

<style scoped>

</style>
