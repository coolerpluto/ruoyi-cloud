<template>
  <div class="app-container">
    <div id="licArea" style="background-color: whitesmoke;">
      <el-form :model="licQueryParams" ref="licQueryForm" size="mini" :inline="true" v-show="licShowSearch"
               label-width="85px">
        <el-form-item label="商机编号" prop="opportunityNum">
          <el-input v-model="licQueryParams.opportunityNum" placeholder="请输入编号" clearable style="width: 220px"
                    @keyup.enter.native="licHandleQuery"/>
        </el-form-item>
        <el-form-item label="最终用户" prop="customerName">
          <el-input v-model="licQueryParams.customerName" placeholder="请输入用户名称" clearable style="width: 220px"
                    @keyup.enter.native="licHandleQuery"/>
        </el-form-item>
        <el-form-item label="申请人账户" prop="createBy">
          <el-input v-model="licQueryParams.createBy" placeholder="请输入申请人登录账户名" clearable style="width: 220px"
                    @keyup.enter.native="licHandleQuery"/>
        </el-form-item>
        <el-form-item label="申请时间">
          <el-date-picker v-model="licDateRange" style="width: 220px" value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetimerange"
                          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                          :default-time="['00:00:00', '23:59:59']"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="licHandleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="licResetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <span style="margin-left: 15px;">License下载模块</span>
        <right-toolbar :showSearch.sync="licShowSearch" @queryTable="licGetList" :columns="licColumns"></right-toolbar>
      </el-row>
      <el-table v-loading="licLoading" :data="licenseList" @selection-change="licHandleSelectionChange"
                @row-click="licHandleRowClick">
        <el-table-column label="Lic序列号" align="center" prop="serial" :show-overflow-tooltip="true"
                         v-if="licColumns[0].visible"/>
        <el-table-column label="申请时间" align="center" prop="applyTime" width="180" v-if="licColumns[1].visible">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="商机编号" align="center" prop="opportunityNum" v-if="licColumns[2].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="授权方式" align="center" prop="authType" v-if="licColumns[3].visible"/>
        <el-table-column label="使用客户" align="center" prop="customerName" v-if="licColumns[4].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="使用用途" align="center" prop="purpose" v-if="licColumns[5].visible"/>
        <el-table-column label="过期时间" align="center" prop="expireTime" :show-overflow-tooltip="true"
                         v-if="licColumns[6].visible"/>
        <el-table-column label="产品类型" align="center" prop="prodType" v-if="licColumns[7].visible"/>
        <el-table-column label="数据库版本" align="center" prop="dbVersion" :show-overflow-tooltip="true"
                         v-if="licColumns[8].visible"/>
        <el-table-column label="下载连接" align="center" prop="serverUrl" :show-overflow-tooltip="true"
                         v-if="licColumns[9].visible"/>
        <el-table-column label="其他参数" align="center" prop="otherParam" v-if="licColumns[10].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="文件状态" align="center" prop="status" v-if="licColumns[11].visible"/>
        <el-table-column label="申请人账户" align="center" prop="createBy" v-if="licColumns[12].visible"/>
        <el-table-column label="创建时间" align="center" prop="createTime" v-if="licColumns[13].visible"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="备注" align="center" prop="remark" v-if="licColumns[14].visible"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-download" @click="licHandleDownload(scope.row)"
                       v-hasPermi="['medium:license:download']">下载
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="lictTotal > 0" :total="lictTotal" :page.sync="licQueryParams.pageNum"
                  :limit.sync="licQueryParams.pageSize" @pagination="licGetList"/>
    </div>
    <div id="mediumDescArea" style="text-align: center;">
      <el-collapse v-model="activeNames">
        <el-collapse-item title="CPU-操作系统-介质 对应关系" name="showMediumDesc">
          <!-- <el-image :src="require('@/assets/images/medium_system_cpu_mapping.png')"-->
          <el-image :src="'http://192.168.110.204:9000/public/images/medium/medium_system_cpu_mapping.png'"
                    style="width: auto;max-width: 100%;">
            <div slot="placeholder" class="image-slot">加载中<span class="dot">...</span></div>
          </el-image>
        </el-collapse-item>
      </el-collapse>
    </div>
    <div id="mediumArea" style="background-color: whitesmoke;">
      <el-form :model="mediumQueryParams" ref="mediumQueryForm" size="mini" :inline="true" v-show="mediumShowSearch"
               label-width="78px">
        <el-form-item label="版本类型" prop="mediumType">
          <el-select v-model="mediumQueryParams.mediumType" placeholder="请选择版本类型" clearable>
            <el-option v-for="dict in dict.type.medium_version_type" :key="dict.value" :label="dict.label"
                       :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="数据库" prop="dbVersion">
          <el-select v-model="mediumQueryParams.dbVersion" placeholder="请选择数据库版本" clearable>
            <el-option v-for="dict in dict.type.medium_db_version" :key="dict.value" :label="dict.label"
                       :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="CPU版" prop="cpuType">
          <el-select v-model="mediumQueryParams.cpuType" placeholder="请选择CPU版本" clearable>
            <el-option v-for="dict in dict.type.medium_cpu_model" :key="dict.value" :label="dict.label"
                       :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="打包方式" prop="packageType">
          <el-select v-model="mediumQueryParams.packageType" placeholder="请选择打包方式" clearable>
            <el-option v-for="dict in dict.type.medium_package_type" :key="dict.value" :label="dict.label"
                       :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="含postgis" prop="hasPostgis">
          <el-tooltip content="含签名和含postgis 一个是一个否时,结果以是的查询为结果" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <el-select v-model="mediumQueryParams.params.hasPostgis" placeholder="请选择" clearable>
            <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="含签名" prop="hasSignature">
          <el-tooltip content="含签名和含postgis 一个是一个否时,结果以是的查询为结果" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <el-select v-model="mediumQueryParams.params.hasSignature" placeholder="请选择" clearable>
            <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="上传时间" prop="createTime">
          <el-date-picker clearable v-model="mediumQueryParams.createTime" type="date" value-format="yyyy-MM-dd"
                          placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="mediumHandleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="mediumResetQuery">重置</el-button>
        </el-form-item>
        <el-form-item prop="licOpportunityNum" label-width="180px">
          <span slot="label">
            <span>关联的商机编号</span>
            <span style="color: red">(下载必填)</span>
          </span>
          <el-input v-model="licOpportunityNum" placeholder="可以点击Lic条目自动填入" style="width: 210px" show-word-limit
                    maxlength="16"/>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <span style="margin-left: 15px;">介质下载模块</span>
        <right-toolbar :showSearch.sync="mediumShowSearch" @queryTable="mediumGetList"
                       :columns="mediumColumns"></right-toolbar>
      </el-row>
      <el-table v-loading="mediumLoading" :data="mediumList" @selection-change="mediumHandleSelectionChange">
        <el-table-column label="介质别名" align="center" prop="mediumName" v-if="mediumColumns[0].visible"/>
        <el-table-column label="版本类型" align="center" prop="mediumType" v-if="mediumColumns[1].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.medium_version_type" :value="scope.row.mediumType"/>
          </template>
        </el-table-column>
        <el-table-column label="数据库版本"
                         align="center" prop="dbVersion" :show-overflow-tooltip="true" v-if="mediumColumns[2].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.medium_db_version" :value="scope.row.dbVersion"/>
          </template>
        </el-table-column>
        <el-table-column label="CPU版本" align="center" prop="cpuType" v-if="mediumColumns[3].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.medium_cpu_model" :value="scope.row.cpuType"/>
          </template>
        </el-table-column>
        <el-table-column label="打包方式" align="center" prop="packageType" :show-overflow-tooltip="true"
                         v-if="mediumColumns[4].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.medium_package_type" :value="scope.row.packageType"/>
          </template>
        </el-table-column>
        <el-table-column label="介质文件名" align="center" prop="mediumFileName" :show-overflow-tooltip="true"
                         v-if="mediumColumns[5].visible"/>
        <el-table-column label="MD5文件名" align="center" prop="mediumMd5FileName" :show-overflow-tooltip="true"
                         v-if="mediumColumns[6].visible"/>
        <el-table-column label="文件状态" align="center" prop="status" v-if="mediumColumns[7].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建者名" align="center" prop="createBy" v-if="mediumColumns[8].visible"/>
        <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true"
                         v-if="mediumColumns[9].visible"/>
        <el-table-column label="更新者名" align="center" prop="updateBy" v-if="mediumColumns[10].visible"/>
        <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true"
                         v-if="mediumColumns[11].visible"/>
        <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"
                         v-if="mediumColumns[12].visible"/>
        <el-table-column width="130px" label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-download" @click="mediumHandleDownload(scope.row)"
                       v-hasPermi="['medium:medium:download']">下载
            </el-button>
            <el-button size="mini" type="text" icon="el-icon-download" @click="securityDownloadPopUp(scope.row)"
                       v-hasPermi="['medium:mediumsecurity:download']">下载附件
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="mediumTotal > 0" :total="mediumTotal" :page.sync="mediumQueryParams.pageNum"
                  :limit.sync="mediumQueryParams.pageSize" @pagination="mediumGetList"/>
    </div>
    <el-dialog :title="securityTitle" :visible.sync="securityOpen" width="1000px" append-to-body>
      <el-form :model="securityQueryParams" ref="securityQueryForm" size="small" :inline="true"
               v-show="securityShowSearch" label-width="68px">
        <el-form-item label="文件类型" prop="fileType">
          <el-select v-model="securityQueryParams.fileType" placeholder="请选择文件类型" clearable>
            <el-option v-for="dict in dict.type.medium_file_type" :key="dict.value" :label="dict.label"
                       :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker clearable v-model="securityQueryParams.createTime" type="date" value-format="yyyy-MM-dd"
                          placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="securityHandleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="securityResetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="securityShowSearch" @queryTable="securityGetList"
                       :columns="securityColumns"></right-toolbar>
      </el-row>

      <el-table v-loading="securityLoading" :data="securityList" @selection-change="securityHandleSelectionChange">
        <el-table-column label="别名名称" align="center" prop="securityName" :show-overflow-tooltip="true" width="180px"
                         v-if="securityColumns[0].visible"/>
        <el-table-column label="文件类型" align="center" prop="fileType" width="80px" v-if="securityColumns[1].visible">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.medium_file_type" :value="scope.row.fileType"/>
          </template>
        </el-table-column>

        <el-table-column label="文件名" align="center" prop="fileName" :show-overflow-tooltip="true"
                         v-if="securityColumns[2].visible"/>
        <el-table-column label="md5名称" align="center" prop="md5FileName" :show-overflow-tooltip="true"
                         v-if="securityColumns[3].visible"/>

        <el-table-column label="对应介质" align="center" prop="mediumName" :show-overflow-tooltip="true"
                         v-if="securityColumns[4].visible"/>
        <el-table-column label="提交人" align="center" prop="createBy" :show-overflow-tooltip="true" width="80px"
                         v-if="securityColumns[5].visible"/>
        <el-table-column label="提交时间" align="center" prop="createTime" :show-overflow-tooltip="true" width="160px"
                         v-if="securityColumns[6].visible"/>
        <el-table-column label="更新人" align="center" prop="updateBy" :show-overflow-tooltip="true" width="80px"
                         v-if="securityColumns[7].visible"/>
        <el-table-column label="更新时间" align="center" prop="updateTime" :show-overflow-tooltip="true" width="160px"
                         v-if="securityColumns[8].visible"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="50px">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-download" @click="securityHandleDownload(scope.row)"
                       v-hasPermi="['medium:mediumsecurity:download']">下载
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="securityTotal>0" :total="securityTotal" :page.sync="securityQueryParams.pageNum"
                  :pageSizes="[3,5,8]" :limit.sync="securityQueryParams.pageSize" @pagination="securityGetList"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="securityCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listLicense} from "@/api/medium/license";
