<template>
  <!-- 导入表 -->
  <el-dialog title="指定数据源-导入表" :visible.sync="visible" width="1200px" top="5vh" append-to-body v-loading="loading">
    <el-form :model="queryDbSourceParams" ref="queryDbSourceParams" size="small" :inline="true" label-width="108px">
      <el-form-item label="数据库名称" prop="name">
        <el-input
          v-model="queryDbSourceParams.name"
          placeholder="请输入数据库名称"
          clearable
          @keyup.enter.native="handleDbSourceQuery"
        />
      </el-form-item>
      <el-form-item label="数据库类型" prop="dbType">
        <el-select v-model="queryDbSourceParams.dbType" placeholder="请选择数据库类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_system_db_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleDbSourceQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetDbSourceQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table v-loading="genDbSourceLoading" :data="genDbSourceList"
                height="200px" highlight-current-row @current-change="dbSourceChange"
                @selection-change="handleSelectionDbSourceChange">
        <el-table-column label="数据源主键" align="center" prop="id"/>
        <el-table-column label="数据库名称" align="center" prop="name"/>
        <el-table-column label="数据库类型" align="center" prop="dbType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_system_db_type" :value="scope.row.dbType"/>
          </template>
        </el-table-column>
        <el-table-column label="主机地址" align="center" prop="host"/>
        <el-table-column label="主机端口" align="center" prop="port"/>
        <el-table-column label="用户名" align="center" prop="username"/>
        <el-table-column label="密码" align="center" prop="password"/>
      </el-table>
      <pagination
        v-show="dbSourceTotal>0"
        :total="dbSourceTotal" :pageSizes="[4, 5, 6]"
        :page.sync="queryDbSourceParams.pageNum"
        :limit.sync="queryDbSourceParams.pageSize"
        @pagination="getDbSourceList"
      />
    </el-row>
    <el-divider/>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入表描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="dbTableList" @selection-change="handleSelectionChange" height="200px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="tableName" label="表名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="tableComment" label="表描述" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :pageSizes="[4, 5, 6]"
        :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleImportTable">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listAssignedDbTable, importTableAssignedDB } from "@/api/tool/gen";
import { listGenDbSource } from "@/api/tool/genDbSource";
export default {
  name: "ImportTableAssignedDB",
  dicts: ['sys_yes_no', 'sys_system_db_type'],
  data() {
    return {
      loading:false,
      // 界面显示和关闭
      visible: false,
      // 选中数组值
      tables: [],
      // 总条数
      total: 0,
      // 表数据
      dbTableList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 4,
        tableName: undefined,
        tableComment: undefined,
        params:{}
      },
      dbSourceSelected:{},
      // 数据源总数
      dbSourceTotal: 0,
      // 遮罩层
      genDbSourceLoading: true,
      // 数据源配置表格数据
      genDbSourceList: [],
      daterangeCreateTime:[],
      // 数据源配置查询参数
      queryDbSourceParams:{
        pageNum: 1,
        pageSize: 4,
        status:"Y",
        name: null,
        dbType: null
      }
    };
  },
  methods: {
    handleDbSourceQuery(){
      this.queryDbSourceParams.pageNum = 1;
      this.getDbSourceList();
    },
    resetDbSourceQuery(){
      this.resetForm("queryDbSourceParams");
      this.handleDbSourceQuery();
    },
    handleSelectionDbSourceChange(){},
    getDbSourceList(){
      this.genDbSourceLoading = true;
      this.queryDbSourceParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryDbSourceParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryDbSourceParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listGenDbSource(this.queryDbSourceParams).then(response => {
        this.genDbSourceList = response.rows;
        this.dbSourceTotal = response.total;
        this.genDbSourceLoading = false;
      });
    },
    dbSourceChange(current,old){
      this.dbTableList = [];
      this.dbSourceSelected = current;
    },
    // 显示弹框
    show() {
      this.getDbSourceList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.tables = selection.map(item => item.tableName);
    },
    // 查询表数据
    getList() {
      if(!this.dbSourceSelected){
        this.$modal.msgError("请先点击数据源，再查询");
        return;
      }
      this.queryParams.dataSourceId = this.dbSourceSelected.id
      listAssignedDbTable(this.queryParams).then(res => {
        if (res.code === 200) {
          this.dbTableList = res.rows;
          this.total = res.total;
        }
      });
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
    /** 导入按钮操作 */
    handleImportTable() {
      if (!this.dbSourceSelected.id) {
        this.$modal.msgError("请选择导入的表对应的数据源");
        return;
      }
      const tableNames = this.tables.join(",");
      if (tableNames == "") {
        this.$modal.msgError("请选择要导入的表");
        return;
      }
      this.loading =true;
      importTableAssignedDB({
        tables: tableNames,
        dataSourceId: this.dbSourceSelected.id
      }).then(res => {
        this.loading =false;
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
