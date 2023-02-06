<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="客户公司" prop="companyName">
        <el-input v-model="queryParams.companyName" placeholder="请输入公司名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="rangeCreateDate" style="width: 340px" value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']"></el-date-picker>
      </el-form-item>
      <el-form-item label="创建者部门" prop="deptIds">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请选择创建者部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者" prop="createIds">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请选择创建者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归属者" prop="ownerIds">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请选择归属者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['crm:company:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['crm:company:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-tickets"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['crm:contact:transfer']"
        >批量交接
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['crm:company:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:company:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="companyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="客户编码" align="center" prop="code" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司名称" align="center" prop="companyName" v-if="columns[0].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司性质" align="center" prop="properties" v-if="columns[1].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.properties"/>
        </template>
      </el-table-column>
      <el-table-column label="行业归属" align="center" prop="industry" v-if="columns[2].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_company_industry_type" :value="scope.row.industry"/>
        </template>
      </el-table-column>
      <el-table-column label="公司法人" align="center" prop="legal" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="注册资金" align="center" prop="capitalReg" v-if="columns[4].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">{{ scope.row.capitalReg }} 万元</template>
      </el-table-column>
      <el-table-column label="缴纳方式" align="center" prop="capitalPayType" v-if="columns[5].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_capital_pay_type" :value="scope.row.capitalPayType"/>
        </template>
      </el-table-column>
      <el-table-column label="公司官网" align="center" prop="website" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="地址代号" align="center" prop="addr" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="公司业务" align="center" prop="businessScope" v-if="columns[9].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_companny_business_scope" :value="scope.row.businessScope"/>
        </template>
      </el-table-column>
      <el-table-column label="公司描述" align="center" prop="remark" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="所有者" align="center" prop="ownerName" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="所有者部门" align="center" prop="deptName" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建者" align="center" prop="createBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[14].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新者" align="center" prop="updateBy" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新者时间" align="center" prop="updateTime" v-if="columns[16].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-zoom-in"
            @click="handleDetail(scope.row)"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:company:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:company:transfer']"
          >交接
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:company:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
    <!-- 添加或修改共享规则配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <div id="baseCompanyArea">
        <label style="font-size: large;">客户基本信息</label>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="baseCompany">
          <el-row>
            <el-col :span="12">
              <el-form-item label="公司名称" prop="companyName">
                <el-input v-model="form.companyName" placeholder="请输入公司名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公司性质" prop="properties">
                <el-select v-model="form.properties" placeholder="请选择公司性质" clearable>
                  <el-option v-for="dict in dict.type.crm_company_properties_type" :key="dict.value" :label="dict.label"
                             :value="dict.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="行业归属" prop="industry">
                <el-select v-model="form.industry" placeholder="请选择公司归属行业" clearable>
                  <el-option v-for="dict in dict.type.crm_company_industry_type" :key="dict.value" :label="dict.label"
                             :value="dict.value"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公司法人" prop="legal">
                <el-input v-model="form.legal" placeholder="请选择输入公司法人"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="注册资金" prop="capitalReg">
                <el-input type=”number” v-model="form.capitalReg" placeholder="请输入公司注册资金">
                  <template slot="append">万元</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="缴纳方式" prop="capitalPayType">
                <el-select v-model="form.capitalPayType" placeholder="请选择公司注册资金缴纳方式" clearable>
                  <el-option v-for="dict in dict.type.crm_capital_pay_type" :key="dict.value" :label="dict.label"
                             :value="dict.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公司官网" prop="website">
                <el-input v-model="form.website" placeholder="请输入公司官网地址"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="业务范围" prop="businessScope">
                <el-select v-model="form.businessScope" placeholder="请选择公司业务范围" clearable>
                  <el-option v-for="dict in dict.type.crm_companny_business_scope" :key="dict.value" :label="dict.label"
                             :value="dict.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公司地址" prop="addr">
                <el-cascader :props="addrProps" v-model="form.addr" clearable placeholder="请选择公司地址">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="详细地址" prop="addrDetail">
                <el-input v-model="form.addrDetail" placeholder="请输入公司其他描述"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="公司描述" prop="remark">
                <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入公司其他描述"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-steps :active="companyDetailActive" align-center simple v-if="baseCompany">
        <el-step title="客户商机管理" icon="el-icon-upload" @click.native="companyDetailActive=1"/>
        <el-step title="联系人管理" icon="el-icon-picture" @click.native="companyDetailActive=2"/>
        <el-step title="客户重点应用" icon="el-icon-picture" @click.native="companyDetailActive=3"/>
      </el-steps>
      <div id="stepsArea" v-if="baseCompany">
        <div id="customerOppArea" v-if="companyDetailActive===1">

        </div>
        <div id="customerContactArea" v-if="companyDetailActive===2">

        </div>
        <div id="customerAppArea" v-if="companyDetailActive===3">

        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    客户管理开发中。。。
    完成：客户基本信息 新增 修改 删除
    待完成：商机关联查询 客户联系人关联管理 客户应用管理管理
  </div>
</template>

<script>
import {listUser, deptTreeSelect} from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listCompany, getCompany, delCompany, addCompany, updateCompany} from "@/api/crm/company";
import {listAddr} from "@/api/system/addr";
import {getDicts as getDicts} from '@/api/system/dict/data'

export default {
  name: "customer",
  dicts: ['crm_companny_business_scope', 'crm_company_industry_type', 'crm_company_properties_type', 'crm_capital_pay_type'],
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
      // 基础客户信息禁止编辑
      baseCompany: false,
      //点击详情时展示的页签
      companyDetailActive: 1,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公司表格数据
      companyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      rangeCreateDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyName: [
          {required: true, message: "公司名称不能为空", trigger: "blur"},
        ],
        capitalReg: [
          {type: "number", message: "请输入正确的数字", trigger: ["blur"], transform: (value) => Number(value)}
        ],
      },
      // 列信息
      columns: [
        {key: 0, label: `公司名称`, visible: true},
        {key: 1, label: `公司性质`, visible: false},
        {key: 2, label: `行业归属`, visible: true},
        {key: 3, label: `公司法人`, visible: false},
        {key: 4, label: `注册资金`, visible: false},
        {key: 5, label: `缴纳方式`, visible: false},
        {key: 6, label: `客户编码`, visible: true},
        {key: 7, label: `公司官网`, visible: false},
        {key: 8, label: `地址代号`, visible: false},
        {key: 9, label: `公司业务`, visible: false},
        {key: 10, label: `公司描述`, visible: false},
        {key: 11, label: `所有者`, visible: false},
        {key: 12, label: `所有者部门`, visible: false},
        {key: 13, label: `创建者`, visible: false},
        {key: 14, label: `创建时间`, visible: true},
        {key: 15, label: `更新者`, visible: false},
        {key: 16, label: `更新时间`, visible: true},
      ],
      // 部门树选项
      deptOptions: undefined,
      // 用户列表
      userList: [],
      addrProps: {
        lazy: true,
        value: 'code',
        label: 'name',
        children: 'children',
        checkStrictly: true,
        lazyLoad: function (node, resolve) {
          let addrParams = {
            level: 'province',
          }
          if (node.data) {
            addrParams.parent = node.data.code;
            switch (node.data.level) {
              case 'province':
                addrParams.level = 'city';
                break;
              case 'city':
                addrParams.level = 'area';
                break;
              case 'area':
                addrParams.level = 'street';
                break;
            }
          }
          listAddr(addrParams).then(response => {
            resolve(response.data)
          })
        }
      },
    };
  },
  created() {
    this.getList();
    //this.getDeptTree();
  },
  methods: {
    getAddrSelectOptions(node, resolve) {
      const {level} = node;
      let id = 0;
      setTimeout(() => {
        const nodes = Array.from({length: level + 1})
          .map(item => ({
            value: ++id,
            label: `选项${id}`,
            leaf: level >= 2
          }));
        // 通过调用resolve将子节点数据返回，通知组件数据加载完成
        resolve(nodes);
      }, 1000);
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      listCompany(this.queryParams).then(response => {
        this.companyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    handleUserQuery(param) {
      listUser({userName: param}).then(response => {
        this.userList = response.rows;
      });
    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 查看客户详情
    handleDetail(row) {
      this.baseCompany = true
      getCompany(row.id).then(response => {
        this.form = response.data;
        this.form.addr = this.form.addr ? this.form.addr.split(',') : this.form.addr;
        this.open = true;
        this.title = "客户相关信息详情";
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCompany(id).then(response => {
        this.form = response.data;
        this.form.addr = this.form.addr ? this.form.addr.split(',') : this.form.addr;
        this.open = true;
        this.title = "修改客户基本信息";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.baseCompany = false
      this.reset();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addr) {
            this.form.addr = this.form.addr.toString();
          }
          if (this.form.id != null) {
            updateCompany(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.code = 'CUSTOMER_' + Date.parse(new Date());
            addCompany(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除公司编号为"' + ids + '"的数据项？').then(function () {
        return delCompany(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/company/export', {
        ...this.queryParams
      }, `company_${new Date().getTime()}.xlsx`)
    }
  },
};
</script>
