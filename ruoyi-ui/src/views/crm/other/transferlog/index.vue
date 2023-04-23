<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="数据模块" prop="model">
        <el-select v-model="queryParams.model" placeholder="请选择数据模块" clearable>
          <el-option v-for="dict in dict.type.crm_business_type" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="转移人" prop="userFrom">
        <el-select v-model="queryParams.userFrom" clearable placeholder="请输入 关键字拼音检索" filterable remote
          :remote-method="getPersonOptions" :loading="flag.transferTargetPersonLoading">
          <el-option v-for="item in personOptions" :key="item.userName" :label="item.nickName" :value="item.userName">
            <span style="float: left">{{ item.nickName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ item.dept.deptName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接收人" prop="userTo">
        <el-select v-model="queryParams.userTo" clearable placeholder="请输入 关键字拼音检索" filterable remote
          :remote-method="getPersonOptions" :loading="flag.transferTargetPersonLoading">
          <el-option v-for="item in personOptions" :key="item.userName" :label="item.nickName" :value="item.userId">
            <span style="float: left">{{ item.nickName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ item.dept.deptName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="截至时间" prop="actionTime">
        <el-date-picker clearable v-model="queryParams.actionTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                        default-time="23:59:59" placeholder="请选择转移截至时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['crm:transferlog:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:transferlog:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:transferlog:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:transferlog:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferlogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="数据模块" align="center" prop="model" v-if="columns[0].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_business_type" :value="scope.row.model"/>
        </template>
      </el-table-column>
      <el-table-column label="来源" align="center" prop="userFrom" :show-overflow-tooltip="true" v-if="columns[1].visible"/>
      <el-table-column label="目标" align="center" prop="userTo" :show-overflow-tooltip="true" v-if="columns[2].visible"/>
      <el-table-column label="操作的记录的ID" align="center" prop="recordIds" :show-overflow-tooltip="true" v-if="columns[3].visible"/>
      <el-table-column label="单次转移数量" align="center" prop="quantity" v-if="columns[4].visible"/>
      <el-table-column label="转移原因" align="center" prop="reason" :show-overflow-tooltip="true" v-if="columns[5].visible"/>
      <el-table-column label="操作时间" align="center" prop="actionTime" v-if="columns[6].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:transferlog:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['crm:transferlog:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
  </div>
</template>

<script>
import {
  listTransferlog,
  getTransferlog,
  delTransferlog,
  addTransferlog,
  updateTransferlog
} from "@/api/crm/transferlog";
import { listEmployee } from "@/api/crm/employee";

export default {
  name: "Transferlog",
  dicts: ['crm_business_type'],
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
      // 转移日志表格数据
      transferlogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        model: null,
        userFrom: null,
        userTo: null,
        actionTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 列信息
      columns: [
        {key: 0, label: `数据模块`, visible: true},
        {key: 1, label: `来源`, visible: true},
        {key: 2, label: `目标`, visible: false},
        {key: 3, label: `操作的记录的ID`, visible: true},
        {key: 4, label: `单次转移数量`, visible: false},
        {key: 5, label: `转移原因`, visible: false},
        {key: 6, label: `操作时间`, visible: false}
      ],
      personOptions: [],
      flag: {
        transferTargetPersonLoading: false,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询转移日志列表 */
    getList() {
      this.loading = true;
      listTransferlog(this.queryParams).then(response => {
        this.transferlogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getPersonOptions(query) {
      this.flag.transferTargetPersonLoading = true
      listEmployee({
        pageNum: 1,
        pageSize: 20,
        userName: query,
      }).then(response => {
        this.personOptions = response.rows;
        this.flag.transferTargetPersonLoading = false;
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
        model: null,
        userFrom: null,
        userTo: null,
        recordIds: null,
        quantity: null,
        reason: null,
        actionTime: null
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
      this.$modal.alert("数据为转移记录,界面不支持手动新增");
      // this.reset();
      // this.open = true;
      // this.title = "添加转移日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$modal.alert("为防止数据被篡改,界面不支持手动修改");
      // this.reset();
      // const id = row.id || this.ids
      // getTransferlog(id).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改转移日志";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTransferlog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTransferlog(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除转移日志编号为"' + ids + '"的数据项？').then(function () {
        return delTransferlog(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/transferlog/export', {
        ...this.queryParams
      }, `transferlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
