<template>
  <div class="app-container" id="baseInfo" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>基础信息</h3>
    <el-divider />
    <el-form ref="baseInfoForm" :rules="rules" :model="baseInfoForm" size="medium" label-width="220px"
      v-loading="flag.baseInfoLoading">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="商机名称" prop="oppInfo.name">
            <el-input v-model="baseInfoForm.oppInfo.name" placeholder="请输入商机名称" clearable :style="{ width: '100%' }">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商机编码" prop="oppInfo.code">
            <label placeholder="自动生产商机编码">{{ baseInfoForm.oppInfo.code || genderOppCode() }}</label>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="应用运营信息">
            <div class="el-form-item__error" v-if="flag.applicationVerify">应用运营信息最少有一条</div>
            <el-button style="float:right;" type="info" icon="el-icon-plus" @click="openDialog">添加新条目</el-button>
            <el-table :data="baseInfoForm.operations">
              <el-table-column label="客户系统名称" align="center" fixed prop="applicationName" :show-overflow-tooltip="true" />
              <el-table-column label="系统开发商" align="center" fixed prop="isv" :show-overflow-tooltip="true" />
              <el-table-column label="适配进度" align="center" fixed prop="currentAdaptedProgress"
                :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.currentAdaptedProgress" />
                </template>
              </el-table-column>
              <el-table-column label="招标前必须完成度" align="center" fixed prop="targetAdaptedProgress"
                :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.sys_work_progress" :value="scope.row.targetAdaptedProgress" />
                </template>
              </el-table-column>
              <el-table-column label="软件分类" align="center" fixed prop="category" :show-overflow-tooltip="true" >
                <template slot-scope="scope">
                  {{scope.row.category instanceof Array?scope.row.category.join("/"):scope.row.category}}
                </template>
              </el-table-column>
              <el-table-column label="项目运作主体" align="center" fixed prop="operationalName" :show-overflow-tooltip="true" />
              <el-table-column label="操作" align="center" fixed>
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-edit"
                    @click="editApplication(scope.row)">修改</el-button>
                  <el-button size="mini" type="text" icon="el-icon-delete"
                    @click="removeApplication(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="baseInfoDialog.title" :visible.sync="baseInfoDialog.open" width="1000px" append-to-body>
      <el-form ref="baseInfoDialog.form" :model="baseInfoDialog.form" :rules="baseInfoDialog.rules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户系统名称" prop="applicationName">
              <el-input v-model="baseInfoDialog.form.applicationName" placeholder="请输入应用名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在用数据库" prop="dbBase">
              <el-select v-model="baseInfoDialog.form.dbBase" placeholder="请选择当前在用的数据库">
                <el-option v-for="dict in dict.type.sys_system_db_type" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="软件分类" prop="category">
              <!-- <el-select v-model="baseInfoDialog.form.category" placeholder="请选择应用软件归属门类">
                <el-option v-for="dict in dict.type.crm_software_category" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select> -->
              <el-cascader ref="categoryCascade" 
              @change="getCategoryCheckedNodes" 
              :props="baseInfoDialog.softwareCategory" 
              v-model="baseInfoDialog.form.category" 
              placeholder="请选择软件归属门类">
                </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="软件开发商" prop="isv">
              <el-select v-model="baseInfoDialog.form.isv" @change="getIsv" placeholder="请输入关键字" filterable remote
                :remote-method="getIsvOptions" :loading="flag.isvOptionsLoading">
                <el-option v-for="item in baseInfoDialog.isvOptions" 
                  :key="item.id" :label="item.companyName"
                  :value="item.companyName">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标前必须完成度" prop="targetAdaptedProgress">
              <el-select v-model="baseInfoDialog.form.targetAdaptedProgress" placeholder="请选择招标前必须完成度">
                <el-option v-for="dict in dict.type.sys_work_progress" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="当前适配进度" prop="currentAdaptedProgress">
              <el-select v-model="baseInfoDialog.form.currentAdaptedProgress" placeholder="请选择HGDB适配进度">
                <el-option v-for="dict in dict.type.sys_work_progress" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目运作主体" prop="operationalName">
              <el-select v-model="baseInfoDialog.form.operationalName"  @change="getOperational" placeholder="请输入关键字" filterable remote
                :remote-method="getOperationalOptions" :loading="flag.operationalOptionsLoading">
                <el-option v-for="item in baseInfoDialog.operationalOptions" 
                  :key="item.id" :label="item.companyName"
                  :value="item.companyName">
                </el-option>
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
  </div>
