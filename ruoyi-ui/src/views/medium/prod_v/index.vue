<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="产品名称" prop="prodName">
        <el-input
          v-model="queryParams.prodName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品版本" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="研发部门" prop="deptId">
        <treeselect v-model="queryParams.deptId" style="width: 215px" :options="deptOptions"
                    :show-count="true" placeholder="请选择归属部门" />
      </el-form-item>
      <el-form-item label="软著登记号" prop="registrationNum">
        <el-input
          v-model="queryParams.registrationNum"
          placeholder="请输入软著登记号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本状态" prop="status">
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
          v-hasPermi="['medium:prod_v:add']"
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
          v-hasPermi="['medium:prod_v:edit']"
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
          v-hasPermi="['medium:prod_v:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['medium:prod_v:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="prod_vList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" width="75" prop="id" />
      <el-table-column label="产品名称" align="center" prop="prodName" v-if="columns[0].visible"/>
      <el-table-column label="产品版本" align="center" prop="version" v-if="columns[1].visible"/>
      <el-table-column label="产品经理" align="center" prop="prodPic" v-if="columns[2].visible"/>
      <el-table-column label="项目经理" align="center" prop="pmPic" v-if="columns[3].visible"/>
      <el-table-column label="归属部门" align="center" prop="deptName" v-if="columns[4].visible"/>
      <el-table-column label="软著登记号" align="center" prop="registrationNum" v-if="columns[5].visible"/>
      <el-table-column label="Lic工具名称" align="center" prop="licToolName" v-if="columns[6].visible"/>
      <el-table-column label="Lic工具文件" align="center" prop="licToolFileName" v-if="columns[6].visible"/>
      <el-table-column label="机器码查询工具文件" align="center" prop="macToolFileName" v-if="columns[7].visible"/>
      <el-table-column label="版本状态" align="center" prop="status" v-if="columns[8].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="是否签名" align="center" prop="signFlag" v-if="columns[9].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.signFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[10].visible"/>
      <el-table-column label="创建者" align="center" prop="createBy" v-if="columns[11].visible"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[12].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateBy" v-if="columns[13].visible"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[14].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="115" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['medium:prod_v:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['medium:prod_v:remove']"
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

    <!-- 添加或修改产品版本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="96px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="产品名称" prop="prodId">
<!--              <el-input v-model="form.prodId" placeholder="请输入版本" >-->
<!--                <el-button slot="append" icon="el-icon-search">选择</el-button>-->
<!--              </el-input>-->
              <el-select
                v-model="form.prodId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入关键词"
                :remote-method="remoteQueryProdMethod"
                :loading="loadingQueryProd">
                <el-option
                  v-for="item in optionsProds"
                  :key="item.id"
                  :label="item.prodName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品版本" prop="version">
              <el-input v-model="form.version" placeholder="请输入版本,如：V1.1.0" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="软著登记号" prop="registrationId">
