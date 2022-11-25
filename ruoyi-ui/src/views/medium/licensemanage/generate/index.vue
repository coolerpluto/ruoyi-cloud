<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="商机编号" prop="opportunityId">
              <el-input v-model="formData.opportunityId" placeholder="请输入商机编号" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>  
          <el-col :span="8">  
            <el-form-item label="数据库授权" prop="dbAuthType">
              <el-select v-model="formData.dbAuthType" placeholder="请输入数据库授权" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in dict.type.medium_lic_db_auth_type" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">  
            <el-form-item label="最终用户" prop="finalCustomer">
              <el-input v-model="formData.finalCustomer" placeholder="请输入最终用户" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="授权用途" prop="purposes">
              <el-select v-model="formData.purposes" placeholder="请输入授权用途" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in dict.type.medium_lic_purposes" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-show="formData.purposes != 'o'">
            <el-form-item label="授权到期日" prop="expiresTime">
              <el-date-picker v-model="formData.expiresTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                :style="{width: '100%'}" placeholder="请选择授权到期日" :clearable="false"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="产品类型" prop="prodType">
              <el-select v-model="formData.prodType" placeholder="请选择产品类型" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in dict.type.medium_lic_prod_type" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-show="formData.prodType == '0'">
            <el-form-item label="数据库版本" prop="dbVersion">
              <el-select v-model="formData.dbVersion" placeholder="请选择数据库版本" filterable  :style="{width: '100%'}">
                <el-option v-for="(item, index) in dict.type.medium_lic_db_version" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row >
          <el-form-item size="large" align="middle">
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import {listGenerated,getGenerateInfo,generateOne,delGenerate} from "@/api/medium/licensegenerate";

export default {
  dicts: ["sys_yes_no","medium_lic_db_auth_type","medium_lic_purposes","medium_lic_prod_type","medium_lic_db_version"],
  name: "License Generate",
  components: {},
  props: [],
  data() {
    return {
      formData: {
        opportunityId: undefined,
        dbAuthType: 'esl',
        finalCustomer: undefined,
        purposes: 't',
        expiresTime: '2099-12-30',
        prodType: '0',
        dbVersion: 'HGDB-SEE-V',
      },
      rules: {
        opportunityId: [{
          required: true,
          message: '请输入商机编号',
          trigger: 'blur'
        }],
        dbAuthType: [{
          required: true,
          message: '请输入数据库授权',
          trigger: 'change'
        }],
        finalCustomer: [{
          required: true,
          message: '请输入最终用户',
          trigger: 'blur'
        }],
        purposes: [{
          required: true,
          message: '请输入授权用途',
          trigger: 'change'
        }],
        expiresTime: [],
        prodType: [{
          required: true,
          message: '请选择产品类型',
          trigger: 'change'
        }],
        dbVersion: [],
      },
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
        generateOne(formData)
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}
</script>
<style>
</style>
