<template>
  <div>
    <cust-info :oppdata="oppdata" ref="custInfo" :stage-show="2"></cust-info>
    <opp-desc :oppdata="oppdata" ref="oppDesc" :stage-show="2"></opp-desc>
    <quotation-info :oppdata="oppdata" ref="quotationInfo" :stage-show="2"></quotation-info>
    <competitor-info :oppdata="oppdata" ref="competitorInfo" :stage-show="2"></competitor-info>
    <KeyContacts ref="keyContacts" :oppdata="oppdata" :stage-show="2" stage-title="至少要填写技术方案撰写者"></KeyContacts>
    <opp-influence ref="oppInfluence" :oppdata="oppdata" :stage-show="2"></opp-influence>
    <summary-info ref="summaryInfo" :oppdata="oppdata" :stage-show="2"></summary-info>
    <advances-info :oppdata="oppdata" ref="advancesInfo" :stage-show="2"></advances-info>
    <key-stand-by :oppdata="oppdata" ref="keyStandBy" :stage-show="2"></key-stand-by>
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
  }, props: {
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  }, created() {
    this.initL02();
  }, methods: {
    initL02() {
    }
  }
}
</script>

<style scoped></style>
