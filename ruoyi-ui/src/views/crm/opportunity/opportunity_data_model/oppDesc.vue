<template>
  <div class="app-container" id="oppDesc" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3>
    <el-divider />
    <el-form ref="oppDescForm" :model="oppDescForm" :rules="rules" size="medium" label-width="220px"
      v-loading="flag.oppDescLoading">
      <div v-if="[1, 2, 3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="客户使用该系统的原因" prop="useSystemReason.propertyVal">
              <el-input v-model="oppDescForm.useSystemReason.propertyVal" type="textarea" resize="none" :rows="3"
                placeholder="请输入客户使用该系统的主要痛点以及原因说明" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="客户要求系统正式上线时间" prop="systemPublicTime.propertyVal">
              <el-date-picker v-model="oppDescForm.systemPublicTime.propertyVal" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户召集ISV、SI启动项目时间" prop="startProjectTime.propertyVal">
              <el-date-picker v-model="oppDescForm.startProjectTime.propertyVal" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="系统项目总预算" prop="projectBudget.propertyVal">
              <el-input v-model="oppDescForm.projectBudget.propertyVal" type="number" placeholder="请输入系统项目总预算金额"
                clearable>
              </el-input>
              {{ convertCurrencyDX(oppDescForm.projectBudget.propertyVal || 0) }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目中DB预算" prop="projectDbBdget.propertyVal">
              <el-input v-model="oppDescForm.projectDbBdget.propertyVal" type="number" placeholder="请输入项目中DB预算金额"
                clearable>
              </el-input>
              {{ convertCurrencyDX(oppDescForm.projectDbBdget.propertyVal || 0) }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="对应我司产品" prop="matchProduct.propertyVal">
              <el-select v-model="oppDescForm.matchProduct.propertyVal" filterable placeholder="请选择对应我司产品" clearable>
                <el-option v-for="item in dict.type.crm_opportunity_main_product" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[2, 3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="有无入围要求" prop="chosedFlag.propertyVal">
              <el-radio v-model="oppDescForm.chosedFlag.propertyVal" label="Y">有</el-radio>
              <el-radio v-model="oppDescForm.chosedFlag.propertyVal" label="N">无</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否批准立项" prop="approveFlag.propertyVal">
              <el-radio v-model="oppDescForm.approveFlag.propertyVal" label="Y">是</el-radio>
              <el-radio v-model="oppDescForm.approveFlag.propertyVal" label="N">否</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资金是否到位" prop="isFundOk.propertyVal">
              <el-radio v-model="oppDescForm.isFundOk.propertyVal" label="Y">是</el-radio>
              <el-radio v-model="oppDescForm.isFundOk.propertyVal" label="N">否</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="预算来源" prop="budgetSource.propertyVal">
              <el-select v-model="oppDescForm.budgetSource.propertyVal" filterable placeholder="请选择预算来源" clearable>
                <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计投标日期" prop="preTenderDate.propertyVal">
              <el-date-picker v-model="oppDescForm.preTenderDate.propertyVal" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计签单期日" prop="preSignDate.propertyVal">
              <el-date-picker v-model="oppDescForm.preSignDate.propertyVal" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="预计招标类型" prop="preInviteTenderType.propertyVal">
              <el-select v-model="oppDescForm.preInviteTenderType.propertyVal" filterable placeholder="请选择招标类型" clearable>
                <el-option v-for="item in dict.type.crm_opportunity_bidding_type" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计合同金额" prop="preContractVal.propertyVal">
              <el-input v-model="oppDescForm.preContractVal.propertyVal" placeholder="请输入预计合同金额" clearable>
              </el-input>
              {{ convertCurrencyDX(oppDescForm.preContractVal.propertyVal || 0) }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="商机背景目的简述" prop="opportunityGoalDesc.propertyVal">
              <el-input v-model="oppDescForm.opportunityGoalDesc.propertyVal" type="textarea" resize="none" :rows="2"
                placeholder="请输入商机背景目的简述" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="核心业务说明" prop="mainBusinessDesc.propertyVal">
              <el-input v-model="oppDescForm.mainBusinessDesc.propertyVal" type="textarea" resize="none" :rows="2"
                placeholder="请输入客户核心业务说明" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[3].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="用户关注点" prop="custInterest.propertyVal">
              <el-select v-model="oppDescForm.custInterest.propertyVal" filterable placeholder="请选择用户关注点" clearable>
                <el-option v-for="item in dict.type.crm_oppo_user_concerns" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[4].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="预计投标日期" prop="preTenderDate.propertyVal">
              <el-date-picker v-model="oppDescForm.preTenderDate.propertyVal" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计签单日期" prop="preSignDate.propertyVal">
              <el-date-picker v-model="oppDescForm.preSignDate.propertyVal" type="date" value-format="yyyy-MM-dd"
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
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo"

export default {
  name: "oppDesc",
  dicts: ['crm_fund_source_type', 'crm_opportunity_main_product', 'crm_opportunity_bidding_type','crm_oppo_user_concerns'],
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '商机描述'
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
        "useSystemReason", "systemPublicTime", "startProjectTime",
        "projectBudget", "projectDbBdget", "matchProduct",

        "chosedFlag", "approveFlag", "isFundOk", "budgetSource",
        "preTenderDate", "preSignDate", "preInviteTenderType",
        "preContractVal", "opportunityGoalDesc", "mainBusinessDesc",

        "custInterest"
      ],
      oppDescForm: {
        "useSystemReason": { model: "oppo_desc", propertyLable: "客户使用该系统的原因" },
        "systemPublicTime": { model: "oppo_desc", propertyLable: "客户要求系统正式上线时间" },
        "startProjectTime": { model: "oppo_desc", propertyLable: "客户召集ISV、SI启动项目时间" },
        "projectBudget": { model: "oppo_desc", propertyLable: "系统项目总预算" },
        "projectDbBdget": { model: "oppo_desc", propertyLable: "项目中DB预算" },
        "matchProduct": { model: "oppo_desc", propertyLable: "对应我司产品" },

        "chosedFlag": { model: "oppo_desc", propertyLable: "有无入围要求" },
        "approveFlag": { model: "oppo_desc", propertyLable: "是否批准立项" },
        "isFundOk": { model: "oppo_desc", propertyLable: "资金是否到位" },
        "budgetSource": { model: "oppo_desc", propertyLable: "预算来源" },
        "preTenderDate": { model: "oppo_desc", propertyLable: "预计投标日期" },
        "preSignDate": { model: "oppo_desc", propertyLable: "预计签单期日" },
        "preInviteTenderType": { model: "oppo_desc", propertyLable: "预计招标类型" },

        "preContractVal": { model: "oppo_desc", propertyLable: "预计合同金额" },
        "opportunityGoalDesc": { model: "oppo_desc", propertyLable: "商机背景目的简述" },
        "mainBusinessDesc": { model: "oppo_desc", propertyLable: "核心业务说明" },

        "custInterest": { model: "oppo_desc", propertyLable: "用户关注点" }
      },
      oppDescModified: {},
      oppDescOriginBak: {},
      //标识集合
      flag: {
        oppDescLoading: false,
        oppDescVerify: false,
      },
      //自定义下拉框集合
      selectOptions: {

      },
      rules: {
        "useSystemReason": {
          propertyVal: [
            { required: true, message: "未填写 客户使用该系统的原因", trigger: "blur" }
          ],
        },
        "systemPublicTime": {
          propertyVal: [
            { required: true, message: "未选择 系统正式上线时间", trigger: "blur" }
          ],
        },
        "startProjectTime": {
          propertyVal: [
            { required: true, message: "未选择 启动项目时间", trigger: "blur" }
          ],
        },
        "projectBudget": {
          propertyVal: [
            { required: true, message: "未填写 系统项目总预算", trigger: "blur" }
          ],
        },
        "projectDbBdget": {
          propertyVal: [
            { required: true, message: "未填写 项目中DB预算", trigger: "blur" }
          ],
        },
        "matchProduct": {
          propertyVal: [
            { required: true, message: "未选择 对应我司产品", trigger: "change" }
          ],
        },

        "chosedFlag": {
          propertyVal: [
            { required: true, message: "未选择 入围要求", trigger: "change" }
          ],
        }, "approveFlag": {
          propertyVal: [
            { required: true, message: "未选择 批准立项", trigger: "change" }
          ],
        }, "isFundOk": {
          propertyVal: [
            { required: true, message: "未选择 资金是否到位", trigger: "change" }
          ],
        }, "budgetSource": {
          propertyVal: [
            { required: true, message: "未选择 预算来源", trigger: "change" }
          ],
        },
        "preTenderDate": {
          propertyVal: [
            { required: true, message: "未选择 预计投标日期", trigger: "blur" }
          ],
        }, "preSignDate": {
          propertyVal: [
            { required: true, message: "未选择 预计签单期日", trigger: "blur" }
          ],
        }, "preInviteTenderType": {
          propertyVal: [
            { required: true, message: "未选择 预计招标类型", trigger: "change" }
          ],
        },
        "preContractVal": {
          propertyVal: [
            { required: true, message: "未填写 预计合同金额", trigger: "blur" }
          ],
        },
        "opportunityGoalDesc": {
          propertyVal: [
            { required: true, message: "未填写 背景目的简述", trigger: "blur" }
          ],
        },
        "mainBusinessDesc": {
          propertyVal: [
            { required: true, message: "未填写 核心业务说明", trigger: "blur" }
          ],
        },

        "custInterest": {
          propertyVal: [
            { required: true, message: "未填选择 用户关注点", trigger: "change" }
          ],
        },
      },
    }
  },
  created() {
    this.initOppDesc();
  },
  methods: {
    initOppDesc() {
      var _this = this;
      this.flag.oppDescLoading = true;
      this.getProperties(function () {
        //console.log("oppDescForm:",_this.oppDescForm)
        _this.flag.oppDescLoading = false;
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
          model: "oppo_desc",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.oppDescForm = response.data;
        this.oppDescOriginBak = JSON.parse(JSON.stringify(this.oppDescForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["oppDescForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      Object.keys(this.oppDescForm).forEach(key => {
        this.oppDescForm[key]["model"]="oppo_desc"
      })
      if (Object.keys(this.oppDescOriginBak).length == 0) {
        this.oppDescModified = JSON.parse(JSON.stringify(this.oppDescForm))
        return;
      }
      this.oppDescModified = {}
      Object.keys(this.oppDescForm).forEach(key => {
        if (this.oppDescOriginBak[key].propertyVal != this.oppDescForm[key].propertyVal) {
          this.oppDescModified[key] = this.oppDescForm[key]
        }
      })
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation()
      return {
        currentData: this.oppDescForm,//最新展示数据
        modifyedData: this.oppDescModified,//改动部分
        originData: this.oppDescOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
