<template>
  <div class="app-container">
    <opp-desc :stage-show="3" :stage-title="'用户需求分析'"></opp-desc>
    <opp-implement :stage-show="3" ></opp-implement>
    <competitor-info></competitor-info>
    <KeyContacts :stage-show="3" stage-title="(至少要填写招标方案撰写者)"></KeyContacts>
    <quotation-info></quotation-info>
    <advances-info></advances-info>
    <key-stand-by></key-stand-by>
  </div>
</template>

<script>
import OppInfluence from "../opportunity_data_model/oppInfluence";
import AdvancesInfo from "../opportunity_data_model/advancesInfo";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
import CompetitorInfo from "../opportunity_data_model/competitorInfo";
import QuotationInfo from "../opportunity_data_model/quotationInfo";
import CustInfo from "../opportunity_data_model/custInfo";
import OppDesc from "../opportunity_data_model/oppDesc";
import keyStandBy from "../opportunity_data_model/keyStandBy";
import KeyContacts from "../opportunity_data_model/keyContacts";
import OppImplement from "../opportunity_data_model/oppImplement";

export default {
  name: "stage_L03",
  components: {
    OppImplement,
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
    console.log("l03-created")
    this.initL03();
  },methods:{
    initL03(){
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
