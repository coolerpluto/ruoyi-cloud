<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px" >
      <el-form-item label="商机编号" prop="opportunityNum">
        <el-input v-model="queryParams.opportunityNum" placeholder="请输入编号" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="最终用户" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入用户名称" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="申请人账户" prop="createBy">
        <el-input v-model="queryParams.createBy" placeholder="请输入申请人登录账户名" clearable style="width: 220px" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="申请时间">
        <el-date-picker v-model="dateRange" style="width: 220px" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="licenseList" @selection-change="handleSelectionChange">
      <!--<el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="Lic序列号" align="center" prop="serial" :show-overflow-tooltip="true" v-if="columns[0].visible"/>
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180" v-if="columns[1].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="商机编号" align="center" prop="opportunityNum" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="授权方式" align="center" prop="authType" v-if="columns[3].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_lic_db_auth_type" :value="scope.row.authType"/>
        </template>
      </el-table-column>
      <el-table-column label="使用客户" align="center" prop="customerName" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="使用用途" align="center" prop="purpose" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_lic_purposes" :value="scope.row.purpose"/>
        </template>
      </el-table-column>
      <el-table-column label="过期时间" align="center" prop="expireTime" :show-overflow-tooltip="true" v-if="columns[6].visible"/>
      <el-table-column label="产品类型" align="center" prop="prodType" v-if="columns[7].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_lic_prod_type" :value="scope.row.prodType"/>
        </template>
      </el-table-column>
      <el-table-column label="数据库版本" align="center" prop="dbVersion" :show-overflow-tooltip="true" v-if="columns[8].visible"/>
      <el-table-column label="下载连接" align="center" prop="serverUrl" :show-overflow-tooltip="true" v-if="columns[9].visible"/>
      <el-table-column label="其他参数" align="center" prop="otherParam" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="文件状态" align="center" prop="status" v-if="columns[11].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="申请人账户" align="center" prop="createBy" v-if="columns[12].visible"/>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[13].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[14].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-download" @click="handleDownload(scope.row)" v-hasPermi="['medium:license:download']">
            下载
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>
  </div>
</template>

<script>
import { listLicense } from "@/api/medium/license";

export default {
  name: "Licensedownload",
  dicts: [
    "sys_normal_disable",
    "medium_lic_purposes",
    "medium_lic_prod_type",
    "medium_lic_db_auth_type",
  ],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中文件id数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 列信息
      columns: [
        { key: 0, label: `Lic序列号`, visible: true },
        { key: 1, label: `申请时间`, visible: true },
        { key: 2, label: `商机编号`, visible: true },
        { key: 3, label: `授权方式`, visible: true },
        { key: 4, label: `使用客户`, visible: true },
        { key: 5, label: `使用用途`, visible: false },
        { key: 6, label: `过期时间`, visible: false },
        { key: 7, label: `产品类型`, visible: false },
        { key: 8, label: `数据库版本`, visible: false },
        { key: 9, label: `下载连接`, visible: false },
        { key: 10, label: `其他参数`, visible: false },
        { key: 11, label: `文件状态`, visible: false },
        { key: 12, label: `申请人账户`, visible: false },
        { key: 13, label: `创建时间`, visible: false },
        { key: 14, label: `备注`, visible: false },
      ],
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
        status: 0,
        opportunityNum: undefined,
        customerName: undefined,
        createBy: undefined,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      listLicense(this.addDateRange(this.queryParams, this.dateRange,'ApplyTime')).then(
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

     /** 下载按钮操作 */
    handleDownload(row) {
      let fileName = row.serial+".zip"
      this.download("medium/license/download",{id:row.id},fileName);
    },
  },
};
</script>
