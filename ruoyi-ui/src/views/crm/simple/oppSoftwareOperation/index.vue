<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="商机Code" prop="opportunityCode">
        <el-input
          v-model="queryParams.opportunityCode"
          placeholder="请输入商机Code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用软件名字" prop="applicationName">
        <el-input
          v-model="queryParams.applicationName"
          placeholder="请输入应用软件名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="是否需要适配" prop="isvNeedAdapted">
        <el-select v-model="queryParams.isvNeedAdapted" placeholder="请选择是否需要适配" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item label="招标前完成度" prop="targetAdaptedProgress">
        <el-select v-model="queryParams.targetAdaptedProgress" placeholder="请选择招标前目标进度" clearable>
          <el-option
            v-for="dict in dict.type.sys_work_progress"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="当前适配进度" prop="currentAdaptedProgress">
        <el-select v-model="queryParams.currentAdaptedProgress" placeholder="请选择当前适配进度" clearable>
          <el-option
            v-for="dict in dict.type.sys_work_progress"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="运作主体名字" prop="operationalName">
        <el-input
          v-model="queryParams.operationalName"
          placeholder="请输入运作主体名字"
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
          v-hasPermi="['crm:oppSoftwareOperation:add']"
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
          v-hasPermi="['crm:oppSoftwareOperation:edit']"
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
          v-hasPermi="['crm:oppSoftwareOperation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:oppSoftwareOperation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oppSoftwareOperationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商机Code" align="center" prop="opportunityCode" />
      <el-table-column label="应用软件名字" align="center" prop="applicationName" />
      <el-table-column label="是否需要适配" align="center" prop="isvNeedAdapted">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isvNeedAdapted"/>
        </template>
      </el-table-column>
      <el-table-column label="招标前目标进度" align="center" prop="targetAdaptedProgress">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.targetAdaptedProgress"/>
        </template>
      </el-table-column>
      <el-table-column label="当前适配进度" align="center" prop="currentAdaptedProgress">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.currentAdaptedProgress"/>
        </template>
      </el-table-column>
      <el-table-column label="运作主体名字" align="center" prop="operationalName" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="更新人" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:oppSoftwareOperation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:oppSoftwareOperation:remove']"
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

    <!-- 添加或修改商机应用运营信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商机Code" prop="opportunityCode">
          <el-input v-model="form.opportunityCode" placeholder="请输入商机Code" />
        </el-form-item>
        <el-form-item label="应用软件名字" prop="applicationName">
          <el-input v-model="form.applicationName" placeholder="请输入应用软件名字" />
        </el-form-item>
        <el-form-item label="是否需要适配" prop="isvNeedAdapted">
          <el-select v-model="form.isvNeedAdapted" placeholder="请选择是否需要适配">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="招标前目标进度" prop="targetAdaptedProgress">
          <el-select v-model="form.targetAdaptedProgress" placeholder="请选择招标前目标进度">
            <el-option
              v-for="dict in dict.type.sys_work_progress"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前适配进度" prop="currentAdaptedProgress">
          <el-select v-model="form.currentAdaptedProgress" placeholder="请选择当前适配进度">
            <el-option
              v-for="dict in dict.type.sys_work_progress"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运作主体名字" prop="operationalName">
          <el-input v-model="form.operationalName" placeholder="请输入运作主体名字" />
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
import { listOppSoftwareOperation, getOppSoftwareOperation, delOppSoftwareOperation, addOppSoftwareOperation, updateOppSoftwareOperation } from "@/api/crm/oppSoftwareOperation";

export default {
  name: "OppSoftwareOperation",
  dicts: ['sys_yes_no', 'sys_work_progress'],
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
      // 商机应用运营信息表格数据
      oppSoftwareOperationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityCode: null,
        applicationName: null,
        isvNeedAdapted: null,
        targetAdaptedProgress: null,
        currentAdaptedProgress: null,
        operationalName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商机应用运营信息列表 */
    getList() {
      this.loading = true;
      listOppSoftwareOperation(this.queryParams).then(response => {
        this.oppSoftwareOperationList = response.rows;
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
        applicationId: null,
        applicationName: null,
        isvNeedAdapted: null,
        targetAdaptedProgress: null,
        currentAdaptedProgress: null,
        operationalId: null,
        operationalName: null,
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
      this.title = "添加商机应用运营信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOppSoftwareOperation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商机应用运营信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOppSoftwareOperation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOppSoftwareOperation(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机应用运营信息编号为"' + ids + '"的数据项？').then(function() {
        return delOppSoftwareOperation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/oppSoftwareOperation/export', {
        ...this.queryParams
      }, `oppSoftwareOperation_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
