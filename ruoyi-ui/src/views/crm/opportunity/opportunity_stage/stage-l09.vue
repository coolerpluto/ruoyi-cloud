<template>
  <div class="app-container" style="pointer-events: none;">
    <quotation-info :stage-show="9"></quotation-info>
    <action-reason :stage-show="9"></action-reason>
    <summary-info :stage-show="9" stage-title="项目经验总结"></summary-info>
    <sign-info></sign-info>
    <advances-info :stage-show="9"></advances-info>
    <key-stand-by :stage-show="9"></key-stand-by>
  </div>
</template>

<script>
import keyStandBy from "../opportunity_data_model/keyStandBy";
import AdvancesInfo from "../opportunity_data_model/advancesInfo";
import QuotationInfo from "../opportunity_data_model/quotationInfo";
import SignInfo from "../opportunity_data_model/signInfo";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
import ActionReason from "../opportunity_data_model/actionReason";
export default {
  name: "stage_L09",
  components: {
    ActionReason,
    SummaryInfo,
    SignInfo,
    QuotationInfo,

    AdvancesInfo,
    keyStandBy,
  },
  created() {
    console.log("l09-created")
    this.initL09();
  },
  methods:{
    initL09(){
      this.$parent.getOppQuotationInfo();
      this.$parent.getOppAdvancesInfo();
      this.$parent.getOppSupportInfo();
    }
  }
}
</script>

<style scoped>

</style>
