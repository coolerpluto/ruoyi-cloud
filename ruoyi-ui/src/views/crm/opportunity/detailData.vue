<template>
  <div class="app-container">
    <!--style="pointer-events: none;"-->
    <el-steps :active="stageActive" size="small" align-center simple>
      <el-step v-for="(item, index) in stageList" :key="index" :title="item.label" :icon="item.icon"
        v-if="stageShowList.includes(item.value)" @click.native="changeStage(item.value)" />
    </el-steps>
    <div style="text-align: center;" v-if="inputReq.action != 'V'">
      <div id="currentStageButArea" v-if="inputReq.currentStage === stageActive">
        <el-button-group>
          <el-button type="primary" icon="el-icon-unlock" v-if="flag.showReActiveBut" @click="reActiveOpportunity()">
            重新激活到L2
          </el-button>
          <el-button type="primary" icon="el-icon-finished" v-if="flag.showSaveUpdateBut"
            @click="saveOrupdateOpportunityData()">
            保存并更新
          </el-button>
          <el-button type="primary" v-if="flag.showNextBut" @click="changeOpportunityStage()">
            下一步
            <i class="el-icon-d-arrow-right el-icon--right"></i>
          </el-button>
        </el-button-group>
        &nbsp;
        <el-dropdown v-show="flag.showNextStageOptions" @command="handleNextStageCommand">
          <el-button type="primary">
            {{ targetStageName }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="item in targetNextStageList" :key="item.value" :command="item.value"
              :disabled="![1, 2, 3, 4, 5, 6, 7, 8, 9, 11].includes(item.value)">
              {{ item.label }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div id="hisStageButArea" v-if="flag.showUpdateBut">
        <el-button-group>
          <el-button type="primary" icon="el-icon-finished" @click="updateOpportunityData()">
            更新信息
          </el-button>
        </el-button-group>
      </div>
    </div>
    <stage-l01 ref="stage01" v-if="stageActive === 1" :oppdata="inputReq" />
    <stage-l02 ref="stage02" v-if="stageActive === 2" :oppdata="inputReq" />
    <stage-l03 ref="stage03" v-if="stageActive === 3" :oppdata="inputReq" />
    <stage-l04 ref="stage04" v-if="stageActive === 4" :oppdata="inputReq" />
    <stage-l05 ref="stage05" v-if="stageActive === 5" :oppdata="inputReq" />
    <stage-l06 ref="stage06" v-if="stageActive === 6" :oppdata="inputReq" />
    <stage-l07 ref="stage07" v-if="stageActive === 7" :oppdata="inputReq" />
    <stage-l08 ref="stage08" v-if="stageActive === 8" :oppdata="inputReq" />
    <stage-l09 ref="stage09" v-if="stageActive === 9" :oppdata="inputReq" />
    <stage-l11 ref="stage11" v-if="stageActive === 11" :oppdata="inputReq" />

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
  getPropertiesMap, getOppUserInfo,
  getOppCostInfo, 
  addUnitedOpp, updateUnitedOpps
} from "@/api/crm/oppUnitedInfo"
export default {
  name: "detailData",
  dicts: ['crm_opportunity_updataeverystage_person'],
  components: comObj,
  data() {
    return {
      inputReq: {
        action: "A",
        opportunityCode: "",
        currentStage: "",
      },
      flag: {
        showReActiveBut: false,
        showSaveUpdateBut: false,
        showNextBut: false,
        showNextStageOptions: false,
        showUpdateBut: false,
        hasSaveOrUpdate: false,
      },
      stageActive: undefined,
      modelActive: 1,
      updateEveryStagePerson: [],
      stageShowList: [1],
      targetNextStage: 0,// 向下新的阶段
      targetStageName: '选择阶段',
      stageShowHis: [],// 经历过的阶段
      targetNextStageList: [],// 向下新可选的阶段
      stageList: [
        { value: 1, label: "L1.新建商机", icon: "el-icon-plus" },
        { value: 2, label: "L2.分析机会", icon: "el-icon-document" },
        { value: 3, label: "L3.落实机会", icon: "el-icon-document-checked" },
        { value: 4, label: "L4.竞标", icon: "el-icon-s-cooperation" },
        { value: 5, label: "L5.合同签订", icon: "el-icon-picture" },
        { value: 6, label: "L6.弃标分析", icon: "el-icon-chat-dot-square" },
        { value: 7, label: "L7.项目停止", icon: "el-icon-chat-dot-square" },
        { value: 8, label: "L8.丢标分析", icon: "el-icon-chat-dot-square" },
        { value: 9, label: "L9.完成合同", icon: "el-icon-s-check" },
        { value: 11, label: "L11.关闭商机", icon: "el-icon-document-delete" },
      ],//可选的阶段
      stageTransferConfig: {},//跳转规则
    };
  },
  created() {
    const opportunityCode = this.$route.params && this.$route.params.code;
    const action = this.$route.params && this.$route.params.action;
    console.log("action:", action)
    this.inputReq.action = action;
    console.log("opportunityCode:", opportunityCode)
    this.inputReq.opportunityCode = opportunityCode;
    //const query = this.$route.query;//参考代码生产传值
    this.init();
  },
  methods: {
    refreshBut() {
      this.flag.showReActiveBut = [6, 7, 8, 11].includes(this.stageActive);
      this.flag.showSaveUpdateBut = true;
      // 新建时不显示下一步的操作按钮
      this.flag.showNextBut = this.inputReq.action == "A" ? false : ![6, 7, 8, 9, 11].includes(this.stageActive);
      this.flag.showNextStageOptions = this.inputReq.action == "A" ? false : ![6, 7, 8, 9, 11].includes(this.stageActive);
      // 特殊人员每个阶段都能更新
      this.updateEveryStagePerson = this.dict.type.crm_opportunity_updataeverystage_person.map((item) => {
        return item.value
      })
      this.flag.showUpdateBut = this.inputReq.currentStage !== this.stageActive && this.updateEveryStagePerson.includes(this.$store.getters.name)
    },
    init() {
      //商机当前阶段、走过的阶段以及阶段跳转配置信息
      stageConfigAndInfo({ code: this.inputReq.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        //商机当前阶段
        this.stageActive = this.inputReq.currentStage = response.data.stageInfo.currentStage * 1;
        //已经经历过的阶段
        let stageHisStr = response.data.stageInfo.stageHis;
        this.stageShowList = stageHisStr.map(function (data) {
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
        this.refreshBut()
        this.loading = false;
      });
    },
    // 商机属性查询
    getProperties(func) {
      if (!this.inputReq.opportunityCode || this.inputReq.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      if (this.inputReq.propertyMap && Object.keys(this.inputReq.propertyMap).length !== 0) {
        if (typeof func == 'function') {
          func();
        }
        return;
      }
      getPropertiesMap({ code: this.inputReq.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.propertyMap = response.data;
        if (typeof func == 'function') {
          func();
        }
      })
    },
    getOppUserInfo(func) {
      if (!this.inputReq.opportunityCode || this.inputReq.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      if (this.inputReq.userInfo && Object.keys(this.inputReq.userInfo).length !== 0) {
        if (typeof func == 'function') {
          func();
        }
        return;
      }
      getOppUserInfo({ code: this.inputReq.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.userInfo = response.data;
        if (typeof func == 'function') {
          func();
        }
      })
    },

    getOppCostInfo(func) {
      if (!this.inputReq.opportunityCode || this.inputReq.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      if (this.inputReq.costInfo && Object.keys(this.inputReq.costInfo).length !== 0) {
        if (typeof func == 'function') {
          func();
        }
        return;
      }
      getOppCostInfo({ code: this.inputReq.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.inputReq.costInfo = response.data;
        if (typeof func == 'function') {
          func();
        }
      })
    },

    refreshNextStageList() {
      // 将字符串转成数字数组
      let _this = this
      if (!this.stageTransferConfig[_this.stageActive]) {
        return;
      }
      let nextStageIdList = this.stageTransferConfig[_this.stageActive].targetStage.split(',').map(function (data) {
        return +data;
      })
      this.targetNextStageList = this.stageList.filter(item => nextStageIdList.includes(item.value))
    },
    //步骤条跳转
    changeStage(targetStage) {
      if (this.stageActive === targetStage) {
        this.$modal.msg("已在选择目标阶段，无需跳转");
      } else if (targetStage === 0) {
        this.$modal.alert("您还未选择目标阶段或者目标阶段选择错误！")
      } else {
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
    updateOpportunityData() {
      console.log(this.inputReq);
      if (this.updateEveryStagePerson.includes(this.$store.getters.name)) {
        // 特殊人 每个阶段都能保存， 保存时仅保留当前显示姐的信息
      }
    },
    saveOrupdateOpportunityData() {
      this.flag.hasSaveOrUpdate = true;
      console.log("saveOrupdateOpportunityData:",this.inputReq);
      switch (this.inputReq.currentStage) {
        case 1:        
          console.log("baseInfo.infoVerify:", this.$refs.stage01.$refs.baseInfo.infoVerify())
          console.log("baseInfo.collectInfo:", this.$refs.stage01.$refs.baseInfo.collectInfo())

          console.log("KeyContacts.infoVerify:", this.$refs.stage01.$refs.keyContacts.infoVerify())
          console.log("KeyContacts.collectInfo:", this.$refs.stage01.$refs.keyContacts.collectInfo())

          console.log("custInfo.infoVerify:", this.$refs.stage01.$refs.custInfo.infoVerify())
          console.log("custInfo.collectInfo:", this.$refs.stage01.$refs.custInfo.collectInfo())

          console.log("policyStandBy.infoVerify:", this.$refs.stage01.$refs.policyStandBy.infoVerify())
          console.log("policyStandBy.collectInfo:", this.$refs.stage01.$refs.policyStandBy.collectInfo())

          console.log("oppDesc.infoVerify:", this.$refs.stage01.$refs.oppDesc.infoVerify())
          console.log("oppDesc.collectInfo:", this.$refs.stage01.$refs.oppDesc.collectInfo())

          console.log("advancesInfo.infoVerify:", this.$refs.stage01.$refs.advancesInfo.infoVerify())
          console.log("advancesInfo.collectInfo:", this.$refs.stage01.$refs.advancesInfo.collectInfo())

          console.log("keyStandBy.infoVerify:", this.$refs.stage01.$refs.keyStandBy.infoVerify())
          console.log("keyStandBy.collectInfo:", this.$refs.stage01.$refs.keyStandBy.collectInfo())
          break;
        case 2:
          console.log("custInfo.infoVerify:", this.$refs.stage02.$refs.custInfo.infoVerify())
          console.log("custInfo.collectInfo:", this.$refs.stage02.$refs.custInfo.collectInfo())

          console.log("oppDesc.infoVerify:", this.$refs.stage02.$refs.oppDesc.infoVerify())
          console.log("oppDesc.collectInfo:", this.$refs.stage02.$refs.oppDesc.collectInfo())

          console.log("quotationInfo.infoVerify:", this.$refs.stage02.$refs.quotationInfo.infoVerify())
          console.log("quotationInfo.collectInfo:", this.$refs.stage02.$refs.quotationInfo.collectInfo())

          console.log("competitorInfo.infoVerify:", this.$refs.stage02.$refs.competitorInfo.infoVerify())
          console.log("competitorInfo.collectInfo:", this.$refs.stage02.$refs.competitorInfo.collectInfo())

          console.log("KeyContacts.infoVerify:", this.$refs.stage02.$refs.keyContacts.infoVerify())
          console.log("KeyContacts.collectInfo:", this.$refs.stage02.$refs.keyContacts.collectInfo())
          
          console.log("oppInfluence.infoVerify:", this.$refs.stage02.$refs.oppInfluence.infoVerify())
          console.log("oppInfluence.collectInfo:", this.$refs.stage02.$refs.oppInfluence.collectInfo())
          
          console.log("summaryInfo.infoVerify:", this.$refs.stage02.$refs.summaryInfo.infoVerify())
          console.log("summaryInfo.collectInfo:", this.$refs.stage02.$refs.summaryInfo.collectInfo())
                    
          console.log("advancesInfo.infoVerify:", this.$refs.stage02.$refs.advancesInfo.infoVerify())
          console.log("advancesInfo.collectInfo:", this.$refs.stage02.$refs.advancesInfo.collectInfo())

          console.log("keyStandBy.infoVerify:", this.$refs.stage02.$refs.keyStandBy.infoVerify())
          console.log("keyStandBy.collectInfo:", this.$refs.stage02.$refs.keyStandBy.collectInfo())
          break;
        case 3:
          console.log("oppDesc.infoVerify:", this.$refs.stage03.$refs.oppDesc.infoVerify())
          console.log("oppDesc.collectInfo:", this.$refs.stage03.$refs.oppDesc.collectInfo())
          
          console.log("oppImplement.infoVerify:", this.$refs.stage03.$refs.oppImplement.infoVerify())
          console.log("oppImplement.collectInfo:", this.$refs.stage03.$refs.oppImplement.collectInfo())

          console.log("competitorInfo.infoVerify:", this.$refs.stage03.$refs.competitorInfo.infoVerify())
          console.log("competitorInfo.collectInfo:", this.$refs.stage03.$refs.competitorInfo.collectInfo())

          console.log("KeyContacts.infoVerify:", this.$refs.stage03.$refs.keyContacts.infoVerify())
          console.log("KeyContacts.collectInfo:", this.$refs.stage03.$refs.keyContacts.collectInfo())
          
          console.log("quotationInfo.infoVerify:", this.$refs.stage03.$refs.quotationInfo.infoVerify())
          console.log("quotationInfo.collectInfo:", this.$refs.stage03.$refs.quotationInfo.collectInfo())
          
          console.log("advancesInfo.infoVerify:", this.$refs.stage03.$refs.advancesInfo.infoVerify())
          console.log("advancesInfo.collectInfo:", this.$refs.stage03.$refs.advancesInfo.collectInfo())

          console.log("keyStandBy.infoVerify:", this.$refs.stage03.$refs.keyStandBy.infoVerify())
          console.log("keyStandBy.collectInfo:", this.$refs.stage03.$refs.keyStandBy.collectInfo())
          break;
        case 4:
          break;
        case 5:
          break;
        case 6:
          break;
        case 7:
          break;
        case 8:
          break;
        case 9:
          break;
        case 11:
          break;
      }
    },
    changeOpportunityStage() {
      console.log(this.inputReq);
      this.changeStage(this.targetNextStage)
      this.flag.hasSaveOrUpdate = false;
    },
    reActiveOpportunity() {
      console.log(this.inputReq);
      this.changeStage(2);
    }
  }
}
</script>

<style scoped></style>
