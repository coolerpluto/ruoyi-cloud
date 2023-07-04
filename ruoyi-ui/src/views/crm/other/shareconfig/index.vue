<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="数据模块" prop="dataModel">
        <el-select v-model="queryParams.dataModel" placeholder="请选择数据模块" clearable>
          <el-option v-for="dict in dict.type.crm_business_type" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['crm:shareconfig:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:shareconfig:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:shareconfig:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:shareconfig:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shareconfigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="数据模块" align="center" prop="dataModel" v-if="columns[0].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_business_type" :value="scope.row.dataModel"/>
        </template>
      </el-table-column>
      <el-table-column label="共享源" align="center" prop="sourceIds" :show-overflow-tooltip="true"
                       v-if="columns[1].visible"/>
      <el-table-column label="共享源类型" align="center" prop="sourceType" v-if="columns[2].visible">
        <template slot-scope="{ row }">
         {{ getShareTypeLable(row.sourceType) }}
        </template>
      </el-table-column>
      <el-table-column label="共享目标" align="center" prop="targetIds" :show-overflow-tooltip="true"
                       v-if="columns[3].visible"/>
      <el-table-column label="共享目标类型" align="center" prop="targetType" v-if="columns[4].visible">
        <template slot-scope="{ row }">
          {{ getShareTypeLable(row.targetType) }}
        </template>
      </el-table-column>
      <el-table-column label="共享目标权限" align="center" prop="permission" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_permission" :value="scope.row.permission"/>
        </template>
      </el-table-column>
      <el-table-column label="共享失效时间" align="center" prop="expTime" :show-overflow-tooltip="true"
                       v-if="columns[6].visible"/>
      <el-table-column label="规则创建人" align="center" prop="createBy" v-if="columns[7].visible"/>
      <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true"
                       v-if="columns[8].visible"/>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[9].visible"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true"
                       v-if="columns[10].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:shareconfig:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['crm:shareconfig:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改共享规则配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="数据模块" prop="dataModel">
              <el-select v-model="form.dataModel" placeholder="请选择数据模块" clearable>
                <el-option v-for="dict in dict.type.crm_business_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="共享源类型" prop="sourceType">
              <el-radio-group v-model="form.sourceType" @input="sourceTypeChange">
                <el-radio v-for="item in shareTypes" :key="item.value" :label="item.value" :disabled="item.disabled">{{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="共享源" prop="sourceIdList">
              <treeselect v-if="form.sourceType!='u'" :multiple="true" :flat="true" :limit="2"
                          :limitText="count => `及其它${count}项`"
                          @select="node => sourceDeptHandleSelect(node)" style="line-height: 15px;"
                          @deselect="node => sourceDeptHandleDelSelect(node)"
                          v-model="form.sourceIdList" :options="deptOptions" :show-count="true" placeholder="请选择归属部门"/>
              <el-select v-if="form.sourceType=='u'" v-model="form.sourceIdList" placeholder="可搜索+多选" remote
                         multiple clearable filterable :remote-method="handleUserQuery" :loading="loading"
                         @change="$forceUpdate()">
                <el-option v-for="item in userList" :key="item.userId" :label="item.nickName"
                           :value="item.userId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="共享目标类型" prop="targetType">
              <el-radio-group v-model="form.targetType" @input="targetTypeChange">
                <el-radio v-for="item in shareTypes" :key="item.value" :label="item.value" :disabled="item.disabled">{{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="共享目标" prop="targetIdList">
              <treeselect v-if="form.targetType!='u'" :multiple="true" :flat="true" :limit="2"
                          :limitText="count => `及其它${count}项`"
                          @select="node => targetDeptHandleSelect(node)" style="line-height: 15px;"
                          @deselect="node => targetDeptHandleDelSelect(node)"
                          v-model="form.targetIdList" :options="deptOptions" :show-count="true" placeholder="请选择归属部门"/>
              <el-select v-if="form.targetType=='u'" v-model="form.targetIdList" placeholder="可搜索+多选" remote
                         multiple clearable filterable :remote-method="handleUserQuery" :loading="loading"
                         @change="$forceUpdate()">
                <el-option v-for="item in userList" :key="item.userId" :label="item.nickName"
                           :value="item.userId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="共享目标权限" prop="permission">
              <el-select v-model="form.permission" placeholder="请选择数据模块" clearable>
                <el-option v-for="dict in dict.type.sys_common_permission" :key="dict.value" :label="dict.label"
                           :value="dict.value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="共享失效时间" prop="expTime">
              <el-date-picker clearable v-model="form.expTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                              default-time="23:59:59" placeholder="请选择转移截至时间">
              </el-date-picker>
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
  listShareconfig,
  getShareconfig,
  delShareconfig,
  addShareconfig,
  updateShareconfig
} from "@/api/crm/shareconfig";
import {listUser, deptTreeSelect} from "@/api/system/user";
import { listEmployee } from "@/api/crm/employee";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Shareconfig",
  components: {Treeselect},
  dicts: ['sys_common_permission', 'crm_business_type', 'sys_yes_no'],
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
      // 共享规则配置表格数据
      shareconfigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dataModel: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dataModel: [
          {required: true, message: "数据模块不能为空", trigger: "change"}
        ],
        sourceIdList: [
          {required: true, message: "共享源不能为空", trigger: "blur"}
        ],
        sourceType: [
          {required: true, message: "共享源类型不能为空", trigger: "blur"}
        ],
        targetIdList: [
          {required: true, message: "共享目标不能为空", trigger: "blur"}
        ],
        targetType: [
          {required: true, message: "共享目标类型不能为空", trigger: "blur"}
        ],
        permission: [
          {required: true, message: "共享目标权限不能为空", trigger: "change"}
        ],
      },
      shareTypes: [{
        label: "人员", value: "u",disabled:false
      }, {
        label: "部门", value: "d",disabled:false
      }, {
        label: "部门及以下", value: "d+",disabled:false
      }],
      // 列信息
      columns: [
        {key: 0, label: `数据模块`, visible: true},
        {key: 1, label: `共享源`, visible: true},
        {key: 2, label: `共享源类型`, visible: true},
        {key: 3, label: `共享目标`, visible: true},
        {key: 4, label: `共享目标类型`, visible: true},
        {key: 5, label: `共享目标权限`, visible: true},
        {key: 6, label: `共享失效时间`, visible: false},
        {key: 7, label: `规则创建人`, visible: false},
        {key: 8, label: `创建时间`, visible: false},
        {key: 9, label: `更新人`, visible: false},
        {key: 10, label: `更新时间`, visible: false},
      ],
      // 部门树选项
      deptOptions: undefined,
      // 用户列表
      userList: [],
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    handleUserQuery(param) {
      debugger
      listEmployee({nickName: param}).then(response => {
        this.userList = response.rows;
      });
    },
    sourceTypeChange(param) {
      debugger
      this.form.sourceIdList = []
    },
    targetTypeChange(param) {
      debugger
      this.form.targetIdList = []
    },
    getShareTypeLable(val){
      for (var i = 0; i < this.shareTypes.length; i++) {
        if (this.shareTypes[i].value == val) {
          return this.shareTypes[i].label;
        }
      }
      return "具体数据";
    },
    /** 查询共享规则配置列表 */
    getList() {
      this.loading = true;
      listShareconfig(this.queryParams).then(response => {
        this.shareconfigList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    targetDeptHandleSelect(node) {
      debugger
      this.form.targetIdList.push(node.id)
    },
    targetDeptHandleDelSelect(node) {
      debugger
      this.form.targetIdList.splice(this.form.targetIdList.indexOf(node.id), 1)
    },
    sourceDeptHandleSelect(node) {
      debugger
      this.form.sourceIdList.push(node.id)
    },
    sourceDeptHandleDelSelect(node) {
      debugger
      this.form.sourceIdList.splice(this.form.targetIdList.indexOf(node.id), 1)
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
        dataModel: null,
        sourceIds: null,
        sourceIdList: [],
        sourceType: null,
        targetIds: null,
        targetIdList: [],
        targetType: null,
        permission: null,
        expTime: '2099-12-31 23:59:59',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
      this.handleUserQuery();
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
      this.title = "添加共享规则配置(此处未人员级和部门级)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShareconfig(id).then(response => {
        this.form = response.data;
        debugger
        this.form.targetIdList = this.form.targetIds ? this.form.targetIds.split(',').map(t => +t) : [];
        this.form.sourceIdList = this.form.sourceIds ? this.form.sourceIds.split(',').map(t => +t) : [];
        this.open = true;
        this.title = "修改共享规则配置(此处未人员级和部门级)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          debugger
          this.form.targetIds = this.form.targetIdList.toString();
          this.form.sourceIds = this.form.sourceIdList.toString();
          if (this.form.id != null) {
            updateShareconfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShareconfig(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除共享规则配置编号为"' + ids + '"的数据项？').then(function () {
        return delShareconfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/shareconfig/export', {
        ...this.queryParams
      }, `shareconfig_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
