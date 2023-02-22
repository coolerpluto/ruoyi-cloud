<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="商机编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入商机编码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="商机名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入商机名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="客户公司" prop="companyName">
        <el-input v-model="queryParams.companyName" placeholder="请输入公司名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建者部门" prop="deptIds">
        <el-input v-model="queryParams.deptIds" placeholder="请选择创建者部门" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建者" prop="createIds">
        <el-input v-model="queryParams.createIds" placeholder="请选择创建者" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="归属者" prop="ownerIds">
        <el-input v-model="queryParams.ownerIds" placeholder="请选择归属者" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="商机阶段" prop="stages">
        <el-select v-model="queryParams.stages" placeholder="请选择商机阶段" clearable multiple collapse-tags>
          <el-option v-for="dict in dict.type.crm_opportunity_status" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="rangeCreateDate" style="width: 340px" value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']"></el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker v-model="rangeUpdateDate" style="width: 340px" value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['crm:opportunity:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:opportunity:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:opportunity:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleTransfer"
                   v-hasPermi="['crm:opportunity:transfer']">批量转交
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:opportunity:export']">导出查询结果
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="opportunityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
      <el-table-column label="商机编码" align="center" prop="code" :show-overflow-tooltip="true" v-if="columns[0].visible"/>
      <el-table-column label="商机名称" align="center" prop="name" :show-overflow-tooltip="true" v-if="columns[1].visible"/>
      <el-table-column label="客户名称" align="center" prop="custName" :show-overflow-tooltip="true" v-if="columns[2].visible"/>
      <el-table-column label="当前阶段" align="center" prop="currentStage" :show-overflow-tooltip="true"
                       v-if="columns[3].visible"/>
      <el-table-column label="归属者" align="center" prop="ownerName" :show-overflow-tooltip="true"
                       v-if="columns[4].visible"/>
      <el-table-column label="投标时间" align="center" prop="tenderTime" :show-overflow-tooltip="true"
                       v-if="columns[5].visible"/>
      <el-table-column label="预签单时间" align="center" prop="preSignedTime" :show-overflow-tooltip="true"
                       v-if="columns[6].visible"/>
      <el-table-column label="预合同金额" align="center" prop="preContractAmount" :show-overflow-tooltip="true"
                       v-if="columns[7].visible"/>
      <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true"
                       v-if="columns[8].visible"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true"
                       v-if="columns[9].visible"/>
      <el-table-column label="操作选项" align="center" fixed="right" width="220" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-zoom-in" @click="handleUpdate(scope.row)">
            查看
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:opportunity:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleTransfer(scope.row)"
                     v-hasPermi="['crm:opportunity:transfer']">转交
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['crm:opportunity:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改商机管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    商机管理开发中。。。<br>
    完成：<br>
    待完成：商机各个模块的：新增、删除、修改、查看(仅查看)、转交、导出
  </div>
</template>

<script>
import {
  listOpportunity,
  getOpportunity,
  delOpportunity,
  addOpportunity,
  updateOpportunity
} from "@/api/crm/opportunity";

export default {
  name: "Opportunity",
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
      // 商机管理表格数据
      opportunityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        companyName: null,
        deptIds: null,
        createIds: null,
        ownerIds: null,
        stages: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      rangeCreateDate: [],
      rangeUpdateDate: [],
      columns: [
        {key: 0, label: `商机编码`, visible: true},
        {key: 1, label: `商机名称`, visible: true},
        {key: 2, label: `客户名称`, visible: true},
        {key: 3, label: `当前阶段`, visible: true},
        {key: 4, label: `归属者`, visible: true},
        {key: 5, label: `投标时间`, visible: true},
        {key: 6, label: `预签单时间`, visible: true},
        {key: 7, label: `预合同金额`, visible: true},
        {key: 8, label: `创建时间`, visible: true},
        {key: 9, label: `更新时间`, visible: true},
        {key: 10, label: `待定`, visible: true},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商机管理列表 */
    getList() {
      this.loading = true;

      this.queryParams.params = {};
      if (null != this.rangeCreateDate && '' != this.rangeCreateDate) {
        this.queryParams.params["beginCreateDate"] = this.rangeCreateDate[0];
        this.queryParams.params["endCreateDate"] = this.rangeCreateDate[1];
      }
      if (null != this.rangeUpdateDate && '' != this.rangeUpdateDate) {
        this.queryParams.params["beginUpdateDate"] = this.rangeUpdateDate[0];
        this.queryParams.params["endUpdateDate"] = this.rangeUpdateDate[1];
      }
      listOpportunity(this.queryParams).then(response => {
        this.opportunityList = response.rows;
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
    /** 新增按钮操作 */
    handleAdd() {
      //this.reset();
      //this.open = true;
      //this.title = "添加商机管理";
      this.$router.push("/crm/opportunity-data/index/0");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      const id = row.id || this.ids
      // getOpportunity(id).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改商机管理";
      // });
      this.$router.push("/crm/opportunity-data/index/" + id);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOpportunity(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOpportunity(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商机管理编号为"' + ids + '"的数据项？').then(function() {
        return delOpportunity(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 转交按钮 */
    handleTransfer(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认转移商机管理编号为"' + ids + '"的数据项？').then(function () {
        return delOpportunity(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/opportunity/export', {
        ...this.queryParams
      }, `opportunity_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
