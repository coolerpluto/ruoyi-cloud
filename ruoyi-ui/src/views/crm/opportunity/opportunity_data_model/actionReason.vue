<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3>
    <el-divider/>
    <el-form ref="actionReasonForm" :model="actionReasonForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-checkbox-group v-model="actionReasonForm.reasons.propertyVal" @change="changeReasonSelect">
          <el-col v-for="item in reasonsList" :key="item.dictCode" :span="8">
            <el-checkbox :label="item.dictValue">
              {{ item.dictLabel }}
            </el-checkbox>
          </el-col>
        </el-checkbox-group>
      </el-row>
      <div id="actionReasonVerify" align="center" v-if="flag.actionReasonVerify">最少选择3个选项</div>
    </el-form>
  </div>
</template>

<script>
import {getDicts as getDicts} from '@/api/system/dict/data'
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo"

export default {
  name: "actionReason",
  props: {
    stageShow: {
      type: Number,
      default: 5
    },
    stageTitle: {
      type: String,
      default: '原因分析'
    },
    reasonDictCode: {
      type: String,
      default: 'crm_opportunity_finish_reason'
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
      actionReasonForm: {
        reasons: {
          propertyVal:[]
        },
      },
      actionReasonModified: {},
      actionReasonOriginBak: {},
      //标识集合
      flag: {
        actionReasonLoading: false,
        actionReasonVerify: false,
      },
      reasonsList: [],
    }
  },
  created() {
    this.initReasonList();
    this.initActionReason();
  },
  methods: {
    initReasonList() {
      getDicts(this.reasonDictCode).then(res => {
        this.reasonsList = res.data;
      }).catch(error => {
        this.reasonsList = []
      })
    },
    changeReasonSelect(obj) {
      console.log(obj)
      //console.log(this.actionReasonForm)
    },
    initActionReason() {
      var _this = this;
      this.flag.actionReasonLoading = true;
      this.getProperties(function () {
        //console.log("actionReasonForm:",_this.actionReasonForm)
        _this.flag.actionReasonLoading = false;
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
          propertyKeys: ["reasons"],
          model: "reasons"+this.stageShow,
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.actionReasonForm = response.data;
        this.actionReasonForm["reasons"].propertyVal = this.actionReasonForm["reasons"].propertyVal?this.actionReasonForm["reasons"].propertyVal.split(","):[]
        this.actionReasonOriginBak = JSON.parse(JSON.stringify(this.actionReasonForm))
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
      // if (this.actionReasonForm["reasons"].propertyVal.length<3){
      //   this.flag.actionReasonVerify = true
      //   flag = false
      // }
      this.$refs["actionReasonForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.actionReasonForm,//最新展示数据
        modifyedData: this.actionReasonModified,//改动部分
        originData: this.actionReasonOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#actionReasonVerify{
  color: #ff4949;
    font-size: 12px;
    line-height: 3;
}
</style>
