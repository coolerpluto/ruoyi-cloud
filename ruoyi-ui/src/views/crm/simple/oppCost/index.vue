<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="商机Code" prop="opportunityCode">
        <el-input
          v-model="queryParams.opportunityCode"
          placeholder="请输入商机code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上限金额" prop="cost">
        <el-input
          v-model="queryParams.cost"
          placeholder="请输入花费金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="截至时间" prop="useTime">
        <el-date-picker clearable
          v-model="queryParams.useTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择使用时间">
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
          v-hasPermi="['crm:oppCost:add']"
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
          v-hasPermi="['crm:oppCost:edit']"
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
          v-hasPermi="['crm:oppCost:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:oppCost:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oppCostList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商机code" align="center" prop="opportunityCode" />
      <el-table-column label="花费金额" align="center" prop="cost" />
      <el-table-column label="使用时间" align="center" prop="useTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.useTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用原因" align="center" prop="reason" />
      <el-table-column label="填报时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:oppCost:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:oppCost:remove']"
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

    <!-- 添加或修改商机花费对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="商机code" prop="opportunityCode">
          <el-input v-model="form.opportunityCode" placeholder="请输入商机code" />
        </el-form-item>
        <el-form-item label="花费金额" prop="cost">
          <el-input v-model="form.cost" placeholder="请输入花费金额" />
        </el-form-item>
        <el-form-item label="使用时间" prop="useTime">
          <el-date-picker clearable
            v-model="form.useTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择使用时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="使用原因" prop="reason">
              <el-input v-model="form.reason" type="textarea" resize="none" :rows="2" />
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
import { listOppCost, getOppCost, delOppCost, addOppCost, updateOppCost } from "@/api/crm/oppCost";

export default {
  name: "OppCost",
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
      // 商机花费表格数据
      oppCostList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityCode: null,
        cost: null,
        useTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        "opportunityCode": [
            { required: true, message: "未填写 商机code", trigger: "blur" }
          ],
        
        "cost":  [
            { required: true, message: "未填写 花费金额", trigger: "blur" }
          ],
        
        "useTime":  [
            { required: true, message: "未填写 使用时间", trigger: "blur" }
          ],
        
        "reason": [
            { required: true, message: "未填写 使用原因", trigger: "blur" }
          ],
        
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商机花费列表 */
    getList() {
      this.loading = true;
      listOppCost(this.queryParams).then(response => {
        this.oppCostList = response.rows;
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
        cost: null,
        inputUserId: null,
        useTime: null,
        reason: null,
        createTime: null,
        creatorId: null,
        operatorId: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商机花费";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOppCost(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商机花费";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOppCost(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOppCost(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机花费编号为"' + ids + '"的数据项？').then(function() {
        return delOppCost(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/oppCost/export', {
        ...this.queryParams
      }, `oppCost_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
