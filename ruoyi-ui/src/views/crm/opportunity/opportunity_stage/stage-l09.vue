<template>
  <div class="app-container" style="pointer-events: none;">
    <quotation-info :oppdata="oppdata" ref="quotationInfo" :stage-show="9"></quotation-info>
    <action-reason :oppdata="oppdata" ref="actionReason" :stage-show="5"></action-reason>
    <summary-info ref="summaryInfo" :oppdata="oppdata" :stage-show="9" stage-title="项目经验总结"></summary-info>
    <sign-info :oppdata="oppdata" ref="signInfo"></sign-info>
    <advances-info :oppdata="oppdata" ref="advancesInfo" :stage-show="9"></advances-info>
    <key-stand-by :oppdata="oppdata" ref="keyStandBy" :stage-show="9"></key-stand-by>
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
  props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  created() {
    this.initL09();
  },
  methods:{
    initL09(){
    }
  }
}
</script>

<style scoped>

</style>
