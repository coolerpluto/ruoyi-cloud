<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="168px">
      <el-form-item label="Lic工具名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入License工具名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="适用系统" prop="systemAdapter">
        <el-select v-model="queryParams.systemAdapter" placeholder="请选择适用系统" clearable>
          <el-option
            v-for="dict in dict.type.medium_file_system_mapping"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="机器码查询工具名称" prop="macFileId">
        <el-input
          v-model="queryParams.macFileId"
          placeholder="请输入机器码文件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['medium:lic_tool:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['medium:lic_tool:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['medium:lic_tool:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:lic_tool:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lic_toolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" width="95" prop="id" />
      <el-table-column label="License工具名称" align="center" prop="name" />
      <el-table-column label="Lic文件适用系统" align="center" prop="systemAdapter" v-if="columns[0].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_file_system_mapping" :value="scope.row.systemAdapter.split(',')"/>
        </template>
      </el-table-column>
      <el-table-column label="需机器码" align="center" prop="macNeed" v-if="columns[1].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.macNeed"/>
        </template>
      </el-table-column>
      <el-table-column label="最大连接数" align="center" prop="maxConnectNum" v-if="columns[2].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.maxConnectNum"/>
        </template>
      </el-table-column>
      <el-table-column label="CPU数量" align="center" prop="cpuNum" v-if="columns[3].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.cpuNum"/>
        </template>
      </el-table-column>
      <el-table-column label="集群节点数" align="center" prop="clusterNodes" v-if="columns[4].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.clusterNodes"/>
        </template>
      </el-table-column>
      <el-table-column label="部署IP" align="center" prop="deployServer" v-if="columns[5].visible"/>
      <el-table-column label="部署路径" align="center" prop="deployPath" v-if="columns[6].visible"/>
      <el-table-column label="Lic工具文件" align="center" prop="toolFileName" v-if="columns[7].visible">
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-download"
            @click="handleDownloadFileByFileId(scope.row.toolFileId,scope.row.toolFileName)"
          >{{ scope.row.toolFileName }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="机器码文件" align="center" prop="macFileName" v-if="columns[8].visible">
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-download"
            @click="handleDownloadFileByFileId(scope.row.macFileId,scope.row.macFileName)"
          >{{ scope.row.macFileName }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="Lic工具状态" align="center" prop="status" v-if="columns[9].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" v-if="columns[10].visible"/>
      <el-table-column label="更新者" align="center" prop="updateBy" v-if="columns[11].visible"/>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[12].visible"/>
      <el-table-column label="操作" align="center"  width="115" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medium:lic_tool:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:lic_tool:remove']"
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

    <!-- 添加或修改License工具信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body v-loading="dialogLoading">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Lic工具名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入工具名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Lic适用系统" prop="systemAdapter">
              <el-select v-model="form.systemAdapter" placeholder="请选择适用系统" multiple collapse-tags>
                <el-option
                  v-for="dict in dict.type.medium_file_system_mapping"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="需机器码" prop="macNeed">
              <el-radio-group v-model="form.macNeed" @change="changeMacNeedHandler">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="需集群节点数" prop="clusterNodes">
              <el-radio-group v-model="form.clusterNodes">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="需最大连接数" prop="maxConnectNum">
              <el-radio-group v-model="form.maxConnectNum">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="需CPU数量" prop="cpuNum">
              <el-radio-group v-model="form.cpuNum">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部署IP" prop="deployServer">
              <el-input v-model="form.deployServer" placeholder="请输入部署IP" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部署路径" prop="deployPath">
              <el-input v-model="form.deployPath" placeholder="请输入部署绝对路径" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Lic工具文件" prop="toolFile">
              <el-upload
                ref="uploadToolFile"
                action=""
                class="upload-demo"
                :http-request="httpLicToolFileRequest"
                :multiple="false"
                :limit="1"
                :auto-upload="true"
                :before-upload="handleBeforeUpload"
                accept=".rpm, .deb, .tar, .zip, .7z, .rar"
                :on-exceed="handleExceed"
              >
                <el-button size="small" type="primary">选择文件</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机器码文件" prop="macFile">
              <el-upload
                ref="uploadMacFile"
                action=""
                class="upload-demo"
                :http-request="httpMacToolFileMd5Request"
                :multiple="false"
                :limit="1"
                :auto-upload="true"
                :before-upload="handleBeforeUpload"
                accept=".zip, .7z, .rar, .txt, .md5"
                :on-exceed="handleExceed"
              >
                <el-button size="small" type="primary">选择文件</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上次文件名" >
              <el-input disabled v-model="form.toolFileName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上次文件名" >
              <el-input  disabled v-model="form.macFileName" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-form-item label="工具状态" prop="status" v-hasPermi="['medium:lictool:public']">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入内容" />
          </el-form-item>
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
import { listLic_tool, getLic_tool, delLic_tool, addLic_tool,addLic_tool_withFile, updateLic_tool,updateLic_tool_withFile } from "@/api/medium/lic_tool";

export default {
  name: "Lic_tool",
  dicts: ['sys_yes_no', 'sys_normal_disable', 'medium_file_system_mapping'],
  data() {
    return {
      // 遮罩层
      loading: true,
      dialogLoading: true,
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
      // License工具信息表格数据
      lic_toolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        systemAdapter: null,
        macNeed: null,
        maxConnectNum: null,
        cpuNum: null,
        clusterNodes: null,
        deployServer: null,
        deployPath: null,
        toolFileId: null,
        macFileId: null,
        status: null,
        createId: null,
        updateId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "工具名称不能为空", trigger: "blur" }
        ],
        systemAdapter: [
          { required: true, message: "适用系统不能为空", trigger: "change" }
        ],

        status: [
          { required: true, message: "Lic工具文件 状态未选择", trigger: "blur" }
        ],

        macNeed: [
          { required: true, message: "是否需要需机器码未选", trigger: "blur" }
        ],
        clusterNodes: [
          { required: true, message: "是否需集群节点数未选", trigger: "blur" }
        ],
        maxConnectNum: [
          { required: true, message: "是否需最大连接数未选", trigger: "blur" }
        ],
        cpuNum: [
          { required: true, message: "是否需CPU数量未选", trigger: "blur" }
        ],

        deployServer: [
          { required: true, message: "部署IP未填写", trigger: "blur" }
        ],
        deployPath: [
          { required: true, message: "部署路径未填写", trigger: "blur" }
        ],
        toolFile: [
          { required: false, message: "License工具文件未选择上传", trigger: "blur" },
        ],
        macFile: [
          { required: false, message: "选择需要机器码,但查询工具未选择上传", trigger: "blur" },
        ],
      },
      // 列信息
      columns: [
        { key: 0, label: `适用系统`, visible: false },
        { key: 1, label: `需机器码`, visible: false },
        { key: 2, label: `最大连接数`, visible: false },
        { key: 3, label: `CPU数量`, visible: false },
        { key: 4, label: `集群节点数`, visible: false },
        { key: 5, label: `部署IP`, visible: false },
        { key: 6, label: `部署路径`, visible: false },
        { key: 7, label: `Lic工具文件`, visible: true },
        { key: 8, label: `机器码文件`, visible: true },
        { key: 9, label: `Lic工具状态`, visible: false },
        { key: 10, label: `创建者`, visible: false },
        { key: 11, label: `更新者`, visible: false },
        { key: 12, label: `备注`, visible: true },
      ]
    };
  },
  created() {
    this.getList();
    // 待完成文件下载
  },
  methods: {
    /** 查询License工具信息列表 */
    getList() {
      this.loading = true;
      listLic_tool(this.queryParams).then(response => {
        this.lic_toolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      // 弹框关闭后 清理一下上传文件的列表
      this.$refs.uploadToolFile.clearFiles()
      this.$refs.uploadMacFile.clearFiles()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        systemAdapter: null,
        macNeed: null,
        maxConnectNum: null,
        cpuNum: null,
        clusterNodes: null,
        deployServer: null,
        deployPath: null,
        toolFileId: null,
        toolFileName:null,
        macFileId: null,
        macFileName: null,
        status: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.rules.toolFile =  [
        { required: true, message: "License工具文件未选择上传", trigger: "blur" },
      ]
      this.title = "添加License工具信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLic_tool(id).then(response => {
        this.form = response.data;
        if(this.form.systemAdapter){
          this.form.systemAdapter = this.form.systemAdapter.split(",");
        }
        this.open = true;
        this.rules.toolFile =  [
          { required: false, message: "License工具文件未选择上传", trigger: "blur" },
        ]
        this.title = "修改License工具信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      // 手动调用上传，这里会调用我们自己定义的
      this.$refs.uploadMacFile.submit();
      this.$refs.uploadToolFile.submit();

      this.$refs["form"].validate(valid => {
        if (valid) {
          // 新建form表单
          const fd = new FormData();
          // 将data转换为form-data
          Object.keys(this.form).forEach((key) => {
            if (this.form[key] instanceof Array) {
              // 如果是数组就循环加入表单，key保持相同即可，这就是表达单的数组
              this.form[key].forEach((item) => {
                fd.append(key, item);
              });
            } else if (this.form[key]) {
              // 如果不是数组且不为空，为空忽略就直接追加进去
              fd.append(key, this.form[key]);
            }
          });
          this.dialogLoading = true
          if (this.form.id != null) {
            //updateLic_tool(this.form).then(response => {
            updateLic_tool_withFile(fd).then(response => {
              this.$modal.msgSuccess("修改成功");
              // 弹框关闭后 清理一下上传文件的列表
              this.$refs.uploadToolFile.clearFiles();
              this.$refs.uploadMacFile.clearFiles();
              this.dialogLoading = false;
              this.open = false;
              this.getList();
            });
          } else {
            //addLic_tool(this.form).then(response => {
            addLic_tool_withFile(fd).then(response => {
              this.$modal.msgSuccess("新增成功");
              // 弹框关闭后 清理一下上传文件的列表
              this.$refs.uploadToolFile.clearFiles();
              this.$refs.uploadMacFile.clearFiles();
              this.dialogLoading = false;
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
      this.$modal.confirm('是否确认删除License工具信息编号为"' + ids + '"的数据项？').then(function() {
        return delLic_tool(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/lic_tool/export', {
        ...this.queryParams
      }, `lic_tool_${new Date().getTime()}.xlsx`)
    },
    changeMacNeedHandler(value) {
      if ('Y' === value) {
        this.rules.macFile = [
          {required: true, message: "选择需要机器码,但查询工具未选择上传", trigger: "blur"},
        ]
      } else {
        this.rules.macFile = [
          {required: false, message: "选择需要机器码,但查询工具未选择上传", trigger: "blur"},
        ]
      }
    },
    // 自定义的提交函数，取出文件设置进请求参数
    httpMacToolFileMd5Request(param) {
      this.form.macFile = param.file;
    },
    httpLicToolFileRequest(param) {
      this.form.toolFile = param.file;
    },
    handleExceed(file, fileList) {
      if (fileList.length > 0) {
        this.$message.error("上传文件大小不能超过1个");
        return false;
      }
    },
    handleBeforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      const whiteList = ["tar", "deb", "rpm", "zip","rar", "7z", "rar", "txt","md5"];
      if (whiteList.indexOf(fileSuffix) === -1) {
        this.$message.error("上传文件只能是特定格式");
        return false;
      }
      const isLtM = file.size / 1024 / 1024 < 500;
      if (!isLtM) {
        this.$message.error("上传文件大小不能超过 500MB");
        return false;
      }
    },
    handleDownloadFileByFileId(fileId,fileName){
      fileName = fileName+".zip";
      this.download("medium/file/download",{id:fileId},fileName,{timeout: 120000});
    },
  }
};
</script>
