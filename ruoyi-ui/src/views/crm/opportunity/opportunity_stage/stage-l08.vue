<template>
  <div class="app-container">
    <action-reason :oppdata="oppdata" ref="actionReason" :stage-show="8" stage-title="丢标原因分析" reason-dict-code="crm_opportunity_lose_reason"></action-reason>
    <winning-bidding :oppdata="oppdata" ref="winningBidding" :stage-show="8" stage-title="丢标结论"></winning-bidding>
    <summary-info ref="summaryInfo" :oppdata="oppdata" :stage-show="8" stage-title="项目经验总结"></summary-info>
    <cost-info  ref="costInfo" :oppdata="oppdata" :stage-show="8" stage-title="费用支出"></cost-info>
  </div>
</template>

<script>
import CostInfo from "../opportunity_data_model/costInfo";
import ActionReason from "../opportunity_data_model/actionReason";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
import WinningBidding from "../opportunity_data_model/winningBidding";
export default {
  name: "stage_L08",
  components: {
    CostInfo,ActionReason,
    WinningBidding,SummaryInfo,
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
    this.initL08();
  },
  methods:{
    initL08(){
    }
  }
}
</script>

<style scoped>

</style>
