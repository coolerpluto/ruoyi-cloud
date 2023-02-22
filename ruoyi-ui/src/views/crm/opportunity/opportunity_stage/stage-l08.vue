<template>
  <div class="app-container">
    <action-reason :stage-show="8" stage-title="丢标原因分析" reason-dict-code="crm_opportunity_lose_reason"></action-reason>
    <winning-bidding :stage-show="8" stage-title="丢标结论"></winning-bidding>

    <summary-info :stage-show="8" stage-title="项目经验总结"></summary-info>
    <cost-info :stage-show="8" stage-title="费用支出"></cost-info>
  </div>
</template>

<script>
import CostInfo from "../opportunity_data_model/costInfo";
import ActionReason from "../opportunity_data_model/actionReason";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
import WinningBidding from "../opportunity_data_model/winningBidding";
export default {
  name: "stage_L08",
  components: {CostInfo,ActionReason,WinningBidding,
    SummaryInfo,}
}
</script>

<style scoped>

</style>
