<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3>
    <el-divider />
    <el-form ref="winningBiddingForm" v-loading="flag.winningBiddingLoading" :rules="rules" :model="winningBiddingForm"
      size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="中标公司名称" prop="winBiddingCompanyName.propertyVal">
            <el-select v-model="winningBiddingForm.winBiddingCompanyName.propertyVal" filterable placeholder="请选择中标公司"
              clearable>
              <el-option v-for="item in dict.type.crm_fund_source_type" :key="item.value" :label="item.label"
                :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="中标公司报价" prop="winBiddingTenderPrice.propertyVal">
            <label>{{ winningBiddingForm.winBiddingTenderPrice.propertyVal }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="中标公司报价总价" prop="winBiddingTotalPrice.propertyVal">
            <label>{{ winningBiddingForm.winBiddingTotalPrice.propertyVal }}</label>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo"

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
        "winBiddingCompetitorId": {},
        "winBiddingCompanyId": {}, "winBiddingCompanyName": {},
        "winBiddingTenderPrice": {}, "winBiddingTotalPrice": {}
      },
      winningBiddingModified: {},
      winningBiddingOriginBak: {},
      //标识集合
      flag: {
        winningBiddingLoading: false,
        winningBiddingVerify: false,
      },
      rules: {
        "winBiddingCompanyName": {
          propertyVal: [
            { required: true, message: "未选择 中标公司名称", trigger: "blur" }
          ],
        },
      },
      companyOptions: [],
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
      // TODO 还需要获得竞标公司的列表 
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
    // 提供本组件的全部数据
    collectInfo() {
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
