<template>
  <div class="app-container">
    <quotation-info :stage-show="5"></quotation-info>
    <action-reason :stage-show="5"></action-reason>
    <summary-info :stage-show="5" stage-title="项目经验总结"></summary-info>
    <sign-info></sign-info>
    <advances-info :stage-show="5"></advances-info>
    <key-stand-by :stage-show="5"></key-stand-by>
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
  name: "stage_L05",
  components: {
    ActionReason,
    SummaryInfo,
    SignInfo,
    QuotationInfo,

    AdvancesInfo,
    keyStandBy,
  }
}
</script>

<style scoped>

</style>