<!--              <el-input v-model="form.registrationId" placeholder="请输入软登记ID" >-->
<!--                <el-button slot="append" icon="el-icon-search">选择</el-button>-->
<!--              </el-input>-->
              <el-select
                v-model="form.registrationId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入软著软件关键词"
                :remote-method="remoteQueryRegistrationMethod"
                :loading="loadingQueryRegistration">
                <el-option
                  v-for="item in optionsRegistration"
                  :key="item.id"
                  :label="item.registrationNum"
                  :value="item.id">
                  <span style="float: left">{{ item.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">登记号:{{ item.registrationNum }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="产品经理" prop="prodPic">
<!--              <el-input v-model="form.prodPic" placeholder="请输入产品经理" />-->
              <el-autocomplete
                v-model="form.prodPic"
                :fetch-suggestions="querySearchEmployeeAsync"
                placeholder="请输入内容"
                @select="handleSelectProdPic"
              ></el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目经理" prop="pmPic">
<!--              <el-input v-model="form.pmPic" placeholder="请输入项目经理" />-->
              <el-autocomplete
                v-model="form.pmPic"
                :fetch-suggestions="querySearchEmployeeAsync"
                placeholder="请输入内容"
                @select="handleSelectPmPic"
              ></el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" style="width: 215px" :options="deptOptions"
                          :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Lic工具" prop="licToolId">
<!--              <el-input v-model="form.licToolId" placeholder="请输入Lic工具" >-->
<!--                <el-button slot="append" icon="el-icon-search">选择</el-button>-->
<!--              </el-input>-->
              <el-select  @change="getLicToolInfo"
                v-model="form.licToolId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入软著软件关键词"
                :remote-method="remoteQueryLicToolMethod"
                :loading="loadingQueryLicTool">
                <el-option
                  v-for="item in optionsLicTool"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
<!--                  <span style="float: left">{{ item.name }}</span>-->
<!--                  <span style="float: right; color: #8492a6; font-size: 13px">机器码查询工具:{{ item.macFileName }}</span>-->
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机器码查询工具" prop="macToolFileName" label-width="128px">
              <el-input v-model="form.macToolFileName" readonly  placeholder="Lic入参对应的机器码工具" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="是否签名" prop="signFlag">
              <el-radio-group v-model="form.signFlag">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProd_v, getProd_v, delProd_v, addProd_v, updateProd_v } from "@/api/medium/prod_v";
import { listEmployee, deptTreeSelect } from "@/api/crm/employee";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listProd,getProd } from "@/api/medium/prod";
import { listLic_tool, getLic_tool } from "@/api/medium/lic_tool";
import { listSoft_registration, getSoft_registration } from "@/api/medium/soft_registration";
export default {
  name: "Prod_v",
  dicts: ['sys_normal_disable','sys_yes_no'],
  components: { Treeselect },
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
      // 产品版本信息表格数据
      prod_vList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prodId: null,
        version: null,
        prodPic: null,
        pmPic: null,
        deptId: null,
        registrationId: null,
        licToolId: null,
        macToolFileId: null,
        status: null,
        signFlag: null,
        createId: null,
        createBy: null,
        createTime: null,
        updateId: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        prodId: [
          { required: true, message: "产品未选择", trigger: "change" }
        ],
        version: [
          { required: true, message: "产品版本不能为空", trigger: "blur" }
        ],
        registrationId: [
          { required: true, message: "软著登记号未选择", trigger: "blur" }
        ],
        prodPic: [
          { required: true, message: "产品经理不能为空", trigger: "blur" }
        ],
        pmPic: [
          { required: true, message: "项目经理不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "研发部门未选择", trigger: "blur" }
        ],
        signFlag: [
          { required: true, message: "是否签名未选择", trigger: "blur" }
        ],
        status: [
          { required: true, message: "产品状态未选择", trigger: "blur" }
        ],
      },
      // 列信息
      columns: [
        { key: 0, label: `产品名称`, visible: true },
        { key: 1, label: `产品版本`, visible: true },
        { key: 2, label: `产品经理`, visible: true },
        { key: 3, label: `项目经理`, visible: true },
        { key: 4, label: `归属部门`, visible: true },
        { key: 5, label: `软著登记号`, visible: true },
        { key: 6, label: `Lic工具`, visible: false },
        { key: 7, label: `机器码查询工具`, visible: true },
        { key: 8, label: `版本状态`, visible: true },
        { key: 9, label: `是否签名`, visible: false },
        { key: 10, label: `备注`, visible: false },
        { key: 11, label: `创建者`, visible: false },
        { key: 12, label: `创建时间`, visible: false },
        { key: 13, label: `更新者`, visible: false },
        { key: 14, label: `更新时间`, visible: false },
      ],
      employeeOptions: [],
      // 部门树选项
      deptOptions: [],
      optionsProds:[],
      loadingQueryProd:false,
      optionsRegistration:[],
      loadingQueryRegistration:false,
      optionsLicTool:[],
      loadingQueryLicTool:false,
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询产品版本信息列表 */
    getList() {
      this.loading = true;
      listProd_v(this.queryParams).then(response => {
        this.prod_vList = response.rows;
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
        prodId: null,
        version: null,
        prodPic: null,
        pmPic: null,
        deptId: null,
        registrationId: null,
        licToolId: null,
        macToolFileId: null,
        status: null,
        signFlag: null,
        remark: null,
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
      this.title = "添加产品版本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.prepareDataForUpdate(row)
      const id = row.id || this.ids
      getProd_v(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品版本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProd_v(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProd_v(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除产品版本信息编号为"' + ids + '"的数据项？').then(function() {
        return delProd_v(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('medium/prod_v/export', {
        ...this.queryParams
      }, `prod_v_${new Date().getTime()}.xlsx`)
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    querySearchEmployeeAsync(queryString, cb){
      listEmployee({
        pageNum: 1,
        pageSize: 20,
        nickName: queryString,
      }).then(response => {
        this.employeeOptions = response.rows;
        //nickName
        this.employeeOptions.forEach(e => {
          e.value = e.nickName;
        })
        //var results =[];
        cb(this.employeeOptions);
        console.log(this.employeeOptions)
      });
    },
    handleSelectProdPic(item){
      console.log("handleSelectProdPic:",item);
    },
    handleSelectPmPic(item){
      console.log("handleSelectPmPic:",item);
    },
    remoteQueryProdMethod(query){
      if (query !== '') {
        this.loadingQueryProd = true;
        listProd({
          pageNum: 1,
          pageSize: 20,
          prodName: query,
        }).then(response => {
          this.optionsProds = response.rows;
          this.loadingQueryProd = false;
        });
      } else {
        this.optionsProds = [];
      }
    },
    remoteQueryRegistrationMethod(query){
      if (query !== '') {
        this.loadingQueryRegistration = true;
        listSoft_registration({
          pageNum: 1,
          pageSize: 20,
          name: query,
        }).then(response => {
          this.optionsRegistration = response.rows;
          this.loadingQueryRegistration = false;
        });
      } else {
        this.optionsRegistration = [];
      }
    },
    remoteQueryLicToolMethod(query){
      if (query !== '') {
        this.loadingQueryLicTool = true;
        listLic_tool({
          pageNum: 1,
          pageSize: 20,
          name: query,
          status: 0,
        }).then(response => {
          this.optionsLicTool = response.rows;
          this.loadingQueryLicTool = false;
        });
      } else {
        this.optionsLicTool = [];
      }
    },
    getLicToolInfo(selected) {
      const select = this.optionsLicTool.find(item => item.id == selected)
      this.form.macToolFileName = select.macFileName;
      this.form.macToolFileId = select.macFileId;
    },
    prepareDataForUpdate(item){
      const registrationNum =item.registrationId
      this.optionsRegistration = [];
      if(registrationNum){
        getSoft_registration(registrationNum).then(response => {
          this.optionsRegistration.push(response.data);
        });
      }
      const prodId = item.prodId;
      this.optionsProds=[];
      if(prodId){
        getProd(prodId).then(response => {
          this.optionsProds.push(response.data);
        });
      }

      const licToolId = item.licToolId;
      this.optionsLicTool =[];
      if(licToolId){
        getLic_tool(licToolId).then(response => {
          this.optionsLicTool.push(response.data);
        });
      }
    },
  }
};
</script>
