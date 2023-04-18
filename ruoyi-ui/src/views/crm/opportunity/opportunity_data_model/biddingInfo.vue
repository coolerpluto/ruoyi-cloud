<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>招投标情况分析</h3>
    <el-divider />
    <el-form ref="biddingInfoForm" v-loading="flag.biddingInfoLoading" :rules="rules" :model="biddingInfoForm"
      size="medium" label-width="220px">
      <!-- <div v-if="[4].includes(stageShow)"> -->
      <!-- <el-button style="float:right;" type="info" icon="el-icon-plus">添加新的对手</el-button> -->
      <el-row>
        <el-table :data="biddingInfoForm.biddingInfo">
          <el-table-column label="对应系统" align="center" prop="applicationName" :show-overflow-tooltip="true" />
          <el-table-column label="厂商名称 / 运作主体" align="center" prop="operationalName" :show-overflow-tooltip="true">
            <template slot-scope="scope">{{ scope.row.isv || '未选择' }} / {{ scope.row.operationalName || '未选择'
            }}</template>
          </el-table-column>
          <el-table-column label="是否参与投标" align="center" prop="operationalTender" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.operationalTender" />
            </template>
          </el-table-column>
          <el-table-column label="支持的数据库厂商" align="center" prop="operationalSupportDb" :show-overflow-tooltip="true" />
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="editApplication(scope.row)" icon="el-icon-edit">修改</el-button>
              <!-- <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="8">
          <el-form-item label="是否了解全部评标专家名单" prop="knowExpertList.propertyVal">
            <el-radio-group v-model="biddingInfoForm.knowExpertList.propertyVal">
              <el-radio label="Y">已了解</el-radio>
              <el-radio label="N">不了解</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否已获得关键专家支持" prop="supportByExpert.propertyVal">
            <el-radio-group v-model="biddingInfoForm.supportByExpert.propertyVal">
              <el-radio label="Y">已获得</el-radio>
              <el-radio label="N">未获得</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- </div> -->
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="biddingInfoDialog.title" :visible.sync="biddingInfoDialog.open" width="1000px" append-to-body>
      <el-form ref="biddingInfoDialog.form" :model="biddingInfoDialog.form" :rules="biddingInfoDialog.rules"
        label-width="150px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="厂商名称/运作主体">
              <label>{{ biddingInfoDialog.form.isv || '未选择' }} / {{ biddingInfoDialog.form.operationalName || '未选择'
              }}</label>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="是否参与投标" prop="operationalTender">
              <el-radio-group v-model="biddingInfoDialog.form.operationalTender">
                <el-radio v-for="item in dict.type.sys_yes_no" :key="item.value" :label="item.value">{{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支持的数据库厂商" prop="operationalSupportDb">
              <el-input v-model="biddingInfoDialog.form.operationalSupportDb" placeholder="请输入支持的数据库厂商,多个时逗号分隔" />
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
import { mergeRecursive } from "@/utils/ruoyi";
import { getPropertiesMap, getOppBaseInfo } from "@/api/crm/oppUnitedInfo"

export default {
  name: "biddingInfo",
  dicts: ['sys_yes_no'],
  props: {
    stageShow: {
      type: Number,
      default: 1
    },
    stageTitle: {
      type: String,
      default: '招投标情况分析'
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      propertyKeys: [
        "knowExpertList", "supportByExpert"
      ],
      biddingInfoForm: {
        biddingInfo: [],
        knowExpertList: { model: "bidding_info", propertyLabel: "是否了解全部评标专家名单" },
        supportByExpert: { model: "bidding_info", propertyLabel: "是否已获得关键专家支持" }
      },
      biddingInfoModified: {},
      biddingInfoOriginBak: {},
      //标识集合
      flag: {
        biddingInfoLoading: false,
        biddingInfoVerify: false,
      },
      rules: {
        knowExpertList: {
          propertyVal: [
            { required: true, message: "未选择 是否了解全部评标专家名单", trigger: "blur" }
          ],
        },
        supportByExpert: {
          propertyVal: [
            { required: true, message: "未选择 是否已获得关键专家支持", trigger: "blur" }
          ],
        },
      },
      //组件弹框承载
      biddingInfoDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {
          operationalTender: [
            { required: true, message: "未选择是否参与投标选项", trigger: "blur" }
          ],
          operationalSupportDb: [
            { required: true, message: "未输入支持的数据库厂商", trigger: "blur" }
          ],
        },
      },
    }
  },
  created() {
    this.initBiddingInfo();
  },
  methods: {
    initBiddingInfo() {
      var _this = this;
      this.flag.biddingInfoLoading = true;
      this.getOppBaseInfo(function () {
        _this.getProperties(function () {
          _this.flag.biddingInfoLoading = false;
        })
      })
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
        this.biddingInfoForm.biddingInfo = response.data ? response.data.operations : [];
        if (typeof func == 'function') {
          func();
        }
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
          model: "bidding_info",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        //this.biddingInfoForm = mergeRecursive(this.biddingInfoForm, response.data);
        Object.keys(response.data).forEach(key => {
          this.biddingInfoForm[key] = response.data[key]
        })
        this.biddingInfoOriginBak = JSON.parse(JSON.stringify(this.biddingInfoForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    editApplication(row) {
      this.biddingInfoDialog.form = Object.assign({}, row);
      this.biddingInfoDialog.open = true;
      this.biddingInfoDialog.title = "修改投标分析信息";
    },
    openDialog() {
      this.biddingInfoDialog.form = {}
      this.biddingInfoDialog.open = true;
      this.biddingInfoDialog.title = "添加投标分析信息";
    },
    submitDialogForm() {
      this.$refs["biddingInfoDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        if (!this.biddingInfoDialog.form.id && !this.biddingInfoDialog.form.tempId) {
          this.biddingInfoDialog.form.tempId = new Date().getTime()//先打个标
          this.biddingInfoForm.biddingInfo.push(this.biddingInfoDialog.form)
        }
        // 替换
        this.biddingInfoForm.biddingInfo.map((item, i) => {
          if (item.id == this.biddingInfoDialog.form.id) {
            this.biddingInfoForm.biddingInfo.splice(i, 1, this.biddingInfoDialog.form)
          }
        })
        this.biddingInfoDialog.open = false;
      });
    },
    cancelDialog() {
      this.biddingInfoDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["biddingInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      debugger
      this.biddingInfoForm["knowExpertList"]["model"] =  "bidding_info";
      this.biddingInfoForm["supportByExpert"]["model"] =  "bidding_info";
      if (Object.keys(this.biddingInfoOriginBak).length == 0) {
        this.biddingInfoModified["knowExpertList"] = this.biddingInfoForm["knowExpertList"];
        this.biddingInfoModified["supportByExpert"] = this.biddingInfoForm["supportByExpert"];
        this.biddingInfoModified.biddingInfo_a = this.biddingInfoForm.biddingInfo;
        return;
      }
      this.biddingInfoModified = {}
      if (this.biddingInfoOriginBak["knowExpertList"].propertyVal != this.biddingInfoForm["knowExpertList"].propertyVal) {
        this.biddingInfoModified["knowExpertList"] = this.biddingInfoForm["knowExpertList"];
      }
      if (this.biddingInfoOriginBak["supportByExpert"].propertyVal != this.biddingInfoForm["supportByExpert"].propertyVal) {
        this.biddingInfoModified["supportByExpert"] = this.biddingInfoForm["supportByExpert"];
      }
      this.biddingInfoModified.biddingInfo_a = []
      this.biddingInfoModified.biddingInfo_m = []
      this.biddingInfoModified.biddingInfo_d = []
      for (let options of this.biddingInfoForm.biddingInfo) {
        if (!options.id) {
          this.biddingInfoModified.biddingInfo_a.push(options)
          continue;
        }
        let currentOld = this.biddingInfoOriginBak.biddingInfo.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.biddingInfoModified.biddingInfo_m.push(options)
      }
      for (let options of this.biddingInfoOriginBak.biddingInfo) {
        let current = this.biddingInfoForm.biddingInfo.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.biddingInfoModified.biddingInfo_d.push(options)
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
        currentData: this.biddingInfoForm,//最新展示数据
        modifyedData: this.biddingInfoModified,//改动部分
        originData: this.biddingInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
