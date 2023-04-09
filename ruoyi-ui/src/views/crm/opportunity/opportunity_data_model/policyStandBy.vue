<template>
  <div class="app-container" id="policyStandBy" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>政策支持信息</h3>
    <el-divider />
    <el-form ref="policyStandByForm" :model="policyStandByForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="10">
          <el-form-item label="政策文件" prop="policyName.propertyVal">
            <el-input v-model="policyStandByForm.policyName.propertyVal" placeholder="请选择政策文件"
              @clear="policyStandByForm.policyReleaseYear.propertyVal = ''" clearable>
              <el-button slot="append" icon="el-icon-search" @click="openDialog">选择</el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="政策年分" prop="policyReleaseYear.propertyVal">
            <el-input v-model="policyStandByForm.policyReleaseYear.propertyVal" readonly placeholder="政策发布年份" clearable>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="policyInfoDialog.title" :visible.sync="policyInfoDialog.open" width="850px" append-to-body>
      <el-form ref="policyInfoDialog.form" :inline="true" :model="policyInfoDialog.form" label-width="75px">
        <el-form-item label="文件名" prop="code">
          <el-input v-model="policyInfoDialog.form.name" placeholder="请输入文件名称" clearable />
        </el-form-item>
        <el-form-item label="发布年份" prop="code">
          <el-input v-model="policyInfoDialog.form.releaseYear" placeholder="请输入年份" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getPolicices">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="policyInfoDialog.form = {}">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="flag.dialogTableLoading" highlight-current-row @current-change="handleCurrentChange"
        :data="policyInfoDialog.policies">
        <el-table-column label="文件名称" align="center" prop="name" :show-overflow-tooltip="true" />
        <el-table-column label="发布年分" align="center" prop="releaseYear" :show-overflow-tooltip="true" />
        <el-table-column label="发布时间" align="center" prop="releaseDate" :show-overflow-tooltip="true" />
        <el-table-column label="发文字号" align="center" prop="fileNum" :show-overflow-tooltip="true" />
        <el-table-column label="描述" align="center" prop="remark" :show-overflow-tooltip="true" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogForm">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getOppPolicyInfo, getPropertiesMap
} from "@/api/crm/oppUnitedInfo"
import {
  listPolicyfile
} from "@/api/crm/policyfile";
export default {
  name: "PolicyStandBy",
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    },
  },
  data() {
    return {
      propertyKeys: [
        "policyId",
        "policyName",
        "policyReleaseYear"
      ],
      policyStandByForm: {
        "policyId": {},
        "policyName": {},
        "policyReleaseYear": {}
      },
      policyStandByModified: {},
      policyStandByOriginBak: {},

      //标识集合
      flag: {
        policyInfoLoading: false,
        dialogTableLoading: false,
      },
      //组件弹框承载
      policyInfoDialog: {
        // 弹出层标题
        title: "选择政策文件",
        // 是否显示弹出层
        open: false,
        form: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          fileNum: null,
          releaseDate: null,
          releaseOffice: null,
          releaseYear: null,
          status: '0'
        },
        policies: [],
        total: 0,
        selectedPolicy: {},
      },
    };
  },
  created() {
    this.initPolicyStandBy();
  },
  methods: {
    initPolicyStandBy() {
      var _this = this
      // 开启遮盖层
      this.flag.policyInfoLoading = true;
      this.getProperties(function () {
        _this.flag.policyInfoLoading = false;
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
          model: "policy",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.policyStandByForm = response.data;
        this.policyStandByOriginBak = JSON.parse(JSON.stringify(this.policyStandByForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    // 现在不是一对多 暂时不启用这个表
    getOppPolicyInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppPolicyInfo({
        code: this.oppdata.opportunityCode
      }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.policyStandByForm = response.data;
        if (typeof func == 'function') {
          func();
        }
      })
    },
    openDialog() {
      this.policyInfoDialog.open = true;
    },
    getPolicices() {
      listPolicyfile(this.policyInfoDialog.form).then(response => {
        this.policyInfoDialog.policies = response.rows;
        this.policyInfoDialog.total = response.total;
        this.loading = false;
      });
    },
    handleCurrentChange(val) {
      this.policyInfoDialog.selectedPolicy = val
    },
    submitDialogForm() {
      this.policyStandByForm.policyId.propertyVal = this.policyInfoDialog.selectedPolicy.id
      //this.policyStandByForm.policyName.propertyVal = this.policyInfoDialog.selectedPolicy.name
      this.policyStandByForm.policyReleaseYear.propertyVal = this.policyInfoDialog.selectedPolicy.releaseYear

      this.policyInfoDialog.open = false;
      this.$set(this.policyStandByForm.policyName, 'propertyVal', this.policyInfoDialog.selectedPolicy.name);

    },
    cancelDialog() {
      this.policyInfoDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["policyStandByForm"].validate(valid => {
        if (!valid) {
          flag = false
          return flag
        }
      });
      // 暂时不校验必填
      // if (!this.policyStandByForm.policyId.propertyVal) {
      //   flag = false
      // }
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      let update = false
      if (Object.keys(this.policyStandByOriginBak).length != 0) {
        if (this.policyStandByForm.policyId.propertyVal !== this.policyStandByOriginBak.policyId.propertyVal) {
          update = true;
        }
        if (this.policyStandByForm.policyName.propertyVal !== this.policyStandByOriginBak.policyName.propertyVal) {
          update = true;
        }
        if (this.policyStandByForm.policyReleaseYear.propertyVal !== this.policyStandByOriginBak.policyReleaseYear.propertyVal) {
          update = true;
        }
      } else {
        update = true;
      }
      if (update) {
        this.policyStandByModified = JSON.parse(JSON.stringify(this.policyStandByForm))
      }
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
      return {
        currentData: this.policyStandByForm,//最新展示数据
        modifyedData: this.policyStandByModified,//改动部分
        originData: this.policyStandByOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
