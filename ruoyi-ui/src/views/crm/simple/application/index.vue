<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="应用名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入应用名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据归类" prop="sourceType">
        <el-select v-model="queryParams.sourceType" placeholder="请选择数据归类" clearable>
          <el-option
            v-for="dict in dict.type.crm_business_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开发进度" prop="devStatus">
        <el-select v-model="queryParams.devStatus" placeholder="请选择应用开发进度" clearable>
          <el-option
            v-for="dict in dict.type.sys_work_progress"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="适配进度" prop="dbAdaptStatus">
        <el-select v-model="queryParams.dbAdaptStatus" placeholder="请选择HGDB适配进度" clearable>
          <el-option
            v-for="dict in dict.type.sys_work_progress"
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
          v-hasPermi="['crm:application:add']"
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
          v-hasPermi="['crm:application:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-tickets"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['crm:application:transfer']"
        >批量转交
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
          v-hasPermi="['crm:application:remove']"
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
          v-hasPermi="['crm:application:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="应用名称" align="center" prop="name" v-if="columns[0].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="当前版本" align="center" prop="version" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="应归门类" align="center" prop="category" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="软件开发商" align="center" prop="isv" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="isv联系人" align="center" prop="isvContactId" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="基础环境" align="center" prop="baseEnviron" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作系统" align="center" prop="opeartSys" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="中间件" align="center" prop="middleSoft" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="当前数据库" align="center" prop="dataBase" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="DB选型" align="center" prop="dbSelectionModel" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="部署类型" align="center" prop="deployType" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="DB部署级别" align="center" prop="deployLevel" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="建设资金来源" align="center" prop="sysBuildMoney" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="数据归类" align="center" prop="sourceType" v-if="columns[13].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_business_type" :value="scope.row.sourceType"/>
        </template>
      </el-table-column>
      <el-table-column label="应用开发进度" align="center" prop="devStatus" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.devStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="HGDB适配进度" align="center" prop="dbAdaptStatus" v-if="columns[15].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.dbAdaptStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="remark" v-if="columns[16].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建者" align="center" prop="createBy" v-if="columns[17].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[18].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新者" align="center" prop="updateBy" v-if="columns[19].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" v-if="columns[20].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="归属者" align="center" prop="ownerId" v-if="columns[21].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="归属部门" align="center" prop="deptId" v-if="columns[22].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:application:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:application:transfer']"
          >转交
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:application:remove']"
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

    <!-- 添加或修改应用信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入应用名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="当前版本" prop="version">
              <el-input v-model="form.version" placeholder="请输入应用当前版本,V1.0.14"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="软件门类" prop="category">
              <el-input v-model="form.category" placeholder="请输入应用软件归属门类"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资金来源" prop="sysBuildMoney">
              <el-input v-model="form.sysBuildMoney" placeholder="请输入应用建设资金来源"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="软件开发商" prop="isv">
              <el-input v-model="form.isv" placeholder="请输入应用软件开发商(已经保存的公司的ID)"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开发商联系人" prop="isvContactId">
              <el-input v-model="form.isvContactId" placeholder="请输入应用软件开发商联系人的ID(已经保存的人的)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用开发进度" prop="devStatus">
              <el-select v-model="form.devStatus" placeholder="请选择应用开发进度">
                <el-option
                  v-for="dict in dict.type.sys_work_progress"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="HGDB适配进度" prop="dbAdaptStatus">
              <el-select v-model="form.dbAdaptStatus" placeholder="请选择HGDB适配进度">
                <el-option
                  v-for="dict in dict.type.sys_work_progress"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部署类型" prop="deployType">
              <el-input v-model="form.deployType" placeholder="请输入应用软件部署类型"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="DB部署级别" prop="deployLevel">
              <el-input v-model="form.deployLevel" placeholder="请输入应用软件DB部署级别"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="当前数据库" prop="dataBase">
              <el-input v-model="form.dataBase" placeholder="请输入应用软件当前数据库"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="DB选型" prop="dbSelectionModel">
              <el-input v-model="form.dbSelectionModel" placeholder="请输入应用软件当前数据库备选数据库"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="基础环境" prop="baseEnviron">
              <el-input v-model="form.baseEnviron" placeholder="请输入应用软件基础环境"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作系统" prop="opeartSys">
              <el-input v-model="form.opeartSys" placeholder="请输入应用软件操作系统"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数据归类" prop="sourceType">
              <el-select v-model="form.sourceType" placeholder="请选择数据归类">
                <el-option
                  v-for="dict in dict.type.crm_business_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其他描述" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入应用软件其他描述"/>
            </el-form-item>
          </el-col>
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
import {
  listApplication,
  getApplication,
  delApplication,
  addApplication,
  updateApplication
} from "@/api/crm/application";

