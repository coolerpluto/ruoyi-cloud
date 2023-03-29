<template>
  <div class="app-container" id="keyContact" :style="{pointerEvents:oppdata.action=='V'?'none':'unset'}">
    <h3>商机关键联系人<span style="color: red;">({{stageTitle}})</span></h3>
    <el-divider/>
    <el-form ref="keyContactForm" :model="keyContactForm" size="medium" label-width="220px" v-loading="flag.contactInfoLoading">
      <el-button style="float:right;" type="info" icon="el-icon-plus" @click="openDialog">添加新联系人</el-button>
      <el-table :data="keyContactForm.keyContacts">
        <el-table-column label="姓名" align="center" prop="name" :show-overflow-tooltip="true"/>
        <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true">
          <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
            </template>
        </el-table-column>
        <el-table-column label="单位名称" align="center" prop="company" :show-overflow-tooltip="true"/>
        <el-table-column label="单位性质" align="center" prop="companyProperty" :show-overflow-tooltip="true">
          <template slot-scope="scope">
              <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.companyProperty"/>
            </template>
        </el-table-column>
        <el-table-column label="人员角色" align="center" prop="roleInCompany" :show-overflow-tooltip="true"/>
        <el-table-column label="人员职务" align="center" prop="postInCompany" :show-overflow-tooltip="true"/>
        <el-table-column label="所在部门" align="center" prop="deptInCompany" :show-overflow-tooltip="true"/>
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
        <el-table-column label="关系程度" align="center" prop="relationship" :show-overflow-tooltip="true"/>
        <el-table-column label="意向厂商" align="center" prop="intention" :show-overflow-tooltip="true"/>
        <el-table-column label="是否会面对手" align="center" prop="sideVisited" :show-overflow-tooltip="true">
          <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.sideVisited"/>
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="editKeyContact(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="removeKeyContact(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div id="contactNumVerify" align="center" v-if="flag.contactNumVerify">{{stageTitle}}</div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="keyContactsDialog.title" :visible.sync="keyContactsDialog.open" width="1000px" append-to-body>
      <el-form ref="keyContactsDialog.form" :model="keyContactsDialog.form" :rules="keyContactsDialog.rules" label-width="100px">
        <el-row>
          <el-col :span="8"></el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogForm">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
 getOppContactsInfo
}  from "@/api/crm/oppUnitedInfo"
export default {
  name: "KeyContacts",
  dicts: [
    'sys_yes_no','sys_user_sex','crm_company_properties_type'
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
        keyContacts:[]
      },
      keyContactModified:{},
      keyContactOriginBak:{},
      flag:{
        contactInfoLoading: false,
        contactNumVerify:false,
      },
      //组件弹框承载
      keyContactsDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {},
      },
    }
  },
  created() {
    this.initKeyContacts();
  },  
  methods:{
    initKeyContacts(){
      var _this = this
      //console.log("KeyContacts:",_this.oppdata)
      // 开启遮盖层
      this.flag.contactInfoLoading = true;
      this.getOppContactsInfo(function(){
        _this.flag.contactInfoLoading = false;
      })
    },
    getOppContactsInfo(func){
      if(!this.oppdata.opportunityCode||this.oppdata.opportunityCode === '0'){
        if (typeof func == 'function'){
          func();
        }
        return
      }      
      getOppContactsInfo({code:this.oppdata.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.keyContactForm.keyContacts = response.data;
        this.keyContactOriginBak = JSON.parse(JSON.stringify(this.keyContactForm))

        if (typeof func == 'function'){
          func();
        }
      })
    },
    removeKeyContact(row) {

    },
    editKeyContact(row) {
      this.keyContactsDialog.open = true;
      this.keyContactsDialog.title = "修改关键联系人信息";
    },
    openDialog() {
      this.keyContactsDialog.open = true;
      this.keyContactsDialog.title = "添加关键联系人信息";
    },
    submitDialogForm() {
      this.keyContactsDialog.open = false;
    },
    cancelDialog() {
      this.keyContactsDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
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
    // 提供本组件的全部数据
    collectInfo() {      
      return {
              currentData:this.keyContactForm,//最新展示数据
              modifyedData:this.keyContactModified,//改动部分
              originData:this.keyContactOriginBak,//原始数据
            };
    },
  },
}
</script>

<style scoped>
#contactNumVerify{
  color: #ff4949;
    font-size: 12px;
    line-height: 3;
}
</style>
