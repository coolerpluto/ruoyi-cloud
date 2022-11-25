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
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:config:add']">
          新增介质
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:config:edit']">
          修改配置
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:config:remove']">
          删除选择
        </el-button>
      </el-col>
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
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row,1)" v-hasPermi="['system:config:edit']">
            修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row,3)" v-hasPermi="['system:config:remove']">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>


    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form :model="form" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px" >
        <el-row>
          <el-form-item label="介质名称" prop="mediumName">
            <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
          </el-form-item>
          <el-form-item label="安装包类型" prop="packageType">
            <el-select v-model="form.packageType" placeholder="请选择" clearable>
              <el-option v-for="dict in dict.type.medium_package_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
            </el-select>  
          </el-form-item>
          <el-form-item label="CPU类型" prop="cpuModel">
            <el-select v-model="form.cpuModel" placeholder="请选择" clearable>
              <el-option v-for="dict in dict.type.medium_cpu_model" :key="dict.value" :label="dict.label" :value="dict.value"/>
            </el-select>  
          </el-form-item>
          <el-form-item label="数据库版本" prop="dbVersion">
            <el-select v-model="form.dbVersion" placeholder="请选择" clearable>
              <el-option v-for="dict in dict.type.medium_db_version" :key="dict.value" :label="dict.label" :value="dict.value"/>
            </el-select>  
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="版本类型" prop="versionType">
            <el-select v-model="form.versionType" placeholder="请选择" clearable>
              <el-option v-for="dict in dict.type.medium_version_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
            </el-select>  
          </el-form-item>
        </el-row>
        <el-row>
          <el-divider />
          <el-row>
            <el-form-item label="介质手册" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="介质文件" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="介质-MD5" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="含有GIS" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="GIS文件" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="GIS-MD5" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="含有签名" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="签名文件" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="签名-MD5" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="商机编号" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="最终用户" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
            <el-form-item label="其他附近" prop="versionType">
              <el-input v-model="form.mediumName" placeholder="请输入介质名称" clearable style="width: 220px"/>
            </el-form-item>
          </el-row>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listMediums,delMedium} from "@/api/medium/mediumdownload";
import {mediumUpload} from "@/api/medium/mediumupload";

export default {
  name: "MeduimUpload",
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        configName: [
          { required: true, message: "参数名称不能为空", trigger: "blur" }
        ],
        configKey: [
          { required: true, message: "参数键名不能为空", trigger: "blur" }
        ],
        configValue: [
          { required: true, message: "参数键值不能为空", trigger: "blur" }
        ]
      }
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        configId: undefined,
        configName: undefined,
        configKey: undefined,
        configValue: undefined,
        configType: "Y",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.configId != undefined) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加参数";
    },
     /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const configId = row.configId || this.ids
      getConfig(configId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改参数";
      });
    },
  },
};
</script>