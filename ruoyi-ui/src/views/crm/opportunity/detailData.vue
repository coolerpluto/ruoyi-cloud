<template>
  <div class="app-container" v-loading="flag.pageLoading">
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
  var names = fileName.split("/").pop().replace(/\.vue$/, "");
  // 获取组件配置
  const componentConfig = stageComponent(fileName);
  // 若该组件是通过"export default"导出的，优先使用".default"，否则退回到使用模块的根
  comObj[names] = componentConfig.default || componentConfig;
});
import {
  stageConfigAndInfo,
  getPropertiesMap, getOppUserInfo,
  addUnitedOpp, updateUnitedOpp
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
        pageLoading: false,
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
    this.inputReq.action = action;
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
      this.targetStageName = nextStageInfo.label
      this.targetNextStage = nextStageInfo.value
    },
    collectInfoByStage(stage) {
      let reqBody = { code: this.inputReq.opportunityCode };//为0时为新增
      switch (stage * 1) {
        case 1:
          reqBody['baseInfo'] = this.$refs.stage01.$refs.baseInfo.collectInfo().modifyedData;
          reqBody['keyContacts'] = this.$refs.stage01.$refs.keyContacts.collectInfo().modifyedData;
          reqBody['custInfo'] = this.$refs.stage01.$refs.custInfo.collectInfo().modifyedData;
          reqBody['policyStandBy'] = this.$refs.stage01.$refs.policyStandBy.collectInfo().modifyedData;
          reqBody['oppDesc'] = this.$refs.stage01.$refs.oppDesc.collectInfo().modifyedData;
          reqBody['advancesInfo'] = this.$refs.stage01.$refs.advancesInfo.collectInfo().modifyedData;
          reqBody['keyStandBy'] = this.$refs.stage01.$refs.keyStandBy.collectInfo().modifyedData;
          break;
        case 2:
          reqBody['custInfo'] = this.$refs.stage02.$refs.custInfo.collectInfo().modifyedData;
          reqBody['oppDesc'] = this.$refs.stage02.$refs.oppDesc.collectInfo().modifyedData
          reqBody['quotationInfo'] = this.$refs.stage02.$refs.quotationInfo.collectInfo().modifyedData;
          reqBody['competitorInfo'] = this.$refs.stage02.$refs.competitorInfo.collectInfo().modifyedData;
          reqBody['keyContacts'] = this.$refs.stage02.$refs.keyContacts.collectInfo().modifyedData;
          reqBody['oppInfluence'] = this.$refs.stage02.$refs.oppInfluence.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage02.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['advancesInfo'] = this.$refs.stage02.$refs.advancesInfo.collectInfo().modifyedData;
          reqBody['keyStandBy'] = this.$refs.stage02.$refs.keyStandBy.collectInfo().modifyedData;
          break;
        case 3:
          reqBody['oppDesc'] = this.$refs.stage03.$refs.oppDesc.collectInfo().modifyedData;
          reqBody['oppImplement'] = this.$refs.stage03.$refs.oppImplement.collectInfo().modifyedData;
          reqBody['competitorInfo'] = this.$refs.stage03.$refs.competitorInfo.collectInfo().modifyedData;
          reqBody['keyContacts'] = this.$refs.stage03.$refs.keyContacts.collectInfo().modifyedData;
          reqBody['quotationInfo'] = this.$refs.stage03.$refs.quotationInfo.collectInfo().modifyedData;
          reqBody['advancesInfo'] = this.$refs.stage03.$refs.advancesInfo.collectInfo().modifyedData;
          reqBody['keyStandBy'] = this.$refs.stage03.$refs.keyStandBy.collectInfo().modifyedData;
          break;
        case 4:
          reqBody['oppDesc'] = this.$refs.stage04.$refs.oppDesc.collectInfo().modifyedData;
          reqBody['biddingInfo'] = this.$refs.stage04.$refs.biddingInfo.collectInfo().modifyedData;
          reqBody['competitorInfo'] = this.$refs.stage04.$refs.competitorInfo.collectInfo().modifyedData;
          reqBody['keyContacts'] = this.$refs.stage04.$refs.keyContacts.collectInfo().modifyedData;
          reqBody['quotationInfo'] = this.$refs.stage04.$refs.quotationInfo.collectInfo().modifyedData;
          reqBody['winningBidding'] = this.$refs.stage04.$refs.winningBidding.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage04.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['advancesInfo'] = this.$refs.stage04.$refs.advancesInfo.collectInfo().modifyedData;
          reqBody['keyStandBy'] = this.$refs.stage04.$refs.keyStandBy.collectInfo().modifyedData;
          break;
        case 5:
          reqBody['quotationInfo'] = this.$refs.stage05.$refs.quotationInfo.collectInfo().modifyedData;
          reqBody['actionReason'] = this.$refs.stage05.$refs.actionReason.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage05.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['signInfo'] = this.$refs.stage05.$refs.signInfo.collectInfo().modifyedData;
          reqBody['advancesInfo'] = this.$refs.stage05.$refs.advancesInfo.collectInfo().modifyedData;
          reqBody['keyStandBy'] = this.$refs.stage05.$refs.keyStandBy.collectInfo().modifyedData;
          break;
        case 6:
          reqBody['actionReason'] = this.$refs.stage06.$refs.actionReason.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage06.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['costInfo'] = this.$refs.stage06.$refs.costInfo.collectInfo().modifyedData;
          break;
        case 7:
          reqBody['actionReason'] = this.$refs.stage07.$refs.actionReason.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage07.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['costInfo'] = this.$refs.stage07.$refs.costInfo.collectInfo().modifyedData;
          break;
        case 8:
          reqBody['actionReason'] = this.$refs.stage08.$refs.actionReason.collectInfo().modifyedData;
          reqBody['winningBidding'] = this.$refs.stage08.$refs.winningBidding.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage08.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['costInfo'] = this.$refs.stage06.$refs.costInfo.collectInfo().modifyedData;
          break;
        case 9:
          reqBody['quotationInfo'] = this.$refs.stage09.$refs.quotationInfo.collectInfo().modifyedData;
          reqBody['actionReason'] = this.$refs.stage09.$refs.actionReason.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage09.$refs.summaryInfo.collectInfo().modifyedData;
          reqBody['signInfo'] = this.$refs.stage09.$refs.signInfo.collectInfo().modifyedData;
          reqBody['advancesInfo'] = this.$refs.stage09.$refs.advancesInfo.collectInfo().modifyedData;
          reqBody['keyStandBy'] = this.$refs.stage09.$refs.keyStandBy.collectInfo().modifyedData;
          break;
        case 11:
          reqBody['actionReason'] = this.$refs.stage11.$refs.actionReason.collectInfo().modifyedData;
          reqBody['summaryInfo'] = this.$refs.stage11.$refs.summaryInfo.collectInfo().modifyedData;
          break;
      }
      return reqBody;
    },
    verifyByStage(stage) {
      switch (stage * 1) {
        case 1:
          let baseInfo1 = this.$refs.stage01.$refs.baseInfo.infoVerify(),
            keyContacts1 = this.$refs.stage01.$refs.keyContacts.infoVerify(),
            custInfo1 = this.$refs.stage01.$refs.custInfo.infoVerify(),
            policyStandBy1 = this.$refs.stage01.$refs.policyStandBy.infoVerify(),
            oppDesc1 = this.$refs.stage01.$refs.oppDesc.infoVerify(),
            advancesInfo1 = this.$refs.stage01.$refs.advancesInfo.infoVerify(),
            keyStandBy1 = this.$refs.stage01.$refs.keyStandBy.infoVerify();
          return baseInfo1 && keyContacts1 && custInfo1 && policyStandBy1 && oppDesc1 && advancesInfo1 && keyStandBy1;
        case 2:
          let custInfo2 = this.$refs.stage02.$refs.custInfo.infoVerify(),
            oppDesc2 = this.$refs.stage02.$refs.oppDesc.infoVerify(),
            quotationInfo2 = this.$refs.stage02.$refs.quotationInfo.infoVerify(),
            competitorInfo2 = this.$refs.stage02.$refs.competitorInfo.infoVerify(),
            keyContacts2 = this.$refs.stage02.$refs.keyContacts.infoVerify(),
            oppInfluence2 = this.$refs.stage02.$refs.oppInfluence.infoVerify(),
            summaryInfo2 = this.$refs.stage02.$refs.summaryInfo.infoVerify(),
            advancesInfo2 = this.$refs.stage02.$refs.advancesInfo.infoVerify(),
            keyStandBy2 = this.$refs.stage02.$refs.keyStandBy.infoVerify();
          return custInfo2 && oppDesc2 && quotationInfo2 && competitorInfo2 && keyContacts2 && oppInfluence2 && summaryInfo2 && advancesInfo2 && keyStandBy2;
        case 3:
          let oppDesc3 = this.$refs.stage03.$refs.oppDesc.infoVerify(),
            oppImplement3 = this.$refs.stage03.$refs.oppImplement.infoVerify(),
            competitorInfo3 = this.$refs.stage03.$refs.competitorInfo.infoVerify(),
            KeyContacts3 = this.$refs.stage03.$refs.keyContacts.infoVerify(),
            quotationInfo3 = this.$refs.stage03.$refs.quotationInfo.infoVerify(),
            advancesInfo3 = this.$refs.stage03.$refs.advancesInfo.infoVerify(),
            keyStandBy3 = this.$refs.stage03.$refs.keyStandBy.infoVerify();
          return oppDesc3 && oppImplement3 && competitorInfo3 && KeyContacts3 && quotationInfo3 && advancesInfo3 && keyStandBy3;
        case 4:
          let oppDesc4 = this.$refs.stage04.$refs.oppDesc.infoVerify(),
            biddingInfo4 = this.$refs.stage04.$refs.biddingInfo.infoVerify(),
            competitorInfo4 = this.$refs.stage04.$refs.competitorInfo.infoVerify(),
            KeyContacts4 = this.$refs.stage04.$refs.keyContacts.infoVerify(),
            quotationInfo4 = this.$refs.stage04.$refs.quotationInfo.infoVerify(),
            winningBidding4 = this.$refs.stage04.$refs.winningBidding.infoVerify(),
            summaryInfo4 = this.$refs.stage04.$refs.summaryInfo.infoVerify(),
            advancesInfo4 = this.$refs.stage04.$refs.advancesInfo.infoVerify(),
            keyStandBy4 = this.$refs.stage04.$refs.keyStandBy.infoVerify();
          return oppDesc4 && biddingInfo4 && competitorInfo4 && KeyContacts4 && quotationInfo4 && winningBidding4 && summaryInfo4 && advancesInfo4 && keyStandBy4;
        case 5:
          let quotationInfo5 = this.$refs.stage05.$refs.quotationInfo.infoVerify(),
            actionReason5 = this.$refs.stage05.$refs.actionReason.infoVerify(),
            summaryInfo5 = this.$refs.stage05.$refs.summaryInfo.infoVerify(),
            signInfo5 = this.$refs.stage05.$refs.signInfo.infoVerify(),
            advancesInfo5 = this.$refs.stage05.$refs.advancesInfo.infoVerify(),
            keyStandBy5 = this.$refs.stage05.$refs.keyStandBy.infoVerify();
          return quotationInfo5 && actionReason5 && summaryInfo5 && signInfo5 && advancesInfo5 && keyStandBy5;
        case 6:
          let actionReason6 = this.$refs.stage06.$refs.actionReason.infoVerify(),
            summaryInfo6 = this.$refs.stage06.$refs.summaryInfo.infoVerify(),
            costInfo6 = this.$refs.stage06.$refs.costInfo.infoVerify();
          return actionReason6 && summaryInfo6 && costInfo6;
        case 7:
          let actionReason7 = this.$refs.stage07.$refs.actionReason.infoVerify(),
            summaryInfo7 = this.$refs.stage07.$refs.summaryInfo.infoVerify(),
            costInfo7 = this.$refs.stage07.$refs.costInfo.infoVerify();
          return actionReason7 && summaryInfo7 && costInfo7;
        case 8:
          let actionReason8 = this.$refs.stage08.$refs.actionReason.infoVerify(),
            winningBidding8 = this.$refs.stage08.$refs.winningBidding.infoVerify(),
            summaryInfo8 = this.$refs.stage08.$refs.summaryInfo.infoVerify(),
            costInfo8 = this.$refs.stage06.$refs.costInfo.infoVerify();
          return actionReason8 && winningBidding8 && summaryInfo8 && costInfo8;
        case 9:
          let quotationInfo9 = this.$refs.stage09.$refs.quotationInfo.infoVerify(),
            actionReason9 = this.$refs.stage09.$refs.actionReason.infoVerify(),
            summaryInfo9 = this.$refs.stage09.$refs.summaryInfo.infoVerify(),
            signInfo9 = this.$refs.stage09.$refs.signInfo.infoVerify(),
            advancesInfo9 = this.$refs.stage09.$refs.advancesInfo.infoVerify(),
            keyStandBy9 = this.$refs.stage09.$refs.keyStandBy.infoVerify();
          return quotationInfo9 && actionReason9 && summaryInfo9 && signInfo9 && advancesInfo9 && keyStandBy9;
        case 11:
          let actionReason11 = this.$refs.stage11.$refs.actionReason.infoVerify(),
            summaryInfo11 = this.$refs.stage11.$refs.summaryInfo.infoVerify();
          return actionReason11 && summaryInfo11;
      }
    },
    updateOpportunityData() {
      if (!this.updateEveryStagePerson.includes(this.$store.getters.name)) {
        // 特殊人 每个阶段都能保存， 保存时仅保留当前显示姐的信息
        this.$modal.msgError("对不去您没权限进行该操作,若需要请联系管理员！");
        return;
      }
      console.log("updateOpportunityData:", this.stageActive);
      if (!this.verifyByStage(this.stageActive)) {
        return;
      }
      let updateData = this.collectInfoByStage(this.stageActive);
      updateData["stageActive"] = this.stageActive;
      updateData["currentStage"] = this.inputReq.currentStage;
      console.log("updateData:", updateData)
      this.flag.pageLoading = true;
      updateUnitedOpp(updateData).then(response => {
        this.$modal.msgSuccess("更新成功");
        this.flag.pageLoading = false;
      }).catch(() => {
        this.$modal.msgError("更新失败");
        this.flag.pageLoading = false;
      });;
    },
    saveOrupdateOpportunityData() {
      this.flag.hasSaveOrUpdate = true;
      console.log("saveOrupdateOpportunityData:", this.inputReq);
      if (!this.verifyByStage(this.inputReq.currentStage)) {
        return;
      }
      let saveOrupdateData = this.collectInfoByStage(this.inputReq.currentStage);
      saveOrupdateData["currentStage"] = this.inputReq.currentStage;
      saveOrupdateData["stageActive"] = this.stageActive;
      console.log("saveOrupdateData:", saveOrupdateData);
      this.flag.pageLoading = true;
      addUnitedOpp(saveOrupdateData).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.flag.pageLoading = false;
      }).catch(() => {
        this.$modal.msgError("新增失败");
        this.flag.pageLoading = false;
      });;
    },
    changeOpportunityStage() {
      this.changeStage(this.targetNextStage)
      this.flag.hasSaveOrUpdate = false;
    },
    reActiveOpportunity() {
      this.changeStage(2);
    }
  }
}
</script>

<style scoped></style>
