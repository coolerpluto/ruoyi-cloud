<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="邮件服务器" prop="server">
        <el-input v-model="queryParams.server" placeholder="请输入" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="是否启用" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择是否启用" clearable>
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <span type="primary">
          有多个启动状态的配置时默认最新的一个</span>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['crm:mailconfig:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:mailconfig:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:mailconfig:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:mailconfig:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mailconfigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="邮件服务器" align="center" prop="server" :show-overflow-tooltip="true"
                       v-if="columns[0].visible"/>
      <el-table-column label="邮箱账户" align="center" prop="secretKey" :show-overflow-tooltip="true"
                       v-if="columns[1].visible"/>
      <el-table-column label="昵称" align="center" prop="nickName" :show-overflow-tooltip="true"
                       v-if="columns[2].visible"/>
      <el-table-column label="是否启用" align="center" prop="status" v-if="columns[3].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" :show-overflow-tooltip="true"
                       v-if="columns[4].visible"/>
      <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true"
                       v-if="columns[5].visible"/>
      <el-table-column label="更新人员" align="center" prop="updateBy" :show-overflow-tooltip="true"
                       v-if="columns[6].visible"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true"
                       v-if="columns[7].visible"/>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" v-if="columns[8].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:mailconfig:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['crm:mailconfig:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改邮箱设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否启用" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="别名" prop="nickName">
              <el-input v-model="form.nickName" placeholder="给记录起个别名"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮件服务器" prop="server">
              <el-input v-model="form.server" placeholder="请输入邮件服务器地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务器端口" prop="port">
              <el-input v-model="form.port" placeholder="请输入邮件服务器使用端口"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱账户" prop="secretKey">
              <!--需要区分资源类型， 是人员还是部门-->
              <el-input v-model="form.secretKey" placeholder="请输入邮箱账户名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱密码" prop="accessKey">
              <!--需要区分资源类型， 是人员还是部门-->
              <el-input v-model="form.accessKey" placeholder="请输入邮箱账户名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :rows="3" v-model="form.remark"></el-input>
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
import {listMailconfig, getMailconfig, delMailconfig, addMailconfig, updateMailconfig} from "@/api/crm/mailconfig";

export default {
  name: "Mailconfig",
  dicts: ['sys_normal_disable'],
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
      // 邮箱设置表格数据
      mailconfigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        server: null,
        secretKey: null,
        nickName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        server: [
          {required: true, message: "邮件发送服务器不能为空", trigger: "blur"}
        ],
        secretKey: [
          {required: true, message: "邮箱账户不能为空", trigger: "blur"},
          {type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"]}
        ],
        accessKey: [
          {required: true, message: "账户密码不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "是否启用不能为空", trigger: "change"}
        ],
      },
      // 列信息
      columns: [
        {key: 0, label: `邮件服务器`, visible: true},
        {key: 1, label: `邮箱账户`, visible: true},
        {key: 2, label: `昵称`, visible: true},
        {key: 3, label: `是否启用`, visible: true},
        {key: 4, label: `创建者`, visible: false},
        {key: 5, label: `创建时间`, visible: false},
        {key: 6, label: `更新人员`, visible: false},
        {key: 7, label: `更新时间`, visible: false},
        {key: 8, label: `备注`, visible: false},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询邮箱设置列表 */
    getList() {
      this.loading = true;
      listMailconfig(this.queryParams).then(response => {
        this.mailconfigList = response.rows;
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
        server: null,
        port: null,
        secretKey: null,
        accessKey: null,
        nickName: null,
        status: null,
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
      this.title = "添加邮箱设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMailconfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改邮箱设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMailconfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMailconfig(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除邮箱设置编号为"' + ids + '"的数据项？').then(function () {
        return delMailconfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/mailconfig/export', {
        ...this.queryParams
      }, `mailconfig_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
