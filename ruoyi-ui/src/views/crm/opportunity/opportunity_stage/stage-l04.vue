<template>
  <div class="app-container">
    <opp-desc :oppdata="oppdata" ref="oppDesc" :stage-show="4" :stage-title="'商机日期描述'"></opp-desc>
    <bidding-info :oppdata="oppdata" ref="biddingInfo" :stage-show="4"></bidding-info>
    <competitor-info :oppdata="oppdata" ref="competitorInfo" :stage-show="4"></competitor-info>
    <KeyContacts :oppdata="oppdata" ref="keyContacts" :stage-show="4" stage-title="至少要填写评标专家关键人"></KeyContacts>
    <quotation-info :oppdata="oppdata" ref="quotationInfo" :stage-show="4"></quotation-info>
    <winning-bidding :oppdata="oppdata" ref="winningBidding" :stage-show="4" :stage-title="'中标结论'"></winning-bidding>
<!--    <competitive-bidding :stage-show="4"></competitive-bidding>-->
    <summary-info ref="summaryInfo" :oppdata="oppdata" :stage-show="4"></summary-info>
    <advances-info :oppdata="oppdata" ref="advancesInfo" :stage-show="4"></advances-info>
    <key-stand-by :oppdata="oppdata" ref="keyStandBy" :stage-show="4"></key-stand-by>
  </div>
</template>

<script>
import AdvancesInfo from "../opportunity_data_model/advancesInfo";
import SummaryInfo from "../opportunity_data_model/summaryInfo";
import CompetitorInfo from "../opportunity_data_model/competitorInfo";
import QuotationInfo from "../opportunity_data_model/quotationInfo";
import OppDesc from "../opportunity_data_model/oppDesc";
import keyStandBy from "../opportunity_data_model/keyStandBy";
import KeyContacts from "../opportunity_data_model/keyContacts";
import CompetitiveBidding from "../opportunity_data_model/competitiveBidding";
import WinningBidding from "../opportunity_data_model/winningBidding";
import BiddingInfo from "../opportunity_data_model/biddingInfo";

export default {
  name: "stage_L04",
  components: {
    BiddingInfo,
    WinningBidding,
    CompetitiveBidding,
    AdvancesInfo,
    SummaryInfo,
    CompetitorInfo,
    QuotationInfo,
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
    this.initL04();
  },methods:{
    initL04(){
    }
  }
}
</script>

<style scoped>

</style>
