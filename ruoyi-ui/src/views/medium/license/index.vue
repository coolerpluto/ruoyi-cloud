<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="申请时间" prop="applyTime">
        <el-date-picker clearable
                        v-model="queryParams.applyTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择申请时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="商机编号" prop="opportunityNum">
        <el-input
          v-model="queryParams.opportunityNum"
          placeholder="请输入商机编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="授权方式" prop="authType">
        <el-select v-model="queryParams.authType" placeholder="请选择授权方式" clearable>
          <el-option
            v-for="dict in dict.type.medium_lic_db_auth_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="使用客户" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入使用客户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用用途" prop="purpose">
        <el-select v-model="queryParams.purpose" placeholder="请选择使用用途" clearable>
          <el-option
            v-for="dict in dict.type.medium_lic_purposes"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="过期时间" prop="expireTime">
        <el-date-picker clearable
                        v-model="queryParams.expireTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择过期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="产品类型" prop="prodType">
        <el-select v-model="queryParams.prodType" placeholder="请选择产品类型" clearable>
          <el-option
            v-for="dict in dict.type.medium_lic_prod_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="数据库版本" prop="dbVersion">
        <el-select v-model="queryParams.dbVersion" placeholder="请选择数据库版本" clearable>
          <el-option
            v-for="dict in dict.type.medium_db_version"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="可用文件" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择文件状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
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
          v-hasPermi="['medium:license:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['medium:license:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['medium:license:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:license:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="licenseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="记录ID" align="center" prop="id" />-->
      <el-table-column label="Lic序列号" align="center" prop="serial" :show-overflow-tooltip="true"/>
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="商机编号" align="center" prop="opportunityNum" :show-overflow-tooltip="true"/>
      <el-table-column label="授权方式" align="center" prop="authType"/>
      <el-table-column label="使用客户" align="center" prop="customerName" :show-overflow-tooltip="true"/>
      <el-table-column label="使用用途" align="center" prop="purpose"/>
      <el-table-column label="过期时间" align="center" prop="expireTime" :show-overflow-tooltip="true"/>
      <el-table-column label="产品类型" align="center" prop="prodType"/>
      <el-table-column label="数据库版本" align="center" prop="dbVersion" :show-overflow-tooltip="true"/>
      <el-table-column label="下载连接" align="center" prop="serverUrl" :show-overflow-tooltip="true"/>
      <el-table-column label="其他参数" align="center" prop="otherParam" v-if="false" :show-overflow-tooltip="true"/>
      <el-table-column label="文件状态" align="center" prop="status"/>
      <!--      <el-table-column label="创建者ID" align="center" prop="createId" />-->
      <el-table-column label="创建者名" align="center" prop="createBy"/>
      <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true"/>
      <!--      <el-table-column label="更新者ID" align="center" prop="updateId" />-->
      <!--      <el-table-column label="更新者名" align="center" prop="updateBy" />-->
      <!--      <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true"/>-->
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medium:license:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:license:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改License文件记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请时间" prop="applyTime">
          <el-date-picker clearable
                          v-model="form.applyTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择申请时间">
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
import {listLicense, getLicense, delLicense, addLicense, updateLicense} from "@/api/medium/license";

export default {
  name: "License",
  dicts: ['sys_yes_no','medium_lic_purposes', 'medium_lic_db_auth_type', 'medium_db_version', 'medium_lic_prod_type'],
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
      // License文件记录表格数据
      licenseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyTime: null,
        opportunityNum: null,
        authType: null,
        customerName: null,
        purpose: null,
        expireTime: null,
        prodType: null,
        dbVersion: null,
        serverUrl: null,
        status: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询License文件记录列表 */
    getList() {
      this.loading = true;
      listLicense(this.queryParams).then(response => {
        this.licenseList = response.rows;
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
        serial: null,
        applyTime: null,
        opportunityNum: null,
        authType: null,
        customerName: null,
        purpose: null,
        expireTime: null,
        prodType: null,
        dbVersion: null,
        serverUrl: null,
        otherParam: null,
        status: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加License文件记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLicense(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改License文件记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLicense(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLicense(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除License文件记录编号为"' + ids + '"的数据项？').then(function () {
        return delLicense(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/license/export', {
        ...this.queryParams
      }, `license_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
