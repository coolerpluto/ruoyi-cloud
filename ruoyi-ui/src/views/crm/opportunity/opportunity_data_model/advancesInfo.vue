<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>商机进展</h3>
    <el-divider />
    <el-form ref="advancesInfoForm" :model="advancesInfoForm" size="medium" v-loading="flag.advancesInfoLoading"
      label-width="220px">
      <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加新进展</el-button>
      <el-table :data="advancesInfoForm.advancesInfo">
        <el-table-column align="center" fixed prop="advances" :show-overflow-tooltip="true" width="500">
          <template slot="header" slot-scope="scope">
            <label placeholder="自动生产商机编码">进展内容<span style="color: red;">(必须注明和我方业务相关瓶颈、建议和意见)</span></label>
          </template>
        </el-table-column>
        <el-table-column label="进展阶段" align="center" prop="stage" :show-overflow-tooltip="true" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.stage"/>
          </template>
        </el-table-column>
        <el-table-column label="进展日期" align="center" prop="advancesDate" :show-overflow-tooltip="true" />
        <el-table-column label="填报人" align="center" prop="creator" :show-overflow-tooltip="true" />
        <el-table-column label="填报时间" align="center" prop="createTime" :show-overflow-tooltip="true" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editAdvances(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeAdvances(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div id="advancesNumVerify" align="center" v-if="flag.advancesNumVerify">最少写一条进展</div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="advancesDialog.title" :visible.sync="advancesDialog.open" width="1000px" append-to-body>
      <el-form ref="advancesDialog.form" :model="advancesDialog.form" :rules="advancesDialog.rules" label-width="100px">
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
import { getOppAdvancesInfo } from "@/api/crm/oppUnitedInfo"
export default {
  name: "advancesInfo",
  dicts:["crm_opportunity_status"],
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
      advancesInfoForm: { advancesInfo: [], },
      advancesInfoModified: {},
      advancesInfoOriginBak: {},
      flag: {
        advancesInfoLoading: false,
        advancesNumVerify: false,
      },
      //组件弹框承载
      advancesDialog: {
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
    this.initAdvancesInfo()
  },
  methods: {
    initAdvancesInfo() {
      var _this = this
      //console.log("advancesInfoForm:",_this.oppdata)
      // 开启遮盖层
      this.flag.advancesInfoLoading = true;
      this.getOppAdvancesInfo(function () {
        _this.flag.advancesInfoLoading = false;
      })
    },
    getOppAdvancesInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppAdvancesInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.advancesInfoForm.advancesInfo = response.data;
        this.advancesInfoOriginBak = JSON.parse(JSON.stringify(this.advancesInfoForm))

        if (typeof func == 'function') {
          func();
        }
      })
    },

    removeAdvances(row) {

    },
    editAdvances(row) {
      this.advancesDialog.open = true;
      this.advancesDialog.title = "修改进展信息";
    },
    openDialog() {
      this.advancesDialog.open = true;
      this.advancesDialog.title = "添加进展信息";
    },
    submitDialogForm() {
      this.advancesDialog.open = false;
    },
    cancelDialog() {
      this.advancesDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      // 暂时不校验必填
      // if (!this.advancesInfoForm.advancesInfo || this.advancesInfoForm.advancesInfo.length == 0) {
      //   this.flag.advancesNumVerify = true
      //   flag = false
      // }
      this.$refs["advancesInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.advancesInfoForm,//最新展示数据
        modifyedData: this.advancesInfoModified,//改动部分
        originData: this.advancesInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#advancesNumVerify {
  color: #ff4949;
  font-size: 12px;
  line-height: 3;
}
</style>
