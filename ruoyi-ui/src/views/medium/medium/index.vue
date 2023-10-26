<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="78px"
    >
      <el-form-item label="版本类型" prop="mediumType">
        <el-select
          v-model="queryParams.mediumType"
          placeholder="请选择版本类型"
          clearable
        >
          <el-option
            v-for="dict in dict.type.medium_version_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="数据库" prop="dbVersion">
        <el-select
          v-model="queryParams.dbVersion"
          placeholder="请选择数据库版本"
          clearable
        >
          <el-option
            v-for="dict in dict.type.medium_db_version"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="CPU版" prop="cpuType">
        <el-select
          v-model="queryParams.cpuType"
          placeholder="请选择CPU版本"
          clearable
        >
          <el-option
            v-for="dict in dict.type.medium_cpu_model"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="适用系统" prop="systemAdapter">
        <el-select
          v-model="queryParams.systemAdapter"
          placeholder="请选择适用系统"
          clearable
        >
          <el-option
            v-for="dict in dict.type.medium_file_system_mapping"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="打包方式" prop="packageType">
        <el-select
          v-model="queryParams.packageType"
          placeholder="请选择打包方式"
          clearable
        >
          <el-option
            v-for="dict in dict.type.medium_package_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="含postgis" prop="hasPostgis">
        <el-tooltip content="含签名和含postgis 一个是一个否时,结果以是的查询为结果" placement="top">
          <i class="el-icon-question"></i>
        </el-tooltip>
        <el-select
          v-model="queryParams.params.hasPostgis"
          placeholder="请选择"
          clearable
        >
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="含签名" prop="hasSignature">
        <el-tooltip content="含签名和含postgis 一个是一个否时,结果以是的查询为结果" placement="top">
          <i class="el-icon-question"></i>
        </el-tooltip>
        <el-select
          v-model="queryParams.params.hasSignature"
          placeholder="请选择"
          clearable
        >
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间" prop="createTime">
        <el-date-picker
          clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >搜索
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
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
          v-hasPermi="['medium:medium:add']"
        >新增
        </el-button
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
          v-hasPermi="['medium:medium:edit']"
        >修改
        </el-button
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
          v-hasPermi="['medium:medium:remove']"
        >删除
        </el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:medium:export']"
        >导出
        </el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        :columns="columns"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="mediumList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="记录ID" align="center" prop="id" />-->
      <el-table-column label="介质别名" align="center" prop="mediumName" v-if="columns[0].visible"/>
      <el-table-column label="版本类型" align="center" prop="mediumType" v-if="columns[1].visible">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.medium_version_type"
            :value="scope.row.mediumType"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="数据库版本"
        align="center"
        prop="dbVersion"
        :show-overflow-tooltip="true"
        v-if="columns[2].visible"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.medium_db_version"
            :value="scope.row.dbVersion"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="CPU版本" align="center" prop="cpuType"
        :show-overflow-tooltip="true"
        v-if="columns[3].visible"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.medium_cpu_model"
            :value="scope.row.cpuType.split(',')"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="适用系统" align="center" prop="systemAdapter"
        :show-overflow-tooltip="true"
        v-if="columns[4].visible"
      >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_file_system_mapping" :value="scope.row.systemAdapter.split(',')"/>
        </template>
      </el-table-column>
      <el-table-column
        label="打包方式" align="center" prop="packageType"
        :show-overflow-tooltip="true"
        v-if="columns[5].visible"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.medium_package_type"
            :value="scope.row.packageType"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="介质文件" align="center" prop="mediumFileName"
        :show-overflow-tooltip="true"
        v-if="columns[6].visible"
      />
      <el-table-column
        label="MD5文件"
        align="center"
        prop="mediumMd5FileName" :show-overflow-tooltip="true"
        v-if="columns[13].visible"
      />
      <el-table-column
        label="文件状态" align="center" prop="status"
        v-if="columns[7].visible"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_normal_disable"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>
      <!--      <el-table-column label="创建者ID" align="center" prop="createId" />-->
      <el-table-column
        label="创建者名" align="center" prop="createBy"
        v-if="columns[8].visible"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :show-overflow-tooltip="true"
        v-if="columns[9].visible"
      />
      <!--      <el-table-column label="更新者ID" align="center" prop="updateId" />-->
      <el-table-column
        label="更新者名" align="center"
        prop="updateBy" v-if="columns[10].visible"
      />
      <el-table-column
        label="更新时间"
        align="center"
        prop="updateTime"
        :show-overflow-tooltip="true"
        v-if="columns[11].visible"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        :show-overflow-tooltip="true"
        v-if="columns[12].visible"
      />
      <el-table-column
        min-width="120px"
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
            v-hasPermi="['medium:medium:edit']"
          >修改
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:medium:remove']"
          >删除
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row)"
            v-hasPermi="['medium:medium:download']"
          >下载
          </el-button
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

    <!-- 添加或修改介质文件信息记录对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="介质别名" prop="mediumName">
              <el-input
                v-model="form.mediumName"
                placeholder="请输入介质名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="数据库版本" prop="dbVersion">
              <el-select
                v-model="form.dbVersion"
                placeholder="请选择数据库版本"
                clearable
              >
                <el-option
                  v-for="dict in dict.type.medium_db_version"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="CPU版本" prop="cpuType">
              <el-select
                v-model="form.cpuType"
                placeholder="请选择CPU版本"
                multiple collapse-tags clearable
              >
                <el-option
                  v-for="dict in dict.type.medium_cpu_model"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="打包方式" prop="packageType">
              <el-select
                v-model="form.packageType"
                placeholder="请选择打包方式"
                clearable
              >
                <el-option
                  v-for="dict in dict.type.medium_package_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="版本类型" prop="mediumType">
              <el-select
                v-model="form.mediumType"
                placeholder="请选择版本类型"
                clearable
              >
                <el-option
                  v-for="dict in dict.type.medium_version_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="适用系统" prop="systemAdapter">
              <el-select
                v-model="form.systemAdapter"
                placeholder="请选择适用系统"
                multiple collapse-tags clearable
              >
                <el-option
                  v-for="dict in dict.type.medium_file_system_mapping"
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
            <el-form-item label="介质文件" prop="mediumFile">
              <el-upload
                ref="uploadMediumFile"
                action=""
                class="upload-demo"
                :http-request="httpMediumFileRequest"
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
            <el-form-item label="介质MD5文件" prop="mediumFileMd5">
              <el-upload
                ref="uploadMediumFileMd5"
                action=""
                class="upload-demo"
                :http-request="httpMediumFileMd5Request"
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
            <el-form-item label="上次文件名">
              <el-input disabled v-model="form.mediumFileName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上次文件名">
              <el-input disabled v-model="form.mediumMd5FileName"/>
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
  listMedium,
  getMedium,
  delMedium,
  addMedium,
  addMediumWithFile,
  updateMedium,
  updateMediumWithFile,
} from "@/api/medium/medium";
import {listProdTree} from "@/api/medium/prod";

