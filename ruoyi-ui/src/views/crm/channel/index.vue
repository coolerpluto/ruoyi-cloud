<template>
  <div class="app-container">
    <el-form :model="queryParams"
      ref="queryForm" size="small"
      :inline="true" v-show="showSearch"
      label-width="68px">
      <el-form-item label="渠道名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入公司名称"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="拥有者" prop="configKey">
        <el-input
          v-model="queryParams.configKey"
          placeholder="请输入拥有者姓名"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者" prop="configKey">
        <el-input
          v-model="queryParams.configKey"
          placeholder="请输入所有者姓名"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 200px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 200px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['system:config:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:config:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:config:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:config:export']"
          >导出</el-button
        >
      </el-col>
      
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="configList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="渠道名称"
        align="center"
        prop="configKey"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="注册资本" align="center" prop="configValue" />
      <el-table-column label="公司地址" align="center" prop="configValue" />
      <el-table-column label="公司官网" align="center" prop="configValue" />

      <el-table-column label="创建人员" align="center" prop="configType"/>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="最新时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:config:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:config:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-col :span="12">
            <el-form-item label="渠道/公司" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入渠道/公司名称" />
            </el-form-item>
          </el-col>  
          <el-col :span="10">
            <el-form-item label="注册资本" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入金额单位:万元">
                <template #append>万元</template>
              </el-input>
            </el-form-item>
          </el-col> 
          <el-col :span="20">
            <el-form-item label="公司官网" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入URL" >
                <template #prepend>Http://</template>
              </el-input>
            </el-form-item>
          </el-col>         
          <el-col :span="8">
            <el-form-item label="省" prop="configKey">
              <el-input v-model="form.configKey" placeholder="请输入参数键名" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="市" prop="configKey">
              <el-input v-model="form.configKey" placeholder="请输入参数键名" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="县/区" prop="configKey">
              <el-input v-model="form.configKey" placeholder="请输入参数键名" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="详细地址" prop="configValue">
              <el-input v-model="form.configValue" placeholder="请输入参数键值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="地址邮编" prop="configValue">
              <el-input v-model="form.configValue" placeholder="请输入参数键值" />
            </el-form-item>
          </el-col>          
          <el-col :span="8">
            <el-form-item label="行业大类" prop="configValue">
              <el-input v-model="form.configValue" placeholder="请输入参数键值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="二级行业" prop="configValue">
              <el-input v-model="form.configValue" placeholder="请输入参数键值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="三级行业" prop="configValue">
              <el-input v-model="form.configValue" placeholder="请输入参数键值" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="信息备注" prop="remark">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
                maxlength="100"
                show-word-limit
              />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getConfig,delConfig,addConfig,updateConfig} from "@/api/system/config";
import {deptTreeSelect} from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listsOpportunity} from "@/api/crm/opportunity";

export default {
  name: "Channel",
  dicts: ["sys_yes_no", "crm_industry_type"],
  components: { Treeselect },
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
      // 部门树选项
      deptOptions: undefined,
      // 总条数
      total: 0,
      // 参数表格数据
      configList: [],
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
        customerName: undefined,
        configKey: undefined,
        configType: undefined,
        deptId: undefined,
        industryType: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerName: [
          { required: true, message: "参数名称不能为空", trigger: "blur" },
        ],
        configKey: [
          { required: true, message: "参数键名不能为空", trigger: "blur" },
        ],
        configValue: [
          { required: true, message: "参数键值不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      listsOpportunity(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.configList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
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
        customerName: undefined,
        configKey: undefined,
        configValue: undefined,
        configType: "Y",
        remark: undefined,
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加新渠道";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.configId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const configId = row.configId || this.ids;
      getConfig(configId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改渠道";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.configId != undefined) {
            updateConfig(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then((response) => {
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
      const configIds = row.configId || this.ids;
      this.$modal
        .confirm('是否确认删除参数编号为"' + configIds + '"的数据项？')
        .then(function () {
          return delConfig(configIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "crm/opportunity/export",
        {
          ...this.queryParams,
        },
        `config_${new Date().getTime()}.xlsx`
      );
    },
    
  },
};
</script>