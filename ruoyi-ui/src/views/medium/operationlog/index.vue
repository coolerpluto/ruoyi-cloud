<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px" >
      <el-form-item label="商机编号" prop="opportunityId">
        <el-input v-model="queryParams.opportunityId" placeholder="请输入商机编号" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="介质编号" prop="mediumId">
        <el-input v-model="queryParams.mediumId" placeholder="请输入介质编号" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="lic序号" prop="licenseId">
        <el-input v-model="queryParams.licenseId" placeholder="请输入lic序号" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="操作人员" prop="operator">
        <el-input v-model="queryParams.operator" placeholder="请输操作员名字" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker v-model="dateRange" style="width: 220px" value-format="yyyy-MM-dd" type="daterange" 
        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:config:remove']">
          删除选择
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['system:config:export']">
          导出结果
        </el-button>
      </el-col>      
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="operationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="审计编号" align="center" prop="logId"/>
      <el-table-column label="资源名称" align="center" prop="sourceName"/>
      <el-table-column label="资源类型" align="center" prop="sourceType" :show-overflow-tooltip="true"/>
      <el-table-column label="资源编号" align="center" prop="sourceNum"/>
      <el-table-column label="操作类型" align="center" prop="operationType" :show-overflow-tooltip="true"/>
      <el-table-column label="操作人员" align="center" prop="operator" />
      <el-table-column label="操作时间" align="center" prop="operationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operationTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['system:config:remove']">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>
  </div>
</template>

<script>
import {list,getInfo,del} from "@/api/medium/operationlog";

export default {
  name: "operationlog",
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 表格数据
      operationList: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityId: undefined,
        mediumId: undefined,
        licenseId: undefined,        
        operator: undefined,
        operationTime: undefined,
      },
    };
  },
  created() {
    // 初始化查询列表
    //this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange,"OperationTime")).then(
        (response) => {
          this.operationList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.logId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const logIds = row.logId || this.ids;
      this.$modal
        .confirm('是否确认删除参数编号为"' + logIds + '"的数据项？')
        .then(function () {
          return del(logIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download("medium/operationlog/export",
        {
          ...this.queryParams,
        },
        `操作审计日志_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>