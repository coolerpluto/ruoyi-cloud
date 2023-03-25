<template>
  <div class="app-container">
    <opp-desc :oppdata="oppdata" ref="oppDesc" :stage-show="3" :stage-title="'用户需求分析'"></opp-desc>
    <opp-implement :oppdata="oppdata" ref="oppImplement" :stage-show="3" ></opp-implement>
    <competitor-info :oppdata="oppdata" ref="competitorInfo" :stage-show="3"></competitor-info>
    <KeyContacts ref="keyContacts" :oppdata="oppdata" :stage-show="3" stage-title="至少要填写招标方案撰写者"></KeyContacts>
    <quotation-info :oppdata="oppdata" ref="quotationInfo" :stage-show="3"></quotation-info>
    <advances-info :oppdata="oppdata" ref="advancesInfo" :stage-show="3"></advances-info>
    <key-stand-by :oppdata="oppdata" ref="keyStandBy" :stage-show="3"></key-stand-by>
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
    this.initL03();
  },methods:{
    initL03(){
      
    }
  }
}
</script>

<style scoped>

</style>
