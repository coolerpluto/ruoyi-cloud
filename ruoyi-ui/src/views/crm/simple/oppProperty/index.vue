<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="字段名" prop="propertyKey">
        <el-input
          v-model="queryParams.propertyKey"
          placeholder="请输入字段名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商机code" prop="opportunityCode">
        <el-input
          v-model="queryParams.opportunityCode"
          placeholder="请输入商机code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模块归属" prop="model">
        <el-select v-model="queryParams.model" placeholder="请选择模块归属" clearable>
          <el-option
            v-for="dict in dict.type.crm_opportunity_data_model"
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
          v-hasPermi="['crm:oppProperty:add']"
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
          v-hasPermi="['crm:oppProperty:edit']"
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
          v-hasPermi="['crm:oppProperty:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:oppProperty:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oppPropertyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="字段名" align="center" prop="propertyKey" />
      <el-table-column label="字段值" align="center" prop="propertyVal" />
      <el-table-column label="商机code" align="center" prop="opportunityCode" />
      <el-table-column label="模块归属" align="center" prop="model">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_opportunity_data_model" :value="scope.row.model"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_show_hide" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:oppProperty:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:oppProperty:remove']"
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

    <!-- 添加或修改商机属性对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字段名" prop="propertyKey">
          <el-input v-model="form.propertyKey" placeholder="请输入字段名" />
        </el-form-item>
        <el-form-item label="商机code" prop="opportunityCode">
          <el-input v-model="form.opportunityCode" placeholder="请输入商机code" />
        </el-form-item>
        <el-form-item label="模块归属" prop="model">
          <el-select v-model="form.model" placeholder="请选择模块归属">
            <el-option
              v-for="dict in dict.type.crm_opportunity_data_model"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否启用" prop="status">
          <el-select v-model="form.status" placeholder="请选择是否启用">
            <el-option
              v-for="dict in dict.type.sys_show_hide"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { listOppProperty, getOppProperty, delOppProperty, addOppProperty, updateOppProperty } from "@/api/crm/oppProperty";

export default {
  name: "OppProperty",
  dicts: ['sys_show_hide', 'crm_opportunity_data_model'],
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
      // 商机属性表格数据
      oppPropertyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        propertyKey: null,
        opportunityCode: null,
        model: null,
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
    /** 查询商机属性列表 */
    getList() {
      this.loading = true;
      listOppProperty(this.queryParams).then(response => {
        this.oppPropertyList = response.rows;
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
        propertyKey: null,
        propertyVal: null,
        opportunityCode: null,
        model: null,
        status: null
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
      this.title = "添加商机属性";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOppProperty(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商机属性";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOppProperty(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOppProperty(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机属性编号为"' + ids + '"的数据项？').then(function() {
        return delOppProperty(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/oppProperty/export', {
        ...this.queryParams
      }, `oppProperty_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
