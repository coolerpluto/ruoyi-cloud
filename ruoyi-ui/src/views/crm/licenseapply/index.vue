<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="商机Code" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入商机Code" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="商机名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入商机名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="客户名称" prop="custName">
        <el-input v-model="queryParams.custName" placeholder="请输入客户名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="用途" prop="purposes">
        <el-select v-model="queryParams.purposes" placeholder="请选择用途" clearable>
          <el-option v-for="dict in dict.type.crm_lic_purposes_type" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="隶属部门" prop="deptId">
        <el-input v-model="queryParams.deptId" placeholder="请输入商机所在部门" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="主负责人" prop="ownerId">
        <el-input v-model="queryParams.ownerId" placeholder="请输入商机负责人员" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="daterangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['crm:licenseApply:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:licenseApply:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:licenseApply:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:licenseApply:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="licenseApplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="商机Code" align="center" prop="code"/>
      <el-table-column label="商机名称" align="center" prop="name"/>
      <el-table-column label="用户名称" align="center" prop="custName"/>
      <el-table-column label="需求时间" align="center" prop="useTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.useTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用途" align="center" prop="purposes">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_lic_purposes_type" :value="scope.row.purposes"/>
        </template>
      </el-table-column>
      <el-table-column label="主负责人" align="center" prop="ownerName"/>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="更新时间" align="center" prop="updateTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:licenseApply:edit']"> 修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['crm:licenseApply:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改license申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="88px">
        <h3>基本信息</h3>
        <el-divider/>
        <el-row>
          <el-col :span="8">
            <el-form-item label="商机名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入商机名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用途" prop="purposes">
              <el-select v-model="form.purposes" placeholder="请选择用途">
                <el-option v-for="dict in dict.type.crm_lic_purposes_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商机Code" prop="code">
              <label placeholder="自动生产商机编码">{{ form.code || genderOppCode() }}</label>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="应用信息">
            <div class="el-form-item__error" v-if="flag.applicationVerify">应用运营信息最少有一条</div>
            <el-button style="float:right;" type="info" icon="el-icon-plus" @click="openAppDialog">添加新条目</el-button>
            <el-table :data="form.operations">
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
          <el-col :span="12">
            <el-form-item label="客户名称" prop="custName">
              <div class="el-form-item__error" v-if="flag.custVerify">未正确获得用户信息</div>
              <el-input v-model="form.custName" id="formCustName"
                        placeholder="请选择客户公司名称">
                <el-button slot="append" icon="el-icon-search" @click="openCustDialog">选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="需求时间" prop="useTime">
              <el-date-picker clearable v-model="form.useTime" type="date" value-format="yyyy-MM-dd"
                              placeholder="请选择用户需求时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <h3>产品信息</h3>
        <el-divider/>
        <el-row>
          <el-button style="float:right;" type="info" @click="openQuotationDialog" icon="el-icon-plus">添加报价</el-button>
          <el-table :data="form.quotationInfo">
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="baseInfoDialog.title" :visible.sync="baseInfoDialog.open" width="1000px" append-to-body>
      <el-form ref="baseInfoDialog.form" :model="baseInfoDialog.form" :rules="baseInfoDialog.rules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户系统名称" prop="applicationName">
              <el-input v-model="baseInfoDialog.form.applicationName" placeholder="请输入应用名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在用数据库" prop="dbBase">
              <el-select v-model="baseInfoDialog.form.dbBase" placeholder="请选择当前在用的数据库">
                <el-option v-for="dict in dict.type.sys_system_db_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="软件开发商" prop="isv">
              <el-select v-model="baseInfoDialog.form.isv" @change="getIsv" placeholder="请输入关键字" filterable remote
                         :remote-method="getIsvOptions" :loading="flag.isvOptionsLoading">
                <el-option v-for="item in baseInfoDialog.isvOptions" :key="item.id" :label="item.companyName"
                           :value="item.companyName">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目运作主体" prop="operationalName">
              <el-select v-model="baseInfoDialog.form.operationalName" @change="getOperational" placeholder="请输入关键字"
                         filterable remote :remote-method="getOperationalOptions"
                         :loading="flag.operationalOptionsLoading">
                <el-option v-for="item in baseInfoDialog.operationalOptions" :key="item.id" :label="item.companyName"
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
              <el-select v-model="quotationDialog.form.productCode" @change="selectProducted">
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
                :data="custInfoDialog.companys">
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
import {
  listLicenseApply, getLicenseApply, delLicenseApply, addLicenseApply, updateLicenseApply
} from "@/api/crm/licenseApply";
import {listCompany} from "@/api/crm/company";
import {getOppQuotationInfo} from "@/api/crm/oppUnitedInfo"

export default {
  name: "LicenseApply",
  dicts: ['crm_lic_purposes_type', 'crm_opportunity_main_product', 'sys_system_db_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // license申请表格数据
      licenseApplyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // $comment时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        custName: null,
        purposes: null,
        deptId: null,
        ownerId: null,
        createTime: null,
      },
      // 表单参数
      form: {
        quotationInfo: []
      },
      // 表单校验
      rules: {
        name: [
          {required: true, message: "商机名称不能为空", trigger: "blur"}
        ],
        custName: [
          {required: true, message: "客户名称不能为空", trigger: "blur"}
        ],
        purposes: [
          {required: true, message: "用途不能为空", trigger: "change"}
        ],
      },
      flag: {
        applicationVerify: false,
        custVerify: false,
        dialogTableLoading: false,
        isvOptionsLoading: false,
        operationalOptionsLoading: false,
      },
      custInfoDialog: {
        form: {},
        companys: [],
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
      baseInfoDialog: {
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
    this.getList();
  },
  methods: {
    openAppDialog() {
      this.baseInfoDialog.form = {}
      this.baseInfoDialog.open = true;
      this.baseInfoDialog.title = "添加应用信息";
      this.getOperationalOptions();
      this.getIsvOptions();
    },
    editApplication(row) {
      this.baseInfoDialog.form = Object.assign({}, row);
      this.baseInfoDialog.open = true;
      this.baseInfoDialog.title = "修改应用信息";
    },
    getOperationalOptions(query) {
      this.flag.operationalOptionsLoading = true
      this.baseInfoDialog.operationalOptions = [{companyName: "瀚高直签", id: "0"}]
      listCompany({
        pageNum: 1,
        pageSize: 20,
        businessScope: "SI", // 公司业务中带S的
        companyName: query
      }).then(response => {
        this.baseInfoDialog.operationalOptions = this.baseInfoDialog.operationalOptions.concat(response.rows);
        this.flag.operationalOptionsLoading = false;
      });
    },
    getOperational(selected) {
      const select = this.baseInfoDialog.operationalOptions.find(item => item.companyName == selected)
      this.baseInfoDialog.form.operationalId = select.id;
    },
    getIsvOptions(query) {
      this.flag.isvOptionsLoading = true
      listCompany({
        pageNum: 1,
        pageSize: 20,
        businessScope: "ISV", //公司业务中带ISV的
        companyName: query
      }).then(response => {
        this.baseInfoDialog.isvOptions = response.rows;
        this.flag.isvOptionsLoading = false;
      });
    },
    getIsv(selected) {
      const select = this.baseInfoDialog.isvOptions.find(item => item.companyName == selected)
      this.baseInfoDialog.form.isvId = select.id;
    },
    submitAppDialog() {
      this.$refs["baseInfoDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.form.operations = this.form.operations || [];
        if (!this.baseInfoDialog.form.id && !this.baseInfoDialog.form.tempId) {
          this.baseInfoDialog.form.tempId = new Date().getTime()//先打个标
          this.form.operations.push(this.baseInfoDialog.form)
        }
        // 替换
        this.form.operations.map((item, i) => {
          //带来的
          if (item.id && item.id == this.baseInfoDialog.form.id) {
            this.form.operations.splice(i, 1, this.baseInfoDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.baseInfoDialog.form.tempId) {
            this.form.operations.splice(i, 1, this.baseInfoDialog.form)
          }
        })
        this.baseInfoDialog.open = false;
      });
    },
    cancelAppDialog() {
      this.baseInfoDialog.open = false;
    },
    removeApplication(row) {
      let index = this.form.operations.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.form.operations.splice(index, 1);
    },
    openQuotationDialog() {
      this.quotationDialog.currentEdit = {
        opportunityCode: this.form.code,
        createBy: this.$store.state.user.name,
        quotationStage: "1",
        quotationInfo: []
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
    selectProducted(val) {
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
        this.form.quotationInfo = this.form.quotationInfo || [];
        if (!this.quotationDialog.currentEdit.id && !this.quotationDialog.currentEdit.tempId) {
          this.quotationDialog.currentEdit.tempId = new Date().getTime();//先打个标
          this.form.quotationInfo.push(this.quotationDialog.currentEdit);
        }
        // 替换
        this.form.quotationInfo.map((item, i) => {
          //带来的
          if (item.id && item.id == this.quotationDialog.currentEdit.id) {
            this.form.quotationInfo.splice(i, 1, this.quotationDialog.currentEdit)
          }
          //新增的
          if (item.tempId && item.tempId == this.quotationDialog.currentEdit.tempId) {
            this.form.quotationInfo.splice(i, 1, this.quotationDialog.currentEdit)
          }
        })
        this.quotationDialog.open = false;
        //正常的复制 界面显示不刷新
        let formbak = JSON.parse(JSON.stringify(this.form));
        this.form = {};
        this.form = JSON.parse(JSON.stringify(formbak));
      });
    },
    removeQuotation(row) {
      let index = this.form.quotationInfo.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId;//新建的或者点击过修改
        } else {
          return item.id == row.id; ////初始化来的还未点击过
        }
      })
      this.form.quotationInfo.splice(index, 1);
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
        this.custInfoDialog.companys = (response.rows);
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
      this.form.custCode = this.custInfoDialog.selectedCompany.code
      this.form.custName = this.custInfoDialog.selectedCompany.companyName;
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
      this.form.code = code;
      return code;
    },
    getOppQuotationInfo(func) {
      if (!this.form.code || this.form.code === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppQuotationInfo({code: this.form.code}).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.form.quotationInfo = response.data;
        this.form.quotationInfo.forEach(e => {
          e.quotationMap = JSON.parse(e.quotationJson);
        })
        if (typeof func == 'function') {
          func();
        }
      })
    },
    /** 查询license申请列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listLicenseApply(this.queryParams).then(response => {
        this.licenseApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        custCode: null,
        custName: null,
        useTime: null,
        purposes: null,
        deptId: null,
        ownerId: null,
        sharedId: null,
        sharedDeptId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加license申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLicenseApply(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改license申请";
        this.getOppQuotationInfo();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLicenseApply(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLicenseApply(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除license申请编号为"' + ids + '"的数据项？').then(function () {
        return delLicenseApply(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/licenseApply/export', {
        ...this.queryParams
      }, `licenseApply_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
