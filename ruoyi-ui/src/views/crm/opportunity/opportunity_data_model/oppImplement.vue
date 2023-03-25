<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3>
    <el-divider />
    <el-form ref="oppImplementForm" :rules="rules" v-loading="flag.oppImplementLoading" :model="oppImplementForm"
      size="medium" label-width="350px">
      <div v-if="[3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="了解招标方案内容及主要评分项(废标项,加分项等)" prop="knowMainMark.propertyVal">
              <!-- <el-radio v-model="oppImplementForm.knowMainMark.propertyVal" label="Y">已了解</el-radio>
              <el-radio v-model="oppImplementForm.knowMainMark.propertyVal" label="N">不了解</el-radio> -->
              <el-radio-group v-model="oppImplementForm.knowMainMark.propertyVal" @change="changeOppImplementHandler">
                <el-radio label="Y">已了解</el-radio>
                <el-radio label="N">不了解</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="公司产品参数是否写入招标方案" prop="companyParamsIn.propertyVal">
              <!-- <el-radio v-model="oppImplementForm.companyParamsIn.propertyVal" label="Y">已写入</el-radio>
              <el-radio v-model="oppImplementForm.companyParamsIn.propertyVal" label="N">未写入</el-radio> -->
              <el-radio-group v-model="oppImplementForm.companyParamsIn.propertyVal" @change="changeOppImplementHandler">
                <el-radio label="Y">已写入</el-radio>
                <el-radio label="N">未写入</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="了解用户招标的决策流程" prop="knowDecideFlow.propertyVal">
              <!-- <el-radio v-model="oppImplementForm.knowDecideFlow.propertyVal" label="Y">已了解</el-radio>
              <el-radio v-model="oppImplementForm.knowDecideFlow.propertyVal" label="N">不了解</el-radio> -->
              <el-radio-group v-model="oppImplementForm.knowDecideFlow.propertyVal" @change="changeOppImplementHandler">
                <el-radio label="Y">已了解</el-radio>
                <el-radio label="N">不了解</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="可以掌控招标流程各环节" prop="canHoldAllProcess.propertyVal">
              <!-- <el-radio v-model="oppImplementForm.canHoldAllProcess.propertyVal" label="Y">可以</el-radio>
              <el-radio v-model="oppImplementForm.canHoldAllProcess.propertyVal" label="N">不可以</el-radio> -->
              <el-radio-group v-model="oppImplementForm.canHoldAllProcess.propertyVal"
                @change="changeOppImplementHandler">
                <el-radio label="Y">可以</el-radio>
                <el-radio label="N">不可以</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="合同落单成功率" prop="contractSuccessRate.propertyVal">
              <label>{{ oppImplementForm.contractSuccessRate.propertyVal }}</label>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo"

export default {
  name: "oppImplement",
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '落实机会关键单点'
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      propertyKeys: [
        "knowMainMark", "companyParamsIn",
        "knowDecideFlow", "canHoldAllProcess",
        "contractSuccessRate"
      ],
      oppImplementForm: {
        "knowMainMark": {}, "companyParamsIn": {},
        "knowDecideFlow": {}, "canHoldAllProcess": {},
        "contractSuccessRate": {}
      },
      oppImplementModified: {},
      oppImplementOriginBak: {},
      //标识集合
      flag: {
        oppImplementLoading: false,
        oppImplementVerify: false,
      },
      rules: {
        "knowMainMark": {
          propertyVal: [
            { required: true, message: "未选择 是否了解招标方案内容及主要评分项", trigger: "blur" }
          ],
        },
        "companyParamsIn": {
          propertyVal: [
            { required: true, message: "未选择 公司产品参数是否写入招标方案", trigger: "blur" }
          ],
        },
        "knowDecideFlow": {
          propertyVal: [
            { required: true, message: "未选择 是否了解用户招标的决策流程", trigger: "blur" }
          ],
        },
        "canHoldAllProcess": {
          propertyVal: [
            { required: true, message: "未选择 是否可以掌控招标流程各环节", trigger: "blur" }
          ],
        },
      }
    }
  },
  created() {
    this.initSummaryInfo();
  },
  methods: {
    initSummaryInfo() {
      var _this = this;
      this.flag.oppImplementLoading = true;
      this.getProperties(function () {
        //console.log("oppImplementForm:",_this.oppImplementForm)
        _this.flag.oppImplementLoading = false;
      })
    },
    // 商机属性查询
    getProperties(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      let req = {
        code: this.oppdata.opportunityCode,
        params: {
          propertyKeys: this.propertyKeys,
          model: "impl_key_points",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.oppImplementForm = response.data;
        this.oppImplementOriginBak = JSON.parse(JSON.stringify(this.oppImplementForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    changeOppImplementHandler(value) {
      var numY = 0
      numY = numY + (this.oppImplementForm.knowMainMark.propertyVal == "Y" ? 1 : 0)
      numY = numY + (this.oppImplementForm.companyParamsIn.propertyVal == "Y" ? 1 : 0)
      numY = numY + (this.oppImplementForm.knowDecideFlow.propertyVal == "Y" ? 1 : 0)
      numY = numY + (this.oppImplementForm.canHoldAllProcess.propertyVal == "Y" ? 1 : 0)
      switch (numY) {
        case 1:
          this.oppImplementForm.contractSuccessRate.propertyVal = "10 %"
          break;
        case 2:
          this.oppImplementForm.contractSuccessRate.propertyVal = "20 %"
          break;
        case 3:
          this.oppImplementForm.contractSuccessRate.propertyVal = "50 %"
          break;
        case 4:
          this.oppImplementForm.contractSuccessRate.propertyVal = "80 %"
          break;
        default:
          this.oppImplementForm.contractSuccessRate.propertyVal = "0 %"
      }
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["oppImplementForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.oppImplementForm,//最新展示数据
        modifyedData: this.oppImplementModified,//改动部分
        originData: this.oppImplementOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
