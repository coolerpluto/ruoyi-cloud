<template>
  <div class="app-container">

    <action-reason :stage-show="6" stage-title="放弃原因分析" reason-dict-code="crm_opportunity_drop_reason"></action-reason>


    <summary-info :stage-show="6" stage-title="总结说明"></summary-info>
    <cost-info :stage-show="6" stage-title="费用支出"></cost-info>
  </div>
</template>

<script>
import CostInfo from "../opportunity_data_model/costInfo";
import ActionReason from "../opportunity_data_model/actionReason";
import SummaryInfo from "../opportunity_data_model/summaryInfo";

export default {
  name: "stage_L06",
  components: {CostInfo,ActionReason,
    SummaryInfo,},
  created() {
    console.log("l06-created")
    this.initL06();
  },methods:{
    initL06(){
      this.$parent.getOppCostInfo();
    }
  }
}
</script>

<style scoped>

</style>
