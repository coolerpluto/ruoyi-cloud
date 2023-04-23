<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="商机Code" prop="opportunityCode">
        <el-input
          v-model="queryParams.opportunityCode"
          placeholder="请输入商机Code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名字" prop="policyName">
        <el-input
          v-model="queryParams.policyName"
          placeholder="请输入文件名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布截至" prop="releaseDate">
        <el-date-picker clearable
          v-model="queryParams.releaseDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发布时间">
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
          v-hasPermi="['crm:oppPolicy:add']"
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
          v-hasPermi="['crm:oppPolicy:edit']"
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
          v-hasPermi="['crm:oppPolicy:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:oppPolicy:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oppPolicyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商机Code" align="center" prop="opportunityCode" />
      <el-table-column label="文件ID" align="center" prop="policyId" />
      <el-table-column label="文件名字" align="center" prop="policyName" />
      <el-table-column label="发布时间" align="center" prop="releaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.releaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:oppPolicy:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:oppPolicy:remove']"
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

    <!-- 添加或修改商机支持政策对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="商机Code" prop="opportunityCode">
          <el-input v-model="form.opportunityCode" placeholder="请输入商机Code" />
        </el-form-item>
        <el-form-item label="文件名字" prop="policyName">
          <el-input v-model="form.policyName" placeholder="请输入文件名字" />
        </el-form-item>
        <el-form-item label="发布时间" prop="releaseDate">
          <el-date-picker clearable
            v-model="form.releaseDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间">
          </el-date-picker>
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
import { listOppPolicy, getOppPolicy, delOppPolicy, addOppPolicy, updateOppPolicy } from "@/api/crm/oppPolicy";

export default {
  name: "OppPolicy",
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
      // 商机支持政策表格数据
      oppPolicyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityCode: null,
        policyName: null,
        releaseDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        "opportunityCode": [
            { required: true, message: "未填写 商机code", trigger: "blur" }
          ],
        
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商机支持政策列表 */
    getList() {
      this.loading = true;
      listOppPolicy(this.queryParams).then(response => {
        this.oppPolicyList = response.rows;
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
        policyId: null,
        policyName: null,
        releaseDate: null
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
      this.title = "添加商机支持政策";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOppPolicy(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商机支持政策";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOppPolicy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOppPolicy(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机支持政策编号为"' + ids + '"的数据项？').then(function() {
        return delOppPolicy(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/oppPolicy/export', {
        ...this.queryParams
      }, `oppPolicy_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
