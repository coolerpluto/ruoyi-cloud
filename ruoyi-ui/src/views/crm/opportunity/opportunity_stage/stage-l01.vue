<template>
  <div>
    <baseInfo :stage-show="1"></baseInfo>
    <KeyContacts :stage-show="1" stage-title="(至少要填写商机提供人员)"></KeyContacts>
    <cust-info></cust-info>
    <policyStandBy></policyStandBy>
    <opp-desc :stage-show="1"></opp-desc>
    <advances-info stage-show="1"></advances-info>
    <key-stand-by stage-show="1"></key-stand-by>
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
  created() {
    console.log("l01-created")
    this.initL01();
  },methods:{
    initL01(){
      this.$parent.getOppUserInfo();
      this.$parent.getOppBaseInfo();
      this.$parent.getOppPolicyInfo();
      this.$parent.getOppAdvancesInfo();
      this.$parent.getOppSupportInfo();
      this.$parent.getOppContactsInfo();
    }

  }
}
</script>

<style scoped>

</style>
