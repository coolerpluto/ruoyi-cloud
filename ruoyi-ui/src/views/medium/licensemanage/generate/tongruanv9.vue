<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="120px">
        <el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="lic版本号" prop="licVer">
                <el-input v-model="formData.licVer" placeholder="请输入license版本号 默认1.2" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="商机编号" prop="licSerial">
                <el-input v-model="formData.licSerial" placeholder="请输入商机编号" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="lic用途" prop="licAuthStyle">
                <el-select v-model="formData.licAuthStyle" placeholder="请选择数据库用途" clearable :style="{width: '100%'}">
                  <el-option label="测试" value="trial"></el-option>
                  <el-option label="交付" value="formal"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="产品" prop="productName">
                <el-input v-model="formData.productName" placeholder="请输入产品 如:hgdb" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="产品名" prop="productType">
                <el-input v-model="formData.productType" placeholder="请输入产品名 如:enterprise" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="主版本号" prop="majorVersion">
                <el-input v-model="formData.majorVersion" placeholder="请输入产品主版本号" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="小版本号" prop="minVersion">
                <el-input v-model="formData.minVersion" placeholder="请输入产品小版本号" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="项目名称" prop="projectInfo">
                <el-input v-model="formData.projectInfo" placeholder="请输入项目名称" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最终用户" prop="userInfo">
                <el-input v-model="formData.userInfo" placeholder="请输入最终用户" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8"></el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="授权方式" prop="licUsedStyle">
                <el-select v-model="formData.licUsedStyle" placeholder="请输入授权方式" clearable :style="{width: '100%'}">
                  <el-option v-for="(item, index) in dict.type.medium_lic_db_auth_type" :key="index" :label="item.label"
                             :value="item.value" :disabled="item.disabled"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="授权开始日" prop="createTime">
                <el-date-picker v-model="formData.createTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                :style="{width: '100%'}" placeholder="请选择授权开始日" :clearable="false"
                                type="date"></el-date-picker>

              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="授权到期日" prop="finalTime">
                <el-date-picker v-model="formData.finalTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                :style="{width: '100%'}" placeholder="请选择授权到期日" :clearable="false"
                                type="date"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="默认数据库" prop="defaultDBname">
                <el-input v-model="formData.defaultDBname" placeholder="请输入默认数据库 如:highgo" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="默认数据库用户" prop="defaultDBuser">
                <el-input v-model="formData.defaultDBuser" placeholder="请输入默认数据库用户 如:highgo" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="默认端口号" prop="defaultDBport">
                <el-input v-model="formData.defaultDBport" placeholder="请输入默认端口号 如:5866" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="最大连接数" prop="maxConnection">
                <el-input-number v-model="formData.maxConnection" :min="0" :max="9999" label="最大连接数"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="CPU核数" prop="maxCpuCores">
                <el-input-number v-model="formData.maxCpuCores" :min="0" :max="9999" label="CPU核数"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="三位随机数" prop="seedIndex">
                <el-input v-model="formData.seedIndex" placeholder="请输入随机三位数" clearable :style="{width: '100%'}">
                  <el-button slot="append" icon="el-icon-refresh" @click="randomSeedIndex"></el-button>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="校验机器码" prop="isCheckHardware">
                <el-radio-group v-model="formData.isCheckHardware">
                  <el-radio :label="'true'">校验</el-radio>
                  <el-radio :label="'false'">不校验</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="是否机器码" prop="isHardwareCode">
                <el-radio-group v-model="formData.isHardwareCode">
                  <el-radio :label="'true'">是机器码</el-radio>
                  <el-radio :label="'false'">不是机器码</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="功能选择" prop="funcList">
                <el-select v-model="formData.funcList" placeholder="请选择数据库版本" multiple filterable
                           :style="{width: '100%'}">
                  <el-option v-for="(item, index) in funcList" :key="index" :label="item.desc"
                             :value="item.key"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="机器码或MAC" prop="hardwareCode">
            <el-input type="textarea" resize="none" :rows="4"
                      placeholder="请输入机器码或者MAC地址,只能输入一种格式,多个时试用逗号分割"
                      v-model="formData.hardwareCode">
            </el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item size="large" align="middle">
            <el-button type="primary" @click="submitForm">生成</el-button>
            <el-button @click="resetForm">重置</el-button>
            <el-button @click="drawer = true">工具说明</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-row>
    <el-drawer title="工具参数说明" :visible.sync="drawer" :direction="'btt'" size="50%" :before-close="handleDrawerClose">
      <el-col :span="12">
        <el-card class="box-card" style="width:95%;white-space:pre-line;">
          生成工具介绍<br>
          <br>
          参数说明：<br>
          License用途：license.cfg文件中的"lic_used_style"，如"测试"<br>
          产品：license.cfg文件中的"productType"，如"enterprise"<br>
          产品版本：license.cfg文件中的"majorVersion"和"minVersion"，如"9"和"0.1"<br>
          商机编号：license.cfg文件中的"lic_serial"，如"A20230224N004620625917"<br>
          项目名称：license.cfg文件中的"project_info"，如"稀土项目"<br>
          最终用户：license.cfg文件中的"user_info"，如"山东浪潮通软"<br>
          机器码：license.cfg文件中的"hardware_code"，可以是licchk -c查询出的机器码<br>
          最大连接数：license.cfg文件中的"max_connection"，如："30"<br>
          授权开始日：license.cfg文件中的"create_time"<br>
          授权到期日：license.cfg文件中的"final_time"<br>
          授权方式：license.cfg文件中的"lic_auth_style"<br>
          <br>
        </el-card>
      </el-col>
      lic_auth_style: 用途：测试trial | 交付formal
      lic_used_style：
      <el-col :span="12">
        <el-card style="width:95%;white-space:pre-line;">
          license.cfg文件中各参数以及含义如下，<br>
          {<br>
          "lic_ver" : "1.2", --license版本号<br>
          "lic_serial" : "A20230224N004620625917", --商机编号<br>
          "lic_auth_style" : "trial", --用途：测试 trial | 交付 formal<br>
          "lic_used_style" : "esl", --授权方式： esl | asfu| fulluse<br>
          "productName" : "hgdb", --产品<br>
          "majorVersion" : "9", --主版本号，版本号内容第一位数字<br>
          "minVersion" : "0.1", --小版本号，版本号内容剩下的数字<br>
          "productType" : "enterprise", --产品名<br>
          "user_info" : "山东浪潮通软", --最终用户<br>
          "project_info" : "稀土项目", --项目名称<br>
          "create_time" : "2023-03-22", --授权开始日<br>
          "final_time" : "2024-03-21", --授权到期日<br>
          "max_connection" : "30", --最大连接数<br>
          "max_cpu_cores" : "0", -- CPU核数 0表示不限<br>
          "defaultDBname" : "highgo", --默认数据库<br>
          "defaultDBuser" : "highgo", --默认数据库用户<br>
          "defaultDBport" : "5866", --默认端口号<br>
          "seedIndex" : "0", --三位随机数：0~999<br>
          "is_check_hardware" : "false", -- lic_used_style为”测试”时，值为false，为”交付”时，为ture<br>
          "is_hardware_code" : "true", -- 固定<br>
          "hardware_code" : "xxxxxxxx", -- 机器码<br>
          "func_list" : ["0","2","3"] -- 固定<br>
          }
        </el-card>
      </el-col>
      使用MAC时：<br>
      "is_hardware_code" : "false",<br>
      "hardware_code" : {<br>
      "MAC":"00:00:00:00:00:00,52:54:00:fe:79:25,52:54:00:5a:b2:be"<br>
      },<br>
      使用机器码时：<br>
      "is_hardware_code" : "true",<br>
      "hardware_code" : "DB16FCDE6F523B12EFCD18874D3F8B58,DB16FCDE6F523B12EFCD18874D3F8B58",<br>
      <br>
      作者： <br>
      葛天成 <br>
    </el-drawer>
  </div>
