<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入文件名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="发文字号" prop="fileNum">
        <el-input v-model="queryParams.fileNum" placeholder="请输入发文字号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker v-model="daterangeReleaseDate" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="发文机构" prop="releaseOffice">
        <el-input v-model="queryParams.releaseOffice" placeholder="请输入发文机构" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="发布年分" prop="releaseYear">
        <el-input v-model="queryParams.releaseYear" placeholder="请输入发布年分" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="文件状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择文件可用状态" clearable>
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
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
                   v-hasPermi="['crm:policyfile:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:policyfile:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:policyfile:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:policyfile:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="policyfileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="文件名" align="center" prop="name" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="发文字号" align="center" prop="fileNum" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="文件路径" align="center" prop="filePath" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="发布时间" align="center" prop="releaseDate" width="180" v-if="columns[3].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.releaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发文机构" align="center" prop="releaseOffice" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="发布年分" align="center" prop="releaseYear" v-if="columns[5].visible"/>
      <el-table-column label="适用省份" align="center" prop="province" v-if="columns[6].visible"/>
      <el-table-column label="适用市" align="center" prop="city" v-if="columns[7].visible"/>
      <el-table-column label="备注说明" align="center" prop="remark" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[9].visible"/>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[11].visible"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="负责部门" align="center" prop="deptId" v-if="columns[13].visible"/>
      <el-table-column label="负责者" align="center" prop="ownerId" v-if="columns[14].visible"/>
      <el-table-column label="文件状态" align="center" prop="status" v-if="columns[15].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:policyfile:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-download" @click="handleDownload(scope.row)"
                     v-hasPermi="['crm:policyfile:download']">下载
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['crm:policyfile:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改政策文件对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="政策文件名" prop="name">
              <el-input v-model="form.name" placeholder="政策文件全名,如:a.txt,默认附件名字"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发文字号" prop="fileNum">
              <el-input v-model="form.fileNum" placeholder="输入发文字号,如:鲁政发(2022)1号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发布日期" prop="releaseDate">
              <el-date-picker
                v-model="form.releaseDate"
                type="date" value-format="yyyy-MM-dd"
                placeholder="选择请选择发布日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布年份" prop="releaseYear">
              <el-input v-model="form.releaseYear" placeholder="请输入文件发布年分，如:2023"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="适用省份" prop="province">
              <el-input v-model="form.province" placeholder="请输入适用省份,如:山东省"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="适用地市" prop="city">
              <el-input v-model="form.city" placeholder="请输入适用地市,如:济南市"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发文机构" prop="releaseOffice">
              <el-input v-model="form.releaseOffice" placeholder="请输入文件发文机构"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文件状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable"
                          :key="dict.value" :label="dict.value">{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="上传文件" prop="policyFileFile">
              <el-upload
                ref="uploadFile"
                action=""
                class="upload-demo"
                :http-request="httpFileRequest"
                :multiple="false"
                :limit="1"
                :auto-upload="true"
                :before-upload="handleBeforeUpload"
                accept=".pdf, .doc, .docx"
                :on-exceed="handleExceed">
                <el-button size="small" type="primary">选择文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传1个pdf/doc/docx类型文件，且不超过100M</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :rows="2" v-model="form.remark"></el-input>
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
  listPolicyfile,
  getPolicyfile,
  delPolicyfile,
  addPolicyfile,
  addPolicyfileWithFile,
  updatePolicyfileWithFile,
  updatePolicyfile
} from "@/api/crm/policyfile";

export default {
  name: "Policyfile",
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
      // 政策文件表格数据
      policyfileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 文件可用状态时间范围
      daterangeReleaseDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        fileNum: null,
        releaseDate: null,
        releaseOffice: null,
        releaseYear: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fileNum: [
          {required: true, message: "发文字号不能为空", trigger: "blur"}
        ],
        releaseDate: [
          {required: true, message: "发布日期不能为空", trigger: "blur"}
        ],
        releaseYear: [
          {required: true, message: "发布年份不能为空", trigger: "blur"},
          {type: "number",message: "只能输入数字",trigger: "blur"}
        ],
        province: [
          {required: true, message: "适用省份不能为空", trigger: "blur"}
        ],
        releaseOffice: [
          {required: true, message: "发文机构不能为空", trigger: "blur"}
        ],
        policyFileFile: [
          {required: true, message: "文件未选择上传", trigger: "blur"},
        ],
      },
      // 列信息
      columns: [
        {key: 0, label: `文件名`, visible: true},
        {key: 1, label: `发文字号`, visible: true},
        {key: 2, label: `文件路径`, visible: true},
        {key: 3, label: `发布时间`, visible: true},
        {key: 4, label: `发文机构`, visible: false},
        {key: 5, label: `发布年分`, visible: false},
        {key: 6, label: `适用省份`, visible: false},
        {key: 7, label: `适用市`, visible: false},
        {key: 8, label: `备注`, visible: false},
        {key: 9, label: `创建人`, visible: false},
        {key: 10, label: `创建时间`, visible: false},
        {key: 11, label: `更新人`, visible: false},
        {key: 12, label: `更新时间`, visible: false},
        {key: 13, label: `负责部门`, visible: false},
        {key: 14, label: `负责人`, visible: false},
        {key: 15, label: `文件状态`, visible: false},
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询政策文件列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeReleaseDate && '' != this.daterangeReleaseDate) {
        this.queryParams.params["beginReleaseDate"] = this.daterangeReleaseDate[0];
        this.queryParams.params["endReleaseDate"] = this.daterangeReleaseDate[1];
      }
      listPolicyfile(this.queryParams).then(response => {
        this.policyfileList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      // 弹框关闭后 清理一下上传文件的列表
      this.$refs.uploadFile.clearFiles()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        fileNum: null,
        filePath: null,
        policyFileFile: null,
        releaseDate: null,
        releaseOffice: null,
        releaseYear: null,
        province: null,
        city: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deptId: null,
        ownerId: null,
        status: "0"
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
      this.daterangeReleaseDate = [];
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
      this.rules.policyFileFile = [
        {required: true, message: "介质文件未选择上传", trigger: "blur"},
      ]
      this.title = "添加政策文件";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPolicyfile(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.rules.policyFileFile = [
          {required: false, message: "介质文件未选择上传", trigger: "blur"},
        ]
        this.title = "修改政策文件";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        this.loading = true;
        this.$refs.uploadFile.submit();
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
            updatePolicyfileWithFile(fd).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPolicyfileWithFile(fd).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
          this.$refs.uploadFile.clearFiles();
        } else {
          this.loading = false;
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除政策文件编号为"' + ids + '"的数据项？').then(function () {
        return delPolicyfile(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/policyfile/export', {
        ...this.queryParams
      }, `policyfile_${new Date().getTime()}.xlsx`)
    },
    handleBeforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      const whiteList = ["pdf", "docx", "doc", "zip", "rar", "7z"];

      if (whiteList.indexOf(fileSuffix) === -1) {
        this.$message.error("上传文件只能是特定格式");
        return false;
      }
      const isLtM = file.size / 1024 / 1024 < 100;
      if (!isLtM) {
        this.$message.error("上传文件大小不能超过 100MB");
        return false;
      }
    },
    handleExceed(file, fileList) {
      if (fileList.length > 0) {
        this.$message.error("上传文件大小不能超过1个");
        return false;
      }
    },
    httpFileRequest(param) {
      this.form.policyFileFile = param.file;
    },
    /**
     * 下载文件
     */
    handleDownload(row) {
      const fileName = row.name + ".zip";
      // 修改默认时间 2min
      this.download("crm/policyfile/download", {...row,}, fileName, {timeout: 120000});
    },
  }
};
</script>
