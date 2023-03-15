<template>
  <div>
    <cust-info :stage-show="2"></cust-info>
    <opp-desc :stage-show="2"></opp-desc>
    <quotation-info></quotation-info>
    <competitor-info></competitor-info>
    <KeyContacts :stage-show="2" stage-title="(至少要填写技术方案撰写者)"></KeyContacts>
    <opp-influence :stage-show="2"></opp-influence>
    <summary-info></summary-info>
    <advances-info></advances-info>
    <key-stand-by></key-stand-by>
  </div>
</template>

<script>
import QuotationInfo from "../opportunity_data_model/quotationInfo";
import CompetitorInfo from "../opportunity_data_model/competitorInfo";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
import CustInfo from "../opportunity_data_model/custInfo";
import OppDesc from "../opportunity_data_model/oppDesc";
import KeyContacts from "../opportunity_data_model/keyContacts";
import keyStandBy from "../opportunity_data_model/keyStandBy";
import AdvancesInfo from "../opportunity_data_model/advancesInfo";
import OppInfluence from "../opportunity_data_model/oppInfluence";

export default {
  name: "stage_L02",
  components: {
    OppInfluence,
    AdvancesInfo,
    SummaryInfo,
    CompetitorInfo,
    QuotationInfo,
    CustInfo,
    OppDesc,
    keyStandBy,
    KeyContacts
  },created() {
    console.log("l02-created")
    this.initL02();
  },methods:{
    initL02(){
      this.$parent.getOppCompetitorInfo();
      this.$parent.getOppQuotationInfo();
      this.$parent.getOppContactsInfo();
      this.$parent.getOppAdvancesInfo();
      this.$parent.getOppSupportInfo();
    }
  }
}
</script>

<style scoped>

</style>
