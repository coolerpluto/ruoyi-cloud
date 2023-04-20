<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3>
    <el-divider />
    <el-form ref="winningBiddingForm" v-loading="flag.winningBiddingLoading" :rules="rules" :model="winningBiddingForm"
      size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="中标公司名称" prop="winBiddingCompetitorId.propertyVal">
            <el-select v-model="winningBiddingForm.winBiddingCompetitorId.propertyVal" filterable placeholder="请选择中标公司"
              @change="changeWinBidding" @visible-change="selectInit">
              <el-option v-for="item in companyOptions" :key="item.id" :label="item.competitorName"
                :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="中标公司报价" prop="winBiddingTenderPrice.propertyVal">
            <el-input v-model="winningBiddingForm.winBiddingTenderPrice.propertyVal" type="number"
              placeholder="请输入中标公司报价金额">
            </el-input>
            {{ convertCurrencyDX(winningBiddingForm.winBiddingTenderPrice.propertyVal || 0) }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="中标公司报价总价" prop="winBiddingTotalPrice.propertyVal">
            <el-input v-model="winningBiddingForm.winBiddingTotalPrice.propertyVal" type="number"
              placeholder="请输入中标公司报价总价金额">
            </el-input>
            {{ convertCurrencyDX(winningBiddingForm.winBiddingTotalPrice.propertyVal || 0) }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo"
import { mergeRecursive } from "@/utils/ruoyi";

export default {
  name: "winningBidding",
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '中标结论'
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  dicts: ['crm_fund_source_type'],
  data() {
    return {
      propertyKeys: [
        "winBiddingCompetitorId",
        "winBiddingCompanyId", "winBiddingCompanyName",
        "winBiddingTenderPrice", "winBiddingTotalPrice"
      ],
      winningBiddingForm: {
        "winBiddingCompetitorId": { model: "winning_bidder", propertyLable: "中标公司竞争标识" },
        "winBiddingCompanyId": { model: "winning_bidder", propertyLable: "中标公司ID" },
        "winBiddingCompanyName": { model: "winning_bidder", propertyLable: "中标公司名称" },
        "winBiddingTenderPrice": { model: "winning_bidder", propertyLable: "中标公司报价投标单价" },
        "winBiddingTotalPrice": { model: "winning_bidder", propertyLable: "中标公司报价总价" }
      },
      winningBiddingModified: {},
      winningBiddingOriginBak: {},
      //标识集合
      flag: {
        winningBiddingLoading: false,
        winningBiddingVerify: false,
      },
      rules: {
        "winBiddingCompetitorId": {
          propertyVal: [
            { required: true, message: "未选择 中标公司名称", trigger: "blur" }
          ],
        },
      },
      companyOptions: [],
    }
  },
  watch: {
      firstName(newName, oldName) {
        this.fullName = newName + ' ' + this.lastName;
      }
  },
  created() {
    this.initWinningBidding();
  },
  methods: {
    initWinningBidding() {
      var _this = this;
      this.flag.winningBiddingLoading = true;
      this.getProperties(function () {
        //console.log("winningBiddingForm:",_this.winningBiddingForm)
        _this.flag.winningBiddingLoading = false;
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
          model: "winning_bidder",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.winningBiddingForm = response.data;
        this.companyOptions = [{ id: this.winningBiddingForm.winBiddingCompetitorId.propertyVal,
           competitorName: this.winningBiddingForm.winBiddingCompanyName.propertyVal, competitorId: this.winningBiddingForm.winBiddingCompanyId.propertyVal }];
        this.winningBiddingOriginBak = JSON.parse(JSON.stringify(this.winningBiddingForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    changeWinBidding(competitorId) {
      const arr1 = this.companyOptions.find(item => item.id == competitorId)
      console.log(arr1)
      this.winningBiddingForm.winBiddingTenderPrice.propertyVal = arr1.tenderPrice;
      this.winningBiddingForm.winBiddingTotalPrice.propertyVal = arr1.tenderTotalPrice;
      this.winningBiddingForm.winBiddingCompanyId.propertyVal = arr1.competitorId;
      //this.winningBiddingForm.winBiddingCompetitorId.propertyVal = arr1.id;
      this.winningBiddingForm.winBiddingCompanyName.propertyVal = arr1.competitorName;
    },
    selectInit(action) {
      if (!action) {
        return
      }
      this.companyOptions = [{ id: 0, competitorName: "我司", competitorId: 0 }].concat(this.$parent.$refs.competitorInfo.competitorInfoForm.competitorInfo);
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["winningBiddingForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      Object.keys(this.winningBiddingForm).forEach(key => {
        this.winningBiddingForm[key]["model"]="winning_bidder"
      })
      if (Object.keys(this.winningBiddingOriginBak).length == 0) {
        this.winningBiddingModified = JSON.parse(JSON.stringify(this.winningBiddingForm))
        return;
      }
      this.winningBiddingModified = {}
      Object.keys(this.winningBiddingForm).forEach(key => {
        if (this.winningBiddingOriginBak[key].propertyVal != this.winningBiddingForm[key].propertyVal) {
          this.winningBiddingModified[key] = this.winningBiddingForm[key]
        }
      })
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation()
      return {
        currentData: this.winningBiddingForm,//最新展示数据
        modifyedData: this.winningBiddingModified,//改动部分
        originData: this.winningBiddingOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
