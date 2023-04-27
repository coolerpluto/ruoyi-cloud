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
        <treeselect v-model="queryParams.deptId" style="width: 215px" :options="deptOptions" :show-count="true"
                    placeholder="请选择归属部门"/>
      </el-form-item>
      <el-form-item label="主负责人" prop="ownerId">
        <el-select v-model="queryParams.ownerId" collapse-tags placeholder="请输入 关键字拼音检索" filterable remote
                   :remote-method="getPersonOptions" :loading="flag.personOptionsLoading">
          <el-option v-for="item in personOptions" :key="item.userName" :label="item.nickName" :value="item.userId">
            <span style="float: left">{{ item.nickName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ item.dept.deptName }}</span>
          </el-option>
        </el-select>
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
      <el-table-column label="商机Code" align="center" prop="code" width="180"/>
      <el-table-column label="商机名称" align="center" prop="name"/>
      <el-table-column label="用户名称" align="center" prop="custName"/>
      <el-table-column label="需求时间" align="center" prop="useTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.useTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用途" align="center" prop="purposes" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_lic_purposes_type" :value="scope.row.purposes"/>
        </template>
      </el-table-column>
      <el-table-column label="主负责人" align="center" prop="nickName" width="120"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:licenseApply:edit']"> 修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-if="$store.getters.name == scope.row.ownerName" v-hasPermi="['crm:licenseApply:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
  </div>
</template>

<script>
import {listLicenseApply, delLicenseApply} from "@/api/crm/licenseApply";
import {listEmployee, deptTreeSelect} from "@/api/crm/employee";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "LicenseApply",
  components: {Treeselect},
  dicts: ['crm_lic_purposes_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      codes: [],
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
      personOptions: [],
      deptOptions: [],
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
      flag: {
        transferTargetPersonLoading: false,
        selectedUnBeLongYou: false,
        personOptionsLoading: false,
      },
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
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
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    getPersonOptions(query) {
      this.flag.personOptionsLoading = true
      listEmployee({
        pageNum: 1,
        pageSize: 20,
        userName: query,
      }).then(response => {
        this.personOptions = response.rows;
        this.flag.personOptionsLoading = false;
      });
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
      this.codes = selection.map(item => item.code)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.flag.selectedUnBeLongYou = selection.findIndex(item => {
        return item.ownerName != this.$store.getters.name
      }) > -1 ? true : false;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push("/crm/licenseApply-data/index/0/A");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const code = row.code || this.codes;
      if (typeof code != 'string') {
        if (this.flag.selectedUnBeLongYou) {
          this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
          return;
        }
      } else if (this.$store.getters.name != row.ownerName) {
        this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
        return;
      }
      this.$router.push("/crm/licenseApply-data/index/" + code + "/M");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const codes = row.code || this.codes;
      if (typeof codes != 'string') {
        if (this.flag.selectedUnBeLongYou) {
          this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
          return;
        }
      } else if (this.$store.getters.name != row.ownerName) {
        this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
        return;
      }
      this.$modal.confirm('是否确认删除license申请编码为"' + codes + '"的数据项？').then(function () {
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
