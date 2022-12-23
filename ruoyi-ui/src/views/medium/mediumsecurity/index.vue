<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件类型" prop="fileType">
        <el-select v-model="queryParams.fileType" placeholder="请选择文件类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_file_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['medium:mediumsecurity:add']"
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
          v-hasPermi="['medium:mediumsecurity:edit']"
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
          v-hasPermi="['medium:mediumsecurity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:mediumsecurity:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mediumsecurityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="别名名称" align="center" prop="securityName" :show-overflow-tooltip="true" width="180px"/>
      <el-table-column label="文件类型" align="center" prop="fileType" width="80px">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.medium_file_type"
            :value="scope.row.fileType"
          />
        </template>
      </el-table-column>
      
      <el-table-column label="文件名" align="center" prop="fileName" :show-overflow-tooltip="true"/>
      <el-table-column label="md5名称" align="center" prop="md5FileName" :show-overflow-tooltip="true"/>
      <el-table-column label="文件状态" align="center" prop="status" width="80px">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_normal_disable"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>

      <el-table-column label="对应介质" align="center" prop="mediumName" :show-overflow-tooltip="true"/>      

      <el-table-column label="创建人" align="center" prop="createBy" :show-overflow-tooltip="true" width="80px"/>
      <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true" width="160px"/>
      <el-table-column label="更新人" align="center" prop="updateBy" :show-overflow-tooltip="true" width="80px"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true" width="160px"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medium:mediumsecurity:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:mediumsecurity:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row)"
            v-hasPermi="['medium:mediumsecurity:download']"
            >下载</el-button>
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

    <!-- 添加或修改介质安全文件记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="关联介质" prop="mediumId">
              <el-select @change="selectMedium"
                v-model="form.mediumId"
                placeholder="请选择介质"
                clearable
              >
                <el-option
                  v-for="medium in mediumSimpleList"
                  :key="medium.id"
                  :label="medium.mediumName"
                  :value="medium.id"                  
                >
                <span style="float: left">{{ medium.mediumName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ medium.cpuType }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ medium.dbVersion }}-</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ medium.packageType }}-</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="版本类型">
              <el-select v-model="mediumSelect.mediumType" disabled  placeholder="请选择">
                <el-option
                  v-for="item in dict.type.medium_version_type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
          <el-form-item label="CPU版本">
            <el-select v-model="mediumSelect.cpuType" disabled  placeholder="请选择">
              <el-option
              v-for="item in dict.type.medium_cpu_model"
              :key="item.value"
              :label="item.label"
              :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>  
          </el-col>
          <el-col :span="8">      
          <el-form-item label="打包方式">
            <el-select v-model="mediumSelect.packageType" disabled placeholder="请选择">
              <el-option
                v-for="item in dict.type.medium_package_type"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="8">
          <el-form-item label="数据库版本">
            <el-select v-model="mediumSelect.dbVersion" disabled  placeholder="请选择">
              <el-option
                v-for="item in dict.type.medium_db_version"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="附件别名" prop="securityName">
              <el-input
                v-model="form.securityName"
                placeholder="请输入附件别名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="附件种类" prop="fileType">
              <el-select
                v-model="form.fileType"
                placeholder="请选择附件种类"
                clearable
              >
                <el-option
                  v-for="dict in dict.type.medium_file_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="文件状态" prop="status">
              <el-select
                v-model="form.status"
                placeholder="status"
              >
                <el-option
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="附件文件" prop="securityFile">
              <el-upload
                ref="uploadSecurityFile"
                action=""
                class="upload-demo"
                :http-request="httpSecurityFileRequest"
                :multiple="false"
                :limit="1"
                :auto-upload="true"
                :before-upload="handleBeforeUpload"
                accept=".rpm, .deb, .tar, .zip, .7z, .rar, .q7"
                :on-exceed="handleExceed"
              >
                <el-button size="small" type="primary">选择文件</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="MD5文件" prop="securityFileMd5">
              <el-upload
                ref="uploadSecurityFileMd5"
                action=""
                class="upload-demo"
                :http-request="httpSecurityFileMd5Request"
                :multiple="false"
                :limit="1"
                :auto-upload="true"
                :before-upload="handleBeforeUpload"
                accept=".zip, .7z, .rar, .md5"
                :on-exceed="handleExceed"
              >
                <el-button size="small" type="primary">选择文件</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上次文件名" >
              <el-input disabled
                v-model="form.fileName"
                placeholder="请输入附件别名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上次文件名" >
              <el-input  disabled
                v-model="form.md5FileName"
                placeholder="请输入附件别名"
              />
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
import { listMediumsecurity, getMediumsecurity, delMediumsecurity, addMediumsecurityWithFile, updateMediumsecurityWithFile } from "@/api/medium/mediumsecurity";
import { simpleMediumList } from "@/api/medium/medium";

