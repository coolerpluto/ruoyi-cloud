<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="联系人名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入联系人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属公司" prop="company">
        <el-input
          v-model="queryParams.company"
          placeholder="请输入所属公司"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机(主)" prop="priPhone">
        <el-input
          v-model="queryParams.priPhone"
          placeholder="请输入手机(主)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据归类" prop="sourceType">
        <el-select v-model="queryParams.sourceType" placeholder="请选择数据归类" clearable>
          <el-option
            v-for="dict in dict.type.crm_business_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['crm:contact:add']"
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
          v-hasPermi="['crm:contact:edit']"
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
        >批量转交
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
          v-hasPermi="['crm:contact:remove']"
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
          v-hasPermi="['crm:contact:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contactList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="联系人名" align="center" prop="name" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="性别" align="center" prop="sex" v-if="columns[1].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="所属公司" align="center" prop="company" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司角色" align="center" prop="roleInCompany" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司职务" align="center" prop="postInCompany" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司部门" align="center" prop="deptInCompany" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="手机(主)" align="center" prop="priPhone" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="手机(副)" align="center" prop="secPhone" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="邮件(主)" align="center" prop="priEmail" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="邮件(副)" align="center" prop="secEmail" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="联系地址" align="center" prop="addr" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="联系类型" align="center" prop="category" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="数据归类" align="center" prop="sourceType" v-if="columns[12].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_business_type" :value="scope.row.sourceType"/>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="remark" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="数据归属" align="center" prop="sourceId" v-if="columns[14].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="负责人" align="center" prop="ownerName" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="隶属部门" align="center" prop="deptName" v-if="columns[16].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建者" align="center" prop="createBy" v-if="columns[17].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[18].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新者" align="center" prop="updateBy" v-if="columns[19].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" v-if="columns[20].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:contact:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:contact:transfer']"
          >转交
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:contact:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改联系人对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="name">
              <el-input v-model="form.name" placeholder="请输入联系人名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属公司" prop="company">
              <el-input v-model="form.company" placeholder="请输入所属公司">
              <el-button slot="append" icon="el-icon-search" @click="openCompanyDialog">本系统查找</el-button>
</el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司角色" prop="roleInCompany">
              <el-input v-model="form.roleInCompany" placeholder="请输入所在公司的角色"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司职务" prop="postInCompany">
              <el-input v-model="form.postInCompany" placeholder="请输入所在公司的职务"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司部门" prop="deptInCompany">
              <el-input v-model="form.deptInCompany" placeholder="请输入所在公司的部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机(主)" prop="priPhone">
              <el-input v-model="form.priPhone" placeholder="请输入手机(主)"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机(副)" prop="secPhone">
              <el-input v-model="form.secPhone" placeholder="请输入手机(副号)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮件(主)" prop="priEmail">
              <el-input v-model="form.priEmail" placeholder="请输入主要使用的邮箱"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮件(副)" prop="secEmail">
              <el-input v-model="form.secEmail" placeholder="请输入次要邮箱"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系类型" prop="category">
              <el-input v-model="form.category" placeholder="请定义联系人的类型,例如:朋友,第一联系人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据归类" prop="sourceType">
              <el-select v-model="form.sourceType" placeholder="请选择数据归类">
                <el-option
                  v-for="dict in dict.type.crm_business_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系地址" prop="addr">
              <el-input v-model="form.addr" placeholder="请选择联系人的地址信息"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述" prop="remark">
              <el-input v-model="form.remark" placeholder="请添加其他描述,例如详细地址"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="companyDialog.title" :visible.sync="companyDialog.open" :show-close="false" label-width="150px"
      width="800px" append-to-body>
      <el-form ref="companyDialog.form" :model="companyDialog.form" @submit.native.prevent>
        <el-row>
          <el-col :span="15">
            <el-input v-model="companyDialog.form.companyName" placeholder="请输入公司名称关键字" clearable />
          </el-col>
          <el-col :span="5">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getListCompanys">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="companyDialog.form.companyName = ''">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
      <el-table v-loading="flag.dialogCompanyTableLoading" highlight-current-row @current-change="handleCurrentChange"
        :data="companyDialog.companys">
        <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true" />
        <el-table-column label="公司性质" align="center" prop="properties" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.properties" />
          </template>
        </el-table-column>
        <el-table-column label="公司法人" align="center" prop="legal" :show-overflow-tooltip="true" />
        <el-table-column label="公司地址" align="center" prop="addrDetail" :show-overflow-tooltip="true" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCompanyDialogForm">确 定</el-button>
        <el-button @click="cancelCompanyDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listContact, getContact, delContact, addContact, updateContact} from "@/api/crm/contact";
