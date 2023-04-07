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
        <el-table-column label="进展阶段" align="center" prop="stage" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.stage" />
          </template>
        </el-table-column>
        <el-table-column label="进展日期" align="center" prop="advancesDate" :show-overflow-tooltip="true" />
        <el-table-column label="填报人" align="center" prop="creator" :show-overflow-tooltip="true" />
        <el-table-column label="填报时间" align="center" prop="createTime" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
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
    <el-dialog :title="advancesDialog.title" :visible.sync="advancesDialog.open" width="750px" append-to-body>
      <el-form ref="advancesDialog.form" :model="advancesDialog.form" :rules="advancesDialog.rules" label-width="100px">
        <el-row>
          <el-col :span="11">
            <el-form-item label="进展日期" prop="advancesDate">
              <el-date-picker v-model="advancesDialog.form.advancesDate" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="当前阶段" prop="stage">
              <el-select v-model="advancesDialog.form.stage" disabled>
                <el-option v-for="item in dict.type.crm_opportunity_status" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22">
            <el-form-item label="进展内容" prop="advances">
              <el-input v-model="advancesDialog.form.advances" type="textarea" resize="none" :rows="3"
                placeholder="请输入进展内容,必须注明和我方业务相关瓶颈、建议和意见" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="填报时间" prop="createTime">
            <el-date-picker v-model="advancesDialog.form.createTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
              readonly>
            </el-date-picker>
          </el-form-item>
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
  dicts: ["crm_opportunity_status"],
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
        rules: {
          advancesDate: [
            { required: true, message: "未选择 进展发生时间", trigger: "blur" }
          ],
          advances: [
            { required: true, message: "未输入对应的进展信息", trigger: "blur" }
          ],
        },
      },
    }
  },
  created() {
    this.initAdvancesInfo()
  },
  methods: {
    initAdvancesInfo() {
      var _this = this
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
        this.advancesInfoOriginBak = JSON.parse(JSON.stringify(this.advancesInfoForm));
        if (typeof func == 'function') {
          func();
        }
      })
    },

    removeAdvances(row) {
      let index = this.advancesInfoForm.advancesInfo.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.advancesInfoForm.advancesInfo.splice(index, 1);
    },
    editAdvances(row) {
      this.advancesDialog.form = Object.assign({}, row);
      this.advancesDialog.open = true;
      this.advancesDialog.title = "修改进展信息";
    },
    openDialog() {
      this.advancesDialog.open = true;
      this.advancesDialog.form = {
        createTime: new Date(),
        stage: this.advancesDialog.form.stage || this.stageShow + ""
      }
      this.advancesDialog.title = "添加进展信息";
    },
    submitDialogForm() {
      this.$refs["advancesDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.advancesDialog.form.creator = this.advancesDialog.form.creator || this.$store.state.user.name;
        if (!this.advancesDialog.form.id && !this.advancesDialog.form.tempId) {
          this.advancesDialog.form.tempId = new Date().getTime()//先打个标
          this.advancesInfoForm.advancesInfo.push(this.advancesDialog.form)
        }
        // 替换
        this.advancesInfoForm.advancesInfo.map((item, i) => {
          //带来的
          if (item.id && item.id == this.advancesDialog.form.id) {
            this.advancesInfoForm.advancesInfo.splice(i, 1, this.advancesDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.advancesDialog.form.tempId) {
            this.advancesInfoForm.advancesInfo.splice(i, 1, this.advancesDialog.form)
          }
        })
        this.advancesDialog.open = false;
      });
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
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.advancesInfoOriginBak).length == 0) {
        this.advancesInfoModified.advancesInfo_a = this.advancesInfoForm.advancesInfo
        return;
      }
      this.advancesInfoModified.advancesInfo_a = []
      this.advancesInfoModified.advancesInfo_m = []
      this.advancesInfoModified.advancesInfo_d = []
      for (let options of this.advancesInfoForm.advancesInfo) {
        if (!options.id) {
          this.advancesInfoModified.advancesInfo_a.push(options)
          continue;
        }
        let currentOld = this.advancesInfoOriginBak.advancesInfo.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.advancesInfoModified.advancesInfo_m.push(options)
      }
      for (let options of this.advancesInfoOriginBak.advancesInfo) {
        let current = this.advancesInfoForm.advancesInfo.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.advancesInfoModified.advancesInfo_d.push(options)
      }
    },
    //对象属性全部平铺的情况
    objEqual(oldObj, newObj) {
      for (let key in oldObj) {
        // 不考虑new新加的标识属性
        if (oldObj[key] !== newObj[key]) {
          return false;
        }
      }
      return true;
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
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
