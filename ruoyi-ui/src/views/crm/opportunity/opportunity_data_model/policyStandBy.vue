<template>
  <div class="app-container" id="policyStandBy" :style="{pointerEvents:oppdata.action=='V'?'none':'unset'}"><h3>政策支持信息</h3>
    <el-divider/>
    <el-form ref="policyStandByForm" :model="policyStandByForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="9">
          <el-form-item label="政策年分" prop="policyReleaseYear.propertyVal">
            <el-input v-model="policyStandByForm.policyReleaseYear.propertyVal" placeholder="请选择政策发布年份" clearable>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="15">
          <el-form-item label="政策文件" prop="policyName.propertyVal">
            <el-input v-model="policyStandByForm.policyName.propertyVal" placeholder="请输入选择政策文件" clearable>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {
  getOppPolicyInfo,getPropertiesMap
}  from "@/api/crm/oppUnitedInfo"
export default {
  name: "PolicyStandBy",
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    },
  },
  data() {
    return {
      propertyKeys:[
        "policyId",
        "policyName",
        "policyReleaseYear"
      ],
      policyStandByForm: {
        "policyId":{},
        "policyName":{},
        "policyReleaseYear" :{}
      },
      policyStandByModified: {},
      policyStandByOriginBak: {},

      //标识集合
      flag: {
        policyInfoLoading: false,
      },
      //组件弹框承载
      policyInfoDialog: {
        // 弹出层标题
        title: "选择用户",
        // 是否显示弹出层
        open: false,
        form: {},
      },
    };
  },
  created() {
    this.initPolicyStandBy();
  },
  methods:{
    initPolicyStandBy(){
      var _this = this
      // 开启遮盖层
      this.flag.policyInfoLoading = true;
      this.getProperties(function(){
        //console.log("policyStandByForm:",_this.policyStandByForm)
        _this.flag.policyInfoLoading = false;
      })
    },
    // 商机属性查询
    getProperties(func){
      if(!this.oppdata.opportunityCode||this.oppdata.opportunityCode === '0'){
        if (typeof func == 'function'){
          func();
        }
        return
      }
      let req={
        code:this.oppdata.opportunityCode,        
        params:{
          propertyKeys: this.propertyKeys,
          model:"policy",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.policyStandByForm = response.data;
        this.policyStandByOriginBak = JSON.parse(JSON.stringify(this.policyStandByForm))
        if (typeof func == 'function'){
          func();
        }
      }).catch(error=>{
        if (typeof func == 'function'){
          func();
        }
      })
    },
    getOppPolicyInfo(func){
      if(!this.oppdata.opportunityCode||this.oppdata.opportunityCode === '0'){
        if (typeof func == 'function'){
          func();
        }
        return
      }      
      getOppPolicyInfo({
        code:this.oppdata.opportunityCode
      }).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.policyStandByForm = response.data;
        if (typeof func == 'function'){
          func();
        }
      })
    },
    openDialog() {
      this.policyInfoLoading.open = true;
    },
    submitDialogForm() {
      this.policyInfoLoading.open = false;
    },
    cancelDialog() {
      this.policyInfoLoading.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["policyStandByForm"].validate(valid => {
        if (!valid) {
          flag = false
          return flag
        }
      });

      if (!this.policyStandByForm.policyId.propertyVal) {
        flag = false
      }      
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {      
      return {
              currentData:this.policyStandByForm,//最新展示数据
              modifyedData:this.policyStandByModified,//改动部分
              originData:this.policyStandByOriginBak,//原始数据
            };
    },
  },
}
</script>

<style scoped>

</style>
