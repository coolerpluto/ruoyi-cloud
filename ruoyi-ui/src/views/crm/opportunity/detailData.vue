<template>
  <div class="app-container">
    <!--style="pointer-events: none;"-->
    <el-steps :active="stageActive" size="small" align-center simple>
      <el-step v-for="(item, index) in stageList" :key="index"
               :title="item.label" :icon="item.icon"
               v-if="stageShowList.includes(item.value)"
               @click.native="changeStage(item.value)"/>
    </el-steps>
    <div style="text-align: center;">
      <div id="currentStageButArea" v-if="inputReq.currentStage === stageActive">
        <el-button-group>
          <el-button type="primary" icon="el-icon-unlock" v-if="[6,7,8,11].includes(stageActive)" @click="changeStage(2)">
            重新激活到L2
          </el-button>
          <el-button type="primary" icon="el-icon-finished">
            保存并更新
          </el-button>
          <el-button type="primary" v-if="![6,7,8,9,11].includes(stageActive)" @click="changeStage(targetNextStage)">
            下一步
            <i class="el-icon-d-arrow-right el-icon--right"></i>
          </el-button>
        </el-button-group>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <el-dropdown v-show="![6,7,8,9,11].includes(stageActive)" @command="handleNextStageCommand">
          <el-button type="primary">
            {{ targetStageName }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="item in targetNextStageList"
                              :key="item.value"
                              :command="item.value"
                              :disabled="![1,2,3,4,5,6,7,8,9,11].includes(item.value)">
              {{ item.label }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div id="hisStageButArea" v-if="inputReq.currentStage !== stageActive && flag.showUpdateBut">
        <el-button-group>
          <el-button type="primary" icon="el-icon-finished">
            更新信息
          </el-button>
        </el-button-group>
      </div>
    </div>
    <stage-l01 v-if="stageActive ===1"/>
    <stage-l02 v-if="stageActive ===2"/>
    <stage-l03 v-if="stageActive ===3"/>
    <stage-l04 v-if="stageActive ===4"/>
    <stage-l05 v-if="stageActive ===5"/>
    <stage-l06 v-if="stageActive ===6"/>
    <stage-l07 v-if="stageActive ===7"/>
    <stage-l08 v-if="stageActive ===8"/>
    <stage-l09 v-if="stageActive ===9"/>
    <stage-l11 v-if="stageActive ===11"/>

  </div>
</template>

<script>
// 引入所有需要的动态组件 以 "stage" 开头,以".vue"结尾
const stageComponent = require.context(
  "./opportunity_stage", //组件所在目录的相对路径
  false, //是否查询其子目录
  /\.vue$/ //匹配基础组件文件名的正则表达式
);
var comObj = {};
stageComponent.keys().forEach(fileName => {
  // 获取文件名
  var names = fileName
    .split("/")
    .pop()
    .replace(/\.vue$/, "");
  // 获取组件配置
  const componentConfig = stageComponent(fileName);
  // 若该组件是通过"export default"导出的，优先使用".default"，否则退回到使用模块的根
  comObj[names] = componentConfig.default || componentConfig;
});
import {
  stageConfigAndInfo,
  getPropertiesMap,getOppBaseInfo,getOppUserInfo,getOppPolicyInfo,
  getOppAdvancesInfo,getOppCostInfo,getOppCompetitorInfo,
  getOppQuotationInfo,getOppSupportInfo,getOppContactsInfo,
  listUnitedOpp,getUnitedOpp,delUnitedOpp,addUnitedOpp,updateUnitedOpp
}  from "@/api/crm/oppUnitedInfo"
export default {
  name: "detailData",
  dicts: ['crm_opportunity_updataeverystage_person'],
  components: comObj,
  data() {
    return {
      inputReq:{
        action:"A",
        opportunityCode:"",
        currentStage:"",
        propertyMap:undefined,
        baseInfo:undefined,
        userInfo:undefined,
        policyInfo:undefined,
        advancesInfo:undefined,
        costInfo:undefined,
        competitorInfo:undefined,
        quotationInfo:undefined,
        supportInfo:undefined,
        contactsInfo:undefined,
        updateEverStagePerson:[],
      },
      flag:{
        showReActiveBut:false,
        showSaveUpdateBut:false,
        showNextBut:false,
        showNextStageOptions:false,
      },
      stageActive: undefined,
      modelActive: 1,
      stageShowList:[1],
      targetNextStage: 0,// 向下新的阶段
      targetStageName: '选择阶段',
      stageShowHis: [],// 经历过的阶段
      targetNextStageList: [],// 向下新可选的阶段
      stageList: [
        {value: 1, label: "L1.新建商机",icon:"el-icon-plus"},
        {value: 2, label: "L2.分析机会",icon:"el-icon-document"},
        {value: 3, label: "L3.落实机会",icon:"el-icon-document-checked"},
        {value: 4, label: "L4.竞标",icon:"el-icon-s-cooperation"},
        {value: 5, label: "L5.合同签订",icon:"el-icon-picture"},
        {value: 6, label: "L6.弃标分析",icon:"el-icon-chat-dot-square"},
        {value: 7, label: "L7.项目停止",icon:"el-icon-chat-dot-square"},
        {value: 8, label: "L8.丢标分析",icon:"el-icon-chat-dot-square"},
        {value: 9, label: "L9.完成合同",icon:"el-icon-s-check"},
        {value: 11, label: "L11.关闭商机",icon:"el-icon-document-delete"},
      ],//可选的阶段
      stageTransferConfig:{},//跳转规则
    };
  },
  created() {
    const opportunityCode = this.$route.params && this.$route.params.code;
    const action = this.$route.params && this.$route.params.action;
    console.log("action:",action)
    this.inputReq.action = action;
    console.log("opportunityCode:",opportunityCode)
    this.inputReq.opportunityCode = opportunityCode;
    //const query = this.$route.query;//参考代码生产传值
    this.init();
  },
  methods: {
    refreshBut() {
      this.flag.showReActiveBut = false;
      this.flag.showSaveUpdateBut = false;
      this.flag.showNextBut = false;
      this.flag.showNextStageOptions = false;

      this.inputReq.updateEverStagePerson = this.dict.type.crm_opportunity_updataeverystage_person.map((item)=>{
        return item.value
      })
      this.flag.showUpdateBut = this.inputReq.updateEverStagePerson.includes(this.$store.getters.name)
    },
    init(){
      //商机当前阶段、走过的阶段以及阶段跳转配置信息
      stageConfigAndInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        //商机当前阶段
        this.stageActive = this.inputReq.currentStage = response.data.stageInfo.currentStage*1;
        //已经经历过的阶段
        let stageHisStr = response.data.stageInfo.stageHis;
        this.stageShowList=stageHisStr.map(function(data){
          return +data;
        })
        this.stageShowList.sort((a, b) => {
          return a - b;
        });
        // 不同阶段的阶段跳转控制列表
        this.stageTransferConfig = response.data.activeConfig;
        // 获取当前阶段的跳转
        // 将字符串转成数字数组
        this.refreshNextStageList()
        if (response.data.stageInfo && response.data.stageInfo.stageHisList){
          this.getProperties()
        }
        this.loading = false;
      });
    },
    // 商机属性查询
    getProperties(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.propertyMap && Object.keys(this.inputReq.propertyMap).length !==0){
        return;
      }
      getPropertiesMap({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.propertyMap = response.data;
      })
    },
    getOppBaseInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.baseInfo && Object.keys(this.inputReq.baseInfo).length !==0){
        return;
      }
      getOppBaseInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.baseInfo = response.data;
      })
    },
    getOppUserInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.userInfo && Object.keys(this.inputReq.userInfo).length !==0){
        return;
      }
      getOppUserInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.userInfo = response.data;
      })
    },
    getOppPolicyInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.policyInfo && Object.keys(this.inputReq.policyInfo).length !==0){
        return;
      }
      getOppPolicyInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.policyInfo = response.data;
      })
    },
    getOppAdvancesInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.advancesInfo && Object.keys(this.inputReq.advancesInfo).length !==0){
        return;
      }
      getOppAdvancesInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.advancesInfo = response.data;
      })
    },
    getOppCostInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.costInfo && Object.keys(this.inputReq.costInfo).length !==0){
        return;
      }
      getOppCostInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.costInfo = response.data;
      })
    },
    getOppCompetitorInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.competitorInfo && Object.keys(this.inputReq.competitorInfo).length !== 0){
        return;
      }
      getOppCompetitorInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.competitorInfo = response.data;
      })
    },
    getOppQuotationInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.quotationInfo && Object.keys(this.inputReq.quotationInfo).length !==0){
        return;
      }
      getOppQuotationInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.quotationInfo = response.data;
      })
    },
    getOppSupportInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.supportInfo && Object.keys(this.inputReq.supportInfo).length !== 0){
        return;
      }
      getOppSupportInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.supportInfo = response.data;
      })
    },
    getOppContactsInfo(){
      if(!this.inputReq.opportunityCode||this.inputReq.opportunityCode === '0'){
        return
      }
      if(this.inputReq.contactsInfo && Object.keys(this.inputReq.contactsInfo).length !== 0){
        return;
      }
      getOppContactsInfo({code:this.inputReq.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.contactsInfo = response.data;
      })
    },
    refreshNextStageList(){
      // 将字符串转成数字数组
      let _this=this
      if(!this.stageTransferConfig[_this.stageActive]){
        return;
      }
      let nextStageIdList = this.stageTransferConfig[_this.stageActive].targetStage.split(',').map(function (data) {
        return +data;
      })
      this.targetNextStageList = this.stageList.filter(item=> nextStageIdList.includes(item.value))
    },
    //步骤条跳转
    changeStage(targetStage) {
      if(this.stageActive === targetStage){
        this.$modal.msg("已在选择目标阶段，无需跳转");
      }else if(targetStage ===0){
        this.$modal.alert("您还未选择目标阶段或者目标阶段选择错误！")
      }else {
        this.stageActive = targetStage;
        this.stageShowList.push(targetStage)
        this.stageShowList.sort((a, b) => {
          return a - b;
        });
        this.targetStageName = '选择阶段'
        this.targetNextStage = 0
        this.refreshBut()
        this.refreshNextStageList()
      }
    },
    //下拉阶段选择
    handleNextStageCommand(command) {
      let nextStageInfo = this.targetNextStageList.find(item => item.value === command)
      console.log(nextStageInfo)
      this.targetStageName = nextStageInfo.label
      this.targetNextStage = nextStageInfo.value
    },
  }
}
</script>

<style scoped>

</style>
