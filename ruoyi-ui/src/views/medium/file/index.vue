<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="服务器" prop="fileServer">
        <el-input
          v-model="queryParams.fileServer"
          placeholder="请输入文件服务器IP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件路径" prop="filePath">
        <el-input
          v-model="queryParams.filePath"
          placeholder="请输入文件路径"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['medium:file:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:file:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>

    </el-row>

    <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="id" :show-overflow-tooltip="true" width="180px" v-if="columns[0].visible" />
      <el-table-column label="文件服务器" align="center" prop="fileServer" :show-overflow-tooltip="true" v-if="columns[1].visible" />
      <el-table-column label="文件路径" align="center" prop="filePath" :show-overflow-tooltip="true" v-if="columns[2].visible" />
      <el-table-column label="文件名" align="center" prop="fileName" :show-overflow-tooltip="true" v-if="columns[3].visible" />
      <el-table-column label="文件类型" align="center" prop="fileType" width="80px" v-if="columns[4].visible" />
      <el-table-column label="记录状态" align="center" prop="status" width="80px" v-if="columns[5].visible" >
         <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_normal_disable"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>

      <el-table-column label="创建者名" align="center" prop="createBy" :show-overflow-tooltip="true" width="100px" v-if="columns[6].visible" />
      <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true" width="150px" v-if="columns[7].visible" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  width="180px" v-if="columns[8].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-zoom-in"
            @click="handleAssociation(scope.row)"
          >查看关联</el-button>
          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:file:remove']"
          >删除</el-button>
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

    <!-- 添加或修改文件记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px" disabled>
        <el-form-item label="文件名字" prop="file_name">
          <el-input
            v-model="form.file_name"
          />
        </el-form-item>
        <el-form-item label="文件路径" prop="file_path">
          <el-input
            v-model="form.file_path"
          />
        </el-form-item>
        <el-form-item label="文件后缀" prop="file_type">
          <el-input
            v-model="form.file_type"
          />
        </el-form-item>
        <el-form-item label="介质别名" prop="medium_name">
          <el-input
            v-model="form.medium_name"
          />
        </el-form-item>
        <el-form-item label="附件别名" prop="security_name">
          <el-input
            v-model="form.security_name"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFile, getFile, delFile, addFile, updateFile,getFileAssociation } from "@/api/medium/file";

export default {
  name: "File",
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
      // 列信息
      columns: [
        { key: 0, label: `记录ID`, visible: true },
        { key: 1, label: `文件服务器`, visible: false },
        { key: 2, label: `文件路径`, visible: true },
        { key: 3, label: `文件名`, visible: true },
        { key: 4, label: `文件类型`, visible: false },
        { key: 5, label: `记录状态`, visible: false },
        { key: 6, label: `创建者名`, visible: false },
        { key: 7, label: `创建时间`, visible: false },
        { key: 8, label: `操作列`, visible: true },
      ],
      // 总条数
      total: 0,
      // 文件记录表格数据
      fileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileServer: null,
        filePath: null,
        fileName: null,
        fileType: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      },
      // 表单参数
      form: {},      
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文件记录列表 */
    getList() {
      this.loading = true;
      listFile(this.queryParams).then(response => {
        this.fileList = response.rows;
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
        file_name: null,
        file_path: null,
        file_type: null,
        medium_name:null,
        security_name:null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
   
    handleAssociation(row){
      this.reset();
      getFileAssociation(row.id).then(response => {
        if (200==response.code){
          if (response.data[0]){
            this.form = response.data[0];        
          }else{
            this.$modal.msgSuccess("暂未发现关联关系")
            return
          }
          this.open = true;
          this.title = "查看文件关联信息";
        }else{
          this.$modal.msgError("查询失败，请稍后重试");
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.loading = true;
      //this.$modal.confirm('是否确认删除文件记录编号为"' + ids + '"的数据项？').then(function() {
      this.$modal.confirm('是否确认删除选择的文件吗？删除后对应下载将会报错请慎重！').then(function() {
        return delFile(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/file/export', {
        ...this.queryParams
      }, `file_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
