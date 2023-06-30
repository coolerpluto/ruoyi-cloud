<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="prodName">
        <el-input
          v-model="queryParams.prodName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品简称" prop="prodSname">
        <el-input
          v-model="queryParams.prodSname"
          placeholder="请输入简称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品性质" prop="property">
        <el-select v-model="queryParams.property" placeholder="请选择性质" clearable>
          <el-option
            v-for="dict in dict.type.medium_prod_property"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.medium_prod_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['medium:prod:add']"
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
          v-hasPermi="['medium:prod:edit']"
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
          v-hasPermi="['medium:prod:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:prod:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="prodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" width="150" prop="id" />
      <el-table-column label="产品名称" align="center" prop="prodName" />
      <el-table-column label="产品简称" align="center" prop="prodSname" />
      <el-table-column label="产品性质" align="center" prop="property" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_prod_property" :value="scope.row.property"/>
        </template>
      </el-table-column>
      <el-table-column label="产品类型" align="center" prop="type" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.medium_prod_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="产品状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="更新人" align="center" prop="updateBy" />
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medium:prod:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:prod:remove']"
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

    <!-- 添加或修改产品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="prodName">
              <el-input v-model="form.prodName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品简称" prop="prodSname">
              <el-input v-model="form.prodSname" placeholder="请输入简称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="产品性质" prop="property">
              <el-radio-group v-model="form.property">
                <el-radio
                  v-for="dict in dict.type.medium_prod_property"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品类型" prop="type">
              <el-radio-group v-model="form.type">
                <el-radio
                  v-for="dict in dict.type.medium_prod_type"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" resize="none" :rows="3" placeholder="请输入内容" />
        </el-form-item>
        <!-- 下面是在编写主表时 同时编辑子表 暂时不提供支持 -->