</template>
<script>
import {generatorV9} from "@/api/medium/license";

export default {
  dicts: ["medium_lic_db_auth_type"],
  name: "LicenseGenerate",
  components: {},
  props: [],
  data() {
    return {
      drawer: false,
      formData: {
        licVer: "1.2",
        licSerial: undefined,
        licAuthStyle: undefined,
        productName: 'hgdb',
        productType: 'enterprise',
        majorVersion: undefined,
        minVersion: undefined,
        projectInfo: undefined,
        userInfo: undefined,
        licUsedStyle: 'esl',
        createTime: new Date(),
        finalTime: new Date(),
        defaultDBname: 'highgo',
        defaultDBuser: 'highgo',
        defaultDBport: '5866',
        maxConnection: 30,
        maxCpuCores: 0,
        seedIndex: undefined,
        isCheckHardware: 'false',
        isHardwareCode: 'false',
        funcList: ["0", "2", "3"],
        hardwareCode: undefined,
      },
      funcList: [
        {'key': '0', desc: "命令行工具"},
        {'key': '1', desc: "图形化工具"},
        {'key': '2', desc: "逻辑备份还原"},
        {'key': '3', desc: "物理备份还原"},
        {'key': '4', desc: "文件导入导出"},
        {'key': '5', desc: "闪回查询"},
        {'key': '6', desc: "快速加载"},
        {'key': '7', desc: "数据分片"},
        {'key': '8', desc: "全文检索"},
        {'key': '9', desc: "并行查询"},
        {'key': '10', desc: "并行备份还原"},
        {'key': '11', desc: "三权分立"},
        {'key': '12', desc: "身份鉴别"},
        {'key': '13', desc: "强制访问控制"},
        {'key': '14', desc: "安全审计"},
        {'key': '15', desc: "数据加密"},
        {'key': '16', desc: "恶意代码防范"},
        {'key': '17', desc: "异构数据"},
        {'key': '18', desc: "日志解析"},
        {'key': '19', desc: "数据脱敏"},
        {'key': '20', desc: "兼容模块"},
        {'key': '21', desc: "定时任务"},
        {'key': '22', desc: "集群软件"},
        {'key': '23', desc: "读写分离"},
        {'key': '24', desc: "性能诊断"},
        {'key': '25', desc: "安全平台"},
        {'key': '26', desc: "日志急救"}
      ]
      ,
      rules: {
        licVer: [{required: true, message: '请输入 License 版本号', trigger: 'blur'}],
        licSerial: [{required: true, message: '请输入商机编号', trigger: 'blur'}],
        licAuthStyle: [{required: true, message: '请选择数据库用途', trigger: 'blur'}],
        productName: [{required: true, message: '请输入产品 如:hgdb', trigger: 'blur'}],
        productType: [{required: true, message: '请输入产品名 如:enterprise', trigger: 'blur'}],
        majorVersion: [{required: true, message: '请输入产品主版本号', trigger: 'blur'}],
        minVersion: [{required: true, message: '请输入产品小版本号', trigger: 'blur'}],
        projectInfo: [{required: true, message: '请输入项目名称', trigger: 'blur'}],
        userInfo: [{required: true, message: '请输入最终用户信息', trigger: 'blur'}],
        licUsedStyle: [{required: true, message: '请选择授权方式', trigger: 'blur'}],
        createTime: [{required: true, message: '请选择授权开始日', trigger: 'blur'}],
        finalTime: [{required: true, message: '请选择授权到期日', trigger: 'blur'}],
        defaultDBname: [{required: true, message: '请输入默认数据库', trigger: 'blur'}],
        defaultDBuser: [{required: true, message: '请输入默认数据库用户', trigger: 'blur'}],
        defaultDBport: [{required: true, message: '请输入默认端口号', trigger: 'blur'}],
        maxConnection: [{required: true, message: '请输入最大连接数', trigger: 'blur'}],
        maxCpuCores: [{required: true, message: '请输入最大CPU核数', trigger: 'blur'}],
        seedIndex: [{required: true, message: '请生成随机数', trigger: 'blur'}],
        isCheckHardware: [{required: true, message: '请选是不是进行机器码校验', trigger: 'blur'}],
        isHardwareCode: [{required: true, message: '请选择是不是的录入机器码', trigger: 'blur'}],
        funcList: [{required: true, message: '选择功能', trigger: 'blur'}],
        hardwareCode: [{required: false, message: '请输入商机编号', trigger: 'blur'}],
      },
    }
  },
  methods: {
    handleDrawerClose(done) {
      done()
    },
    randomSeedIndex() {
      this.formData.seedIndex = Math.floor(Math.random() * 999);
      console.log(this.formData.seedIndex);
    },
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        generatorV9(this.formData).then(response => {
          this.$modal.msgSuccess("生成成功");
          this.resetForm();
        });
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
