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
        <el-table-column label="使用日期" align="center" prop="useTime" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.useTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="投入原因" align="center" prop="reason" :show-overflow-tooltip="true" />
        <el-table-column label="投入金额(元)" align="center" prop="cost" :show-overflow-tooltip="true" />
        <el-table-column label="投入人员" align="center" prop="inputUserName" :show-overflow-tooltip="true" />
        <el-table-column label="填写人员" align="center" prop="createBy" :show-overflow-tooltip="true" />
        <el-table-column label="填写时间" align="center" prop="createTime" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editCostInfo(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeCostInfo(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="costInfosDialog.title" :visible.sync="costInfosDialog.open" width="800px" append-to-body>
      <el-form ref="costInfosDialog.form" :model="costInfosDialog.form" :rules="costInfosDialog.rules"
        label-width="100px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="投入金额" prop="cost">
              <el-input v-model="costInfosDialog.form.cost" type="number" placeholder="请输入投入金额">
              </el-input>
              {{ convertCurrencyDX(costInfosDialog.form.cost || 0) }}
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="使用日期" prop="useTime">
              <el-date-picker v-model="costInfosDialog.form.useTime" placeholder="请选择使用时间" type="date"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22">
            <el-form-item label="投入原因" prop="reason">
              <el-input v-model="costInfosDialog.form.reason" type="textarea" resize="none" :rows="3"
                placeholder="必须注明投入和我方业务相关信息" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="投入人员" prop="inputUserName">
              <el-select v-model="costInfosDialog.form.inputUserName" @change="getSupportor" placeholder="请输入关键字拼音查询"
                filterable remote :remote-method="getSupportorOptions" :loading="flag.personOptionsLoading">
                <el-option v-for="item in costInfosDialog.personOptions" :key="item.id" :label="item.userName"
                  :value="item.userName">
                  <span style="float: left">{{ item.userName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.dept.deptName }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="填写时间" prop="createTime">
              <el-date-picker v-model="costInfosDialog.form.createTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                readonly>
              </el-date-picker>
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
import { getOppCostInfo } from "@/api/crm/oppUnitedInfo"
import { listUser } from "@/api/system/user";

export default {
  name: "costInfo",
  dicts: ['crm_opportunity_status'],
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
      },
      costInfoModified: {},
      costInfoOriginBak: {},
      flag: {
        contactInfoLoading: false,
        contactNumVerify: false,
        personOptionsLoading: false,
      },
      //组件弹框承载
      costInfosDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        personOptions: [],
        rules: {
          cost: [
            { required: true, message: "未填写投入金额", trigger: "blur" }
          ],
          useTime: [
            { required: true, message: "未填写使用日期", trigger: "blur" }
          ],
          reason: [
            { required: true, message: "未填写投入原因", trigger: "blur" }
          ],
        },
      },
    }
  },
  computed: {
    sumAmount() {
      var total = 0
      this.costInfoForm.costInfos.forEach(e => {
        total = (e.cost * 1) + total
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
      let index = this.costInfoForm.costInfos.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.costInfoForm.costInfos.splice(index, 1);
    },
    editCostInfo(row) {
      this.costInfosDialog.form = Object.assign({}, row);
      this.costInfosDialog.open = true;
      this.costInfosDialog.title = "修改花费信息";
    },
    openDialog() {
      this.costInfosDialog.form = {
        createTime: new Date(),
      };
      this.costInfosDialog.open = true;
      this.costInfosDialog.title = "添加新的支出信息";
      this.getSupportorOptions()
    },
    getSupportor(selected) {
      const select = this.costInfosDialog.personOptions.find(item => item.userName == selected)
      this.costInfosDialog.form.inputUserId = select.userId;
    },
    getSupportorOptions(query) {
      this.flag.personOptionsLoading = true
      listUser({
        pageNum: 1,
        pageSize: 20,
        userName: query,
      }).then(response => {
        this.costInfosDialog.personOptions = response.rows;
        this.flag.personOptionsLoading = false;
      });
    },
    submitDialogForm() {
      this.$refs["costInfosDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.costInfosDialog.form.createBy = this.costInfosDialog.form.createBy || this.$store.state.user.name;
        if (!this.costInfosDialog.form.id && !this.costInfosDialog.form.tempId) {
          this.costInfosDialog.form.tempId = new Date().getTime()//先打个标
          this.costInfoForm.costInfos.push(this.costInfosDialog.form)
        }
        // 替换
        this.costInfoForm.costInfos.map((item, i) => {
          //带来的
          if (item.id && item.id == this.costInfosDialog.form.id) {
            this.costInfoForm.costInfos.splice(i, 1, this.costInfosDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.costInfosDialog.form.tempId) {
            this.costInfoForm.costInfos.splice(i, 1, this.costInfosDialog.form)
          }
        })
        this.costInfosDialog.open = false;
      });
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
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.costInfoOriginBak).length == 0) {
        this.costInfoModified.costInfos_a = this.costInfoForm.costInfos
        return;
      }
      this.costInfoModified.costInfos_a = []
      this.costInfoModified.costInfos_m = []
      this.costInfoModified.costInfos_d = []
      for (let options of this.costInfoForm.costInfos) {
        if (!options.id) {
          this.costInfoModified.costInfos_a.push(options)
          continue;
        }
        let currentOld = this.costInfoOriginBak.costInfos.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.costInfoModified.costInfos_m.push(options)
      }
      for (let options of this.costInfoOriginBak.costInfos) {
        let current = this.costInfoForm.costInfos.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.costInfoModified.costInfos_d.push(options)
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