export default {
  name: "Medium",
  dicts: [
    "sys_yes_no",
    "sys_normal_disable",
    "medium_package_type",
    "medium_version_type",
    "medium_db_version",
    "medium_cpu_model",
    "medium_file_system_mapping",
  ],
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
      // 介质文件信息记录表格数据
      mediumList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mediumType: null,
        dbVersion: null,
        cpuType: null,
        packageType: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        params: {hasSignature: null, hasPostgis: null},
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dbVersion: [
          {required: true, message: "数据库版本未选择", trigger: "blur"},
        ],
        cpuType: [
          {required: true, message: "CPU版本未选择", trigger: "blur"},
        ],
        packageType: [
          {required: true, message: "打包方式未选择", trigger: "blur"},
        ],
        mediumType: [
          {required: true, message: "版本类型未选择", trigger: "blur"},
        ],
        mediumFile: [
          {required: true, message: "介质文件未选择上传", trigger: "blur"},
        ],
      },
      listProdVersionTree: [],
      versionProps: {
        expandTrigger: 'hover',
      },
      // 列信息
      columns: [
        {key: 0, label: `介质别名`, visible: true},
        {key: 1, label: `版本类型`, visible: true},
        {key: 2, label: `数据库版本`, visible: true},
        {key: 3, label: `CPU版本`, visible: true},
        {key: 4, label: `适用系统`, visible: true},
        {key: 5, label: `打包方式`, visible: true},
        {key: 6, label: `介质文件`, visible: true},
        {key: 13, label: `MD5文件`, visible: false},
        {key: 7, label: `文件状态`, visible: true},
        {key: 8, label: `创建者名`, visible: false},
        {key: 9, label: `创建者名`, visible: false},
        {key: 10, label: `更新者名`, visible: false},
        {key: 11, label: `更新者名`, visible: false},
        {key: 12, label: `备注`, visible: false},
      ],
    };
  },
  created() {
    this.getList();
    this.listProdTree();
  },
  methods: {
    /** 查询介质文件信息记录列表 */
    getList() {
      this.loading = true;
      listMedium(this.queryParams).then((response) => {
        this.mediumList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      // 弹框关闭后 清理一下上传文件的列表
      this.$refs.uploadMediumFile.clearFiles()
      this.$refs.uploadMediumFileMd5.clearFiles()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        mediumName: null,
        mediumType: null,
        dbVersion: null,
        cpuType: null,
        packageType: null,
        mediumFile: null,
        mediumFileMd5: null,
        status: "0",
        remark: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.rules.mediumFile = [
        {required: true, message: "介质文件未选择上传", trigger: "blur"},
      ]
      this.title = "添加文件及相关配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMedium(id).then((response) => {
        this.form = response.data;
        if (this.form.cpuType) {
          this.form.cpuType = this.form.cpuType.split(",")
        }
        if (this.form.systemAdapter) {
          this.form.systemAdapter = this.form.systemAdapter.split(",")
        }
        this.open = true;
        this.rules.mediumFile = [
          {required: false, message: "介质文件未选择上传", trigger: "blur"},
        ]
        this.title = "修改文件及相关配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      // 手动调用上传，这里会调用我们自己定义的
      this.$refs.uploadMediumFileMd5.submit();
      this.$refs.uploadMediumFile.submit();
      this.loading = true;
      this.$refs["form"].validate((valid) => {
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
            updateMediumWithFile(fd).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.loading = false;
              this.open = false;
              this.getList();
            });
          } else {
            addMediumWithFile(fd).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.loading = false;
              this.open = false;
              this.getList();
            });
          }
          // 弹框关闭后 清理一下上传文件的列表
          this.$refs.uploadMediumFile.clearFiles()
          this.$refs.uploadMediumFileMd5.clearFiles()
        } else {
          this.loading = false;
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除介质文件信息记录编号为"' + ids + '"的数据项？')
        .then(function () {
          return delMedium(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {
        });
    },
    /**
     * 下载文件
     */
    handleDownload(row) {
      const fileName = row.mediumName + ".zip";
      // 修改默认时间 2min
      this.download("medium/medium/download", {...row,}, fileName, {timeout: 120000});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "medium/medium/export",
        {
          ...this.queryParams,
        },
        `medium_${new Date().getTime()}.xlsx`
      );
    },
    // 自定义的提交函数，取出文件设置进请求参数
    httpMediumFileMd5Request(param) {
      this.form.mediumFileMd5 = param.file;
    },
    httpMediumFileRequest(param) {
      this.form.mediumFile = param.file;
    },

    handleBeforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      const whiteList = ["tar", "deb", "rpm", "zip", "rar", "7z", "rar", "txt", "md5"];
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
    listProdTree(searchValue) {
      listProdTree({
        searchValue: searchValue
      }).then((response) => {
        this.listProdVersionTree = response.data;
        console.log(this.listProdVersionTree)
      });
    },
  },
};
</script>
