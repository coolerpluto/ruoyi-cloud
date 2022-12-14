<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px" >
      <el-form-item label="商机编号" prop="opportunityId">
        <el-input v-model="queryParams.opportunityId" placeholder="请输入编号" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="最终用户" prop="customer">
        <el-input v-model="queryParams.customer" placeholder="请输入用户名称" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="申请人" prop="applicant">
        <el-input v-model="queryParams.applicant" placeholder="请输入申请人姓名" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="申请时间">
        <el-date-picker v-model="dateRange" style="width: 220px" value-format="yyyy-MM-dd" type="daterange"
        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="licenseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="License编号" align="center" prop="licenseId" v-if="false" :show-overflow-tooltip="true"/>
      <el-table-column label="License文件名" align="center" prop="licenseName" v-if="false" :show-overflow-tooltip="true"/>
      <el-table-column label="商机编号" align="center" prop="opportunityId" :show-overflow-tooltip="true"/>
      <el-table-column label="商机名称" align="center" prop="opportunityName" :show-overflow-tooltip="true"/>
      <el-table-column label="最终用户" align="center" prop="customer" :show-overflow-tooltip="true"/>
      <el-table-column label="序列编号" align="center" prop="licenseSerial" :show-overflow-tooltip="true"/>
      <el-table-column label="授权方式" align="center" prop="authType" />
      <el-table-column label="产品信息" align="center" prop="prodInfo" :show-overflow-tooltip="true"/>
      <el-table-column label="使用用途" align="center" prop="configType"/>
      <el-table-column label="申请人" align="center" prop="applicant"/>
      <el-table-column label="申请时间" align="center" prop="applicationTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applicationTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="有效期限" align="center" prop="effTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleDownload(scope.row)" v-hasPermi="['system:config:edit']">
            下载
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>
  </div>
</template>

<script>
import {listLicenses,delLicense} from "@/api/medium/licensedownload";

export default {
  name: "Licensedownload",
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
      licenseList: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        opportunityId: undefined,
        customer: undefined,
        applicant: undefined,
      },
    };
  },
  created() {
    //this.getList();
  },
  methods: {
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      listLicenses(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.licenseList = response.rows;
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
      this.ids = selection.map((item) => item.licenseId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const licenseIds = row.licenseId || this.ids;
      this.$modal
        .confirm('是否确认删除参数编号为"' + licenseIds + '"的数据项？')
        .then(function () {
          return delLicense(licenseIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download("medium/licensedownload/export",{
          ...this.queryParams,
        },
        `config_${new Date().getTime()}.xlsx`
      );
    },
     /** 下载按钮操作 */
    handleDownload(row) {
      const fileName = new Date().getTime()+row.licenseName
      this.download("medium/licensedownload/downLoad",{
          "licenseId":row.licenseId
        },
        fileName
      );
    },
  },
};
</script>
