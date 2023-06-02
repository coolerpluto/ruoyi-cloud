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
            个人 商机各阶段数量总和以及分布 数量用柱状图 分布用饼状图
          </div>
        </el-row>
        <el-row class="panel-group">
          <el-col :xs="12" :sm="12" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-money">
                <svg-icon icon-class="money" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">
                  预计成交额
                </div>
                <count-to :start-val="0" :end-val="9280" :duration="4200" class="card-panel-num"/>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="12" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-money">
                <svg-icon icon-class="money" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">
                  成交额
                </div>
                <count-to :start-val="0" :end-val="9280" :duration="4200" class="card-panel-num"/>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <div id="advances">
            个人 商机最新进展 10条 轮播滚动显示
          </div>
        </el-row>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <h2 align="center">全员排名模块</h2>
        <el-row>
          <div id="oppoTop101">
            各阶段个人拥有商机数量Top10 滚动排名显示
          </div>
        </el-row>
        <el-row>
          <div id="oppoTop102">
            个人成交额Top10 滚动排名显示
          </div>
        </el-row>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <h2 align="center">全体人员</h2>
        <el-row>
          <div id="allStageSituation">
            全部 商机各阶段数量总和以及分布
          </div>
        </el-row>
        <el-row class="panel-group">
          <el-col :xs="12" :sm="12" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-money">
                <svg-icon icon-class="money" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">
                  预计成交额
                </div>
                <count-to :start-val="0" :end-val="9280" :duration="4200" class="card-panel-num"/>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="12" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-money">
                <svg-icon icon-class="money" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">
                  成交额
                </div>
                <count-to :start-val="0" :end-val="9280" :duration="4200" class="card-panel-num"/>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <div id="allAdvances">
            全部 商机最新进展 前10或20条
          </div>
        </el-row>
      </el-col>
    </div>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  name: "dashboard",
  components: {
    CountTo
  },
  dicts: [],
  data() {
    return {
      // 遮罩层
      loading: true,
    };
  },
  created() {
    //this.getList();
    //this.getDeptTree();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
    },
    /** 查询参数列表 */
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

.panel-group {
  margin-top: 18px;

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    .icon-money {
      color: #f4516c;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
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
  top:-0.5%
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