import {listAddr } from "@/api/system/addr";
import { listCompany } from "@/api/crm/company";

export default {
  name: "Contact",
  dicts: ['crm_business_type', 'sys_user_sex'],
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
      // 联系人表格数据
      contactList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        sex: null,
        company: null,
        roleInCompany: null,
        postInCompany: null,
        deptInCompany: null,
        priPhone: null,
        secPhone: null,
        priEmail: null,
        secEmail: null,
        addr: null,
        category: null,
        sourceType: null,
        remark: null,
        sourceId: null,
        ownerId: null,
        deptId: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "联系人名称不能为空", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "性别不能为空", trigger: "blur"}
        ],
        company: [
          {required: true, message: "所属公司不能为空", trigger: "blur"}
        ],
        priPhone: [
          {required: true, message: "手机(主)不能为空", trigger: "blur"}
        ],
      },
      // 列信息
      columns: [
        {key: 0, label: `联系人`, visible: true},
        {key: 1, label: `性别`, visible: true},
        {key: 2, label: `所属公司`, visible: true},
        {key: 3, label: `公司角色`, visible: false},
        {key: 4, label: `公司职务`, visible: false},
        {key: 5, label: `公司部门`, visible: false},
        {key: 6, label: `手机(主)`, visible: true},
        {key: 7, label: `手机(副)`, visible: false},
        {key: 8, label: `邮件(主)`, visible: true},
        {key: 9, label: `邮件(副)`, visible: false},
        {key: 10, label: `联系地址`, visible: false},
        {key: 11, label: `联系类型`, visible: false},
        {key: 12, label: `数据归类`, visible: false},
        {key: 13, label: `描述`, visible: false},
        {key: 14, label: `数据归属`, visible: false},
        {key: 15, label: `所有者`, visible: true},
        {key: 16, label: `持有部门`, visible: true},
        {key: 17, label: `创建者`, visible: false},
        {key: 18, label: `创建时间`, visible: false},
        {key: 19, label: `更新者`, visible: false},
        {key: 20, label: `更新时间`, visible: false},
      ],
      flag:{
        dialogCompanyTableLoading:false,
      },
      companyDialog: {
        // 弹出层标题
        title: "公司信息查找",
        // 是否显示弹出层
        open: false,
        form: {
          pageNum: 1,
          pageSize: 10,
          businessScope:"SCOPE", // 公司业务中带SCOPE的 任何公司联系人都可以
          companyName: ""
        },
        selectedCompany: {},
        companys: []
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    openCompanyDialog() {
      this.companyDialog.open = true;
    },
    getListCompanys() {
      this.flag.dialogCompanyTableLoading = true
      listCompany(this.companyDialog.form).then(response => {
        this.companyDialog.companys = (response.rows);
        this.companyDialog.total = response.total;
        this.flag.dialogCompanyTableLoading = false;
      });
    },
    handleCurrentChange(val) {
      this.companyDialog.selectedCompany = val
    },
    submitCompanyDialogForm() {
      if (!this.companyDialog.selectedCompany||Object.keys(this.companyDialog.selectedCompany).length == 0) {
        this.$modal.msgError("您还未选择公司信息");
        return;
      }
      this.form.company = this.companyDialog.selectedCompany.companyName;
      this.form.companyCode = this.companyDialog.selectedCompany.code;
      this.companyDialog.open = false;
    },
    cancelCompanyDialog() {
      this.companyDialog.open = false;
    },
    /** 查询联系人列表 */
    getList() {
      this.loading = true;
      listContact(this.queryParams).then(response => {
        this.contactList = response.rows;
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
        name: null,
        sex: null,
        company: null,
        roleInCompany: null,
        postInCompany: null,
        deptInCompany: null,
        priPhone: null,
        secPhone: null,
        priEmail: null,
        secEmail: null,
        addr: null,
        category: null,
        sourceType: null,
        remark: null,
        sourceId: null,
        ownerId: null,
        deptId: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
        updateTime: null
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
      this.title = "添加联系人";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContact(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改联系人";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContact(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContact(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除联系人编号为"' + ids + '"的数据项？').then(function () {
        return delContact(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/contact/export', {
        ...this.queryParams
      }, `contact_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
