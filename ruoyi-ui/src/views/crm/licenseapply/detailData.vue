<template>
  <div class="app-container">
    <el-form ref="form" :model="data" :rules="rules" label-width="88px">
      <h3>基本信息</h3>
      <el-divider/>
      <el-row>
        <el-col :span="8">
          <el-form-item label="商机名称" prop="licenseData.name">
            <el-input v-model="data.licenseData.name" placeholder="请输入商机名称"/>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="用途" prop="licenseData.purposes">
            <el-select v-model="data.licenseData.purposes" placeholder="请选择用途">
              <el-option v-for="dict in dict.type.crm_lic_purposes_type" :key="dict.value" :label="dict.label"
                         :value="dict.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商机Code" prop="licenseData.code">
            <label placeholder="自动生产商机编码">{{ data.licenseData.code || genderOppCode() }}</label>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="应用信息">
          <div class="el-form-item__error" v-if="flag.applicationVerify">应用运营信息最少有一条</div>
          <el-button style="float:right;" type="info" icon="el-icon-plus" @click="openAppDialog">添加新条目</el-button>
          <el-table :data="data.applicationList">
            <el-table-column label="客户系统名称" align="center" fixed prop="applicationName"
                             :show-overflow-tooltip="true"/>
            <el-table-column label="系统开发商" align="center" fixed prop="isv" :show-overflow-tooltip="true"/>
            <el-table-column label="项目运作主体" align="center" fixed prop="operationalName"
                             :show-overflow-tooltip="true"/>
            <el-table-column label="操作" align="center" fixed>
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit"
                           @click="editApplication(scope.row)">修改
                </el-button>
                <el-button size="mini" type="text" icon="el-icon-delete"
                           @click="removeApplication(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-row>
      <h3>用户概述</h3>
      <el-divider/>
      <el-row>
        <el-col :span="10">
          <el-form-item label="客户名称" prop="licenseData.custName">
            <div class="el-form-item__error" v-if="flag.custVerify">未正确获得用户信息</div>
            <el-input v-model="data.licenseData.custName" id="formCustName"
                      placeholder="请选择客户公司名称">
              <el-button slot="append" icon="el-icon-search" @click="openCustDialog">选择</el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="需求时间" prop="licenseData.useTime">
            <el-date-picker clearable v-model="data.licenseData.useTime" type="date" value-format="yyyy-MM-dd"
                            placeholder="请选择用户需求时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <h3>产品信息</h3>
      <el-divider/>
      <el-row>
        <el-button style="float:right;" type="info" @click="openQuotationDialog" icon="el-icon-plus">添加报价</el-button>
        <el-table :data="data.quotationList">
          <el-table-column label="产品名称" align="center" prop="quotationMap.productName" :show-overflow-tooltip="true"/>
          <el-table-column label="版本" align="center" prop="quotationMap.productVersion"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="版本号" align="center" prop="quotationMap.productSecVersion"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="数量" align="center" prop="quotationMap.productCount" :show-overflow-tooltip="true"/>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="editQuotation(scope.row)" icon="el-icon-edit">修改</el-button>
              <el-button size="mini" type="text" @click="removeQuotation(scope.row)" icon="el-icon-delete">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-form>
    <div style="text-align: center;" v-if="action != 'V'">
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保存并更新</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </div>

    <el-dialog :title="applicationDialog.title" :visible.sync="applicationDialog.open" width="1000px" append-to-body>
      <el-form ref="applicationDialog.form" :model="applicationDialog.form" :rules="applicationDialog.rules"
               label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户系统名称" prop="applicationName">
              <el-input v-model="applicationDialog.form.applicationName" placeholder="请输入应用名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在用数据库" prop="dbBase">
              <el-select v-model="applicationDialog.form.dbBase" placeholder="请选择当前在用的数据库">
                <el-option v-for="dict in dict.type.sys_system_db_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="软件开发商" prop="isv">
              <el-select v-model="applicationDialog.form.isv" @change="getIsv" placeholder="请输入关键字" filterable remote
                         :remote-method="getIsvOptions" :loading="flag.isvOptionsLoading">
                <el-option v-for="item in applicationDialog.isvOptions" :key="item.id" :label="item.companyName"
                           :value="item.companyName">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目运作主体" prop="operationalName">
              <el-select v-model="applicationDialog.form.operationalName" @change="getOperational" placeholder="请输入关键字"
                         filterable remote :remote-method="getOperationalOptions"
                         :loading="flag.operationalOptionsLoading">
                <el-option v-for="item in applicationDialog.operationalOptions" :key="item.id" :label="item.companyName"
                           :value="item.companyName">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAppDialog">确 定</el-button>
        <el-button @click="cancelAppDialog">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="quotationDialog.title" :visible.sync="quotationDialog.open" width="600px" append-to-body>
      <el-form ref="quotationDialog.form" :model="quotationDialog.form" :rules="quotationDialog.rules"
               label-width="100px">
        <el-row>
          <el-col :span="11">
            <el-form-item label="产品名称" prop="productCode">
              <el-select v-model="quotationDialog.form.productCode" @change="selectProduct">
                <el-option v-for="item in dict.type.crm_opportunity_main_product" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11" v-if="false">
            <el-form-item label="产品名称1" prop="productName">
              <el-input v-model="quotationDialog.form.productName">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="数量" prop="productCount">
              <el-input v-model="quotationDialog.form.productCount" placeholder="请输入数量"
                        type="number">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="版本" prop="productVersion">
              <el-select v-model="quotationDialog.form.productVersion" @change="selectProductVersion">
                <el-option v-for="item in quotationDialog.productVersion" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="版本号" prop="productSecVersion">
              <el-select v-model="quotationDialog.form.productSecVersion">
                <el-option v-for="item in quotationDialog.productSecVersion" :key="item.dictValue"
                           :label="item.dictLabel"
                           :value="item.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogQuotationForm">确 定</el-button>
        <el-button @click="cancelDialogQuotation">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择用户" :visible.sync="custInfoDialog.open" width="800px" append-to-body
               :show-close="false">
      <el-form ref="custInfoDialog.form" :inline="true" :model="custInfoDialog.form" label-width="100px"
               @submit.native.prevent>
        <el-form-item label="公司名称" prop="code">
          <el-input v-model="custInfoDialog.form.companyName" placeholder="请输入公司名称" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getListCompany">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="custInfoDialog.form.companyName = ''">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="flag.dialogTableLoading" highlight-current-row @current-change="handleCustSelected"
                :data="custInfoDialog.companyList">
        <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true"/>
        <el-table-column label="详细地址" align="center" prop="addrDetail" :show-overflow-tooltip="true"/>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogCustSelected">确 定</el-button>
        <el-button @click="cancelCustDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getLicenseApplyByCode, saveAndUpdateLicenseApply} from "@/api/crm/licenseApply";
