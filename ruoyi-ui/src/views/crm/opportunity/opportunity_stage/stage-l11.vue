<template>
  <div class="app-container">
    <action-reason :stage-show="11" stage-title="关闭原因分析" reason-dict-code="crm_opportunity_close_reason"></action-reason>


    <summary-info :stage-show="11" stage-title="总结说明"></summary-info>
  </div>
</template>

<script>

import ActionReason from "../opportunity_data_model/actionReason";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
export default {
  name: "stage_L11",
  components: {
    ActionReason,
    SummaryInfo,
  },
  created() {
    console.log("l11-created")
    this.initL11();
  },
  methods:{
    initL11(){
      this.$parent.getOppCostInfo();
    }
  }

}
</script>

<style scoped>

</style>