import {listMedium,} from "@/api/medium/medium";
import {listMediumsecurity} from "@/api/medium/mediumsecurity";

export default {
  name: "OnlyDownload",
  dicts: [
    "sys_yes_no",
    "sys_normal_disable",
    'medium_file_type',
    "medium_package_type",
    "medium_version_type",
    "medium_db_version",
    "medium_cpu_model",
  ],
  data() {
    return {
      activeNames: [],// 若是默认不展开就置空["showMediumDesc"]
      // 点击的行的 lic opportunityNum
      licOpportunityNum: undefined,

      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      //lic 参数
      // 遮罩层
      licLoading: false,
      // 选中文件id数组
      licIds: [],
      // 显示搜索条件
      licShowSearch: true,
      // 列信息
      licColumns: [
        {key: 0, label: `Lic序列号`, visible: true},
        {key: 1, label: `申请时间`, visible: true},
        {key: 2, label: `商机编号`, visible: true},
        {key: 3, label: `授权方式`, visible: true},
        {key: 4, label: `使用客户`, visible: true},
        {key: 5, label: `使用用途`, visible: false},
        {key: 6, label: `过期时间`, visible: false},
        {key: 7, label: `产品类型`, visible: false},
        {key: 8, label: `数据库版本`, visible: false},
        {key: 9, label: `下载连接`, visible: false},
        {key: 10, label: `其他参数`, visible: false},
        {key: 11, label: `文件状态`, visible: false},
        {key: 12, label: `申请人账户`, visible: false},
        {key: 13, label: `创建时间`, visible: false},
        {key: 14, label: `备注`, visible: false},
      ],
      // 总条数
      lictTotal: 0,
      // 表格数据
      licenseList: [],
      // 日期范围
      licDateRange: [],
      // 查询参数
      licQueryParams: {
        pageNum: 1,
        pageSize: 10,
        status: 0,
        opportunityNum: undefined,
        customerName: undefined,
        createBy: undefined,
      },

      //介质参数
      // 遮罩层
      mediumLoading: false,
      // 选中文件id数组
      mediumIds: [],
      // 显示搜索条件
      mediumShowSearch: true,
      mediumColumns: [
        {key: 0, label: `介质别名`, visible: true},
        {key: 1, label: `版本类型`, visible: true},
        {key: 2, label: `数据库版本`, visible: true},
        {key: 3, label: `CPU版本`, visible: true},
        {key: 4, label: `打包方式`, visible: true},
        {key: 5, label: `介质文件名`, visible: false},
        {key: 6, label: `MD5文件名`, visible: false},
        {key: 7, label: `文件状态`, visible: false},
        {key: 8, label: `创建者名`, visible: false},
        {key: 9, label: `创建时间`, visible: false},
        {key: 10, label: `更新者名`, visible: false},
        {key: 11, label: `更新时间`, visible: false},
        {key: 12, label: `备注`, visible: false},
        {key: 13, label: `创建时间`, visible: false},
        {key: 14, label: `备注`, visible: false},
      ],
      // 总条数
      mediumTotal: 0,
      // 介质文件信息记录表格数据
      mediumList: [],
      // 日期范围
      mediumDateRange: [],
      // 查询参数
      mediumQueryParams: {
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
        params:{hasSignature:null,hasPostgis:null},
      },

      //介质附件参数
      // 弹出层标题
      securityTitle: "",
      // 弹出框查询时对应的介质ID
      securityMediumId: "",
      // 是否显示弹出层
      securityOpen: false,
      // 遮罩层
      securityLoading: false,
      // 选中文件id数组
      securityIds: [],
      // 显示搜索条件
      securityShowSearch: true,
      securityColumns: [
        {key: 0, label: `别名名称`, visible: true},
        {key: 1, label: `文件类型`, visible: true},
        {key: 2, label: `文件名`, visible: true},
        {key: 3, label: `md5名称`, visible: true},
        {key: 4, label: `对应介质`, visible: false},
        {key: 5, label: `提交人`, visible: false},
        {key: 6, label: `提交时间`, visible: false},
        {key: 7, label: `更新人`, visible: false},
        {key: 8, label: `更新时间`, visible: false},
      ],
      // 总条数
      securityTotal: 0,
      // 介质文件信息记录表格数据
      securityList: [],
      // 日期范围
      securityDateRange: [],
      // 查询参数
      securityQueryParams: {
        pageNum: 1,
        pageSize: 3,
        mediumId: null,
        status: 0,
        fileType: null,
        createBy: null,
        createTime: null,
      },
    };
  },
  created() {
    this.licGetList();
  },
  methods: {
    /** 查询lic列表 */
    licGetList() {
      this.licLoading = true;
      listLicense(this.addDateRange(this.licQueryParams, this.licDateRange, 'ApplyTime')).then(
        (response) => {
          this.licenseList = response.rows;
          this.lictTotal = response.total;
          this.licLoading = false;
        }
      );
    },
    /** 查询介质文件信息记录列表 */
    mediumGetList() {
      this.mediumLoading = true;
      listMedium(this.mediumQueryParams).then((response) => {
        this.mediumList = response.rows;
        this.mediumTotal = response.total;
        this.mediumLoading = false;
      });
    },
    /** 查询介质安全文件记录列表 */
    securityGetList() {
      this.securityLoading = true;
      this.securityQueryParams.mediumId = this.securityMediumId;
      listMediumsecurity(this.securityQueryParams).then(response => {
        this.securityList = response.rows;
        this.securityTotal = response.total;
        this.securityLoading = false;
      });
    },
    /** 搜索按钮操作 */
    securityHandleQuery() {
      this.securityQueryParams.pageNum = 1;
      this.securityGetList();
    },
    /** 重置按钮操作 */
    securityResetQuery() {
      this.securityDateRange = [];
      this.resetForm("securityQueryForm");
      //this.securityHandleQuery();
    },
    /** 搜索按钮操作 */
    licHandleQuery() {
      this.licQueryParams.pageNum = 1;
      this.licGetList();
    },
    /** 重置查询条件按钮操作 */
    licResetQuery() {
      this.licDateRange = [];
      this.resetForm("licQueryForm");
      //this.licHandleQuery();
    },
    /** 搜索按钮操作 */
    mediumHandleQuery() {
      this.mediumQueryParams.pageNum = 1;
      this.mediumGetList();
    },
    /** 重置查询条件按钮操作 */
    mediumResetQuery() {
      this.mediumDateRange = [];
      this.resetForm("mediumQueryForm");
      //this.mediumHandleQuery();
    },
    // 多选框选中数据
    licHandleSelectionChange(selection) {
      this.licIds = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 多选框选中数据
    mediumHandleSelectionChange(selection) {
      this.mediumIds = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 多选框选中数据
    securityHandleSelectionChange(selection) {
      this.securityIds = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** row:点击的那一行, column:点击的那一列, event:触发的事件 */
    licHandleRowClick(row, column, event) {
      this.licOpportunityNum = row.opportunityNum;
    },
    /** 下载按钮操作 */
    licHandleDownload(row) {
      let fileName = row.serial + ".zip"
      this.download("medium/license/download", {id: row.id}, fileName);
    },
    /**
     * 下载文件
     */
    mediumHandleDownload(row) {
      if (!this.licOpportunityNum) {
        this.$modal.alertError("您还未输入关联的商机编号,可以手动数据输入或点击Lic条目后自动填入,之后再进行操作");
        return;
      }
      row.params = {};
      row.params.opportunityNum = this.licOpportunityNum;
      const fileName = row.mediumName + ".zip";
      // 修改默认时间 2min
      this.download("medium/medium/download", {...row,}, fileName, {timeout: 120000});
    },
    securityDownloadPopUp(mediumRow) {
      if (!this.licOpportunityNum) {
        this.$modal.alertError("您还未输入关联的商机编号,可以手动数据输入或点击Lic条目后自动填入,之后再进行操作");
        return;
      }
      this.resetForm("securityQueryForm");
      this.securityOpen = true;
      this.securityTitle = "下载介质：" + mediumRow.mediumName + " 对应的附件";
      this.securityMediumId = mediumRow.id;
      this.securityHandleQuery();
    },
    // 取消按钮
    securityCancel() {
      this.securityOpen = false;
    },
    /**
     * 下载附件文件
     */
    securityHandleDownload(row) {
      row.params = {};
      row.params.opportunityNum = this.licOpportunityNum;
      const fileName = row.securityName + ".zip";
      // 修改默认时间 2min
      this.download("medium/mediumsecurity/download", {...row,}, fileName, {timeout: 120000});
    },
  },
};
</script>
