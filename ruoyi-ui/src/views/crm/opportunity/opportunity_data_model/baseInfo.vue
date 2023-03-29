<template>
  <div class="app-container" id="baseInfo" :style="{pointerEvents:oppdata.action=='V'?'none':'unset'}">
    <h3>基础信息</h3>
    <el-divider />
    <el-form ref="baseInfoForm" :rules="rules" :model="baseInfoForm" size="medium" label-width="220px"
      v-loading="flag.baseInfoLoading">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="商机名称" prop="oppInfo.name">
            <el-input v-model="baseInfoForm.oppInfo.name" placeholder="请输入商机名称" clearable :style="{ width: '100%' }">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商机编码" prop="oppInfo.code">
            <label placeholder="自动生产商机编码">{{ baseInfoForm.oppInfo.code }}</label>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="应用运营信息">
            <div class="el-form-item__error" v-if="flag.applicationVerify">应用运营信息最少有一条</div>
            <el-button style="float:right;" type="info" icon="el-icon-plus" @click="openDialog">添加新条目</el-button>
            <el-table :data="baseInfoForm.operations">
              <el-table-column label="客户系统名称" align="center" fixed prop="applicationName" :show-overflow-tooltip="true" />
              <el-table-column label="系统开发商" align="center" fixed prop="isv" :show-overflow-tooltip="true" />
              <el-table-column label="适配进度" align="center" fixed prop="currentAdaptedProgress"
                :show-overflow-tooltip="true" >
                <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.currentAdaptedProgress"/>
              </template>
              </el-table-column>

              <el-table-column label="招标前必须完成度" align="center" fixed prop="targetAdaptedProgress"
                :show-overflow-tooltip="true" >
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.targetAdaptedProgress"/>
                </template>
              </el-table-column>
              <el-table-column label="软件分类" align="center" fixed prop="category" :show-overflow-tooltip="true" />
              <el-table-column label="项目运作主体" align="center" fixed prop="operationalName" :show-overflow-tooltip="true" />
              <el-table-column label="操作" align="center" fixed>
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-edit"
                    @click="editApplication(scope.row)">修改</el-button>
                  <el-button size="mini" type="text" icon="el-icon-delete"
                    @click="removeApplication(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="baseInfoDialog.title" :visible.sync="baseInfoDialog.open" width="1000px" append-to-body>
      <el-form ref="baseInfoDialog.form" :model="baseInfoDialog.form" :rules="baseInfoDialog.rules" label-width="100px">
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
import appOperationInfo from "./appOperationInfo";
import {
  getOppBaseInfo
} from "@/api/crm/oppUnitedInfo"
export default {
  name: "baseInfo",
  dicts:['sys_work_progress'],
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      //组件信息承载
      baseInfoForm: {
        oppInfo: {},
        operations: []
      },
      //组件信息承载修改删除的信息
      baseInfoModified: {
        oppInfo: {},
        operations: []
      },
      //组件信息原始承载备份
      baseInfoOriginBak: {
        oppInfo: {},
        operations: []
      },
      //标识集合
      flag: {
        baseInfoLoading: false,
        applicationVerify: false,
      },
      // 表单校验
      rules: {
        oppInfo: {
          name: [
            { required: true, message: "商机名称不能为空", trigger: "blur" }
          ],
        }
      },
      //组件弹框承载
      baseInfoDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {},
      },
    };
  },
  created() {
    this.initBase();
  },
  mounted() {
  },
  methods: {
    initBase() {
      var _this = this
      // 开启遮盖层
      this.flag.baseInfoLoading = true;
      //console.log("base:",_this.oppdata)
      this.getOppBaseInfo(function () {
        _this.flag.baseInfoLoading = false
      });
    },
    //获取最新显示信息
    getOppBaseInfo(func) {
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
        this.baseInfoForm = response.data;
        this.baseInfoOriginBak = JSON.parse(JSON.stringify(this.baseInfoForm))
        if (typeof func == 'function') {
          func();
        }
      })
    },
    removeApplication(row) {

    },
    editApplication(row) {
      this.baseInfoDialog.open = true;
      this.baseInfoDialog.title = "修改应用运营信息";
    },
    openDialog() {
      this.baseInfoDialog.open = true;
      this.baseInfoDialog.title = "添加应用运营信息";
    },
    submitDialogForm() {
      this.baseInfoDialog.open = false;
    },
    cancelDialog() {
      this.baseInfoDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      if (!this.baseInfoForm.operations || this.baseInfoForm.operations.length == 0) {
        this.flag.applicationVerify = true
        flag = false
      }
      this.$refs["baseInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {      
      return {
              currentData:this.baseInfoForm,//最新展示数据
              modifyedData:this.baseInfoModified,//改动部分
              originData:this.baseInfoOriginBak,//原始数据
            };
    },
  },
}
</script>

<style scoped></style>
