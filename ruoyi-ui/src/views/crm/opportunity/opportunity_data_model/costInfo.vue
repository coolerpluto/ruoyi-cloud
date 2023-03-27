<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>商机花费</h3>
    <el-divider />
    <el-form ref="costInfoForm" :model="costInfoForm" size="medium" label-width="220px"
      v-loading="flag.contactInfoLoading">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="累记支出：">
            <label>{{ sumAmount.toFixed(2) }}</label>
          </el-form-item>
        </el-col>
        <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加新的支出</el-button>
      </el-row>
      <el-table :data="costInfoForm.costInfos">
        <el-table-column label="发生日期" align="center" prop="useTime" :show-overflow-tooltip="true" />
        <el-table-column label="投入事由" align="center" prop="reason" :show-overflow-tooltip="true" />
        <el-table-column label="投入金额(元)" align="center" prop="cost" :show-overflow-tooltip="true" />
        <el-table-column label="投入人员" align="center" prop="inputUserName" :show-overflow-tooltip="true" />
        <el-table-column label="填写人员" align="center" prop="creatorBy" :show-overflow-tooltip="true" />
        <el-table-column label="填写时间" align="center" prop="creatorTime" :show-overflow-tooltip="true" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editCostInfo(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeCostInfo(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="costInfosDialog.title" :visible.sync="costInfosDialog.open" width="1000px" append-to-body>
      <el-form ref="costInfosDialog.form" :model="costInfosDialog.form" :rules="costInfosDialog.rules"
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
import {
  getOppCostInfo
} from "@/api/crm/oppUnitedInfo"

export default {
  name: "costInfo",
  props: {
    stageShow: {
      type: Number,
      default: 0
    },
    stageTitle: {
      type: String,
      default: '商机花费支出'
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
      costInfoForm: {
        costInfos: [],
        costTotal: {}
      },
      costInfoModified: {},
      costInfoOriginBak: {},
      flag: {
        contactInfoLoading: false,
        contactNumVerify: false,
      },
      //组件弹框承载
      costInfosDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {},
      },
    }
  },
  computed: {
    sumAmount() {
      var total = 0
      this.costInfoForm.costInfos.forEach(e => {
        total = total + e.cost
      })
      return total;
    },
  },
  created() {
    this.initCostInfos();
  },
  methods: {
    initCostInfos() {
      var _this = this
      //console.log("CostInfos:",_this.oppdata)
      // 开启遮盖层
      this.flag.contactInfoLoading = true;
      this.getOppCostInfo(function () {
        _this.flag.contactInfoLoading = false;
      })
    },
    getOppCostInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppCostInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.costInfoForm.costInfos = response.data;
        this.costInfoOriginBak = JSON.parse(JSON.stringify(this.costInfoForm))
        if (typeof func == 'function') {
          func();
        }
      })
    },

    removeCostInfo(row) {

    },
    editCostInfo(row) {
      this.costInfosDialog.open = true;
      this.costInfosDialog.title = "修改花费信息";
    },
    openDialog() {
      this.costInfosDialog.open = true;
      this.costInfosDialog.title = "添加新的支出信息";
    },
    submitDialogForm() {
      this.costInfosDialog.open = false;
    },
    cancelDialog() {
      this.costInfosDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      if (!this.costInfoForm.costInfos || this.costInfoForm.costInfos.length == 0) {
        this.flag.contactNumVerify = true
        flag = false
      }
      this.$refs["costInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.costInfoForm,//最新展示数据
        modifyedData: this.costInfoModified,//改动部分
        originData: this.costInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#contactNumVerify {
  color: #ff4949;
  font-size: 12px;
  line-height: 3;
}
</style>
