<template>
  <div class="app-container" id="keyContact" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>商机关键联系人<span style="color: red;">({{ stageTitle }})</span></h3>
    <el-divider />
    <el-form ref="keyContactForm" :model="keyContactForm" size="medium" label-width="220px"
      v-loading="flag.contactInfoLoading">
      <el-button style="float:right;" type="info" icon="el-icon-plus" @click="openDialog">添加新联系人</el-button>
      <el-table :data="keyContactForm.keyContacts">
        <el-table-column label="姓名" align="center" prop="name" :show-overflow-tooltip="true" />
        <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
          </template>
        </el-table-column>
        <el-table-column label="单位名称" align="center" prop="company" :show-overflow-tooltip="true" />
        <el-table-column label="单位性质" align="center" prop="companyProperty" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.companyProperty" />
          </template>
        </el-table-column>
        <el-table-column label="人员角色" align="center" prop="roleInOppo" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_external_person_role" :value="scope.row.roleInOppo" />
          </template>
        </el-table-column>
        <el-table-column label="人员职务" align="center" prop="postInCompany" :show-overflow-tooltip="true" />
        <el-table-column label="所在部门" align="center" prop="deptInCompany" :show-overflow-tooltip="true" />
        <el-table-column align="center" prop="priPhone" :show-overflow-tooltip="true">
          <template slot="header" slot-scope="scope">
            <label>联系方式<br><span style="color: red;">(首选手机号)</span></label>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="exposure" :show-overflow-tooltip="true">
          <template slot="header" slot-scope="scope">
            <label>对接情况<span style="color: red;">(按照重要程度选择最新进展)</span></label>
          </template>
        </el-table-column>
        <el-table-column label="关系程度" align="center" prop="relationship" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_hgdb_acceptability" :value="scope.row.relationship" />
          </template>
        </el-table-column>
        <el-table-column label="意向厂商" align="center" prop="intention" :show-overflow-tooltip="true" />
        <el-table-column label="是否会面对手" align="center" prop="sideVisited" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.sideVisited" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="editKeyContact(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="removeKeyContact(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div id="contactNumVerify" align="center" v-if="flag.contactNumVerify">{{ stageTitle }}</div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="keyContactsDialog.title" :visible.sync="keyContactsDialog.open" width="1000px" append-to-body>
      <el-form ref="keyContactsDialog.form" :model="keyContactsDialog.form" :rules="keyContactsDialog.rules"
        label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="keyContactsDialog.form.name" placeholder="请输入联系人名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="keyContactsDialog.form.sex" placeholder="选择联系人性别">
                <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电话(主)" prop="priPhone">
              <el-input v-model="keyContactsDialog.form.priPhone" placeholder="请输入电话号码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="邮件(主)" prop="priEmail">
              <el-input v-model="keyContactsDialog.form.priEmail" placeholder="请输入邮件地址" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮件(备)" prop="secEmail">
              <el-input v-model="keyContactsDialog.form.secEmail" placeholder="请输入备用邮件" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电话(备)" prop="secPhone">
              <el-input v-model="keyContactsDialog.form.secPhone" placeholder="请输入备用电话号码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="单位名称" prop="company">
              <el-input v-model="keyContactsDialog.form.company" placeholder="请输入单位名称">
                <el-button slot="append" icon="el-icon-search" @click="openCompanyDialog">选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="单位性质" prop="companyProperty">
              <el-select v-model="keyContactsDialog.form.companyProperty" placeholder="请选择单位性质">
                <el-option v-for="dict in dict.type.crm_company_properties_type" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所在部门" prop="deptInCompany">
              <el-input v-model="keyContactsDialog.form.deptInCompany" placeholder="请输入所在部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="人员角色" prop="roleInOppo">
              <el-select v-model="keyContactsDialog.form.roleInOppo" placeholder="请录入人员角色">
                <el-option v-for="dict in dict.type.crm_external_person_role" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="人员职务" prop="postInCompany">
              <el-input v-model="keyContactsDialog.form.postInCompany" placeholder="请输入人员职务" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="关系程度" prop="relationship">
              <el-select v-model="keyContactsDialog.form.relationship" placeholder="选择关系程度">
                <el-option v-for="dict in dict.type.crm_hgdb_acceptability" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="意向厂商" prop="intention">
              <el-input v-model="keyContactsDialog.form.intention" placeholder="多个时使用逗号分割" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否见对手" prop="sideVisited">
              <el-select v-model="keyContactsDialog.form.sideVisited" placeholder="选择会面情况">
                <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="对接情况" prop="exposure">
              <el-select v-model="keyContactsDialog.form.exposure" placeholder="请输入最新对接情况">
                <el-option v-for="dict in dict.type.crm_communicate_type" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogForm">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="companyDialog.title" :visible.sync="companyDialog.open" :show-close="false" label-width="150px"
      width="800px" append-to-body>
      <el-form ref="companyDialog.form" :model="companyDialog.form" @submit.native.prevent>
        <el-row>
          <el-col :span="15">
            <el-input v-model="companyDialog.form.companyName" placeholder="请输入公司名称关键字" clearable />
          </el-col>
          <el-col :span="5">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getListCompanys">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="companyDialog.form.companyName = ''">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
      <el-table v-loading="flag.dialogCompanyTableLoading" highlight-current-row @current-change="handleCurrentChange"
        :data="companyDialog.companys">
        <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true" />
        <el-table-column label="公司性质" align="center" prop="properties" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.properties" />
          </template>
        </el-table-column>
        <el-table-column label="公司法人" align="center" prop="legal" :show-overflow-tooltip="true" />
        <el-table-column label="公司地址" align="center" prop="addrDetail" :show-overflow-tooltip="true" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCompanyDialogForm">确 定</el-button>
        <el-button @click="cancelCompanyDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getOppContactsInfo
} from "@/api/crm/oppUnitedInfo"
import { listCompany } from "@/api/crm/company";