export default {
  name: "Application",
  dicts: ['sys_work_progress', 'crm_business_type'],
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
      // 应用信息表格数据
      applicationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        version: null,
        category: null,
        isv: null,
        isvContactId: null,
        baseEnviron: null,
        opeartSys: null,
        middleSoft: null,
        dataBase: null,
        dbSelectionModel: null,
        deployType: null,
        deployLevel: null,
        sysBuildMoney: null,
        sourceId: null,
        sourceType: null,
        devStatus: null,
        dbAdaptStatus: null,
        remark: null,
        ownerId: null,
        deptId: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "应用名字不能为空", trigger: "blur"}
        ],
        category: [
          {required: true, message: "应用归属门类不能为空", trigger: "blur"}
        ],
        dataBase: [
          {required: true, message: "应用当前使用的数据库不能为空", trigger: "blur"}
        ],
      },
      // 列信息
      columns: [
        {key: 0, label: `应用名称`, visible: true},
        {key: 1, label: `当前版本`, visible: true},
        {key: 2, label: `应归门类`, visible: true},
        {key: 3, label: `软件开发商`, visible: true},
        {key: 4, label: `isv联系人`, visible: false},
        {key: 5, label: `基础环境`, visible: false},
        {key: 6, label: `操作系统`, visible: true},
        {key: 7, label: `中间件`, visible: false},
        {key: 8, label: `当前数据库`, visible: true},
        {key: 9, label: `DB选型`, visible: false},
        {key: 10, label: `部署类型`, visible: false},
        {key: 11, label: `DB部署级别`, visible: false},
        {key: 12, label: `建设资金来源`, visible: false},
        {key: 13, label: `数据归类`, visible: false},
        {key: 14, label: `应用开发进度`, visible: true},
        {key: 15, label: `HGDB适配进度`, visible: true},
        {key: 16, label: `描述`, visible: false},
        {key: 17, label: `创建者`, visible: false},
        {key: 18, label: `创建时间`, visible: false},
        {key: 19, label: `更新者`, visible: false},
        {key: 20, label: `更新时间`, visible: false},
        {key: 21, label: `归属者`, visible: true},
        {key: 22, label: `归属部门`, visible: true},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询应用信息列表 */
    getList() {
      this.loading = true;
      listApplication(this.queryParams).then(response => {
        this.applicationList = response.rows;
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
        version: null,
        category: null,
        isv: null,
        isvContactId: null,
        baseEnviron: null,
        opeartSys: null,
        middleSoft: null,
        dataBase: null,
        dbSelectionModel: null,
        deployType: null,
        deployLevel: null,
        sysBuildMoney: null,
        sourceId: null,
        sourceType: null,
        devStatus: null,
        dbAdaptStatus: null,
        remark: null,
        ownerId: null,
        deptId: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加应用信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getApplication(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改应用信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateApplication(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApplication(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除应用信息编号为"' + ids + '"的数据项？').then(function () {
        return delApplication(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/application/export', {
        ...this.queryParams
      }, `application_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
