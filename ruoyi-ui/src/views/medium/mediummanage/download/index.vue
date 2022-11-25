<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px" >
      <el-form-item label="介质名称" prop="mediumName">
        <el-input v-model="queryParams.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
      </el-form-item>
      <el-form-item label="安装包类型" prop="packageType">
        <el-select v-model="queryParams.packageType" placeholder="请选择" clearable>
          <el-option v-for="dict in dict.type.medium_package_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>  
      </el-form-item>
      <el-form-item label="CPU类型" prop="cpuModel">
        <el-select v-model="queryParams.cpuModel" placeholder="请选择" clearable>
          <el-option v-for="dict in dict.type.medium_cpu_model" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>  
      </el-form-item>
      <el-form-item label="数据库版本" prop="dbVersion">
        <el-select v-model="queryParams.dbVersion" placeholder="请选择" clearable>
          <el-option v-for="dict in dict.type.medium_db_version" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>  
      </el-form-item>
      <el-form-item label="版本类型" prop="versionType">
        <el-select v-model="queryParams.versionType" placeholder="请选择" clearable>
          <el-option v-for="dict in dict.type.medium_version_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
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
      
      <el-form-item label="发布时间">
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
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['system:config:export']">
          导出列表
        </el-button>
      </el-col>      
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mediumList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="介质编号" align="center" prop="mediumId" />
      <el-table-column label="介质名称" align="center" prop="mediumName" :show-overflow-tooltip="true"/>
      <el-table-column label="安装包类型" align="center" prop="packageType" />
      <el-table-column label="CPU类型" align="center" prop="cpuModel" />
      <el-table-column label="数据库版本" align="center" prop="dbVersion" />

      <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime) }}</span>
        </template>
      </el-table-column>
      
      <!-- 要区分什么时候可以下什么时间不能下 -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleDownLoad(scope.row,1)" v-hasPermi="['system:config:edit']">
            支持下载签名
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDownLoad(scope.row,2)" v-hasPermi="['system:config:remove']">
            支持下载postgis
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDownLoad(scope.row,3)" v-hasPermi="['system:config:remove']">
            下载介质
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>
  </div>
</template>

<script>
import {listMediums,delMedium} from "@/api/medium/mediumdownload";

export default {
  name: "Meduimdownload",
  dicts: ["sys_yes_no","medium_package_type","medium_cpu_model","medium_version_type","medium_db_version"],
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
      mediumList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mediumName: undefined,
        packageType: undefined,
        cpuModel: undefined,
        dbVersion: undefined,
        versionType: undefined,
        hasPostgis: undefined,
        hasSignature: undefined,
        publishTime: undefined,
      },
    };
  },
  created() {
    // 初始化列表
    //this.getList();
  },
  methods: {
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      listMediums(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.mediumList = response.rows;
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
      this.ids = selection.map((item) => item.configId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    
    /** 删除按钮操作 */
    handleDelete(row) {
      const configIds = row.configId || this.ids;
      this.$modal
        .confirm('是否确认删除参数编号为"' + configIds + '"的数据项？')
        .then(function () {
          return delMedium(configIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download("medium/mediumdownload/export",{
          ...this.queryParams,
        },
        `config_${new Date().getTime()}.xlsx`
      );
    },
    /** 下载操作 */
    handleDownLoad(row,sourceType) {
      //不同类型需要不同的名字
      // TO-DO
      const fileName = new Date().getTime()+row.mediumName 
      this.download("medium/mediumdownload/downLoad",{
          "sourceType":sourceType,
          "mediumId":row.mediumId
        },
        fileName
      );
    },
  },
};
</script>