import {listCompany} from "@/api/crm/company";

export default {
  name: "LicenseApply",
  dicts: ['crm_lic_purposes_type', 'crm_opportunity_main_product', 'sys_system_db_type'],
  data() {
    return {
      action: undefined,
      opportunityCode: undefined,
      // 遮罩层
      loading: true,
      flag: {
        applicationVerify: false,
        custVerify: false,
        dialogTableLoading: false,
        isvOptionsLoading: false,
        operationalOptionsLoading: false,
      },
      data: {
        code:undefined,
        licenseData: {},
        quotationList: [],
        applicationList: [],
      },
      dataBak: {},
      rules: {
        licenseData: {
          name: [
            {required: true, message: "商机名称未填写", trigger: "blur"}
          ],
          purposes: [
            {required: true, message: "用途未选择", trigger: "blur"}
          ],
          custName: [
            {required: true, message: "客户未选择", trigger: "blur"}
          ],
          useTime: [
            {required: true, message: "用户需求时间填写", trigger: "blur"}
          ],
        },
      },
      custInfoDialog: {
        form: {},
        companyList: [],
        open: false,
        total: 0,
        selectedCompany: {},
      },
      quotationDialog: {
        form: {},
        rules: {
          productCode: [
            {required: true, message: "未选择产品", trigger: "blur"}
          ],
          productVersion: [
            {required: true, message: "未选择版本", trigger: "blur"}
          ],
          productSecVersion: [
            {required: true, message: "未选择版本号", trigger: "blur"}
          ],
          productCount: [
            {required: true, message: "未填写数量", trigger: "blur"}
          ],
        }, open: false, title: "",
        productVersion: [],
        productSecVersion: [],
      },
      applicationDialog: {
        title: "",
        open: false,
        form: {},
        rules: {
          applicationName: [
            {required: true, message: "客户系统名称不能为空", trigger: "blur"}
          ],
          dbBase: [
            {required: true, message: "在用数据库不能为空", trigger: "blur"}
          ], isv: [
            {required: true, message: "软件开发商不能为空", trigger: "blur"}
          ], operationalName: [
            {required: true, message: "项目运作主体未选择", trigger: "blur"}
          ],
        },
        operationalOptions: [],
        isvOptions: [],
      },
    };
  },
  created() {
    this.opportunityCode = this.$route.params && this.$route.params.code;
    this.action = this.$route.params && this.$route.params.action;
    this.init();
  },
  methods: {
    init() {
      if (this.action === "A") {
        return;
      }
      this.data.code = this.data.licenseData.code = this.opportunityCode;
      getLicenseApplyByCode({code: this.opportunityCode}).then(res => {
        this.data = res.data;
        this.data.code = this.data.code || this.opportunityCode;
        this.data.quotationList = this.data.quotationList || [];
        this.data.quotationList.forEach(e => {
          e.quotationMap = JSON.parse(e.quotationJson);
        })
        this.dataBak = JSON.parse(JSON.stringify(this.data));
      })
    },
    openAppDialog() {
      this.applicationDialog.form = {
        opportunityCode: this.data.licenseData.code,
        createBy: this.$store.state.user.name,
      }
      this.applicationDialog.open = true;
      this.applicationDialog.title = "添加应用信息";
      this.getOperationalOptions();
      this.getIsvOptions();
    },
    editApplication(row) {
      this.applicationDialog.form = Object.assign({}, row);
      this.applicationDialog.open = true;
      this.applicationDialog.title = "修改应用信息";
    },
    getOperationalOptions(query) {
      this.flag.operationalOptionsLoading = true
      this.applicationDialog.operationalOptions = [{companyName: "瀚高直签", id: "0"}]
      listCompany({
        pageNum: 1,
        pageSize: 20,
        businessScope: "SI", // 公司业务中带S的
        companyName: query
      }).then(response => {
        this.applicationDialog.operationalOptions = this.applicationDialog.operationalOptions.concat(response.rows);
        this.flag.operationalOptionsLoading = false;
      });
    },
    getOperational(selected) {
      const select = this.applicationDialog.operationalOptions.find(item => item.companyName == selected)
      this.applicationDialog.form.operationalId = select.id;
    },
    getIsvOptions(query) {
      this.flag.isvOptionsLoading = true
      listCompany({
        pageNum: 1,
        pageSize: 20,
        businessScope: "ISV", //公司业务中带ISV的
        companyName: query
      }).then(response => {
        this.applicationDialog.isvOptions = response.rows;
        this.flag.isvOptionsLoading = false;
      });
    },
    getIsv(selected) {
      const select = this.applicationDialog.isvOptions.find(item => item.companyName == selected)
      this.applicationDialog.form.isvId = select.id;
    },
    submitAppDialog() {
      this.$refs["applicationDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.data.applicationList = this.data.applicationList || [];
        if (!this.applicationDialog.form.id && !this.applicationDialog.form.tempId) {
          this.applicationDialog.form.tempId = new Date().getTime()//先打个标
          this.data.applicationList.push(this.applicationDialog.form)
        }
        // 替换
        this.data.applicationList.map((item, i) => {
          //带来的
          if (item.id && item.id == this.applicationDialog.form.id) {
            this.data.applicationList.splice(i, 1, this.applicationDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.applicationDialog.form.tempId) {
            this.data.applicationList.splice(i, 1, this.applicationDialog.form)
          }
        })
        this.applicationDialog.open = false;
      });
    },
    cancelAppDialog() {
      this.applicationDialog.open = false;
    },
    removeApplication(row) {
      let index = this.data.applicationList.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id //初始化来的还未点击过
        }
      })
      this.data.applicationList.splice(index, 1);
    },
    openQuotationDialog() {
      this.quotationDialog.currentEdit = {
        opportunityCode: this.data.licenseData.code,
        createBy: this.$store.state.user.name,
        opportunityStage: "1",
      }
      this.quotationDialog.form = {};
      this.quotationDialog.open = true;
      this.quotationDialog.title = "添加产品信息";
    },
    editQuotation(row) {
      this.quotationDialog.currentEdit = JSON.parse(JSON.stringify(row));
      this.quotationDialog.form = Object.assign({}, row.quotationMap);
      this.quotationDialog.open = true;
      this.quotationDialog.title = "修改产品信息";
    },
    selectProduct(val) {
      let select = this.dict.type.crm_opportunity_main_product.find(item => {
        return item.value == val
      })
      this.quotationDialog.form.productName = select.label;
      var _this = this;
      this.getNextOptions(select.raw.remark, function (res) {
        _this.quotationDialog.productVersion = res;
        _this.quotationDialog.productSecVersion = [];
      })
      this.$set(this.quotationDialog.form, 'productVersion', "");
      this.$set(this.quotationDialog.form, 'productSecVersion', "");
    },
    getNextOptions(dictCode, func) {
      this.getDicts(dictCode).then(res => {
        if (typeof func == 'function') {
          func(res.data);
        }
        return
      }).catch(error => {
        if (typeof func == 'function') {
          func([]);
        }
      })
    },
    selectProductVersion(val) {
      this.$set(this.quotationDialog.form, 'productSecVersion', "");
      let select = this.quotationDialog.productVersion.find(item => {
        return item.dictValue == val
      })
      var _this = this;
      this.getNextOptions(select.remark, function (res) {
        _this.quotationDialog.productSecVersion = res;
      })
    },
    submitDialogQuotationForm() {
      this.$refs["quotationDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.quotationDialog.currentEdit.quotationMap = this.quotationDialog.form;
        this.quotationDialog.currentEdit.quotationJson = JSON.stringify(this.quotationDialog.form);
        this.data.quotationList = this.data.quotationList || [];
        if (!this.quotationDialog.currentEdit.id && !this.quotationDialog.currentEdit.tempId) {
          this.quotationDialog.currentEdit.tempId = new Date().getTime();//先打个标
          this.data.quotationList.push(this.quotationDialog.currentEdit);
        }
        // 替换
        this.data.quotationList.map((item, i) => {
          //带来的
          if (item.id && item.id == this.quotationDialog.currentEdit.id) {
            this.data.quotationList.splice(i, 1, this.quotationDialog.currentEdit)
          }
          //新增的
          if (item.tempId && item.tempId == this.quotationDialog.currentEdit.tempId) {
            this.data.quotationList.splice(i, 1, this.quotationDialog.currentEdit)
          }
        })
        this.quotationDialog.open = false;
        //正常的复制 界面显示不刷新
        //let formbak = JSON.parse(JSON.stringify(this.data.applicationList));
        //this.data.applicationList = []
        //this.data.applicationList = JSON.parse(JSON.stringify(formbak));
      });
    },
    removeQuotation(row) {
      let index = this.data.quotationList.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId;//新建的或者点击过修改
        } else {
          return item.id == row.id; ////初始化来的还未点击过
        }
      })
      this.data.quotationList.splice(index, 1);
    },
    cancelDialogQuotation() {
      this.quotationDialog.open = false;
    },
    openCustDialog() {
      this.custInfoDialog.open = true;
    },
    cancelCustDialog() {
      this.custInfoDialog.open = false;
    },
    getListCompany() {
      this.flag.dialogTableLoading = true
      listCompany(this.custInfoDialog.form).then(response => {
        this.custInfoDialog.companyList = (response.rows);
        this.custInfoDialog.total = response.total;
        this.flag.dialogTableLoading = false;
      });
    },
    handleCustSelected(val) {
      this.custInfoDialog.selectedCompany = val;
    },
    submitDialogCustSelected() {
      if (!this.custInfoDialog.selectedCompany.id) {
        this.$modal.msgError("未选择公司的,请选择");
        return
      }
      this.data.licenseData.custCode = this.custInfoDialog.selectedCompany.code
      this.data.licenseData.custName = this.custInfoDialog.selectedCompany.companyName;
      this.custInfoDialog.open = false;
    },
    genderOppCode() {
      function add0(m) {
        return m < 10 ? '0' + m : m
      }

      function add00(m) {
        return m < 10 ? '00' + m : m < 100 ? '0' + m : m
      }

      const currentTime = new Date();
      let year = currentTime.getFullYear();
      let month = currentTime.getMonth() + 1,
        date = currentTime.getDate(),
        hours = currentTime.getHours(),
        minutes = currentTime.getMinutes(),
        seconds = currentTime.getSeconds(),
        milliseconds = currentTime.getMilliseconds();
      let code = "B" + year + add0(month) + add0(date) + "N" + add0(hours) + add0(minutes) + add0(seconds) + add00(milliseconds);
      this.data.code = this.data.licenseData.code = code;
      return code;
    },
    // 取消按钮
    cancel() {
      const obj = { path: "/crm/licenseapply" };
      this.$tab.closeOpenPage(obj);
    },
    /** 保存并更新按钮 */
    submitForm() {
      this.flag.applicationVerify = false;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.data.applicationList.length < 1) {
            this.flag.applicationVerify = true;
            return;
          }
          saveAndUpdateLicenseApply(this.fetchInformation()).then(res => {
            this.$modal.msgSuccess("操作成功");
            this.cancel();
          }).catch(e => {
            this.$modal.msgError("操作失败");
          });
        }
      });
    },
    fetchInformation(){
      let req = {}
      if (Object.keys(this.dataBak).length == 0) {
        req["code"] = this.data.code;
        req["licenseData"] = this.data.licenseData;
        req["quotationList_a"] = this.data.quotationList;
        req["applicationList_a"] = this.data.applicationList;
      } else {
        if (!this.objEqual(this.dataBak.licenseData,this.data.licenseData)){
          req['licenseData'] = this.data.licenseData;
        }
        req.applicationList_a = []
        req.applicationList_m = []
        req.applicationList_d = []
        for (let options of this.data.applicationList) {
          if (!options.id) {
            req.applicationList_a.push(options)
            continue;
          }
          let currentOld = this.dataBak.applicationList.find(item => { return item.id == options.id; })
          if (this.objEqual(currentOld, options)) {
            continue;
          }
          req.applicationList_m.push(options)
        }
        for (let options of this.dataBak.applicationList) {
          let current = this.data.applicationList.find(item => { return item.id == options.id; })
          if (current) {
            continue;
          }
          req.applicationList_d.push(options)
        }
        req.quotationList_a = []
        req.quotationList_m = []
        req.quotationList_d = []
        for (let options of this.data.quotationList) {
          if (!options.id) {
            req.quotationList_a.push(options)
            continue;
          }
          let currentOld = this.dataBak.quotationList.find(item => { return item.id == options.id; })
          if (this.objEqual(currentOld, options)) {
            continue;
          }
          req.quotationList_m.push(options)
        }
        for (let options of this.dataBak.quotationList) {
          let current = this.data.quotationList.find(item => { return item.id == options.id; })
          if (current) {
            continue;
          }
          req.quotationList_d.push(options)
        }
      }
      return req;
    },
    //对象属性全部平铺的情况
    objEqual(oldObj, newObj) {
      for (let key in oldObj) {
        // 不考虑new新加的标识属性
        if (typeof oldObj[key] == 'object') {
          if (!this.objEqual(oldObj[key], newObj[key])) {
            return false;
          }
          continue;
        }
        if (oldObj[key] !== newObj[key]) {
          return false;
        }
      }
      return true;
    },
  }
};
</script>
