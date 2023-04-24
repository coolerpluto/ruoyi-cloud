<template>
  <div>
    <baseInfo ref="baseInfo" :stage-show="1" :oppdata="oppdata"></baseInfo>
    <KeyContacts ref="keyContacts" :stage-show="1" stage-title="至少要填写商机提供人员" :oppdata="oppdata"></KeyContacts>
    <cust-info :oppdata="oppdata" ref="custInfo"></cust-info>
    <policyStandBy :oppdata="oppdata" ref="policyStandBy"></policyStandBy>
    <opp-desc :oppdata="oppdata" ref="oppDesc" :stage-show="1"></opp-desc>
    <advances-info :oppdata="oppdata" ref="advancesInfo" :stage-show="1"></advances-info>
    <key-stand-by :oppdata="oppdata" ref="keyStandBy" :stage-show="1"></key-stand-by>
  </div>
</template>

<script>
import baseInfo from "../opportunity_data_model/baseInfo";
import policyStandBy from "../opportunity_data_model/policyStandBy";
import AdvancesInfo from "../opportunity_data_model/advancesInfo";
import CustInfo from "../opportunity_data_model/custInfo";
import OppDesc from "../opportunity_data_model/oppDesc";
import KeyContacts from "../opportunity_data_model/keyContacts";
import keyStandBy from "../opportunity_data_model/keyStandBy";

export default {
  name: "stage_L01",
  components: {
    keyStandBy,
    KeyContacts,
    OppDesc,
    CustInfo,
    AdvancesInfo,
    baseInfo,
    policyStandBy,
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
    this.initL01();
  }, methods: {
    initL01() {
    }
  }
}
</script>

<style scoped></style>
