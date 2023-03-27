<template>
  <div class="app-container">
    <action-reason :oppdata="oppdata" ref="actionReason" :stage-show="7" stage-title="停止原因分析" reason-dict-code="crm_opportunity_stop_reason"></action-reason>
    <summary-info  ref="summaryInfo" :oppdata="oppdata" :stage-show="7" stage-title="总结说明"></summary-info>
    <cost-info  ref="costInfo" :oppdata="oppdata" :stage-show="7" stage-title="费用支出"></cost-info>
  </div>
</template>

<script>
import CostInfo from "../opportunity_data_model/costInfo";
import ActionReason from "../opportunity_data_model/actionReason";
import SummaryInfo from "../opportunity_data_model/summaryInfo";

export default {
  name: "stage_L07",
  components: {
    CostInfo,ActionReason,
    SummaryInfo,
  },
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  created() {
    this.initL07();
  },
  methods:{
    initL07(){
    }
  }
}
</script>

<style scoped>

</style>
