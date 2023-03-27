<template>
  <div class="app-container">
    <quotation-info :oppdata="oppdata" ref="quotationInfo" :stage-show="5"></quotation-info>
    <action-reason :oppdata="oppdata" ref="actionReason" :stage-show="5"></action-reason>
    <summary-info ref="summaryInfo" :oppdata="oppdata" :stage-show="5" stage-title="项目经验总结"></summary-info>
    <sign-info :oppdata="oppdata" ref="signInfo" :stage-show="5"></sign-info>
    <advances-info :oppdata="oppdata" ref="advancesInfo" :stage-show="5"></advances-info>
    <key-stand-by :oppdata="oppdata" ref="keyStandBy"  :stage-show="5"></key-stand-by>
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
    this.initL05();
  },methods:{
    initL05(){
    }
  }
}
</script>

<style scoped>

</style>
