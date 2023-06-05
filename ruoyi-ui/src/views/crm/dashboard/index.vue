<template>
  <div class="crm-dashboard-editor-container" style="padding: 20px 0px;">
    <div class="dashboard">
      <img class="imgaction" src="@/assets/images/scan.png">
    </div>
    <div style="color: white;padding: 0 10px;">
      <h1 align="center">CRM 商机信息仪表盘</h1>
      <el-col :xs="24" :sm="24" :lg="8">
        <h2 align="center">个人负责范围</h2>
        <el-row>
          <div id="oppoStageSituation">
            <OppStageSelf :dataScope="'self'"></OppStageSelf>
          </div>
        </el-row>
        <el-row>
          <SalesAmountSelf :dataScope="'self'"></SalesAmountSelf>
        </el-row>
        <el-row>
          <div id="advances">
            <OppAdvancesSelf :dataScope="'self'"></OppAdvancesSelf>
          </div>
        </el-row>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <h2 align="center">全员前十排名</h2>
        <el-row>
          <div id="oppoTop101">
            <SalespersonOppTop10></SalespersonOppTop10>
          </div>
        </el-row>
        <el-row>
          <div id="oppoTop102">
            <TurnoverTop10></TurnoverTop10>
          </div>
        </el-row>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <h2 align="center">全部人员概况</h2>
        <el-row>
          <div id="allStageSituation">
            <OppStageAll :dataScope="'all'"></OppStageAll>
          </div>
        </el-row>
        <el-row>
          <SalesAmountAll :dataScope="'all'"></SalesAmountAll>
        </el-row>
        <el-row>
          <div id="allAdvances">
            <OppAdvancesAll :dataScope="'all'"></OppAdvancesAll>
          </div>
        </el-row>
      </el-col>
    </div>
  </div>
</template>

<script>

import OppAdvancesSelf from "./OppAdvances";
import OppAdvancesAll from "./OppAdvances";
import OppStageSelf from "./OppStage";
import OppStageAll from "./OppStage";
import SalesAmountSelf from "./SalesAmount";
import SalesAmountAll from "./SalesAmount";
import SalespersonOppTop10 from "./SalespersonOppTop10";
import TurnoverTop10 from "./TurnoverTop10";

export default {
  name: "dashboard",
  components: {
    OppStageSelf,
    SalesAmountSelf,
    OppAdvancesSelf,
    OppStageAll,
    SalesAmountAll,
    OppAdvancesAll,
    SalespersonOppTop10,
    TurnoverTop10,
  },
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
    };
  },
  created() {
  },
  methods: {
    getList() {
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}

.dashboard {
  background-image: url("../../../assets/images/bg18.jpg");
  background-size: cover;
  //background-position: center;
  background-position-y: -33px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: -99;
}

.scan {
  background-image: url("../../../assets/images/scan.png");
  background-size: cover;
  background-position: center;
  animation-duration: 5s;
  animation-name: run;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  opacity: 1;
  width: 663px;
  height: 663px;
  position: absolute;
}

.imgaction {
  animation-duration: 5s;
  animation-name: run;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  opacity: 1;
  width: 663px;
  height: 663px;
  position: relative;
  top: -0.5%
}

@keyframes run {
  from {
    transform: rotateZ(0);
  }
  to {
    transform: rotateZ(360deg);
  }
}
</style>