export default {
  name: "Mediumsecurity",
  dicts: ['sys_file_type','sys_normal_disable',
  'medium_file_type','medium_version_type','medium_cpu_model',
  "sys_yes_no","medium_package_type","medium_db_version"],
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
      // 介质文件
      mediumSelect:{},
      mediumSimpleList: [],
      // 介质安全文件记录表格数据
      mediumsecurityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileType: null,
        createBy: null,
        createTime: null,
      },
      action:undefined,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mediumId: [
          { required: true, message: "数据库版本未选择", trigger: "blur" },
        ],
        fileType: [
          { required: true, message: "附件种类未选择", trigger: "blur" },
        ],
        securityFile: [
          { required: true, message: "文件未选择上传", trigger: "blur" },
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getMediumList();
  },
  methods: {
    /** 查询介质安全文件记录列表 */
    getList() {
      this.loading = true;
      listMediumsecurity(this.queryParams).then(response => {
        this.mediumsecurityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      
    },
    getMediumList(){
      simpleMediumList({}).then((response) => {
        this.mediumSimpleList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      // 弹框关闭后 清理一下上传文件的列表
      this.$refs.uploadSecurityFile.clearFiles()
      this.$refs.uploadSecurityFileMd5.clearFiles()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        securityName:null,
        mediumId: null,   
        securityFile:null,     
        fileType: null,
        fileId: null,
        md5FileId: null,
        status: "0",
        remark: null
      };
      this.mediumSelect={};
      this.resetForm("form");
    },
    // 自定义的提交函数，取出文件设置进请求参数
    httpSecurityFileMd5Request(param) {
      this.form.securityFileMd5 = param.file;
    },
    httpSecurityFileRequest(param) {
      this.form.securityFile = param.file;
    },
    handleBeforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      const whiteList = ["tar", "deb", "rpm", "zip","rar", "7z", "rar", "q7","md5"];
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
    handleExceed(file, fileList) {
      if (fileList.length > 0) {
        this.$message.error("上传文件大小不能超过1个");
        return false;
      }
    },
    selectMedium(current){
      this.mediumSelect = this.mediumSimpleList.find(item=>{
        return item.id == current;
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
      this.rules.securityFile =  [
          { required: true, message: "文件未选择上传", trigger: "blur" },
        ]
      this.title = "添加介质安全文件记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMediumsecurity(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.rules.securityFile =  [
          { required: false, message: "文件未选择上传", trigger: "blur"  },
        ]
        this.mediumSelect = this.mediumSimpleList.find(item=>{
          return item.id == this.form.mediumId;
        });
        this.title = "修改介质安全文件记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.loading = true;
      this.$refs["form"].validate(valid => {
        // 手动调用上传，这里会调用我们自己定义的
        this.$refs.uploadSecurityFileMd5.submit();
        this.$refs.uploadSecurityFile.submit();
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
          if (this.form.id != null) {
            updateMediumsecurityWithFile(fd).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.loading = false;
              this.open = false;
              this.getList();
            });
          } else {
            addMediumsecurityWithFile(fd).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.loading = false;
              this.open = false;
              this.getList();
            });
          }
          // 弹框关闭后 清理一下上传文件的列表
          this.$refs.uploadSecurityFile.clearFiles()
          this.$refs.uploadSecurityFileMd5.clearFiles()
        }else{
          this.loading = false;
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除介质安全文件记录编号为"' + ids + '"的数据项？').then(function() {
        return delMediumsecurity(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
      /**
     * 下载文件
     */
    handleDownload(row){
      const fileName = row.securityName+".zip";
      // 修改默认时间 1min
      this.download("medium/mediumsecurity/download",{...row,},fileName,{timeout: 120000});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/mediumsecurity/export', {
        ...this.queryParams
      }, `mediumsecurity_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
