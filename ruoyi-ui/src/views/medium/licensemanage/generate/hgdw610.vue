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
              <el-date-picker v-model="formData.expireTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                :style="{width: '100%'}" placeholder="请选择授权到期日" :clearable="false" type="date"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="数据库版本" prop="dbVersion">
              <el-select v-model="formData.dbVersion" placeholder="请选择数据库版本" filterable  :style="{width: '100%'}">
                <el-option v-for="(item, index) in dict.type.medium_db_version" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="集群节点数" prop="segments">
              <el-input-number v-model="formData.segments" :min="0" :max="9999" label="集群节点数"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="机器码" prop="mac">
              <el-input type="textarea" resize="none" :rows="2"
                placeholder="请输入机器码或者MAC地址,只能输入一种格式,多个时试用逗号分割" v-model="formData.mac">
              </el-input>
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
      <el-card class="box-card" style="width:95em;white-space:pre-line;">
        数仓610工具介绍 作者：王林森 <br> 生成工具：hgdw_licgen
        参数说明：<br>
        -m --mode，Lic用途：证书类型，o为交付，t为测试(试用)<br>
        -v --version，产品+产品版本：数据仓库的版本，格式x.y.z，只支持目前已经发布的数仓版本，如：目前发布版本6.1.0，支持参数值-v 6.1.0，如果输入-v 1.1.0会报错<br>
        -b --business，商机编号：用于生成license编号，长度16位不允许使用特殊字符与汉字，仅允许大、小写字母与数字<br>
        -u --userinfo，最终用户：用户识别信息，可用于辅助识别用户<br>
        -M --MAC，机器码：可选参数，指定要绑定的MAC地址，可支持10个，输入为空时不限制MAC地址<br>
        -s --size，集群节点数：集群规模，可选参数，默认值为2，仅计算maseter和所有的segments，节点数计算的是数据库的节点数目，例如一个segments上有两个数据库节点，则该segments上集群规模计算为2<br>
        -a --authorize，授权到期日：授权有效时间，后跟参数格式YYYYMMDD；当为永久授权时，后跟u/unlimited<br>
        -i --impower，授权方式：开放功能，当前仅支持fulluse<br>
        -f --impower, 生成文件指定文件目录位置<br>
        示例：<br>
        （1）生成license，省略-s和-M参数：
        ./hgdw_licgen -v 6.1.0 -m o -b A123456789123456 -u highgo -f /opt/hgdb_licgen/hgdw610/license -a 20231231 -i fulluse<br>
        （2）生成license，省略-M参数：
        ./hgdw_licgen -v 6.1.0 -m o -b A123456789123456 -u highgo -f /opt/hgdb_licgen/hgdw610/license -a 20231231 -i fulluse -s 100<br>
        （3）生成license，省略-s参数：
        ./hgdw_licgen -v 6.1.0 -m o -b A123456789123456 -u highgo -f /opt/hgdb_licgen/hgdw610/license -a 20231231 -i fulluse -M 52:54:00:fe:79:25<br>
        （4）生成license，所有参数：
        ./hgdw_licgen -v 6.1.0 -m o -b A123456789123456 -u highgo -f /opt/hgdb_licgen/hgdw610/license -a 20231231 -i fulluse -s 100 -M 52:54:00:fe:79:25<br>
        <br>
        查看工具：hg_lic：<br>
        -c  -F LICENSEPATH。查看到license的ID、版本号、状态、开放功能、证书类型、支持的集群规模、产品名称、产品版本号、到期时间等信息<br>
        示例：<br>
        (1) 查看license<br>
        ./hg_lic -c -F /opt/hgdb_licgen/hgdw610/license/XCzgFZIpWYQhzqsL94NRKCs5/hgdw_0_o.lic<br>
        <br>
      </el-card>
    </el-row>
  </div>
</template>
<script>
import {generatorHGDW610} from "@/api/medium/license";

export default {
  dicts: ["medium_lic_db_auth_type","medium_lic_purposes","medium_db_version"],
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
        dbVersion: '',
        segments:2,
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

        dbVersion: [],
      },
    }
  },
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return

        generatorHGDW610(this.formData).then(response => {
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
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}
</script>
<style>
</style>
