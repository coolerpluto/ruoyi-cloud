<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="商机code" prop="opportunityCode">
        <el-input
          v-model="queryParams.opportunityCode"
          placeholder="请输入商机code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前阶段" prop="stage">
        <el-select v-model="queryParams.stage" placeholder="请选择当前阶段" clearable>
          <el-option
            v-for="dict in dict.type.crm_opportunity_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进展时间" prop="advancesDate">
        <el-date-picker clearable
          v-model="queryParams.advancesDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择进展时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="提报人" prop="creator">
        <el-select v-model="queryParams.creator" clearable placeholder="请输入 关键字检索" filterable remote
          :remote-method="getPersonOptions" :loading="flag.transferTargetPersonLoading">
          <el-option v-for="item in personOptions" :key="item.userName" :label="item.nickName" :value="item.userName">
            <span style="float: left">{{ item.nickName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ item.dept.deptName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="提报时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择提报时间">
        </el-date-picker>
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
          v-hasPermi="['crm:oppAdvances:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['crm:oppAdvances:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['crm:oppAdvances:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:oppAdvances:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oppAdvancesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商机code" align="center" prop="opportunityCode" />
      <el-table-column label="当前阶段" align="center" prop="stage">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.stage"/>
        </template>
      </el-table-column>
      <el-table-column label="进展内容" align="center" prop="advances" />
      <el-table-column label="进展时间" align="center" prop="advancesDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.advancesDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提报人" align="center" prop="creator" />
      <el-table-column label="提报时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:oppAdvances:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:oppAdvances:remove']"
          >删除</el-button>
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

    <!-- 添加或修改商机进展对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商机code" prop="opportunityCode">
          <el-input v-model="form.opportunityCode" placeholder="请输入商机code" />
        </el-form-item>
        <el-form-item label="当前阶段" prop="stage">
          <el-select v-model="form.stage" placeholder="请选择当前阶段">
            <el-option
              v-for="dict in dict.type.crm_opportunity_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="进展时间" prop="advancesDate">
          <el-date-picker clearable
            v-model="form.advancesDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择进展时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="提报人" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入提报人" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOppAdvances, getOppAdvances, delOppAdvances, addOppAdvances, updateOppAdvances } from "@/api/crm/oppAdvances";
import { listEmployee } from "@/api/crm/employee";

export default {
  name: "OppAdvances",
  dicts: ['crm_opportunity_status'],
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
      // 商机进展表格数据
      oppAdvancesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityCode: null,
        stage: null,
        advancesDate: null,
        creator: null,
        createTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      flag:{
        transferTargetPersonLoading:false,
      },
      personOptions:[],

    };
  },
  created() {
    this.getList();
  },
  methods: {
    getPersonOptions(query) {
      this.flag.transferTargetPersonLoading = true
      listEmployee({
        pageNum: 1,
        pageSize: 20,
        nickName: query,
      }).then(response => {
        this.personOptions = response.rows;
        this.flag.transferTargetPersonLoading = false;
      });
    },
    /** 查询商机进展列表 */
    getList() {
      this.loading = true;
      listOppAdvances(this.queryParams).then(response => {
        this.oppAdvancesList = response.rows;
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
        opportunityCode: null,
        stage: null,
        advances: null,
        advancesDate: null,
        creator: null,
        createTime: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商机进展";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOppAdvances(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商机进展";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOppAdvances(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOppAdvances(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机进展编号为"' + ids + '"的数据项？').then(function() {
        return delOppAdvances(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/oppAdvances/export', {
        ...this.queryParams
      }, `oppAdvances_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
