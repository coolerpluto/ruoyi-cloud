<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>商机支持人(公司内部人员)</h3>
    <el-divider />
    <el-form ref="keyStandByForm" :model="keyStandByForm" size="medium" v-loading="flag.keyStandByLoading"
      label-width="220px">
      <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加新支持人</el-button>
      <el-table :data="keyStandByForm.keyStandBy">
        <el-table-column align="center" fixed prop="supportVal" :show-overflow-tooltip="true" width="500">
          <template slot="header" slot-scope="scope">
            <label>支持内容<span style="color: red;">(必须注明和我方业务相关信息)</span></label>
          </template>
        </el-table-column>
        <el-table-column label="支持人员" align="center" prop="supportName" :show-overflow-tooltip="true" />
        <el-table-column label="介入阶段" align="center" prop="opportunityStage" :show-overflow-tooltip="true" />
        <el-table-column label="填报人" align="center" prop="createBy" :show-overflow-tooltip="true" />
        <el-table-column label="填报时间" align="center" prop="createTime" :show-overflow-tooltip="true" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editStandBy(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeStandBy(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="keyStandByDialog.title" :visible.sync="keyStandByDialog.open" width="1000px" append-to-body>
      <el-form ref="keyStandByDialog.form" :model="keyStandByDialog.form" :rules="keyStandByDialog.rules"
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
import { getOppSupportInfo } from "@/api/crm/oppUnitedInfo"

export default {
  name: "keyStandBy",
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
      keyStandByForm: { keyStandBy: [] },
      keyStandByModified: {},
      keyStandByOriginBak: {},
      flag: {
        keyStandByLoading: false,
        keyStandByNumVerify: false,
      },
      //组件弹框承载
      keyStandByDialog: {
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
    this.initKeyStandBy()
  },
  methods: {
    initKeyStandBy() {
      var _this = this
      //console.log("keyStandByForm:",_this.oppdata)
      // 开启遮盖层
      this.flag.keyStandByLoading = true;
      this.getOppSupportInfo(function () {
        _this.flag.keyStandByLoading = false;
      })
    },
    getOppSupportInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppSupportInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.keyStandByForm.keyStandBy = response.data;
        this.keyStandByOriginBak = JSON.parse(JSON.stringify(this.keyStandByForm))

        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },

    removeStandBy(row) {

    },
    editStandBy(row) {
      this.keyStandByDialog.open = true;
      this.keyStandByDialog.title = "修改进展信息";
    },
    openDialog() {
      this.keyStandByDialog.open = true;
      this.keyStandByDialog.title = "添加进展信息";
    },
    submitDialogForm() {
      this.keyStandByDialog.open = false;
    },
    cancelDialog() {
      this.keyStandByDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      // 暂时不校验必填
      // if (!this.keyStandByForm.keyStandBy || this.keyStandByForm.keyStandBy.length == 0) {
      //   this.flag.advancesNumVerify = true
      //   flag = false
      // }
      this.$refs["keyStandByForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.keyStandByForm,//最新展示数据
        modifyedData: this.keyStandByModified,//改动部分
        originData: this.keyStandByOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
