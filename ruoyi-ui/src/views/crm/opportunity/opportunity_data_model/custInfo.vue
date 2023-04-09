<template>
  <div class="app-container" id="custInfo" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>用户信息概述</h3>
    <el-divider />
    <el-form ref="custInfoForm" :rules="rules" :model="custInfoForm" v-loading="flag.custInfoLoading" size="medium"
      label-width="220px">
      <div v-if="stageShow == 1">
        <el-row :gutter="15">
          <el-col :span="9">
            <el-form-item label="客户公司名称" prop="userCompanyName.propertyVal">
              <div class="el-form-item__error" v-if="flag.custVerify">未正确获得用户信息</div>
              <el-input v-model="custInfoForm.userCompanyName.propertyVal" id="userCompanyNamepropertyVal"
                placeholder="请选择客户公司名称">
                <el-button slot="append" icon="el-icon-search" @click="openDialog">选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="15">
            <el-form-item label="公司地址" prop="userCompanyAddr.propertyVal">
              <el-input v-model="custInfoForm.userCompanyAddr.propertyVal" readonly placeholder="请选择公司地址三级联动">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="9">
            <el-form-item label="地址邮编" prop="userCompanyZipCode.propertyVal">
              <el-input v-model="custInfoForm.userCompanyZipCode.propertyVal" readonly placeholder="请输入地址邮编">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="15">
            <el-form-item label="详细地址" prop="userCompanyAddrDetail.propertyVal">
              <el-input v-model="custInfoForm.userCompanyAddrDetail.propertyVal" readonly placeholder="请输入公司地址到门牌号">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="客户描述" prop="userCompanyDesc.propertyVal">
              <el-input v-model="custInfoForm.userCompanyDesc.propertyVal" readonly type="textarea" resize="none"
                :rows="2" placeholder="请输入对客户的描述信息">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="stageShow == 2">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="新老客户" prop="newOrOldCust.propertyVal">
              <el-radio v-model="custInfoForm.newOrOldCust.propertyVal" label="new">新客户</el-radio>
              <el-radio v-model="custInfoForm.newOrOldCust.propertyVal" label="old">老客户</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="行业影响力" prop="industryInfluence.propertyVal">
              <el-select v-model="custInfoForm.industryInfluence.propertyVal" placeholder="请选择">
                <el-option v-for="item in dict.type.crm_industry_influence" :key="item.value" :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="项目是否正式发布红头文件" prop="redHeadedDoc.propertyVal">
              <el-radio v-model="custInfoForm.redHeadedDoc.propertyVal" label="have">有发布</el-radio>
              <el-radio v-model="custInfoForm.redHeadedDoc.propertyVal" label="no">未发布</el-radio>
              <el-radio v-model="custInfoForm.redHeadedDoc.propertyVal" label="unknow">不知道</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目是否有会议指示" prop="meetingInstruction.propertyVal">
              <el-radio v-model="custInfoForm.meetingInstruction.propertyVal" label="have">有指示</el-radio>
              <el-radio v-model="custInfoForm.meetingInstruction.propertyVal" label="no">无指示</el-radio>
              <el-radio v-model="custInfoForm.meetingInstruction.propertyVal" label="unknow">不知道</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否需要售前支持" prop="preSaleSupport.propertyVal">
              <el-radio v-model="custInfoForm.preSaleSupport.propertyVal" label="need">需要支持</el-radio>
              <el-radio v-model="custInfoForm.preSaleSupport.propertyVal" label="no">暂不需要</el-radio>
              <el-radio v-model="custInfoForm.preSaleSupport.propertyVal" label="done">已完成支持</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="custInfoDialog.title" :visible.sync="custInfoDialog.open" width="800px" append-to-body>
      <el-form ref="custInfoDialog.form" :inline="true" :model="custInfoDialog.form" label-width="100px">
        <el-form-item label="公司名称" prop="code">
          <el-input v-model="custInfoDialog.form.companyName" placeholder="请输入公司名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getListCompanys">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="custInfoDialog.form.companyName = ''">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="flag.dialogTableLoading" highlight-current-row @current-change="handleCurrentChange"
        :data="custInfoDialog.companys">
        <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true" />
        <el-table-column label="公司地址" align="center" prop="addr" :show-overflow-tooltip="true" />
        <el-table-column label="详细地址" align="center" prop="addrDetail" :show-overflow-tooltip="true" />
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
import { listCompany } from "@/api/crm/company";
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo";
export default {
  name: "custInfo",
  dicts: ['crm_industry_influence'],
  props: {
    stageShow: {
      type: Number,
      default: 1
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
        "userCompanyId",
        "userCompanyName",
        "userCompanyCode",
        "userCompanyAddr",
        "userCompanyZipCode",
        "userCompanyAddrDetail",
        "userCompanyDesc",

        "newOrOldCust",
        "industryInfluence",
        "redHeadedDoc",
        "meetingInstruction",
        "preSaleSupport"
      ],
      custInfoForm: {
        "userCompanyId": { propertyLable: "用户公司ID", model: "cust" },
        "userCompanyName": { propertyLable: "用户公司名称", model: "cust" },
        "userCompanyCode": { propertyLable: "用户公司code", model: "cust" },
        "userCompanyAddr": { propertyLable: "用户公司地址", model: "cust" },
        "userCompanyZipCode": { propertyLable: "用户地址邮编", model: "cust" },
        "userCompanyAddrDetail": { propertyLable: "用户详细地址", model: "cust" },
        "userCompanyDesc": { propertyLable: "用户公司描述", model: "cust" },

        "newOrOldCust": { propertyLable: "新老用户", model: "cust" },
        "industryInfluence": { propertyLable: "用户公司影响力", model: "cust" },
        "redHeadedDoc": { propertyLable: "是否有红头文件支持", model: "cust" },
        "meetingInstruction": { propertyLable: "是否有会议指示", model: "cust" },
        "preSaleSupport": { propertyLable: "是否需要售前支持", model: "cust" }
      },
      custInfoModified: {},
      custInfoOriginBak: {},
      //标识集合
      flag: {
        custInfoLoading: false,
        dialogTableLoading: false,
        custVerify: false,
      },
      rules: {
        userCompanyName: {
          propertyVal: [
            { required: true, message: "未获得用户信息", trigger: "change" }
          ],
        }
      },
      //组件弹框承载
      custInfoDialog: {
        // 弹出层标题
        title: "选择用户",
        // 是否显示弹出层
        open: false,
        form: {
          pageNum: 1,
          pageSize: 15,
          sourceType: "customer",
          //businessScope:"S", // 公司业务中带S的
          companyName: ""
        },
        companys: [],
        total: 0,
        selectedCompany: {},
      },
    }
  },
  created() {
    this.initCustInfo();
  },
  methods: {
    initCustInfo() {
      var _this = this
      // 开启遮盖层
      this.flag.custInfoLoading = true;
      this.getProperties(function () {
        //console.log("custInfoForm:",_this.custInfoForm)
        _this.flag.custInfoLoading = false;
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
          model: "cust",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.custInfoForm = response.data;
        this.custInfoOriginBak = JSON.parse(JSON.stringify(this.custInfoForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    openDialog() {
      this.custInfoDialog.open = true;
    },
    getListCompanys() {
      this.flag.dialogTableLoading = true
      listCompany(this.custInfoDialog.form).then(response => {
        this.custInfoDialog.companys = (response.rows);
        this.custInfoDialog.total = response.total;
        this.flag.dialogTableLoading = false;
      });
    },
    handleCurrentChange(val) {
      this.custInfoDialog.selectedCompany = val
    },
    submitDialogForm() {
      this.custInfoForm.userCompanyId.propertyVal = this.custInfoDialog.selectedCompany.id
      //this.custInfoForm.userCompanyName.propertyVal = this.custInfoDialog.selectedCompany.companyName
      this.custInfoForm.userCompanyCode.propertyVal = this.custInfoDialog.selectedCompany.code
      this.custInfoForm.userCompanyAddr.propertyVal = this.custInfoDialog.selectedCompany.addr
      this.custInfoForm.userCompanyAddrDetail.propertyVal = this.custInfoDialog.selectedCompany.addrDetail
      this.custInfoForm.userCompanyZipCode.propertyVal = this.custInfoDialog.selectedCompany.zipCode
      this.custInfoForm.userCompanyDesc.propertyVal = this.custInfoDialog.selectedCompany.remark
      this.custInfoDialog.open = false;
      this.$set(this.custInfoForm.userCompanyName, 'propertyVal', this.custInfoDialog.selectedCompany.companyName);
    },
    cancelDialog() {
      this.custInfoDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["custInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
          return flag
        }
      });

      if (flag && !this.custInfoForm.userCompanyId.propertyVal) {
        this.flag.custVerify = true
        flag = false
      }
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.custInfoOriginBak).length == 0) {
        this.custInfoModified = JSON.parse(JSON.stringify(this.custInfoForm))
        return;
      }
      this.custInfoModified = {}
      Object.keys(this.custInfoForm).forEach(key => {
        if (this.custInfoOriginBak[key].propertyVal != this.custInfoForm[key].propertyVal) {
          this.custInfoModified[key] = this.custInfoForm[key]
        }
      })
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
      return {
        currentData: this.custInfoForm,//最新展示数据
        modifyedData: this.custInfoModified,//改动部分
        originData: this.custInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
