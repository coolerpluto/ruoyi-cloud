<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="客户公司" prop="companyName">
        <el-input v-model="queryParams.companyName" placeholder="请输入公司名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="客户编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入客户编码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="rangeCreateDate" style="width: 340px" value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']"></el-date-picker>
      </el-form-item>
      <el-form-item label="创建人" prop="params.createBys">
        <el-select v-model="queryParams.params.createBys" collapse-tags placeholder="请输入 关键字拼音检索" filterable remote
                   multiple :remote-method="getPersonOptions" :loading="flag.personOptionsLoading">
          <el-option v-for="item in personOptions" :key="item.userName" :label="item.nickName" :value="item.userName">
            <span style="float: left">{{ item.nickName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ item.dept.deptName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="主负责人" prop="params.ownerIds">
        <el-select v-model="queryParams.params.ownerIds" collapse-tags placeholder="请输入 关键字拼音检索" filterable remote
                   multiple :remote-method="getPersonOptions" :loading="flag.personOptionsLoading">
          <el-option v-for="item in personOptions" :key="item.userName" :label="item.nickName" :value="item.userId">
            <span style="float: left">{{ item.nickName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ item.dept.deptName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="隶属部门" prop="params.deptIds">
        <treeselect v-model="queryParams.params.deptIds" style="width: 215px" :options="deptOptions" :show-count="true"
                    placeholder="请选择归属部门"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['crm:company:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['crm:company:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-s-promotion" size="mini" :disabled="multiple"
                   @click="handleTransfer"
                   v-hasPermi="['crm:company:transfer']">批量交接
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['crm:company:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['crm:company:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="companyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编码" align="center" prop="code" v-if="columns[6].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="名称" align="center" prop="companyName" v-if="columns[0].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司性质" align="center" prop="properties" v-if="columns[1].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.properties"/>
        </template>
      </el-table-column>
      <el-table-column label="行业类别" align="center" prop="industry" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="公司法人" align="center" prop="legal" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="注册资金" align="center" prop="capitalReg" v-if="columns[4].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">{{ scope.row.capitalReg }} 万元</template>
      </el-table-column>
      <el-table-column label="缴纳方式" align="center" prop="capitalPayType" v-if="columns[5].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_capital_pay_type" :value="scope.row.capitalPayType"/>
        </template>
      </el-table-column>
      <el-table-column label="公司官网" align="center" prop="website" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="地址代号" align="center" prop="addr" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="公司业务" align="center" prop="businessScope" v-if="columns[9].visible"
                       :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.crm_companny_business_scope" :value="scope.row.businessScope"/>
        </template>
      </el-table-column>
      <el-table-column label="公司描述" align="center" prop="remark" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="所有者" align="center" prop="nickName" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="所有者部门" align="center" prop="deptName" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建者" align="center" prop="createBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[14].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新者" align="center" prop="updateBy" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" v-if="columns[16].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-zoom-in" @click="handleDetail(scope.row)">详情
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['crm:company:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-s-promotion" @click="handleTransfer(scope.row)"
                     v-if="$store.getters.name == scope.row.ownerName" v-hasPermi="['crm:company:transfer']">交接
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-if="$store.getters.name == scope.row.ownerName" v-hasPermi="['crm:company:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改商机管理对话框 -->
    <el-dialog :title="transfer.title" :visible.sync="transfer.open" width="500px" append-to-body>
      <el-form ref="transfer.form" :model="transfer.form" :rules="transfer.rules" label-width="130px">
        <el-row>
          <el-col :span="18">
            <el-form-item label="转移目标人" prop="ownerName">
              <el-select v-model="transfer.form.ownerName" @change="getTargetPerson" placeholder="请输入 关键字拼音" filterable
                         remote :remote-method="getPersonOptions" :loading="flag.transferTargetPersonLoading">
                <el-option v-for="item in personOptions" :key="item.id" :label="item.nickName" :value="item.userName">
                  <span style="float: left">{{ item.nickName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.dept.deptName }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="transfer.form.remark" type="textarea" resize="none" :rows="3" placeholder="最多100字说明"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="transferSubmitForm">确 定</el-button>
        <el-button @click="transferCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="open" width="80%" :close-on-click-modal="false" append-to-body>
      <div id="baseCompanyArea">
        <label style="font-size: large;">客户基本信息</label>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="baseCompanyReadOnly">
          <el-row>
            <el-col :span="12">
              <el-form-item label="公司名称" prop="companyName">
                <el-input v-model="form.companyName" placeholder="请输入公司名称">
                  <el-button slot="append" icon="el-icon-search" @click="openCompanyDialog">选择</el-button>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公司性质" prop="properties">
                <el-select v-model="form.properties" placeholder="请选择公司性质" clearable>
                  <el-option v-for="dict in dict.type.crm_company_properties_type" :key="dict.value" :label="dict.label"
                             :value="dict.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="行业归属" prop="industry">
                <el-select v-model="form.industryCategory" placeholder="请选择行业大类" @change="handleSelectIndustryCategory">
                  <el-option v-for="dict in industryCategories" :key="dict.dictValue" :label="dict.dictLabel"
                             :value="dict.dictValue"/>
                </el-select>
                <el-select v-model="form.industrySubcategory" placeholder="请选择行业小类" clearable>
                  <el-option v-for="dict in industrySubcategories" :key="dict.dictValue" :label="dict.dictLabel"
                             :value="dict.dictValue"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公司法人" prop="legal">
                <el-input v-model="form.legal" placeholder="请选择输入公司法人"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="注册资金" prop="capitalReg">
                <el-input type="number" v-model="form.capitalReg" placeholder="请输入公司注册资金">
                  <template slot="append">万元</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="缴纳方式" prop="capitalPayType">
                <el-select v-model="form.capitalPayType" placeholder="请选择公司注册资金缴纳方式" clearable>
                  <el-option v-for="dict in dict.type.crm_capital_pay_type" :key="dict.value" :label="dict.label"
                             :value="dict.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公司官网" prop="website">
                <el-input v-model="form.website" placeholder="请输入公司官网地址"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="业务范围" prop="businessScope">
                <el-select v-model="form.businessScope" placeholder="请选择公司业务范围" clearable>
                  <el-option v-for="dict in dict.type.crm_companny_business_scope" :key="dict.value" :label="dict.label"
                             :value="dict.value" :disabled="!dict.value.includes('ISV')"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item prop="addr">
                <span slot="label">
                  公司地址
                  <el-tooltip content="推荐补如:北京/朝阳区/八里庄街道,商机中会使用到" placement="top">
                    <i class="el-icon-question"></i>
                  </el-tooltip>
                </span>
                <el-cascader :props="addrProps" v-model="form.addr" clearable placeholder="请选择公司地址"
                             style="width: 250px;">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="zipCode">
                <span slot="label">
                  地址邮编
                  <el-tooltip content="推荐补如:250000,商机中会使用到" placement="top">
                    <i class="el-icon-question"></i>
                  </el-tooltip>
                </span>
                <el-input v-model="form.zipCode" placeholder="请输入邮编"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="详细地址" prop="addrDetail">
                <el-input v-model="form.addrDetail" placeholder="请输入公司其他描述"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item prop="remark">
                <span slot="label">
                  公司描述
                  <el-tooltip content="推荐补,商机中会使用到" placement="top">
                    <i class="el-icon-question"></i>
                  </el-tooltip>
                </span>
                <el-input v-model="form.remark" type="textarea" :rows="1" placeholder="请输入公司介绍描述"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-steps :active="companyDetailActive" align-center simple v-if="baseCompanyReadOnly">
        <el-step title="客户商机管理" icon="el-icon-upload" @click.native="changeStep(1)"/>
        <el-step title="联系人管理" icon="el-icon-picture" @click.native="changeStep(2)"/>
        <el-step title="客户重点应用" icon="el-icon-picture" @click.native="changeStep(3)"/>
      </el-steps>
      <div id="stepsArea" v-if="baseCompanyReadOnly">
        <div id="customerOppArea" v-if="companyDetailActive === 1">
          <el-table v-loading="loading" :data="customerOppoList" key="one">
            <el-table-column label="商机Code" align="center" prop="code" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <router-link :to="'/crm/opportunity-data/index/'+scope.row.code+ '/V'" class="link-type">
                  <span>{{ scope.row.code }}</span>
                </router-link>
              </template>
            </el-table-column>
            <el-table-column label="商机名称" align="center" prop="name" :show-overflow-tooltip="true"/>
            <el-table-column label="当前阶段" align="center" prop="currentStage" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.currentStage"/>
              </template>
            </el-table-column>
            <el-table-column label="归属者" align="center" prop="nickName" :show-overflow-tooltip="true"/>
            <el-table-column label="投标时间" align="center" prop="preTenderDate" :show-overflow-tooltip="true"/>
            <el-table-column label="预计合同金额" align="center" prop="preContractVal" :show-overflow-tooltip="true"/>
            <el-table-column label="创建时间" align="center" prop="createTime" :show-overflow-tooltip="true"/>
          </el-table>
          <pagination v-show="customerOppoTotal > 0" :pageSizes="[4, 6, 8]" :total="customerOppoTotal"
                      :page.sync="queryCustomerOppoParams.pageNum" :limit.sync="queryCustomerOppoParams.pageSize"
                      @pagination="queryCustomerOppoList"/>
        </div>
        <div id="customerContactArea" v-if="companyDetailActive === 2">
          <el-table v-loading="loading" :data="customerContactList" key="two">
            <el-table-column label="联系人" align="center" prop="name" :show-overflow-tooltip="true"/>
            <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
              </template>
            </el-table-column>
            <el-table-column label="手机(主)" align="center" prop="priPhone" :show-overflow-tooltip="true"/>
            <el-table-column label="当前角色" align="center" prop="roleInCompany" :show-overflow-tooltip="true"/>
            <el-table-column label="当前职务" align="center" prop="postInCompany" :show-overflow-tooltip="true"/>
            <el-table-column label="所在部门" align="center" prop="deptInCompany" :show-overflow-tooltip="true"/>
            <el-table-column label="联系人类型" align="center" prop="category" :show-overflow-tooltip="true"/>
            <el-table-column label="邮件(主)" align="center" prop="priEmail" :show-overflow-tooltip="true"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdateCustomerContact(scope.row)"
                           v-hasPermi="['crm:company:edit']">修改
                </el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteCustomerContact(scope.row)"
                           v-hasPermi="['crm:company:remove']">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="customerContactTotal > 0" :pageSizes="[4, 6, 8]" :total="customerContactTotal"
                      :page.sync="queryCustomerContactParams.pageNum" :limit.sync="queryCustomerContactParams.pageSize"
                      @pagination="queryCustomerContactList"/>
          <div style="text-align: center;">
            <el-button type="primary" round @click="handleAddCustomerContact()">添加新联系人</el-button>
          </div>
        </div>
        <div id="customerAppArea" v-if="companyDetailActive === 3">
          <el-table v-loading="loading" :data="customerAppList" key="third">
            <el-table-column label="项目/应用名" align="center" prop="name" :show-overflow-tooltip="true"/>
            <el-table-column label="开发商" align="center" prop="isvName" :show-overflow-tooltip="true"/>
            <el-table-column label="当前数据库" align="center" prop="dataBase" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_system_db_type" :value="scope.row.dataBase"/>
              </template>
            </el-table-column>
            <el-table-column label="归属门类" align="center" prop="category" :show-overflow-tooltip="true"/>
            <el-table-column label="HGDB适配进度" align="center" prop="dbAdaptStatus" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.dbAdaptStatus"/>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdateCustomerApp(scope.row)"
                           v-hasPermi="['crm:company:edit']">修改
                </el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteCustomerApp(scope.row)"
                           v-hasPermi="['crm:company:remove']">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="customerAppTotal > 0" :pageSizes="[4, 6, 8]" :total="customerAppTotal"
                      :page.sync="queryCustomerAppParams.pageNum" :limit.sync="queryCustomerAppParams.pageSize"
                      @pagination="queryCustomerAppList"/>
          <div style="text-align: center;">
            <el-button type="primary" round @click="handleAddCustomerApp()">添加新项目/应用</el-button>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer" v-if="!baseCompanyReadOnly">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="companyDialog.title" :visible.sync="companyDialog.open" :show-close="false" label-width="150px"
               width="800px" append-to-body>
      <el-form ref="companyDialog.form" :model="companyDialog.form" @submit.native.prevent>
        <el-row>
          <el-col :span="15">
            <el-input v-model="companyDialog.form.searchValue" placeholder="请输入公司名称关键字" clearable
                      @keyup.enter.native="getListCompanys"/>
          </el-col>
          <el-col :span="5">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getListCompanys">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="companyDialog.form.searchValue = ''">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
      <el-table v-loading="flag.dialogCompanyTableLoading" highlight-current-row @current-change="handleCurrentChange"
                :data="companyDialog.companys">
        <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true"/>
        <el-table-column label="公司性质" align="center" prop="properties" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.properties"/>
          </template>
        </el-table-column>
        <el-table-column label="公司法人" align="center" prop="legal" :show-overflow-tooltip="true"/>
        <el-table-column label="公司地址" align="center" prop="addrDetail" :show-overflow-tooltip="true"/>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCompanyDialogForm">确 定</el-button>
        <el-button @click="cancelCompanyDialog">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="appAreaTitle" :visible.sync="openAppArea" width="40%" :close-on-click-modal="false"
               append-to-body>
      <el-form ref="formApp" :model="formApp" :rules="rulesApp" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用名称" prop="name">
              <el-input v-model="formApp.name" placeholder="请输入应用名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="当前版本" prop="version">
              <el-input v-model="formApp.version" placeholder="请输入应用当前版本,如:V1.0.14"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="软件分类大类" prop="categoryL1">
              <el-select v-model="formApp.categoryL1" @change="handleSelectCategoryL1" placeholder="请选择应用软件归属大类">
                <el-option v-for="item in appArea.categoryL1Options" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="软件分类小类" prop="categoryL2">
              <el-select v-model="formApp.categoryL2" placeholder="请选择应用软件归属小类">
                <el-option v-for="item in appArea.categoryL2Options" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictValue">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资金来源" prop="sysBuildMoney">
              <el-select v-model="formApp.sysBuildMoney" placeholder="请选择应用建设资金来源">
                <el-option v-for="dict in dict.type.crm_fund_source_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="软件开发商" prop="isvName">
              <el-input v-model="formApp.isvName" placeholder="请输入应用软件开发商"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用开发进度" prop="devStatus">
              <el-select v-model="formApp.devStatus" placeholder="请选择应用开发进度">
                <el-option v-for="dict in dict.type.sys_work_progress" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="HGDB适配进度" prop="dbAdaptStatus">
              <el-select v-model="formApp.dbAdaptStatus" placeholder="请选择HGDB适配进度">
                <el-option v-for="dict in dict.type.sys_work_progress" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部署类型" prop="deployType">
              <el-input v-model="formApp.deployType" placeholder="请输入应用软件部署类型"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="DB部署级别" prop="deployLevel">
              <el-input v-model="formApp.deployLevel" placeholder="请输入应用软件DB部署级别"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="当前数据库" prop="dataBase">
              <el-select v-model="formApp.dataBase" placeholder="请选择应用软件当前数据库">
                <el-option v-for="dict in dict.type.sys_system_db_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="DB选型" prop="dbSelectionModel">
              <el-input v-model="formApp.dbSelectionModel" placeholder="请输入应用软件当前数据库备选数据库"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="基础环境" prop="baseEnviron">
              <el-input v-model="formApp.baseEnviron" placeholder="请输入应用软件基础环境"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作系统" prop="opeartSys">
              <el-select v-model="formApp.opeartSys" placeholder="请选择应用软件操作系统">
                <el-option v-for="dict in dict.type.sys_operating_system" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="其他描述" prop="remark">
              <el-input v-model="formApp.remark" placeholder="请输入应用软件其他描述"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAppForm">确 定</el-button>
        <el-button @click="cancelApp">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="contactAreaTitle" :visible.sync="openContactArea" width="40%" :close-on-click-modal="false"
               append-to-body>
      <el-form ref="formContact" :model="formContact" :rules="rulesContact" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="name">
              <el-input v-model="formContact.name" placeholder="请输入联系人名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="formContact.sex" placeholder="请选择性别">
                <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
                           :value="dict.value"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属公司" prop="company">
              <el-input v-model="formContact.company" placeholder="请输入所属公司" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司角色" prop="roleInCompany">
              <el-input v-model="formContact.roleInCompany" placeholder="请输入所在公司的角色"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司职务" prop="postInCompany">
              <el-input v-model="formContact.postInCompany" placeholder="请输入所在公司的职务"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司部门" prop="deptInCompany">
              <el-input v-model="formContact.deptInCompany" placeholder="请输入所在公司的部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机(主)" prop="priPhone">
              <el-input v-model="formContact.priPhone" placeholder="请输入手机(主)"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机(副)" prop="secPhone">
              <el-input v-model="formContact.secPhone" placeholder="请输入手机(副号)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮件(主)" prop="priEmail">
              <el-input v-model="formContact.priEmail" placeholder="请输入主要使用的邮箱"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮件(副)" prop="secEmail">
              <el-input v-model="formContact.secEmail" placeholder="请输入次要邮箱"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系类型" prop="category">
              <el-input v-model="formContact.category" placeholder="请定义联系人的类型,例如:朋友,第一联系人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系地址" prop="addr">
              <el-input v-model="formContact.addr" placeholder="请选择联系人的地址信息"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述" prop="remark">
              <el-input v-model="formContact.remark" placeholder="请添加其他描述,例如详细地址"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitContactForm">确 定</el-button>
        <el-button @click="cancelContact">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listEmployee, deptTreeSelect} from "@/api/crm/employee";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {
  listCompany, transferCompanyOwner, tianYanChaSearch,
  getCompany, delCompany, addCompany, updateCompany,
  listOppoByCompany, listAppByCompany, listContactByCompany
} from "@/api/crm/company";
import {listAddr} from "@/api/system/addr";
import {
  getApplication, delApplication, addApplication, updateApplication
} from "@/api/crm/application";
import {getContact, delContact, addContact, updateContact} from "@/api/crm/contact";
import {getDicts as getDicts} from '@/api/system/dict/data'
import fecha from 'element-ui/src/utils/date';

export default {
  name: "CUST",
  components: {Treeselect},
  dicts: [
    'crm_companny_business_scope',
    'crm_company_properties_type',
    'crm_capital_pay_type',
    'crm_business_type',
    'crm_fund_source_type',
    'sys_user_sex',
    'sys_work_progress',
    'sys_system_db_type',
    'sys_operating_system',
    'crm_opportunity_status',
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
      // 基础客户信息禁止编辑
      baseCompanyReadOnly: false,
      //点击详情时展示的页签
      companyDetailActive: 1,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      customerOppoTotal: 0,
      customerContactTotal: 0,
      customerAppTotal: 0,
      // 公司表格数据
      companyList: [],
      // 公司商机表格数据
      customerOppoList: [],
      // 公司联系人表格数据
      customerContactList: [],
      // 公司项目应用表格数据
      customerAppList: [],
      // 弹出层标题
      title: "",
      appAreaTitle: "",
      contactAreaTitle: "",
      // 是否显示弹出层
      open: false,
      openAppArea: false,
      openContactArea: false,
      // 日期范围
      rangeCreateDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        params: {}
      },
      queryCustomerAppParams: {
        pageNum: 1,
        pageSize: 4,
      },
      queryCustomerContactParams: {
        pageNum: 1,
        pageSize: 4,
      },
      queryCustomerOppoParams: {
        pageNum: 1,
        pageSize: 4,
      },
      // 表单参数
      form: {},
      personOptions: [],
      industryCategories: [],
      industrySubcategories: [],
      formApp: {},
      appArea: {
        categoryL1Options: [],
        categoryL2Options: [],
      },
      formContact: {},
      // 表单校验
      rules: {
        companyName: [
          {required: true, message: "公司名称不能为空", trigger: "blur"},
        ],
        businessScope: [
          {required: true, message: "公司业务范围不能为空", trigger: "blur"},
        ],
      },
      rulesApp: {
        name: [
          {required: true, message: "应用名字不能为空", trigger: "blur"}
        ],
        category: [
          {required: true, message: "应用归属门类不能为空", trigger: "blur"}
        ],
        dataBase: [
          {required: true, message: "应用当前使用的数据库不能为空", trigger: "blur"}
        ],
      },
      rulesContact: {
        name: [
          {required: true, message: "联系人名称不能为空", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "性别不能为空", trigger: "blur"}
        ],
        company: [
          {required: true, message: "所属公司不能为空", trigger: "blur"}
        ],
        priPhone: [
          {required: true, message: "手机(主)不能为空", trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ],
        priEmail: [
          {type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"]}
        ],
        secEmail: [
          {type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"]}
        ],
      },
      // 列信息
      columns: [
        {key: 0, label: `公司名称`, visible: true},
        {key: 1, label: `公司性质`, visible: false},
        {key: 2, label: `行业归属`, visible: true},
        {key: 3, label: `公司法人`, visible: false},
        {key: 4, label: `注册资金`, visible: false},
        {key: 5, label: `缴纳方式`, visible: false},
        {key: 6, label: `客户编码`, visible: true},
        {key: 7, label: `公司官网`, visible: false},
        {key: 8, label: `地址代号`, visible: false},
        {key: 9, label: `公司业务`, visible: false},
        {key: 10, label: `公司描述`, visible: false},
        {key: 11, label: `主负责人`, visible: false},
        {key: 12, label: `隶属部门`, visible: false},
        {key: 13, label: `创建人`, visible: false},
        {key: 14, label: `创建时间`, visible: true},
        {key: 15, label: `更新者`, visible: false},
        {key: 16, label: `更新时间`, visible: true},
      ],
      // 部门树选项
      deptOptions: [],
      // 用户列表
      userList: [],
      addrProps: {
        lazy: true,
        value: 'code',
        label: 'name',
        children: 'children',
        checkStrictly: true,
        lazyLoad: function (node, resolve) {
          let addrParams = {
            level: 'province',
          }
          if (node.data) {
            addrParams.parent = node.data.code;
            switch (node.data.level) {
              case 'province':
                addrParams.level = 'city';
                break;
              case 'city':
                addrParams.level = 'area';
                break;
              case 'area':
                addrParams.level = 'street';
                break;
            }
          }
          listAddr(addrParams).then(response => {
            resolve(response.data)
          })
        }
      },
      flag: {
        dialogCompanyTableLoading: false,
        personOptionsLoading: false,
        selectedUnbeLongYou: false,
      },
      companyDialog: {
        // 弹出层标题
        title: "查找公司信息-关联天眼查",
        // 是否显示弹出层
        open: false,
        form: {
          pageNum: 1,
          pageSize: 15,
          searchValue: "",
          sourceType: "CUST"
        },
        selectedCompany: {},
        companys: []
      },
      transfer: {
        form: {
          params: {}
        },
        open: false,
        title: "",
        rules: {
          ownerName: [
            {required: true, message: "目标人不能为空", trigger: "blur"}
          ],
        }
      },
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    getAddrSelectOptions(node, resolve) {
      const {level} = node;
      let id = 0;
      setTimeout(() => {
        const nodes = Array.from({length: level + 1})
          .map(item => ({
            value: ++id,
            label: `选项${id}`,
            leaf: level >= 2
          }));
        // 通过调用resolve将子节点数据返回，通知组件数据加载完成
        resolve(nodes);
      }, 1000);
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      this.queryParams.sourceType = "CUST";
      listCompany(this.addDateRange(this.queryParams, this.rangeCreateDate, 'CreateDate')).then(response => {
        this.companyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    getPersonOptions(query) {
      this.flag.personOptionsLoading = true
      listEmployee({
        pageNum: 1,
        pageSize: 20,
        userName: query,
      }).then(response => {
        this.personOptions = response.rows;
        this.flag.personOptionsLoading = false;
      });
    },
    getCtegoryByCode(dictCode, func) {
      if (!dictCode) {
        if (typeof func == 'function') {
          func();
        }
        return;
      }
      getDicts(dictCode).then(res => {
        if (typeof func == 'function') {
          func(res.data);
        }
        return res.data;
      })
    },
    handleSelectIndustryCategory(val) {
      let select = this.industryCategories.find(item => {
        return item.dictValue == val;
      })
      var _this = this;
      this.getCtegoryByCode(select.remark, function (res) {
        _this.industrySubcategories = res;
        _this.$set(_this.form, 'industrySubcategory', '');
      });
    },
    handleGetIndustry() {
      if (!this.form.industryCategory) {
        return;
      }
      var _this = this
      let selectCategory = this.industryCategories.find(item => {
        return item.dictValue == _this.form.industryCategory;
      })
      this.form.industry = selectCategory.dictLabel;
      if (!this.form.industrySubcategory) {
        return;
      }
      let selectSubcategory = this.industrySubcategories.find(item => {
        return item.dictValue == _this.form.industrySubcategory;
      })
      this.form.industry = this.form.industry + '/' + selectSubcategory.dictLabel;
    },
    getIndustryCategories(func) {
      var _this = this;
      this.getCtegoryByCode('com_industry_category', function (res) {
        _this.industryCategories = res;
        _this.industrySubcategories = [];
        if (typeof func == 'function') {
          func();
        }
      });
      this.$set(this, 'industryCategories', []);
      this.$set(this, 'industrySubcategories', []);
    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
      this.baseCompanyReadOnly = false;
    },
    resetApp() {
      this.formApp = {}
      this.resetForm("formApp");
    },
    resetContact() {
      this.formContact = {}
      this.resetForm("formContact");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // 查询参数
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        params: {}
      },
        this.rangeCreateDate = [],
        this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.codes = selection.map(item => item.code);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
      this.flag.selectedUnbeLongYou = selection.findIndex(item => {
        return item.ownerName != this.$store.getters.name
      }) > -1 ? true : false;
    },
    // 查看客户详情
    handleDetail(row) {
      this.customerContactList = []
      this.customerAppList = []

      this.baseCompanyReadOnly = true
      getCompany(row.id).then(response => {
        this.form = response.data;
        this.form.addr = this.form.addr ? this.form.addr.split(',') : this.form.addr;
        this.open = true;
        this.title = "客户相关信息详情";
        this.changeStep(1);
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户基本信息";
      this.getIndustryCategories();
    },
    openCompanyDialog() {
      this.companyDialog.open = true;
      this.companyDialog.form.searchValue = null;
    },
    getListCompanys() {
      this.flag.dialogCompanyTableLoading = true
      tianYanChaSearch(this.companyDialog.form).then(response => {
        this.companyDialog.companys = (response.rows || response.data);
        this.companyDialog.total = response.total;
        this.flag.dialogCompanyTableLoading = false;
      });
    },
    handleCurrentChange(val) {
      this.companyDialog.selectedCompany = val
    },
    cancelCompanyDialog() {
      this.companyDialog.open = false;
    },
    submitCompanyDialogForm() {
      if (!this.companyDialog.selectedCompany || Object.keys(this.companyDialog.selectedCompany).length == 0) {
        this.$modal.msgError("您还未选择公司信息");
        return;
      }
      for (const key in this.companyDialog.selectedCompany) {
        if (this.companyDialog.selectedCompany[key] == "" || this.companyDialog.selectedCompany[key] == null) {
          delete this.companyDialog.selectedCompany[key];
        }
      }
      if (this.form.code) {
        delete this.companyDialog.selectedCompany["code"];
      }
      this.form = {...this.form, ...this.companyDialog.selectedCompany};
      this.companyDialog.open = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      // 若是禁止非主负责人修改则放开下面代码
      // if (typeof id != 'string') {
      //   if (this.flag.selectedUnbeLongYou) {
      //     this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
      //     return;
      //   }
      // }else if(this.$store.getters.name != row.ownerName){
      //   this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
      //   return;
      // }
      getCompany(id).then(response => {
        this.form = response.data;
        this.form.addr = this.form.addr ? this.form.addr.split(',') : this.form.addr;
        this.open = true;
        this.title = "修改客户基本信息";
        var _this = this;
        this.form.industry = []
        this.getIndustryCategories(function () {
          let select = _this.industryCategories.find(item => {
            return item.dictValue == _this.form.industryCategory;
          })
          _this.getCtegoryByCode(select.remark, function (res) {
            _this.industrySubcategories = res;
          });
        })
      });
    },
    /** 转交按钮 */
    handleTransfer(row) {
      const ids = row.id || this.ids;
      const codes = row.code || this.codes;
      this.transfer.form = {params: {}}//Object.assign({}, row)
      if (typeof codes == 'string') {
        if (this.$store.getters.name != row.ownerName) {
          this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
          return;
        }
        this.transfer.form.params.selectedCodes = [codes];
        this.transfer.form.params.companyIds = [ids];
      } else {
        if (this.flag.selectedUnbeLongYou) {
          this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
          return;
        }
        this.transfer.form.params.selectedCodes = codes;
        this.transfer.form.params.companyIds = ids;
      }
      this.transfer.open = true;
      this.transfer.title = '转移客/用户主负责人';
    },
    getTargetPerson(selected) {
      const select = this.personOptions.find(item => item.userName == selected)
      this.transfer.form.ownerId = select.userId;
      this.transfer.form.deptId = select.deptId;
    },
    /** 提交按钮 */
    transferSubmitForm() {
      this.$refs["transfer.form"].validate(valid => {
        if (valid) {
          let codes = this.transfer.form.params.selectedCodes
          var _this = this;
          this.transfer.form.sourceType = "CUST";
          this.$modal.confirm('是否确认交接编码为"' + codes + '"的客/用户项？').then(function () {
            return transferCompanyOwner(_this.transfer.form);
          }).then(() => {
            this.resetTransfer();
            this.transfer.open = false;
            this.getList();
            this.$modal.msgSuccess("转移成功");
          }).catch(() => {
          });
        }
      });
    },
    // 取消按钮
    transferCancel() {
      this.transfer.open = false;
      this.resetTransfer();
    },
    resetTransfer() {
      this.transfer.form = {};
      this.resetForm("transfer.form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.baseCompanyReadOnly = false
      this.reset();
    },
    changeStep(newStep) {
      this.companyDetailActive = newStep;
      switch (newStep) {
        case 1:
          // TODO
          this.queryCustomerOppoList();
          break;
        case 2:
          this.queryCustomerContactList();
          break;
        case 3:
          this.queryCustomerAppList();
          break;
      }
    },
    queryCustomerOppoList() {
      this.loading = true;
      this.queryCustomerOppoParams.id = this.form.id;//公司ID
      this.queryCustomerOppoParams.code = this.form.code;//公司编码
      listOppoByCompany(this.queryCustomerOppoParams).then(response => {
        this.customerOppoList = response.rows;
        this.customerOppoTotal = response.total;
        this.loading = false;
      });
    },
    handleOppoView(row) {
      const code = row.code;
      this.$router.push("/crm/opportunity-data/index/" + code + "/V");
    },
    queryCustomerContactList() {
      this.loading = true;
      this.queryCustomerContactParams.id = this.form.id;
      this.queryCustomerContactParams.code = this.form.code;
      listContactByCompany(this.queryCustomerContactParams).then(response => {
        this.customerContactList = response.rows;
        this.customerContactTotal = response.total;
        this.loading = false;
      });
    },
    queryCustomerAppList() {
      this.loading = true;
      this.queryCustomerAppParams.id = this.form.id;
      listAppByCompany(this.queryCustomerAppParams).then(response => {
        this.customerAppList = response.rows;
        this.customerAppTotal = response.total;
        this.loading = false;
      });
    },
    handleSelectCategoryL1(val) {
      let select = this.appArea.categoryL1Options.find(item => {
        return item.dictValue == val;
      })
      debugger
      if (!select) {
        return;
      }
      var _this = this;
      this.getCtegoryByCode(select.remark, function (res) {
        _this.appArea.categoryL2Options = res;
        _this.$set(_this.formApp, 'categoryL2', '');
      });
    },
    getSelectCategoryL1(func) {
      var _this = this;
      this.getCtegoryByCode('crm_software_category', function (res) {
        _this.appArea.categoryL1Options = res;
        _this.appArea.categoryL2Options = [];
        if (typeof func == 'function') {
          func();
        }
      });
      this.$set(this.appArea, 'categoryL1Options', []);
      this.$set(this.appArea, 'categoryL2Options', []);
    },

    cancelApp() {
      this.openAppArea = false;
      this.resetApp();
    },
    cancelContact() {
      this.openContactArea = false;
      this.resetContact();
    },
    submitAppForm() {
      this.$refs["formApp"].validate(valid => {
        if (valid) {
          let selectCategoryL1 = this.appArea.categoryL1Options.find(item => {
            return item.dictValue == this.formApp.categoryL1;
          });
          let selectCategoryL2 = this.appArea.categoryL2Options.find(item => {
            return item.dictValue == this.formApp.categoryL2;
          })
          this.formApp.category = selectCategoryL1.dictLabel + "/" + selectCategoryL2.dictLabel;
          if (this.formApp.id != null) {
            updateApplication(this.formApp).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openAppArea = false;
              this.changeStep(3);
            });
          } else {
            addApplication(this.formApp).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.openAppArea = false;
              this.changeStep(3);
            });
          }
        }
      });
    },
    submitContactForm() {
      this.$refs["formContact"].validate(valid => {
        if (valid) {
          if (this.formContact.id != null) {
            updateContact(this.formContact).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openContactArea = false;
              this.changeStep(2);
            });
          } else {
            addContact(this.formContact).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.openContactArea = false;
              this.changeStep(2);
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addr) {
            this.form.addr = this.form.addr.toString();
          }
          this.handleGetIndustry();
          if (this.form.id != null) {
            updateCompany(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (!this.form.code) {
              let timeStr = fecha.format(new Date(), 'yyyyMMdd HHmmss');
              let rondomStr = Math.random().toString(36).slice(-7);
              this.form.code = 'CUST' + timeStr.replace(" ", rondomStr);
            }
            this.form.sourceType = "CUST";
            addCompany(this.form).then(response => {
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
      const codes = row.code || this.codes;
      if (typeof ids != 'string') {
        if (this.flag.selectedUnbeLongYou) {
          this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
          return;
        }
      } else if (this.$store.getters.name != row.ownerName) {
        this.$modal.msgError("禁止操作，您选择了不属于您的数据请检查后再操作！");
        return;
      }
      this.$modal.confirm('是否确认删除公司编码为"' + codes + '"的数据项？').then(function () {
        return delCompany(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/company/export', {
        ...this.queryParams
      }, `company_${new Date().getTime()}.xlsx`)
    },

    handleAddCustomerContact() {
      this.resetContact();
      this.openContactArea = true;
      this.formContact.sourceId = this.form.id;
      this.formContact.sourceType = "CUST";
      this.formContact.company = this.form.companyName;
      this.contactAreaTitle = "添加客户联系人";
    },
    handleAddCustomerApp() {
      this.resetApp();
      this.openAppArea = true;
      this.formApp.sourceId = this.form.id;
      this.formApp.sourceType = "CUST";
      this.appAreaTitle = "添加客户项目/应用";
      this.getSelectCategoryL1();
    },
    handleUpdateCustomerContact(row) {
      this.resetContact();
      const id = row.id;
      getContact(id).then(response => {
        this.formContact = response.data;
        this.openContactArea = true;
        this.contactAreaTitle = "修改客户联系人";
      });
    },
    handleUpdateCustomerApp(row) {
      this.resetApp();
      const id = row.id;
      getApplication(id).then(response => {
        this.formApp = response.data;
        this.openAppArea = true;
        this.appAreaTitle = "修改客户项目/应用";
        var _this = this;
        this.getSelectCategoryL1(function () {
          let select = _this.appArea.categoryL1Options.find(item => {
            return item.dictValue == _this.formApp.categoryL1;
          })
          if (!select) {
            return;
          }
          _this.getCtegoryByCode(select.remark, function (res) {
            _this.appArea.categoryL2Options = res;
          });
        });
      });
    },
    handleDeleteCustomerApp(row) {
      const ids = row.id;
      this.$modal.confirm('是否确认删除公司应用编号为"' + ids + '"的数据项？').then(function () {
        return delApplication(ids);
      }).then(() => {
        this.changeStep(3);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    handleDeleteCustomerContact(row) {
      const ids = row.id;
      this.$modal.confirm('是否确认删除公司联系人编号为"' + ids + '"的数据项？').then(function () {
        return delContact(ids);
      }).then(() => {
        this.changeStep(2);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
  },
};
</script>
