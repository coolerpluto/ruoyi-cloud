<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="版本类型" prop="mediumType">
        <el-select v-model="queryParams.mediumType" placeholder="请选择版本类型" clearable>
          <el-option
            v-for="dict in dict.type.medium_version_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="数据库" prop="dbVersion">
        <el-select v-model="queryParams.dbVersion" placeholder="请选择数据库版本" clearable>
          <el-option
            v-for="dict in dict.type.medium_db_version"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="CPU版" prop="cpuType">
        <el-select v-model="queryParams.cpuType" placeholder="请选择CPU版本" clearable>
          <el-option
            v-for="dict in dict.type.medium_cpu_model"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="打包方式" prop="packageType">
        <el-select v-model="queryParams.packageType" placeholder="请选择打包方式" clearable>
          <el-option
            v-for="dict in dict.type.medium_package_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="含postgis" prop="hasPostgis">
        <el-select v-model="queryParams.hasPostgis" placeholder="请选择" clearable>
          <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="含签名" prop="hasSignature">
        <el-select v-model="queryParams.hasSignature" placeholder="请选择" clearable>
          <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
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
          v-hasPermi="['medium:medium:add']"
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
          v-hasPermi="['medium:medium:edit']"
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
          v-hasPermi="['medium:medium:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:medium:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mediumList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="记录ID" align="center" prop="id" />-->
      <el-table-column label="版本类型" align="center" prop="mediumType" />
      <el-table-column label="数据库版本" align="center" prop="dbVersion" />
      <el-table-column label="CPU版本" align="center" prop="cpuType" />
      <el-table-column label="打包方式" align="center" prop="packageType" />
      <el-table-column label="介质文件" align="center" prop="mediumFileId" />
      <el-table-column label="介质MD5文件" align="center" prop="mediumMd5FileId" />
      <el-table-column label="文件状态" align="center" prop="status" />
<!--      <el-table-column label="创建者ID" align="center" prop="createId" />-->
      <el-table-column label="创建者名" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
<!--      <el-table-column label="更新者ID" align="center" prop="updateId" />-->
      <el-table-column label="更新者名" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medium:medium:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:medium:remove']"
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

    <!-- 添加或修改介质文件信息记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMedium, getMedium, delMedium, addMedium, updateMedium } from "@/api/medium/medium";

export default {
  name: "Medium",
  dicts: ['sys_yes_no','medium_package_type', 'medium_version_type', 'medium_db_version', 'medium_cpu_model'],
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
      // 介质文件信息记录表格数据
      mediumList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mediumType: null,
        dbVersion: null,
        cpuType: null,
        packageType: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
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
    /** 查询介质文件信息记录列表 */
    getList() {
      this.loading = true;
      listMedium(this.queryParams).then(response => {
        this.mediumList = response.rows;
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
        mediumType: null,
        dbVersion: null,
        cpuType: null,
        packageType: null,
        mediumFileId: null,
        mediumMd5FileId: null,
        status: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
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
      this.title = "添加介质文件信息记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMedium(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改介质文件信息记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMedium(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMedium(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除介质文件信息记录编号为"' + ids + '"的数据项？').then(function() {
        return delMedium(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/medium/export', {
        ...this.queryParams
      }, `medium_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
