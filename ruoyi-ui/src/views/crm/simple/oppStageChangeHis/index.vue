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
      <el-form-item label="老阶段" prop="sourceStage">
        <el-select v-model="queryParams.sourceStage" placeholder="请选择老阶段" clearable>
          <el-option
            v-for="dict in dict.type.crm_opportunity_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="新阶段" prop="targetStage">
        <el-select v-model="queryParams.targetStage" placeholder="请选择新阶段" clearable>
          <el-option
            v-for="dict in dict.type.crm_opportunity_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="转变截至" prop="changeTime">
        <el-date-picker clearable
          v-model="queryParams.changeTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择转变时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="操作员名" prop="operatorName">
        <el-input
          v-model="queryParams.operatorName"
          placeholder="请输入操作员名"
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
          v-hasPermi="['crm:oppStageChangeHis:add']"
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
          v-hasPermi="['crm:oppStageChangeHis:edit']"
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
          v-hasPermi="['crm:oppStageChangeHis:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:oppStageChangeHis:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oppStageChangeHisList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商机code" align="center" prop="opportunityCode" />
      <el-table-column label="老阶段" align="center" prop="sourceStage">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.sourceStage"/>
        </template>
      </el-table-column>
      <el-table-column label="新阶段" align="center" prop="targetStage">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.targetStage"/>
        </template>
      </el-table-column>
      <el-table-column label="转变时间" align="center" prop="changeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.changeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作员名" align="center" prop="operatorName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:oppStageChangeHis:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:oppStageChangeHis:remove']"
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

    <!-- 添加或修改商机阶段历史对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商机code" prop="opportunityCode">
          <el-input v-model="form.opportunityCode" placeholder="请输入商机code" />
        </el-form-item>
        <el-form-item label="老阶段" prop="sourceStage">
          <el-select v-model="form.sourceStage" placeholder="请选择老阶段">
            <el-option
              v-for="dict in dict.type.crm_opportunity_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新阶段" prop="targetStage">
          <el-select v-model="form.targetStage" placeholder="请选择新阶段">
            <el-option
              v-for="dict in dict.type.crm_opportunity_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="转变时间" prop="changeTime">
          <el-date-picker clearable
            v-model="form.changeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择转变时间">
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
import { listOppStageChangeHis, getOppStageChangeHis, delOppStageChangeHis, addOppStageChangeHis, updateOppStageChangeHis } from "@/api/crm/oppStageChangeHis";

export default {
  name: "OppStageChangeHis",
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
      // 商机阶段历史表格数据
      oppStageChangeHisList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityCode: null,
        sourceStage: null,
        targetStage: null,
        changeTime: null,
        operatorName: null
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
    /** 查询商机阶段历史列表 */
    getList() {
      this.loading = true;
      listOppStageChangeHis(this.queryParams).then(response => {
        this.oppStageChangeHisList = response.rows;
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
        sourceStage: null,
        targetStage: null,
        changeTime: null,
        operatorId: null,
        operatorName: null
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
      this.title = "添加商机阶段历史";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOppStageChangeHis(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商机阶段历史";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOppStageChangeHis(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOppStageChangeHis(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机阶段历史编号为"' + ids + '"的数据项？').then(function() {
        return delOppStageChangeHis(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/oppStageChangeHis/export', {
        ...this.queryParams
      }, `oppStageChangeHis_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