</template>

<script>
import {listCompany} from "@/api/crm/company";
import {getDicts as getDicts} from '@/api/system/dict/data'

import {
  getOppBaseInfo
} from "@/api/crm/oppUnitedInfo"
export default {
  name: "baseInfo",
  dicts: ['sys_work_progress', 'sys_system_db_type'],
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      //组件信息承载
      baseInfoForm: {
        oppInfo: {},
        operations: []
      },
      //组件信息承载修改删除的信息
      baseInfoModified: {
        oppInfo: {},
        operations_a: [],
        operations_m: [],
        operations_d: []
      },
      //组件信息原始承载备份
      baseInfoOriginBak: {
        oppInfo: {},
        operations: []
      },
      //标识集合
      flag: {
        baseInfoLoading: false,
        applicationVerify: false,
        operationalOptionsLoading: false,
        isvOptionsLoading: false,
      },
      // 表单校验
      rules: {
        oppInfo: {
          name: [
            { required: true, message: "商机名称不能为空", trigger: "blur" }
          ],
        }
      },
      //组件弹框承载
      baseInfoDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {
          applicationName: [
            { required: true, message: "客户系统名称不能为空", trigger: "blur" }
          ],
          dbBase: [
            { required: true, message: "在用数据库不能为空", trigger: "blur" }
          ],
          category: [
            { required: true, message: "软件分类不能为空", trigger: "blur" }
          ],
          isv: [
            { required: true, message: "软件开发商不能为空", trigger: "blur" }
          ],
          targetAdaptedProgress: [
            { required: true, message: "招标前必须完成度不能为空", trigger: "blur" }
          ],
          currentAdaptedProgress: [
            { required: true, message: "当前适配进度不能为空", trigger: "blur" }
          ],
          operationalName: [
            { required: true, message: "项目运作主体未选择", trigger: "blur" }
          ],
        },
        isvOptions: [],
        operationalOptions: [],
        softwareCategory:{
          lazy: true,
          value: 'dictLabel',
          label: 'dictLabel',
          children: 'children',
          checkStrictly: false,
          lazyLoad: function(node, resolve){
            let dictCode="crm_software_category"
            if (node.data){
              dictCode=node.data.remark
            }
            if (!dictCode){
              resolve();
              return
            }
            getDicts(dictCode).then(res => {
              resolve(res.data);
            })            
          },
        },
      },
    };
  },
  created() {
    this.initBase();
  },
  mounted() {
  },
  methods: {
    initBase() {
      var _this = this
      // 开启遮盖层
      this.flag.baseInfoLoading = true;
      //console.log("base:",_this.oppdata)
      this.getOppBaseInfo(function () {
        _this.flag.baseInfoLoading = false
      });
    },
    //获取最新显示信息
    getOppBaseInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppBaseInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.baseInfoForm = response.data;
        this.baseInfoOriginBak = JSON.parse(JSON.stringify(this.baseInfoForm))
        if (typeof func == 'function') {
          func();
        }
      })
    },
    genderOppCode() {
      function add0(m) {
        return m < 10 ? '0' + m : m
      }
      function add00(m) {
        return m < 10 ? '00' + m : m < 100 ? '0' + m : m
      }
      const currentTime = new Date();
      let year = currentTime.getFullYear();
      let month = currentTime.getMonth() + 1,
        date = currentTime.getDate(),
        hours = currentTime.getHours(),
        minutes = currentTime.getMinutes(),
        seconds = currentTime.getSeconds(),
        milliseconds = currentTime.getMilliseconds();
      let code = "A" + year + add0(month) + add0(date) + "N" + add0(hours) + add0(minutes) + add0(seconds) + add00(milliseconds)
      return code;
    },
    getOperationalOptions (query) {
      this.flag.operationalOptionsLoading = true
      this.baseInfoDialog.operationalOptions = [{companyName:"瀚高直签",id:"0"}]
      listCompany({pageNum: 1,
        pageSize: 20,
        businessScope:"S", // 公司业务中带S的
        companyName:query
      }).then(response => {
        this.baseInfoDialog.operationalOptions = this.baseInfoDialog.operationalOptions.concat(response.rows);
        this.flag.operationalOptionsLoading = false;
      });
    },
    getOperational(selected){
      const select = this.baseInfoDialog.operationalOptions.find(item => item.companyName == selected)
      this.baseInfoDialog.form.operationalId= select.id;
    },
    getIsvOptions(query){
      this.flag.isvOptionsLoading = true     
      listCompany({pageNum: 1,
        pageSize: 20,
        businessScope:"ISV", //公司业务中带ISV的
        companyName:query
      }).then(response => {
        this.baseInfoDialog.isvOptions = response.rows;
        this.flag.isvOptionsLoading = false;
      });
    },
    getIsv(selected){
      const select = this.baseInfoDialog.isvOptions.find(item => item.companyName == selected)
      this.baseInfoDialog.form.isvId= select.id;
    },
    getCategoryCheckedNodes(req){
      //console.log('getCategoryCheckedNodes',this.$refs.categoryCascade.getCheckedNodes())
    },
    removeApplication(row) {
      let index = this.baseInfoForm.operations.findIndex(item=>{
        if (row.tempId){
            return item.tempId == row.tempId//新建的或者点击过修改
        }else{
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.baseInfoForm.operations.splice(index, 1);
    },
    editApplication(row) {
      //this.getCategoryCheckedNodes()
      this.baseInfoDialog.form = row 
      this.baseInfoDialog.form.category = this.baseInfoDialog.form.category.split('/')
      this.baseInfoDialog.open = true;
      this.baseInfoDialog.title = "修改应用运营信息";
    },
    openDialog() {
      this.baseInfoDialog.form={}
      this.baseInfoDialog.open = true;
      this.baseInfoDialog.title = "添加应用运营信息";
      this.getOperationalOptions();
      this.getIsvOptions()
    },
    submitDialogForm() {
      this.$refs["baseInfoDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.baseInfoDialog.form.category = this.baseInfoDialog.form.category.join("/");
        if (!this.baseInfoDialog.form.id && !this.baseInfoDialog.form.tempId){
          this.baseInfoDialog.form.tempId = new Date().getTime()//先打个标
          this.baseInfoForm.operations.push(this.baseInfoDialog.form)
        }
        console.log("submitDialogForm",this.baseInfoForm.operations)
        this.baseInfoDialog.open = false;
      });
    },
    cancelDialog() {
      this.baseInfoDialog.open = false;
      if (this.baseInfoDialog.form.category) {
        this.baseInfoDialog.form.category = this.baseInfoDialog.form.category.join("/");
      }
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.flag.applicationVerify = false
      if (!this.baseInfoForm.operations || this.baseInfoForm.operations.length == 0) {
        this.flag.applicationVerify = true
        flag = false
      }
      this.$refs["baseInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation (){
      this.baseInfoModified.operations_a=[]
      this.baseInfoModified.operations_m=[]
      this.baseInfoModified.operations_d=[]
      if (this.baseInfoOriginBak.oppInfo.name !==this.baseInfoForm.oppInfo.name){
        this.baseInfoModified.oppInfo.name = this.baseInfoForm.oppInfo.name
      }
      console.log("fetchInformation",this.baseInfoForm.operations)
      for(let options of this.baseInfoForm.operations){
        if (!options.id){
          this.baseInfoModified.operations_a.push(options)
          continue;
        }
        let currentOld = this.baseInfoOriginBak.operations.find(item=>{return item.id==options.id;})
        if(this.objEqual(currentOld,options)){
          continue;
        }
        this.baseInfoModified.operations_m.push(options)
      }
      console.log("baseInfoOriginBak",this.baseInfoOriginBak.operations)
      for(let options of this.baseInfoOriginBak.operations){
        let current = this.baseInfoForm.operations.find(item=>{return item.id==options.id;})
        if(current){
          continue;
        }
        this.baseInfoModified.operations_d.push(options)
      }
    },
    //对象属性全部平铺的情况
    objEqual(oldObj,newObj){
      for(let key in oldObj){
        // 不考虑new新加的标识属性
        if(oldObj[key] !== newObj[key]){
          return false;
        }
      }
      return true;
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
      return {
        currentData: this.baseInfoForm,//最新展示数据
        modifyedData: this.baseInfoModified,//改动部分
        originData: this.baseInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
