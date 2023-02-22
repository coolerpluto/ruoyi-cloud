<template>
  <div class="app-container"><h3>政策支持信息</h3>
    <el-divider/>
    <el-form ref="policyStandByForm" :model="policyStandByForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="9">
          <el-form-item label="政策年分" prop="releaseYear">
            <el-input v-model="policyStandByForm.releaseYear" placeholder="请选择政策发布年份" clearable>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="15">
          <el-form-item label="政策文件" prop="policyName">
            <el-input v-model="policyStandByForm.policyName" placeholder="请输入选择政策文件" clearable>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "PolicyStandBy",
  data() {
    return {
      policyStandByForm: {},
    };
  },
  methods:{

  },
}
</script>

<style scoped>

</style>
