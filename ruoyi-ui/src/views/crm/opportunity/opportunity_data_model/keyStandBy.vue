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
        <el-table-column label="介入阶段" align="center" prop="opportunityStage" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.opportunityStage" />
          </template>
        </el-table-column>
        <el-table-column label="填报人" align="center" prop="createBy" :show-overflow-tooltip="true" />
        <el-table-column label="添加时间" align="center" prop="createTime" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
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
          <el-col :span="10">
            <el-form-item label="支持人员" prop="supportName">
              <el-select v-model="keyStandByDialog.form.supportName" @change="getSupportor" placeholder="请输入 关键字拼音"
                filterable remote :remote-method="getSupportorOptions" :loading="flag.keyStandByOptionsLoading">
                <el-option v-for="item in keyStandByDialog.personOptions" :key="item.id" :label="item.userName"
                  :value="item.userName">
                  <span style="float: left">{{ item.userName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.dept.deptName }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22">
            <el-form-item label="支持内容" prop="supportVal">
              <el-input v-model="keyStandByDialog.form.supportVal" type="textarea" resize="none" :rows="3"
                placeholder="必须注明和我方业务相关信息" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="添加时间" prop="createTime">
              <el-date-picker v-model="keyStandByDialog.form.createTime" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" readonly>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="介入阶段" prop="opportunityStage">
              <el-select v-model="keyStandByDialog.form.opportunityStage" disabled>
                <el-option v-for="item in dict.type.crm_opportunity_status" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
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
import { listUser } from "@/api/system/user";
import { listEmployee } from "@/api/crm/employee";
export default {
  name: "keyStandBy",
  dicts: ['crm_opportunity_status'],
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
        keyStandByOptionsLoading: false,
      },
      //组件弹框承载
      keyStandByDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        personOptions: [],
        rules: {
          supportName: [
            { required: true, message: "未选择 支持人员", trigger: "blur" }
          ],
          supportVal: [
            { required: true, message: "未输入对应的支持内容", trigger: "blur" }
          ],
        },
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
      let index = this.keyStandByForm.keyStandBy.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.keyStandByForm.keyStandBy.splice(index, 1);
    },
    editStandBy(row) {
      this.keyStandByDialog.form = Object.assign({}, row);
      this.keyStandByDialog.open = true;
      this.keyStandByDialog.title = "修改进展信息";
    },
    openDialog() {
      this.keyStandByDialog.form = {
        createTime: new Date(),
        opportunityStage: this.keyStandByDialog.form.opportunityStage || this.stageShow + ""
      }
      this.keyStandByDialog.open = true;
      this.keyStandByDialog.title = "添加进展信息";
      this.getSupportorOptions()
    },
    getSupportor(selected) {
      const select = this.keyStandByDialog.personOptions.find(item => item.userName == selected)
      this.keyStandByDialog.form.supportId = select.userId;
    },
    getSupportorOptions(query) {
      this.flag.keyStandByOptionsLoading = true
      listEmployee({
        pageNum: 1,
        pageSize: 20,
        userName: query,
      }).then(response => {
        this.keyStandByDialog.personOptions = response.rows;
        this.flag.keyStandByOptionsLoading = false;
      });
    },
    submitDialogForm() {
      this.$refs["keyStandByDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.keyStandByDialog.form.createBy = this.keyStandByDialog.form.createBy || this.$store.state.user.name;

        if (!this.keyStandByDialog.form.id && !this.keyStandByDialog.form.tempId) {
          this.keyStandByDialog.form.tempId = new Date().getTime()//先打个标
          this.keyStandByForm.keyStandBy.push(this.keyStandByDialog.form)
        }
        // 替换
        this.keyStandByForm.keyStandBy.map((item, i) => {
          //带来的
          if (item.id && item.id == this.keyStandByDialog.form.id) {
            this.keyStandByForm.keyStandBy.splice(i, 1, this.keyStandByDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.keyStandByDialog.form.tempId) {
            this.keyStandByForm.keyStandBy.splice(i, 1, this.keyStandByDialog.form)
          }
        })
        this.keyStandByDialog.open = false;
      });
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
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.keyStandByOriginBak).length == 0) {
        this.keyStandByModified.keyStandBy_a = this.keyStandByForm.keyStandBy
        return;
      }
      this.keyStandByModified.keyStandBy_a = []
      this.keyStandByModified.keyStandBy_m = []
      this.keyStandByModified.keyStandBy_d = []
      for (let options of this.keyStandByForm.keyStandBy) {
        if (!options.id) {
          this.keyStandByModified.keyStandBy_a.push(options)
          continue;
        }
        let currentOld = this.keyStandByOriginBak.keyStandBy.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.keyStandByModified.keyStandBy_m.push(options)
      }
      for (let options of this.keyStandByOriginBak.keyStandBy) {
        let current = this.keyStandByForm.keyStandBy.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.keyStandByModified.keyStandBy_d.push(options)
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
      this.fetchInformation()
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
