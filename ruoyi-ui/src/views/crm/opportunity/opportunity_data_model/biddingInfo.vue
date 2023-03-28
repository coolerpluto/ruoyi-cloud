<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>招投标情况分析</h3>
    <el-divider />
    <el-form ref="biddingInfoForm" v-loading="flag.biddingInfoLoading" :rules="rules" :model="biddingInfoForm"
      size="medium" label-width="220px">
      <div v-if="[4].includes(stageShow)">
        <!-- <el-button style="float:right;" type="info" icon="el-icon-plus">添加新的对手</el-button> -->
        <el-table :data="biddingInfoForm.biddingInfo">
          <el-table-column label="对应系统" align="center" prop="applicationName" :show-overflow-tooltip="true" />
          <el-table-column label="厂商名称 / 运作主体" align="center" prop="operationalName" :show-overflow-tooltip="true">
            <template slot-scope="scope">{{ scope.row.isv || '未选择' }} / {{ scope.row.operationalName || '未选择'
            }}</template>
          </el-table-column>
          <el-table-column label="是否参与投标" align="center" prop="operationalTender" :show-overflow-tooltip="true" />
          <el-table-column label="支持的数据库厂商" align="center" prop="operationalSupportDb" :show-overflow-tooltip="true" />
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="editApplication(scope.row)" icon="el-icon-edit">修改</el-button>
              <!-- <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button> -->
            </template>
          </el-table-column>
        </el-table>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="是否了解全部评标专家名单" prop="knowExpertList.propertyVal">
              <!-- <el-radio v-model="biddingInfoForm.knowExpertList.propertyVal" label="Y">已了解</el-radio>
              <el-radio v-model="biddingInfoForm.knowExpertList.propertyVal" label="N">不了解</el-radio> -->
              <el-radio-group v-model="biddingInfoForm.knowExpertList.propertyVal">
                <el-radio label="Y">已了解</el-radio>
                <el-radio label="N">不了解</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否已获得关键专家支持" prop="supportByExpert.propertyVal">
              <!-- <el-radio v-model="biddingInfoForm.supportByExpert.propertyVal" label="Y">已获得</el-radio>
              <el-radio v-model="biddingInfoForm.supportByExpert.propertyVal" label="N">未获得</el-radio> -->
              <el-radio-group v-model="biddingInfoForm.supportByExpert.propertyVal">
                <el-radio label="Y">已获得</el-radio>
                <el-radio label="N">未获得</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="biddingInfoDialog.title" :visible.sync="biddingInfoDialog.open" width="1000px" append-to-body>
      <el-form ref="biddingInfoDialog.form" :model="biddingInfoDialog.form" :rules="biddingInfoDialog.rules"
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
import { mergeRecursive } from "@/utils/ruoyi";
import { getPropertiesMap, getOppBaseInfo } from "@/api/crm/oppUnitedInfo"

export default {
  name: "biddingInfo",
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
        "knowExpertList": {}, "supportByExpert": {}
      },
      biddingInfoModified: {},
      biddingInfoOriginBak: {},
      //标识集合
      flag: {
        biddingInfoLoading: false,
        biddingInfoVerify: false,
      },
      rules: {
        "knowExpertList": {
          propertyVal: [
            { required: true, message: "未选择 是否了解全部评标专家名单", trigger: "blur" }
          ],
        },
        "supportByExpert": {
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
        rules: {},
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
          //console.log("this.biddingInfoForm:", _this.biddingInfoForm);
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
        this.biddingInfoForm = mergeRecursive(this.biddingInfoForm, response.data);
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
      this.biddingInfoDialog.open = true;
      this.biddingInfoDialog.title = "修改投标分析信息";
    },
    openDialog() {
      this.biddingInfoDialog.open = true;
      this.biddingInfoDialog.title = "添加投标分析信息";
    },
    submitDialogForm() {
      this.biddingInfoDialog.open = false;
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
    // 提供本组件的全部数据
    collectInfo() {
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
