<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="商机编号" prop="opportunityNum">
              <el-input v-model="formData.opportunityNum" placeholder="请输入商机编号" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="数据库授权" prop="authType">
              <el-select v-model="formData.authType" placeholder="请输入数据库授权" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in dict.type.medium_lic_db_auth_type" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="最终用户" prop="customerName">
              <el-input v-model="formData.customerName" placeholder="请输入最终用户" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="授权用途" prop="purpose">
              <el-select v-model="formData.purpose" placeholder="请输入授权用途" clearable :style="{width: '100%'}" @change="purposeChanged">
                <el-option v-for="(item, index) in dict.type.medium_lic_purposes" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-show="formData.purpose != 'o'">
            <el-form-item label="授权到期日" prop="expireTime">
              <el-date-picker v-model="formData.expireTime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                :style="{width: '100%'}" placeholder="请选择授权到期日" :clearable="false" type="datetime"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="产品类型" prop="prodType">
              <el-select v-model="formData.prodType" placeholder="请选择产品类型" clearable :style="{width: '100%'}" @change="prodTypeChanged">
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
            <el-button type="primary" @click="submitForm">生成</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-row>
    <el-row>
      <el-card class="box-card" style="width:55%;white-space:pre-line;">
      License生成工具介绍<br>
      <br>
      参数说明：<br>
      1. -a 指定授权有效期, 可为'u'或'unlimited'（永久）或具体时间（xxxxxxxx 必须为 8 位未来时间）<br>
      2. -b 指定商机编号, 长度为16位, 可由大小写字母和数字组成 <br>
      3. -i 指定授权方式, 'fulluse'; 'esl'; 'asfu'<br>
      4. -m 指定授权用途, 't'测试; 'o'交付<br>
      5. -f 指定license文件存放目录（default: 当前文件夹下的license）<br>
      6. -p 选择要管控的其他产品和有效期,（永久为u或者unlimited）。<br>
          格式为db_ha/xxx、hg_proxy/xxx，/后的参数为对应产品的有效期，如果永久有效则为db_ha/u。<br>
          如指定多个产品，需要指定多个-p参数。如不指定该参数表示不选择除数据库外的产品/工具 <br>
      7. -u 指定用户信息，一般为用户汉字全称, 长度为60位，允许输入汉字、英文、数字，在utf8编码下，汉字最大输入长度为20个汉字 <br>
      8. -v 指定数据库版本, 格式: HGDB-SEE-Vx.y <br>
    <br>
      作者： <br>
        chengyuanhao@highgo.com 、liuzihe@highgo.com <br>
      </el-card>
    </el-row>
  </div>
</template>
<script>
import {generate} from "@/api/medium/license";

export default {
  dicts: ["medium_lic_db_auth_type","medium_lic_purposes","medium_lic_prod_type","medium_lic_db_version"],
  name: "LicenseGenerate",
  components: {},
  props: [],
  data() {
    return {
      formData: {
        opportunityNum: undefined,
        authType: 'esl',
        customerName: undefined,
        purpose: 't',
        expireTime: '2099-12-30 23:59:59',
        prodType: '0',
        dbVersion: 'HGDB-SEE-V',
      },
      rules: {
        opportunityNum: [{
          required: true,
          message: '请输入商机编号',
          trigger: 'blur'
        }],
        authType: [{
          required: true,
          message: '请输入数据库授权',
          trigger: 'change'
        }],
        customerName: [{
          required: true,
          message: '请输入最终用户',
          trigger: 'blur'
        }],
        purpose: [{
          required: true,
          message: '请输入授权用途',
          trigger: 'change'
        }],
        expireTime: [],
        prodType: [{
          required: true,
          message: '请选择产品类型',
          trigger: 'change'
        }],
        dbVersion: [],
      },
    }
  },
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return

        generate(this.formData).then(response => {
          this.$modal.msgSuccess("生成成功");
          this.resetForm();
        });
      })
    },
    purposeChanged(val){
      if(val==='o'){
        this.formData.expireTime=null;
      }else {
        this.formData.expireTime='2099-12-30 23:59:59';
      }
    },
    prodTypeChanged(val){
      if(val==='0'){
        this.formData.dbVersion= 'HGDB-SEE-V';
      }else {
        this.formData.dbVersion= null;
      }
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}
</script>
<style>
</style>
