<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>签约信息</h3><el-divider/>
    <el-form ref="signInfoForm" :rules="rules" v-loading="flag.signInfoLoading" :model="signInfoForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="签约状态" prop="signStatus.propertyVal">
            <el-radio v-model="signInfoForm.signStatus.propertyVal" label="Y">已签约</el-radio>
            <el-radio v-model="signInfoForm.signStatus.propertyVal" label="N">未签约</el-radio>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15" v-if="signInfoForm.signStatus.propertyVal == 'Y'">
        <el-col :span="8">
          <el-form-item label="签约渠道" prop="signChannel.propertyVal">
            <el-select v-model="signInfoForm.signChannel.propertyVal" filterable placeholder="请选择签约渠道" clearable>
              <el-option v-for="item in signChannelList" :key="item.id" :label="item.operationalName" :value="item.operationalId+''" width="300px">
                <span style="float: left">{{ item.operationalName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">isv:{{ item.isv }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="签约日期" prop="signDate.propertyVal">
            <el-date-picker
              v-model="signInfoForm.signDate.propertyVal"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15" v-if="signInfoForm.signStatus.propertyVal == 'Y'">
        <el-col :span="20">
          <el-form-item label="状态说明" prop="signRemark.propertyVal">
            <el-input v-model="signInfoForm.signRemark.propertyVal" type="textarea" resize="none" :rows="3"
                      placeholder="请输入项目总结经验,小于500个字" clearable>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { getPropertiesMap,getOppBaseInfo } from "@/api/crm/oppUnitedInfo"

export default {
  name: "signInfo",
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data(){
    return {
      propertyKeys: [
        "signStatus", "signChannel",
        "signDate", "signRemark"
      ],
      signInfoForm:{
        "signStatus":{}, "signChannel":{},
        "signDate":{}, "signRemark":{}
      },
      signInfoModified: {},
      signInfoOriginBak: {},
      //标识集合
      flag: {
        signInfoLoading: false,
        signInfoVerify: false,
      },
      rules: {
        "signStatus": {
          propertyVal: [
            { required: true, message: "未选择 签约状态", trigger: "blur" }
          ],
        },
        "signChannel": {
          propertyVal: [
            { required: true, message: "未选择 签约渠道", trigger: "blur" }
          ],
        },
        "signDate": {
          propertyVal: [
            { required: true, message: "未选择 签约日期", trigger: "blur" }
          ],
        },
        "signRemark": {
          propertyVal: [
            { required: true, message: "未填写 状态说明", trigger: "blur" }
          ],
        },
      },
      signChannelList:[],
    }
  },
  created() {
    this.initSignInfo();
  },
  methods: {
    initSignInfo() {
      var _this = this;
      this.flag.signInfoLoading = true;
      this.getOperationInfo(function(){
        _this.getProperties(function () {
          //console.log("signInfoForm:",_this.signInfoForm)
          _this.flag.signInfoLoading = false;
        })
      });      
    },
    //获取运营最新信息
    getOperationInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppBaseInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.signChannelList = response.data ? response.data.operations : [];
        if (typeof func == 'function') {
          func();
        }
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
          model: "sign_info",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.signInfoForm = response.data;
        this.signInfoOriginBak = JSON.parse(JSON.stringify(this.signInfoForm))
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
      console.log("5",this.signInfoForm)
      this.$refs["signInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.signInfoForm,//最新展示数据
        modifyedData: this.signInfoModified,//改动部分
        originData: this.signInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>

</style>