export default {
  name: "KeyContacts",
  dicts: [
    'sys_yes_no', 'sys_user_sex', 'crm_company_properties_type', 'crm_external_person_role', 'crm_hgdb_acceptability', 'crm_communicate_type'
  ],
  props: {
    stageShow: {
      type: Number,
      required: true
    },
    stageTitle: {
      type: String,
      default: ''
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    },
  },
  data() {
    return {
      keyContactForm: {
        keyContacts: []
      },
      keyContactModified: {
      },
      keyContactOriginBak: {
      },
      flag: {
        contactInfoLoading: false,
        contactNumVerify: false,
      },
      //组件弹框承载
      keyContactsDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {
          name: [
            { required: true, message: "联系人名称不能为空", trigger: "blur" }
          ],
          sex: [
            { required: true, message: "联系人性别不能为空", trigger: "blur" }
          ],
          priPhone: [
            { required: true, message: "联系人电话不能为空", trigger: "blur" }
          ],
          intention: [
            { required: true, message: "联系人意向厂商不能为空", trigger: "blur" }
          ],
          exposure: [
            { required: false, message: "对接情况不能为空", trigger: "blur" }
          ],
          sideVisited: [
            { required: false, message: "是否会面对手不能为空", trigger: "blur" }
          ],
          relationship: [
            { required: false, message: "关系程度不能为空", trigger: "blur" }
          ],
          roleInOppo: [
            { required: true, message: "人员角色不能为空", trigger: "blur" }
          ],
        },
      },
      companyDialog: {
        // 弹出层标题
        title: "公司信息查找",
        // 是否显示弹出层
        open: false,
        form: {
          pageNum: 1,
          pageSize: 15,
          //businessScope:"S", // 公司业务中带SCOPE的 任何公司联系人都可以
          companyName: ""
        },
        selectedCompany: {},
        companys: []
      },
    }
  },
  created() {
    this.initKeyContacts();
  },
  methods: {
    initKeyContacts() {
      var _this = this
      // 开启遮盖层
      this.flag.contactInfoLoading = true;
      this.getOppContactsInfo(function () {
        _this.flag.contactInfoLoading = false;
      })
    },
    getOppContactsInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppContactsInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.keyContactForm.keyContacts = response.data;
        this.keyContactOriginBak = JSON.parse(JSON.stringify(this.keyContactForm))

        if (typeof func == 'function') {
          func();
        }
      })
    },
    removeKeyContact(row) {
      let index = this.keyContactForm.keyContacts.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.keyContactForm.keyContacts.splice(index, 1);
    },
    editKeyContact(row) {
      this.keyContactsDialog.form = Object.assign({}, row);
      this.keyContactsDialog.open = true;
      this.keyContactsDialog.title = "修改关键联系人信息";
    },
    openDialog() {
      this.keyContactsDialog.open = true;
      this.keyContactsDialog.form = {}
      this.keyContactsDialog.title = "添加关键联系人信息";
    },
    openCompanyDialog() {
      this.companyDialog.open = true;
    },
    getListCompanys() {
      this.flag.dialogCompanyTableLoading = true
      listCompany(this.companyDialog.form).then(response => {
        this.companyDialog.companys = (response.rows);
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
      if (Object.keys(this.companyDialog.selectedCompany).length == 0) {
        this.$modal.msgError("您还未选择公司信息");
        return;
      }
      this.keyContactsDialog.form.company = this.companyDialog.selectedCompany.companyName;
      this.keyContactsDialog.form.companyCode = this.companyDialog.selectedCompany.code;
      this.keyContactsDialog.form.companyProperty = this.companyDialog.selectedCompany.properties;
      this.companyDialog.open = false;
    },
    submitDialogForm() {
      this.$refs["keyContactsDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        if (!this.keyContactsDialog.form.id && !this.keyContactsDialog.form.tempId) {
          this.keyContactsDialog.form.tempId = new Date().getTime()//先打个标
          this.keyContactForm.keyContacts.push(this.keyContactsDialog.form)
        }
        // 替换
        this.keyContactForm.keyContacts.map((item, i) => {
          //带来的
          if (item.id && item.id == this.keyContactsDialog.form.id) {
            this.keyContactForm.keyContacts.splice(i, 1, this.keyContactsDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.keyContactsDialog.form.tempId) {
            this.keyContactForm.keyContacts.splice(i, 1, this.keyContactsDialog.form)
          }
        })
        this.keyContactsDialog.open = false;
      });
    },
    cancelDialog() {
      this.keyContactsDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.flag.contactNumVerify = false
      if (!this.keyContactForm.keyContacts || this.keyContactForm.keyContacts.length == 0) {
        this.flag.contactNumVerify = true
        flag = false
      }
      this.$refs["keyContactForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.keyContactOriginBak).length == 0) {
        this.keyContactModified.keyContacts_a = this.keyContactForm.keyContacts
        return;
      }
      this.keyContactModified.keyContacts_a = []
      this.keyContactModified.keyContacts_m = []
      this.keyContactModified.keyContacts_d = []

      for (let options of this.keyContactForm.keyContacts) {
        if (!options.id) {
          this.keyContactModified.keyContacts_a.push(options)
          continue;
        }
        let currentOld = this.keyContactOriginBak.keyContacts.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.keyContactModified.keyContacts_m.push(options)
      }
      for (let options of this.keyContactOriginBak.keyContacts) {
        let current = this.keyContactForm.keyContacts.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.keyContactModified.keyContacts_d.push(options)
      }
    },
    //对象属性全部平铺的情况
    objEqual(oldObj, newObj) {
      for (let key in oldObj) {
        // 不考虑new新加的标识属性
        if (oldObj[key] !== newObj[key]) {
          return false;
        }
      }
      return true;
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
      return {
        currentData: this.keyContactForm,//最新展示数据
        modifyedData: this.keyContactModified,//改动部分
        originData: this.keyContactOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#contactNumVerify {
  color: #ff4949;
  font-size: 12px;
  line-height: 3;
}
</style>