<!--        <el-divider content-position="center">-->
<!--          产品版本信息信息<span style="color: #ff0000; font-size: 13px">(此处添加的版本亦可以再在版本维护处进行二次维护)</span>-->
<!--        </el-divider>-->
<!--        <el-row :gutter="10" class="mb8">-->
<!--          <el-col :span="1.5">-->
<!--            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddTProdVersionInfo">添加</el-button>-->
<!--          </el-col>-->
<!--          <el-col :span="1.5">-->
<!--            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteTProdVersionInfo">删除</el-button>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <el-table :data="tProdVersionInfoList" :row-class-name="rowTProdVersionInfoIndex" @selection-change="handleTProdVersionInfoSelectionChange" ref="tProdVersionInfo">-->
<!--          <el-table-column type="selection" width="50" align="center" />-->
<!--          <el-table-column label="序号" align="center" prop="index" width="50"/>-->
<!--          <el-table-column label="产品版本" prop="version" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.version" placeholder="请输入版本" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="产品经理" prop="prodPic" width="150">-->
<!--            <template slot-scope="scope">-->
<!--&lt;!&ndash;              <el-input v-model="scope.row.prodPic" placeholder="请输入产品经理" />&ndash;&gt;-->
<!--              <el-autocomplete-->
<!--                v-model="scope.row.prodPic"-->
<!--                :fetch-suggestions="querySearchEmployeeAsync"-->
<!--                placeholder="请输入产品经理名"-->
<!--                @select="handleSelectProdPic"-->
<!--              ></el-autocomplete>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="项目经理" prop="pmPic" width="150">-->
<!--            <template slot-scope="scope">-->
<!--&lt;!&ndash;              <el-input v-model="scope.row.pmPic" placeholder="请输入项目经理" />&ndash;&gt;-->
<!--              <el-autocomplete-->
<!--                v-model="scope.row.pmPic"-->
<!--                :fetch-suggestions="querySearchEmployeeAsync"-->
<!--                placeholder="请输入项目经理名"-->
<!--                @select="handleSelectPmPic"-->
<!--              ></el-autocomplete>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="归属部门" prop="deptId" width="150">-->
<!--            <template slot-scope="scope">-->
<!--&lt;!&ndash;              <el-input v-model="scope.row.deptId" placeholder="请输入归属部门" />&ndash;&gt;-->
<!--              <treeselect v-model="scope.row.deptId" style="width: 215px"-->
<!--                          :options="deptOptions"-->
<!--                          :show-count="true" placeholder="请选择归属部门" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="软登记号" prop="registrationId" width="150">-->
<!--            <template slot-scope="scope">-->
<!--&lt;!&ndash;              <el-input v-model="scope.row.registrationId" placeholder="请输入软登记ID" />&ndash;&gt;-->
<!--              <el-select-->
<!--                v-model="value"-->
<!--                filterable-->
<!--                remote-->
<!--                reserve-keyword-->
<!--                placeholder="请输入关键词"-->
<!--                :remote-method="remoteMethodRegistration"-->
<!--                :loading="loading">-->
<!--                <el-option-->
<!--                  v-for="item in options"-->
<!--                  :key="item.value"-->
<!--                  :label="item.label"-->
<!--                  :value="item.value">-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="Lic工具" prop="licToolId" width="150">-->
<!--            <template slot-scope="scope">-->
<!--&lt;!&ndash;              <el-input v-model="scope.row.licToolId" placeholder="请输入Lic工具ID" />&ndash;&gt;-->
<!--              <el-select-->
<!--                v-model="value"-->
<!--                filterable-->
<!--                remote-->
<!--                reserve-keyword-->
<!--                placeholder="请输入关键词"-->
<!--                :remote-method="remoteMethodLic"-->
<!--                :loading="loading">-->
<!--                <el-option-->
<!--                  v-for="item in options"-->
<!--                  :key="item.value"-->
<!--                  :label="item.label"-->
<!--                  :value="item.value">-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="机器码工具" prop="macToolFileId" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.macToolFileId" placeholder="请输入机器码工具ID" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="版本状态" prop="status" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-select v-model="scope.row.status" placeholder="请选择状态">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_normal_disable"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="是否签名" prop="signFlag" width="150">-->
<!--            <template slot-scope="scope">-->
<!--&lt;!&ndash;              <el-input v-model="scope.row.signFlag" placeholder="请输入是否签名" />&ndash;&gt;-->
<!--              <el-select v-model="scope.row.signFlag" placeholder="请选择是否签名">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_yes_no"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--        </el-table>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProd, getProd, delProd, addProd, updateProd } from "@/api/medium/prod";
import { listEmployee, deptTreeSelect } from "@/api/crm/employee";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Prod",
  dicts: ['sys_normal_disable','sys_yes_no','medium_prod_type','medium_prod_property'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedTProdVersionInfo: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品信息表格数据
      prodList: [],
      // 产品版本信息表格数据
      tProdVersionInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prodName: null,
        prodSname: null,
        property: null,
        type: null,
        status: null,
        createId: null,
        updateId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        prodName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        property: [
          { required: true, message: "产品性质未选择", trigger: "blur" }
        ],
        type: [
          { required: true, message: "产品类型未选择", trigger: "blur" }
        ],
        status: [
          { required: true, message: "产品状态未选择", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品信息列表 */
    getList() {
      this.loading = true;
      listProd(this.queryParams).then(response => {
        this.prodList = response.rows;
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
        prodName: null,
        prodSname: null,
        property: null,
        type: null,
        status: null,
        remark: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
        updateTime: null
      };
      this.tProdVersionInfoList = [];
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
      this.title = "添加产品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProd(id).then(response => {
        this.form = response.data;
        //下面是在编写主表时 同时编辑子表 暂时不提供支持
        //this.tProdVersionInfoList = response.data.tProdVersionInfoList;
        this.open = true;
        this.title = "修改产品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 下面是在编写主表时 同时编辑子表 暂时不提供支持
          // this.form.tProdVersionInfoList = this.tProdVersionInfoList;
          if (this.form.id != null) {
            updateProd(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProd(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除产品信息编号为"' + ids + '"的数据项？').then(function() {
        return delProd(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 产品版本信息序号 */
    rowTProdVersionInfoIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 产品版本信息添加按钮操作 */
    handleAddTProdVersionInfo() {
      let obj = {};
      obj.version = "";
      obj.prodPic = "";
      obj.pmPic = "";
      obj.deptId = "";
      obj.registrationId = "";
      obj.licToolId = "";
      obj.macToolFileId = "";
      obj.status = "";
      obj.signFlag = "";
      obj.remark = "";
      obj.createId = "";
      obj.createBy = "";
      obj.createTime = "";
      obj.updateId = "";
      obj.updateBy = "";
      obj.updateTime = "";
      this.tProdVersionInfoList.push(obj);
    },
    /** 产品版本信息删除按钮操作 */
    handleDeleteTProdVersionInfo() {
      if (this.checkedTProdVersionInfo.length == 0) {
        this.$modal.msgError("请先选择要删除的产品版本信息数据");
      } else {
        const tProdVersionInfoList = this.tProdVersionInfoList;
        const checkedTProdVersionInfo = this.checkedTProdVersionInfo;
        this.tProdVersionInfoList = tProdVersionInfoList.filter(function(item) {
          return checkedTProdVersionInfo.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleTProdVersionInfoSelectionChange(selection) {
      this.checkedTProdVersionInfo = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/prod/export', {
        ...this.queryParams
      }, `prod_${new Date().getTime()}.xlsx`)
    },
    /** 同步查询产品经理和项目经理 */
    querySearchEmployeeAsync(queryString, cb){

    },
    /** 选择产品经理 */
    handleSelectProdPic(item){

    },
    /** 选择项目经理 */
    handleSelectPmPic(item){

    },
    /** 同步查询Lic工具 */
    remoteMethodLic(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.options = this.list.filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.options = [];
      }
    },
    /** 同步查询软著信息 */
    remoteMethodRegistration(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.options = this.list.filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.options = [];
      }
    },
  }
};
</script>
