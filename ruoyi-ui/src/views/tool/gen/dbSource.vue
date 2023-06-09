<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="数据库名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入数据库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据库类型" prop="dbType">
        <el-select v-model="queryParams.dbType" placeholder="请选择数据库类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_system_db_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['tool:genDbSource:add']"
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
          v-hasPermi="['tool:genDbSource:edit']"
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
          v-hasPermi="['tool:genDbSource:remove']"
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
          v-hasPermi="['tool:genDbSource:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="genDbSourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="数据源主键" align="center" prop="id" v-if="columns[0].visible"/>
      <el-table-column label="数据库名称" align="center" prop="name" v-if="columns[1].visible"/>
      <el-table-column label="数据库类型" align="center" prop="dbType" v-if="columns[2].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_system_db_type" :value="scope.row.dbType"/>
        </template>
      </el-table-column>
      <el-table-column label="oracle连接方式" align="center" prop="oracleConnMode" v-if="columns[3].visible"/>
      <el-table-column label="oracle连接服务名或SID" align="center" prop="serviceNameOrSid" v-if="columns[4].visible"/>
      <el-table-column label="主机地址" align="center" prop="host" v-if="columns[5].visible"/>
      <el-table-column label="主机端口" align="center" prop="port" v-if="columns[6].visible"/>
      <el-table-column label="用户名" align="center" prop="username" v-if="columns[7].visible"/>
      <el-table-column label="密码" align="center" prop="password" v-if="columns[8].visible"/>
      <el-table-column label="模式" align="center" prop="schemaName" v-if="columns[9].visible"/>
      <el-table-column label="是否可用" align="center" prop="status" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[11].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tool:genDbSource:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tool:genDbSource:remove']"
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

    <!-- 添加或修改代码生成数据源配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="数据库类型" prop="dbType">
              <el-select v-model="form.dbType" placeholder="请选择数据库类型">
                <el-option
                  v-for="dict in dict.type.sys_system_db_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据库名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入数据库名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.dbType == 'oracle'">
          <el-col :span="12">
            <el-form-item label="oracle连接方式" prop="oracleConnMode">
              <el-radio-group v-model="form.oracleConnMode">
                <el-radio label="service_name">服务名</el-radio>
                <el-radio label="sid">SID</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="oracle连接服务名或SID" prop="serviceNameOrSid">
              <el-input v-model="form.serviceNameOrSid" placeholder="oracle连接服务名或SID"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="主机地址" prop="host">
              <el-input v-model="form.host" placeholder="请输入主机地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主机端口" prop="port">
              <el-input v-model="form.port" placeholder="请输入主机端口号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入密码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if="form.dbType == 'pgsql'">
            <el-form-item label="模式" prop="schemaName">
              <el-input v-model="form.schemaName" placeholder="请输入模式"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否可用" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea"  resize="none" :rows="3" placeholder="请输入内容"/>
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
import {
  listGenDbSource,
  getGenDbSource,
  delGenDbSource,
  addGenDbSource,
  updateGenDbSource
} from "@/api/tool/genDbSource";

export default {
  name: "GenDbSource",
  dicts: ['sys_yes_no', 'sys_system_db_type'],
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
      // 代码生成数据源配置表格数据
      genDbSourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        dbType: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dbType: [
          {required: true, message: "数据库类型不能为空", trigger: "blur"},
        ],
        name: [
          {required: true, message: "数据库名称不能为空", trigger: "blur"},
        ],
        oracleConnMode: [
          {required: true, message: "oracle连接方式不能为空", trigger: "blur"},
        ],
        serviceNameOrSid: [
          {required: true, message: "oracle连接服务名或SID未选择", trigger: "blur"},
        ],
        host: [
          {required: true, message: "主机地址不能为空", trigger: "blur"},
        ],
        port: [
          {required: true, message: "主机端口不能为空", trigger: "blur"},
        ],
        username: [
          {required: true, message: "用户名不能为空", trigger: "blur"},
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"},
        ],
        schemaName: [
          {required: true, message: "模式不能为空", trigger: "blur"},
        ],
        status: [
          {required: true, message: "是否可用未选择", trigger: "blur"},
        ],
      },
      columns: [
        {key: 0, label: `数据源主键`, visible: true},
        {key: 1, label: `数据库名称`, visible: true},
        {key: 2, label: `数据库类型`, visible: true},
        {key: 3, label: `oracle连接方式`, visible: false},
        {key: 4, label: `oracle连接服务名或SID`, visible: false},
        {key: 5, label: `主机地址`, visible: true},
        {key: 6, label: `主机端口`, visible: true},
        {key: 7, label: `用户名`, visible: true},
        {key: 8, label: `密码`, visible: true},
        {key: 9, label: `模式`, visible: false},
        {key: 10, label: `是否可用`, visible: true},
        {key: 11, label: `备注`, visible: false},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询代码生成数据源配置列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listGenDbSource(this.queryParams).then(response => {
        this.genDbSourceList = response.rows;
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
        name: null,
        dbType: 'mysql',
        oracleConnMode: null,
        serviceNameOrSid: null,
        host: null,
        port: null,
        username: 'root',
        password: null,
        schemaName: 'public',
        status: 'Y',
        createBy: null,
        createTime: null,
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
      this.daterangeCreateTime = [];
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
      this.title = "添加代码生成数据源配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGenDbSource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改代码生成数据源配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGenDbSource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGenDbSource(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除代码生成数据源配置编号为"' + ids + '"的数据项？').then(function () {
        return delGenDbSource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tool/genDbSource/export', {
        ...this.queryParams
      }, `genDbSource_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
