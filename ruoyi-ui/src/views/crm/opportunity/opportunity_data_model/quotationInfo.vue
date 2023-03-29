<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>产品以及报价</h3><el-divider />
    <el-form ref="quotationInfoForm" v-loading="flag.quotationLoading" :model="quotationInfoForm" size="medium"
      label-width="220px">
      <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加报价</el-button>
      <el-table :data="quotationInfoForm.quotationInfo">
        <el-table-column label="产品名称" align="center" prop="quotationMap.productName" :show-overflow-tooltip="true" />
        <el-table-column label="大版本" align="center" prop="quotationMap.productVersion" :show-overflow-tooltip="true" />
        <el-table-column label="小版本" align="center" prop="quotationMap.productSecVersion" :show-overflow-tooltip="true" />
        <el-table-column label="数量" align="center" prop="quotationMap.productCount" :show-overflow-tooltip="true" />
        <el-table-column label="单价" align="center" prop="quotationMap.productPrice" :show-overflow-tooltip="true" />
        <el-table-column label="总价" align="center" prop="quotationMap.productTotalPrice" :show-overflow-tooltip="true" />
        <el-table-column label="报价阶段" align="center" prop="quotationMap.quotationStage" :show-overflow-tooltip="true" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.quotationMap.quotationStage"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editQuotation(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeQuotation(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div id="quotationVerify" align="center" v-if="flag.quotationVerify">当前阶段最少有一条报价</div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="quotationDialog.title" :visible.sync="quotationDialog.open" width="1000px" append-to-body>
      <el-form ref="quotationDialog.form" :model="quotationDialog.form" :rules="quotationDialog.rules"
        label-width="100px">
        <el-row>
          <el-col :span="8"></el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogForm">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getOppQuotationInfo } from "@/api/crm/oppUnitedInfo"
export default {
  name: "quotationInfo",
  dicts:['crm_opportunity_status'],
  props: {
    stageShow: {
      type: Number,
      required: true
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    },
  },
  data() {
    return {
      quotationInfoForm: { quotationInfo: [] },
      quotationInfoModified: {},
      quotationInfoOriginBak: {},
      flag: {
        quotationLoading: false,
        quotationVerify: false,
      },
      //组件弹框承载
      quotationDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {},
      },
    }
  },
  created() {
    this.initQuotationInfo()
  },
  methods: {
    initQuotationInfo() {
      var _this = this
      //console.log("advancesInfoForm:",_this.oppdata)
      // 开启遮盖层
      this.flag.quotationLoading = true;
      this.getOppQuotationInfo(function () {
        //console.log("quotationInfoForm:",_this.quotationInfoForm)
        _this.flag.quotationLoading = false;
      })
    },
    getOppQuotationInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppQuotationInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.quotationInfoForm.quotationInfo = response.data;
        this.quotationInfoForm.quotationInfo.forEach(e => {
          e.quotationMap = JSON.parse(e.quotationJson)
        })
        this.quotationInfoOriginBak = JSON.parse(JSON.stringify(this.quotationInfoForm))

        if (typeof func == 'function') {
          func();
        }
      })
    },

    removeQuotation(row) {

    },
    editQuotation(row) {
      this.quotationDialog.open = true;
      this.quotationDialog.title = "修改报价信息";
    },
    openDialog() {
      this.quotationDialog.open = true;
      this.quotationDialog.title = "添加报价新信息";
    },
    submitDialogForm() {
      this.quotationDialog.open = false;
    },
    cancelDialog() {
      this.quotationDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      // 暂时校验必填
      if (!this.quotationInfoForm.quotationInfo || this.quotationInfoForm.quotationInfo.length == 0) {
        this.flag.quotationVerify = true
        flag = false
      }
      this.$refs["quotationInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.quotationInfoForm,//最新展示数据
        modifyedData: this.quotationInfoModified,//改动部分
        originData: this.quotationInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#quotationVerify {
  color: #ff4949;
  font-size: 12px;
  line-height: 3;
}
</style>
