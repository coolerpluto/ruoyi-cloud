<template>
  <div class="panel-group">
    <div class="block">
      <span class="demonstration">统计时间：</span>
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        align="right"
        unlink-panels
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :picker-options="pickerOptions">
      </el-date-picker>
    </div>
    <el-col :xs="12" :sm="12" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            预计成交额
          </div>
          <count-to :start-val="0" :end-val="preSaleVal" :duration="4200" class="card-panel-num"/>
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
          <count-to :start-val="0" :end-val="saleVal" :duration="4200" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  name: "SalesAmount",
  components: {
    CountTo
  },
  props: {
    dataScope: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      preSaleVal: 50000,
      saleVal: 23340,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      dateRange: [],
    };
  },
  created() {
    this.init();
  },
  mounted() {
  },
  methods: {
    init() {
      console.log(this.$options.name + ":" + this.dataScope);
      //TODO 调用接口 查询预计成交额和成交额 入参 dataScope dateRange
    },
  },
}
</script>

<style rel="stylesheet/scss" lang="scss">

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

</style>
